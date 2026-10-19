package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.CodedState;

/**
 * Spring Data JPA repository for the states (CODED_STATE; replaces CommonDAO.getStateValue).
 */
public interface CodedStateRepository extends JpaRepository<CodedState, String> {

	/**
	 * The code of a state by its name, ignoring case.
	 *
	 * @param stateName the name of the state (e.g. "New York")
	 * @return the state code (e.g. "NY"), or null if no state has that name
	 */
	@Query("""
			select codedState.stateCode
			from CodedState codedState
			where upper(codedState.stateDescription) = upper(:stateName)
			""")
	String findStateCodeByName(String stateName);
}
