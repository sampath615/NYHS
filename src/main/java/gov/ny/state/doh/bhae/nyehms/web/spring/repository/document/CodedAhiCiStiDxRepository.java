package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.CodedAhiCiStiDx;

/** The concurrent STI diagnosis codes (table CODED_AHI_CI_STI_DX). */
public interface CodedAhiCiStiDxRepository extends JpaRepository<CodedAhiCiStiDx, String> {

	/** A code with its description (a projection: a code that repeats gives several rows, as before). */
	record DiagnosisCode(String diagnosisCode, String diagnosisDesc) {
	}

	/** All codes, in database order. */
	List<DiagnosisCode> findAllBy();
}
