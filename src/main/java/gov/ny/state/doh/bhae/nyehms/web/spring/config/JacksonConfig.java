package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tools.jackson.databind.annotation.JsonPOJOBuilder;
import tools.jackson.databind.cfg.MapperConfig;
import tools.jackson.databind.introspect.AccessorNamingStrategy;
import tools.jackson.databind.introspect.AnnotatedClass;
import tools.jackson.databind.introspect.DefaultAccessorNamingStrategy;

/**
 * Jackson 3 settings that keep the JSON identical to what the pages got from Jackson 2.
 * <p>
 * Most of it is in application.yml (spring.jackson.use-jackson2-defaults and dates as
 * timestamps). The one thing Jackson 3 has no setting for is how a property name is made
 * from a getter, and the VOs depend on it because their fields are UPPER_CASE:
 * <pre>
 *   getWORKER_ID()   Jackson 2: "worker_ID"    Jackson 3: "WORKER_ID"
 *   getSTATENO()     Jackson 2: "stateno"      Jackson 3: "STATENO"
 * </pre>
 * The JS reads the Jackson 2 names (e.g. row.worker_ID, row.create_DATE), so this restores
 * the Jackson 2 rule: lower-case the leading run of capital letters. Remove it only together
 * with the JS, or once the VOs are replaced (JPA/DTO step) and the names are set explicitly.
 */
@Configuration(proxyBeanMethods = false)
public class JacksonConfig {

    @Bean
    JsonMapperBuilderCustomizer jackson2PropertyNames() {
        return builder -> builder.accessorNaming(new Jackson2NamingProvider());
    }

    /** Same prefixes as Jackson's default; base-name check off, as in Jackson 2 (getvalue() is a getter). */
    static class Jackson2NamingProvider extends DefaultAccessorNamingStrategy.Provider {

        private static final long serialVersionUID = 1L;

        Jackson2NamingProvider() {
            super("set", JsonPOJOBuilder.DEFAULT_WITH_PREFIX, "get", "is", null);
        }

        @Override
        public AccessorNamingStrategy forPOJO(MapperConfig<?> config, AnnotatedClass targetClass) {
            return new Jackson2Naming(config, targetClass, _setterPrefix, _getterPrefix, _isGetterPrefix);
        }
    }

    static class Jackson2Naming extends DefaultAccessorNamingStrategy {

        Jackson2Naming(MapperConfig<?> config, AnnotatedClass forClass,
                       String mutatorPrefix, String getterPrefix, String isGetterPrefix) {
            super(config, forClass, mutatorPrefix, getterPrefix, isGetterPrefix, null);
        }

        /** Jackson 2's legacyManglePropertyName: "WORKER_ID" -> "worker_ID", "URL" -> "url", "Name" -> "name". */
        @Override
        protected String stdManglePropertyName(String basename, int offset) {
            int end = basename.length();
            if (end == offset) {
                return null;
            }
            char c = basename.charAt(offset);
            char d = Character.toLowerCase(c);
            if (c == d) {
                return basename.substring(offset);
            }
            StringBuilder sb = new StringBuilder(end - offset);
            sb.append(d);
            for (int i = offset + 1; i < end; ++i) {
                c = basename.charAt(i);
                d = Character.toLowerCase(c);
                if (c == d) {
                    sb.append(basename, i, end);
                    break;
                }
                sb.append(d);
            }
            return sb.toString();
        }
    }
}
