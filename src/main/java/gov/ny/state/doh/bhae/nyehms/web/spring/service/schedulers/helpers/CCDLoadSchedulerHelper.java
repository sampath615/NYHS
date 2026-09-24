package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.helpers;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.CCDDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDDocVO;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CCDLoadSchedulerHelper {

	@Autowired
	private CCDDocumentDAO ccdDocumentDAO;

	public CCDLoadSchedulerHelper() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Sets the private ccdDocumentDAO variable for use from outside Spring,
	 * where autowiring does not happen.
	 * 
	 * @param ccdddao
	 */
	public void setCCDDocumentDAO(CCDDocumentDAO ccdddao) {
		ccdDocumentDAO = ccdddao;
	}

	private static Logger log = LoggerFactory.getLogger(CCDLoadSchedulerHelper.class);

	/**
	 * Inserts a row into RAW_CCD_DOC and zero or more rows into each of the
	 * nine other RAW_CCD_XXX tables. TODO Implement error checking and/or
	 * exception handling
	 */
	
	public void writeRawCCDData(RawCCDDocVO theCCD, boolean individualLabResultInsert) {

}}
