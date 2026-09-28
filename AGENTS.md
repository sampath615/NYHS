# AGENTS.md — NYEHMS

This document orients engineers and coding agents working in the NYEHMS codebase. It describes the **current** system as it actually exists (not an idealized version), flags cleanup/modernization candidates, and lays out the direction of a planned modernization. **Nothing described here as a "candidate" has been changed unless it appears in the Cleanup Log below** — the rest is analysis only.

> **What is NYEHMS?** Based on package names (`gov.ny.state.doh.bhae.nyehms`), constants (eHARS, LAFT, ADAP, PRF, AHI, partner services), and document-feed types (HIV/STD labs, cancer, birth, death, Medicaid, DOCCS), this is a NY State Department of Health surveillance/case-management system — most plausibly for HIV/AIDS (eHARS-related) and related communicable-disease reporting, receiving data feeds from dozens of external source systems (labs, hospitals, NYC DOH, Medicaid, corrections, cancer registry, vital records) and supporting case worker assignment, review, clerical processing, and reporting workflows. Deployed under WebLogic context root `doh2/applinks/nyehms`.

## Cleanup Log

Working rules: one small reviewable cleanup step per commit; no unrelated refactors or reformatting; verify references across Java, Spring XML, Quartz config, JSP, JS before removing; preserve existing line endings (source files are CRLF).

**Build baseline: build confirmed at `81f00ac` (HEAD after the eighth cleanup pass); deployment confirmed through `2f13871`.** The project owner reported a working deployment after each of the first stages (`77b99f6`, `5e78269`, `e8840de`, `da444ee`, `2f13871`), a working build for `88f2727`, and a good build of the current HEAD after the eighth pass. The exact build command, JDK/Maven versions and output were not captured in this repo, and the agent machine has no Maven, but it does have a JDK 21 (`~\/.jdks\/ms-21*`, also IntelliJ's bundled JBR), so a **compile check** is possible: `javac --release 8 -proc:none` over all of `src/main/java` with every jar in `~/.m2` on the classpath. That baseline reports **299 errors in 23 files**, all caused by jars missing from the local Maven cache (HL7 `hapi`, Oracle JDBC, Symantec ScanEngine, `weblogic.*`, CRYPTOCard, HIE endec) plus an slf4j version clash — none in controllers except the WebLogic aspect. Compare error *sets* (file + message, ignoring line numbers) before and after any change; any new error is yours. This is not a substitute for Maven, so recording a clean `mvn clean package -DskipTests` (JDK 8; deps such as `wlfullclient` and `gov.nyhealth.comdir` must resolve from a local/internal repo) is still worth doing. Screen-level regression checks of the changed mappers (scheduler page, assignments, document review, lab reports, provider/facility standardization, scan and CCD screens) are not yet recorded.

**Status / resume here (end of the sixth cleanup pass):**

- **Code state:** dead-code cleanup is complete per the automated scans. Cleanup commits, oldest first: `77b99f6`, `5e78269`, `e8840de`, `da444ee`, `2f13871`, `88f2727`, `020ee5b`, `5d6bfd0`, `b84cc85` (each followed by an AGENTS.md log commit). Nothing has been pushed from the agent session.
- **Verification status:** the project owner confirmed a working deployment through `2f13871`, a working build for `88f2727`, and a good build of `HEAD` (`81f00ac`) after the eighth pass, which covers `020ee5b`, `5d6bfd0`, `b84cc85`, `e9f249e` and `8746622`. **Deployment/startup of those five later commits has not been explicitly confirmed** — a compile success does not exercise mapper XML or JSP changes, so deploy `HEAD` and click through the affected screens. Two annotation leftovers from earlier DAO cleanups were fixed on GitHub by the project owner (`f3eb72b`, `06b8993`); an audit of all touched Java files found no others (see Tooling notes). Each cleanup commit is self-contained, so a failure can be isolated with `git revert <hash>`.
- **Still not recorded anywhere:** a clean `mvn clean package -DskipTests` result from a machine with JDK 8 + Maven (the agent machine has neither), and screen-level regression checks (scheduler page, assignments, document review, lab reports, provider/facility standardization, scan and CCD screens). Also confirm from access logs that the removed root-level applet files (`AppletCall.jsp`, `index.jsp`, `launch.html`, `launch.jnlp`) were not requested directly.
- **Remaining cleanup:** none flagged by the automated scans after the seventh pass. What is left is documentation and decisions: refresh the stale sections of this file (§0 counts, §1 tree, §3 candidate table, §8 step 1, Summary still describe already-removed items as candidates), decide fix-vs-remove for the remaining small bug in §8 step 4 (`controller.reports` vs `controller.report` pointcut; the `/getUserGuide` bug went away with the User Guide removal), and check access logs for the removed root-level applet files. Prose comments were intentionally kept.
- **Next stage (modernization, not started):** in this order — (1) record a real Maven build baseline; (2) ~~remove the hardcoded WebLogic deploy password from `pom.properties`~~ — file removed in the tenth pass; **still to do: rotate that password** (it remains in Git history); (3) fix or remove `ControllerLayerInterceptingAspect` (WebLogic-internal `ServletRequestImpl` type check, plus the `controller.reports` vs `controller.report` pointcut typo); (4) split `spring-config.xml` by concern; (5) then the Java 21 / Tomcat / modular-monolith / MyBatis→JPA plan in sections 6–8. Keep the working rules above: one small reviewable step per commit, verify references before removing, no unrelated refactors.
- **Testing status (important for modernization):** the project has **no automated tests** after the ninth pass. The removed suite was scratch-level (16 `@Test` methods needing a live database, never run by the build). Before the Java 21 / Tomcat / JPA work, plan for characterization or integration tests around the areas you will touch (e.g. the ~20 "easy JPA" document DAOs and the MyBatis mappers), and add `junit`/`spring-test` (or JUnit 5) back to the pom **with `test` scope** when you do.
- **Working conventions used so far:** dry-run first, then apply; verify with comment-stripped diffs (zero added code lines), declaration-name diffs, brace balance using a character-level lexer to mask comments and strings (regex masks are unreliable — a 37,000-character string literal once defeated one), and XML well-formedness checks; commit code first, then a separate AGENTS.md commit that records the code commit hash.

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
| `e9f249e` | **Seventh cleanup pass:** deleted `scanning/ShowScannedFileNames.jsp` (its controller method had been removed with the earlier comment sweep, so it became unreferenced; the earlier note saying a live method returned it was stale); removed 6 unused methods — private `ManualQuartzJobServiceImpl.populateCountyValue`, `DocumentTransactionHelper.checkAndAddFacilityAddress`/`checkAndSubProviderAddress`, `QuartzSchedulerTransactionHelper.storeDocumentMatchDataToStandardAndMatchResultsTable`, `AssignmentController.parseAssignmentsByRegionByStatus`, and public `QuartzSchedulerTransactionHelper.processMultiStepDocumentsToGether` (with their `@Transactional`/Javadoc); removed the dead AllJaro entries from the LOCAL fallback `SchedulerMetaData.properties` (`AJJARO_REFERENCE_ID_POSITION`, `ALLJARO_*` x7, `LAST_ALL_JARO_SCHEDULER_RUN_DATE`, `LOAD_ALLJARO_SCHEDULER`) and the then-unused `CommonConstants.LOAD_ALLJARO_SCHEDULER`; 2 freed imports. | `NycDeathDocumentServiceImpl.isOldNycDeathFile` was a scan false positive (it is called from live code at line 377) and stays. DB rows for the removed scheduler keys (if any) are outside this repo. |
| `8746622` | **Eighth cleanup pass — removed the User Guide / context-sensitive help feature:** deleted `controller/userGuideHelp/ContextSensitiveUserGuideHelpController` (endpoints `/getUserGuide` [broken — its view never existed], `/getWebUserGuide`, `/getWebUserGuideFields`, `/help/getHelpPage`), the 3 `userguide/` JSPs (`webUserGuide`, `webUserGuideContent`, `webUserGuideFieldContent`), the 25 `help/**` page JSPs, 70 empty `<helpContext .../>` markers in 12 JSPs (administration, reports and facility/provider standardization pages), the "USER GUIDE" button in `common/Menu.jsp`, the `js/userguide/webUserGuide.js` include in `email/helpLine.jsp`, and the empty "Help Manual" overlay `div` in `common/commonScriptsIncludes.html`. This also resolves the `/getUserGuide` bug from section 3. | **Assets outside this repo still exist and are now unused:** the external static path (`${staticPath}`) likely still holds `js/userguide/webUserGuide.js`, `css/userguide/webUserGuide.css`, `docs/UserGuideNyehms_07_15_2019.pdf`, the `userGuideModal()` function and the script that renders `<helpContext>` icons/`referenceTarget` help — clean those up where that content is deployed. The `clusterHelpIcon` definition tooltips/dialogs (`document/ClusterDocument.jsp`, `uploads/uploadCluster.jsp`) are a separate mechanism and were kept. The `HELPLINE` menu item and email form are a separate feature and were kept. |
| `f4301bd` | **Ninth cleanup pass — removed all JUnit tests:** deleted the whole `src/test` tree — 17 test classes (`web/junit/**`: `AdministrationTest`, `SearchManageProfileTest`, `SystemMessageTest`, `AssignmentModuleTest`, `DocumentTest`, `PersonTest`, `SearchEngineTest`, 5 facility/provider standardization tests, `CommonTest`, `DBConnectionTest`, `AjaxCalls`, `BuildTests`), their 16 `@Test` methods, and the test resources (`spring-context-junit.xml`, a test copy of `messages_en.properties`); deleted the then-unused `web/spring/util/GetSQLSession` (its only users were a test and the test Spring config); removed the test-only `junit:junit:4.10` and `spring-test` dependencies (both were compile-scope, so they were being packaged into the WAR) and the `maven-surefire-plugin` block (`skipTests=true`, `groups=BuildTests`) from `pom.xml`. Also in this pass: removed the now-unused `Transactional` import in `NycPRFPartnerDAO` (left over after the two GitHub fixes). The unused `EharsVariableConstants.EHARS_VARIABLES` map was removed and then **restored at the project owner's request** (see section 3). | The tests were never run by the build (`skipTests=true`) and needed a live database and the full Spring context; recoverable from Git history (`e8740b6`). **There are now no automated tests at all** — see the note at the end of this file before starting any migration. |
| `f67f99c` | **Tenth cleanup pass — removed `pom.properties` and its Maven plugin:** deleted `src/main/webapp/WEB-INF/properties/pom.properties` and the `properties-maven-plugin` block in `pom.xml` that read it at the `initialize` phase (with a Windows-only backslash path). Verified first: no live pom content used any of its keys — the only consumer of `wluser`/`wlpassword`/`wl.deploy.url`/`project.*` was the already commented-out `weblogic-maven-plugin` deploy block — and nothing else in the repo referenced the file. The WAR name and packaging are unchanged (`artifactId` `nyehms`, packaging `war`, `finalName=${artifactId}`). This also stops the WebLogic deploy password from being packaged into `WEB-INF/properties/` of every WAR. | **The password is still in Git history** (3 commits, and the repo is on GitHub) — **rotate it** wherever that WebLogic admin account is used; rewriting history is optional and needs a force-push. The commented-out `weblogic-maven-plugin` block in `pom.xml` was left alone (it now refers to properties that no longer exist; delete it or re-supply the values via `settings.xml`/environment variables if local deploys are ever wanted again). |
| `abd0146` | **Eleventh step — duplicate-code consolidation #1 (controller session boilerplate):** added `web/spring/security/UserObjectArgumentResolver` (a `HandlerMethodArgumentResolver`, registered under `<mvc:annotation-driven>` in `spring-config.xml`) that returns `request.getSession().getAttribute("UserObj")` — same semantics as the old code (session created if missing, `null` if the attribute is absent). Converted **551 `@RequestMapping` handlers in 59 controllers**: the local `UserObject userObject = ... getAttribute("UserObj")` declaration became a `UserObject userObject` parameter appended to the signature, and the `HttpSession session = request.getSession()` line was dropped where `session` was otherwise unused (457 times). Existing parameters and the `if(userObject==null) ...` fallbacks were not touched. `ControllerLayerInterceptingAspect` now skips null arguments (it would otherwise throw a `NullPointerException` on a null `UserObject`). Verified by a full `javac --release 8` compile whose error set is identical to the baseline (see build note), a probe proving controllers are really type-checked, and a diff audit (every added line = an old signature + the parameter; 1,594 removed lines = 551 declarations + 457 session lines + 35 imports + 551 old signatures). | **NOT YET DEPLOYED OR RUN — the project owner will deploy and test.** Not converted (18 old-pattern lines remain): handlers called from other code (`generateADAPNYCFile`, `getEharsStateno`, `uploadRIDRData`, `uploadCluster`), handlers that reference `"UserObj"` elsewhere or invalidate the session (`agreeNYEHMSTANDC`, `getPrfDocAsHTML`, `getRawPrfDoc`), and multi-line declarations. The `Map targetMap` + `MapBindingResult` pair (~370 occurrences) and non-handler helpers were left alone. **If something breaks:** `git revert abd0146` restores the old behaviour in one step. Smoke-test screens that rely on the logged-in user: login/landing, search, assignments, document review, uploads, standardization, admin. |
| `3728ace` | **Twelfth step — duplicate-code consolidation #2 (exact method copies E-1 to E-3 of `docs/DUPLICATE_CODE_REPORT.md`):** removed `DocumentReviewTransactionHelper.processClusterPartnerDocument` (an identical copy of the `ClusterPartnerReviewTransactionHelper` method with no callers) and the field/import only it used; replaced the identical `isNameHavingSuffix` methods and suffix lists in `CCDDocumentServiceImpl`/`HL7DocumentServiceImpl` with the new `web/spring/util/PersonNameUtil.splitSuffix` (same results); merged `PerinatalServiceImpl.newImage`/`newFooterImage` into one `newImage(WordprocessingMLPackage, Part, byte[])` (both already called the same docx4j overload). Verified by a full `javac --release 8` compile whose error list is identical to the baseline (299 errors). | Not yet deployed. Smoke-test: CCD and HL7 document processing (names with suffixes such as JR/SR/III), cluster-partner review, perinatal performance letter (header and footer images). E-4 (9 copies of the phone search-mask setter) and the rest of the report are still open. |

Manual checks for `77b99f6`: app starts cleanly on WebLogic (DONE — deployment works), the scheduler page renders and its other buttons work (not yet recorded), external static JS has no references to `*CreatingAllJaro` IDs or `runAllJaroLoaderJob` (not yet recorded).

---

## 0. Facts and Numbers

| Metric | Count |
|---|---|
| Java source files (`src/main/java`) | 683 (no test sources — all JUnit tests were removed in the ninth pass) |
| JSP files | 376 |
| MyBatis mapper XML files (files with `<mapper namespace>`) | 76 |
| DAO/mapper Java interfaces | 98 (web) + 4 (schedulers) |
| Service classes | 235 (~70k lines) |
| Controller classes | 62 |
| Domain/VO/DTO classes | 224 |
| Standalone `.js` files in the repo | **0** — all JS is served from an external static-content path, not this repo |
| Quartz jobs (active) | 12 cron-triggered + 1 manual-only (`runCCDLoaderJob`) |
| Java version | 8 (`maven-compiler-plugin` source/target `1.8`) |
| Spring Framework version | 5.3.20 (XML config, not Spring Boot) |
| Build | Maven, packaged as `.war`, WebLogic-specific manifest entries |

Root build file is `pom.xml` (Maven, `.war` packaging). *Numbers in this section were re-measured after the ninth cleanup pass (Java file count), the eighth (JSP and controller counts) and the seventh (the rest); the original analysis counted 741 Java files, ~439 JSPs and 257 domain classes.*

---

## 1. Current Project Structure

Base Java package: `gov.ny.state.doh.bhae.nyehms`

```
src/main/java/gov/ny/state/doh/bhae/nyehms/
├── utils/                          # 1 loose static-helper class (County)
├── web/
│   ├── ajax/json/                  # GridObject, JsonObject — JSON payload shapes for jqGrid/AJAX
│   └── spring/
│       ├── controller/             # 62 files — Spring MVC @Controllers, grouped by feature
│       ├── service/                # 235 files — business logic, grouped by feature
│       ├── dao/                    # 98 MyBatis mapper interfaces, mirrors service grouping
│       ├── domain/                 # 224 VO/DTO classes — MyBatis result-map targets AND MVC form objects
│       ├── bo/                     # 3 legacy "business object" processor classes
│       ├── util/                   # shared utility classes (CommonUtil, DateUtil, SendEmail, etc.)
│       ├── constants/, enums/      # CommonConstants, EharsVariableConstants, AppConstants
│       ├── security/               # UserAuthorization, UserObject (session principal)
│       ├── aop/                    # exception-handling, logging, and intercepting aspects
│       ├── validation/             # per-document-type Validators
│       ├── view/                   # legacy Spring AbstractPdfView/AbstractXlsView subclasses (PDF/Excel export)
│       ├── listener/                # ServletContextListener (app bootstrap)
│       └── exception/               # NyehmsException
├── schedulers/spring/dao/{ecrls,nyehms,ursdb}/   # scheduler-specific MyBatis DAOs, 3 separate datasources
└── webservice/{oauth,webclient}/   # modern WebClient/reactor-based HCS OAuth2 client (outside component-scan!)
```

```
src/main/resources/
├── spring-config.xml               # the entire Spring wiring: datasources, MyBatis, Quartz, MVC
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
└── WEB-INF/jsp/                    # feature-folder JSPs (see §2 and the count in §0)
```

### Layer responsibilities (as-built, not idealized)

- **Controllers** (`web/spring/controller/**`): Spring `@Controller` classes, `.html`-suffixed `@RequestMapping`s, mostly return JSP view-name strings against a `Map<String,Object>` model rather than typed `@ModelAttribute`/`ResponseEntity`. Heavily pull `UserObject` off the `HttpSession` (569 occurrences across 59 files). Several controllers contain real business logic and even direct DAO access (see §3/§5).
- **Services** (`web/spring/service/**`): the largest layer (235 files, ~70k lines). Mix of legitimate business logic and — in a substantial minority of classes — raw JDBC, PDF/Excel/FreeMarker generation, email sending, and SFTP file transfer, all inside `@Service` classes that also do database orchestration. `*TransactionHelper` classes (~20 of them, one per feature) wrap MyBatis calls in `@Transactional` boundaries.
- **DAOs / MyBatis mappers** (`web/spring/dao/**` + matching XML in `resources/.../dao/**`): interface-per-feature, XML-per-interface, wired via 3 separate `MapperScannerConfigurer`s onto 3 separate `SqlSessionFactory`s (`sqlSessionFactory`/`appDS`, `elrlsSessionFactory`/`appDS`, `ursSessionFactory`/`ursDS`) plus a 3rd raw datasource (`eharsDS`) accessed only via hand-rolled JDBC (`ConnectionDAO`/`JdbcConnectionDAO`), not MyBatis. The `document/` subpackage alone has 44 DAOs, one per external data-feed type.
- **Domain/VOs** (`web/spring/domain/**`): plain getter/setter classes with no Jackson/Bean-Validation annotations in most cases; the same VO instance is typically populated by MyBatis via reflection **and** bound to JSP forms/models — a dual-purpose pattern that will need to be split for a clean JPA entity vs. DTO separation later.
- **JSP + JS** (`webapp/WEB-INF/jsp/**`): server-rendered views resolved via `InternalResourceViewResolver` (prefix `/WEB-INF/jsp/`, suffix `.jsp`). **No JavaScript lives in this repository at all** — every JSP loads JS from an externally deployed static path (`${staticPath}/js/...`, set from `session.getAttribute("staticPath")`, e.g. `/hpn/ctrldocs/nyehms`), including shared libraries (jQuery 1.9.1, jQuery UI 1.10.1, jqGrid 4.5.4) centralized via `common/commonScriptsIncludes.html`. This is an important discovery for modernization: **front-end assets are managed and deployed outside this codebase** and are not visible to static analysis here.
- **Config**: `spring-config.xml` is the single, large, XML-based Spring context (datasources, MyBatis session factories, Quartz jobs/triggers, MVC view resolvers, JAXB/Jackson marshalling). `web.xml`/`weblogic.xml` carry WebLogic-specific deployment descriptors (JNDI-based filters, session replication, context-root). Environment selection is via `*_environment.properties` files.
- **Utilities**: scattered across `utils/` (1 file), `web/spring/util/` (9 files) and `web/spring/util/scheduler/LabCommonUtils` (1,484 lines), and many per-feature `*TransactionHelper`/`*Util`/`*Helper` classes embedded directly inside `service/` subpackages rather than centralized.

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
Quartz (spring-config.xml JobDetailFactoryBean/CronTriggerFactoryBean, 12 cron-triggered jobs + 1 manual-only)
        → *Loader / *ServiceImpl classes in web/spring/service/schedulers/**
        → same Service → DAO → Oracle pattern, plus JSch/SFTP and JavaMail for file exchange and notifications
```

View resolution: controller returns a String like `"search/personSearchTabDetail"` → `InternalResourceViewResolver` → `/WEB-INF/jsp/search/personSearchTabDetail.jsp`. A **separate** `ResourceBundleViewResolver` (`views.properties`) resolves a small set of non-JSP export views (PDF via iText `AbstractPdfView`, Excel via `AbstractXlsView`) — these are Java classes in `web/spring/view/**`, not JSPs.

JSP folder names line up close to 1:1 with controller package names (administration, assignment, document, lab, person, review, search, standardization, uploads all match directly); a few don't match literally but resolve correctly through the view-name string (controller package `report` → JSP folder `reports`; `scan` → `scanning`).

---

## 3. Unnecessary Code Candidates — status

The original analysis listed candidates here with a "do not delete" warning. After verification, **all High-confidence items have been removed** across the cleanup passes recorded in the Cleanup Log; the original table is in Git history (commit `b7b944a`). This section now tracks what was resolved and what is still open.

**Resolved (see the Cleanup Log for commits):** `AllJaroLoader` + `RunAllJaroLoaderJob` and their JSP buttons (`77b99f6`); the Sierra loader job (`5e78269`); `AuthFilter`; the unwired `jms/**` package and its tests; `SSDMDocValidator`/`ADAPDocValidator`; `UpperCase`/`UpperCaseValidator`; `GetTblNameMap`; `domain/test/**` + `TestController`; `utils/HtmlMethods`; `SubProviderAddressDAO` (+ XML); the empty `SystemMessagingController`; the `sqljdbc4` and `maven-plugin-api` Maven dependencies; ~60 unused `CommonConstants` entries; `CCDDocumentServiceDOMXPATH` (+ Impl); the unreferenced JSPs (`AssignmentsCountsSuper`, `labFacilitiesGrid` and ~40 more); the applet/Web Start bundle; plus unused VOs, DAO methods and mapper SQL, imports, fields and commented-out code (see the Appendix).

**Still open:**

| Item | Why | Confidence |
|---|---|---|
| `EharsVariableConstants.EHARS_VARIABLES` map (~40 entries) | Its only call site was a commented-out line in `AssignmentServiceImpl`, which the comment sweep removed; the map now has no references. The sibling `EHARS_VAR_NAME_MAP` in the same file IS used dynamically — do not remove the whole class. (It was missed by the constants sweep because it is a map, not a `public static final String`.) | High |
| `reports/LoincResults.jsp` | No controller view-name match found; only a substring-style reference. Verify before removing. | Low |
| `ConnectionDAO.getSqlServerConnectionObj()` | Misleading name — it is wired to `eharsDS`, an Oracle datasource. Real code (called from 6 places); rename later, do not remove. | Note |
| ~~`/getUserGuide` → missing `UserGuide.jsp`~~ | **RESOLVED (eighth pass)** — the whole User Guide / context-sensitive help feature was removed, including this broken endpoint. | ~~Medium~~ |
| `aop/intercepting/ControllerLayerInterceptingAspect.java` pointcut `controller.reports..*` | Actual package is `controller.report` (singular), so that segment can never match and report-package controllers silently miss whatever this aspect does. Also imports WebLogic-internal `ServletRequestImpl` (see section 5). A bug — decide fix vs. remove. | High |

**Caveats that apply across this whole section** (still valid for any future cleanup — do not blindly classify code as unused):
- `@Service`/`@Component`/`@Controller` classes under `gov.ny.state.doh.bhae.nyehms.web.spring.*` are auto-registered by the single `<context:component-scan>` in `spring-config.xml` even with zero explicit XML bean refs and zero direct Java callers.
- Classes under `gov.ny.state.doh.bhae.nyehms.webservice.*` (a *different* top-level package) are **outside** that component-scan base package — e.g. `webservice/oauth/AccessTokenClient.java` carries `@Service`/`@Autowired` but is actually instantiated with `new AccessTokenClient(...)` by `NyhemsHCSWebServiceOp`, not by Spring DI. This is a wiring inconsistency worth fixing, not dead code.
- `RunXxxLoaderJob extends QuartzJobBean` classes are invoked by Quartz via reflection from `spring-config.xml`'s `JobDetailFactoryBean.jobClass` — absence of a direct Java caller does **not** imply dead code for this pattern.
- MyBatis mapper interfaces are proxied by `MapperScannerConfigurer`; their XML is loaded by convention (same package path, same name as the interface) — only a literal `@Autowired`/interface-reference grep can confirm liveness.
- Properties files are looked up by dynamically built keys (see the Appendix) — never prune them by name search.

---

## 4. Redundant / Duplicate Code

The full assessment, with impact/risk ratings and a phased plan, is in [`docs/DUPLICATE_CODE_REPORT.md`](docs/DUPLICATE_CODE_REPORT.md).

- **~40 near-identical `Raw*/Std*` document-feed pipelines**, each spanning Controller → Service → DAO/XML → VO, one per external source system (ADAP, AHI, Birth, Cancer, CASA, CCD, Death, Doccs, HL7, ICCR, Medicaid, NDI, PRF, RAS, SSDM, STD, SYPH, TB, TTH, Tracking, …). Every layer repeats the same method-name skeleton per feed: `get<Feed>Doc`, `insertRaw<Feed>Document`, `insertStd<Feed>Document`, `get<Feed>DocumentForClericalReview`, `getRawDocAsHTML`/`sendEmailNotificationTo<Feed>Users`. This is the single largest duplication surface in the codebase, spanning `web/spring/dao/document/**` (44 DAOs), `web/spring/domain/document/**` (132 VOs, 76 of them loose at the top level with inconsistent sub-packaging), `web/spring/service/document/**`, and `person/DocumentController.java` (which alone has ~28 near-identical `getXxxDocAsHTML` methods, lines 213–662+). **Consolidation direction**: a generic/strategy-based document-feed abstraction (type → handler map) instead of one bespoke class per feed at every layer — a strong long-term target, but high-risk/high-effort given ~40x fan-out; do this after the modular-monolith boundary work, not before.
- **Repeated session/user-lookup boilerplate — MOSTLY DONE (`abd0146`)**: 551 handlers in 59 controllers now take `UserObject userObject` as a parameter (resolved by `UserObjectArgumentResolver`) instead of pulling it off the session; 18 old-pattern lines remain (see the Cleanup Log row). Still repeated: `Map targetMap = new HashMap(); BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");` (~370 pairs in 50 files) and the `HttpSession session = request.getSession();` line where `session` is used for other attributes.
- **Repeated file-upload handling**: 7 controllers (`ADAPNYCFeedBackController`, `ClepAuditResultsController`, `LaftCountReconcilleController`, `RecordSearchReviewController`, `DocumentsScanController`, `DatafluxUploadController`, `FileUploadController`) each independently handle `MultipartFile`/`MultipartHttpServletRequest` with no shared upload utility/base controller.
- **Ad hoc email-sending wrappers**: 24 service files wrap the shared `web/spring/util/SendEmail.java` utility with their own `sendEmail*` methods rather than a single shared notification service — e.g. near-duplicate `sendEmailToBureauDirector` logic exists independently in both `EPRFOOCDocumentServiceImpl.java:471` and `DocumentReviewTransactionHelper.java:512`.
- **Report/export generation duplication**: 9+ service files independently hand-build PDF (itext), Excel (POI via `AbstractXlsView`), and FreeMarker output rather than going through one shared report-building service. The 16 `Generate*` classes in `web/spring/view/review/**` each re-implement Excel header/style/row boilerplate on top of Spring's `AbstractXlsView` — e.g. 4 separate `Generate*ReportByDailyStaff` classes share an identical report shape.
- **Two CCD parsing implementations — RESOLVED**: `CCDDocumentServiceDOMXPATHImpl` was unused (nothing injected its interface) and was removed; `CCDDocumentServiceImpl` is the live CCD parser.
- **Per-feature `*TransactionHelper` classes** (~20 of them, one per service subpackage: `PersonTransactionHelper`, `AssignmentTransactionHelper`, `DocumentTransactionHelper`, `RiaTransactionHelper`, etc.) repeat the same "wrap DAO calls in a transactional boundary" shape — a plausible candidate for a shared generic transactional-service base once the module boundaries are clearer.
- **Raw/Std VO pairing duplicated ~7+ times structurally**: `RawCCDDocVO`/`StdCCDDocVo`, `RawHL7DocVO`/`StdHL7DocVO`, `RawHIEDTHDocVO`/`StdHIEDTHDocVO`, etc. — each subpackage reinvents the "incoming raw record" vs. "standardized record" concept with its own field set instead of a shared base/interface.
- **JSP header/menu/footer includes are actually well-consolidated** (not a duplication problem): `common_includes.jsp`, `commonStylesIncludes.html`, `commonScriptsIncludes.html`, `MainMenuIncludes.jsp`, `Menu.jsp` are reused 90–150+ times each via `<%@ include %>`/`jsp:include`. The one real duplication here is cosmetic: the same one-line scriptlet `pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));` appears independently (not via include) in 52 JSP files.
- **Two grid libraries coexisting**: legacy jqGrid (4.5.4, the default via `commonScriptsIncludes.html`) alongside a newer `commonagGridScriptsIncludes.html` (ag-Grid) now referenced from 25 JSP files — evidence of an in-progress, incomplete UI library migration that predates this modernization effort.

**Measured duplication (line-block and method clone detection, after the tenth pass; analysis only):**

| Area | Duplicated share |
|---|---|
| Java, all | 16.9% of significant lines are inside repeated 8-line blocks (14,393 of 85,110) |
| Java, excluding `domain/` | 11.9% (5,941 of 49,899) |
| Mapper XML (SQL) | 19.9% (6-line blocks) |
| JSPs | 36.8% (8-line blocks; includes taglib/page-directive headers) |

- **Domain VOs (~5,800 duplicated lines):** the same person / provider / facility / race / address field groups (`FNAME`/`MNAME`/`LNAME`, `PERSON_*`, `PROV_*`, `RACE_*`) are re-declared with hand-written accessors across 20+ `domain/document/**` VOs — candidates for shared base classes or JPA `@Embeddable`s.
- **Excel/PDF views (~800 lines):** style-setup blocks repeat 9–12 times across 21 view classes; `GenerateMergeReportByDailyStaff` and `GenerateBadMergeReportByDailyStaff` share a 121-line block; 51 `createCellStyle()` calls; 3 `buildPdfDocument()` methods share a 33-line shape.
- **Scheduler loaders:** the "get server IP address" block is copied in 7 loaders (13 files call `InetAddress.getLocalHost()`); the `loadSchedulers` environment check appears in 12 files — a base class would remove both.
- **Exact method copies:** `processClusterPartnerDocument()` (37 lines, `ClusterPartnerReviewTransactionHelper` and `DocumentReviewTransactionHelper`), `isNameHavingSuffix()` (`CCDDocumentServiceImpl` and `HL7DocumentServiceImpl`), `newImage()`/`newFooterImage()` in `PerinatalServiceImpl`.
- **Same-shape methods:** 17 `get<Feed>DocAsHTML()` methods in `DocumentController` (9 lines each); 4 update/add methods in `LabReportsServiceImpl`; 3 upload endpoints across `ClepAuditResultsController`/`FileUploadController`.
- **Service exception template:** 225 `catch (DataAccessException ...)` blocks in 64 files, 75 `inspectDBTransacionResult` calls; 47 inline `new SimpleDateFormat(...)` (21 with `"MM/dd/yyyy"`; not thread-safe); 37 raw JDBC connection acquisitions in 18 files.
- **Mapper SQL:** `ADAPDocumentDAO.xml` (a 276-line block twice), `SchedulerJobsDAO.xml` (two ~185-line column lists), `RiaRecordsReviewDAO.xml` (101 lines; a 24-line `case when` x4), `ClericalReportDAO.xml` (23 lines x14), an 88-line search-field `<when>` chain shared by `RawSubFacilityDAO.xml`/`RawProviderAddressDAO.xml` — candidates for `<sql>`/`<include>` fragments.
- **JSPs:** 17–21 identical directive lines open 22–25 pages; `PrfDocument2016`/`PrfDocument2023`/`NycPrfDocument` share 202 lines; 4 `ClericalReview*` pages share 134 lines; most duplication is in `document/` and `worker/`.

---

## 5. Problematic Legacy Code

These are the patterns that will most complicate the planned Java 21 / Tomcat / modular-monolith / JPA migration.

**Direct database access outside the DAO layer** (the biggest single issue for a MyBatis→JPA migration, since it's invisible to any repository-based approach):
- `web/spring/dao/jdbc/impl/JdbcConnectionDAO.java` hands out raw `Connection` objects (`getConnectionObj()` from `appDS`, `getSqlServerConnectionObj()` — actually Oracle `eharsDS`, see §3) which 6+ services then use to build hand-rolled `PreparedStatement`/`CallableStatement` SQL.
- Confirmed raw JDBC (`Connection`/`Statement`/`PreparedStatement`) in **17 service files**, heaviest in `ManualQuartzJobServiceImpl.java` and `LabProficiencyServiceImpl.java` (9 occurrences).
- Raw `CallableStatement` built directly in application code (not MyBatis) in `LabReportsServiceImpl.java:910` and `AddLabProficiencyDataVO.java:50`.
- One controller, `ExperimentalController.java:26,40`, `@Autowired`s a DAO (`CCDDocumentDAO`) directly, skipping the service layer entirely.

**God classes mixing many responsibilities:**
- `ManualQuartzJobServiceImpl.java` — **6,610 lines**, the largest file in the app. Combines Quartz job control, raw JDBC, JSch/SFTP file transfer, JavaMail, and ECLRS/HIV lab-record validation in one `@Service`. Individual methods run to roughly **1,300 lines** (`getRawDocsList`) and **750 lines** (`storeDocumentMatchResults`).
- `person/DocumentController.java` — **1,919 lines**, 55 `@RequestMapping`s, 43 `@Autowired` fields — effectively an omnibus controller for nearly every document type.
- Other files over ~1,500 lines: `HL7DocumentServiceImpl.java` (2,678), `FileUploadServiceImpl.java` (2,606), `CCDDocumentServiceImpl.java` (2,259), `QuartzSchedulerTransactionHelper.java` (2,215, despite the "helper" name), `assignment/AssignmentController.java` (1,584), `LabCommonUtils.java` (1,484), `LabReportsController.java` (1,299), `AssignmentReabstractUtil.java` (1,004); the largest domain classes are `RawAcrfDocVO` (3,285), `EcrlsLabDocVO` (2,272) and `PsAssignmentView` (2,095).
- `web/spring/bo/**` — all 3 "business object" classes mix data access, `HttpServletRequest` handling, and presentation-string encoding (e.g. `ProcessSearchResults.java:42` injects a literal marker string `"~#&^italics~#&^"` into a data field for the JSP to later interpret) inside classes with no clean single responsibility.

**Business logic embedded in controllers:**
- `DocumentController.getClericalDocumentAsPerson` (~168 lines) and `.getACRFFormAsHTML` (~151 lines) branch on document-type strings and assemble page models by calling 8+ services sequentially — this orchestration belongs in a service.
- `AssignmentController.getAssignmentReportMatrix` (~118 lines) builds `Calendar`-based month-range/report-matrix logic inline.
- `LabReportsController.java` (1,338 lines, ~86 methods) reads like an entire un-split "Lab Administration" module living inside one controller.

**Old Spring/Java patterns that will need addressing:**
- Field injection (`@Autowired` on fields, never constructors) is universal — 43 fields in one controller alone.
- Manual `Map<String,Object>` model + `HttpServletRequest` parameters instead of typed binding/`ResponseEntity`, across the large majority of the 62 controllers.
- Hand-built `BindingResult`/`MapBindingResult` instead of `@Valid` + injected `BindingResult`.
- Legacy Spring `AbstractPdfView`/`AbstractXlsView` subclasses (17 classes) instead of a modern export-generation service.
- `SimpleDateFormat`, `java.util.Date`/`Calendar` throughout (no `java.time` usage found in sampled god classes) — direct Java 8→21 friction, though low-risk to modernize incrementally.
- `org.json.simple.JSONObject` used instead of the Jackson already configured in Spring MVC.

**WebLogic-specific coupling:**
- Three datasources configured via `weblogic.jndi.WLInitialContextFactory` JNDI lookups in `spring-config.xml` (`appDS`, `ursDS`, `eharsDS`) — these must become plain JNDI or Tomcat-managed pooled datasources under the Tomcat migration.
- `aop/intercepting/ControllerLayerInterceptingAspect.java` imports the WebLogic-internal class `weblogic.servlet.internal.ServletRequestImpl` directly and type-checks against it — this aspect silently no-ops under any other servlet container (including Tomcat) today, which is both a portability blocker and a currently-latent behavior gap.
- `weblogic.xml` (context-root, session replication, JSP precompile flags) and `wlfullclient` Maven dependency are WebLogic-only artifacts with no Tomcat equivalent — need a replacement/removal plan.
- `webapp/WEB-INF/properties/pom.properties` used to contain a **hardcoded WebLogic deployment password** and was packaged into every WAR. The file and the `properties-maven-plugin` that read it were removed in the tenth pass (nothing used its values). **The password is still in Git history and on GitHub — rotate it.**

**Excessive/monolithic XML configuration:**
- `spring-config.xml` is a single ~450-line file mixing datasource config, 3 MyBatis session factories, ~13 Quartz job/trigger bean pairs, and MVC/JAXB/Jackson wiring — a good candidate to split by concern (datasource, MyBatis, scheduling, MVC) even before any Spring Boot migration.
- `standardization/**` and large chunks of `assignment/`, `review/`, and `CommonDAO.xml` rely heavily on Oracle stored procedures (`PKG_STANDARDIZATION.*`, `PKG_CONSOLIDATION.*`) — 38 mapper XML files, ~130 `CALLABLE` statements total — meaning substantial business logic lives in the Oracle database itself, not in Java, which the eventual JPA migration cannot simply "port."
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

**No conversion has been performed.** Classification below is based on scanning the 76 mapper XML files for dynamic SQL (`<if>/<choose>/<foreach>`), `statementType="CALLABLE"`, and Oracle-specific syntax (`CONNECT BY`, `ROWNUM`, `LISTAGG`).

### Easy JPA Candidates (single-table/two-table CRUD, no dynamic SQL, no stored procs)

~20 of the 44 `document/` feed DAOs have zero dynamic-SQL tags and do plain parameterized CRUD on one RAW_/STD_ table pair. Representative, fully-verified examples:
- `document/BirthDocumentDAO.xml` (68 lines, 8 static statements)
- `document/TBDocumentDAO.xml` (108 lines, one 2-table join, all static SQL)
- `document/CancerDocumentDAO.xml`, `CASADocumentDAO.xml`, `MedicaidDocumentDAO.xml`, `NDIDocumentDAO.xml`, `QualCareDocumentDAO.xml`, `RASDocumentDAO.xml`, `SSDMDocumentDAO.xml`

These are the pragmatic **first wave**: predictable columns, no PL/SQL, no `CALLABLE`, minimal joins. Moving these to Spring Data JPA repositories would touch a well-isolated slice of the `document` module without disturbing the DAOs that carry real complexity.

A second, moderate tier (static SQL but a 2–4 table join, or a single `CALLABLE`/`ROWNUM`/`LISTAGG` usage) — most of the remaining `document/` DAOs plus `report/ProviderReportDAO.xml`, `report/SearchHistoryDAO.xml`, `report/StatusLabReportsDAO.xml` — could follow as a **second wave**, likely needing a native-query escape hatch for the one non-standard clause per file rather than blocking the whole file from migrating.

### Keep MyBatis Temporarily (complex, Oracle-specific, or stored-procedure-driven)

- **`standardization/facility/**` and `standardization/provider/**`** (16 files) — almost entirely built on `CALLABLE` calls into `PKG_STANDARDIZATION.*` Oracle packages (e.g. `SubProviderAndAddressDAO.xml`: 17 dynamic tags + 3 CALLABLE; `RawProviderAddressDAO.xml`: 16 dynamic + 5 CALLABLE). Real business logic lives in the database here — not portable to JPA without first extracting that logic from PL/SQL.
- **`assignment/AssignmentDAO.xml`** (980 lines, dynamic-SQL heavy, 14 CALLABLE) and **`assignment/reports/AssignmentReportsDAO.xml`** (479 lines, 25 dynamic tags) — large dynamic search/report queries.
- **`review/RecordSearchReviewDAO.xml`** (uses `CONNECT BY LEVEL` for a number generator) and **`review/RiaRecordsReviewDAO.xml`** (1,117 lines, heavy CALLABLE).
- **`document/HL7DocumentDAO.xml`** (1,164 lines) and **`document/CCDDocumentDAO.xml`** (1,088 lines) — both use `CONNECT BY` twice: once as a sequence-value generator loop, once as a CSV-split trick via `regexp_substr(...) CONNECT BY LEVEL`.
- **`lab/LabReportsDAO.xml`** (CSV-split via `CONNECT BY`, 3 CALLABLE), **`lab/LaftCountReconcileDAO.xml`** (9 CALLABLE), **`lab/LaftDetailsDAO.xml`** (6 CALLABLE).
- **`CommonDAO.xml`** — the shared/cross-cutting mapper (permissions, session, address/county/state lookups, error logging). Contains 2 `CALLABLE` calls, heavy `ROWNUM`/`LISTAGG`/`DECODE` usage, nested dynamic `<choose>`, and — notably — a fully dynamic table-name substitution (`${tableName}`, lines ~166-168) that cannot be represented as a static JPA query at all and should stay MyBatis (or be redesigned) regardless of migration timeline. Because it's injected broadly across nearly every feature, it's also a poor first-mover even where individual statements look simple.
- Any mapper using `statementType="CALLABLE"` (38 files, ~130 occurrences total) — these need explicit `StoredProcedureQuery`/native handling if ever ported, not a straight `@Query`/derived-method JPA translation.

Raw JDBC usage (`JdbcConnectionDAO`, direct `CallableStatement` construction in `LabReportsServiceImpl.java`/`AddLabProficiencyDataVO.java`) sits **outside MyBatis entirely** and needs its own remediation plan before or alongside any MyBatis→JPA work, since neither MyBatis nor JPA tooling has visibility into it today.

---

## 8. Recommended Cleanup Order

Status legend: DONE / OPEN. Keep the rule of one small, verified, reviewable step per commit.

1. **DONE — Verify and remove confirmed dead code** — seven cleanup passes (see the Cleanup Log and the Appendix).
2. **DONE — Sweep `CommonConstants.java`.** Still OPEN from this step: the `EharsVariableConstants.EHARS_VARIABLES` map (section 3).
3. **OPEN — Record a real build baseline and finish verification** — a clean `mvn clean package -DskipTests` on a machine with JDK 8 + Maven; confirm deployment of the latest cleanup commits (see "Status / resume here"); screen-level regression checks; access-log check for the removed applet files.
4. **OPEN — Fix or remove the remaining "silently broken" finding** — the `controller.reports` vs `controller.report` pointcut in `ControllerLayerInterceptingAspect` (section 3). The other one, `/getUserGuide`, was resolved by removing the User Guide feature (eighth pass). This changes behavior, so decide with the team.
5. **PARTLY DONE — Hardcoded WebLogic deploy password.** The file `pom.properties` and its Maven plugin were removed (tenth pass). **OPEN: rotate the password** (it remains in Git history) and optionally purge history.
6. **OPEN — Address the WebLogic-coupled `ControllerLayerInterceptingAspect`** (`weblogic.servlet.internal.ServletRequestImpl` type-check) — needs a fix before a Tomcat migration can work correctly.
7. **PARTLY DONE — Consolidate obvious duplicate/redundant code** (section 4): the session/user-lookup boilerplate is done for 551 handlers (`abd0146`, pending deploy/test). OPEN: the `targetMap`/`bindingResult` pair (~370), the file-upload handling duplication (7 controllers), and the copy-pasted `buildDate` scriptlet (52 JSPs) via a single request attribute. See "Measured duplication" below for the priority list.
8. **OPEN — Clean package/config boundaries** — split `spring-config.xml` by concern (datasource, MyBatis, scheduling, MVC), fix the `webservice.oauth`/component-scan base-package mismatch.
9. **OPEN — Prepare modular monolith structure** — carve out module boundaries per section 6 starting with the most isolated modules (e.g. `standardization`, `lab`) before the large, ~40x-duplicated `documentintake` module, whose generic-abstraction redesign (section 4) should happen before or during the split.
10. **OPEN — Upgrade Java/Spring dependencies** (Java 8 to 21, dependency bumps) — now touching a smaller, better-understood codebase than at the start.
11. **OPEN — Move deployment from WebLogic to Tomcat** — contingent on items 5, 6 and 8 (WebLogic-specific filters, JNDI datasource config, `weblogic.xml`).
12. **OPEN — Gradually migrate the "Easy JPA Candidate" MyBatis mappers** (section 7, ~20 single-table `document/` DAOs first) to Spring Data JPA, module by module. Remember mapper XML is loaded by convention next to its interface — move them together.
13. **OPEN — Handle complex MyBatis/Oracle queries and stored-procedure-backed modules separately** (section 7 "Keep MyBatis" list) as a longer-horizon workstream.

---

## Summary

**Done so far:** seven cleanup passes removed the dead code identified in section 3 and the Appendix — the AllJaro and Sierra jobs, `AuthFilter`, the `jms` package, scratch/test controllers, the applet/Web Start bundle, ~60 unused constants, unused VOs, DAO methods and mapper SQL, unused imports and fields, ~3,300 commented-out code lines, and two unused Maven dependencies. Net effect: 683 Java files (was 741) and 376 JSPs (was ~439).

**Top open items:** (1) record a real Maven build baseline and confirm deployment of the latest commits; (2) rotate the WebLogic deploy password that was in `pom.properties` (file removed, but the value remains in Git history on GitHub); (3) the WebLogic-coupled `ControllerLayerInterceptingAspect` and its `controller.reports` pointcut typo; (4) the unused `EHARS_VARIABLES` map; (5) clean up the now-unused User Guide / help assets in the external static-content path; (6) decide on adding tests back (there are none — see the testing note above).

**Top redundant-code areas (unchanged):** the ~40-feed document-intake pipeline duplicated across DAO/Service/Domain/Controller layers (biggest by far), the 569x-repeated session/user-lookup boilerplate in controllers, and the ad hoc per-service email/report-generation wrappers.

**Biggest legacy/problematic areas (unchanged):** `ManualQuartzJobServiceImpl.java` (6,610-line god class mixing JDBC/JSch/mail/business logic), 17 services doing raw JDBC outside the DAO layer, `person/DocumentController.java` (1,919-line omnibus controller), universal field-injection/`Map`-based-model Spring MVC patterns, and the WebLogic-internal-class coupling in `ControllerLayerInterceptingAspect`.

**Suggested future modules:** person, document-intake, assignment, lab, review, standardization, administration, authentication, reporting, scheduling, messaging, uploads/scanning, search, integrations — all derived directly from existing package/JSP groupings, no invented boundaries.

**Best next step:** items 3-5 of section 8 (build baseline, the pointcut bug, password rotation) — each is small and independent, and they unblock the Tomcat/Java 21 work.

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

**Remaining unused-method candidates (Medium) — mostly DONE in the fourth pass:** removed `ProcessSearchCriteria.getPersonSearchCriteriaMap`, `ClusterDocumentServiceImpl.checkforValidDemoInfo`, `HIEDTHDocumentServiceImpl.getMapFromNodeMap`, `PRFDocumentServiceImpl.checkDinFormat`, `DocumentSummaryReportsServiceImpl.loadSQLQueries`, `ManualQuartzJobServiceImpl.loadRawFacilityMappingResults`, `PreMatchHITSchedulerHelper.writeRawDiagnosisToStdLayer/writeRawReviewedList`, `CommonUtil.convertStringToDateFormat/convertStringToTimeStamp`, `DateUtil` (4), `InitializeUtil` (4), `LabCommonUtils.getOldNycColumns`. **Still-open items from the fourth pass were resolved in the seventh pass** (all removed except `isOldNycDeathFile`, a false positive that stays). `GetSQLSession.getSqlSession` was kept deliberately (it is a bean-style util; only its getter was unused). **Unused Maven dependencies — DONE:** removed `sqljdbc4` and `maven-plugin-api:2.0` (its only transitive was a test-scope junit; `pom.xml` declares its own junit 4.10). `log4j-*`, `ojdbc6` and `hapi-*` are used indirectly and must stay.

**Notes for future cleanup and the MyBatis migration:**

- **Mapper XML is loaded by convention, not by config.** There is no `mapperLocations` in `spring-config.xml`; `MapperScannerConfigurer` binds each interface and MyBatis loads the XML named after the interface from the same package path. An XML file with no same-named interface (like the removed `StdDocumentDAO.xml`) is never loaded. Keep this in mind when converting mappers to JPA or moving packages: XML files must move together with their interface.
- **Tooling lesson (fourth pass):** an automated method remover once removed the wrong span because a single 37,000-character string literal in `DocumentSummaryReportsServiceImpl` defeated a regex-based comment/string mask (Perl regex repetition limit), so braces inside the string were counted. It was caught by a declaration-diff check (a live Override-annotated method had vanished), the file was restored from Git, and the removal was redone with a character-level lexer. Every code-removal pass since then is verified by (a) comment-stripped diff showing zero added lines, (b) a declaration-name diff showing exactly the intended names removed, and (c) brace balance under the lexer.

**Open findings after the sixth pass (final rescan):**

- **Nothing else is flagged by the automated scans** (zero-reference classes, no-caller DAO methods, orphan mapper SQL, unused `resultMap`s, constants, imports, fields, JSPs, dead mapper XML files).
- **`scanning/ShowScannedFileNames.jsp`** — DONE in the seventh pass (it had become unreferenced; the earlier claim that a live controller method returned it was stale).
- **Skipped unused-method candidates — DONE in the seventh pass** except `NycDeathDocumentServiceImpl.isOldNycDeathFile`, which is a scan false positive (called from live code at line 377) and stays. Note `HL7DocumentServiceImpl` has its own private `populateCountyValue` (live); only the unused copy in `ManualQuartzJobServiceImpl` was removed.
- **Properties files are live — do not prune by name search.** `SchedulerMetaData.properties` is the LOCAL-stage fallback for scheduler metadata (`ManualQuartzJobServiceImpl.getSchedulerMetaData()` loads it via `getResourceAsStream` when `stage=LOCAL` and `loadSchedulersDataFromProperty=Y`); keys are then read by literal name from the resulting map, so dead keys (e.g. the `ALLJARO_*` ones) could be pruned but the file itself must stay. `CommonEnvironment.properties` keys are looked up dynamically (`getProperty(reportType+"_report")`, `getProperty(parameterName+"_HEADERS")`, `getProperty(testResults)` for values such as `NRCT`), so keys that "appear unused" by name are live. `messages_en.properties` codes may also be resolved dynamically. (`pom.properties` was removed in the tenth pass; it was not used by any live build config.) (An earlier version of this appendix wrongly called `SchedulerMetaData.properties` unused.)
- **False positives to ignore in future scans:** Spring beans `multipartResolver`, `viewResolver`, `applicationContextProvder`, `freeMarkerConfigurationFactory` (found by fixed name or type by the framework).
- **Tooling notes:** (1) the DAO-method remover's Javadoc walk-up once mistook a one-line `/*...*/` comment for the end of a Javadoc and would have deleted a large span; caught in the dry run (74 live statements suddenly reported orphan), fixed, and re-verified with an interface-aware method-name diff. (2) The field remover initially left an `@Autowired` line behind for private fields; caught in the dry run, fixed, and re-verified by checking that every removed line was an import, `@Autowired` or a field declaration and that no `@Autowired` is left dangling. (3) The early DAO-method remover (commits `e8840de`, `5d6bfd0`) removed a method's Javadoc but not a `@Transactional` annotation sitting between the Javadoc and the method, leaving that annotation dangling: at the end of `NycPRFPartnerDAO` (a compile error) and attached to the next method in `DocumentDAO` (a silent behavior change). The project owner fixed both directly on GitHub (`f3eb72b`, `06b8993`, merged in `422866e`). A follow-up audit compared, for all 204 Java files touched since the baseline commit, how many times each annotation attaches to each declaration against the original: it reproduces both defects on their pre-fix versions and finds none at `HEAD`. Rule for any future method removal: remove the annotations directly above the method too, and run that annotation-target audit.

