package gov.ny.state.doh.bhae.nyehms.web.spring.repository.cluster;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.cluster.CodedEharsEthnicity;

/** The eHARS ethnicity codes (CODED_EHARS_ETHNICITY). */
public interface CodedEharsEthnicityRepository extends JpaRepository<CodedEharsEthnicity, String> {

	/** An ethnicity code. */
	record Ethnicity(String eharsEthCode, String eharsEthDesc) {
	}

	/** All ethnicity codes, by code. */
	List<Ethnicity> findAllByOrderByEharsEthCode();
}
