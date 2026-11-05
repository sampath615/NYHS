package gov.ny.state.doh.bhae.nyehms.web.spring.entity.review;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The SQL of a perinatal (NBS) review screen (table CODED_PERINATAL_REVIEW_SQL): the query and its ORDER BY, which
 * NBSReviewService completes and runs through NBSReviewDAO.getPerinatalReviewRecords / getNBSYearListToReview.
 * <p>
 * Read-only. <b>SCREEN_CD as the key is assumed</b> (one row per screen). SCREEN_SQL may be a CLOB; it is read as text
 * (getString), as the former map result read it.
 */
@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CODED_PERINATAL_REVIEW_SQL")
public class CodedPerinatalReviewSql {

	@Id
	@Column(name = "SCREEN_CD")
	private String screenCd;

	@Column(name = "SCREEN_SQL")
	private String screenSql;

	@Column(name = "ORDERBY")
	private String orderby;
}
