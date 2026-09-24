package gov.ny.state.doh.bhae.nyehms.web.ajax.json;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.stereotype.Component;

/*
 * Author 	: Srikar Kummamuri
 * Date 	: 2/12/12
 */
@Component
//@XmlSeeAlso({gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonAddressVO.class})  
@XmlRootElement
public class GridObject implements Serializable {	
	
	private int currentPage;
	private int totalPages;
	private int recordsPerPage;
	private int totalRecords;
	private int records;
	//list of rows need to display on the grid
	private List rows;
	private String message;

	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getRecordsPerPage() {
		return recordsPerPage;
	}
	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public int getRecords() {
		return records;
	}
	public void setRecords(int records) {
		this.records = records;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
