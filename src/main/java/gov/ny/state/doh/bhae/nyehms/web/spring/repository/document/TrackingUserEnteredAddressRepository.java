package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.TrackingUserEnteredAddress;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.TrackingAddressRow;

/** The addresses entered in the tracking system (table TRACKING_USER_ENTERED_ADDRESS). */
public interface TrackingUserEnteredAddressRepository extends JpaRepository<TrackingUserEnteredAddress, Integer> {

	/**
	 * A document's address with the name of the worker who entered it (the PL/SQL function PKG_TRK_COMMON.fn_get_fullname,
	 * passed through); any one row if several match (as the former rownum &lt; 2 without ORDER BY).
	 *
	 * @return the row, or an empty list
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.TrackingAddressRow(address,
					function('PKG_TRK_COMMON.fn_get_fullname' as String, users.lastName, users.firstName, users.middleName, null))
			from TrackingUserEnteredAddress address
			left join NyehmsUsers users on address.createid = users.legacyUid
			where address.documentId = ?1
			fetch first 1 rows only
			""")
	List<TrackingAddressRow> findFirstWithCreator(int documentId);
}
