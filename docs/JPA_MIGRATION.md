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

**Next candidates** (small, live, single table, typed): `EharsDataLoaderDAO.deleteRIDREharsData` (`EHARS_RIDR`), `OldCasesDocumentDAO`, `MedicaidOldDocumentDAO`, `BlackBoxDocDAO`, `NDIDocumentDAO`, then the rest of the NYC PRF document (`NycPRFDocumentDAO`, which also writes `STD_NYCPRF_PARTNER`).

**Dead persistence code found (delete rather than migrate):** `TTHDocumentService` + `TTHDocumentDAO` (+ XML); `FacilityService.getFacilityTypeCodes` + `FacilityCodedDAO` (+ XML).
