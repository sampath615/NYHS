package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;

import java.util.List;

public class LAFTCountReconcileResolutionVO {
	
	private String identifiedBy;
	private boolean reconciled;
	private String collectionStartDate;
	private String collectionEndDate;
	private String contactDate;
	
	private String surMonLabStaff;
	private String surMonLabStaffEmail;
	private String surMonLabStaffPhNo;
	private String surMonOpenEndedResComments;
	private String bhaeComments;
	private String coLaftStaff;
	
	private List<LAFTCountReconcileTestDataVO> countTestDataList;

	public String getIdentifiedBy() {
		return identifiedBy;
	}

	public void setIdentifiedBy(String identifiedBy) {
		this.identifiedBy = identifiedBy;
	}

	public boolean isReconciled() {
		return reconciled;
	}

	public void setReconciled(boolean reconciled) {
		this.reconciled = reconciled;
	}

	public String getCoLaftStaff() {
		return coLaftStaff;
	}

	public void setCoLaftStaff(String coLaftStaff) {
		this.coLaftStaff = coLaftStaff;
	}

	public String getCollectionStartDate() {
		return collectionStartDate;
	}

	public void setCollectionStartDate(String collectionStartDate) {
		this.collectionStartDate = collectionStartDate;
	}

	public String getCollectionEndDate() {
		return collectionEndDate;
	}

	public void setCollectionEndDate(String collectionEndDate) {
		this.collectionEndDate = collectionEndDate;
	}

	public String getContactDate() {
		return contactDate;
	}

	public void setContactDate(String contactDate) {
		this.contactDate = contactDate;
	}

	public String getSurMonLabStaff() {
		return surMonLabStaff;
	}

	public void setSurMonLabStaff(String surMonLabStaff) {
		this.surMonLabStaff = surMonLabStaff;
	}

	public String getSurMonLabStaffEmail() {
		return surMonLabStaffEmail;
	}

	public void setSurMonLabStaffEmail(String surMonLabStaffEmail) {
		this.surMonLabStaffEmail = surMonLabStaffEmail;
	}

	public String getSurMonLabStaffPhNo() {
		return surMonLabStaffPhNo;
	}

	public void setSurMonLabStaffPhNo(String surMonLabStaffPhNo) {
		this.surMonLabStaffPhNo = surMonLabStaffPhNo;
	}

	public String getSurMonOpenEndedResComments() {
		return surMonOpenEndedResComments;
	}

	public void setSurMonOpenEndedResComments(String surMonOpenEndedResComments) {
		this.surMonOpenEndedResComments = surMonOpenEndedResComments;
	}

	public String getBhaeComments() {
		return bhaeComments;
	}

	public void setBhaeComments(String bhaeComments) {
		this.bhaeComments = bhaeComments;
	}

	public List<LAFTCountReconcileTestDataVO> getCountTestDataList() {
		return countTestDataList;
	}

	public void setCountTestDataList(List<LAFTCountReconcileTestDataVO> countTestDataList) {
		this.countTestDataList = countTestDataList;
	}
	
	
	
}
