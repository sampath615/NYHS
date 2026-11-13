package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/** The HIV categories (table CODED_HIV_CATEGORY), read-only. <b>HIV_CATEG as the key is assumed.</b> */
@Entity
@Immutable
@Getter
@Table(name = "CODED_HIV_CATEGORY")
public class CodedHivCategory {

	@Id
	@Column(name = "HIV_CATEG")
	private String hivCateg;

	@Column(name = "HIV_CATEG_DESC")
	private String hivCategDesc;
}
