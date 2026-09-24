package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

public class PRFPartnerVO {	
	 	private Integer partnerId;
	    private Integer documentId;
	    private Long prfSrcCd;
	    private String fname;
	    private String mname;
	    private String lname;
	    private String aliasFname;
	    private String aliasMname;
	    private String aliasLname;
	    private String address;
	    private String city;
	    private String state;
	    private String zip;
	    private String plus4;
	    private String county;
	    private String homePhone;
	    private String cellPhone;
	    private String pager;
	    private String email;
	    private String dobText;
	    private Integer age;
	    private String gender;
	    private String ethnicity;
	    private boolean raceWhite;
	    private boolean raceBlack;
	    private boolean raceAsian;
	    private boolean raceIndian;
	    private boolean racePacific;
	    private boolean raceUnknown;
	    private boolean raceOther;
	    private String raceOtherText;
	    private String complexion;
	    private String hair;
	    private String height;
	    private String weight;
	    private String otherCharacteristics;
	    private String placeOfEmployment;
	    private String workHours;
	    private String workPhone;
	    private String workPhoneExt;
	    private String maritalStatus;
	    private String pregnant;
	    private String spouse;
	    private String dvScreen;
	    private String dvRisk;
	    private String partnerAssistanceStatus;
	    private String partnerNotifiedDate;
	    private String partnerStatus;
	    private String partnerTestDate;
	    private boolean identifiedByHdPs;
	    private String createId;
	    private String createDate;
	    private String entryReviewId;
	    private String entryReviewDate;
	    private String highPriority;
	    private String elicitedByPnap;
	    private String elicitedByAct;
	    private String processed;
	    private String stdAddress;
	    
	    //Marker field to identify the temp save to object but this is not a db field.
	    //This field is used by JSP page to discriminate this object as removable (delete) object by user before final save.
	    private String tempSave;
	    //Like above this field is flag to remove object from list. This is for delete functionality.
	    private String tempDelete;
	    
	    
	    
	    
	    
	    
	    private String telephone1;
	    private String telephone2;
	    private String telephoneNo1Type1;
	    private String telephoneNo2Type2;
	    private String websiteMobileApp1;
	    private String websiteMobileApp1Username;
	    private String websiteMobileApp2;
	    private String websiteMobileApp2Username;
	    private String sexAtBirth;
	    private String physicalDescription;
	    
	    private String additionalPartGenderIdentity;
	    
	    
	    private final String DEFAULT_DATE = "../../....";
		
		public Integer getPartnerId() {
			return partnerId;
		}



		public void setPartnerId(Integer partnerId) {
			this.partnerId = partnerId;
		}



		public Integer getDocumentId() {
			return documentId;
		}



		public void setDocumentId(Integer documentId) {
			this.documentId = documentId;
		}



		public String getFname() {
			return fname;
		}



		public void setFname(String fname) {
			this.fname = fname;
		}



		public String getMname() {
			return mname;
		}



		public void setMname(String mname) {
			this.mname = mname;
		}



		public String getLname() {
			return lname;
		}



		public void setLname(String lname) {
			this.lname = lname;
		}



		public String getAliasFname() {
			return aliasFname;
		}



		public void setAliasFname(String aliasFname) {
			this.aliasFname = aliasFname;
		}



		public String getAliasMname() {
			return aliasMname;
		}



		public void setAliasMname(String aliasMname) {
			this.aliasMname = aliasMname;
		}



		public String getAliasLname() {
			return aliasLname;
		}



		public void setAliasLname(String aliasLname) {
			this.aliasLname = aliasLname;
		}



		public String getAddress() {
			return address;
		}



		public void setAddress(String address) {
			this.address = address;
		}



		public String getCity() {
			return city;
		}



		public void setCity(String city) {
			this.city = city;
		}



		public String getState() {
			return state;
		}



		public void setState(String state) {
			this.state = state;
		}



		public String getZip() {
			return zip;
		}



		public void setZip(String zip) {
			this.zip = zip;
		}



		public String getPlus4() {
			return plus4;
		}



		public void setPlus4(String plus4) {
			this.plus4 = plus4;
		}



		public String getCounty() {
			return county;
		}



		public void setCounty(String county) {
			this.county = county;
		}



		public String getHomePhone() {
			return homePhone;
		}



		public void setHomePhone(String homePhone) {
			this.homePhone = homePhone;
		}



		public String getCellPhone() {
			return cellPhone;
		}



		public void setCellPhone(String cellPhone) {
			this.cellPhone = cellPhone;
		}



		public String getPager() {
			return pager;
		}



		public void setPager(String pager) {
			this.pager = pager;
		}



		public String getEmail() {
			return email;
		}



		public void setEmail(String email) {
			this.email = email;
		}



		public String getDobText() {
			return dobText;
		}



		public void setDobText(String dobText) {
			
			if(DEFAULT_DATE.equals(dobText)){
				this.dobText = null;
			}else{
				this.dobText = dobText;
			}
			
		}



		public Integer getAge() {
			return age;
		}



		public void setAge(Integer age) {
			this.age = age;
		}



		public String getGender() {
			return gender;
		}



		public void setGender(String gender) {
			this.gender = gender;
		}



		public String getEthnicity() {
			return ethnicity;
		}



		public void setEthnicity(String ethnicity) {
			this.ethnicity = ethnicity;
		}



		public boolean isRaceWhite() {
			return raceWhite;
		}



		public void setRaceWhite(boolean raceWhite) {
			this.raceWhite = raceWhite;
		}



		public boolean isRaceBlack() {
			return raceBlack;
		}



		public void setRaceBlack(boolean raceBlack) {
			this.raceBlack = raceBlack;
		}



		public boolean isRaceAsian() {
			return raceAsian;
		}



		public void setRaceAsian(boolean raceAsian) {
			this.raceAsian = raceAsian;
		}



		public boolean isRaceIndian() {
			return raceIndian;
		}



		public void setRaceIndian(boolean raceIndian) {
			this.raceIndian = raceIndian;
		}



		public boolean isRacePacific() {
			return racePacific;
		}



		public void setRacePacific(boolean racePacific) {
			this.racePacific = racePacific;
		}



		public boolean isRaceUnknown() {
			return raceUnknown;
		}



		public void setRaceUnknown(boolean raceUnknown) {
			this.raceUnknown = raceUnknown;
		}



		public boolean isRaceOther() {
			return raceOther;
		}



		public void setRaceOther(boolean raceOther) {
			this.raceOther = raceOther;
		}



		public String getRaceOtherText() {
			return raceOtherText;
		}



		public void setRaceOtherText(String raceOtherText) {
			this.raceOtherText = raceOtherText;
		}



		public String getComplexion() {
			return complexion;
		}



		public void setComplexion(String complexion) {
			this.complexion = complexion;
		}



		public String getHair() {
			return hair;
		}



		public void setHair(String hair) {
			this.hair = hair;
		}



		public String getHeight() {
			return height;
		}



		public void setHeight(String height) {
			this.height = height;
		}



		public String getWeight() {
			return weight;
		}



		public void setWeight(String weight) {
			this.weight = weight;
		}



		public String getOtherCharacteristics() {
			return otherCharacteristics;
		}



		public void setOtherCharacteristics(String otherCharacteristics) {
			this.otherCharacteristics = otherCharacteristics;
		}



		public String getPlaceOfEmployment() {
			return placeOfEmployment;
		}



		public void setPlaceOfEmployment(String placeOfEmployment) {
			this.placeOfEmployment = placeOfEmployment;
		}



		public String getWorkHours() {
			return workHours;
		}



		public void setWorkHours(String workHours) {
			this.workHours = workHours;
		}



		public String getWorkPhone() {
			return workPhone;
		}



		public void setWorkPhone(String workPhone) {
			this.workPhone = workPhone;
		}



		public String getWorkPhoneExt() {
			return workPhoneExt;
		}



		public void setWorkPhoneExt(String workPhoneExt) {
			this.workPhoneExt = workPhoneExt;
		}



		public String getMaritalStatus() {
			return maritalStatus;
		}



		public void setMaritalStatus(String maritalStatus) {
			this.maritalStatus = maritalStatus;
		}



		public String getPregnant() {
			return pregnant;
		}



		public void setPregnant(String pregnant) {
			this.pregnant = pregnant;
		}



		public String getSpouse() {
			return spouse;
		}



		public void setSpouse(String spouse) {
			this.spouse = spouse;
		}



		public String getDvScreen() {
			return dvScreen;
		}



		public void setDvScreen(String dvScreen) {
			this.dvScreen = dvScreen;
		}



		public String getDvRisk() {
			return dvRisk;
		}



		public void setDvRisk(String dvRisk) {
			this.dvRisk = dvRisk;
		}



		public String getPartnerAssistanceStatus() {
			return partnerAssistanceStatus;
		}



		public void setPartnerAssistanceStatus(String partnerAssistanceStatus) {
			this.partnerAssistanceStatus = partnerAssistanceStatus;
		}



		public String getPartnerNotifiedDate() {
			return partnerNotifiedDate;
		}



		public void setPartnerNotifiedDate(String partnerNotifiedDate) {
			
			if(DEFAULT_DATE.equals(partnerNotifiedDate)){
				this.partnerNotifiedDate = null;
			}else{
				this.partnerNotifiedDate = partnerNotifiedDate;
			}
			
		}



		public String getPartnerStatus() {
			return partnerStatus;
		}



		public void setPartnerStatus(String partnerStatus) {
			this.partnerStatus = partnerStatus;
		}



		public String getPartnerTestDate() {
			return partnerTestDate;
		}



		public void setPartnerTestDate(String partnerTestDate) {
			
			if(DEFAULT_DATE.equals(partnerTestDate)){
				this.partnerTestDate = null;
			}else{
				this.partnerTestDate = partnerTestDate;
			}
			
		}



		public boolean isIdentifiedByHdPs() {
			return identifiedByHdPs;
		}



		public void setIdentifiedByHdPs(boolean identifiedByHdPs) {
			this.identifiedByHdPs = identifiedByHdPs;
		}



		public String getCreateId() {
			return createId;
		}



		public void setCreateId(String createId) {
			this.createId = createId;
		}



		public String getCreateDate() {
			return createDate;
		}



		public void setCreateDate(String createDate) {
			this.createDate = createDate;
		}



		public String getEntryReviewId() {
			return entryReviewId;
		}



		public void setEntryReviewId(String entryReviewId) {
			this.entryReviewId = entryReviewId;
		}



		public String getEntryReviewDate() {
			return entryReviewDate;
		}



		public void setEntryReviewDate(String entryReviewDate) {
			this.entryReviewDate = entryReviewDate;
		}



		public String getHighPriority() {
			return highPriority;
		}



		public void setHighPriority(String highPriority) {
			this.highPriority = highPriority;
		}



		public String getElicitedByPnap() {
			return elicitedByPnap;
		}



		public void setElicitedByPnap(String elicitedByPnap) {
			this.elicitedByPnap = elicitedByPnap;
		}



		public String getElicitedByAct() {
			return elicitedByAct;
		}



		public void setElicitedByAct(String elicitedByAct) {
			this.elicitedByAct = elicitedByAct;
		}



		public String getProcessed() {
			return processed;
		}



		public void setProcessed(String processed) {
			this.processed = processed;
		}



		public String getStdAddress() {
			return stdAddress;
		}



		public void setStdAddress(String stdAddress) {
			this.stdAddress = stdAddress;
		}



		public String getTempSave() {
			return tempSave;
		}



		public void setTempSave(String tempSave) {
			this.tempSave = tempSave;
		}



		public String getTempDelete() {
			return tempDelete;
		}



		public void setTempDelete(String tempDelete) {
			this.tempDelete = tempDelete;
		}

		

		public Long getPrfSrcCd() {
			return prfSrcCd;
		}



		public void setPrfSrcCd(Long prfSrcCd) {
			this.prfSrcCd = prfSrcCd;
		}

		

		public String getTelephone1() {
			return telephone1;
		}



		public void setTelephone1(String telephone1) {
			this.telephone1 = telephone1;
		}



		public String getTelephone2() {
			return telephone2;
		}



		public void setTelephone2(String telephone2) {
			this.telephone2 = telephone2;
		}



		public String getTelephoneNo1Type1() {
			return telephoneNo1Type1;
		}



		public void setTelephoneNo1Type1(String telephoneNo1Type1) {
			this.telephoneNo1Type1 = telephoneNo1Type1;
		}



		public String getTelephoneNo2Type2() {
			return telephoneNo2Type2;
		}



		public void setTelephoneNo2Type2(String telephoneNo2Type2) {
			this.telephoneNo2Type2 = telephoneNo2Type2;
		}



		public String getWebsiteMobileApp1() {
			return websiteMobileApp1;
		}



		public void setWebsiteMobileApp1(String websiteMobileApp1) {
			this.websiteMobileApp1 = websiteMobileApp1;
		}



		public String getWebsiteMobileApp1Username() {
			return websiteMobileApp1Username;
		}



		public void setWebsiteMobileApp1Username(String websiteMobileApp1Username) {
			this.websiteMobileApp1Username = websiteMobileApp1Username;
		}



		public String getWebsiteMobileApp2() {
			return websiteMobileApp2;
		}



		public void setWebsiteMobileApp2(String websiteMobileApp2) {
			this.websiteMobileApp2 = websiteMobileApp2;
		}



		public String getWebsiteMobileApp2Username() {
			return websiteMobileApp2Username;
		}



		public void setWebsiteMobileApp2Username(String websiteMobileApp2Username) {
			this.websiteMobileApp2Username = websiteMobileApp2Username;
		}

		

		public String getSexAtBirth() {
			return sexAtBirth;
		}



		public void setSexAtBirth(String sexAtBirth) {
			this.sexAtBirth = sexAtBirth;
		}



		public String getPhysicalDescription() {
			return physicalDescription;
		}



		public void setPhysicalDescription(String physicalDescription) {
			this.physicalDescription = physicalDescription;
		}




		//Using this ToString to identify the Partner form is empty or not
		@Override
		public String toString() {
			return new StringBuilder("").append(partnerId).append(prfSrcCd).append(fname).append(mname).append(lname).append(aliasFname).append(aliasMname).append(aliasLname).append(address).append(city).append(state).append(zip).append(plus4).append(county).append(homePhone).append(cellPhone).append(pager).append(email).append(dobText).append(age).append(gender).append(ethnicity).append(raceWhite).append(raceBlack).append(raceAsian).append(raceIndian).append(racePacific).append(raceUnknown).append(raceOther).append(raceOtherText).append(complexion).append(hair).append(height).append(weight).append(otherCharacteristics).append(placeOfEmployment).append(workHours).append(workPhone).append(workPhoneExt).append(maritalStatus).append(pregnant).append(spouse).append(dvScreen).append(dvRisk).append(partnerAssistanceStatus).append(partnerNotifiedDate).append(partnerStatus).append(partnerTestDate).append(createId).append(createDate).append(entryReviewId).append(entryReviewDate).append(highPriority).append(elicitedByPnap).append(elicitedByAct).append(processed).append(stdAddress).append(telephone1).append(telephone2).append(telephoneNo1Type1).append(telephoneNo2Type2).append(websiteMobileApp1).append(websiteMobileApp1Username).append(websiteMobileApp2).append(websiteMobileApp2Username).append(sexAtBirth).append(physicalDescription).toString();
		}



		public String getAdditionalPartGenderIdentity() {
			return additionalPartGenderIdentity;
		}



		public void setAdditionalPartGenderIdentity(String additionalPartGenderIdentity) {
			this.additionalPartGenderIdentity = additionalPartGenderIdentity;
		}

		
}
