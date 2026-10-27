package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import gov.ny.state.doh.bhae.nyehms.web.spring.record.StdConfDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.StdConfRefData;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.StdConfDocRepository;
import lombok.RequiredArgsConstructor;

/**
 * The confirmed STD (PS confirmed STD) reports: the document screen, the code lists, and the import writing the
 * standardized report.
 *
 * @author vxp06
 */
@Service
@RequiredArgsConstructor
public class STDConfDocumentService {

	private final StdConfDocRepository stdConfDocRepository;
	private final CommonDocumentService commonDocumentService;

	/** A confirmed STD report for the document screen, or null if there is none. */
	public StdConfDocData getSTDConfDoc(int docId) {
		return stdConfDocRepository.findDocument(docId);
	}

	/**
	 * Writes the standardized report (STD_STD_CONFIRMED_DOC) of a received report.
	 *
	 * @param docVO the received report (a RAW_STD_CONFIRMED_DOC row), value per column name; not changed
	 * @return 1
	 */
	public int writeRawSTDConfDocDataToStdTable(Map<Object, Object> docVO, BigDecimal documentId) {
		stdConfDocRepository.saveAndFlush(StdConfDocMapping.toStdDoc(docVO, commonDocumentService::validateState));
		return 1;
	}

	/** The code lists of the confirmed STD reports, by coded column and code. */
	public List<StdConfRefData> getSTDConfRefData() {
		return stdConfDocRepository.findRefData();
	}

	/** The codes of each coded column, in code order. */
	public Map<String, List<String>> getStdConfLookupData() {
		return stdConfDocRepository.findRefData().stream().collect(Collectors.groupingBy(
				StdConfRefData::refColumn, Collectors.mapping(StdConfRefData::lookupCd, Collectors.toList())));
	}
}
