package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.nyehms", sqlSessionFactoryRef = "sqlSessionFactory")   // web.spring.dao has no MyBatis mapper left
@MapperScan(basePackages = "gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.ecrls", sqlSessionFactoryRef = "elrlsSessionFactory")
@MapperScan(basePackages = "gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.ursdb", sqlSessionFactoryRef = "ursSessionFactory")
public class MyBatisConfig {

    /** Main NYEHMS database: the scheduler "nyehms" DAO (EharsDataLoaderDAO); the web DAOs are JPA now. */
    @Bean
    public SqlSessionFactory  sqlSessionFactory(@Qualifier("appDS") DataSource appDS) throws Exception {
        return sessionFactory(appDS, "gov.ny.state.doh.bhae.nyehms.web.spring.domain");
    }

    /** ECLRS scheduler DAOs. These also run on the main NYEHMS database (appDS). */
    @Bean
    public SqlSessionFactory elrlsSessionFactory(@Qualifier("appDS") DataSource appDS) throws Exception {
        return sessionFactory(appDS, "gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.ecrls");
    }

    /** URS database scheduler DAOs. */
    @Bean
    public SqlSessionFactory ursSessionFactory(@Qualifier("ursDS") DataSource ursDS) throws Exception {
        return sessionFactory(ursDS, "gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.ursdb");
    }



    private SqlSessionFactory sessionFactory(DataSource dataSource, String typeAliasesPackage) throws Exception {

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setTypeAliasesPackage(typeAliasesPackage);
        return factoryBean.getObject();

    }

}
