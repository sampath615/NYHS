package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.MessageReturnVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.MessageSentVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PSMessageCommentsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PSMessageVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.UpdatedDispositionsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.TypedRowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values;

/**
 * The former nested result map {@code psMessageDetailsMap} of CommonDocLabsDAO.getPSMessageDetails: a row of the
 * message details query is read into the parts of a {@link PSMessageVO} (the sent message, the returned message,
 * one review comment, one updated disposition), each part set from its named columns only (the nested result maps
 * were not auto-mapped), a part with no value being null; {@link #group} then folds the rows into one PSMessageVO per
 * document as MyBatis did: the first row's parts, the comments and dispositions collected without repeats.
 */
final class PsMessageRows {

	private PsMessageRows() {
	}

	/** The parts of one row. */
	record Part(Integer documentId, MessageSentVO sent, MessageReturnVO returned, PSMessageCommentsVO comment, UpdatedDispositionsVO disposition) {
	}

	private static final TypedRowMapper<MessageSentVO> SENT = Rows.resultMap(MessageSentVO.class,
			"TRIGGER_DOC_ID", "TRIGGER_DOC_ID", "MESSAGE_ENTERED_DATE_SENT", "MESSAGE_ENTERED_DATE", "MESSAGE_RECEIVED_DATE_SENT", "MESSAGE_RECEIVED_DATE",
			"CDESSCASEKEY_SENT", "CDESSCASEKEY", "CDESSDISMISSED", "CDESSDISMISSED", "TRIGGER_MSG_DESC", "TRIGGER_MSG_DESC", "TRIGGER_MSG_TYPE", "TRIGGER_MSG_TYPE",
			"REVIEW_COMMENTS", "REVIEW_COMMENTS", "REVIEW_DATE", "REVIEW_DATE", "REVIEW_UID", "REVIEW_UID", "PROCESS_IND", "PROCESS_IND", "TRIGGER_BY", "TRIGGER_BY",
			"LAFT_COMMENTS", "LAFT_COMMENTS", "LAFT_REASONS", "LAFT_REASON");

	private static final TypedRowMapper<PSMessageCommentsVO> COMMENT = Rows.resultMap(PSMessageCommentsVO.class,
			"MESSAGE_COMMENTS", "COMMENTS", "COMMENTS_CREATE_ID", "CREATE_ID", "COMMENTS_CREATE_DATE", "CREATE_DATE");

	private static final TypedRowMapper<MessageReturnVO> RETURNED = Rows.resultMap(MessageReturnVO.class,
			"MESSAGE_ENTERED_DATE", "MESSAGE_ENTERED_DATE", "MESSAGE_RECEIVED_DATE", "MESSAGE_RECEIVED_DATE", "CDESSCASEKEY", "CDESSCASEKEY", "PREGNANT", "PREGNANT",
			"RACE_WHITE", "RACE_WHITE", "RACE_BLACK", "RACE_BLACK", "RACE_ASIAN", "RACE_ASIAN", "RACE_PACIFIC", "RACE_PACIFIC", "RACE_INDIAN", "RACE_INDIAN",
			"RACE_MULTIPLE", "RACE_MULTIPLE", "RACE_OTHER", "RACE_OTHER", "SWM", "SWM", "SWF", "SWF", "SWT", "SWT", "SWA", "SWA", "SEX_WOUT_CONDOM", "SEX_WOUT_CONDOM",
			"SEX_WHILE_INTOX", "SEX_WHILE_INTOX", "EXCHANGE_SEX", "EXCHANGE_SEX", "FEMALE_SEX_MSM", "FEMALE_SEX_MSM", "SEX_IDU", "SEX_IDU", "INCARCERATED", "INCARCERATED",
			"SHARED_DRUG_EQUIP", "SHARED_DRUG_EQUIP", "IDU", "IDU", "CRACK", "CRACK", "COCAINE", "COCAINE", "HEROIN", "HEROIN", "METH", "METH", "NITRATES", "NITRATES",
			"ED_MEDS", "ED_MEDS", "STATENO", "STATENO", "CITY_NO", "CITY_NO", "DOB", "DOB", "DOD", "DOD", "DX_DATE", "DX_DATE", "FIRST_NAME", "FIRST_NAME",
			"MIDDLE_NAME", "MIDDLE_NAME", "LAST_NAME", "LAST_NAME", "OTHER_RISK", "OTHER_RISK", "SEX_AT_BIRTH", "SEX_AT_BIRTH", "GENDER", "GENDER",
			"PERSON_STATE", "PERSON_STATE", "DX_STATE", "DX_STATE", "BIRTH_COUNTRY", "BIRTH_COUNTRY", "PERSON_CITY", "PERSON_CITY", "DX_CITY", "DX_CITY",
			"DISPOSITION_CODE", "DISPOSITION_CODE", "PERSON_ZIP", "PERSON_ZIP", "DX_ZIP", "DX_ZIP", "OTHER_DRUGS", "OTHER_DRUGS", "PERSON_ADDRESS", "PERSON_ADDRESS",
			"DX_ADDRESS", "DX_ADDRESS", "DIN", "DIN", "NYSID", "NYSID", "X900POSITIVE", "X900POSITIVE", "X900DXDATE", "X900DXDATE",
			"DIAGNOSISLOCATION", "DIAGNOSISLOCATION", "DIAGNOSISLOCOUTSIDEUS", "DIAGNOSISLOCOUTSIDEUS", "INITMEDAPPT", "INITMEDAPPT", "DATEMEDAPPT", "DATEMEDAPPT",
			"ATTENDEDMEDAPPT", "ATTENDEDMEDAPPT", "TESTTECHNOLOGY", "TESTTECHNOLOGY", "NINEHUNDREDTEST", "NINEHUNDREDTEST", "CURRENT900TESTDATE", "CURRENT900TESTDATE",
			"CURRENT900TESTRESULT", "CURRENT900TESTRESULT", "PREPREFERRAL", "PREPREFERRAL", "EVERONPREP", "EVERONPREP", "CURRENTONPREP", "CURRENTONPREP",
			"REFERRALPREPINFO", "REFERRALPREPINFO", "FIELDNOTE", "FIELDNOTE");

	private static final TypedRowMapper<UpdatedDispositionsVO> DISPOSITION = Rows.resultMap(UpdatedDispositionsVO.class,
			"PS_DATA_DISPOSITION_CODE", "DISPOSITION_CODE", "DISPOSITION_DESC", "DISPOSITION_DESC", "DISPOSITION_DATE", "DISPOSITION_DATE", "FIELDNOTES", "FIELDNOTES",
			"UPDATED_BY", "UPDATED_BY", "UPDATED_DATE", "UPDATED_DATE");

	private static final List<String> SENT_COLUMNS = columns("TRIGGER_DOC_ID", "MESSAGE_ENTERED_DATE_SENT", "MESSAGE_RECEIVED_DATE_SENT", "CDESSCASEKEY_SENT",
			"CDESSDISMISSED", "TRIGGER_MSG_DESC", "TRIGGER_MSG_TYPE", "REVIEW_COMMENTS", "REVIEW_DATE", "REVIEW_UID", "PROCESS_IND", "TRIGGER_BY", "LAFT_COMMENTS",
			"LAFT_REASONS");
	private static final List<String> COMMENT_COLUMNS = columns("MESSAGE_COMMENTS", "COMMENTS_CREATE_ID", "COMMENTS_CREATE_DATE");
	private static final List<String> RETURNED_COLUMNS = columns("MESSAGE_ENTERED_DATE", "MESSAGE_RECEIVED_DATE", "CDESSCASEKEY", "PREGNANT", "RACE_WHITE",
			"RACE_BLACK", "RACE_ASIAN", "RACE_PACIFIC", "RACE_INDIAN", "RACE_MULTIPLE", "RACE_OTHER", "SWM", "SWF", "SWT", "SWA", "SEX_WOUT_CONDOM", "SEX_WHILE_INTOX",
			"EXCHANGE_SEX", "FEMALE_SEX_MSM", "SEX_IDU", "INCARCERATED", "SHARED_DRUG_EQUIP", "IDU", "CRACK", "COCAINE", "HEROIN", "METH", "NITRATES", "ED_MEDS",
			"STATENO", "CITY_NO", "DOB", "DOD", "DX_DATE", "FIRST_NAME", "MIDDLE_NAME", "LAST_NAME", "OTHER_RISK", "SEX_AT_BIRTH", "GENDER", "PERSON_STATE", "DX_STATE",
			"BIRTH_COUNTRY", "PERSON_CITY", "DX_CITY", "DISPOSITION_CODE", "PERSON_ZIP", "DX_ZIP", "OTHER_DRUGS", "PERSON_ADDRESS", "DX_ADDRESS", "DIN", "NYSID",
			"X900POSITIVE", "X900DXDATE", "DIAGNOSISLOCATION", "DIAGNOSISLOCOUTSIDEUS", "INITMEDAPPT", "DATEMEDAPPT", "ATTENDEDMEDAPPT", "TESTTECHNOLOGY",
			"NINEHUNDREDTEST", "CURRENT900TESTDATE", "CURRENT900TESTRESULT", "PREPREFERRAL", "EVERONPREP", "CURRENTONPREP", "REFERRALPREPINFO", "FIELDNOTE");
	private static final List<String> DISPOSITION_COLUMNS = columns("PS_DATA_DISPOSITION_CODE", "DISPOSITION_DESC", "DISPOSITION_DATE", "FIELDNOTES", "UPDATED_BY",
			"UPDATED_DATE");

	private static List<String> columns(String... names) {
		return List.of(names);
	}

	/** A row's parts, each read from its named columns only (with the property's type, as the result map's jdbcType / javaType read them). */
	static final TypedRowMapper<Part> PART = new TypedRowMapper<Part>() {
		@Override
		public Class<?> typeOf(String label, int index) {
			String upper = label.toUpperCase(java.util.Locale.ENGLISH);
			if (upper.equals("DOCUMENT_ID")) {
				return int.class;
			}
			for (TypedRowMapper<?> mapper : List.of(SENT, COMMENT, RETURNED, DISPOSITION)) {
				Class<?> type = mapper.typeOf(label, index);
				if (type != null && (SENT_COLUMNS.contains(upper) || COMMENT_COLUMNS.contains(upper) || RETURNED_COLUMNS.contains(upper)
						|| DISPOSITION_COLUMNS.contains(upper))) {
					return type;
				}
			}
			return null;
		}

		@Override
		public Part map(Row row) {
			Object documentId = row.indexOf("DOCUMENT_ID") < 0 ? null : row.get("DOCUMENT_ID");
			return new Part(documentId == null ? null : Values.longValue(documentId).intValue(), SENT.map(only(row, SENT_COLUMNS)),
					RETURNED.map(only(row, RETURNED_COLUMNS)), COMMENT.map(only(row, COMMENT_COLUMNS)), DISPOSITION.map(only(row, DISPOSITION_COLUMNS)));
		}
	};

	/** The row's columns among the given ones (the first of a repeated label), so the result map auto-maps nothing else. */
	private static Row only(Row row, List<String> columns) {
		List<String> labels = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		for (String column : columns) {
			int index = row.indexOf(column);
			if (index >= 0) {
				labels.add(column);
				values.add(row.get(index));
			}
		}
		return new Row(labels, values);
	}

	/**
	 * The rows folded into one PSMessageVO per document (in the rows' order): the first row's DOCUMENT_ID, sent and
	 * returned parts; the comments and dispositions of all its rows, a repeated one (all its columns equal) once, a
	 * part with no value left out.
	 */
	static List<PSMessageVO> group(List<Part> parts) {
		List<PSMessageVO> messages = new ArrayList<>();
		PSMessageVO current = null;
		Integer currentId = null;
		Set<List<Object>> comments = new HashSet<>();
		Set<List<Object>> dispositions = new HashSet<>();
		for (Part part : parts) {
			if (current == null || !java.util.Objects.equals(currentId, part.documentId())) {
				current = new PSMessageVO();
				currentId = part.documentId();
				current.setDOCUMENT_ID(part.documentId() == null ? 0 : part.documentId());
				current.setMessageSentVO(part.sent());
				current.setMessageReturnVO(part.returned());
				if (part.sent() != null) {
					part.sent().setReviewComments(new ArrayList<>());
				}
				if (part.returned() != null) {
					part.returned().setUpdatedDispositionsList(new ArrayList<>());
				}
				comments = new HashSet<>();
				dispositions = new HashSet<>();
				messages.add(current);
			}
			if (part.comment() != null && current.getMessageSentVO() != null
					&& comments.add(List.of(text(part.comment().getCOMMENTS()), text(part.comment().getCREATE_ID()), text(part.comment().getCREATE_DATE())))) {
				current.getMessageSentVO().getReviewComments().add(part.comment());
			}
			if (part.disposition() != null && current.getMessageReturnVO() != null && dispositions.add(List.of(text(part.disposition().getDISPOSITION_CODE()),
					text(part.disposition().getDISPOSITION_DESC()), text(part.disposition().getDISPOSITION_DATE()), text(part.disposition().getFIELDNOTES()),
					text(part.disposition().getUPDATED_BY()), text(part.disposition().getUPDATED_DATE())))) {
				current.getMessageReturnVO().getUpdatedDispositionsList().add(part.disposition());
			}
		}
		return messages;
	}

	private static String text(Object value) {
		return value == null ? "" : value.toString();
	}
}
