package gov.ny.state.doh.bhae.nyehms.web.spring.entity.review;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A row of a CDC record search file as it was sent (table NYEHMS_REC_SEARCH_SENT): its sequence in the file and the
 * person's soundex, date of birth and birth sex (RecordSearchReviewDAO).
 * <p>
 * Inserted by an HQL insert, changed by a JPQL bulk update and read with projections only, never loaded as an entity,
 * so the key is only for the mapping: <b>(SENT_FILE_NAME, SENT_FILE_SEQ) as the key is assumed</b>. The worker id
 * columns are numbers.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "NYEHMS_REC_SEARCH_SENT")
@IdClass(NyehmsRecSearchSentId.class)
public class NyehmsRecSearchSent {

	@Id
	@Column(name = "SENT_FILE_NAME")
	private String sentFileName;

	@Id
	@Column(name = "SENT_FILE_SEQ")
	private Long sentFileSeq;

	@Column(name = "LNAME_SOUNDEX")
	private String lnameSoundex;

	@Column(name = "DOB_TEXT")
	private String dobText;

	@Column(name = "BIRTH_SEX")
	private String birthSex;

	@Column(name = "STATENO")
	private String stateno;

	@Column(name = "CITYNO")
	private String cityno;

	@Column(name = "EHARS_STATENO")
	private String eharsStateno;

	@Column(name = "CREATE_UID")
	private Long createUid;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "UPLOAD_DATE")
	private Timestamp uploadDate;

	@Column(name = "END_DATE")
	private Timestamp endDate;

	@Column(name = "END_DATED_UID")
	private Long endDatedUid;
}
