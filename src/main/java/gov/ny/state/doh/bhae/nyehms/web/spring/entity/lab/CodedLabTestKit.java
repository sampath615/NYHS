package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

/**
 * The lab test kits (table CODED_LAB_TEST_KIT).
 * <p>
 * Read-only. <b>TEST_KIT_CD as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_LAB_TEST_KIT")
public class CodedLabTestKit {

	@Id
	@Column(name = "TEST_KIT_CD")
	private String testKitCd;

	@Column(name = "TEST_KIT_DESC")
	private String testKitDesc;

	@Column(name = "SORT_ORDER")
	private Long sortOrder;
}
