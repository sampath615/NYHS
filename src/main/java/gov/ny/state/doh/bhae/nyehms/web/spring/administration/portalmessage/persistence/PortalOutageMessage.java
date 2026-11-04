package gov.ny.state.doh.bhae.nyehms.web.spring.administration.portalmessage.persistence;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * A Provider Portal outage notice (table PORTAL_MODULE_OUTAGE). One row targets one portal module,
 * marked by a 'Y' in that module's column (ADMIN, PRF_DE, OOC_INQ, DNLTC_INQ, SNP_ID or AIRS_EPI).
 * <p>
 * Database behaviour this entity relies on:
 * <ul>
 *   <li>MSG_ID defaults to PORTAL_OUTAGE_MSG_ID.NEXTVAL, so it is left out of the INSERT (IDENTITY strategy).
 *       The table has no primary-key constraint; the sequence keeps MSG_ID unique.</li>
 *   <li>FLAG_DELETED defaults to 'N'. Set explicitly anyway.</li>
 * </ul>
 * CREATE_BY / UPDATE_BY hold the user id (not the worker id).
 */
@Entity
@Table(name = "PORTAL_MODULE_OUTAGE")
public class PortalOutageMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MSG_ID")
    private Long id;

    @Column(name = "ADMIN", length = 1)
    private String admin;

    @Column(name = "PRF_DE", length = 1)
    private String prfDe;

    @Column(name = "OOC_INQ", length = 1)
    private String oocInq;

    @Column(name = "DNLTC_INQ", length = 1)
    private String dnltcInq;

    @Column(name = "SNP_ID", length = 1)
    private String snpId;

    @Column(name = "AIRS_EPI", length = 1)
    private String airsEpi;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "DISPLAY_MSG", length = 4000)
    private String displayMsg;

    @Column(name = "FLAG_DELETED", length = 1)
    private String flagDeleted;

    @Column(name = "CREATE_BY", length = 10, updatable = false)
    private String createBy;

    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "CREATE_DATE", updatable = false)
    private LocalDateTime createDate;

    @Column(name = "UPDATE_BY", length = 10)
    private String updateBy;

    /** Database time on insert and every update (was SYSDATE in the old SQL). */
    @UpdateTimestamp(source = SourceType.DB)
    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    protected PortalOutageMessage() {
    }

    public static PortalOutageMessage create(String moduleCode, LocalDateTime start, LocalDateTime end,
                                             String displayMsg, String userId) {
        PortalOutageMessage message = new PortalOutageMessage();
        message.setModule(moduleCode);
        message.startDate = start;
        message.endDate = end;
        message.displayMsg = displayMsg;
        message.flagDeleted = "N";
        message.createBy = userId;
        message.updateBy = userId;
        return message;
    }

    public void edit(String moduleCode, LocalDateTime start, LocalDateTime end, String displayMsg, String userId) {
        setModule(moduleCode);
        this.startDate = start;
        this.endDate = end;
        this.displayMsg = displayMsg;
        this.updateBy = userId;
    }

    /** Soft delete; only the flag and "updated by" change (as before). */
    public void markDeleted(String userId) {
        this.flagDeleted = "Y";
        this.updateBy = userId;
    }

    /**
     * Sets 'Y' on the column for the page's module code (PROVIDER_PORTAL_MODULES.PP_MODULE_CD)
     * and clears the others. An unknown code leaves all six empty, as the old code did.
     */
    private void setModule(String moduleCode) {
        admin = prfDe = oocInq = dnltcInq = snpId = airsEpi = null;
        switch (moduleCode == null ? "" : moduleCode) {
            case "ADMIN_PAGE" -> admin = "Y";
            case "EPRF_DE" -> prfDe = "Y";
            case "OOC_INQ" -> oocInq = "Y";
            case "DNLTC_INQ" -> dnltcInq = "Y";
            case "SNP_ID" -> snpId = "Y";
            case "AIRS_EPI" -> airsEpi = "Y";
            default -> { }
        }
    }

    /**
     * The grid's "Module" column: name of the first flagged column, or " " (old SQL CASE expression).
     * Note it shows the column name (ADMIN, PRF_DE), not the page code (ADMIN_PAGE, EPRF_DE); the JS maps them back.
     */
    public String getModuleColumn() {
        if ("Y".equals(admin)) return "ADMIN";
        if ("Y".equals(prfDe)) return "PRF_DE";
        if ("Y".equals(oocInq)) return "OOC_INQ";
        if ("Y".equals(dnltcInq)) return "DNLTC_INQ";
        if ("Y".equals(snpId)) return "SNP_ID";
        if ("Y".equals(airsEpi)) return "AIRS_EPI";
        return " ";
    }

    public Long getId() { return id; }
    public String getAdmin() { return admin; }
    public String getPrfDe() { return prfDe; }
    public String getOocInq() { return oocInq; }
    public String getDnltcInq() { return dnltcInq; }
    public String getSnpId() { return snpId; }
    public String getAirsEpi() { return airsEpi; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public String getDisplayMsg() { return displayMsg; }
    public String getFlagDeleted() { return flagDeleted; }
    public String getCreateBy() { return createBy; }
    public LocalDateTime getCreateDate() { return createDate; }
    public String getUpdateBy() { return updateBy; }
    public LocalDateTime getUpdateDate() { return updateDate; }
}
