package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.JpaConfig;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PSMessageVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.CallParam;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import jakarta.persistence.ParameterMode;

/**
 * The common document DAO: the JPQL statements against an in-memory H2 database in Oracle mode
 * (db/common-doc-labs.sql), next to their data; the former nested result map of the message details on sample rows;
 * the procedure and the native statements (per-call tables, Oracle-only SQL) checked without a database.
 */
@SpringJUnitConfig(CommonDocLabsDaoTest.TestConfig.class)
class CommonDocLabsDaoTest {

	@Configuration
	@EnableTransactionManagement
	@Import({JpaConfig.class, NativeStatements.class, JpqlStatements.class, CommonDocLabsDAO.class})
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("commondoclabs;MODE=Oracle").generateUniqueName(false)
					.addScript("db/common-doc-labs.sql").build();
		}
	}

	@Autowired CommonDocLabsDAO dao;
	@Autowired NativeStatements statements;

	@Test
	void codeListsAsBefore() {
		assertThat(dao.getCodedArvs()).containsExactly(json("LOOKUP_CD", "3TC", "ARV_DESC", "Lamivudine"), json("LOOKUP_CD", "AZT", "ARV_DESC", "Zidovudine"));
		assertThat(dao.getCodedSexualOrientationPrfValues()).extracting(o -> o.get("SEX_ORIENT_CD")).containsExactly("G", "H");
		assertThat(dao.getCodedSexualOrientationValues()).hasSize(3);
		assertThat(dao.getCodedFacilityTypeValues()).extracting(f -> f.get("FACILITY_TYPE_CODE")).containsExactly("C", "H");
		assertThat(dao.getLAFTReasonslist()).extracting(r -> r.get("REASON_DESC")).containsExactly("Deceased", "Moved");
		assertThat(dao.getLAFTReasonslist().get(0).get("REASON_ID")).isEqualTo(1L);          // a Long now (a BigDecimal before)
		assertThat(dao.getExpandedRaceList()).extracting(r -> r.get("EHARS_RACE_DESC")).containsExactly("Black", "White");
		assertThat(dao.getStdToRawColumnList("TB")).containsExactly(Map.of("RAW_COLUMN", "FIRSTNAME", "STD_COLUMN", "FNAME"));
		assertThat(dao.getUnitsOfMeasurement()).containsExactly("copies", "mL");
		assertThat(dao.getPSMessageReferenceData("GENDER")).containsExactly(Map.of("LOOKUP_CD", "F", "LOOKUP_NAME", "Female"), Map.of("LOOKUP_CD", "M", "LOOKUP_NAME", "Male"));
		assertThat(dao.getHIVCategories()).containsExactly(Map.of("HIV_CATEG", "1", "HIV_CATEG_DESC", "HIV, not AIDS"));
		assertThat(dao.getAIDSCategories()).containsExactly(Map.of("AIDS_CATEG", "3", "AIDS_CATEG_DESC", "AIDS"));

		Map<String, Boolean> filter = new HashMap<>();
		filter.put("filterRows", true);
		List<JSONObject> grouped = dao.getDispositionCodeValues(filter);                 // formerly LISTAGG per description and sort order
		assertThat(grouped).hasSize(2);
		assertThat(grouped.get(0)).containsEntry("DISPOSITION_CODE", "1,2").containsEntry("DISPOSITION_DESC", "Interviewed").containsEntry("SORT_ORDER", 1L);
		assertThat(grouped.get(1)).containsEntry("DISPOSITION_CODE", "3").containsEntry("DISPOSITION_DESC", "Not located");
		filter.put("filterRows", false);
		assertThat(dao.getDispositionCodeValues(filter)).hasSize(4).extracting(d -> d.get("DISPOSITION_DESC")).containsExactly("Deceased", "Interviewed", "Interviewed", "Not located");
	}

	@SuppressWarnings("unchecked")
	private static JSONObject json(Object... keysAndValues) {
		JSONObject object = new JSONObject();
		for (int i = 0; i < keysAndValues.length; i += 2) {
			object.put(keysAndValues[i], keysAndValues[i + 1]);
		}
		return object;
	}

	@Test
	void locatorAssociationsAndPersonTablesAsBefore() {
		assertThat(dao.getDocumentType(200)).isEqualTo("HIV_LAB");
		assertThat(dao.getSubDocumentType(200)).isEqualTo("VL");
		assertThat(dao.getSubDocumentType(100)).isNull();
		assertThat(dao.deleteDocFromDocLocator(100)).isEqualTo(1);
		assertThat(dao.deleteDocFromDocLocator(999)).isZero();
		Map<Object, Object> ids = new HashMap<>();
		ids.put("documentIds", new Long[] {200L, 300L});
		dao.inActivateDocument(ids);
		assertThat(statements.list("select IS_ACTIVE from DOCUMENT_LOCATOR order by DOCUMENT_ID", Rows.scalar(String.class))).containsExactly("N", "N", "N");

		Map<Object, Object> person = dao.getPersonDocData(100);
		assertThat(person).containsEntry("STATENO", "S1").containsEntry("PS_ASSESS_STATUS", BigDecimal.valueOf(3)).containsEntry("SURV_ASSIGNMENT_ID", BigDecimal.valueOf(55))
				.containsEntry("SURV_ASSESS_STATUS", BigDecimal.valueOf(2)).containsEntry("DISCARD_STATUS", "N");
		assertThat(dao.getPersonDocData(300)).isNull();                                   // disassociated
		assertThat(dao.getStatenoDetailsByDocumentId(200)).isEqualTo(Map.of("STATENO", "S1"));   // a null status is not put
		assertThat(dao.checkForActiveDocumentInPersonTables(100)).isEqualTo(1);
		assertThat(dao.checkForActiveDocumentInPersonTables(300)).isZero();

		Map<Object, Object> status = new HashMap<>();
		status.put("assignProcess", "4");
		status.put("p_user_id", 42);
		status.put("documentId", 200);
		assertThat(dao.updateSurvellianceAssignmentStatus(status)).isEqualTo(1);
		status.put("pS_ASSIGN_STATUS", 5);
		assertThat(dao.updateDocumentPsAssignStatus(status)).isEqualTo(1);
		Map<String, Object> row = statements.one("select SURV_ASSESS_STATUS, PS_ASSESS_STATUS, WORKER_ID from PERSON_DOC_ASSOCIATION where DOCUMENT_ID = 200", Rows.map());
		assertThat(row.get("SURV_ASSESS_STATUS")).hasToString("4");
		assertThat(row.get("PS_ASSESS_STATUS")).hasToString("5");
		assertThat(row.get("WORKER_ID")).hasToString("42");

		Map<Object, Object> end = new HashMap<>();
		end.put("associationStatus", "DISASSOC_EDIT");
		end.put("p_user_id", 43);
		end.put("p_documentId_list", List.of(100, 300));
		assertThat(dao.deleteEntryInPersonDocAssociation(end)).isEqualTo(1);             // 300 was already disassociated
		row = statements.one("select ASSOCIATION_STATUS_CODE, WORKER_ID, DISSASSOCIATION_DATE from PERSON_DOC_ASSOCIATION where DOCUMENT_ID = 100", Rows.map());
		assertThat(row).containsEntry("ASSOCIATION_STATUS_CODE", "DISASSOC_EDIT");
		assertThat((Timestamp) row.get("DISSASSOCIATION_DATE")).isCloseTo(new Timestamp(System.currentTimeMillis()), 60_000);
		assertThat(dao.getPersonDocData(100)).isNull();

		assertThat(dao.deleteEntryInDocumentPersonAttr(100)).isEqualTo(1);
		dao.deleteEntryInDocumentRiskTable(100);
		assertThat(dao.deleteEntryInDocumentRace(100)).isEqualTo(1);
		assertThat(statements.one("select count(*) from DOCUMENT_RISKS", Rows.scalar(Integer.class))).isEqualTo(1);
		assertThat(statements.one("select count(*) from DOCUMENT_RACE", Rows.scalar(Integer.class))).isEqualTo(1);

		Map<Object, Object> stateno = new HashMap<>();
		stateno.put("p_stateno", "S1");
		assertThat(dao.checkAndInactiveStateno(stateno)).isZero();                          // S1 still has document 200
		stateno.put("p_stateno", "S2");
		assertThat(dao.checkAndInactiveStateno(stateno)).isEqualTo(1);
		assertThat(statements.one("select FLAG_ACTIVE from PERSON where STATENO = 'S2'", Rows.scalar(String.class))).isEqualTo("N");
		stateno.put("p_reviewComments", "inactive");
		stateno.put("p_user_id", 42);
		dao.rejectMergePairForInactiveStateno(stateno);
		assertThat(statements.list("select STATUS_CD from NYEHMS_DUPLICATE_STATENOS order by DUPLICATION_ID", Rows.scalar(String.class))).containsExactly("REJECT", "REJECT", "MERGED");
		dao.rejectBadMergeStatneForInactiveStateno(stateno);
		assertThat(statements.list("select STATUS_CD, REVIEW_COMMENTS from NYEHMS_UNMERGING_STATENOS order by UNMERGE_ID", Rows.map()))
				.containsExactly(Map.of("STATUS_CD", "REJECT", "REVIEW_COMMENTS", "inactive"), Map.of("STATUS_CD", "DONE"));
	}

	@Test
	void messagesHistoryAddressesAndTheClericalPersonAsBefore() {
		Map<Object, Object> message = new HashMap<>();
		message.put("newDocumentId", 101);
		message.put("oldDocumentId", 100);
		assertThat(dao.updateTriggeringDocumentIdInNyehmsMessage(message)).isEqualTo(1);
		Map<String, Object> row = statements.one("select FOLLOW_UP_DOC_ID, EDITED_FLAG from NYEHMS_STD_MESSAGE_SENT where DOCUMENT_ID = 400", Rows.map());
		assertThat(row.get("FOLLOW_UP_DOC_ID")).hasToString("101");
		assertThat(row).containsEntry("EDITED_FLAG", "Y");
		message.put("p_user_id", "42");
		message.put("p_documentId", 101);
		dao.deleteIndicatePsMessage(message);
		row = statements.one("select DELETE_IND, REVIEW_COMMENTS, REVIEW_UID, REVIEW_DATE from NYEHMS_STD_MESSAGE_SENT where DOCUMENT_ID = 400", Rows.map());
		assertThat(row).containsEntry("DELETE_IND", "Y").containsEntry("REVIEW_COMMENTS", "Triggering Document Deleted").containsEntry("REVIEW_UID", "42");
		assertThat(row.get("REVIEW_DATE")).isNotNull();
		message.put("associationStatus", "DISASSOC_DEL");
		dao.deletePsMessageForTriggeringDocument(message);                                // document 400 follows 101 now: its association ends
		row = statements.one("select ASSOCIATION_STATUS_CODE, DISSASSOCIATION_DATE from PERSON_DOC_ASSOCIATION where DOCUMENT_ID = 400", Rows.map());
		assertThat(row).containsEntry("ASSOCIATION_STATUS_CODE", "DISASSOC_DEL");
		assertThat(row.get("DISSASSOCIATION_DATE")).isNotNull();

		Map<Object, Object> ridr = new HashMap<>();
		ridr.put("acrfDocId", 777);
		ridr.put("workerId", "jdoe");
		ridr.put("ridrDocumentId", 10);
		assertThat(dao.updateRIDRDocumentWithAcrf(ridr)).isEqualTo(1);
		ridr.put("ridrDocumentId", 11);
		assertThat(dao.updateRIDRDocumentWithAcrf(ridr)).isZero();                          // already resolved
		row = statements.one("select RESOLVED_DOC_ID, RESOLVED_UID, RESOLVED_DOC_TYPE, RESOLVED_DATE from CDC_RIDR_INFO where RIDR_ID = 10", Rows.map());
		assertThat(row.get("RESOLVED_DOC_ID")).hasToString("777");
		assertThat(row).containsEntry("RESOLVED_UID", "jdoe").containsEntry("RESOLVED_DOC_TYPE", "A");
		assertThat(row.get("RESOLVED_DATE")).isNotNull();

		Map<String, Integer> copy = new HashMap<>();
		copy.put("newDocumentId", 101);
		copy.put("oldDocumentId", 100);
		dao.createNonCaseWithNewDocument(copy);
		row = statements.one("select * from DELETED_DOC_HISTORY where TRIGGERING_DOC_ID = 101", Rows.map());
		assertThat(row.get("CREATE_ID")).hasToString("1");
		assertThat(row).containsEntry("CREATE_DATE", Timestamp.valueOf("2024-02-03 04:05:06")).containsEntry("FLAG_ACTIVE", "Y").containsEntry("REASON_CODE", "DUP")
				.containsEntry("COMMENTS", "twice");
		assertThat(dao.getDiscardedDocumentDetails(100)).isEqualTo(Map.of("CREATE_ID", "Doe, Jane", "CREATE_DATE", "02/03/2024", "REASON_DESC", "Duplicate"));
		assertThat(dao.getDiscardedDocumentDetails(999)).isNull();

		assertThat(dao.getTriggeringDocumentAddressIds(200)).isEqualTo(Map.of("PROVIDER_ADDRESS_ID", 12L, "FACILITY_ADDRESS_ID", 22L));
		assertThat(dao.getTriggeringDocumentAddressIds(100)).isEqualTo(Map.of("PROVIDER_ADDRESS_ID", 11L));   // a null is not put
		assertThat(dao.getOpenAssignmentsForTriggeringDocIds(100)).containsExactlyInAnyOrder(1, 2);
		assertThat(dao.getAssignmentAddessIds(1)).isEqualTo(Map.of("PROVIDER_ADDRESS_ID", 31L, "FACILITY_ADDRESS_ID", 41L));   // the current address only
		assertThat(dao.getAssignmentAddessIds(2)).isEqualTo(Map.of("FACILITY_ADDRESS_ID", 42L));
		assertThat(dao.getAssignmentAddessIds(3)).isNull();

		Map<String, Integer> ria = new HashMap<>();
		ria.put("NEW_DOCUMENTID", 201);
		ria.put("OLD_DOCUMENTID", 200);
		dao.updateRiaLabDocument(ria);
		assertThat(statements.list("select LAB_DOC_ID from NYEHMS_RIA_DOC order by DOCUMENT_ID", Rows.scalar(String.class))).containsExactly("201", "200");   // the merged one stays

		PersonViewVO person = dao.getClericalDocumentAsPerson(100);
		assertThat(person.getFNAME()).isEqualTo("Ann");
		assertThat(person.getMNAME()).isEqualTo("B");
		assertThat(person.getLNAME()).isEqualTo("Lee");
		assertThat(person.getSUFFIX()).isEqualTo("Jr");
		assertThat(person.getGENDER()).isEqualTo("Female");
		assertThat(person.getBirthSex()).isEqualTo("F");
		assertThat(person.getDateOfDeath()).isEqualTo("01/02/2020");
		assertThat(person.getDOB()).isEqualTo("05/06/1980");
		assertThat(person.getETHNICITY()).isEqualTo("Hispanic");
		assertThat(person.getPERSON_COUNTY()).isEqualTo("Albany");
		assertThat(person.getPERSON_PLUS4()).isEqualTo("1234");
		assertThat(person.getHomePhone()).isEqualTo("5551234");
		assertThat(person.getCellPhone()).isEqualTo("5551234");
		assertThat(person.getWorkPhone()).isEqualTo("5559999");
		PersonViewVO uncoded = dao.getClericalDocumentAsPerson(101);                       // the (+) joins: no gender, county or ethnicity name
		assertThat(uncoded.getFNAME()).isEqualTo("Bob");
		assertThat(uncoded.getGENDER()).isEmpty();
		assertThat(uncoded.getPERSON_COUNTY()).isEmpty();
		assertThat(dao.getClericalDocumentAsPerson(999)).isNull();
	}

	// ---- the message details result map, on sample rows -------------------------------------------------------------

	private static Row row(Object... labelsAndValues) {
		List<String> labels = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		for (int i = 0; i < labelsAndValues.length; i += 2) {
			labels.add((String) labelsAndValues[i]);
			values.add(labelsAndValues[i + 1]);
		}
		return new Row(labels, values);
	}

	@Test
	void messageDetailRowsFoldIntoOneMessagePerDocumentWithoutRepeats() {
		List<PsMessageRows.Part> parts = new ArrayList<>();
		for (Row row : List.of(
				row("DOCUMENT_ID", 400, "TRIGGER_MSG_TYPE", "NEW_DX", "TRIGGER_DOC_ID", 100, "CDESSCASEKEY_SENT", 9, "REVIEW_DATE", "01/02/2024", "STATENO", "S1",
						"RACE_WHITE", true, "CDESSCASEKEY", 9, "PS_DATA_DISPOSITION_CODE", 1, "DISPOSITION_DESC", "Interviewed", "MESSAGE_COMMENTS", "first", "COMMENTS_CREATE_ID", "Doe, Jane",
						"COMMENTS_CREATE_DATE", "01/02/2024 10:00:00 AM", "DOCUMENT_ID", 400),
				row("DOCUMENT_ID", 400, "TRIGGER_MSG_TYPE", "NEW_DX", "TRIGGER_DOC_ID", 100, "CDESSCASEKEY_SENT", 9, "REVIEW_DATE", "01/02/2024", "STATENO", "S1",
						"RACE_WHITE", true, "CDESSCASEKEY", 9, "PS_DATA_DISPOSITION_CODE", 1, "DISPOSITION_DESC", "Interviewed", "MESSAGE_COMMENTS", "second", "COMMENTS_CREATE_ID", "Doe, Jane",
						"COMMENTS_CREATE_DATE", "01/03/2024 10:00:00 AM", "DOCUMENT_ID", 400),
				row("DOCUMENT_ID", 500, "TRIGGER_MSG_TYPE", "VL", "TRIGGER_DOC_ID", 200, "CDESSCASEKEY_SENT", null, "REVIEW_DATE", null, "STATENO", null,
						"RACE_WHITE", null, "CDESSCASEKEY", null, "PS_DATA_DISPOSITION_CODE", null, "DISPOSITION_DESC", null, "MESSAGE_COMMENTS", null, "COMMENTS_CREATE_ID", null,
						"COMMENTS_CREATE_DATE", null, "DOCUMENT_ID", 500))) {
			parts.add(PsMessageRows.PART.map(row));
		}
		List<PSMessageVO> messages = PsMessageRows.group(parts);
		assertThat(messages).hasSize(2);
		PSMessageVO first = messages.get(0);
		assertThat(first.getDOCUMENT_ID()).isEqualTo(400);
		assertThat(first.getMessageSentVO().getTRIGGER_MSG_TYPE()).isEqualTo("NEW_DX");
		assertThat(first.getMessageSentVO().getTRIGGER_DOC_ID()).isEqualTo(100);
		assertThat(first.getMessageSentVO().getCDESSCASEKEY()).isEqualTo(9);
		assertThat(first.getMessageSentVO().getDOCUMENT_ID()).isNull();                      // not mapped by the result map (no auto-mapping of nested maps)
		assertThat(first.getMessageSentVO().getReviewComments()).extracting(c -> c.getCOMMENTS()).containsExactly("first", "second");
		assertThat(first.getMessageReturnVO().getSTATENO()).isEqualTo("S1");
		assertThat(first.getMessageReturnVO().isRACE_WHITE()).isTrue();
		assertThat(first.getMessageReturnVO().getUpdatedDispositionsList()).hasSize(1);       // the same disposition on both rows: once
		assertThat(first.getMessageReturnVO().getUpdatedDispositionsList().get(0).getDISPOSITION_CODE()).isEqualTo(1);
		PSMessageVO second = messages.get(1);
		assertThat(second.getDOCUMENT_ID()).isEqualTo(500);
		assertThat(second.getMessageSentVO().getTRIGGER_MSG_TYPE()).isEqualTo("VL");
		assertThat(second.getMessageSentVO().getReviewComments()).isEmpty();
		assertThat(second.getMessageReturnVO()).isNull();                                    // no returned message: the association is null, as before
		assertThat(PsMessageRows.PART.typeOf("DOCUMENT_ID", 0)).isEqualTo(int.class);
		assertThat(PsMessageRows.PART.typeOf("RACE_WHITE", 5)).isEqualTo(boolean.class);
		assertThat(PsMessageRows.PART.typeOf("MESSAGE_ENTERED_DATE_SENT", 3)).isEqualTo(java.sql.Date.class);   // the VO's property type
		assertThat(PsMessageRows.PART.typeOf("UNMAPPED_COLUMN", 7)).isNull();
	}

	// ---- the native statements and the procedure, without a database ----------------------------------------------

	private record Query(String sql, Object[] parameters) {
	}

	@SuppressWarnings("unchecked")
	private static Query capturedList(NativeStatements mocked) {
		ArgumentCaptor<String> sql = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Object[]> parameters = ArgumentCaptor.forClass(Object[].class);
		verify(mocked).list(sql.capture(), any(RowMapper.class), parameters.capture());
		return new Query(sql.getValue(), parameters.getValue());
	}

	@SuppressWarnings("unchecked")
	private static Query capturedOne(NativeStatements mocked) {
		ArgumentCaptor<String> sql = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Object[]> parameters = ArgumentCaptor.forClass(Object[].class);
		verify(mocked).one(sql.capture(), any(RowMapper.class), parameters.capture());
		return new Query(sql.getValue(), parameters.getValue());
	}

	private static Query capturedUpdate(NativeStatements mocked) {
		ArgumentCaptor<String> sql = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Object[]> parameters = ArgumentCaptor.forClass(Object[].class);
		verify(mocked).update(sql.capture(), parameters.capture());
		return new Query(sql.getValue(), parameters.getValue());
	}

	private static NativeStatements mockedStatements() {
		NativeStatements mocked = mock(NativeStatements.class);
		when(mocked.list(any(), any(), any(Object[].class))).thenReturn(new ArrayList<>());
		when(mocked.update(any(), any(Object[].class))).thenReturn(1);
		return mocked;
	}

	@Test
	void perCallTablesAreAllowListedAndTheSqlKept() {
		Map<Object, Object> map = new HashMap<>();
		map.put("tableName", "RAW_TB_DOC");
		map.put("statusCode", 17);
		map.put("document_ID", 100);
		NativeStatements mocked = mockedStatements();
		assertThat(new CommonDocLabsDAO(mocked, null).updateRawDocumentStatusCode(map)).isEqualTo(1);
		Query query = capturedUpdate(mocked);
		assertThat(query.sql()).isEqualTo("update RAW_TB_DOC set CURRENT_PROCESS_STATUS = ?1, PROCESS_DATE = SYSDATE where DOCUMENT_ID = ?2");
		assertThat(query.parameters()).containsExactly(17, 100);
		map.put("tableName", "STD_TB_DOC");
		mocked = mockedStatements();
		new CommonDocLabsDAO(mocked, null).updateStdDocumentStatusCode(map);
		assertThat(capturedUpdate(mocked).sql()).isEqualTo("update STD_TB_DOC set CURRENT_PROCESS_STATUS = ?1 where DOCUMENT_ID = ?2");
		mocked = mockedStatements();
		new CommonDocLabsDAO(mocked, null).deleteDocumentFromStdTable(map);
		assertThat(capturedUpdate(mocked).sql()).isEqualTo("delete from STD_TB_DOC where DOCUMENT_ID = ?1");
		map.put("tableName", "RAW_TB_DOC");                                               // a raw table is not a standardized one
		assertThatThrownBy(() -> new CommonDocLabsDAO(mockedStatements(), null).deleteDocumentFromStdTable(map)).isInstanceOf(InvalidDataAccessApiUsageException.class);
		map.put("tableName", "PERSON");
		assertThatThrownBy(() -> new CommonDocLabsDAO(mockedStatements(), null).updateRawDocumentStatusCode(map)).isInstanceOf(InvalidDataAccessApiUsageException.class);
		assertThatThrownBy(() -> new CommonDocLabsDAO(mockedStatements(), null).getDocData(map)).isInstanceOf(InvalidDataAccessApiUsageException.class);

		map.put("tableName", "std_hiv_eclrs_doc");
		mocked = mockedStatements();
		new CommonDocLabsDAO(mocked, null).getDocData(map);
		query = capturedOne(mocked);
		assertThat(query.sql()).isEqualTo("select * from std_hiv_eclrs_doc where DOCUMENT_ID =  ?1");
		assertThat(query.parameters()).containsExactly(100);

		Map<Object, Object> source = new HashMap<>();
		source.put("tableName", "STD_HIV_ECLRS_DOC");
		source.put("STATENO", null);                                                       // never given by the callers: the literal null, as ${STATENO} rendered it
		source.put("SOURCE_ID", "LAB_SRC_ID");
		source.put("documentId", 100);
		mocked = mockedStatements();
		new CommonDocLabsDAO(mocked, null).getStatenoAndSourceCodeFromStdDocData(source);
		assertThat(capturedOne(mocked).sql()).isEqualTo("select  null STATENO,  LAB_SRC_ID SOURCE_ID  from STD_HIV_ECLRS_DOC where DOCUMENT_ID =  ?1");
		source.put("SOURCE_ID", "LAB_SRC_ID from DUAL --");
		assertThatThrownBy(() -> new CommonDocLabsDAO(mockedStatements(), null).getStatenoAndSourceCodeFromStdDocData(source)).isInstanceOf(InvalidDataAccessApiUsageException.class);

		Map<Object, Object> column = new HashMap<>();
		column.put("tableName", "RAW_TB_DOC");
		column.put("targetColumn", "SERIALNO");
		column.put("refColumn", "DOCUMENT_ID");
		column.put("refValue", 100);
		mocked = mockedStatements();
		new CommonDocLabsDAO(mocked, null).getColumnDataFromTable(column);
		assertThat(capturedOne(mocked).sql()).isEqualTo("select SERIALNO from RAW_TB_DOC where DOCUMENT_ID =  ?1");
		column.put("targetColumn", "SERIALNO, SSN");
		assertThatThrownBy(() -> new CommonDocLabsDAO(mockedStatements(), null).getColumnDataFromTable(column)).isInstanceOf(InvalidDataAccessApiUsageException.class);

		Map<Object, Object> status = new HashMap<>();
		status.put("rawTableName", "RAW_HIV_ECLRS_DOC");
		status.put("processStatus", 14);
		status.put("workerId", "jdoe");
		status.put("documentIds", new Long[] {1L, 2L, 3L});
		mocked = mockedStatements();
		new CommonDocLabsDAO(mocked, null).updateDocumentsStatus(status);
		query = capturedUpdate(mocked);
		assertThat(query.sql()).isEqualTo("update RAW_HIV_ECLRS_DOC set CURRENT_PROCESS_STATUS = ?1, PROCESS_DATE = SYSDATE, REVIEW_UID = ?2, REVIEW_DATE = SYSDATE  where DOCUMENT_ID in (?3, ?4, ?5)");
		assertThat(query.parameters()).containsExactly(14, "jdoe", 1L, 2L, 3L);
		status.put("rawTableName", "STD_HIV_ECLRS_DOC");
		assertThatThrownBy(() -> new CommonDocLabsDAO(mockedStatements(), null).updateDocumentsStatus(status)).isInstanceOf(InvalidDataAccessApiUsageException.class);
	}

	@Test
	void oracleOnlyStatementsAndTheProcedureKeepTheirShape() {
		NativeStatements mocked = mockedStatements();
		Map<String, Object> types = new HashMap<>();
		types.put("p_module_id", "16");
		types.put("p_worker_id", 42);
		new CommonDocLabsDAO(mocked, null).getDocumentTypes(types);
		Query query = capturedList(mocked);
		assertThat(query.sql()).contains("FROM TABLE(get_user_permissions(?1, ?2))");
		assertThat(query.parameters()).containsExactly("16", 42);

		mocked = mockedStatements();
		new CommonDocLabsDAO(mocked, null).getNycLabDoc(200);
		query = capturedOne(mocked);
		assertThat(query.sql()).contains("r1.PERSON_ADDRESS1 || ' ' || r1.PERSON_ADDRESS2 rawAddress").contains("FROM RAW_NYC_EHARS_LAB_DOC r1")
				.contains("ON (std.document_id = r1.document_id and  r1.DOCUMENT_ID=?1)").contains("LEFT OUTER JOIN PERSON_DOC_ASSOCIATION PDOC");
		assertThat(query.parameters()).containsExactly(200);

		mocked = mockedStatements();
		Map<Object, Object> message = new HashMap<>();
		message.put("documentId", 100);
		message.put("documentType", "PS_MSG_LIST");
		assertThat(new CommonDocLabsDAO(mocked, null).getPSMessageDetails(message)).isEmpty();
		query = capturedList(mocked);
		assertThat(query.sql()).contains("from NYEHMS_STD_MESSAGE_SENT where\n         FOLLOW_UP_DOC_ID =  ?1").contains("se.document_id = comments.document_id and\n         se.FOLLOW_UP_DOC_ID =  ?1")
				.contains("AND NVL (se.PROCESS_IND, 'XX') NOT IN ('N', 'E')\n       and se.FOLLOW_UP_DOC_ID =  ?1").doesNotContain("%s");
		assertThat(query.parameters()).containsExactly(100);
		mocked = mockedStatements();
		message.put("documentType", "PS_MSG");
		new CommonDocLabsDAO(mocked, null).getPSMessageDetails(message);
		assertThat(capturedList(mocked).sql()).contains("and se.DOCUMENT_ID =  ?1").doesNotContain("FOLLOW_UP_DOC_ID =  ?1");

		NativeStatements procedures = mock(NativeStatements.class);
		when(procedures.call(any(), any(CallParam[].class))).thenReturn(Arrays.asList(null, null, null, null, 0, "moved", null));
		Map<Object, Object> move = new HashMap<>();
		move.put("p_documentId", "100");
		move.put("p_document_type", "TB");
		move.put("p_user_id", "42");
		move.put("p_stateno", null);
		move.put("p_called_from", "EDIT");
		new CommonDocLabsDAO(procedures, null).moveStdDocumentToDocAttrTables(move);
		ArgumentCaptor<String> procedure = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<CallParam[]> parameters = ArgumentCaptor.forClass(CallParam[].class);
		verify(procedures).call(procedure.capture(), parameters.capture());
		assertThat(procedure.getValue()).isEqualTo("PKG_DOCUMENT_MANAGEMENT.pr_insert_raw_to_std");
		assertThat(parameters.getValue()).hasSize(7);
		assertThat(parameters.getValue()[3].type()).isEqualTo(String.class);
		assertThat(parameters.getValue()[4].mode()).isEqualTo(ParameterMode.OUT);
		assertThat(parameters.getValue()[6].value()).isEqualTo("EDIT");
		assertThat(move).containsEntry("p_code_out", 0).containsEntry("p_msg_out", "moved");
	}
}
