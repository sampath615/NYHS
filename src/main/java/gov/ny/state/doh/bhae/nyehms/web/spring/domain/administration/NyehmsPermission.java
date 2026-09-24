package gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration;

public class NyehmsPermission {
	public NyehmsPermission(long permissionId, long moduleId, long subModuleId,
			String moduleName, String subModuleName,
			String permissionDescription) {
		super();
		this.permissionId = permissionId;
		this.subModuleId = subModuleId;
		this.subModuleName = subModuleName;
		this.permissionDescription = permissionDescription;
	}
	public NyehmsPermission(){
		
	}
	private long permissionId;
	private long subModuleId;
	private String subModuleName;
	private String permissionDescription;
	private String isSuggestedPermission;
	public String getIsSuggestedPermission() {
		return isSuggestedPermission;
	}
	public void setIsSuggestedPermission(String isSuggestedPermission) {
		this.isSuggestedPermission = isSuggestedPermission;
	}
	public long getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(long permissionId) {
		this.permissionId = permissionId;
	}
	public long getSubModuleId() {
		return subModuleId;
	}
	public void setSubModuleId(long subModuleId) {
		this.subModuleId = subModuleId;
	}
	public String getSubModuleName() {
		return subModuleName;
	}
	public void setSubModuleName(String subModuleName) {
		this.subModuleName = subModuleName;
	}
	public String getPermissionDescription() {
		return permissionDescription;
	}
	public void setPermissionDescription(String permissionDescription) {
		this.permissionDescription = permissionDescription;
	}
}
