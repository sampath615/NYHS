package gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration;

public class NyehmsRole {

	private long roleCode;
	public long getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(long roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	public char getIsExpandable() {
		return isExpandable;
	}
	public void setIsExpandable(char isExpandable) {
		this.isExpandable = isExpandable;
	}
	private String roleDescription;
	private char isExpandable='N';
}
