package gov.ny.state.doh.bhae.nyehms.web.spring.repository.cluster;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.cluster.CodedClusterIdentificationMethod;

/** The cluster identification methods (CODED_CLUSTER_IDENTIFICATION_METHOD). */
public interface CodedClusterIdentificationMethodRepository extends JpaRepository<CodedClusterIdentificationMethod, String> {

	/** An identification method. */
	record Method(String identificationMethodCode, String identificationMethodDesc) {
	}

	/** All methods, in table order (no sort, as before). */
	List<Method> findAllBy();
}
