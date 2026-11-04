package gov.ny.state.doh.bhae.nyehms.web.spring.dao.administration;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.longValue;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.text;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.timestamp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.PortalSystemMessage;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.SystemMessage;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.SystemMessageArchive;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration.NyehmsSystemMessage;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration.PortalModuleOutage;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import lombok.RequiredArgsConstructor;

/**
 * System messages and provider portal outage messages (formerly a MyBatis mapper with the same name and methods),
 * in JPQL over the entities of {@code entity.administration}.
 * <p>
 * The select items are named after the properties of the former result maps, so the VOs are filled as before; the
 * dates are formatted as the former TO_CHAR(..., 'MM/DD/YYYY HH:MI:SS AM') did.
 */
@Repository
@RequiredArgsConstructor
public class SystemMessageDAO {

	/** TO_CHAR(x, 'MM/DD/YYYY HH:MI:SS AM') */
	private static final String DATE_TIME = "'MM/dd/yyyy hh:mm:ss a'";

	private static final String ACTIVE_MESSAGES = """
			select v.msgId as messageId, v.msgBody as messageBody, v.createdBy as createdBy, v.createdByName as createdByName,
			format(v.effectiveStartDate as %1$s) as effectiveStartDate, format(v.effectiveEndDate as %1$s) as effectiveEndDate,
			v.flagHistoryExist as historyFlag, format(v.lastUpdatedDate as %1$s) as lastUpdatedDate, v.lastUpdatedBy as lastUpdatedBy,
			v.lastUpdatedByName as lastUpdatedByName, v.lastUpdatedShortName as lastUpdatedByShortName, v.createdByShortName as createdByShortName
			from ActiveSystemMessageView v
			""".formatted(DATE_TIME);

	private static final String IN_PROGRESS_MESSAGES = """
			select v.msgBody as messageBody from ActiveSystemMessageView v where sysdate > v.effectiveStartDate
			""";

	private static final String INACTIVE_MESSAGES = """
			select v.msgId as messageId, v.msgBody as messageBody, v.createdByName as createdByName,
			format(v.effectiveStartDate as %1$s) as effectiveStartDate, format(v.effectiveEndDate as %1$s) as effectiveEndDate,
			v.flagDeleted as deleted, v.flagHistoryExist as historyFlag, format(v.lastUpdatedDate as %1$s) as lastUpdatedDate,
			v.lastUpdatedByName as lastUpdatedByName, v.lastUpdatedShortName as lastUpdatedByShortName, v.createdByShortName as createdByShortName
			from InactiveSystemMessageView v
			""".formatted(DATE_TIME);

	private static final String MESSAGE_HISTORY = """
			select v.msgId as messageId, v.msgBody as messageBody, v.createdByName as createdByName,
			format(v.effectiveStartDate as %1$s) as effectiveStartDate, format(v.effectiveEndDate as %1$s) as effectiveEndDate,
			v.flagDeleted as deleted, format(v.lastUpdatedDate as %1$s) as lastUpdatedDate, v.lastUpdatedByName as lastUpdatedByName,
			v.lastUpdatedShortName as lastUpdatedByShortName, v.createdByShortName as createdByShortName
			from SystemMessageHistoryView v where v.msgId = ?1
			""".formatted(DATE_TIME);

	private static final String UPDATE_MESSAGE = """
			update NyehmsSystemMessage m set m.effectiveStartDate = ?1, m.lastUpdatedBy = ?2, m.lastUpdatedDate = sysdate,
			m.effectiveEndDate = ?3, m.flagDeleted = ?4, m.msgBody = ?5 where m.msgId = ?6
			""";

	/** The portal outage messages; the condition follows. */
	private static final String PORTAL_MESSAGES = """
			select o.msgId as msgId,
			case when o.admin = 'Y' then 'ADMIN' when o.prfDe = 'Y' then 'PRF_DE' when o.oocInq = 'Y' then 'OOC_INQ'
			when o.dnltcInq = 'Y' then 'DNLTC_INQ' when o.snpId = 'Y' then 'SNP_ID' when o.airsEpi = 'Y' then 'AIRS_EPI' else ' ' end as module_name,
			o.admin as admin, o.prfDe as prf_de, o.oocInq as ooc_inq, o.dnltcInq as dnltc_inq, o.snpId as snp_id, o.airsEpi as airs_epi,
			format(o.startDate as %1$s) as start_date, format(o.endDate as %1$s) as end_date, o.displayMsg as display_msg,
			o.flagDeleted as flag_deleted, o.createBy as create_by, format(o.createDate as 'MM/dd/yyyy') as create_date,
			coalesce(o.updateBy, ' ') as update_by, format(o.updateDate as 'MM/dd/yyyy') as update_date
			from PortalModuleOutage o
			""".formatted(DATE_TIME);

	private static final String PORTAL_MODULES = """
			select m.ppModuleCd as PP_MODULE_CD, m.ppModuleName as PP_MODULE_NAME from ProviderPortalModule m order by m.ppModuleCd
			""";

	private static final String UPDATE_PORTAL_MESSAGE = """
			update PortalModuleOutage o set o.admin = ?1, o.prfDe = ?2, o.oocInq = ?3, o.dnltcInq = ?4, o.snpId = ?5, o.airsEpi = ?6,
			o.startDate = ?7, o.endDate = ?8, o.displayMsg = ?9, o.updateBy = ?10, o.updateDate = sysdate where o.msgId = ?11
			""";

	private static final String DELETE_PORTAL_MESSAGE = """
			update PortalModuleOutage o set o.updateBy = ?1, o.updateDate = sysdate, o.flagDeleted = ?2 where o.msgId = ?3
			""";

	private final JpqlStatements statements;

	public List<SystemMessage> getActiveSystemMessages() {
		return statements.list(ACTIVE_MESSAGES, Rows.bean(SystemMessage.class));
	}

	public List<SystemMessage> getInActiveSystemMessages() {
		return statements.list(INACTIVE_MESSAGES, Rows.bean(SystemMessage.class));
	}

	public List<SystemMessage> getInProgressActiveSystemMessages() {
		return statements.list(IN_PROGRESS_MESSAGES, Rows.bean(SystemMessage.class));
	}

	/**
	 * Inserts a system message.
	 *
	 * @param values effectiveStartDate, effectiveEndDate (dates), messageBody, createdBy, lastUpdatedBy
	 */
	public int saveSystemMessageDetails(Map<Object, Object> values) {
		return statements.insert(NyehmsSystemMessage.builder()
				.effectiveStartDate(timestamp(values.get("effectiveStartDate")))
				.effectiveEndDate(timestamp(values.get("effectiveEndDate")))
				.msgBody(text(values.get("messageBody")))
				.createdBy(longValue(values.get("createdBy")))
				.lastUpdatedBy(longValue(values.get("lastUpdatedBy")))
				.build());
	}

	/**
	 * Changes a system message (LAST_UPDATED_BY is set to createdBy, as before).
	 *
	 * @param values effectiveStartDate, effectiveEndDate, createdBy, deleted, messageBody, messageId
	 */
	public int updateSystemMessageDetails(Map<Object, Object> values) {
		return statements.update(UPDATE_MESSAGE, timestamp(values.get("effectiveStartDate")), longValue(values.get("createdBy")),
				timestamp(values.get("effectiveEndDate")), text(values.get("deleted")), text(values.get("messageBody")),
				longValue(values.get("messageId")));
	}

	/**
	 * The earlier versions of a message, grouped by message id as the former nested result map grouped them (one
	 * archive per MSG_ID, its versions in row order).
	 */
	public List<SystemMessageArchive> getSystemMessageArchives(SystemMessage systemMessage) {
		Map<Long, SystemMessageArchive> archives = new LinkedHashMap<>();
		for (SystemMessage version : statements.list(MESSAGE_HISTORY, Rows.bean(SystemMessage.class), systemMessage.getMessageId())) {
			SystemMessageArchive archive = archives.computeIfAbsent(version.getMessageId(), id -> {
				SystemMessageArchive newArchive = new SystemMessageArchive();
				newArchive.setMessageId(id);
				newArchive.setSystemMessageArchives(new ArrayList<>());
				return newArchive;
			});
			archive.getSystemMessageArchives().add(version);
		}
		return new ArrayList<>(archives.values());
	}

	public List<PortalSystemMessage> getActivePortalSystemMessages() {
		return statements.list(PORTAL_MESSAGES + " where o.flagDeleted = 'N' and (o.endDate is null or o.endDate > sysdate)",
				Rows.bean(PortalSystemMessage.class));
	}

	public List<PortalSystemMessage> getInActivePortalSystemMessages() {
		return statements.list(PORTAL_MESSAGES + " where (o.flagDeleted = 'Y' or sysdate > o.endDate)", Rows.bean(PortalSystemMessage.class));
	}

	public List<JSONObject> getPortalModuleNames() {
		return statements.list(PORTAL_MODULES, Rows.json());
	}

	/**
	 * Inserts a portal outage message.
	 *
	 * @param values ADMIN, PRF_DE, OOC_INQ, DNLTC_INQ, SNP_ID, AIRS_EPI ("Y" or ""), startDate, endDate (a date or ""),
	 *        displayMsg, create_by, update_by
	 */
	public int savePortalSystemMessageDetails(Map<Object, Object> values) {
		return statements.insert(PortalModuleOutage.builder()
				.admin(text(values.get("ADMIN")))
				.prfDe(text(values.get("PRF_DE")))
				.oocInq(text(values.get("OOC_INQ")))
				.dnltcInq(text(values.get("DNLTC_INQ")))
				.snpId(text(values.get("SNP_ID")))
				.airsEpi(text(values.get("AIRS_EPI")))
				.startDate(timestamp(values.get("startDate")))
				.endDate(timestamp(values.get("endDate")))
				.displayMsg(text(values.get("displayMsg")))
				.createBy(text(values.get("create_by")))
				.updateBy(text(values.get("update_by")))
				.build());
	}

	/**
	 * Changes a portal outage message, or (flagDeleted other than 'N') only marks it deleted, as the former
	 * {@code <choose>} did.
	 */
	public int updatePortalSystemMessageDetails(Map<Object, Object> values) {
		Object flagDeleted = values.get("flagDeleted");
		if (flagDeleted != null && "N".equals(flagDeleted.toString())) {
			return statements.update(UPDATE_PORTAL_MESSAGE, text(values.get("ADMIN")), text(values.get("PRF_DE")), text(values.get("OOC_INQ")),
					text(values.get("DNLTC_INQ")), text(values.get("SNP_ID")), text(values.get("AIRS_EPI")), timestamp(values.get("startDate")),
					timestamp(values.get("endDate")), text(values.get("displayMsg")), text(values.get("update_by")), longValue(values.get("msgId")));
		}
		return statements.update(DELETE_PORTAL_MESSAGE, text(values.get("update_by")), text(flagDeleted), longValue(values.get("msgId")));
	}
}
