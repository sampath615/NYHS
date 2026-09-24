package gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider;

public class SubProviderVO {
	
	private long providerId;
	private String providerFname;
	private String providerMname;
	private String providerLname;
	private String providerSuffix;
	private String providerNPI;
	private int totalSubProviderAddress;
	private long inputProvAddrId;
	
	public String getProviderFullName() {
		return providerLname +","+ providerFname + " " +providerMname ;
	}
	
	public int getTotalSubProviderAddress() {
		return totalSubProviderAddress;
	}
	public void setTotalSubProviderAddress(int totalSubProviderAddress) {
		this.totalSubProviderAddress = totalSubProviderAddress;
	}
	public long getProviderId() {
		return providerId;
	}
	public void setProviderId(long providerId) {
		this.providerId = providerId;
	}
	public String getProviderFname() {
		return providerFname;
	}
	public void setProviderFname(String providerFname) {
		this.providerFname = providerFname;
	}
	public String getProviderMname() {
		return providerMname;
	}
	public void setProviderMname(String providerMname) {
		this.providerMname = providerMname;
	}
	public String getProviderLname() {
		return providerLname;
	}
	public void setProviderLname(String providerLname) {
		this.providerLname = providerLname;
	}
	public String getProviderSuffix() {
		return providerSuffix;
	}
	public void setProviderSuffix(String providerSuffix) {
		this.providerSuffix = providerSuffix;
	}
	public long getInputProvAddrId() {
		return inputProvAddrId;
	}
	public void setInputProvAddrId(long inputProvAddrId) {
		this.inputProvAddrId = inputProvAddrId;
	}
	
    public String getProviderNPI() {
		return providerNPI;
	}

	public void setProviderNPI(String providerNPI) {
		this.providerNPI = providerNPI;
	}

@Override
   public String toString(){
	   
	   	
	   StringBuilder b =new StringBuilder();
	   b.append("{");
	   b.append("\"providerId\"");
	   b.append(':');
	   b.append(providerId);
	   b.append(',');
	   b.append("\"providerFullName\"");
	   b.append(':');
	   b.append("\""+getProviderFullName()+"\"");
	   b.append(',');
	   b.append("\"providerFname\"");
	   b.append(':');
	   b.append("\""+providerFname+"\"");
	   b.append(',');
	   b.append("\"providerMname\"");
	   b.append(':');
	   b.append("\""+providerMname+"\"");
	   b.append(',');
	   b.append("\"providerLname\"");
	   b.append(':');
	   b.append("\""+providerLname+"\"");
	   
	   b.append("}");
	   
	   
	   
	   return b.toString();
	}
}
