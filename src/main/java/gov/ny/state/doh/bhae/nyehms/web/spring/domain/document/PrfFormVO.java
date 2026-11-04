package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.util.LinkedList;

public class PrfFormVO {	
	
	private PRFDocVO stdPRFDocVO;	
	private LinkedList<PRFPartnerVO> stdPRFPartnerVOList;
	public PRFDocVO getStdPRFDocVO() {
		return stdPRFDocVO;
	}
	public void setStdPRFDocVO(PRFDocVO stdPRFDocVO) {
		this.stdPRFDocVO = stdPRFDocVO;
	}
	public LinkedList<PRFPartnerVO> getStdPRFPartnerVOList() {
		return stdPRFPartnerVOList;
	}
	public void setStdPRFPartnerVOList(LinkedList<PRFPartnerVO> stdPRFPartnerVOList) {
		this.stdPRFPartnerVOList = stdPRFPartnerVOList;
	}
	
	
}
