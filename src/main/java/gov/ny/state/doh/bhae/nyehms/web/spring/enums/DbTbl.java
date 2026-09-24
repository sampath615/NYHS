package gov.ny.state.doh.bhae.nyehms.web.spring.enums;

public enum DbTbl {
	
	//Table Names.
	stateNoTbl("PERSON_DOC_ASSOCIATION"),
	stateNoTblField("STATENO"),
		
	firstNameTbl("FIRST_NAME"),
	firstNameTblField("FIRST_NAME"),
	firstNameTblFieldId("FIRST_NAME_ID"),
	firstNameBestTbl("FIRST_NAME_BEST"),
	
	middleNameTbl("MIDDLE_NAME"),
	middleNameTblField("MIDDLE_NAME"),
	middleNameTblFieldId("MIDDLE_NAME_ID"),
	middleNameBestTbl("MIDDLE_NAME_BEST"),
	
	lastNameTbl("LAST_NAME"),
	lastNameTblField("LAST_NAME"),
	lastNameTblFieldId("LAST_NAME_ID"),
	lastNameBestTbl("LAST_NAME_BEST"),
	
	ssnTbl("SSN"),
	ssnTblField("SSN"),
	ssnTblFieldId("SSN_ID"),
	ssnBestTbl("SSN_BEST"),
	
	dinTbl("DIN"),
	dinTblField("DIN"),
	dinTblFieldId("DIN_ID"),
	dinBestTbl("DIN_BEST"),	
	
	suffixTbl("SUFFIX"),
	suffixTblField("SUFFIX"),
	suffixTblFieldId("SUFFIX_ID"),
	suffixBestTbl("SUFFIX_BEST"),
	
	genderTbl("GENDER"),
	genderTblField("GENDER"),
	genderTblFieldId("GENDER_ID"),
	genderBestTbl("GENDER_BEST"),	
	
	birthSexTbl("BIRTH_SEX"),
	birthSexTblField("BIRTH_SEX"),
	birthSexTblFieldId("BIRTH_SEX_ID"),
	birthSexBestTbl("BIRTH_SEX_BEST"),	
	
	stateOfDeathTbl("STATE_OF_DEATH"),
	stateOfDeathTblField("STATE_CODE"),
	stateOfDeathTblFieldId("STATE_CODE_ID"),
	stateOfDeathBestTbl("STATE_OF_DEATH_BEST"),	
	
	dateOfDeathTbl("DATE_OF_DEATH"),
	dateOfDeathTblField("DATE_OF_DEATH"),
	dateOfDeathTblFieldId("DATE_OF_DEATH_ID"),
	dateOfDeathBestTbl("DATE_OF_DEATH_BEST"),	
	
	nysidTbl("NYSID"),
	nysidTblField("NYSID"),
	nysidTblFieldId("NYSID_ID"),
	nysidBestTbl("NYSID_BEST"),	
	
	medRecordNumberTbl("MED_RECORD_NUMER"),	
	medRecordNumberTblField("MED_RECORD_NUMER"),
	medRecordNumberTblFieldId("MED_RECORD_NUMER_ID"),
	medRecordNumberBestTbl("MED_RECORD_BEST"),
	
	insuranceTbl("INSURANCE"),
	insuranceTblField("INSURANCE_NUMER"),	
	
	dateOfBirthTbl("DATE_OF_BIRTH"),
	dateOfBirthTblField("DATE_OF_BIRTH"),
	dateOfBirthTblFieldId("DATE_OF_BIRTH_ID"),
	dateOfBirthBestTbl("DATE_OF_BIRTH_BEST"),	
	
	birthCountryTbl("BIRTH_COUNTRY"),
	birthCountryTblField("BIRTH_COUNTRY"),
	birthCountryTblFieldId("BIRTH_COUNTRY_ID"),
	birthCountryBestTbl("BIRTH_COUNTRY_BEST"),	
	
	ethnicityTbl("ETHNICITY"),
	ethnicityTblField("ETHNICITY_CODE"),
	ethnicityTblFieldId("ETHNICITY_ID"),
	ethnicityBestTbl("ETHNICITY_BEST"),	
	
	raceTbl("RACE"),
	raceTblField("RACE_CODE"),
	raceTblFieldId("RACE_CODE_ID"),
	raceBestTbl("RACE_BEST"),	
	
	personAddressTbl("PERSON_ADDRESS"),
	personAddressBestTbl("PERSON_ADDRESS_BEST"),	
	
	communicationTbl("COMMUNICATION"),
	communicationBestTbl("COMMUNICATION_BEST");		
	
	private String Name;
	
	DbTbl(String Name){
		this.Name = Name;
	}
	
	public String getName() {
	    return Name;
	    }
	
}
