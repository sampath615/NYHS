package gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.reports;

import java.util.List;

public class AssignmentReportsVO{
	
	private String REGION_DESCRIPTION;
	
	private List<WorkerAssignmentsReportsVO> workerAssignmentsReportsVO;

	public String getREGION_DESCRIPTION() {
		return REGION_DESCRIPTION;
	}

	public void setREGION_DESCRIPTION(String rEGION_DESCRIPTION) {
		REGION_DESCRIPTION = rEGION_DESCRIPTION;
	}

	public List<WorkerAssignmentsReportsVO> getWorkerAssignmentsReportsVO() {
		return workerAssignmentsReportsVO;
	}

	public void setWorkerAssignmentsReportsVO(
			List<WorkerAssignmentsReportsVO> workerAssignmentsReportsVO) {
		this.workerAssignmentsReportsVO = workerAssignmentsReportsVO;
	}
	
	
}
