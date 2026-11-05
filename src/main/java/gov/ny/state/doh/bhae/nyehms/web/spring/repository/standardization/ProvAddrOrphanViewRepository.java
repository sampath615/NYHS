package gov.ny.state.doh.bhae.nyehms.web.spring.repository.standardization;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.standardization.ProvAddrOrphanView;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.ProvAddrOrphanRow;

/** The provider addresses without a provider (view NYEHMS_PROV_ADDR_ORPHAN_VW). */
public interface ProvAddrOrphanViewRepository extends JpaRepository<ProvAddrOrphanView, Long> {

	/** Every row as it is, in the view's order (a projection: the view has one row per address and phone). */
	List<ProvAddrOrphanRow> findAllBy();
}
