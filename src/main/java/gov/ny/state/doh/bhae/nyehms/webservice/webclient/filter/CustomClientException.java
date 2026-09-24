package gov.ny.state.doh.bhae.nyehms.webservice.webclient.filter;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientException;

public class CustomClientException extends WebClientException{

	  // private final HttpStatus status;
	  //  private final ErrorDetails details;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private static HttpStatus status;
   // private final ErrorDetails details; 
	
	/*public CustomClientException(String msg) {
		super(msg);
	
	}*/
	
	public CustomClientException( HttpStatus status) {
		  super(status.getReasonPhrase());
		this.status = status;
		
	}

	public CustomClientException() {
		  super(status.getReasonPhrase());
		// TODO Auto-generated constructor stub
	}

	public static HttpStatus getStatus() {
		return status;
	}
	
	

}
