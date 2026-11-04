package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class RawHL7ORUVO {
	
	private int rawHl7OruId;
	private int documentId;
	private int obrCount;
	private int obxCount;
	private int nteCount;

	public RawHL7ORUVO() {
		// TODO Auto-generated constructor stub
	}

	public int getRawHl7OruId() {
		return rawHl7OruId;
	}

	public void setRawHl7OruId(int rawHl7OruId) {
		this.rawHl7OruId = rawHl7OruId;
	}

	public int getDocumentId() {
		return documentId;
	}

	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}

	public int getObrCount() {
		return obrCount;
	}

	public void setObrCount(int obrCount) {
		this.obrCount = obrCount;
	}

	public int getObxCount() {
		return obxCount;
	}

	public void setObxCount(int obxCount) {
		this.obxCount = obxCount;
	}
	
	public int getNteCount() {
		return nteCount;
	}

	public void setNteCount(int nteCount) {
		this.nteCount = nteCount;
	}

	public String toString() {
			return ReflectionToStringBuilder.toString(this);
	}

}
