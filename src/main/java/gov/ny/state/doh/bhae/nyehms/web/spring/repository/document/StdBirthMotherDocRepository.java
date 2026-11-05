package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdBirthMotherDoc;

/** The standardized mothers of birth records (table STD_BIRTH_MOTHER_DOC). */
public interface StdBirthMotherDocRepository extends JpaRepository<StdBirthMotherDoc, Long> {

	long countByBirthMatchPk(String birthMatchPk);

	/** Sets the matched address of all rows of a birth record (a bulk update: the rows are not unique per record). */
	@Modifying
	@Query("""
			update StdBirthMotherDoc d set d.personAddress = ?1, d.personCity = ?2, d.personState = ?3, d.personZip = ?4,
			d.personCounty = ?5, d.personLatitude = ?6, d.personLongitude = ?7, d.personCensusTract = ?8,
			d.matchedProcessDate = sysdate where d.birthMatchPk = ?9""")
	int updateMatchResults(String personAddress, String personCity, String personState, String personZip, String personCounty,
			String personLatitude, String personLongitude, String personCensusTract, String birthMatchPk);
}
