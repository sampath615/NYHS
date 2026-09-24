package gov.ny.state.doh.bhae.nyehms.web.spring.enums;

public enum AppConstants {

	//These fields are from the search engine page of the application.
	searchPersonViewSearchFields("STATENO,FIRST_NAME,LAST_NAME,DATE_OF_BIRTH,SSN,DIN,NYSID,DATE_OF_DEATH"), 
	stateNoTblField("STATENO"),
	nyehmLogDebugString("****---NYEHMS LOG--****"),
	nyehmLogErrorString("****@@@--NYEHMS ERROR--@@@****"),
	//we show a friendly message to the user when an error occurs
	NYEHM_USER_FRIENDLY_ERROR_MESSAGE("Transaction Failed. Please try again"),
	//used for custom transaction message to be shown to the user from each business method
	NYEHM_TRANSACTION_MESSAGE("TRANSACTION_MESSAGE"),
    //The transaction result key to be send to the web client to access the transaction result for response processing and conditional rendering 
	NYEHM_TRANSACTION_RESULT("TRANSACTION_RESULT"),
	//the transaction success result value for the NYEHM_TRANSACTION_RESULT key to be send to the web client during a successful transaction
	NYEHM_TRANSACTION_SUCCESS_CODE("TRANSACTION_SUCCESS"),
	//the transaction failure result value value for the NYEHM_TRANSACTION_RESULT key to be send to the web client during an unsuccessful transaction
	NYEHM_TRANSACTION_FAILURE_CODE("TRANSACTION_FAILURE"),
	//the error code key to pass the DB error code to the  
	NYEHM_DB_TRANSACTION_ERROR_CODE("TRANSACTION_CODE"),
	//the error code key to pass the DB error message  
	NYEHM_DB_TRANSACTION_ERROR_MESSAGE("TRANSACTION_ERROR");
	
	private String Name;

	AppConstants(String Name) {
		this.Name = Name;
	}

	public String getName() {
		return Name;
	}

}
