package gov.ny.state.doh.bhae.nyehms.web.spring.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * SafeNet (Thales) Java Authentication SDK settings.
 *
 * <pre>
 * safenet:
 * ini-path: 'C:/Program Files/Thales/JavaSDK/ini/JCryptoWrapperWin.ini'
 * </pre>
 *
 * @param iniPath full path to JCryptoWrapperWin.ini (Windows) or JCryptoWrapper.ini (Linux)
 */


@ConfigurationProperties(prefix="nyehms.safenet")
public record SafeNetProperties(String iniPath){

}
