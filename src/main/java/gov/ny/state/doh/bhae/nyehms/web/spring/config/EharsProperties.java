package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Display text for eHARS diagnosis codes, bound from nyehms.ehars.* .
 */
@ConfigurationProperties(prefix = "nyehms.ehars")
public record EharsProperties(
        /* STAGE_ZERO_DX code (Y/N) -> text */
        Map<String, String> stageZeroDx,
        /* STAGE_CD4_OI_DX code (1/2/3/9) -> text */
        Map<String, String> stageCd4OiDx) {
}
