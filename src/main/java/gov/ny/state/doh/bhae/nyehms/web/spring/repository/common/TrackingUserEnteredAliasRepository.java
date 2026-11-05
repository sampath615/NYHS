package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.TrackingUserEnteredAlias;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.TrackingAliasRow;

/** The aliases entered in the tracking system (table TRACKING_USER_ENTERED_ALIAS). */
public interface TrackingUserEnteredAliasRepository extends JpaRepository<TrackingUserEnteredAlias, Integer> {

	/**
	 * A document's alias with the name of the worker who entered it (the PL/SQL function PKG_TRK_COMMON.fn_get_fullname,
	 * passed through); any one row if several match (as the former rownum &lt; 2 without ORDER BY).
	 *
	 * @return the row, or an empty list
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.TrackingAliasRow(alias,
					function('PKG_TRK_COMMON.fn_get_fullname' as String, users.lastName, users.firstName, users.middleName, null))
			from TrackingUserEnteredAlias alias
			left join NyehmsUsers users on alias.createid = users.legacyUid
			where alias.documentId = ?1
			fetch first 1 rows only
			""")
	List<TrackingAliasRow> findFirstWithCreator(int documentId);
}
