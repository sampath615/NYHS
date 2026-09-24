package gov.ny.state.doh.bhae.nyehms.web.spring.util;

import org.apache.ibatis.session.SqlSessionFactory;

/*
 * Author 	: Srikar Kummamuri
 * Date 	: 2/12/12
 */

public class GetSQLSession {	
	private SqlSessionFactory sqlSessionFactory;
	private org.apache.ibatis.session.SqlSession sqlSession;


	
	public org.apache.ibatis.session.SqlSession getSqlSession(){
		
		/*
		Resource res  = new FileSystemResource("/WEB-INF/spring-servlet.xml");		
		ClassPathResource res = new ClassPathResource("spring-servlet.xml");
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-servlet.xml");
		
		BeanFactory beanFactory = 
		new XmlBeanFactory(getClass().getResourceAsStream("/spring-servlet.xml"));

		BeanFactory factory = new XmlBeanFactory(res);
		SqlSessionTemplate test = (SqlSessionTemplate) factory.getBean("sqlSession");	
		return test.getConnection();
		*/
		
		return sqlSession;
	}
	
	public void setSqlSession(org.apache.ibatis.session.SqlSession sqlSession) {
	    this.sqlSession = sqlSession;
	  }	
	
}
