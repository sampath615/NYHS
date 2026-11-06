package gov.ny.state.doh.bhae.nyehms.web.spring.repository.cluster;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.cluster.CodedClustMolRelatedness;

/** The molecular cluster relatedness values (CODED_CLUST_MOL_RELATEDNESS). */
public interface CodedClustMolRelatednessRepository extends JpaRepository<CodedClustMolRelatedness, String> {

	/** A relatedness value. */
	record Relatedness(String relatedness) {
	}

	/** All values, in table order (no sort, as before). */
	List<Relatedness> findAllBy();
}
