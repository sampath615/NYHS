package gov.ny.state.doh.bhae.nyehms.web.spring.repository.administration;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration.CodedBureauName;

/** The bureaus (CODED_BUREAU_NAME). */
public interface CodedBureauNameRepository extends JpaRepository<CodedBureauName, String> {

	/** A bureau. */
	record Bureau(String bureauCd, String bureauDesc) {
	}

	/** The bureaus without an end date, in this order. */
	List<Bureau> findByEndDateIsNull(Sort sort);
}
