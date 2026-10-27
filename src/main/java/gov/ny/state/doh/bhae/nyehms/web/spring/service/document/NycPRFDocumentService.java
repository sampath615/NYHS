package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycprfPartner;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NycPrfDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.NycprfDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.NycprfPartnerRepository;
import lombok.RequiredArgsConstructor;

/**
 * The NYC PRF forms (STD_NYCPRF_DOC) and their partners (STD_NYCPRF_PARTNER): the NYC PRF document screen and the
 * import that standardizes a received form with its partners.
 */
@Service
@RequiredArgsConstructor
public class NycPRFDocumentService {

	private final NycprfDocRepository nycprfDocRepository;
	private final NycprfPartnerRepository nycprfPartnerRepository;

	/** A NYC PRF form with its partners for the document screen. */
	public NycPrfDocData getNycPRFDoc(int docId) {
		return nycprfDocRepository.findDocument(docId)
				.withPartnerList(nycprfPartnerRepository.findPartnersOfDocument(docId));
	}

	/**
	 * Standardizes a received NYC PRF form and its partners in one transaction (the import's entry point).
	 *
	 * @param docVO the received form (RAW_NYCPRF_DOC), value per field name; not changed
	 * @return the number of standardized forms written
	 */
	@Transactional
	public int writeNYCPRFDocAndPartnersData(Map<Object, Object> docVO, BigDecimal documentId) {
		nycprfDocRepository.saveAndFlush(NycPrfStdDocMapping.toStdDoc(docVO));   // flush: insert now, before the partners
		List<StdNycprfPartner> partners = nycprfPartnerRepository.findReceivedPartnersOfDocument(documentId.longValue()).stream()
				.map(NycPrfStdPartnerMapping::toStdPartner)
				.toList();
		nycprfPartnerRepository.saveAllAndFlush(partners);
		return 1;
	}
}
