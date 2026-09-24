package gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration;

import java.util.List;

public class ModuleAndPermissionsVO {
		private long moduleId;
		private String moduleName;
		private List<NyehmsPermission> nyehmsPermissions;
		public List<NyehmsPermission> getNyehmsPermissions() {
			return nyehmsPermissions;
		}
		public void setNyehmsPermissions(List<NyehmsPermission> nyehmsPermissions) {
			this.nyehmsPermissions = nyehmsPermissions;
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
}
