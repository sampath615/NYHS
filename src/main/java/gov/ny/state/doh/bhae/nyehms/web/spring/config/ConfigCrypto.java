package gov.ny.state.doh.bhae.nyehms.web.spring.config;


import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;

import static gov.ny.state.doh.bhae.nyehms.web.spring.controller.initialize.InitializeController.decrypt;


public final class ConfigCrypto {

    private static final int IV_LENGTH = 12;
    private  static final int TAG_BITS = 128;

    private ConfigCrypto() {}

    /** Is the value in ENC(...) format? */
    public static boolean isEncrypted(String value) {
        return value != null && value.startsWith("ENC(") && value.endsWith(")");
    }

    /** Plain text -> ENC(...) */
    public static String encrypt(String plain, String base64Key) throws GeneralSecurityException{
        byte[] iv =  new byte[IV_LENGTH];
        new SecureRandom().nextBytes(iv);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        cipher.init(Cipher.ENCRYPT_MODE,key(base64Key), new GCMParameterSpec(TAG_BITS,iv));

        byte[] encrypted = cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8));



        byte[] combined = ByteBuffer.allocate(iv.length + encrypted.length).put(iv).put(encrypted).array();

        return "ENC(" + Base64.getEncoder().encodeToString(combined) + ")";
    }

        /** ENC(...) -> plain text */
        public static String decrypt(String encValue, String base64Key) throws GeneralSecurityException {
            byte[] combined = Base64.getDecoder().decode(encValue.substring(4, encValue.length() - 1));
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, key(base64Key), new GCMParameterSpec(TAG_BITS, combined, 0, IV_LENGTH));
            byte[] plain = cipher.doFinal(combined, IV_LENGTH, combined.length - IV_LENGTH);
            return new String(plain, StandardCharsets.UTF_8);
        }

        private static SecretKeySpec key(String base64Key) {
            return new SecretKeySpec(Base64.getDecoder().decode(base64Key), "AES");
        }
        /** Developer tool: prints ENC(...) for the given password. */
        public static void main(String[] args) throws Exception {
            String key = System.getenv("NYEHMS_CONFIG_KEY");
            String enc = encrypt(args[0], key);
            System.out.println(enc);
            System.out.println("Round-trip OK: " + decrypt(enc, key).equals(args[0]));
        }
}
