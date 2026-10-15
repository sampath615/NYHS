package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import gov.ny.state.doh.bhae.nyehms.web.spring.view.assignment.AssignmentsPdfView;
import gov.ny.state.doh.bhae.nyehms.web.spring.view.report.GenerateNdvlReportSpreadsheet;
import gov.ny.state.doh.bhae.nyehms.web.spring.view.report.GeneratePsMessageOOCExpsReport;
import gov.ny.state.doh.bhae.nyehms.web.spring.view.report.GeneratePsMessageReport;
import gov.ny.state.doh.bhae.nyehms.web.spring.view.report.GenerateRiaActivityReportSpreadsheet;
import gov.ny.state.doh.bhae.nyehms.web.spring.view.review.*;
import gov.ny.state.doh.bhae.nyehms.web.spring.view.uploads.PSConfirmedSTDPdfView;
import gov.ny.state.doh.bhae.nyehms.web.spring.view.uploads.UpdatedMessagesPdfView;
import gov.ny.state.doh.bhae.nyehms.web.spring.view.uploads.UploadedRecordSearchStatnosPdfView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * PDF and Excel download views.
 * <p>
 * Replaces the 25 view beans of spring-config.xml. The METHOD NAME is the bean
 * name, and the bean name is the view name the controllers return (resolved by
 * the BeanNameViewResolver in {@link WebMvcConfig}). Renaming a method here
 * breaks the matching download, so keep the names in step with the controllers.
 */
//@Configuration
public class ReportViewConfig {

/*    @Bean
    public UpdatedMessagesPdfView  updatedMessageResultsPdfView(){
        return new UpdatedMessagesPdfView();
    }*/

    /*@Bean
    public DownloadFeedbackSNPDataView downloadFeedbackSNPDataView() {
        return new DownloadFeedbackSNPDataView();
    }*/

   /* @Bean
    public GenerateEPRFSummarySpreadsheet generateEPRFSummarySpreadsheet() {
        return new GenerateEPRFSummarySpreadsheet();
    }*/

    /*@Bean
    public GenerateCDCReturnFileSpreadsheet generateCDCReturnFile() {
        return new GenerateCDCReturnFileSpreadsheet();
    }*/

  /*  @Bean
    public GenerateLabErrorReportByErrorCode generateLabErrorReportByErrorCode() {
        return new GenerateLabErrorReportByErrorCode();
    }*/

  /*  @Bean
    public GenerateLabErrorReportByFacility generateLabErrorReportByFacility() {
        return new GenerateLabErrorReportByFacility();
    }
*/
   /* @Bean
    public GenerateLabErrorReportByStatus generateLabErrorReportByStatus() {
        return new GenerateLabErrorReportByStatus();
    }*/

 /*   @Bean
    public GenerateLabErrorStatusReportByFacility generateLabErrorStatusReportByFacility() {
        return new GenerateLabErrorStatusReportByFacility();
    }
*/
  /*  @Bean
    public GenerateMergeReportByOrigin generateMergeReportByOrigin() {
        return new GenerateMergeReportByOrigin();
    }*/

  /*  @Bean
    public GenerateMergeReportByBadMerges generateMergeReportByBadMerges() {
        return new GenerateMergeReportByBadMerges();
    }*/

   /* @Bean
    public GenerateMergeReportByDailyStaff generateMergeReportByDailyStaff() {
        return new GenerateMergeReportByDailyStaff();
    }*/

/*    @Bean
    public GenerateBadMergeReportByDailyStaff generateBadMergeReportByDailyStaff() {
        return new GenerateBadMergeReportByDailyStaff();
    }*/

    /*@Bean
    public GenerateADAPNYCDataSpreadsheet generateADAPNYCDataFile() {
        return new GenerateADAPNYCDataSpreadsheet();
    }*/

    /*@Bean
    public GenerateADAPNYCSummarySpreadsheet generateADAPNYCSummaryFile() {
        return new GenerateADAPNYCSummarySpreadsheet();
    }*/

  /*  @Bean
    public GenerateStatenoHistoryData generateStatenoHistoryData() {
        return new GenerateStatenoHistoryData();
    }*/

    /*@Bean
    public GenerateCurrentStatenoData generateCurrentStatenoData() {
        return new GenerateCurrentStatenoData();
    }*/

/*    @Bean
    public UploadedRecordSearchStatnosPdfView uploadedRecordSearchStatnosPdfView() {
        return new UploadedRecordSearchStatnosPdfView();
    }*/

   /* @Bean
    public PSConfirmedSTDPdfView psConfirmedSTDErrorPdfView() {
        return new PSConfirmedSTDPdfView();
    }*/

/*    @Bean
    public GeneratePsMessageReport psMessageReportSpreadsheet() {
        return new GeneratePsMessageReport();
    }*/

  /*  @Bean
    public GeneratePsMessageOOCExpsReport generatePsOOCExpsReport() {
        return new GeneratePsMessageOOCExpsReport();
    }*/

/*    @Bean
    public GenerateNdvlReportSpreadsheet ndvlReportSpreadsheet() {
        return new GenerateNdvlReportSpreadsheet();
    }*/

/*    @Bean
    public GenerateRiaActivityReportSpreadsheet riaActivityReportSpreadsheet() {
        return new GenerateRiaActivityReportSpreadsheet();
    }*/

    /*@Bean
    public GenerateScanningReportByDailyStaff generateScanningReportByDailyStaff() {
        return new GenerateScanningReportByDailyStaff();
    }*/

 /*   @Bean
    public GenerateClericalReportByDailyStaff generateClericalReportsByDailyStaff() {
        return new GenerateClericalReportByDailyStaff();
    }*/




}
