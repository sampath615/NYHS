# NYEHMS: WebLogic 12c to Tomcat 11 Migration

| | |
|---|---|
| **Branch** | `java21-upgrade` (Java 21 + Tomcat 11; `main` stays on Java 8 / WebLogic 12c) |
| **Target stack** | Apache Tomcat 11 (Jakarta EE 11, Servlet 6.1) on JDK 21, Spring Framework 7.0 |
| **Status** | Code, configuration and build changes complete and compile-checked. **One blocker:** the HIN security filter jars (section 4). Not yet built with Maven against the internal repository, deployed or tested. |

---

## 1. Target stack

| Layer | Before | After |
|---|---|---|
| JDK | 8 | 21 (`<release>21</release>`, `<parameters>true</parameters>`) |
| Server | WebLogic 12c (Java EE 7, `javax.*`) | Tomcat 11 (Jakarta EE 11, `jakarta.*`) |
| Spring Framework | 5.3.20 | 7.0.9 |
| Servlet / JSP / JSTL | `javax.servlet-api` 3.0.1 (packaged!), `jstl` 1.2 | `jakarta.servlet-api` 6.1.0 (provided), Jakarta Standard Tag Library 3.0 (`jakarta.servlet.jsp.jstl` 3.0.1) |
| `@PostConstruct` | JDK (`javax.annotation`) | `jakarta.annotation-api` 3.0.0 (provided by Tomcat) |
| JAXB (oxm marshaller, docx4j) | JDK (`javax.xml.bind`) | `jakarta.xml.bind-api` 4.0.2 + `jaxb-runtime` 4.0.5 |
| AspectJ | `aspectjtools` 1.6.12 | `aspectjweaver` 1.9.22.1 |
| MyBatis | `mybatis` 3.3.0, `mybatis-spring` 1.2.3 | `mybatis` 3.5.19, `mybatis-spring` 4.0.0 |
| Oracle JDBC | `ojdbc6` 11.1 in the WAR | `ojdbc11` 23.5 in `$CATALINA_HOME/lib` (provided) |
| Quartz | 2.2.1 | 2.5.2 |
| Apache POI | 3.15 | 5.4.1 (with `commons-io` 2.18.0) |
| docx4j | 8.x (JAXB 2, `javax`) | 11.5.14 (Jakarta JAXB) + ImportXHTML 11.4.8 |
| Jackson | 1.x `jackson-xc` + transitive 2.x | `jackson-databind` 2.22.3 |
| Reactor (WebClient) | `reactor-netty` 0.9, `reactor-core` 3.3 | `reactor-netty-http` 1.3.7, `reactor-core` 3.8.7 |
| Removed | `wlfullclient` 10.3, `validation-api` 1.0 (unused), `jackson-xc`, docx4j MOXy, WebLogic manifest/build plugins | |

---

## 2. What changed

### WebLogic removed
| Area | Before | After |
|---|---|---|
| Data sources | `JndiObjectFactoryBean` + `weblogic.jndi.WLInitialContextFactory` (`BHAE_NYEHMS_DS_ONE_GL`, `BHAE_NYEHMS_URS`, `BHAE_EHARS_DS_ONE`) | `<jee:jndi-lookup resource-ref="true">` (`java:comp/env/jdbc/<same name>`) in the new `src/main/resources/spring-datasource.xml`, imported by `spring-config.xml`; pools in `src/main/webapp/META-INF/context.xml` (credentials from `${...}` placeholders); `<resource-ref>`s in `web.xml`. |
| Oracle arrays (`LabReportsService`, `LaftDetailsService`) | `(OracleConnection) connection` cast (works only with WebLogic's pool) + deprecated `ArrayDescriptor`/`ARRAY` | `connection.unwrap(OracleConnection.class)` + `createOracleArray(...)`. |
| `ControllerLayerInterceptingAspect` | Matched only `weblogic.servlet.internal.ServletRequestImpl` | Matches any `HttpServletRequest` (pending item P-7 done). The `controller.reports` pointcut typo (P-6) is unchanged. |
| Reminder loaders | Unused field with `weblogic.jws.Context` | Removed. |
| `weblogic.xml` | context root, 1800 s session, cookie path `/`, JSP precompile, session replication | Removed: timeout → `web.xml`; cookie path → `context.xml`; context root → WAR name; JSPs compile on first request; session replication → Tomcat cluster config if needed. |
| `environment.properties` | `db.jndi.server=t3://localhost:7001` | Removed (never read). |

### Jakarta EE / Spring 7
| Area | Change |
|---|---|
| Java sources | `javax.servlet.*` → `jakarta.servlet.*`, `javax.annotation.PostConstruct` → `jakarta.annotation.PostConstruct`, `javax.xml.bind.*` → `jakarta.xml.bind.*` (184 references, 105 files). JDK packages (`javax.crypto`, `javax.sql`, `javax.xml.parsers`) and the standalone `javax.mail`/`javax.activation` libraries stay. |
| `web.xml` | Jakarta EE 6.1 schema, `metadata-complete="true"` (keeps the old "no annotation scanning" behavior of the 2.5 descriptor); `<multipart-config>` on the `DispatcherServlet` (409,657,600 bytes, as before). |
| PDF/Excel views | `ResourceBundleViewResolver` + `views.properties` (removed in Spring 7) → `BeanNameViewResolver` + 25 view beans in `spring-config.xml` with the same ids, so controllers are unchanged. |
| Excel base class | `AbstractExcelReportView` no longer extends Spring's `AbstractXlsView` (deprecated for removal in 7.0); it extends `AbstractView` and renders exactly as before (`HSSFWorkbook`, content type `application/vnd.ms-excel`). |
| Uploads | `CommonsMultipartResolver` (removed) → `StandardServletMultipartResolver`; `CommonsMultipartFile` parameters in the scan controller/services → `MultipartFile`. |
| Jackson 1.x | Unused `JaxbAnnotationIntrospector` bean removed. |
| WebClient | `WebClientResponseException.getRawStatusCode()` (removed) → `getStatusCode().value()` in `AccessTokenClient`. |
| JSPs | **No change needed.** The Jakarta Tags 3.0 implementation still registers the old `http://java.sun.com/jsp/jstl/*` URIs; no JSP uses `javax.*`, `jsp:plugin` or other removed features. |

---

## 3. Behavior notes for testing

| Topic | Note |
|---|---|
| Parameter names | Spring 6.1+ resolves unnamed `@RequestParam`/`@PathVariable` parameters (e.g. `@RequestParam int rows`) from compiled parameter names. The pom now compiles with `-parameters`; **an IDE build must use the same flag**, otherwise requests fail with "name for argument ... not specified". |
| Trailing slash | Spring 6+ no longer matches `/x.html/` to `/x.html`. |
| MyBatis 3.5 | No mapper uses positional `#{0}` parameters and no multi-argument DAO method relies on implicit names, so the known 3.3 → 3.5 incompatibilities do not apply; still regression-test the stored-procedure (`CALLABLE`) screens. |
| Jackson | `MappingJackson2HttpMessageConverter` is deprecated in Spring 7 (Jackson 3 is the new default) but fully supported; move to `JacksonJsonHttpMessageConverter` + Jackson 3 later. |
| Logging | `log4j` 2.17.2 and `slf4j` 1.7 were kept; consider 2.24+/2.x later. `FullAppLogger` uses a deprecated `LogFactory.useLog4JLogging()`. |

---

## 4. Blocker: HIN/HCS security filters

`web.xml` routes every request through `gov.ny.health.hcs.fwrk.security.HCSSecurityFilter` and `us.ny.state.health.hin.srvs.filter.ServiceConsumerSecurityFilter` from `hin-framework-common` 1.2.5 / `hin-framework-services` 1.1.1 (internal repository; not available to this analysis). These were built for WebLogic and are almost certainly compiled against **`javax.servlet`**, which Tomcat 11 does not provide: the application would fail at startup with `NoClassDefFoundError: javax/servlet/Filter`.

Options, in order of preference:
1. **Jakarta builds from the owning (HIN/HCS) team**, then update the two versions in `pom.xml`.
2. **Stop-gap conversion** with the Apache Tomcat *Jakarta EE Migration Tool*:
   `java -jar jakartaee-migration-<version>-shaded.jar hin-framework-common-1.2.5.jar hin-framework-common-1.2.5-jakarta.jar` (same for `hin-framework-services`), publish the converted jars to the internal repository under a new version, and reference those.
3. Confirm with the owners that the filters do not depend on WebLogic security APIs (`weblogic.security.*`) or WebLogic-specific headers.

Also check `endec`, `BSIDJavaAPI` and `SymJavaAPI` the same way (they compiled here only as missing classes).

---

## 5. Tomcat 11 set-up

1. **JDK 21** and **Tomcat 11.0.x**.
2. Copy **`ojdbc11.jar`** to `$CATALINA_HOME/lib`.
3. In `$CATALINA_BASE/conf/catalina.properties` add `nyehms.db.app.url/user/password`, `nyehms.db.urs.url/user/password`, `nyehms.db.ehars.url/user/password`.
4. Set the application stage as on WebLogic, e.g. `CATALINA_OPTS=-Dstage=PROD` in `bin/setenv.sh` (`setenv.bat` on Windows).
5. Build: `mvn clean package` with `JAVA_HOME` = JDK 21 (needs the internal repository for the HIN, `endec`, BSID, Symantec and `hapi` artifacts).
6. Deploy the WAR as **`doh2#applinks#nyehms.war`** (context path `/doh2/applinks/nyehms`).
7. Watch the startup log for: `NoClassDefFoundError: javax/...` (a library still on `javax` — see section 4), bean-creation errors, JAXB errors, and the `CommonService` CRYPTOCard path debug lines (proves `@PostConstruct` ran).
8. Smoke-test: login/landing, person search, document review (HL7, CCD, ACRF), assignments and reports, lab reports incl. LOINC local-code merge and LAFT reprise / do-not-report / delete (Oracle arrays), uploads and scanning (multipart), scheduler page, admin, system messages, every Excel/PDF download, the perinatal letter (docx4j), and the HCS OAuth web-service calls (WebClient).

---

## 6. Verification done

| Check | Result |
|---|---|
| Dependency resolution | `mvn dependency:copy-dependencies` on the new pom (internal artifacts excluded): 142 jars, no `javax.servlet` classes, no conflicting duplicates; `jakarta.el-api` excluded (Tomcat provides it). |
| Compile | `javac --release 21 -parameters` over all sources against that classpath (+ public `hapi` 2.2 jars): **33 errors, all caused by the three unavailable internal jars** (Symantec ScanEngine, HIE `endec`, CRYPTOCard). The original Java 8 baseline had 299 errors. |
| Deprecations | Nothing marked for removal; remaining deprecations: `Date(String)` ×8, `URL(String)` ×1 (kept on purpose, see `docs/JAVA21_UPGRADE.md`), `LogFactory.useLog4JLogging()` ×1. |
| Spring configuration | Every class named in `spring-config.xml` exists in the resolved jars or the project; all XML namespaces still resolve in Spring 7. |
| XML | `pom.xml`, `web.xml`, `context.xml`, `spring-config.xml` well-formed. |
| **Not done** | Maven build with the internal repository, deployment, runtime tests. |
