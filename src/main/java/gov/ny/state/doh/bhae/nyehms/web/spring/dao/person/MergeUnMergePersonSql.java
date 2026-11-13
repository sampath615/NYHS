package gov.ny.state.doh.bhae.nyehms.web.spring.dao.person;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.number;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.text;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.timestamp;

import java.util.Map;

import org.hibernate.type.StandardBasicTypes;

import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.Param;

/**
 * The SQL of the {@link MergeUnMergePersonDAO} statements that stay native, taken unchanged from the former MyBatis mapper
 * ({@code #{...}} replaced by {@code ?1, ?2, ...}; generated from the mapper XML); why each one stays native is said at
 * its DAO method. The {@code ...Args} methods give the values bound to a statement, in order, typed as the mapper's
 * {@code jdbcType} bound a null ({@code VARCHAR} and {@code CLOB} a text, {@code DECIMAL} a number, {@code DATE} a date,
 * {@code TIMESTAMP} a timestamp); a value that is not null is bound as it is, as MyBatis bound it by its Java type (a
 * {@code java.util.Date} with {@code jdbcType=DATE} as its day only, as MyBatis' DateOnlyTypeHandler did). A value without
 * a jdbcType is bound as it is. A statement with one simple parameter binds it as {@code ?1} wherever the mapper named it.
 */
final class MergeUnMergePersonSql {

	private MergeUnMergePersonSql() {
	}

	/**
	 * A value bound as {@code #{x,jdbcType=DATE}}: a {@code java.util.Date} (the class itself) as its day only (MyBatis'
	 * DateOnlyTypeHandler, setDate), any other value as it is (a Timestamp keeps its time), null as a DATE null.
	 */
	static Param date(Object value) {
		if (value != null && value.getClass() == java.util.Date.class) {
			return NativeStatements.param(new java.sql.Date(((java.util.Date) value).getTime()), StandardBasicTypes.DATE);
		}
		return NativeStatements.param(value, StandardBasicTypes.DATE);
	}

	/** The SQL of {@code commitCurrentTransaction}, as the former mapper had it (0 bound values). */
	static final String COMMIT_CURRENT_TRANSACTION = """
			begin
			    COMMIT;
			    END;
			""";

	/** The SQL of {@code addACRFForRejectedStateno}, as the former mapper had it (5 bound values). */
	static final String ADD_ACRF_FOR_REJECTED_STATENO = """
				insert into raw_Acrf_doc(DOCUMENT_ID, DOCUMENT_SOURCE, PERSON_COMPLETING, STATENO, FNAME, MNAME, LNAME, LNAME_SOUNDEX, ADDRESS, CITY, STATE, ZIPCODE, PLUS4, COUNTY_FIPS, TELEPHONE, DATE_OF_BIRTH, ID_TYPE_CODE, OTHER_ID_NUMBER, DATE_COMPLETED, RECEIVED_DATE, OLD_ACRF)
			select  ?1, ?2, ?3, ?4,
			                                FIRST_NAME, MIDDLE_NAME, LAST_NAME, soundex(LAST_NAME), ADDRESS, ADDRESS_CITY,
			       ADDRESS_STATE, ADDRESS_ZIPCODE, PLUS4, COUNTY_FIPS, HOME_PHONE, TO_CHAR(DATE_OF_BIRTH, 'MM/DD/YYYY'), '3', SSN, TRUNC(SYSDATE), trunc(SYSDATE), 'N'
			from document_person_attr a
			where a.document_id = (select out.document_id
			from person_doc_association out
			where out.stateno = ?5
			and out.DISSASSOCIATION_DATE is null
			and out.association_date = (select max(inn.association_date)
			                        from person_doc_association inn
			                        where inn.stateno = out.stateno
			                        and inn.DISSASSOCIATION_DATE is null ) and rownum < 2)
			""";

	/** The values of {@link #ADD_ACRF_FOR_REJECTED_STATENO}, in order. */
	static Object[] addACRFForRejectedStatenoArgs(Map<?, ?> map) {
		return new Object[] {
				map.get("DOCUMENT_ID"), map.get("DOCUMENT_SOURCE"), map.get("PERSON_COMPLETING"), map.get("ASSOCIATE_STATENO"),
				map.get("ASSOCIATE_STATENO")
		};
	}

	/** The SQL of {@code validateDodAgainstCollectionDateBeforeMerge}, as the former mapper had it (2 bound values). */
	static final String VALIDATE_DOD_BEFORE_MERGE = """
				WITH data AS
			(
			SELECT Max(a.date_of_death) dod, Max(s.calc_collection_date) hiv_calc_dt, Max(e.calc_collection_date) std_calc_dt
			FROM person_doc_association p
			LEFT OUTER JOIN document_person_attr a
			ON (a.document_id = p.document_id AND a.date_of_death IS NOT NULL)
			LEFT OUTER JOIN std_hiv_eclrs_doc s
			ON (p.document_id = s.document_id)
			LEFT OUTER JOIN std_std_eclrs_doc e
			ON (p.document_id = e.document_id)
			WHERE p.dissassociation_date IS NULL
			AND p.stateno in (?1, ?2)
			)
			SELECT 'Y'
			FROM data
			WHERE dod < hiv_calc_dt OR dod < std_calc_dt
			""";

	/** The values of {@link #VALIDATE_DOD_BEFORE_MERGE}, in order. */
	static Object[] validateDodAgainstCollectionDateBeforeMergeArgs(Map<?, ?> map) {
		return new Object[] {
				map.get("stateno1"), map.get("stateno2")
		};
	}

	/** The SQL of {@code getMergeReportsByOrigin}, as the former mapper had it (0 bound values). */
	static final String MERGE_REPORTS_BY_ORIGIN = """
			select history.*, curr.Currently_NEW from
			    (
			        select c.origin_desc ORIGIN,
			            sum ( case
			                    when (d.run_date = (trunc(sysdate, 'd') - 2 - 7*7)) then d.count_of_new
			                    else 0 end
			                ) as "MergeReportWeek1",
			            sum ( case
			                    when (d.run_date = (trunc(sysdate, 'd') - 2 - 7*6)) then d.count_of_new
			                    else 0 end
			                ) as "MergeReportWeek2",
			            sum ( case
			                    when (d.run_date = (trunc(sysdate, 'd') - 2 - 7*5)) then d.count_of_new
			                    else 0 end
			                ) as "MergeReportWeek3",
			            sum ( case
			                    when (d.run_date = (trunc(sysdate, 'd') - 2 - 7*4)) then d.count_of_new
			                    else 0 end
			                ) as "MergeReportWeek4",
			            sum ( case
			                    when (d.run_date = (trunc(sysdate, 'd') - 2 - 7*3)) then d.count_of_new
			                    else 0 end
			                ) as "MergeReportWeek5",
			            sum ( case
			                    when (d.run_date = (trunc(sysdate, 'd') - 2 - 7*2)) then d.count_of_new
			                    else 0 end
			                ) as "MergeReportWeek6",
			            sum ( case
			                    when (d.run_date = (trunc(sysdate, 'd') - 2 - 7*1)) then d.count_of_new
			                    else 0 end
			                ) as "MergeReportWeek7",
			            sum ( case
			                    when (d.run_date = (trunc(sysdate, 'd') - 2 - 7*0)) then d.count_of_new
			                    else 0 end
			                ) as "MergeReportWeek8"
			        from coded_merge_origins c left join weekly_count_of_new d
			        on d.origin_code = c.origin_code
			        where (c.END_DATE is NULL or c.END_DATE > sysdate)
			        group by c.origin_desc
			        order by c.origin_desc asc
			    ) history
			full outer join
			    (
			        select a.origin_desc ORIGIN,
			            sum( case
			                        when (
			                                b.status_cd='NEW'
			                            )
			                        then 1 else 0 end
			                ) as Currently_NEW
			            from coded_merge_origins a left join nyehms_duplicate_statenos b
			            on b.origin_code = a.origin_code
			
			            where (a.END_DATE is NULL or a.END_DATE > sysdate)
			            and b.stateno_1 in (select stateno from person where flag_active='Y')
			        	and b.stateno_2 in (select stateno from person where flag_active='Y')
			            group by a.origin_desc
			            order by a.origin_desc asc
			    ) curr
			on history.ORIGIN = curr.ORIGIN
			""";

	/** The SQL of {@code getMergeReportsByBadMerges}, as the former mapper had it (0 bound values). */
	static final String MERGE_REPORTS_BY_BAD_MERGES = """
			select history."BadMergesWeek1", history."BadMergesWeek2", history."BadMergesWeek3", history."BadMergesWeek4",
			    history."BadMergesWeek5", history."BadMergesWeek6", history."BadMergesWeek7", history."BadMergesWeek8", curr.Currently_NEW from
			    (
			        select 1 as dummyId,
			            sum ( case
			                    when (wnb.run_date = (trunc(sysdate, 'd') - 2 - 7*7)) then wnb.count_of_new
			                    else 0 end
			                ) as "BadMergesWeek1",
			            sum ( case
			                    when (wnb.run_date = (trunc(sysdate, 'd') - 2 - 7*6)) then wnb.count_of_new
			                    else 0 end
			                ) as "BadMergesWeek2",
			            sum ( case
			                    when (wnb.run_date = (trunc(sysdate, 'd') - 2 - 7*5)) then wnb.count_of_new
			                    else 0 end
			                ) as "BadMergesWeek3",
			            sum ( case
			                    when (wnb.run_date = (trunc(sysdate, 'd') - 2 - 7*4)) then wnb.count_of_new
			                    else 0 end
			                ) as "BadMergesWeek4",
			            sum ( case
			                    when (wnb.run_date = (trunc(sysdate, 'd') - 2 - 7*3)) then wnb.count_of_new
			                    else 0 end
			                ) as "BadMergesWeek5",
			            sum ( case
			                    when (wnb.run_date = (trunc(sysdate, 'd') - 2 - 7*2)) then wnb.count_of_new
			                    else 0 end
			                ) as "BadMergesWeek6",
			            sum ( case
			                    when (wnb.run_date = (trunc(sysdate, 'd') - 2 - 7*1)) then wnb.count_of_new
			                    else 0 end
			                ) as "BadMergesWeek7",
			            sum ( case
			                    when (wnb.run_date = (trunc(sysdate, 'd') - 2 - 7*0)) then wnb.count_of_new
			                    else 0 end
			                ) as "BadMergesWeek8"
			        from weekly_new_bad_merges wnb group by 1
			    ) history
				full outer join
			    (
			        select 1 as dummyId,
			            sum( case
			                    when (
			                            unm.status_cd='NEW'
			                        )
			                    then 1 else 0 end
			                ) as Currently_NEW
			            from nyehms_unmerging_statenos unm join person p on unm.stateno = p.stateno and p.flag_active = 'Y' group by 1
			    ) curr
				on history.dummyId = curr.dummyId
			""";

	/** The SQL of {@code getMergeReportsByDailyStaff}, as the former mapper had it (2 bound values). */
	static final String MERGE_REPORTS_BY_DAILY_STAFF = """
			select to_char(trunc(a.last_updated_date, 'IW'), 'yyyy_mm_dd') WEEKOF, to_char(a.last_updated_date, 'mm/dd/yyyy') UPDATED_DATE, to_char(a.last_updated_date, 'DAY') DAY_NAME,
				b.legacy_uid UPDATED_USER, a.status_cd STATUS, count(*) COUNT
			from nyehms_duplicate_statenos a join nyehms_users b
				on a.last_updated_by = b.worker_id
			where a.last_updated_date between to_date(?1, 'MM/DD/YYYY') and to_date(?2, 'MM/DD/YYYY')+1
				and a.status_cd in ('ACCEPT', 'REJECT')
			group by to_char(trunc(a.last_updated_date, 'IW'), 'yyyy_mm_dd'), to_char(a.last_updated_date, 'mm/dd/yyyy'), to_char(a.last_updated_date, 'DAY'), b.legacy_uid, a.status_cd
			order by to_date(updated_date, 'mm/dd/yyyy'), UPDATED_USER, STATUS
			""";

	/** The values of {@link #MERGE_REPORTS_BY_DAILY_STAFF}, in order. */
	static Object[] getMergeReportsByDailyStaffArgs(Map<?, ?> map) {
		return new Object[] {
				map.get("fromDate"), map.get("toDate")
		};
	}

	/** The SQL of {@code getBadMergeReportsByDailyStaff}, as the former mapper had it (2 bound values). */
	static final String BAD_MERGE_REPORTS_BY_DAILY_STAFF = """
			select to_char(trunc(a.last_updated_date, 'IW'), 'yyyy_mm_dd') WEEKOF, to_char(a.last_updated_date, 'mm/dd/yyyy') UPDATED_DATE, to_char(a.last_updated_date, 'DAY') DAY_NAME,
				b.legacy_uid UPDATED_USER, a.status_cd STATUS, count(*) COUNT
			from nyehms_unmerging_statenos a join nyehms_users b
				on a.last_updated_by = b.worker_id
			where a.last_updated_date between to_date(?1, 'MM/DD/YYYY') and to_date(?2, 'MM/DD/YYYY')+1
				and a.status_cd in ('ACCEPT', 'REJECT')
			group by to_char(trunc(a.last_updated_date, 'IW'), 'yyyy_mm_dd'), to_char(a.last_updated_date, 'mm/dd/yyyy'), to_char(a.last_updated_date, 'DAY'), b.legacy_uid, a.status_cd
			order by to_date(updated_date, 'mm/dd/yyyy'), UPDATED_USER, STATUS
			""";

	/** The values of {@link #BAD_MERGE_REPORTS_BY_DAILY_STAFF}, in order. */
	static Object[] getBadMergeReportsByDailyStaffArgs(Map<?, ?> map) {
		return new Object[] {
				map.get("fromDate"), map.get("toDate")
		};
	}

	/** The SQL of {@code getDependentDocumentList}, as the former mapper had it (19 bound values). */
	static final String DEPENDENT_DOCUMENTS = """
				SELECT document_id
				  FROM (
				            SELECT demo.document_id
				              FROM std_cluster_doc clust, std_cluster_doc demo
				            WHERE clust.document_id = ?1
				               AND clust.demo_document_id = demo.demo_document_id
				            UNION
				            SELECT document_id
				              FROM (
				            SELECT Distinct resistance_doc_id_1, resistance_doc_id_2, resistance_doc_id_3
				              FROM std_ras_doc ras, std_cluster_doc clust
				            WHERE clust.document_id = ?1
				               AND (ras.resistance_doc_id_1 = clust.demo_document_id
				                    OR ras.resistance_doc_id_2 = clust.demo_document_id
				                    OR ras.resistance_doc_id_3 = clust.demo_document_id))
				            UNPIVOT (document_id FOR data IN (resistance_doc_id_1, resistance_doc_id_2, resistance_doc_id_3))
				            UNION
				            SELECT a.document_id
				              FROM (
				            SELECT ras.resistance_doc_id_1, ras.resistance_doc_id_2, ras.resistance_doc_id_3
				              FROM std_ras_doc ras
				             WHERE ras.resistance_doc_id_1 = ?1
				                    OR ras.resistance_doc_id_2 = ?1
				                    OR ras.resistance_doc_id_3 = ?1)
				            UNPIVOT (document_id FOR data IN (resistance_doc_id_1, resistance_doc_id_2, resistance_doc_id_3)) a
				            LEFT OUTER JOIN std_cluster_doc clust
				            ON (clust.demo_document_id = a.document_id)
				            UNION
				            SELECT clust.document_id
				              FROM (
				            SELECT ras.resistance_doc_id_1, ras.resistance_doc_id_2, ras.resistance_doc_id_3
				              FROM std_ras_doc ras
				            WHERE ras.resistance_doc_id_1 = ?1
				                    OR ras.resistance_doc_id_2 = ?1
				                    OR ras.resistance_doc_id_3 = ?1)
				            UNPIVOT (document_id FOR data IN (resistance_doc_id_1, resistance_doc_id_2, resistance_doc_id_3)) a, std_cluster_doc clust
				            WHERE a.document_id = clust.demo_document_id
				        )
				WHERE document_id != ?1
				UNION
				SELECT demo_document_id from std_cluster_doc where document_id = ?1
			          UNION
			          SELECT document_id from std_cluster_doc where demo_document_id = ?1
			union
			           select lab_doc_id DOCUMENT_ID from nyehms_ria_doc ria where document_id in ?1 and lab_doc_id not in ?1
			           union
			           select document_id from nyehms_ria_doc ria where lab_doc_id in ?1 and document_id not in ?1
			           union
			           select TRIGGERING_DOC_ID DOCUMENT_ID from SURVEILLANCE_ASSIGNMENT where AHI_DOC_ID in ?1 and  TRIGGERING_DOC_ID not in ?1
			              union
			           select AHI_DOC_ID DOCUMENT_ID from SURVEILLANCE_ASSIGNMENT where TRIGGERING_DOC_ID in ?1 and  AHI_DOC_ID not in ?1
			""";
}
