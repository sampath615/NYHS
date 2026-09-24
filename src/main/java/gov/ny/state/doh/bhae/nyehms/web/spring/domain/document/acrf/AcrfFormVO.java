package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CodedAcrfTestVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AcrfFormVO {
	
	private RawAcrfDocVO rawAcrfDoc;
	private List<RawAcrfLabsVO> rawAcrfLabs;	
	//Drop Down Lists for Lab Tests 
	private List<CodedAcrfTestVO> hivABNonTypTstMap = new ArrayList<CodedAcrfTestVO>();
	private List<CodedAcrfTestVO> hivABTypTstMap = new ArrayList<CodedAcrfTestVO>();
	private List<CodedAcrfTestVO> hivQualitativeTypTstMap = new ArrayList<CodedAcrfTestVO>();
	private List<CodedAcrfTestVO> hivQuantitativeTstMap = new ArrayList<CodedAcrfTestVO>();
	private List<CodedAcrfTestVO> drugResistanceTstMap = new ArrayList<CodedAcrfTestVO>();
	
	private Map<String, List<CodedAcrfTestVO>> labTestResultMap = new HashMap<>();
	
	
	private List<CodedArvMedicationsVO> arvMedicationsList = new ArrayList<CodedArvMedicationsVO>();
	
	private List<AcrfRidrVO> acrfRidrList = new ArrayList<AcrfRidrVO>();
	private String reabstract;
	
	
		
	private List<RawAcrfCd4TstVO> rawAcrfCd4Tst;
	//Saved ACRF Forms will be sent back to user.
	private Map<String, String> savedAcrfDocMap = new HashMap<String, String>();
		
	public RawAcrfDocVO getRawAcrfDoc() {
		return rawAcrfDoc;
	}
	public void setRawAcrfDoc(RawAcrfDocVO rawAcrfDoc) {
		this.rawAcrfDoc = rawAcrfDoc;
	}
	public List<RawAcrfLabsVO> getRawAcrfLabs() {
		return rawAcrfLabs;
	}
	public void setRawAcrfLabs(List<RawAcrfLabsVO> rawAcrfLabs) {
		this.rawAcrfLabs = rawAcrfLabs;
	}
		
	public List<CodedAcrfTestVO> getHivABNonTypTstMap() {
		return hivABNonTypTstMap;
	}
	public void setHivABNonTypTstMap(List<CodedAcrfTestVO> hivABNonTypTstMap) {
		this.hivABNonTypTstMap = hivABNonTypTstMap;
	}
	public List<CodedAcrfTestVO> getHivABTypTstMap() {
		return hivABTypTstMap;
	}
	public void setHivABTypTstMap(List<CodedAcrfTestVO> hivABTypTstMap) {
		this.hivABTypTstMap = hivABTypTstMap;
	}
	public List<CodedAcrfTestVO> getHivQualitativeTypTstMap() {
		return hivQualitativeTypTstMap;
	}
	public void setHivQualitativeTypTstMap(
			List<CodedAcrfTestVO> hivQualitativeTypTstMap) {
		this.hivQualitativeTypTstMap = hivQualitativeTypTstMap;
	}
	public List<CodedAcrfTestVO> getHivQuantitativeTstMap() {
		return hivQuantitativeTstMap;
	}
	public void setHivQuantitativeTstMap(List<CodedAcrfTestVO> hivQuantitativeTstMap) {
		this.hivQuantitativeTstMap = hivQuantitativeTstMap;
	}
	public List<RawAcrfCd4TstVO> getRawAcrfCd4Tst() {
		return rawAcrfCd4Tst;
	}
	public void setRawAcrfCd4Tst(List<RawAcrfCd4TstVO> rawAcrfCd4Tst) {
		this.rawAcrfCd4Tst = rawAcrfCd4Tst;
	}
	
	public Map<String, String> getSavedAcrfDocMap() {
		return savedAcrfDocMap;
	}
	public void setSavedAcrfDocMap(Map<String, String> savedAcrfDocMap) {
		this.savedAcrfDocMap = savedAcrfDocMap;
	}
	public List<CodedArvMedicationsVO> getArvMedicationsList() {
		return arvMedicationsList;
	}
	public void setArvMedicationsList(List<CodedArvMedicationsVO> arvMedicationsList) {
		this.arvMedicationsList = arvMedicationsList;
	}
	public List<AcrfRidrVO> getAcrfRidrList() {
		return acrfRidrList;
	}
	public void setAcrfRidrList(List<AcrfRidrVO> acrfRidrList) {
		this.acrfRidrList = acrfRidrList;
	}
	public List<CodedAcrfTestVO> getDrugResistanceTstMap() {
		return drugResistanceTstMap;
	}
	public void setDrugResistanceTstMap(List<CodedAcrfTestVO> drugResistanceTstMap) {
		this.drugResistanceTstMap = drugResistanceTstMap;
	}
	public String getReabstract() {
		return reabstract;
	}
	public void setReabstract(String reabstract) {
		this.reabstract = reabstract;
	}
	public Map<String, List<CodedAcrfTestVO>> getLabTestResultMap() {
		return labTestResultMap;
	}
	public void setLabTestResultMap(Map<String, List<CodedAcrfTestVO>> labTestResultMap) {
		this.labTestResultMap = labTestResultMap;
	}
	
	
	
}
