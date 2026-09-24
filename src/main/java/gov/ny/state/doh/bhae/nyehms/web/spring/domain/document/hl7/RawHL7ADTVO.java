package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import ca.uhn.hl7v2.model.v25.segment.DG1;

public class RawHL7ADTVO {
	
	private int rawHl7AdtId;
	private int documentId;
	private String eventTypeCode;
	private String eventDateTimeText;
	private int nk1Count;
	private int obxCount;
	private int al1Count;
	private int dg1Count;
	private List<DG1> dg1List = new ArrayList<DG1>();
	private int pr1Count;
	private int gt1Count;
	private int in1Count;

	public RawHL7ADTVO() {
		// TODO Auto-generated constructor stub
	}

	public int getRawHl7AdtId() {
		return rawHl7AdtId;
	}

	public void setRawHl7AdtId(int rawHl7AdtId) {
		this.rawHl7AdtId = rawHl7AdtId;
	}

	public int getDocumentId() {
		return documentId;
	}

	public void setDocumentId(int documentIdd) {
		this.documentId = documentIdd;
	}

	public String getEventTypeCode() {
		return eventTypeCode;
	}

	public void setEventTypeCode(String eventTypeCode) {
		this.eventTypeCode = eventTypeCode;
	}

	public String getEventDateTimeText() {
		return eventDateTimeText;
	}

	public void setEventDateTimeText(String eventDateTimeText) {
		this.eventDateTimeText = eventDateTimeText;
	}

	public int getNk1Count() {
		return nk1Count;
	}

	public void setNk1Count(int nk1Count) {
		this.nk1Count = nk1Count;
	}

	public int getObxCount() {
		return obxCount;
	}

	public void setObxCount(int obxCount) {
		this.obxCount = obxCount;
	}

	public int getAl1Count() {
		return al1Count;
	}

	public void setAl1Count(int al1Count) {
		this.al1Count = al1Count;
	}

	public int getDg1Count() {
		return dg1Count;
	}

	public void setDg1Count(int dg1Count) {
		this.dg1Count = dg1Count;
	}
	
	public List<DG1> getDg1List() {
		return dg1List;
	}

	public void setDg1List(List<DG1> dg1List) {
		this.dg1List = dg1List;
	}

	public int getPr1Count() {
		return pr1Count;
	}

	public void setPr1Count(int pr1Count) {
		this.pr1Count = pr1Count;
	}

	public int getGt1Count() {
		return gt1Count;
	}

	public void setGt1Count(int gt1Count) {
		this.gt1Count = gt1Count;
	}

	public int getIn1Count() {
		return in1Count;
	}

	public void setIn1Count(int in1Count) {
		this.in1Count = in1Count;
	}

	public String toString() {
			return ReflectionToStringBuilder.toString(this);
	}

}
