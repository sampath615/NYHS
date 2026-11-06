package gov.ny.state.doh.bhae.nyehms.web.spring.entity.review;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A provider follow-up call of an RIA document (table RIA_PROVIDER_FOLLOW_UP_CALL), added, changed and ended by the
 * RIA screen (RiaRecordsReviewDAO) with HQL inserts and JPQL bulk updates, read with projections.
 * <p>
 * The former insert did not set RIA_PROVIDER_FOLLOW_UP_ID, so the database fills it (a trigger or identity column):
 * <b>the key and its generation by the database are assumed</b>. CREATE_DATE is also mapped as text
 * ({@code createDateText}, read only): the screen's VO read it with getString, as the database driver renders a date.
 * The worker ids and RIA_DOC_ID are numbers.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "RIA_PROVIDER_FOLLOW_UP_CALL")
public class RiaProviderFollowUpCall {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RIA_PROVIDER_FOLLOW_UP_ID")
	private Long riaProviderFollowUpId;

	@Column(name = "RIA_DOC_ID")
	private Long riaDocId;

	@Column(name = "STATENO", insertable = false, updatable = false)
	private String stateno;

	@Column(name = "DATE_OF_CONTACT")
	private Timestamp dateOfContact;

	@Column(name = "CONTACT_REASON")
	private String contactReason;

	@Column(name = "PROVIDER")
	private String provider;

	@Column(name = "PROVIDER_ROLE")
	private String providerRole;

	@Column(name = "FACILITY")
	private String facility;

	@Column(name = "CONTACT_PERSON")
	private String contactPerson;

	@Column(name = "RIA_MATERIALS_PROVIDED")
	private String riaMaterialsProvided;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "CREATE_DATE", insertable = false, updatable = false)
	private String createDateText;

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "END_DATE")
	private Timestamp endDate;
}
