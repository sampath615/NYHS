package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import java.sql.Timestamp;

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
 * The contacts of a lab (table LAB_ALTERNATIVE_CONTACTS): name, phone, e-mail, contact type, report flags, notes and retirement.
 * <p>
 * The new contact of the contact screen is saved as an entity (its key is read back, as the former insert's generated key was); the columns that insert did not set are not insertable, so the database fills them as before. The other inserts are JPQL inserts of their own columns; changes are bulk JPQL updates. <b>CONTACT_ID as the key, filled by the database on insert, is assumed.</b>
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "LAB_ALTERNATIVE_CONTACTS")
public class LabAlternativeContacts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CONTACT_ID")
	private Long contactId;

	@Column(name = "PFI")
	private String pfi;

	@Column(name = "CONTACT_NAME")
	private String contactName;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "PHONE_EXT")
	private String phoneExt;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "FAX", insertable = false)
	private String fax;

	@Column(name = "CONTACT_TYPE_CODE")
	private String contactTypeCode;

	@Column(name = "SEND_REPORT_CARD")
	private String sendReportCard;

	@Column(name = "SEND_COUNT_REPORT")
	private String sendCountReport;

	@Column(name = "SEND_DONOR_REPORT", insertable = false)
	private String sendDonorReport;

	@Column(name = "SEND_STARHS_REPORT", insertable = false)
	private String sendStarhsReport;

	@Column(name = "QA")
	private String qa;

	@Column(name = "NOTES")
	private String notes;

	@Column(name = "MAIN_CONTACT")
	private String mainContact;

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "RETIRED_DATE", insertable = false)
	private Timestamp retiredDate;

	@Column(name = "LAST_UPDATED_BY", insertable = false)
	private Long lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DATE", insertable = false)
	private Timestamp lastUpdatedDate;
}
