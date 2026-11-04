package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * Mail settings, bound from nyehms.mail.* in application.yml + application-{profile}.yml.
 * Recipients are comma-separated address lists and differ per environment;
 * subjects and message texts are the same everywhere and live in application.yml.
 */
@ConfigurationProperties(prefix = "nyehms.mail")
public record MailProperties(
        Recipients helpLine,
        Recipients acrf,
        BureauDirector bureauDirector,
        Recipients ahiDocument,
        PsMessage psMessage,
        AirsAgency airsAgency,
        String eharsLoaderTo,
        String recordSearchUploadCc,
        String errorNotificationsTo,
        String documentStatusReportTo,
        String hivDocumentUpdateDateTo) {

    public record Recipients(String from, String to, String cc, String subject) {}

    /**
     * OOC/DNLTC provider-portal summary sent to the Data to Care Unit.
     * The *MsgContent templates contain placeholders (_FACILITY_NAME_, _PROV_COUNTY_, ...)
     * that DocumentReviewTransactionHelper fills in.
     */
    public record BureauDirector(String to, String cc, String subject, String header, String warningMsg,
                                 String hicappMsgContent, String generalMsgContent) {}

    public record PsMessage(String dodTo, String dodCc, String uploadCc) {}

    /** Status email for AIRS extracts from agencies missing in the BHAE reference table. */
    public record AirsAgency(String subject, String bodyMsg) {}
}
