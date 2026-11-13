package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

/**
 * The SQL of the {@link HITDocumentDAO} statements that stay native, taken unchanged from the former MyBatis mapper;
 * why each one stays native is said at its DAO method.
 */
final class HITDocumentSql {

	private HITDocumentSql() {
	}

	/** The SQL of {@code insertHitMessageCount}, as the former mapper had it. */
	static final String INSERT_HIT_MESSAGE_COUNT = """
			INSERT INTO hit_message_counts
						(
						     hit_id
						   , type
						   , received_date
						   , total
						   , distinct_count
						   , duplicates
						   , max_uphn_tranx_in_id
						)
						WITH hit_data AS
						(
							select 'CCD' type, trunc(receivedtime) receivedday,
							count(*) tot, count(distinct payloadname) dist,count(*)-count(distinct payloadname) dupes,
							max(uphn_tranx_in_id) max_uphn_tranx_in_id
							from uphn_tranx_in
							where uphn_tranx_in_id > (SELECT MAX(MAX_UPHN_TRANX_IN_ID) from HIT_MESSAGE_COUNTS where TYPE = 'CCD')
							and topic = 'DEERPROD'
							and trunc(receivedtime) > (select MAX(RECEIVED_DATE) from hit_message_counts where TYPE = 'CCD')  and trunc(receivedtime) <= SYSDATE - 1
							group by trunc(receivedtime)

						union

							select 'HL7' type, trunc(receivedtime) receivedday,
							count(*) tot, count(distinct payloadname) dist,count(*)-count(distinct payloadname) dupes,
							max(uphn_tranx_in_id) max_uphn_tranx_in_id
							from uphn_tranx_in
							where uphn_tranx_in_id > (SELECT MAX(MAX_UPHN_TRANX_IN_ID) from HIT_MESSAGE_COUNTS where TYPE = 'HL7')
							and topic = 'DEERHL7PROD'
							and payloadname like '%.txt'
							and trunc(receivedtime) > (select MAX(RECEIVED_DATE) from hit_message_counts where TYPE = 'HL7')  and trunc(receivedtime) <= SYSDATE - 1
							group by trunc(receivedtime)

						union

							select 'EXP' type, trunc(receivedtime) receivedday,
							count(*) tot, count(distinct payloadname) dist,count(*)-count(distinct payloadname) dupes,
							max(uphn_tranx_in_id) max_uphn_tranx_in_id
							from uphn_tranx_in
							where uphn_tranx_in_id > (SELECT MAX(MAX_UPHN_TRANX_IN_ID) from HIT_MESSAGE_COUNTS where TYPE = 'EXP')
							and topic = 'DEERHL7PROD'
							and trunc(receivedtime) > (select MAX(RECEIVED_DATE) from hit_message_counts where TYPE = 'EXP')  and trunc(receivedtime) <= SYSDATE - 1
							and payloadname like '%.csv'
							group by trunc(receivedtime)

							order by receivedday, type
						)
						SELECT hit_message_count_seq.nextval hit_id, a.type, a.receivedday, a.tot, a.dist, a.dupes, a.max_uphn_tranx_in_id
						FROM hit_data a
			""";

	/** The SQL of {@code getCountsForTodayMail}, as the former mapper had it. */
	static final String COUNTS_FOR_TODAY_MAIL = """
			WITH hits_recd AS
			                (
			                        SELECT a.*, ROW_NUMBER() OVER (PARTITION BY a.type ORDER BY a.received_date DESC) rn
			                        FROM hit_message_counts a
			                        --WHERE a.received_date < trunc(sysdate)
			                 ),
			                 distinct_type AS
			                 (
			                       SELECT 1 slno, 'HL7' type
			                       FROM dual
			                       UNION
			                       SELECT 2 slno, 'CCD' type
			                       FROM dual
			                       UNION
			                       SELECT 3 slno, 'EXP' type
			                       FROM dual
			                  )
			                       SELECT b.type "TYPE",
			                              a.received_date "LAST_RECEIVED",
			                              a.total "TOTAL",
			                              a.distinct_count "DISTINCT"
			                        , CASE
			                         WHEN a.type = 'HL7' AND a.received_date = TRUNC(sysdate) - 1 THEN 'Y'
			                         ELSE 'N'
			                         END hl7_recd_yn
			                        , CASE
			                         WHEN a.type = 'CCD' AND a.received_date BETWEEN Trunc(next_day(sysdate,'MONDAY') - 7)
			                         AND Trunc(next_day(sysdate,'MONDAY') - 1)
			                         THEN 'Y'
			                         WHEN a.type = 'CCD' AND Trim(Upper(to_char(sysdate, 'DAY'))) = 'MONDAY'
			                         THEN 'Y'
			                         ELSE 'N'
			                         END ccd_recd_yn
			                         , CASE
			                         WHEN a.type = 'EXP' AND Trunc(sysdate) <= Trunc(sysdate,'MM') + 14
			                         THEN 'Y'
			                         WHEN a.type = 'EXP' AND a.received_date BETWEEN Trunc(sysdate,'MM') AND Trunc(sysdate,'MM') + 14
			                         THEN 'Y'
			                         WHEN (a.type = 'EXP' AND Trim(Upper(to_char(a.received_date, 'MONTH'))) = Trim(Upper(to_char(SYSDATE, 'MONTH'))))
			                         THEN 'Y'
			                         ELSE 'N'
			                         END exp_recd_yn
			                         FROM hits_recd a RIGHT OUTER JOIN distinct_type b
			                         ON ( a.type = b.type AND rn = 1 )
			                         ORDER BY slno
			""";

	/** The SQL of {@code getCountsForHITLiveEvent}, as the former mapper had it. */
	static final String COUNTS_FOR_HIT_LIVE_EVENT = """
			select 'CCD' type, trunc(receivedtime) receivedday,
								count(*) tot, count(distinct payloadname) dist,count(*)-count(distinct payloadname) dupes,
								max(uphn_tranx_in_id) max_uphn_tranx_in_id
								from uphn_tranx_in
								where uphn_tranx_in_id > (SELECT MAX(MAX_UPHN_TRANX_IN_ID) from HIT_MESSAGE_COUNTS where TYPE = 'CCD')
								and topic = 'DEERPROD'
								and trunc(receivedtime) = trunc(SYSDATE)
								group by trunc(receivedtime)

							union

								select 'HL7' type, trunc(receivedtime) receivedday,
								count(*) tot, count(distinct payloadname) dist,count(*)-count(distinct payloadname) dupes,
								max(uphn_tranx_in_id) max_uphn_tranx_in_id
								from uphn_tranx_in
								where uphn_tranx_in_id > (SELECT MAX(MAX_UPHN_TRANX_IN_ID) from HIT_MESSAGE_COUNTS where TYPE = 'HL7')
								and topic = 'DEERHL7PROD'
								and payloadname like '%.txt'
								and trunc(receivedtime) = trunc(SYSDATE)
								group by trunc(receivedtime)

							union

								select 'EXP' type, trunc(receivedtime) receivedday,
								count(*) tot, count(distinct payloadname) dist,count(*)-count(distinct payloadname) dupes,
								max(uphn_tranx_in_id) max_uphn_tranx_in_id
								from uphn_tranx_in
								where uphn_tranx_in_id > (SELECT MAX(MAX_UPHN_TRANX_IN_ID) from HIT_MESSAGE_COUNTS where TYPE = 'EXP')
								and topic = 'DEERHL7PROD'
								and trunc(receivedtime) = trunc(SYSDATE)
								and payloadname like '%.csv'
								group by trunc(receivedtime)
								order by receivedday, type
			""";

	/** The SQL of {@code deleteFromUphnTranxPropsIn}, as the former mapper had it. */
	static final String DELETE_FROM_UPHN_TRANX_PROPS_IN = """
			DELETE UPHN_TRANX_PROPS_IN UTPI
				WHERE EXISTS (SELECT 1
			                 FROM UPHN_TRANX_IN UTI
			                WHERE UTPI.UPHN_TRANX_IN_ID = UTI.UPHN_TRANX_IN_ID
			                AND Trunc(uti.receivedtime) < SYSDATE - 30)
			""";
}
