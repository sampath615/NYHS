package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Finds the JPA DAO classes of the scheduler package {@code schedulers.spring.dao.nyehms} (e.g. SchedulerJobsDAO, a
 * {@code @Repository} class since it moved from MyBatis to JPA), which lies outside the {@code web.spring} packages the
 * application scans. The MyBatis mapper interfaces left in that package (EharsDataLoaderDAO) are still registered by
 * MyBatisConfig's {@code @MapperScan}; a component scan does not pick up interfaces.
 */
@Configuration
@ComponentScan(basePackages = "gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.nyehms")
public class SchedulerDaoConfig {
}
