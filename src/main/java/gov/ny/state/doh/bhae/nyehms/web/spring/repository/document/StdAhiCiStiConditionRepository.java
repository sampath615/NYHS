package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdAhiCiStiCondition;

/** The concurrent STI conditions of the AHI forms (table STD_AHI_CI_STI_CONDITION). */
public interface StdAhiCiStiConditionRepository extends JpaRepository<StdAhiCiStiCondition, Long> {

	/** A condition (a projection: the table has no key). The date is the text MM/DD/YYYY. */
	record CiStiCondition(Long ahiDocumentId, String ciStiConditionCode, String ciStiConditionDxDate) {
	}

	/** The conditions of a form, in database order. */
	List<CiStiCondition> findByAhiDocumentId(Long ahiDocumentId);
}
