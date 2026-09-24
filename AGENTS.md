# AGENTS.md — NYEHMS

This document orients engineers and coding agents working in the NYEHMS codebase. It describes the **current** system as it actually exists (not an idealized version), flags cleanup/modernization candidates, and lays out the direction of a planned modernization. **Nothing described here as a "candidate" has been changed unless it appears in the Cleanup Log below** — the rest is analysis only.

> **What is NYEHMS?** Based on package names (`gov.ny.state.doh.bhae.nyehms`), constants (eHARS, LAFT, ADAP, PRF, AHI, partner services), and document-feed types (HIV/STD labs, cancer, birth, death, Medicaid, DOCCS), this is a NY State Department of Health surveillance/case-management system — most plausibly for HIV/AIDS (eHARS-related) and related communicable-disease reporting, receiving data feeds from dozens of external source systems (labs, hospitals, NYC DOH, Medicaid, corrections, cancer registry, vital records) and supporting case worker assignment, review, clerical processing, and reporting workflows. Deployed under WebLogic context root `doh2/applinks/nyehms`.

## Cleanup Log

Working rules: one small reviewable cleanup step per commit; no unrelated refactors or reformatting; verify references across Java, Spring XML, Quartz config, JSP, JS before removing; preserve existing line endings (source files are CRLF).

**Build baseline: deployment verified after `2f13871`.** The app was deployed and reported working by the project owner after each cleanup stage: after `77b99f6`, `5e78269` and `e8840de`, and again after `da444ee` and `2f13871` (current HEAD at that point). No Maven build output or test run has been recorded from the agent's machine (it has no Maven and only a JRE, no JDK), so a clean `mvn clean package -DskipTests` (JDK 8; deps such as `wlfullclient` and `gov.nyhealth.comdir` must resolve from a local/internal repo) is still worth recording here. Screen-level regression checks of the changed mappers (scheduler page, assignments, document review, lab reports, provider/facility standardization, scan and CCD screens) are not yet recorded.

| Commit | Change | Left intentionally for later |
|---|---|---|
| `77b99f6` | Removed `AllJaroLoader.java`, `RunAllJaroLoaderJob.java`; removed the commented-out `allJaroLoader`/`runAllJaroLoaderJob`/`allJaroCronTrigger` XML in `spring-config.xml`; removed the "Create AllJaro" button table from `manualQuartzSchedulerJob.jsp`. | `CommonConstants.LOAD_ALLJARO_SCHEDULER` (now unused); `SchedulerJobsDAO.getLatestAllJaroData()` (no mapped SQL, no callers); commented-out AllJaro code in `QualityStageLoader`/`RawRecordsCleaner`; `ALLJARO_*`/`JAROT_*` entries in `SchedulerMetaData.properties` (JAROT settings are still used by live code); copy-pasted "started loading alljaro data" log line in `RunEharsDataLoaderJob`. |
| `5e78269` | **Sierra job:** removed `RunSierraServiceLoaderJob.java` and `SierraServiceLoader.java` (not wired anywhere in `spring-config.xml`, not even in a comment; no JSP references). **DAO methods:** removed 22 interface methods that had no callers and no mapped SQL (8 in `SchedulerJobsDAO` incl. `getLatestAllJaroData`; 2 in `AssignmentDAO`; 1 each in `ADAPDocumentDAO`, `DocumentDAO`, `HITDocumentDAO`, `NonCaseDocumentDAO`, `LaftCountReconcileDAO`, `LoincResultDomainMappingDAO`, `ProviderPortalReportDAO`, `SearchDAO`, `SubFacilityDAO`, `FileUploadDAO`; 2 in `CCDDocumentDAO`). **VOs:** deleted 17 unreferenced domain classes (`JsonRequestReaderVO`, `AdminSearchVO`, `AssignmentDocsVO`, `AssignmentReportsVO`, `DocumentViewVO`, `EcrlsLabEditDocVO`, `StdEcrlsLabEditDocVO`, `PRFPartnerDocumentVO`, `HitMessageCountVO`, `RawCCDPersonAddressOldVO`, `RawHL7NteVO`, `DocumentsStatusVO`, `LAFTCountReconcileVO`, `LAFTCountReconcileDashboardVO`, `LAFTReconcileDashboardVO`, `LoincInterpretResultsVO`, `LabDocumentVO`, `PersonACRFVO`, `AhiAlertUserNotesHistoryVO`). | Orphaned mapper XML for the removed DAO methods (see scan findings below); unused imports left in edited DAO interfaces; `SchedulerMetaData.properties` `SIERRA`/`ALLJARO` entries. |
| `e8840de` | **Remaining High/Medium candidates from the scan appendix:** deleted 22 files (see appendix "DONE" items); removed 34 unused DAO methods together with their 34 mapper XML statements; removed 17 orphan mapper XML statements; removed 27 unused `CommonConstants` entries. | JSPs (Low), commented-out code (~1,500 lines), `GetTblNameMap`, `jms` xmlns in `spring-config.xml`, ~9 shadowed XML statements, unused imports in edited DAO interfaces. |
| `da444ee`, `2f13871` | **Second cleanup pass** (comment sweep in `da444ee`; the rest in `2f13871`): removed 9 remaining orphan mapper XML statements; removed `GetTblNameMap` and its unused injections (`ProcessSearchCriteria`, `PersonServiceImpl`, `MergeUnMergePersonServiceImpl`, `SearchEngineTest`); deleted 18 unreferenced JSPs; removed ~3,300 commented-out code lines (98 Java files) and 69 commented-out XML blocks (18 files). | 7 JSPs with substring-only name hits; block comments and prose comments; `jms` xmlns in `spring-config.xml`; unused imports in edited files. |
| `88f2727` | **Third cleanup pass:** deleted 9 cascade-orphaned VOs (`AssignmentSuperTallyListVO`, `AssignmentWorkerTallyListVO`, `WorkerAssignmentsReportsVO`, `HIVABTstNonTypDiffVO`, `HIVABTstTypDiffVO`, `HIVQualiTstVO`, `HIVQuantiTstVO`, `LAFTCountReconcileResolutionVO`, `SubProvAddrsForRawProvAddrsVO`) and the unused `enums/DbTbl`; removed 15 unused mapper `resultMap` fragments (12 XML files); removed 129 unused imports (72 main files + 5 stale imports in `AssignmentModuleTest`). Conservative rule: an import is kept if its name appears anywhere else in the file, including comments. | Unused public/private methods (list in appendix below), Maven deps `sqljdbc4` and `maven-plugin-api`, 7 JSPs, block/prose comments, `jms` xmlns, duplicate `LabDocumentDAO` namespace in `StdDocumentDAO.xml`. |
| `020ee5b` | **Fourth cleanup pass:** removed 19 unused methods (~280 lines; see appendix), 26 imports freed by them, 43 block-commented code blocks (~1,400 lines, 26 files, `/* ... */` only, whole-line, >=70% code-like), 7 more unreferenced JSPs, the unused `jms` xmlns/schemaLocation in `spring-config.xml`, the dead `StdDocumentDAO.xml` mapper (never loaded — no same-named interface), and the `sqljdbc4` and `maven-plugin-api` dependencies in `pom.xml`. | 6 methods skipped by the remover (see appendix); prose comments (intentionally kept); duplicate-namespace problem is now moot because `StdDocumentDAO.xml` is gone. |
| `5d6bfd0` | **Fifth cleanup pass:** removed 26 no-caller DAO interface methods (18 in `PersonDAO` incl. the `get*DocumentDetails` family whose only callers were commented-out code, 4 in `SchedulerJobsDAO`, `AHIDocumentDAO.checkAhiFlagEligibility`, `ScanDocumentDAO.getDocDetails`, `LaftFollowupDocumentDAO.getDocumentReviewComment`, `RecordSearchReviewDAO.updateNoReturnSequences`) with their 24 mapper XML statements; deleted 3 unreferenced VOs (`NyehmsRolePermissionsViewVO`, `LAFTCountReconcileTestDataVO`, `UploadPSConfirmedSTDVO`); removed 4 unused `CommonConstants` entries. | JSPs newly unreferenced, ~128 unused private/`@Autowired` fields, legacy applet files, `SchedulerMetaData.properties`, `webapp/lib` jars (see "Open findings after the fifth pass"). |
| `b84cc85` | **Sixth cleanup pass:** deleted the legacy Java applet / Web Start bundle (`AppletCall.jsp`, `index.jsp`, `index2.jsp`, `launch.html`, `launch.jnlp`, `nyehmssecappj.jar` and its 3 `webapp/lib` jars — the applet call in `InitializeController` was already commented out); deleted 6 JSPs left unreferenced by earlier removals (`reports/ClusterViews`, `uploads/uploadClusterError`, `uploads/delManualAddedStatenoTSCluster`, `initialize/CryptoAuthSucess`, `standardization/facility/confirm/NewPageGrdStandardizeDialog`, `standardization/facility/search/GrandSearchCriteria`); removed 2 unused `AppConstants` enum constants; removed 126 unused private/`@Autowired` fields (75 files; the `@Autowired` line goes with its field, fields carrying other annotations were skipped) and the 77 imports they freed. | `scanning/ShowScannedFileNames.jsp` (still returned by a live controller method); properties files (all live — see notes below). |

Manual checks for `77b99f6`: app starts cleanly on WebLogic (DONE — deployment works), the scheduler page renders and its other buttons work (not yet recorded), external static JS has no references to `*CreatingAllJaro` IDs or `runAllJaroLoaderJob` (not yet recorded).

---

## 0. Facts and Numbers

| Metric | Count |
|---|---|
| Java source files (`src/main/java`) | 741 |
| JSP files | ~439 (436 by direct glob) |
| MyBatis mapper XML files (app + scheduler) | ~104 |
| DAO/mapper Java interfaces | 99 (web) + 4 (schedulers) |
| Service classes | 241 |
| Controller classes | 65 |
| Domain/VO/DTO classes | 257 |
| Standalone `.js` files in the repo | **0** — all JS is served from an external static-content path, not this repo |
| Quartz-scheduled jobs (active) | 13 (+1 commented-out dead job, see §3) |
| Java version | 8 (`maven-compiler-plugin` source/target `1.8`) |
| Spring Framework version | 5.3.20 (XML config, not Spring Boot) |
| Build | Maven, packaged as `.war`, WebLogic-specific manifest entries |

Root build file is `pom.xml` (Maven, `.war` packaging).

---

## 1. Current Project Structure

Base Java package: `gov.ny.state.doh.bhae.nyehms`

```
src/main/java/gov/ny/state/doh/bhae/nyehms/
├── utils/                          # 2 loose static-helper classes (County, HtmlMethods)
├── web/
│   ├── ajax/json/                  # GridObject, JsonObject — JSON payload shapes for jqGrid/AJAX
│   └── spring/
│       ├── controller/             # 65 files — Spring MVC @Controllers, grouped by feature
│       ├── service/                # 241 files — business logic, grouped by feature
│       ├── dao/                    # 99 MyBatis mapper interfaces, mirrors service grouping
│       ├── domain/                 # 257 VO/DTO classes — MyBatis result-map targets AND MVC form objects
│       ├── bo/                     # 4 legacy "business object" processor classes
│       ├── util/                   # shared utility classes (CommonUtil, DateUtil, SendEmail, etc.)
│       ├── constants/, enums/      # CommonConstants, EharsVariableConstants, AppConstants
│       ├── security/               # AuthFilter (dead), UserAuthorization, UserObject (session principal)
│       ├── aop/                    # exception-handling, logging, and intercepting aspects
│       ├── validation/             # per-document-type Validators
│       ├── view/                   # legacy Spring AbstractPdfView/AbstractXlsView subclasses (PDF/Excel export)
│       ├── listener/                # ServletContextListener (app bootstrap)
│       ├── exception/               # NyehmsException, CommonException
│       └── jms/                    # EnQueMessage/DeQueMessage/JmsExceptionListener — dead, unwired (§3)
├── schedulers/spring/dao/{ecrls,nyehms,ursdb}/   # scheduler-specific MyBatis DAOs, 3 separate datasources
└── webservice/{oauth,webclient}/   # modern WebClient/reactor-based HCS OAuth2 client (outside component-scan!)
```

```
src/main/resources/
├── spring-config.xml               # the entire Spring wiring: datasources, MyBatis, Quartz, MVC, JMS namespace (unused)
├── log4j2.xml
├── *_environment.properties        # per-environment config: DEV/EVAL/LOCAL/PROD
├── views.properties                 # ResourceBundleViewResolver — PDF/Excel export views ONLY, not JSPs
├── templates/*.ftl                  # 8 FreeMarker templates (perinatal letters, lab reports, doc-status summaries)
├── services/config/                 # webservice-client-config.xml, service-invocation-config.xml (HIN framework)
└── gov/ny/state/doh/bhae/nyehms/... # MyBatis mapper XML, mirrors src/main/java/.../dao package-for-package
```

```
src/main/webapp/
├── WEB-INF/web.xml                 # HCSSecurityFilter + ServiceConsumerSecurityFilter (external HIN libs), DispatcherServlet on /web/*
├── WEB-INF/weblogic.xml            # WebLogic-specific: context-root, session/JSP descriptors
├── WEB-INF/jsp/                    # 439 JSPs, ~24 top-level feature folders (see §2)
├── WEB-INF/properties/pom.properties  # ⚠ contains a hardcoded WebLogic deploy password (see §3)
```

### Layer responsibilities (as-built, not idealized)

- **Controllers** (`web/spring/controller/**`): Spring `@Controller` classes, `.html`-suffixed `@RequestMapping`s, mostly return JSP view-name strings against a `Map<String,Object>` model rather than typed `@ModelAttribute`/`ResponseEntity`. Heavily pull `UserObject` off the `HttpSession` (589 occurrences across 58 files). Several controllers contain real business logic and even direct DAO access (see §3/§5).
- **Services** (`web/spring/service/**`): the largest layer (241 files, ~77k lines). Mix of legitimate business logic and — in a substantial minority of classes — raw JDBC, PDF/Excel/FreeMarker generation, email sending, and SFTP file transfer, all inside `@Service` classes that also do database orchestration. `*TransactionHelper` classes (~20 of them, one per feature) wrap MyBatis calls in `@Transactional` boundaries.
- **DAOs / MyBatis mappers** (`web/spring/dao/**` + matching XML in `resources/.../dao/**`): interface-per-feature, XML-per-interface, wired via 3 separate `MapperScannerConfigurer`s onto 3 separate `SqlSessionFactory`s (`sqlSessionFactory`/`appDS`, `elrlsSessionFactory`/`appDS`, `ursSessionFactory`/`ursDS`) plus a 3rd raw datasource (`eharsDS`) accessed only via hand-rolled JDBC (`ConnectionDAO`/`JdbcConnectionDAO`), not MyBatis. The `document/` subpackage alone has 44 DAOs, one per external data-feed type.
- **Domain/VOs** (`web/spring/domain/**`): plain getter/setter classes with no Jackson/Bean-Validation annotations in most cases; the same VO instance is typically populated by MyBatis via reflection **and** bound to JSP forms/models — a dual-purpose pattern that will need to be split for a clean JPA entity vs. DTO separation later.
- **JSP + JS** (`webapp/WEB-INF/jsp/**`): server-rendered views resolved via `InternalResourceViewResolver` (prefix `/WEB-INF/jsp/`, suffix `.jsp`). **No JavaScript lives in this repository at all** — every JSP loads JS from an externally deployed static path (`${staticPath}/js/...`, set from `session.getAttribute("staticPath")`, e.g. `/hpn/ctrldocs/nyehms`), including shared libraries (jQuery 1.9.1, jQuery UI 1.10.1, jqGrid 4.5.4) centralized via `common/commonScriptsIncludes.html`. This is an important discovery for modernization: **front-end assets are managed and deployed outside this codebase** and are not visible to static analysis here.
- **Config**: `spring-config.xml` is the single, large, XML-based Spring context (datasources, MyBatis session factories, Quartz jobs/triggers, MVC view resolvers, JAXB/Jackson marshalling, JMS namespace declared-but-unused). `web.xml`/`weblogic.xml` carry WebLogic-specific deployment descriptors (JNDI-based filters, session replication, context-root). Environment selection is via `*_environment.properties` files.
- **Utilities**: scattered across `utils/` (2 files), `web/spring/util/` (9 files, including a 1,506-line `LabCommonUtils`), and many per-feature `*TransactionHelper`/`*Util`/`*Helper` classes embedded directly inside `service/` subpackages rather than centralized.

---

## 2. Current Architecture

Actual request flow, confirmed by tracing `PersonController` → `PersionService` [sic] → `PersonDAO`/`PersonDAO.xml` → Oracle, and generalized across the app:

```
Browser (JSP-rendered page + externally-hosted JS, jQuery/jqGrid AJAX)
        │  HTTP request to /web/*.html  (DispatcherServlet, spring-config.xml)
        ▼
HCSSecurityFilter → ServiceConsumerSecurityFilter   (external HIN/HCS platform filters, web.xml — perimeter auth)
        ▼
Spring MVC @Controller  (web/spring/controller/**)
        │  - pulls UserObject from HttpSession (session key "UserObj")
        │  - some controllers call Service; some call DAO directly (architecture violation, §5)
        ▼
@Service (web/spring/service/**)
        │  - business rules, orchestration, *TransactionHelper for @Transactional boundaries
        │  - some services also do raw JDBC, PDF/Excel/FreeMarker generation, email, SFTP (§5)
        ▼
MyBatis Mapper interface + XML (web/spring/dao/**, resources/.../dao/**)
        │  - parameterized SQL / dynamic <if>/<choose>/<foreach> / stored-procedure calls
        ▼
Oracle Database (3 WebLogic JNDI datasources: appDS, ursDS, eharsDS)
        ▲
        └── some services bypass MyBatis entirely via ConnectionDAO/JdbcConnectionDAO (raw JDBC on appDS/eharsDS)

Scheduled side-flow:
Quartz (spring-config.xml JobDetailFactoryBean/CronTriggerFactoryBean, 13 active jobs)
        → *Loader / *ServiceImpl classes in web/spring/service/schedulers/**
        → same Service → DAO → Oracle pattern, plus JSch/SFTP and JavaMail for file exchange and notifications
```

View resolution: controller returns a String like `"search/personSearchTabDetail"` → `InternalResourceViewResolver` → `/WEB-INF/jsp/search/personSearchTabDetail.jsp`. A **separate** `ResourceBundleViewResolver` (`views.properties`) resolves a small set of non-JSP export views (PDF via iText `AbstractPdfView`, Excel via `AbstractXlsView`) — these are Java classes in `web/spring/view/**`, not JSPs.

JSP folder names line up close to 1:1 with controller package names (administration, assignment, document, lab, person, review, search, standardization, uploads all match directly); a few don't match literally but resolve correctly through the view-name string (controller package `report` → JSP folder `reports`; `scan` → `scanning`; `userGuideHelp` → `userguide`).

---

## 3. Unnecessary Code Candidates

**Do not delete.** Each item below was checked by grepping across Java, MyBatis XML, JSP, and `spring-config.xml` before being listed — but only a human with deploy/runtime visibility should make the final call, especially for anything touching Quartz (reflection-invoked) or Spring component-scan (annotation-invoked).

| Item | Why it looks unnecessary | Confidence |
|---|---|---|
| ~~`web/spring/service/schedulers/AllJaroLoader.java` + `RunAllJaroLoaderJob.java`~~ | **DONE — removed in commit `77b99f6`** (see Cleanup Log). Was: beans/trigger commented out in `spring-config.xml`, plus 4 dead buttons in `manualQuartzSchedulerJob.jsp`. | ~~High~~ |
| `web/spring/security/AuthFilter.java` | Its only reference anywhere in the repo is the commented-out `<filter-class>` in `web.xml:63`. Zero other Java/JSP/XML references. | **High** |
| `web/spring/jms/EnQueMessage.java`, `DeQueMessage.java`, `JmsExceptionListener.java` | `spring-config.xml` declares the `jms` XML namespace but never defines a `ConnectionFactory`, `JmsTemplate`, or any `<jms:...>` element. Entire package is unwired. | **High** |
| `web/spring/validation/document/SSDMDocValidator.java`, `ADAPDocValidator.java` | `DocumentValidator.java` (the only dispatcher) instantiates only `TBDocValidator`/`LabDocValidator`; these two classes have no other referrers. | **High** |
| `web/spring/validation/custom/antn/UpperCase.java` + `UpperCaseValidator.java` | The `@UpperCase` annotation is never applied to any field anywhere in the codebase. | **High** |
| `web/spring/bo/GetTblNameMap.java` | Entire method body (~100 lines) is block-commented; only an empty `@Component` shell with one unused field remains live. | **High** |
| `web/spring/domain/test/Person.java`, `PersonInfo.java` + `controller/TestController.java` | Class-level Javadoc self-describes as a Spring-validation demo/scratchpad ("used for testing purpose of any new functionality"); hardcoded sample data, no other feature references `domain.test.*`. Endpoints are technically live/routable but serve no product function. | **High** (leftover scratch code, not wired to any real feature) |
| `utils/HtmlMethods.java` | Zero callers found anywhere in `src/main`. | **High** |
| `dao/standardization/provider/SubProviderAddressDAO.java`/`.xml` | Only self-reference found; appears superseded by the actively-used, similarly-named `SubProviderAndAddressDAO`. | **High** |
| `EharsVariableConstants.EHARS_VARIABLES` map (~40 entries) | Its only call site is a commented-out line in `AssignmentServiceImpl.java:765`. (The sibling `EHARS_VAR_NAME_MAP` in the same file IS actively used dynamically — do not remove the whole class.) | **High** |
| `com.microsoft.sqlserver:sqljdbc4` Maven dependency (`pom.xml`) | No `jdbc:sqlserver:` URL or SQL Server JNDI datasource exists anywhere. The one method that sounds related, `ConnectionDAO.getSqlServerConnectionObj()`, is actually wired to `eharsDS` — an Oracle datasource — in `spring-config.xml:188-191`. The method name is misleading; the driver itself appears unused. | **Medium** (dependency looks dead; the misleadingly-named method is real/called-from-6-places code, keep it, just note the name is wrong) |
| `administration/SystemMessagingController.java` | 9 lines, only a commented-out `@RequestMapping`; effectively an empty stub. | **Medium** |
| ~30% of sampled `CommonConstants.java` constants (6 of 20 sampled, e.g. `SCHEDULER_QUALITY_STAGE`, `TRIGGER_STOP`, `TEST_TYPE_IFA`, `STD_RESULTS_HIV2`, `NEW_HIE_DTH_DATA`) | No references outside their own declaration. `CommonConstants.java` (332 lines, ~110 constants) is an ungrouped "junk drawer" spanning many unrelated concerns — a fuller audit (not just the 20 sampled) would likely find more. | **Medium** (sampled only; needs a full sweep) |
| `reports/LoincResults.jsp`, `reports/labFacilitiesGrid.jsp`, `assignment/AssignmentsCountsSuper.jsp` | No controller view-name match, no JSP include/AJAX reference found anywhere. | **Low** (static grep only — dynamic view-name construction or access-log evidence should be checked before removal) |
| `userguide/UserGuideController` endpoint `/getUserGuide` → view `"userguide/UserGuide"` | No `UserGuide.jsp` exists (only `webUserGuide.jsp`, case-sensitive mismatch) — endpoint is likely broken/orphaned, possibly superseded by `/getWebUserGuide`. | **Medium** |
| `aop/intercepting/ControllerLayerInterceptingAspect.java` pointcut segment for `controller.reports..*` | Actual package is `controller.report` (singular) — this portion of the pointcut can never match; report-package controllers silently don't get whatever behavior this aspect provides. Functionally dead logic, not a whole dead file. | **High** |

**Caveats that apply across this whole section** (per project instructions — do not blindly classify code as unused):
- `@Service`/`@Component`/`@Controller` classes under `gov.ny.state.doh.bhae.nyehms.web.spring.*` are auto-registered by the single `<context:component-scan>` in `spring-config.xml:24` even with zero explicit XML bean refs and zero direct Java callers.
- Classes under `gov.ny.state.doh.bhae.nyehms.webservice.*` (a *different* top-level package) are **outside** that component-scan base package — e.g. `webservice/oauth/AccessTokenClient.java` carries `@Service`/`@Autowired` but is actually instantiated with `new AccessTokenClient(...)` by `NyhemsHCSWebServiceOp`, not by Spring DI. This is a wiring inconsistency worth fixing, not dead code.
- `RunXxxLoaderJob extends QuartzJobBean` classes are invoked by Quartz via reflection from `spring-config.xml`'s `JobDetailFactoryBean.jobClass` — absence of a direct Java caller does **not** imply dead code for this pattern (this is why AllJaroLoader could only be confirmed dead via its commented-out bean, not via a caller search alone).
- MyBatis mapper interfaces are proxied by `MapperScannerConfigurer`, not registered in XML individually — only a literal `@Autowired`/interface-reference grep can confirm liveness.

---

## 4. Redundant / Duplicate Code

- **~40 near-identical `Raw*/Std*` document-feed pipelines**, each spanning Controller → Service → DAO/XML → VO, one per external source system (ADAP, AHI, Birth, Cancer, CASA, CCD, Death, Doccs, HL7, ICCR, Medicaid, NDI, PRF, RAS, SSDM, STD, SYPH, TB, TTH, Tracking, …). Every layer repeats the same method-name skeleton per feed: `get<Feed>Doc`, `insertRaw<Feed>Document`, `insertStd<Feed>Document`, `get<Feed>DocumentForClericalReview`, `getRawDocAsHTML`/`sendEmailNotificationTo<Feed>Users`. This is the single largest duplication surface in the codebase, spanning `web/spring/dao/document/**` (44 DAOs), `web/spring/domain/document/**` (132 VOs, 76 of them loose at the top level with inconsistent sub-packaging), `web/spring/service/document/**`, and `person/DocumentController.java` (which alone has ~28 near-identical `getXxxDocAsHTML` methods, lines 213–662+). **Consolidation direction**: a generic/strategy-based document-feed abstraction (type → handler map) instead of one bespoke class per feed at every layer — a strong long-term target, but high-risk/high-effort given ~40x fan-out; do this after the modular-monolith boundary work, not before.
- **Repeated session/user-lookup boilerplate**: `HttpSession session = request.getSession(); UserObject userObject = (UserObject) session.getAttribute("UserObj");` (or equivalent) repeated **589 times across 58 of 65 controller files**. Candidate for a `HandlerMethodArgumentResolver` or `@ModelAttribute`-based interceptor.
- **Repeated file-upload handling**: 7 controllers (`ADAPNYCFeedBackController`, `ClepAuditResultsController`, `LaftCountReconcilleController`, `RecordSearchReviewController`, `DocumentsScanController`, `DatafluxUploadController`, `FileUploadController`) each independently handle `MultipartFile`/`MultipartHttpServletRequest` with no shared upload utility/base controller.
- **Ad hoc email-sending wrappers**: 30 service files wrap the shared `web/spring/util/SendEmail.java` utility with their own `sendEmail*` methods rather than a single shared notification service — e.g. near-duplicate `sendEmailToBureauDirector` logic exists independently in both `EPRFOOCDocumentServiceImpl.java:471` and `DocumentReviewTransactionHelper.java:512`.
- **Report/export generation duplication**: 9+ service files independently hand-build PDF (itext), Excel (POI via `AbstractXlsView`), and FreeMarker output rather than going through one shared report-building service. The 16 `Generate*` classes in `web/spring/view/review/**` each re-implement Excel header/style/row boilerplate on top of Spring's `AbstractXlsView` — e.g. 4 separate `Generate*ReportByDailyStaff` classes share an identical report shape.
- **Two CCD parsing implementations**: `CCDDocumentServiceImpl` and `CCDDocumentServiceDOMXPATHImpl` (1,256 lines) both implement CCD-document parsing via different strategies — worth confirming which is actually current before treating both as live.
- **Per-feature `*TransactionHelper` classes** (~20 of them, one per service subpackage: `PersonTransactionHelper`, `AssignmentTransactionHelper`, `DocumentTransactionHelper`, `RiaTransactionHelper`, etc.) repeat the same "wrap DAO calls in a transactional boundary" shape — a plausible candidate for a shared generic transactional-service base once the module boundaries are clearer.
- **Raw/Std VO pairing duplicated ~7+ times structurally**: `RawCCDDocVO`/`StdCCDDocVo`, `RawHL7DocVO`/`StdHL7DocVO`, `RawHIEDTHDocVO`/`StdHIEDTHDocVO`, etc. — each subpackage reinvents the "incoming raw record" vs. "standardized record" concept with its own field set instead of a shared base/interface.
- **JSP header/menu/footer includes are actually well-consolidated** (not a duplication problem): `common_includes.jsp`, `commonStylesIncludes.html`, `commonScriptsIncludes.html`, `MainMenuIncludes.jsp`, `Menu.jsp` are reused 90–150+ times each via `<%@ include %>`/`jsp:include`. The one real duplication here is cosmetic: the same one-line scriptlet `pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));` appears independently (not via include) in ~54 JSP files.
- **Two grid libraries coexisting**: legacy jqGrid (4.5.4, the default via `commonScriptsIncludes.html`) alongside a newer `commonagGridScriptsIncludes.html` (ag-Grid) used in only 8 places — evidence of an in-progress, incomplete UI library migration that predates this modernization effort.

---

## 5. Problematic Legacy Code

These are the patterns that will most complicate the planned Java 21 / Tomcat / modular-monolith / JPA migration.

**Direct database access outside the DAO layer** (the biggest single issue for a MyBatis→JPA migration, since it's invisible to any repository-based approach):
- `web/spring/dao/jdbc/impl/JdbcConnectionDAO.java` hands out raw `Connection` objects (`getConnectionObj()` from `appDS`, `getSqlServerConnectionObj()` — actually Oracle `eharsDS`, see §3) which 6+ services then use to build hand-rolled `PreparedStatement`/`CallableStatement` SQL.
- Confirmed raw JDBC (`Connection`/`Statement`/`PreparedStatement`) in **18 service files, ~60 occurrences**, heaviest in `ManualQuartzJobServiceImpl.java` and `LabProficiencyServiceImpl.java` (9 occurrences).
- Raw `CallableStatement` built directly in application code (not MyBatis) in `LabReportsServiceImpl.java:910` and `AddLabProficiencyDataVO.java:50`.
- One controller, `ExperimentalController.java:26,40`, `@Autowired`s a DAO (`CCDDocumentDAO`) directly, skipping the service layer entirely.

**God classes mixing many responsibilities:**
- `ManualQuartzJobServiceImpl.java` — **6,825 lines**, the largest file in the app. Combines Quartz job control, raw JDBC, JSch/SFTP file transfer, JavaMail, and ECLRS/HIV lab-record validation in one `@Service`. Individual methods run to **1,327 lines** (`getRawDocsList`) and **752 lines** (`storeDocumentMatchResults`).
- `person/DocumentController.java` — **1,967 lines**, 60 methods, 55 `@RequestMapping`s, 43 `@Autowired` fields — effectively an omnibus controller for nearly every document type.
- Other files over ~1,500 lines: `FileUploadServiceImpl.java` (3,141), `HL7DocumentServiceImpl.java` (2,739), `QuartzSchedulerTransactionHelper.java` (2,394, despite the "helper" name), `CCDDocumentServiceImpl.java` (2,288), `assignment/AssignmentController.java` (1,691), `LabReportsController.java` (1,338), `LabCommonUtils.java` (1,506), `AssignmentReabstractUtil.java` (1,180).
- `web/spring/bo/**` — all 4 "business object" classes mix data access, `HttpServletRequest` handling, and presentation-string encoding (e.g. `ProcessSearchResults.java:42` injects a literal marker string `"~#&^italics~#&^"` into a data field for the JSP to later interpret) inside classes with no clean single responsibility.

**Business logic embedded in controllers:**
- `DocumentController.getClericalDocumentAsPerson` (~168 lines) and `.getACRFFormAsHTML` (~151 lines) branch on document-type strings and assemble page models by calling 8+ services sequentially — this orchestration belongs in a service.
- `AssignmentController.getAssignmentReportMatrix` (~118 lines) builds `Calendar`-based month-range/report-matrix logic inline.
- `LabReportsController.java` (1,338 lines, ~86 methods) reads like an entire un-split "Lab Administration" module living inside one controller.

**Old Spring/Java patterns that will need addressing:**
- Field injection (`@Autowired` on fields, never constructors) is universal — 43 fields in one controller alone.
- Manual `Map<String,Object>` model + `HttpServletRequest` parameters instead of typed binding/`ResponseEntity`, across the large majority of the 65 controllers.
- Hand-built `BindingResult`/`MapBindingResult` instead of `@Valid` + injected `BindingResult`.
- Legacy Spring `AbstractPdfView`/`AbstractXlsView` subclasses (17 classes) instead of a modern export-generation service.
- `SimpleDateFormat`, `java.util.Date`/`Calendar` throughout (no `java.time` usage found in sampled god classes) — direct Java 8→21 friction, though low-risk to modernize incrementally.
- `org.json.simple.JSONObject` used instead of the Jackson already configured in Spring MVC.

**WebLogic-specific coupling:**
- Three datasources configured via `weblogic.jndi.WLInitialContextFactory` JNDI lookups in `spring-config.xml` (`appDS`, `ursDS`, `eharsDS`) — these must become plain JNDI or Tomcat-managed pooled datasources under the Tomcat migration.
- `aop/intercepting/ControllerLayerInterceptingAspect.java` imports the WebLogic-internal class `weblogic.servlet.internal.ServletRequestImpl` directly and type-checks against it — this aspect silently no-ops under any other servlet container (including Tomcat) today, which is both a portability blocker and a currently-latent behavior gap.
- `weblogic.xml` (context-root, session replication, JSP precompile flags) and `wlfullclient` Maven dependency are WebLogic-only artifacts with no Tomcat equivalent — need a replacement/removal plan.
- `webapp/WEB-INF/properties/pom.properties` contains a **hardcoded WebLogic deployment password** (`wlpassword=password77`) — a security hygiene issue independent of the modernization effort, worth flagging to the team regardless of migration timing.

**Excessive/monolithic XML configuration:**
- `spring-config.xml` is a single ~480-line file mixing datasource config, 3 MyBatis session factories, ~13 Quartz job/trigger bean pairs, MVC/JAXB/Jackson wiring, and an unused JMS namespace declaration — a good candidate to split by concern (datasource, MyBatis, scheduling, MVC) even before any Spring Boot migration.
- `standardization/**` and large chunks of `assignment/`, `review/`, and `CommonDAO.xml` rely heavily on Oracle stored procedures (`PKG_STANDARDIZATION.*`, `PKG_CONSOLIDATION.*`) — 39 mapper XML files, ~123 `CALLABLE` statements total — meaning substantial business logic lives in the Oracle database itself, not in Java, which the eventual JPA migration cannot simply "port."
- `CommonDAO.xml:166-168` and `:` (notification list query) use `${tableName}`/`${COLUMN_NAME}` raw string substitution (MyBatis `${}`, not `#{}`) — this is a SQL-injection-shaped pattern even if current call sites only pass internally-controlled values; worth a security review independent of modernization.

---

## 6. Modernization Direction

**This section documents direction only — no migration has been performed.**

| From | To |
|---|---|
| Java 8 | Java 21 |
| WebLogic | Tomcat |
| Single monolithic package tree, XML-configured Spring | Modular monolith, one module per business area |
| MyBatis everywhere | JPA/Hibernate for simple CRUD; MyBatis retained for complex/Oracle-specific/stored-procedure queries |

### Candidate business modules (derived from the actual package/JSP structure, not invented)

Based on the controller/service/dao/JSP groupings observed throughout this document, these are the natural module boundaries already implicit in the codebase:

- **authentication / security** — `security/**`, `HCSSecurityFilter`/`ServiceConsumerSecurityFilter` integration, `UserObject`, login JSPs
- **administration** — user/role/region management, system messages, `administration/**`
- **person** — core person/patient record, merge/un-merge, `person/**`
- **document-intake** — the ~40-feed raw/std document pipeline (ADAP, HL7, CCD, Medicaid, Cancer, Death, Birth, Doccs, etc.) — likely the largest module, possibly worth further splitting by feed category (labs, vital records, external-agency feeds) rather than one giant module
- **assignment** — case assignment/reassignment/reabstraction workflow, `assignment/**`
- **lab** — lab reports, proficiency, LAFT tracking, viral load, `lab/**`
- **review** — clinical/clerical/RIA/perinatal/NBS/cluster-partner review workflows, `review/**`
- **standardization** — facility & provider name/address standardization, `standardization/**`
- **reporting** — cross-cutting report generation (PDF/Excel/FreeMarker), `report(s)/**`, `web/spring/view/**`
- **scheduling / batch** — Quartz jobs, `service/schedulers/**`, `schedulers/spring/dao/**`
- **messaging / notifications** — internal message box, email notification, `messageBox/**`
- **uploads / scanning** — file upload and document scanning intake, `uploads/**`, `scan/**`
- **search** — global/cross-entity search, `search/**`
- **integrations** — Sierra, HCS/OAuth webservice client, partner-service feeds — `sierra/**`, `webservice/**`, `partnerService/**`

### Suggested future package structure (illustrative — not to be applied yet)

```
com.nyehms
├── person
│   ├── controller
│   ├── service
│   ├── domain
│   └── repository
├── documentintake
│   ├── controller
│   ├── service
│   ├── domain
│   └── repository
├── assignment
│   ├── controller / service / domain / repository
├── lab
│   ├── controller / service / domain / repository
├── review
│   ├── controller / service / domain / repository
├── standardization
│   ├── controller / service / domain / repository
├── administration
│   ├── controller / service / domain / repository
├── authentication
│   ├── controller / service / domain / repository
├── reporting
│   ├── controller / service / domain
├── scheduling
│   ├── job / service / repository
├── integrations
│   ├── hcs / sierra / partnerservice
└── shared
    ├── configuration
    ├── exception
    └── utility
```

Given the ~40x document-feed duplication (§4), the `documentintake` module is the single highest-leverage place to introduce a generic/strategy-based abstraction rather than porting all ~40 feed-specific class families 1:1 into the new structure.

---

## 7. MyBatis → JPA Migration Candidates

**No conversion has been performed.** Classification below is based on scanning all ~78 in-scope mapper XML files for dynamic SQL (`<if>/<choose>/<foreach>`), `statementType="CALLABLE"`, and Oracle-specific syntax (`CONNECT BY`, `ROWNUM`, `LISTAGG`).

### Easy JPA Candidates (single-table/two-table CRUD, no dynamic SQL, no stored procs)

~20 of the 44 `document/` feed DAOs have zero dynamic-SQL tags and do plain parameterized CRUD on one RAW_/STD_ table pair. Representative, fully-verified examples:
- `document/BirthDocumentDAO.xml` (68 lines, 8 static statements)
- `document/TBDocumentDAO.xml` (108 lines, one 2-table join, all static SQL)
- `document/CancerDocumentDAO.xml`, `CASADocumentDAO.xml`, `MedicaidDocumentDAO.xml`, `NDIDocumentDAO.xml`, `QualCareDocumentDAO.xml`, `RASDocumentDAO.xml`, `SSDMDocumentDAO.xml`

These are the pragmatic **first wave**: predictable columns, no PL/SQL, no `CALLABLE`, minimal joins. Moving these to Spring Data JPA repositories would touch a well-isolated slice of the `document` module without disturbing the DAOs that carry real complexity.

A second, moderate tier (static SQL but a 2–4 table join, or a single `CALLABLE`/`ROWNUM`/`LISTAGG` usage) — most of the remaining `document/` DAOs plus `report/ProviderReportDAO.xml`, `report/SearchHistoryDAO.xml`, `report/StatusLabReportsDAO.xml` — could follow as a **second wave**, likely needing a native-query escape hatch for the one non-standard clause per file rather than blocking the whole file from migrating.

### Keep MyBatis Temporarily (complex, Oracle-specific, or stored-procedure-driven)

- **`standardization/facility/**` and `standardization/provider/**`** (16 files) — almost entirely built on `CALLABLE` calls into `PKG_STANDARDIZATION.*` Oracle packages (e.g. `SubProviderAndAddressDAO.xml`: 17 dynamic tags + 3 CALLABLE; `RawProviderAddressDAO.xml`: 16 dynamic + 5 CALLABLE). Real business logic lives in the database here — not portable to JPA without first extracting that logic from PL/SQL.
- **`assignment/AssignmentDAO.xml`** (1,055 lines, 81 statements, 31 dynamic tags, 14 CALLABLE) and **`assignment/reports/AssignmentReportsDAO.xml`** (487 lines, 25 dynamic tags) — large dynamic search/report queries.
- **`review/RecordSearchReviewDAO.xml`** (uses `CONNECT BY LEVEL` for a number generator) and **`review/RiaRecordsReviewDAO.xml`** (1,124 lines, heavy CALLABLE).
- **`document/HL7DocumentDAO.xml`** (1,254 lines) and **`document/CCDDocumentDAO.xml`** (1,433 lines) — both use `CONNECT BY` twice: once as a sequence-value generator loop, once as a CSV-split trick via `regexp_substr(...) CONNECT BY LEVEL`.
- **`lab/LabReportsDAO.xml`** (CSV-split via `CONNECT BY`, 3 CALLABLE), **`lab/LaftCountReconcileDAO.xml`** (9 CALLABLE), **`lab/LaftDetailsDAO.xml`** (6 CALLABLE).
- **`CommonDAO.xml`** — the shared/cross-cutting mapper (permissions, session, address/county/state lookups, error logging). Contains 2 `CALLABLE` calls, heavy `ROWNUM`/`LISTAGG`/`DECODE` usage, nested dynamic `<choose>`, and — notably — a fully dynamic table-name substitution (`${tableName}`, lines ~166-168) that cannot be represented as a static JPA query at all and should stay MyBatis (or be redesigned) regardless of migration timeline. Because it's injected broadly across nearly every feature, it's also a poor first-mover even where individual statements look simple.
- Any mapper using `statementType="CALLABLE"` (39 files, ~123 occurrences total) — these need explicit `StoredProcedureQuery`/native handling if ever ported, not a straight `@Query`/derived-method JPA translation.

Raw JDBC usage (`JdbcConnectionDAO`, direct `CallableStatement` construction in `LabReportsServiceImpl.java`/`AddLabProficiencyDataVO.java`) sits **outside MyBatis entirely** and needs its own remediation plan before or alongside any MyBatis→JPA work, since neither MyBatis nor JPA tooling has visibility into it today.

---

## 8. Recommended Cleanup Order

1. **Verify and remove confirmed dead code** — start with the **High**-confidence items in §3 (~~`AllJaroLoader`/`RunAllJaroLoaderJob` + its broken JSP buttons~~ — done, `77b99f6`; next: `AuthFilter`, the unwired `jms/**` package, `SSDMDocValidator`/`ADAPDocValidator`, `UpperCase`/`UpperCaseValidator`, `GetTblNameMap`'s dead body, `domain/test/**` + `TestController`, `HtmlMethods`, `SubProviderAddressDAO`, `EHARS_VARIABLES`). These are low-risk, well-verified, and immediately shrink the surface area for every subsequent step.
2. **Fix the two "silently broken" findings** discovered during this analysis, since they're cheap and currently misleading anyone reading the code: the `controller.reports` vs `controller.report` pointcut typo in `ControllerLayerInterceptingAspect`, and the `/getUserGuide` → missing `UserGuide.jsp` view. (Decide fix vs. removal with the team — these aren't cleanup so much as bugs uncovered along the way.)
3. **Do a full sweep of `CommonConstants.java`/`EharsVariableConstants.java`** beyond the sampled subset in §3 — this file pattern (many unused named constants) is likely to repeat, and a scripted "grep every constant name" pass is cheap to run exhaustively.
4. **Consolidate obvious duplicate/redundant code** (§4) that's low-risk to touch without behavior change: the session/user-lookup boilerplate (589 occurrences) via an interceptor/argument-resolver, the file-upload handling duplication (7 controllers), and the copy-pasted `buildDate` scriptlet (54 JSPs) via a single request attribute set once per request.
5. **Address the WebLogic-coupled `ControllerLayerInterceptingAspect`** (`weblogic.servlet.internal.ServletRequestImpl` type-check) — this needs a fix before a Tomcat migration can work correctly, not just before it's "nice to have."
6. **Clean package/config boundaries** — split `spring-config.xml` by concern (datasource, MyBatis, scheduling, MVC), fix the `webservice.oauth`/component-scan base-package mismatch, and remove the hardcoded WebLogic password from `pom.properties`.
7. **Prepare modular monolith structure** — carve out module boundaries per §6 starting with the most isolated modules (e.g. `standardization`, `lab`) before attempting the large, ~40x-duplicated `documentintake` module, which likely needs its generic-abstraction redesign (§4) done *before* or *during* the module split rather than after.
8. **Upgrade Java/Spring dependencies** (Java 8 → 21, dependency version bumps) — do this after dead-code removal and package cleanup so the upgrade touches a smaller, better-understood codebase.
9. **Move deployment from WebLogic to Tomcat** — contingent on #5 and #6 above (WebLogic-specific filters, JNDI datasource config, `weblogic.xml`).
10. **Gradually migrate the "Easy JPA Candidate" MyBatis mappers** (§7, ~20 single-table `document/` DAOs first) to Spring Data JPA, module by module as each module boundary is established in step 7.
11. **Handle complex MyBatis/Oracle queries and stored-procedure-backed modules separately** (§7 "Keep MyBatis" list) — treat `standardization/**`, `CommonDAO.xml`, and the CALLABLE-heavy mappers as a distinct, longer-horizon workstream, since real business logic in Oracle PL/SQL needs to be understood and possibly extracted before those can move.

---

## Summary

**Top unnecessary-code candidates:** (`AllJaroLoader`/`RunAllJaroLoaderJob` already removed), `AuthFilter` (dead, disabled in `web.xml`), the entire unwired `jms/**` package, `domain/test/**` + `TestController` (leftover scratch code), `utils/HtmlMethods.java` (zero callers), `SubProviderAddressDAO` (superseded), and a likely-dead `sqljdbc4` Maven dependency.

**Top redundant-code areas:** the ~40-feed document-intake pipeline duplicated across DAO/Service/Domain/Controller layers (biggest by far), the 589x-repeated session/user-lookup boilerplate in controllers, and the ad hoc per-service email/report-generation wrappers instead of shared abstractions.

**Biggest legacy/problematic areas:** `ManualQuartzJobServiceImpl.java` (6,825-line god class mixing JDBC/JSch/mail/business logic), 18 services doing raw JDBC outside the DAO layer, `person/DocumentController.java` (1,967-line omnibus controller), universal field-injection/`Map`-based-model Spring MVC patterns, and the WebLogic-internal-class coupling in `ControllerLayerInterceptingAspect`.

**Suggested future modules:** person, document-intake, assignment, lab, review, standardization, administration, authentication, reporting, scheduling, messaging, uploads/scanning, search, integrations — all derived directly from existing package/JSP groupings, no invented boundaries.

**Best next cleanup task:** the AllJaro removal is done (`77b99f6`). Pick the next High-confidence item from §3 (e.g. `AuthFilter` or the unwired `jms/**` package), one small reviewable step at a time.

---

## Appendix: Full-codebase dead-code scan (static, name-based)

Scripted scan of all Java, mapper XML, JSP, properties and config. Evidence is name search only: reflection, string-built SQL IDs, string-built view names and the external static JS are **not** covered. "Done" items are recorded in the Cleanup Log.

**Zero-reference classes (High) — DONE:** removed `HtmlMethods`, `DBUtils`, `CommonException`, `CustomClientException`, `SSDMDocValidator`, `ADAPDocValidator`, `SubProviderAddressDAO` (+ XML), `AuthFilter`, `UpperCase`/`UpperCaseValidator`, `TestController` + `domain/test/**`, the empty `SystemMessagingController` stub, and the `jms/**` package (with `EnQueMessageTest`/`DeQueMessageTest`). Also removed `bo/GetTblNameMap` and its 3 unused injection points (incl. one in `SearchEngineTest`). The `jms` XML namespace declaration in `spring-config.xml` was removed in the fourth pass.

**Unused service (Medium) — DONE:** removed `CCDDocumentServiceDOMXPATH` + `CCDDocumentServiceDOMXPATHImpl`; `CCDDocumentServiceImpl` is the live CCD parser.

**DAO methods with no callers but with mapper SQL (Medium) — DONE:** removed 34 interface methods and their mapper XML statements (SchedulerJobsDAO 11, CCDDocumentDAO 4, DocumentDAO 4, LabReportsDAO 3, NycDeathDocumentDAO 2, SubProviderDAO 2, and one each in PcrfDocDAO, ClericalReviewDAO, ClusterDocumentDAO, NycPRFPartnerDAO, ClusterPartnerReviewDAO, SubFacilityDAO, RawProviderAddressDAO, SubProviderAndAddressDAO).

**Mapper XML statements with no interface method (Medium) — DONE:** removed all 26 orphan statements (17 first, then the 9 whose names also exist in other DAOs/services; none was called by string ID or exists on its own mapper interface).

**Unused constants (High) — DONE:** removed 27 unused constants from `CommonConstants`. `LOAD_ALLJARO_SCHEDULER` and a few `LOAD_*` names were kept because they still match text in properties/other files.

**Unreferenced JSPs — DONE (18 in the second pass, the last 7 in the fourth):** removed 18 JSPs with no reference of any kind (even as a substring, case-insensitive) in Java/XML/JSP/properties: `administration/InProgressSystemMessages`, `assignment/{AssignmentsCountsSuper,AssignmentsCountsWorker,psMessageReportsMatrix}`, `common/SuccessPage`, `document/{EditLabDocument,OldPrfProviderDetails,clusterPartner/ClusterPartnerDocumentDisposition}`, `messageBox/messageListContentSent`, `person/{ClusterPartnerPersonView,HepatitisDocument,ePRFOOCclericalReview}`, `reports/{AcceptedTestDomain,addLabPermitAndInstrument,labFacilitiesGrid}`, `review/nbs/{nbsComparisonPage,nbsHivStatusReview}`, `standardization/facility/DeficientReports`. **The last 7 (`common/Footer`, `document/ClusterPartner`, `document/ScannedDocument`, `person/DocumentView`, `person/OOCClericalReview`, `standardization/facility/NewPageGrand`, `worker/ACRFImmunoassays`) were deleted after confirming their names only matched as substrings of unrelated names and that no dynamic view or include paths exist.**

**Commented-out code — DONE:** removed ~3,300 whole-line `//` commented-code lines (runs of 2+ code-looking lines) across 98 Java files and 69 commented-out markup blocks across 18 XML files (mapper XML, `spring-config.xml`, `web.xml`). Not touched: block comments (`/* */`), prose comments, JSP comments, and single isolated commented lines. Note: this also removed a commented-out alternate JNDI name `BHAE_NYEHMS_PRODUCTION_DS_ONE` in `spring-config.xml` — restore from Git history if you need it as a deployment note.

**Corrections to §3:**
- The `jms/**` package is not fully isolated: `src/test/.../EnQueMessageTest` imports `EnQueMessage` (and `DeQueMessageTest` exists alongside it). Removing the package requires removing those tests too; note `pom.xml` sets `skipTests=true`, which would hide a test-compile break only if test compilation is also skipped — verify.
- `web/spring/domain/cryptocardImage/ImageServiceImpl` is live (injected via `ImageService` in `ImageController`), though it sits in the wrong layer (`domain`).

**Remaining unused-method candidates (Medium) — mostly DONE in the fourth pass:** removed `ProcessSearchCriteria.getPersonSearchCriteriaMap`, `ClusterDocumentServiceImpl.checkforValidDemoInfo`, `HIEDTHDocumentServiceImpl.getMapFromNodeMap`, `PRFDocumentServiceImpl.checkDinFormat`, `DocumentSummaryReportsServiceImpl.loadSQLQueries`, `ManualQuartzJobServiceImpl.loadRawFacilityMappingResults`, `PreMatchHITSchedulerHelper.writeRawDiagnosisToStdLayer/writeRawReviewedList`, `CommonUtil.convertStringToDateFormat/convertStringToTimeStamp`, `DateUtil` (4), `InitializeUtil` (4), `LabCommonUtils.getOldNycColumns`. **Still open (skipped as annotated, overloaded or comment-referenced; verify by hand):** `NycDeathDocumentServiceImpl.isOldNycDeathFile`, `QuartzSchedulerTransactionHelper.processMultiStepDocumentsToGether` and `storeDocumentMatchDataToStandardAndMatchResultsTable`, `DocumentTransactionHelper.checkAndAddFacilityAddress/checkAndSubProviderAddress`, `ManualQuartzJobServiceImpl.populateCountyValue`, `AssignmentController.parseAssignmentsByRegionByStatus`. `GetSQLSession.getSqlSession` was kept deliberately (it is a bean-style util; only its getter was unused). **Unused Maven dependencies — DONE:** removed `sqljdbc4` and `maven-plugin-api:2.0` (its only transitive was a test-scope junit; `pom.xml` declares its own junit 4.10). `log4j-*`, `ojdbc6` and `hapi-*` are used indirectly and must stay.

**Notes for future cleanup and the MyBatis migration:**

- **Mapper XML is loaded by convention, not by config.** There is no `mapperLocations` in `spring-config.xml`; `MapperScannerConfigurer` binds each interface and MyBatis loads the XML named after the interface from the same package path. An XML file with no same-named interface (like the removed `StdDocumentDAO.xml`) is never loaded. Keep this in mind when converting mappers to JPA or moving packages: XML files must move together with their interface.
- **Tooling lesson (fourth pass):** an automated method remover once removed the wrong span because a single 37,000-character string literal in `DocumentSummaryReportsServiceImpl` defeated a regex-based comment/string mask (Perl regex repetition limit), so braces inside the string were counted. It was caught by a declaration-diff check (a live Override-annotated method had vanished), the file was restored from Git, and the removal was redone with a character-level lexer. Every code-removal pass since then is verified by (a) comment-stripped diff showing zero added lines, (b) a declaration-name diff showing exactly the intended names removed, and (c) brace balance under the lexer.

**Open findings after the sixth pass (final rescan):**

- **Nothing else is flagged by the automated scans** (zero-reference classes, no-caller DAO methods, orphan mapper SQL, unused `resultMap`s, constants, imports, fields, JSPs, dead mapper XML files).
- **`scanning/ShowScannedFileNames.jsp`** is still returned by `DocumentsScanController.getScannedDocumentFileNames`; nothing in the repo links to that endpoint, but the external static JS might. Keep unless access logs show the endpoint is unused.
- **Skipped unused-method candidates (verify by hand):** `NycDeathDocumentServiceImpl.isOldNycDeathFile`, `QuartzSchedulerTransactionHelper.processMultiStepDocumentsToGether` and `storeDocumentMatchDataToStandardAndMatchResultsTable`, `DocumentTransactionHelper.checkAndAddFacilityAddress/checkAndSubProviderAddress`, `ManualQuartzJobServiceImpl.populateCountyValue`, `AssignmentController.parseAssignmentsByRegionByStatus`.
- **Properties files are live — do not prune by name search.** `SchedulerMetaData.properties` is the LOCAL-stage fallback for scheduler metadata (`ManualQuartzJobServiceImpl.getSchedulerMetaData()` loads it via `getResourceAsStream` when `stage=LOCAL` and `loadSchedulersDataFromProperty=Y`); keys are then read by literal name from the resulting map, so dead keys (e.g. the `ALLJARO_*` ones) could be pruned but the file itself must stay. `CommonEnvironment.properties` keys are looked up dynamically (`getProperty(reportType+"_report")`, `getProperty(parameterName+"_HEADERS")`, `getProperty(testResults)` for values such as `NRCT`), so keys that "appear unused" by name are live. `messages_en.properties` codes may also be resolved dynamically. `pom.properties` keys belong to the deploy setup. (An earlier version of this appendix wrongly called `SchedulerMetaData.properties` unused.)
- **False positives to ignore in future scans:** Spring beans `multipartResolver`, `viewResolver`, `applicationContextProvder`, `freeMarkerConfigurationFactory` (found by fixed name or type by the framework).
- **Tooling notes:** (1) the DAO-method remover\x27s Javadoc walk-up once mistook a one-line `/*...*/` comment for the end of a Javadoc and would have deleted a large span; caught in the dry run (74 live statements suddenly reported orphan), fixed, and re-verified with an interface-aware method-name diff. (2) The field remover initially left an `@Autowired` line behind for private fields; caught in the dry run, fixed, and re-verified by checking that every removed line was an import, `@Autowired` or a field declaration and that no `@Autowired` is left dangling.

