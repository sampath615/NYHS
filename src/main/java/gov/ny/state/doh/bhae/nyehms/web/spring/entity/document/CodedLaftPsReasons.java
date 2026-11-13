package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/** The LAFT review reasons of a partner services message (table CODED_LAFT_PS_REASONS), read-only. <b>REASON_ID as the key is assumed.</b> */
@Entity
@Immutable
@Getter
@Table(name = "CODED_LAFT_PS_REASONS")
public class CodedLaftPsReasons {

	@Id
	@Column(name = "REASON_ID")
	private Long reasonId;

	@Column(name = "REASON_DESC")
	private String reasonDesc;
}
