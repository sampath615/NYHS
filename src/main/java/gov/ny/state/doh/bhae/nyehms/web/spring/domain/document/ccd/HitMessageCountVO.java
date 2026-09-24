package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd;

import java.math.BigDecimal;
import java.util.Date;

public class HitMessageCountVO {

	private String type;
	private Date receivedDate;
	private BigDecimal total;
	private BigDecimal distinctCount;
	private BigDecimal duplicates;
	private BigDecimal minUphnTranxInId;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total2) {
		this.total = total2;
	}

	public BigDecimal getDistinctCount() {
		return distinctCount;
	}

	public void setDistinctCount(BigDecimal distinctCount) {
		this.distinctCount = distinctCount;
	}

	public BigDecimal getDuplicates() {
		return duplicates;
	}

	public void setDuplicates(BigDecimal duplicates) {
		this.duplicates = duplicates;
	}

	public BigDecimal getMinUphnTranxInId() {
		return minUphnTranxInId;
	}

	public void setMinUphnTranxInId(BigDecimal minUphnTranxInId) {
		this.minUphnTranxInId = minUphnTranxInId;
	}

}
