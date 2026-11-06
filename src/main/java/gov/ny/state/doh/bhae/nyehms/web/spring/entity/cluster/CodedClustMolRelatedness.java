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
 * The molecular cluster relatedness values (table CODED_CLUST_MOL_RELATEDNESS), read-only. <b>RELATEDNESS as the key
 * is assumed.</b>
 */
@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CODED_CLUST_MOL_RELATEDNESS")
public class CodedClustMolRelatedness {

	@Id
	@Column(name = "RELATEDNESS")
	private String relatedness;
}
