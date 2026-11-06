package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

/**
 * The submission (report file) types of the labs (table CODED_LAB_SUBMISSION_TYPE); the columns the JPQL reads (the list of all types is read with select *, which stays SQL).
 * <p>
 * Read-only. <b>SUBMISSION_TYPE_CD as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_LAB_SUBMISSION_TYPE")
public class CodedLabSubmissionType {

	@Id
	@Column(name = "SUBMISSION_TYPE_CD")
	private String submissionTypeCd;

	@Column(name = "DESCRIPTION")
	private String description;
}
