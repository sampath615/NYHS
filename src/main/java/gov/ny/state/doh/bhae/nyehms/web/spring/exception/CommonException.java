package gov.ny.state.doh.bhae.nyehms.web.spring.exception;

public class CommonException extends RuntimeException{
	 
	private static final long serialVersionUID = 1L;
	
	private String exceptionMessage;
 	
	public CommonException(String exceptionMessageParam) {
		this.exceptionMessage = exceptionMessageParam;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessageParam) {
		this.exceptionMessage = exceptionMessageParam;
	}
 
}
