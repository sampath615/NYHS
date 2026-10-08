# NYEHMS: MyBatis to Spring Data JPA

| | |
|---|---|
| **Branch** | `spring-boot-migration` (Spring Boot 4.0.5, Hibernate 7.2, Spring Data JPA 4.0) |
| **Approach** | Module by module. MyBatis and JPA run side by side on the same `appDS` pool and in the same transactions until the last mapper is gone. |
| **Done** | NYC PRF partner list (`NycPRFPartnerDAO` → `NycprfPartnerRepository`) |

---

## 1. Scope

Measured over all 76 mapper files (1,332 statements):

| Kind | Statements | JPA approach |
|---|---:|---|
| Plain, typed SQL | 201 | Entity + repository (derived or JPQL query); native query where the SQL is Oracle-specific. |
| `Map` / `JSONObject` in or out (untyped) | 906 | Introduce a DTO/projection per query first, then migrate. |
| Stored procedures (`CALLABLE`) | 132 | `@Procedure` / `StoredProcedureQuery`, or keep on plain JDBC — business logic stays in PL/SQL. |
| Oracle-specific SQL (`NVL`, `TO_CHAR`, `(+)`, `CONNECT BY`, `ROWNUM`...) | 668 | Native queries (unchanged SQL). |
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
3. **Entity** in `web.spring.entity.<area>` (field names in Java style, `@Column` with the real name). Keep the existing VO for the JSPs/controllers; map entity ↔ VO in the repository or service.
4. **Repository** in `web.spring.repository.<area>`: derived/JPQL queries for simple cases; for SQL that must stay exactly as it was, a query fragment (`XxxQueries` + `XxxQueriesImpl`) with a native query and **explicitly typed columns** (`NativeQuery.addScalar(label, String.class / java.sql.Date.class ...)`) so values convert exactly as MyBatis did.
5. **Service**: replace the DAO with the repository; nothing else changes.
6. **Delete** the DAO interface and its mapper XML (they are bound by name; nothing else to unregister).
7. **Verify**:
   - side-by-side run of the old mapper (from Git) and the new repository on the same H2 (Oracle mode) data — every field of every row equal;
   - a JUnit test in `src/test` against H2 with the expected values (`mvn test`);
   - full compile; then deploy and click through the screen.

Notes: Hibernate rejects a native query that selects the same column name twice (MyBatis ignored it). `java.sql.Date` in a VO means MyBatis read the column with `getDate()` (date only) — read it the same way.

---

## 4. Progress

| Module | Was (MyBatis) | Now (JPA) | Verified | Notes |
|---|---|---|---|---|
| NYC PRF partner list (NYC PRF document view) | `NycPRFPartnerDAO.getNycPRFPartner` (`RAW_NYCPRF_PARTNER` ⨝ `STD_NYCPRF_PARTNER`) | `NycprfPartnerRepository.findPartnersOfDocument` (native query, typed columns) + read-only entity `StdNycprfPartner` | Side by side with the old mapper on H2: identical (DISTINCT, missing rows, NULLs, number-as-text, date-only). `NycprfPartnerRepositoryTest` (4 tests) incl. the shared-transaction check. Full compile. | Entity key `(PATIENT_ID, NYCPRF_DOCUMENT_ID)` is **assumed** — confirm (step 2) before using the entity for writes (`NycPRFDocumentDAO` still inserts these rows). The duplicated `PARTNER_TEST_DATE_TEXT` column was dropped from the SQL. **Not yet deployed.** |

**Next candidates** (small, live, single table, typed): `EharsDataLoaderDAO.deleteRIDREharsData` (`EHARS_RIDR`), `OldCasesDocumentDAO`, `MedicaidOldDocumentDAO`, `BlackBoxDocDAO`, `NDIDocumentDAO`, then the rest of the NYC PRF document (`NycPRFDocumentDAO`, which also writes `STD_NYCPRF_PARTNER`).

**Dead persistence code found (delete rather than migrate):** `TTHDocumentService` + `TTHDocumentDAO` (+ XML); `FacilityService.getFacilityTypeCodes` + `FacilityCodedDAO` (+ XML).
