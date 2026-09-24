package gov.ny.state.doh.bhae.nyehms.web.spring.domain.person;


public class PSMessageVO {

	private int DOCUMENT_ID;
	private MessageReturnVO messageReturnVO;
	private MessageSentVO messageSentVO;
	
	
	public int getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(int dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public MessageReturnVO getMessageReturnVO() {
		return messageReturnVO;
	}
	public void setMessageReturnVO(MessageReturnVO messageReturnVO) {
		this.messageReturnVO = messageReturnVO;
	}
	public MessageSentVO getMessageSentVO() {
		return messageSentVO;
	}
	public void setMessageSentVO(MessageSentVO messageSentVO) {
		this.messageSentVO = messageSentVO;
	}
	

	
}
