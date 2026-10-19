package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.record.StdConfDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.StdConfRefData;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.StdConfDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

/**
 * The confirmed STD (PS confirmed STD) reports: the document screen, the code lists, and the import writing the
 * standardized report.
 *
 * @author vxp06
 */
@Service
public class STDConfDocumentService {

	@Autowired
	StdConfDocRepository stdConfDocRepository;
	@Autowired
	CommonDocumentService commonDocumentService;

	private Map<String, String> drugListMap = new HashMap<>();

	public StdConfDocData getSTDConfDoc(int docId,
			BindingResult dummyBindingResult, UserObject userObject, String id){

		return stdConfDocRepository.findDocument(docId);
	}

	/**
	 * Writes the standardized report (STD_STD_CONFIRMED_DOC) of a received report.
	 *
	 * @param docVO the received report (a RAW_STD_CONFIRMED_DOC row), value per column name; not changed
	 * @return 1
	 */
	public int writeRawSTDConfDocDataToStdTable(Map<Object, Object> docVO, BigDecimal documentId) {
		stdConfDocRepository.saveAndFlush(StdConfDocMapping.toStdDoc(docVO,
				state -> commonDocumentService.validateState(state)));
		return 1;
	}

	public void loadSTDConfReferenceVariables() {
		for (StdConfRefData drug : stdConfDocRepository.findRefDataOfColumn("DRUG_USED_LIST")) {
			drugListMap.put(drug.lookupCd(), drug.lookupName());
		}
	}

	public void resetDocumentReferenceVariables(){
		if(drugListMap != null && drugListMap.size() > 0){
			drugListMap.clear();
		}
	}

	/** The code lists of the confirmed STD reports, by coded column and code. */
	public List<StdConfRefData> getSTDConfRefData(){
		return stdConfDocRepository.findRefData();
	}

	/** The codes of each coded column, in code order. */
	public Map<String, List<String>> getStdConfLookupData(){
		return stdConfDocRepository.findRefData().stream().collect(Collectors.groupingBy(
				StdConfRefData::refColumn, Collectors.mapping(StdConfRefData::lookupCd, Collectors.toList())));
	}
}
