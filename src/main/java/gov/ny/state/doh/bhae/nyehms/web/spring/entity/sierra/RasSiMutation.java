package gov.ny.state.doh.bhae.nyehms.web.spring.entity.sierra;

import org.hibernate.annotations.Immutable;
import org.springframework.data.domain.Persistable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A mutation of a gene of a Sierra resistance response (table RAS_SI_MUTATION), as the Sierra job writes it
 * (SierraResponseDAO).
 * <p>
 * Inserted once with MUTATION_SEQ from the sequence MUTATION_SEQ (as the former insert took MUTATION_SEQ.NEXTVAL),
 * never changed. The flags are written as the texts the job passes. <b>MUTATION_SEQ as the key is assumed</b>.
 */
@Entity
@Immutable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "RAS_SI_MUTATION")
public class RasSiMutation implements Persistable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MUTATION_SEQ")
	@SequenceGenerator(name = "MUTATION_SEQ", sequenceName = "MUTATION_SEQ", allocationSize = 1)
	@Column(name = "MUTATION_SEQ")
	private Long mutationSeq;

	@Column(name = "SEQ_RESULT_ID")
	private Integer seqResultId;

	@Column(name = "GENE_SEQ_ID")
	private Integer geneSeqId;

	@Column(name = "MUTATION_TYPE")
	private String mutationType;

	@Column(name = "MUTATION_NAME")
	private String mutationName;

	@Column(name = "WILD_TYPE")
	private String wildType;

	@Column(name = "POSITION")
	private String position;

	@Column(name = "NUCLE_ACID")
	private String nucleAcid;

	@Column(name = "TRANSLATEDAA")
	private String translatedaa;

	@Column(name = "INSERTEDNAS")
	private String insertednas;

	@Column(name = "ISINSERTION")
	private String isinsertion;

	@Column(name = "ISDELETION")
	private String isdeletion;

	@Column(name = "ISINDEL")
	private String isindel;

	@Column(name = "ISAMBIGUOUS")
	private String isambiguous;

	@Column(name = "ISAPOBECMUTATION")
	private String isapobecmutation;

	@Column(name = "ISAPOBECDRM")
	private String isapobecdrm;

	@Column(name = "HASSTOP")
	private String hasstop;

	@Column(name = "ISUNUSUAL")
	private String isunusual;

	@Override
	public Long getId() {
		return mutationSeq;
	}

	/** Rows are only ever inserted. */
	@Override
	public boolean isNew() {
		return true;
	}
}
