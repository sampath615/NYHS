package gov.ny.state.doh.bhae.nyehms.web.spring.repository.administration;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration.CodedNyehmsUserFacilityVw;

/** The users' facility groups (CODED_NYEHMS_USER_FACILITY_VW). */
public interface CodedNyehmsUserFacilityVwRepository extends JpaRepository<CodedNyehmsUserFacilityVw, String> {

	/** A facility group. */
	record FacilityGroup(String facilityGroupCd, String facilityDispName) {
	}

	/** All facility groups (in the database's order, as before). */
	List<FacilityGroup> findAllBy();
}
