package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.CodedAhiAlertSrc;

/** The sources of an AHI alert (table CODED_AHI_ALERT_SRC). */
public interface CodedAhiAlertSrcRepository extends JpaRepository<CodedAhiAlertSrc, String> {

	/** A code with its description (a projection: a code that repeats gives several rows, as before). */
	record AlertSource(String ahiAlertCode, String ahiAlertDesc) {
	}

	/** All codes, in database order. */
	List<AlertSource> findAllBy();
}
