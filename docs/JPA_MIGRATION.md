# NYEHMS: MyBatis to Spring Data JPA

| | |
|---|---|
| **Branch** | `spring-boot-migration` (Spring Boot 4.0.5, Hibernate 7.2, Spring Data JPA 4.0) |
| **Approach** | Module by module. MyBatis and JPA run side by side on the same `appDS` pool and in the same transactions until the last mapper is gone. |
| **Done** | NYC PRF partner list (`NycPRFPartnerDAO` → `NycprfPartnerRepository`) |
| **Conventions** | JPQL / derived queries only (no native SQL); entities with annotated fields only (no getters/setters); data read for a screen comes back as a Java `record` built with JPQL `select new` (no VO class, no accessors, no mapping code). |

---

## 1. Scope

Measured over all 76 mapper files (1,332 statements):

| Kind | Statements | JPA approach |
|---|---:|---|
| Plain, typed SQL | 201 | Entities + repository with derived or JPQL queries. |
| `Map` / `JSONObject` in or out (untyped) | 906 | Introduce a DTO/projection per query first, then migrate. |
| Stored procedures (`CALLABLE`) | 132 | `@Procedure` / `StoredProcedureQuery`, or keep on plain JDBC — business logic stays in PL/SQL. |
| Oracle-specific SQL (`NVL`, `TO_CHAR`, `(+)`, `CONNECT BY`, `ROWNUM`...) | 668 | Rewrite in JPQL where an equivalent exists (`coalesce`, `cast`, explicit joins, `fetch first`); otherwise decide per case (no native SQL by default). |
| `${}` string substitution | 104 | Redesign (dynamic table/column names cannot be bound parameters); review for SQL injection. |

(Counts overlap: a statement can be in several rows.) Removing MyBatis completely therefore means rewriting about 1,100 statements; do it in small, tested steps, smallest and most self-contained modules first.

---

## 2. Infrastructure (in place)

- `pom.xml`: `spring-boot-starter-data-jpa` (versions from the Boot parent); test scope: `spring-boot-starter-test`, `h2`.
- `config/JpaConfig`: entity manager factory on **appDS** (entities in `web.spring.entity`), `@EnableJpaRepositories` (repositories in `web.spring.repository`), `hibernate.hbm2ddl.auto=none` (the schema is never created, changed or validated).
- **Transactions:** the single transaction manager is now a `JpaTransactionManager` on appDS (moved from `DataSourceConfig`). It exposes its JDBC connection to MyBatis and the raw JDBC code, so a `@Transactional` method that mixes MyBatis, JDBC and JPA runs on one connection in one transaction (covered by a test). `ursDS` and `eharsDS` are still MyBatis/JDBC only.

---

## 3. Recipe for one module

1. **Pick** a live module (check that its service is actually called — `TTHDocumentService` and `FacilityService.getFacilityTypeCodes` turned out to be dead code).
2. **Confirm the table keys and column types** before writing an entity:
   ```sql
   SELECT c.table_name, c.constraint_type, cc.column_name, cc.position
   FROM all_constraints c JOIN all_cons_columns cc ON cc.owner = c.owner AND cc.constraint_name = c.constraint_name
   WHERE c.table_name IN ('<TABLE>') AND c.constraint_type IN ('P','U') ORDER BY c.table_name, cc.position;
   SELECT table_name, column_name, data_type, data_length, nullable FROM all_tab_columns
   WHERE table_name IN ('<TABLE>') ORDER BY table_name, column_id;
   ```
   A wrong `@Id` makes Hibernate merge rows that share it — never guess it for entity reads or writes.
3. **Entity** in `web.spring.entity.<area>`: Java-style field names with `@Column` carrying the real column name; `@Immutable` while the module is read-only.
4. **Repository** in `web.spring.repository.<area>`: a Spring Data interface with derived or JPQL (`@Query`) methods only — **no native SQL, no implementation classes**. Return what the screen needs as a `record` built with a JPQL constructor expression (`select new <Record>(...)`), replacing the old VO class:
   - **Display-only data** (the usual case): a record works with JSP EL on Tomcat 11 (`RecordELResolver`) and with Spring's form tags for display (`form:input path="list[i].X"` reads record components). Keep the VO's property names as component names so the JSPs and the generated HTML names/ids do not change. Check first that nothing posts the form back into these objects and that the old VO has no other writers.
   - **Data posted back by a form**: records cannot be filled field by field; keep a mutable form class (or test Spring constructor binding) — decide per screen.
   - The JPQL select list must follow the record's component order; a mismatch stops the application at startup (Spring Data validates the query).
   - **Writes**: a row the application creates is an entity built with Lombok `@Builder` (no setters) and saved with `saveAndFlush`; the entity implements `Persistable` with `isNew() = true` so `save` inserts without a select first. Any cleaning the old SQL did in Oracle (`upper(trim(...))`, `FN_TO_DATE`, empty text = NULL, implicit number/date conversions) moves to Java in the mapping class — compare the whole path (old mapping + old SQL vs new mapping + save) on H2 with random records. Map written DATE columns as `java.sql.Timestamp` (keeps the time, still a `java.util.Date` for `<fmt:formatDate>`). Set-based copies between tables (`INSERT ... SELECT`) stay HQL `insert ... select` (`@Modifying @Query`); Hibernate type-checks them: text literals (`'1'`) for text attributes, `cast(... as String)`, `function('fn_to_date' as Date, ...)`, `cast(sysdate as Date)`.
5. **Service**: replace the DAO with the repository; nothing else changes.
6. **Delete** the DAO interface and its mapper XML (they are bound by name; nothing else to unregister).
7. **Verify**:
   - side-by-side run of the old mapper (from Git) and the new repository on the same H2 (Oracle mode) data — every field of every row equal;
   - a JUnit test in `src/test` against H2 with the expected values (`mvn test`);
   - full compile; then deploy and click through the screen.

Notes: `java.sql.Date` in a VO means MyBatis read the column with `getDate()` (date only): map the entity attribute and the record component as `java.sql.Date` (the JSP's `<fmt:formatDate>` needs a `java.util.Date`); Hibernate then reads it with `getDate()` too. A numeric column that a VO holds as text can be mapped as a `String` attribute (read with `getString()`, like MyBatis). JPQL `select distinct new ...` keeps SQL `DISTINCT` semantics. Entity keys only matter once entities are loaded or written; projections (`select new`) never go through the persistence context.

---

## 4. Progress

| Module | Was (MyBatis) | Now (JPA) | Verified | Notes |
|---|---|---|---|---|
| NYC PRF partner list (NYC PRF document view) | `NycPRFPartnerDAO.getNycPRFPartner` (`RAW_NYCPRF_PARTNER` ⨝ `STD_NYCPRF_PARTNER`) | `NycprfPartnerRepository.findPartnersOfDocument`: one JPQL `select distinct new NycprfPartner(...)` (a record, replacing the `NycPRFPartnerVO` class) over the read-only entities `StdNycprfPartner` and `RawNycprfPartner` | Side by side with the old mapper on H2: identical (DISTINCT, missing rows, NULLs, number-as-text, date-only). `NycprfPartnerRepositoryTest` (4 tests) incl. the shared-transaction check. Full compile. | Entity key `(PATIENT_ID, NYCPRF_DOCUMENT_ID)` is **assumed** — confirm (step 2) before using the entity for writes (`NycPRFDocumentDAO` still inserts these rows). The mapper selected `PARTNER_TEST_DATE_TEXT` twice; the JPQL selects it once (same result). Removed with it: the dead `NycPrfFormVO`, the `/addNycPRFPartner` and `/deleteNycPRFPartner` endpoints (no JSP bound `nycPrfFormVO`; the delete loop was commented out) and the empty `NycPRFDocumentService.insertNycPRFDoc` stub. **Not yet deployed.** |
| NYC PRF document (NYC PRF document view, `/getNycPrfDoc.html`) | `NycPRFDocumentDAO.getNycPRFDoc` (`RAW_NYCPRF_DOC` ⨝ `STD_NYCPRF_DOC` ⟕ `person_doc_association`, 146 columns) | `NycprfDocRepository.findDocument`: one JPQL `select new NycPrfDocData(stdNycprfDoc, ...)` (same style as the partner query) over the read-only entities `StdNycprfDoc` (142 columns), `RawNycprfDoc` and the shared `PersonDocAssociation`; the mapper's `TO_CHAR(DOB/FIELD_PROC_DATE, 'MM/DD/YYYY')` is now done in the JSP with `<fmt:formatDate pattern="MM/dd/yyyy">`, like the page's other dates. The record `NycPrfDocData` (wrapping the `StdNycprfDoc` entity, plus the received address, the current STATENO/DISCARD_STATUS) replaces the `NycPRFDocVO` class; partners are added with `withPartnerList`. | Side by side with the original mapper on H2: 290 values identical incl. types (before DOB and FIELD_PROC_DATE moved to the entity as dates); `NycprfDocRepositoryTest` (4 tests); all 137 JSP properties resolve (Spring form tags and Tomcat 11 EL); full compile. | `NycPrfDocument.jsp` reads `nycPRFDocVO.stdNycprfDoc.<field>`; every form tag got an explicit `id` equal to the id Spring generated before (path for inputs/selects, path + counter for checkboxes/radio buttons, `${...Status.count}` inside loops), so the inline script and `prfDoc.js` still find fields by id. **The HTML `name` attributes changed** (e.g. `FNAME` → `stdNycprfDoc.fname`); the page is not posted back, but check `prfDoc.js` for name-based selectors. Constant `tthMainTTHSource` (unused) dropped. Keys assumed: `DOCUMENT_ID` for both document tables, `(DOCUMENT_ID, STATENO)` for `PERSON_DOC_ASSOCIATION`. **Not yet deployed.** |
| NYC PRF import (scheduler: raw → standardized, `DocumentTransactionHelper.writeNYCPRFDocAndPartnersData`) | `NycPRFDocumentDAO.writeStdDocData` (insert of 177 columns from a `Map`, with `upper(trim())`/`FN_TO_DATE` in SQL) and `writeRawPRFPartnerDataToStdTable` (`INSERT INTO STD_NYCPRF_PARTNER ... SELECT ... FROM RAW_NYCPRF_PARTNER`) | Form: `NycPrfStdDocMapping.toStdDoc(raw)` builds the `StdNycprfDoc` entity (`@Builder`, `Persistable`, date columns as `Timestamp`), the service calls `nycprfDocRepository.saveAndFlush`; the SQL cleaning is done in Java (`upper`, `trimmed` = spaces only, empty → null, `fnToDate`, `date`, `oracleText`). Partners: `NycprfPartnerRepository.insertStdPartnersOfDocument` (HQL `insert ... select`, same `CASE` codes). | Whole path before vs after on two H2 databases: 5,000 random records × 178 columns identical (890,000 values; `CREATE_DATE` by day); partners: 20 rows × 72 columns identical. `NycPrfStdDocMappingTest` (10), repository tests incl. `savesANewStandardizedDocument` and `insertsTheStandardizedPartnersOfADocument` (21 tests in total). Full compile. | **Assumptions** (not visible in H2 — check at deployment): `FN_TO_DATE` returns null for an invalid date; text into a DATE column (`DOB`, `FIELD_PROC_DATE`, ...) is MM/DD/YYYY or YYYY-MM-DD (Oracle used the session date format; anything else now fails the record); a date value for a text column fails the record (Oracle formatted it); Java `toUpperCase(Locale.ROOT)` = Oracle `UPPER` for ASCII. Changes: `CREATE_DATE` keeps the time (was bound with `setDate`); the entity reads date columns as `Timestamp` with their time (the screen shows MM/dd/yyyy); `PRINTED_DATE` has a text read attribute and a `Timestamp` write attribute (`printedDateValue`); `DOCUMENT_ID` is the `@Id` (still assumed). Partner insert: race flags/`CREATE_UID` as text, `cast(NYCPRF_DOCUMENT_ID as varchar)`, `cast(sysdate as date)` (same stored values); H2 adds `escape ''` to LIKE (NULL in Oracle mode), so that branch was compared with the clause stripped. **Not yet deployed.** |

**Next candidates** (small, live, single table, typed): `EharsDataLoaderDAO.deleteRIDREharsData` (`EHARS_RIDR`), `OldCasesDocumentDAO`, `MedicaidOldDocumentDAO`, `BlackBoxDocDAO`, `NDIDocumentDAO`, then the last NYC PRF statement (`NycPRFDocumentDAO.getNYCPRFDocumentForClericalReview`).

**Dead persistence code found (delete rather than migrate):** `TTHDocumentService` + `TTHDocumentDAO` (+ XML); `FacilityService.getFacilityTypeCodes` + `FacilityCodedDAO` (+ XML).
