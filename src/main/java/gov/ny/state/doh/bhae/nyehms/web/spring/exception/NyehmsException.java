package gov.ny.state.doh.bhae.nyehms.web.spring.exception;

public class NyehmsException extends Exception{
	
	private static final long serialVersionUID = 2L;


	  	public NyehmsException() {}	    
	  	public NyehmsException(String msg) { 
	    	super(msg); 
	    }
	    public NyehmsException(Throwable cause) { 
	    	super(cause); 
	    }   
	    public NyehmsException(String msg, Throwable cause) { 
	    	super(msg, cause); 
	    } 
	    
	    public String toString()
        {
           return(" " + getMessage());
        }	    
	
}
