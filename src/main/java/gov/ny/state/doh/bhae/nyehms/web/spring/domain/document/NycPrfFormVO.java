package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.util.LinkedList;

public class NycPrfFormVO {
	
	private NycPRFDocVO nycPRFDocVO;
	private LinkedList<NycPRFPartnerVO> nycPRFPartnerVOList;
	public NycPRFDocVO getNycPRFDocVO() {
		return nycPRFDocVO;
	}
	public void setNycPRFDocVO(NycPRFDocVO nycPRFDocVO) {
		this.nycPRFDocVO = nycPRFDocVO;
	}
	public LinkedList<NycPRFPartnerVO> getNycPRFPartnerVOList() {
		return nycPRFPartnerVOList;
	}
	public void setNycPRFPartnerVOList(LinkedList<NycPRFPartnerVO> nycPRFPartnerVOList) {
		this.nycPRFPartnerVOList = nycPRFPartnerVOList;
	}
	
}
