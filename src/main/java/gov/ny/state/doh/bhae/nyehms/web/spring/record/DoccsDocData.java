package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import java.util.List;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.DinDoccsDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdDoccsDoc;

/**
 * A DOCCS (Department of Corrections) report as the DOCCS document screen shows it (DoccsDocument.jsp, model attribute
 * {@code stdDoccsDocVO}: {@code stdDoccsDocVO.stdDoccsDoc.<field>} etc.); replaces the former DoccsDocVO.
 *
 * @param stdDoccsDoc the standardized document (STD_DOCCS_DOC)
 * @param discardStatus DISCARD_STATUS of that match
 * @param stateno STATENO of the current person match (PERSON_DOC_ASSOCIATION), if any
 * @param dinDoccsDocs the DIN records of the report, added by DoccsDocumentService
 */
public record DoccsDocData(StdDoccsDoc stdDoccsDoc,
		String discardStatus,
		String stateno,
		List<DinDoccsDocVO> dinDoccsDocs) {

	/** The document as read (used by the JPQL constructor expression). */
	public DoccsDocData(StdDoccsDoc stdDoccsDoc, String discardStatus, String stateno) {
		this(stdDoccsDoc, discardStatus, stateno, null);
	}

	/** The same document with {@code dinDoccsDocs}. */
	public DoccsDocData withDinDoccsDocs(List<DinDoccsDocVO> dinDoccsDocs) {
		return new DoccsDocData(stdDoccsDoc, discardStatus, stateno, dinDoccsDocs);
	}
}
