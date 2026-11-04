package gov.ny.state.doh.bhae.nyehms.web.spring.dao.administration;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.longValue;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.text;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.timestamp;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.PortalSystemMessage;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.SystemMessage;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.SystemMessageArchive;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration.ActiveSystemMessageView;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration.InactiveSystemMessageView;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration.NyehmsSystemMessage;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration.PortalModuleOutage;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.SystemMessageHistoryRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.administration.ActiveSystemMessageViewRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.administration.InactiveSystemMessageViewRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.administration.NyehmsSystemMessageRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.administration.PortalModuleOutageRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.administration.ProviderPortalModuleRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.administration.SystemMessageHistoryViewRepository;
import lombok.RequiredArgsConstructor;

/**
 * System messages and provider portal outage messages (formerly a MyBatis mapper with the same name and methods),
 * through Spring Data repositories; the entities are turned into the screens' VOs here.
 * <p>
 * Dates are shown as the former TO_CHAR(..., 'MM/DD/YYYY HH:MI:SS AM') showed them. The dates the former SQL set with
 * SYSDATE, and the "now" of the active / in-progress conditions, are the application server's clock.
 */
@Repository
@RequiredArgsConstructor
public class SystemMessageDAO {

	/** TO_CHAR(x, 'MM/DD/YYYY HH:MI:SS AM') */
	private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a", Locale.US);

	/** TO_CHAR(x, 'MM/DD/YYYY') */
	private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US);

	private final ActiveSystemMessageViewRepository activeMessages;
	private final InactiveSystemMessageViewRepository inactiveMessages;
	private final SystemMessageHistoryViewRepository messageHistory;
	private final NyehmsSystemMessageRepository systemMessages;
	private final PortalModuleOutageRepository portalMessages;
	private final ProviderPortalModuleRepository portalModules;

	public List<SystemMessage> getActiveSystemMessages() {
		return activeMessages.findAll().stream().map(SystemMessageDAO::toSystemMessage).collect(toList());
	}

	public List<SystemMessage> getInActiveSystemMessages() {
		return inactiveMessages.findAll().stream().map(SystemMessageDAO::toSystemMessage).collect(toList());
	}

	/** The active messages already in effect (only their text, as before). */
	public List<SystemMessage> getInProgressActiveSystemMessages() {
		return activeMessages.findByEffectiveStartDateBefore(now()).stream().map(view -> {
			SystemMessage message = new SystemMessage();
			if (view.getMsgBody() != null) {
				message.setMessageBody(view.getMsgBody());
			}
			return view.getMsgBody() == null ? null : message;          // a row without values was null, as before
		}).collect(toList());
	}

	/**
	 * Inserts a system message.
	 *
	 * @param values effectiveStartDate, effectiveEndDate (dates), messageBody, createdBy, lastUpdatedBy
	 */
	@Transactional
	public int saveSystemMessageDetails(Map<Object, Object> values) {
		systemMessages.saveAndFlush(NyehmsSystemMessage.builder()
				.effectiveStartDate(timestamp(values.get("effectiveStartDate")))
				.effectiveEndDate(timestamp(values.get("effectiveEndDate")))
				.msgBody(text(values.get("messageBody")))
				.createdBy(longValue(values.get("createdBy")))
				.lastUpdatedBy(longValue(values.get("lastUpdatedBy")))
				.lastUpdatedDate(now())
				.build());
		return 1;
	}

	/**
	 * Changes a system message (LAST_UPDATED_BY becomes createdBy, as before).
	 *
	 * @param values messageId, effectiveStartDate, effectiveEndDate, createdBy, deleted, messageBody
	 * @return 1, or 0 if there is no such message
	 */
	@Transactional
	public int updateSystemMessageDetails(Map<Object, Object> values) {
		Optional<NyehmsSystemMessage> message = systemMessages.findById(longValue(values.get("messageId")));
		message.ifPresent(m -> m.change(timestamp(values.get("effectiveStartDate")), timestamp(values.get("effectiveEndDate")),
				text(values.get("messageBody")), text(values.get("deleted")), longValue(values.get("createdBy")), now()));
		return message.isPresent() ? 1 : 0;
	}

	/** The earlier versions of a message: one archive per message id, its versions in row order. */
	public List<SystemMessageArchive> getSystemMessageArchives(SystemMessage systemMessage) {
		Map<Long, SystemMessageArchive> archives = new LinkedHashMap<>();
		for (SystemMessageHistoryRow row : messageHistory.findByMsgId(systemMessage.getMessageId())) {
			SystemMessageArchive archive = archives.computeIfAbsent(row.msgId(), id -> {
				SystemMessageArchive newArchive = new SystemMessageArchive();
				newArchive.setMessageId(id);
				newArchive.setSystemMessageArchives(new ArrayList<>());
				return newArchive;
			});
			archive.getSystemMessageArchives().add(toSystemMessage(row));
		}
		return new ArrayList<>(archives.values());
	}

	public List<PortalSystemMessage> getActivePortalSystemMessages() {
		return portalMessages.findByFlagDeletedAndEndDateIsNullOrFlagDeletedAndEndDateAfter("N", "N", now()).stream()
				.map(SystemMessageDAO::toPortalMessage).collect(toList());
	}

	public List<PortalSystemMessage> getInActivePortalSystemMessages() {
		return portalMessages.findByFlagDeletedOrEndDateBefore("Y", now()).stream().map(SystemMessageDAO::toPortalMessage).collect(toList());
	}

	/** The portal modules by code: PP_MODULE_CD, PP_MODULE_NAME. */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getPortalModuleNames() {
		return portalModules.findAll(Sort.by("ppModuleCd")).stream().map(module -> {
			JSONObject row = new JSONObject();
			putIfSet(row, "PP_MODULE_CD", module.getPpModuleCd());
			putIfSet(row, "PP_MODULE_NAME", module.getPpModuleName());
			return row.isEmpty() ? null : row;
		}).collect(toList());
	}

	/**
	 * Inserts a portal outage message.
	 *
	 * @param values ADMIN, PRF_DE, OOC_INQ, DNLTC_INQ, SNP_ID, AIRS_EPI ("Y" or ""), startDate, endDate (a date or ""),
	 *        displayMsg, create_by, update_by
	 */
	@Transactional
	public int savePortalSystemMessageDetails(Map<Object, Object> values) {
		Timestamp now = now();
		portalMessages.saveAndFlush(portalMessage(values).createBy(text(values.get("create_by"))).createDate(now)
				.updateBy(text(values.get("update_by"))).updateDate(now).build());
		return 1;
	}

	/**
	 * Changes a portal outage message, or (flagDeleted other than 'N') only sets its deleted flag, as before.
	 *
	 * @return 1, or 0 if there is no such message
	 */
	@Transactional
	public int updatePortalSystemMessageDetails(Map<Object, Object> values) {
		Optional<PortalModuleOutage> message = portalMessages.findById(longValue(values.get("msgId")));
		Object flagDeleted = values.get("flagDeleted");
		String updateBy = text(values.get("update_by"));
		if (flagDeleted != null && "N".equals(flagDeleted.toString())) {
			message.ifPresent(m -> m.change(portalMessage(values).build(), updateBy, now()));
		} else {
			message.ifPresent(m -> m.flagDeleted(text(flagDeleted), updateBy, now()));
		}
		return message.isPresent() ? 1 : 0;
	}

	/** The module flags, dates and text of a portal outage message from the screen's values. */
	private static PortalModuleOutage.PortalModuleOutageBuilder portalMessage(Map<Object, Object> values) {
		return PortalModuleOutage.builder()
				.admin(emptyAsNull(values.get("ADMIN")))
				.prfDe(emptyAsNull(values.get("PRF_DE")))
				.oocInq(emptyAsNull(values.get("OOC_INQ")))
				.dnltcInq(emptyAsNull(values.get("DNLTC_INQ")))
				.snpId(emptyAsNull(values.get("SNP_ID")))
				.airsEpi(emptyAsNull(values.get("AIRS_EPI")))
				.startDate(timestamp(values.get("startDate")))
				.endDate(timestamp(values.get("endDate")))
				.displayMsg(emptyAsNull(values.get("displayMsg")));
	}

	private static SystemMessage toSystemMessage(ActiveSystemMessageView view) {
		SystemMessage message = new SystemMessage();
		boolean set = false;
		if (view.getMsgId() != null) { message.setMessageId(view.getMsgId()); set = true; }
		if (view.getMsgBody() != null) { message.setMessageBody(view.getMsgBody()); set = true; }
		if (view.getCreatedBy() != null) { message.setCreatedBy(view.getCreatedBy().toString()); set = true; }
		if (view.getCreatedByName() != null) { message.setCreatedByName(view.getCreatedByName()); set = true; }
		if (view.getEffectiveStartDate() != null) { message.setEffectiveStartDate(dateTime(view.getEffectiveStartDate())); set = true; }
		if (view.getEffectiveEndDate() != null) { message.setEffectiveEndDate(dateTime(view.getEffectiveEndDate())); set = true; }
		if (firstChar(view.getFlagHistoryExist()) != null) { message.setHistoryFlag(firstChar(view.getFlagHistoryExist())); set = true; }
		if (view.getLastUpdatedDate() != null) { message.setLastUpdatedDate(dateTime(view.getLastUpdatedDate())); set = true; }
		if (view.getLastUpdatedBy() != null) { message.setLastUpdatedBy(view.getLastUpdatedBy()); set = true; }
		if (view.getLastUpdatedByName() != null) { message.setLastUpdatedByName(view.getLastUpdatedByName()); set = true; }
		if (view.getLastUpdatedShortName() != null) { message.setLastUpdatedByShortName(view.getLastUpdatedShortName()); set = true; }
		if (view.getCreatedByShortName() != null) { message.setCreatedByShortName(view.getCreatedByShortName()); set = true; }
		return set ? message : null;
	}

	private static SystemMessage toSystemMessage(InactiveSystemMessageView view) {
		SystemMessage message = new SystemMessage();
		boolean set = false;
		if (view.getMsgId() != null) { message.setMessageId(view.getMsgId()); set = true; }
		if (view.getMsgBody() != null) { message.setMessageBody(view.getMsgBody()); set = true; }
		if (view.getCreatedByName() != null) { message.setCreatedByName(view.getCreatedByName()); set = true; }
		if (view.getEffectiveStartDate() != null) { message.setEffectiveStartDate(dateTime(view.getEffectiveStartDate())); set = true; }
		if (view.getEffectiveEndDate() != null) { message.setEffectiveEndDate(dateTime(view.getEffectiveEndDate())); set = true; }
		if (firstChar(view.getFlagDeleted()) != null) { message.setDeleted(firstChar(view.getFlagDeleted())); set = true; }
		if (firstChar(view.getFlagHistoryExist()) != null) { message.setHistoryFlag(firstChar(view.getFlagHistoryExist())); set = true; }
		if (view.getLastUpdatedDate() != null) { message.setLastUpdatedDate(dateTime(view.getLastUpdatedDate())); set = true; }
		if (view.getLastUpdatedByName() != null) { message.setLastUpdatedByName(view.getLastUpdatedByName()); set = true; }
		if (view.getLastUpdatedShortName() != null) { message.setLastUpdatedByShortName(view.getLastUpdatedShortName()); set = true; }
		if (view.getCreatedByShortName() != null) { message.setCreatedByShortName(view.getCreatedByShortName()); set = true; }
		return set ? message : null;
	}

	private static SystemMessage toSystemMessage(SystemMessageHistoryRow row) {
		SystemMessage message = new SystemMessage();
		boolean set = false;
		if (row.msgId() != null) { message.setMessageId(row.msgId()); set = true; }
		if (row.msgBody() != null) { message.setMessageBody(row.msgBody()); set = true; }
		if (row.createdByName() != null) { message.setCreatedByName(row.createdByName()); set = true; }
		if (row.effectiveStartDate() != null) { message.setEffectiveStartDate(dateTime(row.effectiveStartDate())); set = true; }
		if (row.effectiveEndDate() != null) { message.setEffectiveEndDate(dateTime(row.effectiveEndDate())); set = true; }
		if (firstChar(row.flagDeleted()) != null) { message.setDeleted(firstChar(row.flagDeleted())); set = true; }
		if (row.lastUpdatedDate() != null) { message.setLastUpdatedDate(dateTime(row.lastUpdatedDate())); set = true; }
		if (row.lastUpdatedByName() != null) { message.setLastUpdatedByName(row.lastUpdatedByName()); set = true; }
		if (row.lastUpdatedShortName() != null) { message.setLastUpdatedByShortName(row.lastUpdatedShortName()); set = true; }
		if (row.createdByShortName() != null) { message.setCreatedByShortName(row.createdByShortName()); set = true; }
		return set ? message : null;
	}

	private static PortalSystemMessage toPortalMessage(PortalModuleOutage outage) {
		PortalSystemMessage message = new PortalSystemMessage();
		if (outage.getMsgId() != null) {
			message.setMsgId(outage.getMsgId());
		}
		message.setModule_name(moduleName(outage));
		if (firstChar(outage.getAdmin()) != null) message.setAdmin(firstChar(outage.getAdmin()));
		if (firstChar(outage.getPrfDe()) != null) message.setPrf_de(firstChar(outage.getPrfDe()));
		if (firstChar(outage.getOocInq()) != null) message.setOoc_inq(firstChar(outage.getOocInq()));
		if (firstChar(outage.getDnltcInq()) != null) message.setDnltc_inq(firstChar(outage.getDnltcInq()));
		if (firstChar(outage.getSnpId()) != null) message.setSnp_id(firstChar(outage.getSnpId()));
		if (firstChar(outage.getAirsEpi()) != null) message.setAirs_epi(firstChar(outage.getAirsEpi()));
		if (outage.getStartDate() != null) message.setStart_date(dateTime(outage.getStartDate()));
		if (outage.getEndDate() != null) message.setEnd_date(dateTime(outage.getEndDate()));
		if (outage.getDisplayMsg() != null) message.setDisplay_msg(outage.getDisplayMsg());
		if (firstChar(outage.getFlagDeleted()) != null) message.setFlag_deleted(firstChar(outage.getFlagDeleted()));
		if (outage.getCreateBy() != null) message.setCreate_by(outage.getCreateBy());
		if (outage.getCreateDate() != null) message.setCreate_date(DATE.format(outage.getCreateDate().toLocalDateTime()));
		message.setUpdate_by(outage.getUpdateBy() != null ? outage.getUpdateBy() : " ");         // COALESCE(UPDATE_BY, ' ')
		if (outage.getUpdateDate() != null) message.setUpdate_date(DATE.format(outage.getUpdateDate().toLocalDateTime()));
		return message;
	}

	/** The first module flagged 'Y', ' ' if none (the former CASE). */
	private static String moduleName(PortalModuleOutage outage) {
		if ("Y".equals(outage.getAdmin())) return "ADMIN";
		if ("Y".equals(outage.getPrfDe())) return "PRF_DE";
		if ("Y".equals(outage.getOocInq())) return "OOC_INQ";
		if ("Y".equals(outage.getDnltcInq())) return "DNLTC_INQ";
		if ("Y".equals(outage.getSnpId())) return "SNP_ID";
		if ("Y".equals(outage.getAirsEpi())) return "AIRS_EPI";
		return " ";
	}

	private static String dateTime(Timestamp time) {
		return DATE_TIME.format(time.toLocalDateTime());
	}

	/** A one-character flag as the char property read it (the first character; null for no text). */
	private static Character firstChar(String text) {
		return text == null || text.isEmpty() ? null : text.charAt(0);
	}

	/** '' was stored as NULL by Oracle. */
	private static String emptyAsNull(Object value) {
		String text = text(value);
		return text == null || text.isEmpty() ? null : text;
	}

	@SuppressWarnings("unchecked")
	private static void putIfSet(JSONObject row, String key, Object value) {
		if (value != null) {
			row.put(key, value);
		}
	}

	private static Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}

	private static <T> java.util.stream.Collector<T, ?, List<T>> toList() {
		return java.util.stream.Collectors.toCollection(ArrayList::new);
	}
}
