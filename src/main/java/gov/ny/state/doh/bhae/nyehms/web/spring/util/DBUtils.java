package gov.ny.state.doh.bhae.nyehms.web.spring.util;


import java.sql.Connection;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBUtils {
	
	private static Logger log = LoggerFactory.getLogger(DBUtils.class);
	
	private String jndiUrl;
	private String jndiName;
	
	public void setJNDIURL(String url){	
		jndiUrl = url;		
	}	
	
	public void setJNDIName(String name){
		jndiName= name;		
	}	
	
	public Connection getDBConnection(){		
		
		Connection conn=null;		
		Hashtable<Object,Object> ht = new Hashtable<Object,Object>();  
		ht.put(Context.INITIAL_CONTEXT_FACTORY,  "weblogic.jndi.WLInitialContextFactory");  
		ht.put(Context.PROVIDER_URL, jndiUrl);
				
		try{			
			Context ic = new InitialContext(ht);			
			DataSource dataSource = (DataSource) ic.lookup(jndiName);			
			conn = (Connection)dataSource.getConnection();			
		}catch(Exception e){
			log.debug("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROOOOOOOOOOOOOOOOOOORRRRRRRRRRRRR {}",e.getMessage());			
		}
		return conn;
	}
}
