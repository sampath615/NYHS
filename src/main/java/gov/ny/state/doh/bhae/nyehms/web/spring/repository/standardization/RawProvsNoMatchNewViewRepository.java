package gov.ny.state.doh.bhae.nyehms.web.spring.repository.standardization;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.standardization.RawProvsNoMatchNewView;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.RawProvsNoMatchNewRow;

/** The raw provider addresses not yet standardized, new screen (view NYEHMS_RAW_PROVS_NO_MATCH_NEW_VW), read as projections. */
public interface RawProvsNoMatchNewViewRepository extends JpaRepository<RawProvsNoMatchNewView, Long> {

	/** The first 1000 rows, in the view's order (the former ROWNUM &lt;= 1000). */
	List<RawProvsNoMatchNewRow> findTop1000By();

	/** The rows of the given raw addresses linked to the given sub provider address. */
	List<RawProvsNoMatchNewRow> findBySubProviderAddressIdAndRawProviderAddressIdIn(Long subProviderAddressId, Collection<Long> rawProviderAddressIds);
}
