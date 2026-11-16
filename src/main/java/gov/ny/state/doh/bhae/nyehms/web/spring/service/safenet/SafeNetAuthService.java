package gov.ny.state.doh.bhae.nyehms.web.spring.service.safenet;

import CRYPTOCard.API.CRYPTOCardAPI;
import gov.ny.state.doh.bhae.nyehms.web.spring.config.SafeNetProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Security;
import java.util.Arrays;

/**
 * @author rzp05
 * Crypto card (SafeNet MobilePASS) authentication using the Thales
 * SafeNet Java Authentication SDK 2.1.1 (pure Java, no native library).
 *
 * <p>The SDK reads its server URLs, agent key, FIPS mode and logging settings from the INI file
 * configured in {@code safenet.ini-path}. The PIN / passcode is never logged.</p>
 */

@Slf4j
@Service
public class SafeNetAuthService {

    // ReturnedResult codes from arrData[7] from SafeNet API
    private static final int RC_FAILURE = 0;
    private static final int RC_SUCCESS = 1;

    //arrData layout (Dev guide 2.1.1)
    private static final int IDX_USER = 0;
    private static final int IDX_OTP = 2;
    private static final int IDX_RESULT = 7;
    private static final int IDX_BOTH_SERVERS_DOWN = 8;
    private static final int IDX_ERROR_MESSAGE = 9;
    private static final int ARR_SIZE = 12;  //[10] client IP, [11] resource name: optional, left blan

    private final SafeNetProperties  safeNetProperties;

    private CRYPTOCardAPI api;
    private volatile boolean initialized;

    public SafeNetAuthService(SafeNetProperties safeNetProperties) {
        this.safeNetProperties = safeNetProperties;
    }

    /** Initializes the SDK once for the life of the application, as the Dev Guide recommends. */
    @PostConstruct
    void init(){
        String iniPath = safeNetProperties.iniPath();
        if(iniPath == null ||  iniPath.isBlank() || !Files.isReadable(Path.of(iniPath))){
            log.error("SafeNet INI file not found or not readable at [{}]. Crypto card login will be unavailable.", iniPath);
            return;
        }
        try{
            api = CRYPTOCardAPI.getInstance();
            api.setINIPath(iniPath);
            api.LoadJNILibrary(); // SDK 2.x : reads the INI and initializes; name kept for backward compatibility

            initialized = true;
            log.info("SafeNet SDK initialized using INI [{}]", iniPath);

        } catch (Exception | LinkageError ex) {
            log.error("SafeNet SDK initialization failed using INI [{}]", iniPath, ex);
        }
    }

    /** The SDK registers the BC FIPS provider JVM-wide; remove it on undeploy so a
     *  redeploy doesn't keep using classes from the old (closed) classloader. */
    @PreDestroy
    void shutdown(){
        Security.removeProvider("BCFIPS");
    }

    /**
     * Verifies a crypto card PIN + passcode for the given user.
     *
     * @param userId HCS user id, e.g. RZP05
     * @param pinAndPasscode the user's PIN followed by the current MobilePASS passcode
     */
    public synchronized SafeNetAuthResult authenticate(String userId, String pinAndPasscode){

        if(CommonUtil.isNullOrEmpty(userId) || CommonUtil.isNullOrEmpty(pinAndPasscode)){
            return SafeNetAuthResult.INVALID_INPUT;
        }

        if(!initialized){
            log.error("SafeNet authentication requested for user [{}] but the SDK is not initialized", userId);
            return SafeNetAuthResult.UNAVAILABLE;
        }

        String[] arrData = new String[ARR_SIZE];

        Arrays.fill(arrData, ""); // SDK expect string, never null

        arrData[IDX_USER] = userId.trim();
        arrData[IDX_OTP] = pinAndPasscode.trim();

        try{
            api.Authenticate(arrData);

        } catch (Exception | LinkageError ex) {
            log.error("SafeNet Authenticate call failed for user [{}]", userId, ex);
            return SafeNetAuthResult.UNAVAILABLE;
        }

        String errorMessage = arrData[IDX_ERROR_MESSAGE];

        if("1".equals(trim(arrData[IDX_BOTH_SERVERS_DOWN]))){
            log.error("SafeNet: both authentication servers are down. Message [{}]", errorMessage);
            return SafeNetAuthResult.UNAVAILABLE;
        }

        int resultCode;
        try{
            resultCode = Integer.parseInt(trim(arrData[IDX_RESULT]));
        }catch(NumberFormatException ex){
            log.error("SafeNet returned no result code for user [{}]. Message [{}]", userId,
                    errorMessage);
            return SafeNetAuthResult.UNAVAILABLE;
        }

        switch (resultCode) {

            case RC_SUCCESS:
                log.info("SafeNet authentication succeeded for user [{}]", userId);
                return SafeNetAuthResult.SUCCESS;
            case RC_FAILURE:
                if (CommonUtil.isNotNullAndEmpty(errorMessage)) {
                    // A message alongside code 0 usually means a communication/key problem, not a wrong passcode
                    log.warn("SafeNet authentication failed for user [{}]. Message [{}]", userId,
                            errorMessage);
                    return  SafeNetAuthResult.UNAVAILABLE;
                }else {
                    log.info("SafeNet authentication failed for user [{}] (wrong PIN/passcode)",
                                userId);
                    }
                return SafeNetAuthResult.FAILED;
            default:
                // 2 challenge, 3 server PIN, 4 user PIN change, 5 outer window, 6-8 password/PIN change, 9 expired
                log.warn("SafeNet returned unsupported result code [{}] for user [{}]. Message [{}]",
                        resultCode, userId, errorMessage);

                return SafeNetAuthResult.ACTION_REQUIRED;

        }

    }

    public boolean isAvailable() {
        return initialized;
    }


    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }

}
