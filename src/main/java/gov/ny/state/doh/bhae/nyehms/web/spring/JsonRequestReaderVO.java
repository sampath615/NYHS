package gov.ny.state.doh.bhae.nyehms.web.spring.domain;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentsListVO;
import java.util.List;

/*
 * Sample calss being used by JsonPojoCeonverter.
 */

public class JsonRequestReaderVO {
	
	
	private List<AssignmentsListVO> rows;

	public List<AssignmentsListVO> getRows() {
		return rows;
	}

	public void setRows(List<AssignmentsListVO> rows) {
		this.rows = rows;
	}	

}
