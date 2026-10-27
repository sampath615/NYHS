package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.CodedInstitute;

/**
 * Spring Data JPA repository for the institutions of the death records (CODED_INSTITUTE; replaces
 * DeathDocumentDAO.getFacilityName).
 */
public interface CodedInstituteRepository extends JpaRepository<CodedInstitute, String> {

	/**
	 * The name of the institution whose code range contains an institution code.
	 *
	 * @return the name, or null if no range contains the code
	 */
	@Query("""
			select institute.insDescription
			from CodedInstitute institute
			where :inst between institute.insCodeStart and institute.insCodeEnd
			""")
	String findDescriptionOfInstitute(String inst);
}
