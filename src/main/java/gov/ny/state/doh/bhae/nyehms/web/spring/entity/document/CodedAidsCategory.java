package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/** The AIDS categories (table CODED_AIDS_CATEGORY), read-only. <b>AIDS_CATEG as the key is assumed.</b> */
@Entity
@Immutable
@Getter
@Table(name = "CODED_AIDS_CATEGORY")
public class CodedAidsCategory {

	@Id
	@Column(name = "AIDS_CATEG")
	private String aidsCateg;

	@Column(name = "AIDS_CATEG_DESC")
	private String aidsCategDesc;
}
