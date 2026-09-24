package gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads;

import java.util.List;

/**
 * @author skg02
 *
 */
public class ClusterUploadVO {
	
	private String clusterType;
	private String clusterId;
	private String uploadClusterFile;
	private List <String> clusterCountiesList;
	private String clusterCountiesCds;
	private String clusterDate;
	private String curr_stateno;
	private String uploadType;
	private String existingClusterId;
	private int dupStatenoCount;
	private String relatedness;
	private String description;
	private String spaceTimeDxStartDate;
	private String spaceTimeDxEndDate;
	private String moleTimeFrame;
	private String tsUpdateRetire;
	private int clusterIdPk;
	private String clusterIdentifyDate;
	private String spaceTimeClusterEndDate;
	private List <String> transmissionRiskList;
	private String transmissionRiskCds;
	private String birthSex;
	private List <String> raceList;	
	private String raceCds;
	private String ethnicity;
	private String ageRangeFrom;
	private String ageRangeTo;
	
	public String getClusterType() {
		return clusterType;
	}
	public void setClusterType(String clusterType) {
		this.clusterType = clusterType;
	}
	public String getClusterId() {
		return clusterId;
	}
	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}
	public String getUploadClusterFile() {
		return uploadClusterFile;
	}
	public void setUploadClusterFile(String uploadClusterFile) {
		this.uploadClusterFile = uploadClusterFile;
	}
	public List<String> getClusterCountiesList() {
		return clusterCountiesList;
	}
	public void setClusterCountiesList(List<String> clusterCountiesList) {
		this.clusterCountiesList = clusterCountiesList;
	}
	public String getClusterCountiesCds() {
		return clusterCountiesCds;
	}
	public void setClusterCountiesCds(String clusterCountiesCds) {
		this.clusterCountiesCds = clusterCountiesCds;
	}
	public String getClusterDate() {
		return clusterDate;
	}
	public void setClusterDate(String clusterDate) {
		this.clusterDate = clusterDate;
	}
	public String getCurr_stateno() {
		return curr_stateno;
	}
	public void setCurr_stateno(String curr_stateno) {
		this.curr_stateno = curr_stateno;
	}
	public String getUploadType() {
		return uploadType;
	}
	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}
	public String getExistingClusterId() {
		return existingClusterId;
	}
	public void setExistingClusterId(String existingClusterId) {
		this.existingClusterId = existingClusterId;
	}
	public int getDupStatenoCount() {
		return dupStatenoCount;
	}
	public void setDupStatenoCount(int dupStatenoCount) {
		this.dupStatenoCount = dupStatenoCount;
	}
	public String getRelatedness() {
		return relatedness;
	}
	public void setRelatedness(String relatedness) {
		this.relatedness = relatedness;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSpaceTimeDxStartDate() {
		return spaceTimeDxStartDate;
	}
	public void setSpaceTimeDxStartDate(String spaceTimeDxStartDate) {
		this.spaceTimeDxStartDate = spaceTimeDxStartDate;
	}
	public String getSpaceTimeDxEndDate() {
		return spaceTimeDxEndDate;
	}
	public void setSpaceTimeDxEndDate(String spaceTimeDxEndDate) {
		this.spaceTimeDxEndDate = spaceTimeDxEndDate;
	}
	public String getMoleTimeFrame() {
		return moleTimeFrame;
	}
	public void setMoleTimeFrame(String moleTimeFrame) {
		this.moleTimeFrame = moleTimeFrame;
	}
	public String getTsUpdateRetire() {
		return tsUpdateRetire;
	}
	public void setTsUpdateRetire(String tsUpdateRetire) {
		this.tsUpdateRetire = tsUpdateRetire;
	}
	public int getClusterIdPk() {
		return clusterIdPk;
	}
	public void setClusterIdPk(int clusterIdPk) {
		this.clusterIdPk = clusterIdPk;
	}
	public String getClusterIdentifyDate() {
		return clusterIdentifyDate;
	}
	public void setClusterIdentifyDate(String clusterIdentifyDate) {
		this.clusterIdentifyDate = clusterIdentifyDate;
	}
	public String getSpaceTimeClusterEndDate() {
		return spaceTimeClusterEndDate;
	}
	public void setSpaceTimeClusterEndDate(String spaceTimeClusterEndDate) {
		this.spaceTimeClusterEndDate = spaceTimeClusterEndDate;
	}
	public String getBirthSex() {
		return birthSex;
	}
	public void setBirthSex(String birthSex) {
		this.birthSex = birthSex;
	}
	public String getEthnicity() {
		return ethnicity;
	}
	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}
	public String getAgeRangeFrom() {
		return ageRangeFrom;
	}
	public void setAgeRangeFrom(String ageRangeFrom) {
		this.ageRangeFrom = ageRangeFrom;
	}
	public String getAgeRangeTo() {
		return ageRangeTo;
	}
	public void setAgeRangeTo(String ageRangeTo) {
		this.ageRangeTo = ageRangeTo;
	}
	public List<String> getTransmissionRiskList() {
		return transmissionRiskList;
	}
	public void setTransmissionRiskList(List<String> transmissionRiskList) {
		this.transmissionRiskList = transmissionRiskList;
	}
	public List<String> getRaceList() {
		return raceList;
	}
	public void setRaceList(List<String> raceList) {
		this.raceList = raceList;
	}
	public String getTransmissionRiskCds() {
		return transmissionRiskCds;
	}
	public void setTransmissionRiskCds(String transmissionRiskCds) {
		this.transmissionRiskCds = transmissionRiskCds;
	}
	public String getRaceCds() {
		return raceCds;
	}
	public void setRaceCds(String raceCds) {
		this.raceCds = raceCds;
	}
	
}
