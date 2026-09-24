package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7;

public class RawHL7MedicationVO {

	public RawHL7MedicationVO() {
		// TODO Auto-generated constructor stub
	}
	
	private int uphnId;
	private int hl7DocId;
	private String orderControl;               
	private String placerOrderNumber;         
	private String fillerOrderNumber;        
	private String placerGroupNumber;         
	private String orderStatus;           
	private String dateTimeofTransaction;     
	private String enteringOrganization;       
	private String orderProviderFname;        
	private String orderProviderLname;        
	private String orderProviderMname;        
	private String orderProviderID;          
	private String orderEffectiveDatetime;
	private String orderingFacilityName;      
	private String orderingFacilityAddress1;  
	private String orderingFacilityAddress2;  
	private String orderingFacilityCity;    
	private String orderingFacilityState;     
	private String orderingFacilityZipcode;   
	private String orderingFacilityCounty;    
	private String orderingFacilityCountry;   
	private String orderingFacilityPhone;     
	private String orderingProviderAddress1;  
	private String orderingProviderAddress2;  
	private String orderingProviderCity;    
	private String orderingProviderState;     
	private String orderingProviderZipcode;  
	private String orderingProviderCounty;    
	private String orderingProviderCountry;
	private String orderType;     
	private String rxoMedName;                
	private String rxcMedName;                
	private String rxeMedName;                
	private String rxeOriginalOrderDate;     
	private String rxePharmRefill;        
	private String rxeDispPharm;              
	private String medicationName;
	
	
	public int getUphnId() {
		return uphnId;
	}
	public void setUphnId(int uphnId) {
		this.uphnId = uphnId;
	}
	public int getHl7DocId() {
		return hl7DocId;
	}
	public void setHl7DocId(int hl7DocId) {
		this.hl7DocId = hl7DocId;
	}
	public String getOrderControl() {
		return orderControl;
	}
	public void setOrderControl(String orderControl) {
		this.orderControl = orderControl;
	}
	public String getPlacerOrderNumber() {
		return placerOrderNumber;
	}
	public void setPlacerOrderNumber(String placerOrderNumber) {
		this.placerOrderNumber = placerOrderNumber;
	}
	public String getFillerOrderNumber() {
		return fillerOrderNumber;
	}
	public void setFillerOrderNumber(String fillerOrderNumber) {
		this.fillerOrderNumber = fillerOrderNumber;
	}
	public String getPlacerGroupNumber() {
		return placerGroupNumber;
	}
	public void setPlacerGroupNumber(String placerGroupNumber) {
		this.placerGroupNumber = placerGroupNumber;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getDateTimeofTransaction() {
		return dateTimeofTransaction;
	}
	public void setDateTimeofTransaction(String dateTimeofTransaction) {
		this.dateTimeofTransaction = dateTimeofTransaction;
	}
	public String getEnteringOrganization() {
		return enteringOrganization;
	}
	public void setEnteringOrganization(String enteringOrganization) {
		this.enteringOrganization = enteringOrganization;
	}
	public String getOrderProviderFname() {
		return orderProviderFname;
	}
	public void setOrderProviderFname(String orderProviderFname) {
		this.orderProviderFname = orderProviderFname;
	}
	public String getOrderProviderLname() {
		return orderProviderLname;
	}
	public void setOrderProviderLname(String orderProviderLname) {
		this.orderProviderLname = orderProviderLname;
	}
	public String getOrderProviderMname() {
		return orderProviderMname;
	}
	public void setOrderProviderMname(String orderProviderMname) {
		this.orderProviderMname = orderProviderMname;
	}
	public String getOrderProviderID() {
		return orderProviderID;
	}
	public void setOrderProviderID(String orderProviderID) {
		this.orderProviderID = orderProviderID;
	}
	public String getOrderEffectiveDatetime() {
		return orderEffectiveDatetime;
	}
	public void setOrderEffectiveDatetime(String orderEffectiveDatetime) {
		this.orderEffectiveDatetime = orderEffectiveDatetime;
	}
	public String getOrderingFacilityName() {
		return orderingFacilityName;
	}
	public void setOrderingFacilityName(String orderingFacilityName) {
		this.orderingFacilityName = orderingFacilityName;
	}
	public String getOrderingFacilityAddress1() {
		return orderingFacilityAddress1;
	}
	public void setOrderingFacilityAddress1(String orderingFacilityAddress1) {
		this.orderingFacilityAddress1 = orderingFacilityAddress1;
	}
	public String getOrderingFacilityAddress2() {
		return orderingFacilityAddress2;
	}
	public void setOrderingFacilityAddress2(String orderingFacilityAddress2) {
		this.orderingFacilityAddress2 = orderingFacilityAddress2;
	}
	public String getOrderingFacilityCity() {
		return orderingFacilityCity;
	}
	public void setOrderingFacilityCity(String orderingFacilityCity) {
		this.orderingFacilityCity = orderingFacilityCity;
	}
	public String getOrderingFacilityState() {
		return orderingFacilityState;
	}
	public void setOrderingFacilityState(String orderingFacilityState) {
		this.orderingFacilityState = orderingFacilityState;
	}
	public String getOrderingFacilityZipcode() {
		return orderingFacilityZipcode;
	}
	public void setOrderingFacilityZipcode(String orderingFacilityZipcode) {
		this.orderingFacilityZipcode = orderingFacilityZipcode;
	}
	public String getOrderingFacilityCounty() {
		return orderingFacilityCounty;
	}
	public void setOrderingFacilityCounty(String orderingFacilityCounty) {
		this.orderingFacilityCounty = orderingFacilityCounty;
	}
	public String getOrderingFacilityCountry() {
		return orderingFacilityCountry;
	}
	public void setOrderingFacilityCountry(String orderingFacilityCountry) {
		this.orderingFacilityCountry = orderingFacilityCountry;
	}
	public String getOrderingFacilityPhone() {
		return orderingFacilityPhone;
	}
	public void setOrderingFacilityPhone(String orderingFacilityPhone) {
		this.orderingFacilityPhone = orderingFacilityPhone;
	}
	public String getOrderingProviderAddress1() {
		return orderingProviderAddress1;
	}
	public void setOrderingProviderAddress1(String orderingProviderAddress1) {
		this.orderingProviderAddress1 = orderingProviderAddress1;
	}
	public String getOrderingProviderAddress2() {
		return orderingProviderAddress2;
	}
	public void setOrderingProviderAddress2(String orderingProviderAddress2) {
		this.orderingProviderAddress2 = orderingProviderAddress2;
	}
	public String getOrderingProviderCity() {
		return orderingProviderCity;
	}
	public void setOrderingProviderCity(String orderingProviderCity) {
		this.orderingProviderCity = orderingProviderCity;
	}
	public String getOrderingProviderState() {
		return orderingProviderState;
	}
	public void setOrderingProviderState(String orderingProviderState) {
		this.orderingProviderState = orderingProviderState;
	}
	public String getOrderingProviderZipcode() {
		return orderingProviderZipcode;
	}
	public void setOrderingProviderZipcode(String orderingProviderZipcode) {
		this.orderingProviderZipcode = orderingProviderZipcode;
	}
	public String getOrderingProviderCounty() {
		return orderingProviderCounty;
	}
	public void setOrderingProviderCounty(String orderingProviderCounty) {
		this.orderingProviderCounty = orderingProviderCounty;
	}
	public String getOrderingProviderCountry() {
		return orderingProviderCountry;
	}
	public void setOrderingProviderCountry(String orderingProviderCountry) {
		this.orderingProviderCountry = orderingProviderCountry;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getRxoMedName() {
		return rxoMedName;
	}
	public void setRxoMedName(String rxoMedName) {
		this.rxoMedName = rxoMedName;
	}
	public String getRxcMedName() {
		return rxcMedName;
	}
	public void setRxcMedName(String rxcMedName) {
		this.rxcMedName = rxcMedName;
	}
	public String getRxeMedName() {
		return rxeMedName;
	}
	public void setRxeMedName(String rxeMedName) {
		this.rxeMedName = rxeMedName;
	}
	public String getRxeOriginalOrderDate() {
		return rxeOriginalOrderDate;
	}
	public void setRxeOriginalOrderDate(String rxeOriginalOrderDate) {
		this.rxeOriginalOrderDate = rxeOriginalOrderDate;
	}
	public String getRxePharmRefill() {
		return rxePharmRefill;
	}
	public void setRxePharmRefill(String rxePharmRefill) {
		this.rxePharmRefill = rxePharmRefill;
	}
	public String getRxeDispPharm() {
		return rxeDispPharm;
	}
	public void setRxeDispPharm(String rxeDispPharm) {
		this.rxeDispPharm = rxeDispPharm;
	}
	public String getMedicationName() {
		return medicationName;
	}
	public void setMedicationName(String medicationName) {
		this.medicationName = medicationName;
	}
}

  
