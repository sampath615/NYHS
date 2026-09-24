package gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration;

public class NyehmsRolePermissionsViewVO {
	
	
	private long roleCode;
	private String roleDescription;
	private long moduleId;
	private String moduleName;
	private long permissionId;
	private String permissionDesc;
	
	
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
	public long getModuleId() {
		return moduleId;
	}
	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public long getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(long permissionId) {
		this.permissionId = permissionId;
	}
	public String getPermissionDesc() {
		return permissionDesc;
	}
	public void setPermissionDesc(String permissionDesc) {
		this.permissionDesc = permissionDesc;
	}

	

}
