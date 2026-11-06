package gov.ny.state.doh.bhae.nyehms.web.spring.repository.cluster;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.cluster.CodedClustMolTimeframe;

/** The molecular cluster time frames (CODED_CLUST_MOL_TIMEFRAME). */
public interface CodedClustMolTimeframeRepository extends JpaRepository<CodedClustMolTimeframe, String> {

	/** A time frame. */
	record Timeframe(String timeframeCode, String timeframeDesc) {
	}

	/** All time frames, by code. */
	List<Timeframe> findAllByOrderByTimeframeCode();
}
