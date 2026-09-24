package gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider;

public class SubProvAddrsForRawProvAddrsVO {
	private long subProviderAddressId;
	private long inputRawProvAddressId;
	
	public long getSubProviderAddressId() {
		return subProviderAddressId;
	}
	public void setSubProviderAddressId(long subProviderAddressId) {
		this.subProviderAddressId = subProviderAddressId;
	}
	public long getInputRawProvAddressId() {
		return inputRawProvAddressId;
	}
	public void setInputRawProvAddressId(long inputRawProvAddressId) {
		this.inputRawProvAddressId = inputRawProvAddressId;
	}
}
