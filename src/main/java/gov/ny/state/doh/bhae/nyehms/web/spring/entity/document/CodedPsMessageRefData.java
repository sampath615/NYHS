package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.io.Serializable;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The code lists of the partner services message columns (table CODED_PS_MESSAGE_REF_DATA): per column, its codes and
 * names. Read-only, through a projection. <b>(REF_COLUMN, LOOKUP_CD) as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@IdClass(CodedPsMessageRefData.Key.class)
@Table(name = "CODED_PS_MESSAGE_REF_DATA")
public class CodedPsMessageRefData {

	@Id
	@Column(name = "REF_COLUMN")
	private String refColumn;

	@Id
	@Column(name = "LOOKUP_CD")
	private String lookupCd;

	@Column(name = "LOOKUP_NAME")
	private String lookupName;

	/** The assumed key. */
	@EqualsAndHashCode
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Key implements Serializable {

		private static final long serialVersionUID = 1L;

		private String refColumn;
		private String lookupCd;
	}
}
