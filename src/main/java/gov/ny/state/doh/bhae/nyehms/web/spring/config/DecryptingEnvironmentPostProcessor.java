package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import org.springframework.boot.EnvironmentPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Runs at startup, right after the yml files are loaded.
 * Finds every ENC(...) value and replaces it with the decrypted value.
 */
public class DecryptingEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    private static final String KEY_NAME = "NYEHMS_CONFIG_KEY";
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment env, SpringApplication app) {
        Map<String, Object> decrypted = new HashMap<>();
        Set<String> seen = new HashSet<>();
        for (PropertySource<?> source : env.getPropertySources()) {
            if (source instanceof EnumerablePropertySource<?> enumerable) {
                collectDecrypted(env, enumerable, decrypted, seen);
            }
        }
        if (!decrypted.isEmpty()) {
            env.getPropertySources().addFirst(new MapPropertySource("decryptedProperties", decrypted));
        }
    }
    /** Looks at every property in one source and decrypts the ENC(...) ones (raw values, no placeholder resolution). */
    private void collectDecrypted(ConfigurableEnvironment env, EnumerablePropertySource<?> source,
                                  Map<String, Object> decrypted, Set<String> seen) {
        for (String name : source.getPropertyNames()) {
            if (!seen.add(name)) {
                continue;                       // a higher-priority source already defines this name
            }
            Object raw = source.getProperty(name);   // raw value: does NOT resolve ${...}
            if (raw instanceof String value && ConfigCrypto.isEncrypted(value)) {
                decrypted.put(name, decrypt(env, name, value));
            }
        }
    }

    /** Decrypts one value, with clear error messages if the key is missing or wrong. */
    private String decrypt(ConfigurableEnvironment env, String name, String value) {
        String key = env.getProperty(KEY_NAME);
        if (key == null || key.isBlank()) {
            throw new IllegalStateException(KEY_NAME + " is not set, cannot decrypt " + name);
        }
        try {
            return ConfigCrypto.decrypt(value, key);
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Could not decrypt " + name + " (wrong key?)", e);
        }
    }
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE; // run after the yml files are loaded
    }
}