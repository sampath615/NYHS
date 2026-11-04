package gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads;


public class ClusterUploadErrorVO implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String clusterId;
	private int documentId;
	private String clusterType;
	private String uploadedEharsStateno;
	private String uploadedNyehmsStateno;
	private String strClusterId;
	private String errorReason;
	private String identificationMethod;
	private String identificationDate;	
	
	public String getClusterId() {
		return clusterId;
	}
	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}
	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}

	public String getUploadedEharsStateno() {
		return uploadedEharsStateno;
	}
	public void setUploadedEharsStateno(String uploadedEharsStateno) {
		this.uploadedEharsStateno = uploadedEharsStateno;
	}
	public String getUploadedNyehmsStateno() {
		return uploadedNyehmsStateno;
	}
	public void setUploadedNyehmsStateno(String uploadedNyehmsStateno) {
		this.uploadedNyehmsStateno = uploadedNyehmsStateno;
	}
	public String getClusterType() {
		return clusterType;
	}
	public void setClusterType(String clusterType) {
		this.clusterType = clusterType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getStrClusterId() {
		return strClusterId;
	}
	public void setStrClusterId(String strClusterId) {
		this.strClusterId = strClusterId;
	}
	public String getErrorReason() {
		return errorReason;
	}
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}
	public String getIdentificationMethod() {
		return identificationMethod;
	}
	public void setIdentificationMethod(String identificationMethod) {
		this.identificationMethod = identificationMethod;
	}
	public String getIdentificationDate() {
		return identificationDate;
	}
	public void setIdentificationDate(String identificationDate) {
		this.identificationDate = identificationDate;
	}
}
