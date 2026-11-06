package gov.ny.state.doh.bhae.nyehms.web.spring.entity.cluster;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The cluster identification methods (table CODED_CLUSTER_IDENTIFICATION_METHOD), read-only.
 * <b>IDENTIFICATION_METHOD_CODE as the key is assumed</b>, and the code as text.
 */
@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CODED_CLUSTER_IDENTIFICATION_METHOD")
public class CodedClusterIdentificationMethod {

	@Id
	@Column(name = "IDENTIFICATION_METHOD_CODE")
	private String identificationMethodCode;

	@Column(name = "IDENTIFICATION_METHOD_DESC")
	private String identificationMethodDesc;
}
