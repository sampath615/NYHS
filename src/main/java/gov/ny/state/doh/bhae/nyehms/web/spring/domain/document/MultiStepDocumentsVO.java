package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.util.List;



public class MultiStepDocumentsVO {

	private String RANKING;
	
	private List<EcrlsLabDocVO> multiStepDocuments;
	
	
	
	public String getRANKING() {
		return RANKING;
	}
	public void setRANKING(String rANKING) {
		RANKING = rANKING;
	}
	public List<EcrlsLabDocVO> getMultiStepDocuments() {
		return multiStepDocuments;
	}
	public void setMultiStepDocuments(List<EcrlsLabDocVO> multiStepDocuments) {
		this.multiStepDocuments = multiStepDocuments;
	}
	
	
	
	
}
