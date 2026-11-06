package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.EharsPersonNyc;

/** The persons of the NYC eHARS (table EHARS_PERSON_NYC). */
public interface EharsPersonNycRepository extends JpaRepository<EharsPersonNyc, String> {

	/** The NYC persons with the city number that are the person a document is associated with (an open association). */
	@Query("""
			select count(n) from EharsPersonNyc n
			where exists (select 1 from PersonDocAssociation p where n.nyehmsStateno = p.stateno and p.documentId = ?1 and p.dissassociationDate is null)
			and n.cityno = ?2
			""")
	long countPersonsOfDocument(Integer documentId, String cityno);

	/** Whether a NYC person has the city number (the number must not be null). */
	boolean existsByCityno(String cityno);
}
