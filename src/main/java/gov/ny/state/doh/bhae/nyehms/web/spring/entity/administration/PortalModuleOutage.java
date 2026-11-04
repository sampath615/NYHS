package gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnTransformer;

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
 * A provider portal outage message (table PORTAL_MODULE_OUTAGE): read, inserted and updated by SystemMessageDAO.
 * <p>
 * <b>Assumed:</b> MSG_ID is filled by the database on insert (identity column or trigger; the former insert did not
 * set it). FLAG_DELETED is not written on insert (its default applies, as before). CREATE_DATE and UPDATE_DATE are
 * the database clock (SYSDATE) when not given, as before.
 */
@Entity
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

	@ColumnTransformer(write = "coalesce(?, SYSDATE)")
	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "UPDATE_BY")
	private String updateBy;

	@ColumnTransformer(write = "coalesce(?, SYSDATE)")
	@Column(name = "UPDATE_DATE")
	private Timestamp updateDate;
}
