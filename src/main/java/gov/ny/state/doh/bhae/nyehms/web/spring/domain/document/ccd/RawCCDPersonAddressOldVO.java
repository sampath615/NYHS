package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd;

public class RawCCDPersonAddressOldVO {
	
	private long PERSON_ADDRESS_ID;
	private long   DOCUMENT_ID;
	private String ADDRESS_USE;
	private String PERSON_ADDRESS_1;
	private String PERSON_ADDRESS_2;
	private String PERSON_CITY;
	private String PERSON_STATE;
	private String PERSON_ZIP;
	private String PERSON_COUNTRY;
	private String USEABLE_PERIOD_LOW;
	private String USEABLE_PERIOD_HIGH;

	public long getPERSON_ADDRESS_ID() {
		return PERSON_ADDRESS_ID;
	}
	public void setPERSON_ADDRESS_ID(long pERSON_ADDRESS_ID) {
		PERSON_ADDRESS_ID = pERSON_ADDRESS_ID;
	}
	public long getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(long dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}

	public String getADDRESS_USE() {
		return ADDRESS_USE;
	}
	public void setADDRESS_USE(String aDDRESS_USE) {
		ADDRESS_USE = aDDRESS_USE;
	}
	public String getPERSON_ADDRESS_1() {
		return PERSON_ADDRESS_1;
	}
	public void setPERSON_ADDRESS_1(String pERSON_ADDRESS_1) {
		PERSON_ADDRESS_1 = pERSON_ADDRESS_1;
	}
	public String getPERSON_ADDRESS_2() {
		return PERSON_ADDRESS_2;
	}
	public void setPERSON_ADDRESS_2(String pERSON_ADDRESS_2) {
		PERSON_ADDRESS_2 = pERSON_ADDRESS_2;
	}
	public String getPERSON_CITY() {
		return PERSON_CITY;
	}
	public void setPERSON_CITY(String pERSON_CITY) {
		PERSON_CITY = pERSON_CITY;
	}
	public String getPERSON_STATE() {
		return PERSON_STATE;
	}
	public void setPERSON_STATE(String pERSON_STATE) {
		PERSON_STATE = pERSON_STATE;
	}
	public String getPERSON_ZIP() {
		return PERSON_ZIP;
	}
	public void setPERSON_ZIP(String pERSON_ZIP) {
		PERSON_ZIP = pERSON_ZIP;
	}
	public String getPERSON_COUNTRY() {
		return PERSON_COUNTRY;
	}
	public void setPERSON_COUNTRY(String pERSON_COUNTRY) {
		PERSON_COUNTRY = pERSON_COUNTRY;
	}
	public String getUSEABLE_PERIOD_LOW() {
		return USEABLE_PERIOD_LOW;
	}
	public void setUSEABLE_PERIOD_LOW(String uSEABLE_PERIOD_LOW) {
		USEABLE_PERIOD_LOW = uSEABLE_PERIOD_LOW;
	}
	public String getUSEABLE_PERIOD_HIGH() {
		return USEABLE_PERIOD_HIGH;
	}
	public void setUSEABLE_PERIOD_HIGH(String uSEABLE_PERIOD_HIGH) {
		USEABLE_PERIOD_HIGH = uSEABLE_PERIOD_HIGH;
	}
	
	
	/**
	 * Sets street address lines assuming two max and in the right order
	 * @param addressLine
	 */
	public void addAddressLine(String addressLine) {
		if (PERSON_ADDRESS_1 == null) {
			setPERSON_ADDRESS_1(addressLine);
		} else {
			setPERSON_ADDRESS_2(addressLine);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder ab = new StringBuilder();
		ab.append("Address Use: " + ADDRESS_USE + "\n");
		ab.append(PERSON_ADDRESS_1).append("\n")
		.append(PERSON_ADDRESS_2).append("\n")
		.append(PERSON_CITY).append(", ").append(PERSON_STATE).append(" " + PERSON_COUNTRY + "\n");
		ab.append(PERSON_ZIP + "\n");
		ab.append("Useable from " + USEABLE_PERIOD_HIGH + " to " + USEABLE_PERIOD_LOW + "\n");
		return ab.toString();
	}
	


}
