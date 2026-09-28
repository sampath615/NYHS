package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RawHL7ObrVO {

	public RawHL7ObrVO() {
		// TODO Auto-generated constructor stub

	}

	private int hl7DocId;	
	private int hl7labTestId;	
	private long uphnTranxInId;	
	private int obrSetId;	
	private String obrAccessionNum;
	private String obrcallBackPhone;
	private Date obrCollectionDate;
	private Date obrObservationDate;
	private String obrProviderFirstName;
	private String obrProviderLastName;
	private String obrProviderMiddleName;
	private String obrProviderNameSuffix;
	private String obrProviderFacilityId;
	private String obrProviderLicenseId;
	private String obrProviderNpi;
	private String obrProviderIdList;
	private Date obrResultReportDate;
	private String obrSpecimenSourceCode;
	private String obrSpecimenSourcename;
	private String obrLoincDesc;
	private String obrLoincCode;
	private String obrTestCode;
	private String obrTestDesc;
	private String obrTestCodingSystem;
	private String obrAltTestCode;
	private String obrAltTestDesc;
	private String obrAltTestCodingSystem;
	private String obrNote;
	private List<RawHL7ObxVO> theOBXList = new ArrayList<>();
	private String obrProviderName;
	
	private String obrCollectionDateStr;
	private String obrObservationDateStr;
		
	public int getHl7DocId() {
		return hl7DocId;
	}
	public void setHl7DocId(int hl7DocId) {
		this.hl7DocId = hl7DocId;
	}
		
	public int getHl7labTestId() {
		return hl7labTestId;
	}
	public void setHl7labTestId(int hl7labTestId) {
		this.hl7labTestId = hl7labTestId;
	}
	
	public long getUphnTranxInId() {
		return uphnTranxInId;
	}
	
	public void setUphnTranxInId(long uphnTranxInId) {
		this.uphnTranxInId = uphnTranxInId;
	}
	public int getObrSetId() {
		return obrSetId;
	}
	public void setObrSetId(int obrSetId) {
		this.obrSetId = obrSetId;
	}
	public String getObrAccessionNum() {
		return obrAccessionNum;
	}
	public void setObrAccessionNum(String obrAccessionNum) {
		this.obrAccessionNum = obrAccessionNum;
	}
	public String getObrcallBackPhone() {
		return obrcallBackPhone;
	}
	public void setObrcallBackPhone(String obrcallBackPhone) {
		this.obrcallBackPhone = obrcallBackPhone;
	}
	public Date getObrCollectionDate() {
		return obrCollectionDate;
	}
	public void setObrCollectionDate(Date obrCollectionDate) {
		this.obrCollectionDate = obrCollectionDate;
	}
	public Date getObrObservationDate() {
		return obrObservationDate;
	}
	public void setObrObservationDate(Date obrObservationDate) {
		this.obrObservationDate = obrObservationDate;
	}
	public String getObrProviderFirstName() {
		return obrProviderFirstName;
	}
	public void setObrProviderFirstName(String obrProviderFirstName) {
		this.obrProviderFirstName = obrProviderFirstName;
	}
	public String getObrProviderLastName() {
		return obrProviderLastName;
	}
	public void setObrProviderLastName(String obrProviderLastName) {
		this.obrProviderLastName = obrProviderLastName;
	}
	public String getObrProviderMiddleName() {
		return obrProviderMiddleName;
	}
	public void setObrProviderMiddleName(String obrProviderMiddleName) {
		this.obrProviderMiddleName = obrProviderMiddleName;
	}
	public String getObrProviderNameSuffix() {
		return obrProviderNameSuffix;
	}
	public void setObrProviderNameSuffix(String obrProviderNameSuffix) {
		this.obrProviderNameSuffix = obrProviderNameSuffix;
	}
	public String getObrProviderFacilityId() {
		return obrProviderFacilityId;
	}
	public void setObrProviderFacilityId(String obrProviderFacilityId) {
		this.obrProviderFacilityId = obrProviderFacilityId;
	}
	public String getObrProviderLicenseId() {
		return obrProviderLicenseId;
	}
	public void setObrProviderLicenseId(String obrProviderLicenseId) {
		this.obrProviderLicenseId = obrProviderLicenseId;
	}
	public String getObrProviderNpi() {
		return obrProviderNpi;
	}
	public void setObrProviderNpi(String obrProviderNpi) {
		this.obrProviderNpi = obrProviderNpi;
	}
	public String getObrProviderIdList() {
		return obrProviderIdList;
	}
	public void setObrProviderIdList(String obrProviderIdList) {
		this.obrProviderIdList = obrProviderIdList;
	}
	public Date getObrResultReportDate() {
		return obrResultReportDate;
	}
	public void setObrResultReportDate(Date obrResultReportDate) {
		this.obrResultReportDate = obrResultReportDate;
	}
	public String getObrSpecimenSourceCode() {
		return obrSpecimenSourceCode;
	}
	public void setObrSpecimenSourceCode(String obrSpecimenSourceCode) {
		this.obrSpecimenSourceCode = obrSpecimenSourceCode;
	}
	public String getObrSpecimenSourcename() {
		return obrSpecimenSourcename;
	}
	public void setObrSpecimenSourcename(String obrSpecimenSourcename) {
		this.obrSpecimenSourcename = obrSpecimenSourcename;
	}
	public List<RawHL7ObxVO> getTheOBXList() {
		return theOBXList;
	}
	public String getObrLoincDesc() {
		return obrLoincDesc;
	}
	public void setObrLoincDesc(String obrLoincDesc) {
		this.obrLoincDesc = obrLoincDesc;
	}
	public String getObrLoincCode() {
		return obrLoincCode;
	}
	public void setObrLoincCode(String obrLoincCode) {
		this.obrLoincCode = obrLoincCode;
	}
	public String getObrTestCode() {
		return obrTestCode;
	}
	public void setObrTestCode(String obrTestCode) {
		this.obrTestCode = obrTestCode;
	}
	public String getObrTestDesc() {
		return obrTestDesc;
	}
	public void setObrTestDesc(String obrTestDesc) {
		this.obrTestDesc = obrTestDesc;
	}
	public String getObrTestCodingSystem() {
		return obrTestCodingSystem;
	}
	public void setObrTestCodingSystem(String obrTestCodingSystem) {
		this.obrTestCodingSystem = obrTestCodingSystem;
	}
	public String getObrAltTestCode() {
		return obrAltTestCode;
	}
	public void setObrAltTestCode(String obrAltTestCode) {
		this.obrAltTestCode = obrAltTestCode;
	}
	public String getObrAltTestDesc() {
		return obrAltTestDesc;
	}
	public void setObrAltTestDesc(String obrAltTestDesc) {
		this.obrAltTestDesc = obrAltTestDesc;
	}
	public String getObrAltTestCodingSystem() {
		return obrAltTestCodingSystem;
	}
	public void setObrAltTestCodingSystem(String obrAltTestCodingSystem) {
		this.obrAltTestCodingSystem = obrAltTestCodingSystem;
	}
	public String getObrNote() {
		return obrNote;
	}
	public void setObrNote(String obrNote) {
		this.obrNote = obrNote;
	}
	public void setTheOBXList(List<RawHL7ObxVO> theOBXList) {
		this.theOBXList = theOBXList;
	}
	public String getObrCollectionDateStr() {
		return obrCollectionDateStr;
	}
	public void setObrCollectionDateStr(String obrCollectionDateStr) {
		this.obrCollectionDateStr = obrCollectionDateStr;
	}
	public String getObrObservationDateStr() {
		return obrObservationDateStr;
	}
	public void setObrObservationDateStr(String obrObservationDateStr) {
		this.obrObservationDateStr = obrObservationDateStr;
	}
	public String getObrProviderName() {
		return obrProviderName;
	}
	public void setObrProviderName(String obrProviderName) {
		this.obrProviderName = obrProviderName;
	}
	
		
}
