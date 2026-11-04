# NYEHMS: Java 8 to Java 21 Upgrade

| | |
|---|---|
| **Branch** | `java21-upgrade` (branched from `main` at `c64b700`) |
| **Status** | Code and build changes complete; compiles with `javac --release 21`. **Not yet built with Maven or deployed.** |
| **Framework line** | Moved on to Spring 7 / Jakarta EE for Tomcat 11 — see `docs/TOMCAT_MIGRATION.md`, which supersedes sections 1 and 2 below. |

---

## 1. Runtime Requirement (read first)

A WAR compiled for Java 21 (class file version 65) only runs on a server that itself runs on JDK 21:

| Target | Works? | Notes |
|---|---|---|
| WebLogic 12.x / 14.1.1 | **No** | These support JDK 8/11 only; deployment fails with `UnsupportedClassVersionError`. |
| WebLogic 14.1.2 or later | Yes | Certified on JDK 17 and 21; still Java EE 8 (`javax.*`), which Spring 5.3 needs. |
| Tomcat 9.0.x on JDK 21 | Yes | Servlet 4 / `javax.*`. This is the planned target (section 6 of AGENTS.md). |
| Tomcat 10+ | **No** | Jakarta EE 9+ (`jakarta.*`); would need Spring 6 and a package rename across the code base. |

The project currently depends on `wlfullclient` 10.3 and uses a BEA-namespace `weblogic.xml`, which suggests an older WebLogic. **Keep deploying `main` (Java 8) to the current servers**; merge this branch only when a JDK 21 runtime is available.

---

## 2. Build Changes (`pom.xml`)

> **Superseded.** This table describes the first, `javax`-based step (Spring 5.3.39, Tomcat 9). The branch has since moved to Spring 7 / Jakarta EE for Tomcat 11; the current dependency set is in `docs/TOMCAT_MIGRATION.md`, section 1.

| Change | From | To | Why |
|---|---|---|---|
| Compiler | `maven-compiler-plugin` 3.8.0, `source/target 1.8` | 3.13.0, `<release>21</release>` | Compile for Java 21; `release` also checks JDK API usage. |
| WAR plugin | `maven-war-plugin` 2.2 | 3.4.0 | 2.2 fails on JDK 16+ (reflective access in its XStream dependency). |
| Spring Framework | 5.3.20 | 5.3.39 | Last 5.3.x release; supports JDK 8–21 (older 5.3.x cannot fully read Java 21 class files during component scanning). |
| AspectJ | `aspectjtools` 1.6.12 | `aspectjweaver` 1.9.22.1 | AspectJ 1.6 cannot parse Java 21 class files; Spring AOP only needs the weaver. Required by the `@Aspect` classes and `<aop:aspectj-autoproxy/>`. |
| Servlet API | `javax.servlet-api` 3.0.1, compile scope | `provided` scope | Supplied by the container; bundling it in `WEB-INF/lib` is rejected by Tomcat. |
| JSR-250 | (JDK) | `javax.annotation-api` 1.3.2 | Removed from the JDK in 11; needed for `@PostConstruct` in `CommonService`. Without it Spring silently skips that initialisation. |
| JAXB | (JDK) | `jakarta.xml.bind-api` 2.3.3 + `org.glassfish.jaxb:jaxb-runtime` 2.3.9 (still `javax.xml.bind`) | Removed from the JDK in 11; used by the `oxm:jaxb2-marshaller` (`GridObject`, `JsonObject`, `SearchPersonCriteriaVO`) and by Jackson 1.x `JaxbAnnotationIntrospector`. |
| docx4j JAXB binding | `docx4j-JAXB-Internal` 8.1.4 | `docx4j-JAXB-ReferenceImpl` 8.1.4 | The "Internal" binding uses `com.sun.xml.internal.bind`, which no longer exists. Used by the perinatal letter (`PerinatalService`). |

---

## 3. Code That Had to Change

| File | Change | Reason |
|---|---|---|
| `controller/initialize/InitializeController.java` | `javax.xml.bind.DatatypeConverter.parseBase64Binary(...)` → `java.util.Base64.getMimeDecoder().decode(...)` | `DatatypeConverter` is part of JAXB, no longer in the JDK. Identical output for well-formed input (with or without line breaks) — verified on 80,000 samples. For malformed/unpadded input the old method silently dropped (or crashed on) the last partial group; that input cannot decrypt either way. |

Everything else compiled unchanged with `--release 21`: there is no use of `sun.*` internals, Nashorn/`ScriptEngine`, CORBA, JAX-WS, `SecurityManager`, `finalize()` or `Thread.stop()`.

### Deprecated APIs replaced

| File | Before | After |
|---|---|---|
| `service/assignment/AssignmentTransactionHelper.java` (12) | `new Locale("en", "US")` (deprecated in Java 19) | `Locale.US` (equal value) |
| 3 standardization services (4) | `new Integer(1)` (marked for removal) | `Integer.valueOf(1)` — all readers unbox, no identity comparison |

### Deprecated APIs deliberately left

| Where | API | Why it was not changed |
|---|---|---|
| `UnStandardizedProviderService.readUrl` | `new URL(String)` (deprecated 20) | The URL contains user-entered names/cities with only spaces encoded; `URI.create(...)` would reject characters that `new URL` accepts today. Fix together with proper query encoding (`URLEncoder`). |
| `SystemMessageService` (8) | `new Date(String)` (deprecated) | Relies on the lenient legacy `Date.parse` format rules; replace with an explicit `DateTimeFormatter` once the exact input format is confirmed (pending item P-2). |

---

## 4. Language Modernization Applied

All changes are compile-time only (no behavior change) and were verified by a full `javac --release 21` compile.

| Change | Count | Example |
|---|---:|---|
| Diamond operator in declarations | 1,509 in 174 files | `Map<Object,Object> m = new HashMap<>();` |
| Local `StringBuffer` → `StringBuilder` | 16 | Locals only; fields and one local returned as `StringBuffer` were kept. |
| Arrow-form `switch` (no fall-through) | 9 switches | `case "ACT" -> crfDocVO.setSurveillanceMethodCode("Active");` |
| Pattern matching for `instanceof` | 4 | `if (cellValue instanceof BigDecimal decimal) { ... decimal.doubleValue() ... }` |
| Anonymous `Comparator` → lambda with `List.sort` | 1 | `StatusReportsService` monthly column sort |

The one remaining old-style switch (`STDConfDocumentService`) does not have the simple one-action-per-case shape and was left as is.

---

## 5. Not Changed Yet (modernization backlog)

These are larger structural changes. Each needs its own reviewed step and, ideally, tests first.

| Area | Size | Direction |
|---|---|---|
| Raw JDBC without try-with-resources | 72 references in 25 files; 150 `close()` calls | `try (Connection c = ...; PreparedStatement ps = ...)`, or move the SQL into MyBatis / `JdbcTemplate`. |
| `java.util.Date` / `Calendar` / `SimpleDateFormat` | 60 files; 47 `SimpleDateFormat` | `java.time` (`LocalDate`, `DateTimeFormatter`) — pending item P-2. |
| `org.json.simple` | 111 files | Jackson (already configured for Spring MVC). |
| Logging | 244 log calls with string concatenation; 19 `printStackTrace()`; 42 `System.out/err` | SLF4J placeholders `{}`, `log.error(msg, e)`. |
| Map iteration | 29 `keySet()` + `get()` loops | `entrySet()` / `forEach`. |
| Libraries | MyBatis 3.3.0, `mybatis-spring` 1.2.3, `ojdbc6`, Jackson 1.x (`org.codehaus`), `reactor-netty` 0.9, `commons-lang3` 3.5, `google-collections` 1.0, `wlfullclient` 10.3 | MyBatis 3.5.x + `mybatis-spring` 2.1.x; `ojdbc11`; Jackson 2; current Reactor; Guava. Upgrade one at a time with regression checks. |
| VOs | 224 getter/setter classes | Not candidates for `record` while MyBatis and JSP binding need setters; revisit with the JPA/DTO split. |

**Security note found during the review:** `InitializeController.decrypt` uses a hard-coded AES key in ECB mode. Unrelated to Java 21, but should be reviewed.

---

## 6. Verification

| Check | Result |
|---|---|
| `javac --release 21` over all sources, with every jar in the local Maven cache plus the new JAXB/JSR-250 API jars | Compiles; the only errors are the pre-existing ones from jars missing on the analysis machine (HL7 hapi, Oracle JDBC, Symantec, WebLogic, CRYPTOCard, HIE endec, an slf4j clash) — identical to the Java 8 baseline (3 fewer, because diamonds removed explicit type arguments that named missing classes). |
| Base64 replacement | Old and new decoders compared on 80,000 inputs. |
| `pom.xml` | Well-formed XML. |
| **Not done here** | `mvn clean package` with JDK 21 (no Maven on the analysis machine), deployment, and runtime tests. |

### Build and test checklist (on a machine with JDK 21 and Maven)

1. `mvn clean package -DskipTests` with `JAVA_HOME` pointing to JDK 21. Check `mvn dependency:tree` for duplicate `javax.xml.bind` jars.
2. Deploy to Tomcat 9 (or WebLogic 14.1.2+) running JDK 21, with the JNDI datasources configured.
3. Watch startup for: `UnsupportedClassVersionError`, `InaccessibleObjectException` (add a targeted `--add-opens` only if a library needs it), AspectJ pointcut errors, JAXB `ClassNotFoundException`, and the `CommonService` CRYPTOCard path debug lines (proves `@PostConstruct` ran).
4. Smoke-test: login/landing, person search, document review (HL7, CCD, ACRF), assignments and reports, lab reports, uploads, scheduler page, admin, system messages, the Excel/PDF downloads, and the perinatal letter (docx4j).
