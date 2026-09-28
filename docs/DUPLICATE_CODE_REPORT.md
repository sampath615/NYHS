# NYEHMS: Duplicate and Redundant Code Assessment

| | |
|---|---|
| **System** | NYEHMS (NY State DOH surveillance and case-management application) |
| **Baseline** | Commit `e69a795`, branch `main` |
| **Scope** | `src/main/java`, MyBatis mapper XML (`src/main/resources/**/*DAO.xml`), JSPs (`src/main/webapp`) |
| **Date** | 2026-09-25 |
| **Status** | Analysis only. Apart from the item marked *Completed*, no code has been changed as a result of this report. |

---

## 1. Executive Summary

About one sixth of the Java code, one fifth of the SQL and more than a third of the JSP markup is in blocks that are repeated elsewhere in the codebase. Most of it follows one pattern: each external data feed, report and screen was built by copying an existing one and changing it, not by extending a shared abstraction.

The duplication falls into three groups:

1. **Structural duplication, which needs design work.** About 40 document-feed pipelines repeat the same Controller → Service → DAO → VO shape, and the domain VOs re-declare the same person, provider and address fields. This is the largest share of the problem, but it can only be addressed through the modular-monolith and JPA work, not by mechanical refactoring.
2. **Boilerplate, which can be removed safely in small steps.** This covers session lookups, dummy `BindingResult` objects, date formatters, scheduler start-up checks, Excel cell styles and SQL column lists. These changes are low risk and each one can be verified on its own.
3. **Exact copies.** A small number of methods and JSP blocks are identical copies and can be merged at almost no risk.

The largest boilerplate item, the controller session lookup (551 handlers), has already been consolidated (commit `abd0146`).

---

## 2. Method

| Technique | What it detects |
|---|---|
| Line-block clone detection | Sliding window of 8 significant lines (6 for SQL). Whitespace is normalized and comments, blank lines, imports and lone braces are ignored. A line counts as duplicated if it falls inside any window that occurs more than once. |
| Method clone detection | Method bodies of 8 or more significant lines, compared twice: as exact text, and as structure with identifiers and literals normalized. |
| Idiom frequency | Individual statements repeated 25 or more times outside `domain/`. |
| Targeted searches | Counts of known patterns (`MapBindingResult`, `SimpleDateFormat`, `createCellStyle`, JDBC connection acquisition, and others), each checked against the code. |

**Limitations.** The detection is textual. It does not catch code that does the same thing in a different way, and some of the JSP figure comes from required page directives. The percentages show where duplication is concentrated. They are not a precise measure of effort.

---

## 3. Measured Duplication

| Area | Significant lines | In duplicated blocks | Share |
|---|---:|---:|---:|
| Java, all packages | 84,113 | 14,231 | **16.9%** |
| Java, excluding `domain/` | 48,902 | 5,779 | **11.8%** |
| Mapper XML (SQL) | 24,862 | 4,942 | **19.9%** |
| JSPs | 65,330 | 24,028 | **36.8%** |

**Where it is concentrated:**
- `domain/document/` has 5,767 duplicated lines, about 40% of all Java duplication.
- `service/document/` is the largest source of duplication outside the domain classes.
- `dao/document/` accounts for 1,765 duplicated SQL lines.
- `jsp/document/` accounts for 7,202 duplicated JSP lines.

The document-intake feature area is the main source of duplication in every layer.

---

## 4. Findings

Each finding is rated for **Impact** (how much code it affects and what maintenance it costs) and **Risk** (how likely a consolidation is to change behavior).

### 4.1 Structural duplication (design-level)

#### F-01. Document-feed pipelines repeated per source system
- **Impact:** Very high. **Risk:** High.
- About 40 external feeds (ADAP, AHI, Birth, Cancer, CASA, CCD, Death, DOCCS, HL7, Medicaid, NDI, PRF, RAS, SSDM, STD, TB, and others) each have their own controller methods, service, DAO, mapper XML and value objects.
- Every feed repeats the same operations: `get<Feed>Doc`, `insertRaw<Feed>Document`, `insertStd<Feed>Document`, `get<Feed>DocumentForClericalReview` and `get<Feed>DocAsHTML`.
- `DocumentController` alone has 22 `get*DocAsHTML` handlers, 17 of them with the same 9-line body.
- **Recommendation:** Introduce a single document-feed abstraction: a feed-type registry mapping each type to a handler, with shared raw/standardized processing steps. Do this as part of the `documentintake` module design, not as a stand-alone refactor.

#### F-02. Field groups re-declared across domain VOs
- **Impact:** High (about 5,800 lines). **Risk:** Medium.
- The same person, provider, facility, race and address fields (`FNAME`/`MNAME`/`LNAME`, `PERSON_*`, `PROV_*`, `RACE_*`), with hand-written getters and setters, are declared in more than 20 `domain/document/**` classes.
- The Raw/Std VO pairs (`RawCCDDocVO`/`StdCCDDocVo`, `RawHL7DocVO`/`StdHL7DocVO`, and others) each redefine the raw-versus-standardized record concept independently.
- **Recommendation:** Extract shared base classes now, or JPA `@Embeddable` types during the JPA migration. MyBatis result maps bind by property name, so moving fields into a superclass does not affect them, but every result map must be re-tested.

#### F-03. Per-feature transaction helpers
- **Impact:** Medium. **Risk:** Medium.
- 17 `*TransactionHelper` classes repeat the same pattern: open a `@Transactional` boundary, call DAOs, inspect status codes and map exceptions.
- **Recommendation:** Revisit once module boundaries exist. A shared template or base service would remove the repeated error handling (see F-06).

### 4.2 Repeated boilerplate (safe to consolidate incrementally)

#### F-04. Controller session lookup (*Completed*)
- `UserObject userObject = (UserObject) session.getAttribute("UserObj")` was repeated in 551 handlers across 59 controllers.
- It was replaced by `UserObjectArgumentResolver` in commit `abd0146`, which is awaiting deployment testing.
- 18 occurrences were deliberately left unchanged because those handlers are called directly from other code or use the session in other ways.

#### F-05. Hand-built `BindingResult` objects
- **Impact:** High (487 constructions in 59 files). **Risk:** Varies by pattern.

| Pattern | Occurrences | Assessment |
|---|---:|---|
| `Map targetMap = new HashMap(); BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");` | ~370 | **Not mechanically removable.** `targetMap` is the JSON response the controller returns, and services write into it through `bindingResult.getTarget()`. The fix is a typed response object (DTO or `ResponseEntity`) during modernization. |
| `Map dummyMindingMap = new HashMap(); BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");` | 73 in 22 files | **Safe to consolidate.** Pure scaffolding to satisfy service signatures; the result is never read. It can be replaced with a one-line factory method. *Completed* (`9876676`): 69 sites now call `BindingResults.newDummy()`; one site whose map is also the response was kept. |

#### F-06. Service-layer error handling
- **Impact:** High. **Risk:** Low to Medium.
- 225 `catch (DataAccessException …)` blocks in 64 files follow the same template: set `transactionMessage`, log it, then call `commonExceptionHandler.inspectDBTransacionResult(...)`, which is called 490 times in total.
- The declarations `String transactionMessage = null;` (167 times) and `Map inOutParameterMap = new HashMap()` / `Map inputParameterMap = new HashMap()` (about 280 times) repeat alongside it.
- **Recommendation:** Add a small helper, or an aspect, that runs a DAO call and handles the result consistently. Convert one feature package at a time.

#### F-07. Scheduler loader start-up logic
- **Impact:** Medium. **Risk:** Low.
- The same "resolve the server IP address" block is copied into 7 loaders; 13 files call `InetAddress.getLocalHost()`.
- The `loadSchedulers` environment check appears in 13 files.
- **Recommendation:** Add an abstract loader base class holding the shared checks.

#### F-08. Excel and PDF export views
- **Impact:** Medium (about 800 lines). **Risk:** Low.
- 26 view classes repeat header, style and row set-up: 51 `createCellStyle()` calls in 21 files, and one `setCellStyle(boldStyle)` statement repeated 54 times.
- `GenerateMergeReportByDailyStaff` and `GenerateBadMergeReportByDailyStaff` share a 121-line block.
- Three `buildPdfDocument()` methods have the same 33-line structure (`PSConfirmedSTDPdfView`, `UpdatedMessagesPdfView`, `UploadedRecordSearchStatnosPdfView`).
- **Recommendation:** Add a shared export base class (styles, header row, column writer), or a report-builder service.

#### F-09. Date formatting
- **Impact:** Low to Medium. **Risk:** Low.
- There are 47 inline `new SimpleDateFormat(...)` constructions; 21 of them use `"MM/dd/yyyy"`.
- `SimpleDateFormat` is not thread-safe, so any copy that gets moved into a shared field becomes a defect.
- **Recommendation:** Centralize the formats in `DateUtil`, using `java.time.DateTimeFormatter` constants after the Java 21 upgrade.

#### F-10. Raw JDBC connection handling
- **Impact:** Medium. **Risk:** Medium.
- 37 connection acquisitions in 18 files, each with its own statement, result-set and close handling.
- **Recommendation:** Standardize on Spring `JdbcTemplate`, or move the statements into MyBatis. This is a prerequisite for the JPA work.

#### F-11. Ad hoc email wrappers
- **Impact:** Medium. **Risk:** Low.
- 24 service classes wrap the shared `SendEmail` utility with their own sending logic.
- For example, `sendEmailToBureauDirector` is implemented separately in `EPRFOOCDocumentServiceImpl` and in `DocumentReviewTransactionHelper`.
- **Recommendation:** Create a single notification service that takes a template and a recipient list.

#### F-12. File-upload handling in controllers
- **Impact:** Medium. **Risk:** Low to Medium.
- 7 controllers handle `MultipartFile`/`MultipartHttpServletRequest` independently. Three upload endpoints have the same 15-line structure: `ClepAuditResultsController.uploadHIVSurveysFile`, `FileUploadController.uploadPSConfirmedStdsFile` and `FileUploadController.uploadNYCSurvRejectFile`.
- **Recommendation:** Add a shared upload helper that validates the file, stores it and reports status.

### 4.3 Exact and near-exact method copies (low risk)

| # | Method | Locations | Size | Status |
|---|---|---|---|---|
| E-1 | `processClusterPartnerDocument()` | `ClusterPartnerReviewTransactionHelper`, `DocumentReviewTransactionHelper` | 37 lines, identical | *Completed* (`3728ace`): the `DocumentReviewTransactionHelper` copy had no callers and was removed |
| E-2 | `isNameHavingSuffix()` | `CCDDocumentServiceImpl`, `HL7DocumentServiceImpl` | 15 lines, identical | *Completed* (`3728ace`): replaced by `util/PersonNameUtil.splitSuffix` |
| E-3 | `newImage()` / `newFooterImage()` | `PerinatalServiceImpl` | 11 lines, identical | *Completed* (`3728ace`): one `newImage` taking a docx4j `Part` |
| E-4 | Phone search-mask setters (`setPhone()` and similar) | `RawSubFacilityVO`, `SubProviderAndAddressVO`, `RawProviderAddressVO`, `RawAcrfDocVO` (5 setters) | 8 setters plus `CommonUtil.getUnMaskedPhoneNumber` | *Completed* (`83308aa`): replaced by `CommonUtil.toPhoneSearchPattern` |

**Same structure, different identifiers** (14 groups, 31 redundant copies, about 440 lines):
- 4 update/add methods in `LabReportsServiceImpl`.
- 4 clerical-review handlers in `ClericalReviewController`.
- 4 raw-status update methods in `CCDDocumentServiceImpl`/`CommonDocumentServiceImpl`.
- 3 methods each in `InitializeServiceImpl`, `NBSReviewServiceImpl` and `ManualQuartzJobController`/`ExperimentalController`.
- The facility/provider `add*Details` and `unMatch*` families.

**Recommendation:** Merge E-1 to E-4 into single shared methods (all four are done). Refactor the same-structure groups into one parameterized method each, one group per change.

### 4.4 SQL (mapper XML)

#### F-13. Repeated column lists and predicates
- **Impact:** Medium (4,942 lines). **Risk:** Low.
- **Largest cases:**
  - `ADAPDocumentDAO.xml` contains the same 276-line block twice.
  - `SchedulerJobsDAO.xml` has two column lists of about 185 lines each.
  - `RiaRecordsReviewDAO.xml` repeats a 24-line `CASE WHEN` expression four times.
  - `ClericalReportDAO.xml` repeats a 23-line block 14 times.
  - An 88-line search-criteria `<when>` chain is shared by `RawSubFacilityDAO.xml` and `RawProviderAddressDAO.xml`.
- *Partly completed* (`4e8f5e5`): 8 fragments extracted in `ADAPDocumentDAO.xml`, `SchedulerJobsDAO.xml` and `RiaRecordsReviewDAO.xml` (about 900 duplicated lines). MyBatis 3.3.0 has no `<include>` properties, so blocks that differ only by a table name (for example `ClericalReportDAO.xml`) need a MyBatis upgrade first; the `NyehmsWorkerRegionsViewDAO.xml` copies sit inside CDATA sections and cannot be included.
- **Recommendation:** Extract `<sql>` fragments within each mapper. The generated SQL does not change, so these are good first changes to verify, for example by logging the generated SQL before and after.

### 4.5 JSP views

#### F-14. Copied page sections
- **Impact:** High (24,028 lines). **Risk:** Medium, because pages are rendered with externally hosted JavaScript.
- **Largest cases:**
  - 17–21 identical directive and taglib lines open 22–25 pages.
  - `PrfDocument2016`, `PrfDocument2023` and `NycPrfDocument` share 202 lines.
  - Four `ClericalReview*` pages share 134 lines.
  - The `buildDate` scriptlet was copied into 52 JSPs. *Completed* (`11a47a3`): the value is now published once as an application attribute by `NYEHMSAppServletContextListener`.
  - Most of the duplication is under `document/` and `worker/`.
- The shared header, menu and footer includes are already well consolidated and are not part of this finding.
- **Recommendation:** Move the directive header into one include. Set `buildDate` once, for example as an application attribute or through an interceptor. Split the PRF and clerical-review pages into shared fragments. Every step needs visual regression checks.

---

## 5. Items Reviewed and Not Considered Defects

- **The `targetMap` response pattern (F-05, first row).** It looks repetitive, but each occurrence creates the response payload for its request. It should be replaced as part of the API design, not collapsed mechanically.
- **Shared JSP includes** (`common_includes.jsp`, `commonScriptsIncludes.html`, `Menu.jsp` and others) are already reused 90–150 times each.
- **Two grid libraries** (jqGrid and ag-Grid, the latter in 25 JSPs). This is an unfinished UI migration rather than code duplication. It should be tracked as a separate decision.

---

## 6. Recommended Sequence

Each step should be a separate, reviewable commit, verified by a compile comparison against the baseline, a diff audit and deployment smoke tests.

| Phase | Items | Rationale |
|---|---|---|
| **1. Low risk, no behavior change** | E-1 to E-4; F-05 dummy `BindingResult` (73 sites); F-13 `<sql>` fragments; the `buildDate` scriptlet and JSP directive header (F-14) | Mechanical changes that are easy to verify and do not change behavior. |
| **2. Shared infrastructure** | F-07 scheduler base class; F-08 export base class; F-09 date formats; F-11 notification service; F-12 upload helper | Each change is limited to one technical concern and touches a bounded set of files. |
| **3. Service templates** | F-06 error-handling helper; the same-structure method groups in 4.3 | Touches many services. Convert one package at a time. |
| **4. With modernization** | F-01 document-feed abstraction; F-02 VO base classes and `@Embeddable`s; F-03 transaction helpers; F-05 typed responses; F-10 JDBC | Depends on the module boundaries, the JPA entities and the Java 21 baseline. |

**Prerequisite.** The project has no automated tests. Before starting Phases 3 and 4, add characterization tests around the affected services and mappers, so that behavior is proven unchanged rather than assumed.
