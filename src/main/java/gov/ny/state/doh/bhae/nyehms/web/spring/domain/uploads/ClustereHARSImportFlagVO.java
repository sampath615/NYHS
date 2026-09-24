package gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads;

public class ClustereHARSImportFlagVO {
	
	private int clusterIdPk;
	private String clusterName;
	private String clusterType;
	private String clusterDesc;
	private String createDate;
	private String eHARSImportFlag;	
	private String flagSetBy;
	private String flagSetDate;
	
	public int getClusterIdPk() {
		return clusterIdPk;
	}
	public void setClusterIdPk(int clusterIdPk) {
		this.clusterIdPk = clusterIdPk;
	}
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	public String getClusterType() {
		return clusterType;
	}
	public void setClusterType(String clusterType) {
		this.clusterType = clusterType;
	}
	public String getClusterDesc() {
		return clusterDesc;
	}
	public void setClusterDesc(String clusterDesc) {
		this.clusterDesc = clusterDesc;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String geteHARSImportFlag() {
		return eHARSImportFlag;
	}
	public void seteHARSImportFlag(String eHARSImportFlag) {
		this.eHARSImportFlag = eHARSImportFlag;
	}
	public String getFlagSetBy() {
		return flagSetBy;
	}
	public void setFlagSetBy(String flagSetBy) {
		this.flagSetBy = flagSetBy;
	}
	public String getFlagSetDate() {
		return flagSetDate;
	}
	public void setFlagSetDate(String flagSetDate) {
		this.flagSetDate = flagSetDate;
	}

}
