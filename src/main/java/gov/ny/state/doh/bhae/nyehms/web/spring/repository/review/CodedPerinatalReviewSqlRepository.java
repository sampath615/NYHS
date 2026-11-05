package gov.ny.state.doh.bhae.nyehms.web.spring.repository.review;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.review.CodedPerinatalReviewSql;

/** The SQL of the perinatal (NBS) review screens (table CODED_PERINATAL_REVIEW_SQL). */
public interface CodedPerinatalReviewSqlRepository extends JpaRepository<CodedPerinatalReviewSql, String> {
}
