package gov.ny.state.doh.bhae.nyehms.web.spring.repository.cluster;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.cluster.CodedTransmissionCategory;

/** The transmission categories (CODED_TRANSMISSION_CATEGORY). */
public interface CodedTransmissionCategoryRepository extends JpaRepository<CodedTransmissionCategory, String> {

	/** A transmission category. */
	record Category(String categoryCode, String categDescShort) {
	}

	/** All categories, by code. */
	List<Category> findAllByOrderByCategoryCode();
}
