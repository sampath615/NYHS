package gov.ny.state.doh.bhae.nyehms.web.spring.repository.administration;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration.CodedNyehmsUserType;

/** The user types (CODED_NYEHMS_USER_TYPE). */
public interface CodedNyehmsUserTypeRepository extends JpaRepository<CodedNyehmsUserType, String> {

	/** A user type. */
	record UserType(String userTypeCode, String userTypeDesc) {
	}

	/** All user types (in the database's order, as before). */
	List<UserType> findAllBy();
}
