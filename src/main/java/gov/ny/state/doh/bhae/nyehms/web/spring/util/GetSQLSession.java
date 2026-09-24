package gov.ny.state.doh.bhae.nyehms.web.spring.util;


/*
 * Author 	: Srikar Kummamuri
 * Date 	: 2/12/12
 */

public class GetSQLSession {	
	private org.apache.ibatis.session.SqlSession sqlSession;


	
	public org.apache.ibatis.session.SqlSession getSqlSession(){
		
		
		return sqlSession;
	}
	
	public void setSqlSession(org.apache.ibatis.session.SqlSession sqlSession) {
	    this.sqlSession = sqlSession;
	  }	
	
}
