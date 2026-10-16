package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdClimsDoc;

/**
 * A CLIMS lab report as the CLIMS document screen shows it (CLIMSDocument.jsp, model attribute
 * {@code stdCLIMSDocVO}: {@code stdCLIMSDocVO.stdClimsDoc.<field>} etc.); replaces the former CLIMSDocVO.
 *
 * @param stdClimsDoc the standardized document (STD_CLIMS_DOC)
 * @param stateno STATENO of the current person match (PERSON_DOC_ASSOCIATION), if any
 * @param discardStatus DISCARD_STATUS of that match
 * @param facaddr1 facility address line 1 as received (RAW_CLIMS_DOC.FACADDR1)
 * @param facaddr2 facility address line 2 as received (RAW_CLIMS_DOC.FACADDR2)
 * @param tma TMA, or HIV1PANTHER when TMA is empty
 */
public record ClimsDocData(StdClimsDoc stdClimsDoc,
		String stateno,
		String discardStatus,
		String facaddr1,
		String facaddr2,
		String tma) {
}
