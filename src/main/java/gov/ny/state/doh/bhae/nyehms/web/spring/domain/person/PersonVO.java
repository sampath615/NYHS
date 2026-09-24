package gov.ny.state.doh.bhae.nyehms.web.spring.domain.person;

import java.io.Serializable;
import java.util.List;

/*
 * Author 	: Sampath Anumolu
 * Date 	: 7/8/14
 */

public class PersonVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private PersonViewVO personViewVO;
	
	private PersonRisksVO personRisksVO;
	
	private List<PersonDocHistoryVO> personDocHistoryList;
	
	private List<String> statenoHistory;
	
	
	public PersonViewVO getPersonViewVO() {
		return personViewVO;
	}
	public void setPersonViewVO(PersonViewVO personViewVO) {
		this.personViewVO = personViewVO;
	}
	public PersonRisksVO getPersonRisksVO() {
		return personRisksVO;
	}
	public void setPersonRisksVO(PersonRisksVO personRisksVO) {
		this.personRisksVO = personRisksVO;
	}
	public List<String> getStatenoHistory() {
		return statenoHistory;
	}
	public void setStatenoHistory(List<String> statenoHistory) {
		this.statenoHistory = statenoHistory;
	}
	public List<PersonDocHistoryVO> getPersonDocHistoryList() {
		return personDocHistoryList;
	}
	public void setPersonDocHistoryList(List<PersonDocHistoryVO> personDocHistoryList) {
		this.personDocHistoryList = personDocHistoryList;
	}
	
	
	

}
