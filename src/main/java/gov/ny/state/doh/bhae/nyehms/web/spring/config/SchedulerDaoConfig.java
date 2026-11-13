package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Finds the DAO classes of the scheduler packages {@code schedulers.spring.dao.*} (SchedulerJobsDAO,
 * EharsDataLoaderDAO, EcrlsDataDAO, AIDADataDAO — {@code @Repository} classes since they moved from MyBatis), which lie
 * outside the {@code web.spring} packages the application scans.
 */
@Configuration
@ComponentScan(basePackages = "gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao")
public class SchedulerDaoConfig {
}
