package gov.ny.state.doh.bhae.nyehms.web.spring.repository.review;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.review.StdClimsDocReview;

/** The review columns of the standardized CLIMS lab reports (table STD_CLIMS_DOC). */
public interface StdClimsDocReviewRepository extends JpaRepository<StdClimsDocReview, Integer> {
}
