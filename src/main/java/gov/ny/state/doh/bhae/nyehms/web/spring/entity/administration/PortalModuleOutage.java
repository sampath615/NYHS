package gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration;

import java.sql.Timestamp;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A provider portal outage message (table PORTAL_MODULE_OUTAGE), read, inserted and changed by SystemMessageDAO.
 * <p>
 * <b>Assumed:</b> MSG_ID is filled by the database on insert (identity column or trigger; the former insert did not
 * set it). FLAG_DELETED is not written on insert, so its default applies. A change writes only the changed columns.
 */
@Entity
@DynamicUpdate
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "PORTAL_MODULE_OUTAGE")
public class PortalModuleOutage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MSG_ID")
	private Long msgId;

	@Column(name = "ADMIN")
	private String admin;

	@Column(name = "PRF_DE")
	private String prfDe;

	@Column(name = "OOC_INQ")
	private String oocInq;

	@Column(name = "DNLTC_INQ")
	private String dnltcInq;

	@Column(name = "SNP_ID")
	private String snpId;

	@Column(name = "AIRS_EPI")
	private String airsEpi;

	@Column(name = "START_DATE")
	private Timestamp startDate;

	@Column(name = "END_DATE")
	private Timestamp endDate;

	@Column(name = "DISPLAY_MSG")
	private String displayMsg;

	@Column(name = "FLAG_DELETED", insertable = false)
	private String flagDeleted;

	@Column(name = "CREATE_BY")
	private String createBy;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "UPDATE_BY")
	private String updateBy;

	@Column(name = "UPDATE_DATE")
	private Timestamp updateDate;

	/** Changes the message: its modules, dates and text. */
	public void change(PortalModuleOutage changes, String updateBy, Timestamp updateDate) {
		this.admin = changes.admin;
		this.prfDe = changes.prfDe;
		this.oocInq = changes.oocInq;
		this.dnltcInq = changes.dnltcInq;
		this.snpId = changes.snpId;
		this.airsEpi = changes.airsEpi;
		this.startDate = changes.startDate;
		this.endDate = changes.endDate;
		this.displayMsg = changes.displayMsg;
		this.updateBy = updateBy;
		this.updateDate = updateDate;
	}

	/** Sets the deleted flag (the screen's delete). */
	public void flagDeleted(String flagDeleted, String updateBy, Timestamp updateDate) {
		this.flagDeleted = flagDeleted;
		this.updateBy = updateBy;
		this.updateDate = updateDate;
	}
}
