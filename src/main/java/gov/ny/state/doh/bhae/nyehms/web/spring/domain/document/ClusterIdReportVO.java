package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.util.List;

public class ClusterIdReportVO {
	
	List<ClusterIdReportDataVO> clusterIdReportDataList;
	ClusterIdReportHeaderVO clusterIdReportHeaderDetails;
	
	public List<ClusterIdReportDataVO> getClusterIdReportDataList() {
		return clusterIdReportDataList;
	}
	public void setClusterIdReportDataList(
			List<ClusterIdReportDataVO> clusterIdReportDataList) {
		this.clusterIdReportDataList = clusterIdReportDataList;
	}
	
	public ClusterIdReportHeaderVO getClusterIdReportHeaderDetails() {
		return clusterIdReportHeaderDetails;
	}
	public void setClusterIdReportHeaderDetails(
			ClusterIdReportHeaderVO clusterIdReportHeaderDetails) {
		this.clusterIdReportHeaderDetails = clusterIdReportHeaderDetails;
	}


}
