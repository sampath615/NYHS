package gov.ny.state.doh.bhae.nyehms.web.spring.service.scan;

import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

@Service
public class RemoteServerUtil {

	private static Logger log = LoggerFactory.getLogger(RemoteServerUtil.class);
	private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();

	private java.util.Properties config = new java.util.Properties();
	private JSch jsch = new JSch();
	private Session session = null;
	private ChannelSftp obj_SFTPChannel = null;

	public ChannelSftp getRemoteServerSession()  {

//		if (obj_SFTPChannel != null && obj_SFTPChannel.isConnected()) {
//			return obj_SFTPChannel;
//		}

		try {

			createSFTPChannel();
			
		} catch (JSchException e) {
			
			log.error(nyehmsLog+"   : JSchException while creating the SFTP Channel   "+e.getMessage());
			
			StringWriter sw = new StringWriter();
			
			PrintWriter pw = new PrintWriter(sw);
			
			e.printStackTrace(pw);
			
			String sStackTrace = sw.toString(); // stack trace as a string
			
			if(CommonUtil.isNotNullAndEmpty(sStackTrace) && sStackTrace.contains("session is down")) {
				try {
					log.error("Session is down creating the channel again");
					createSFTPChannel();
				}catch(Exception sessionException) {
					log.error("Unable to connect to the session {}", sessionException.getMessage());
				}
				
			}
			
			log.error(sStackTrace);
			
		}  catch (Exception e) {
			
			log.error(nyehmsLog+"   : Exception while creating the SFTP Channel      "+e.getMessage());
			
			StringWriter sw = new StringWriter();
			
			PrintWriter pw = new PrintWriter(sw);
			
			e.printStackTrace(pw);
			
			String sStackTrace = sw.toString(); // stack trace as a string
			
			log.error(sStackTrace);

		
		} 
		
		return obj_SFTPChannel;

	}
	
	private void createSFTPChannel()throws JSchException, UnsupportedEncodingException {
		log.error("Session and Channel is not availabe, preparing SFTP Channel and Session");

		if(session == null || !session.isConnected()){

			log.error("Preparing the Session");

			//config.put("kex", EnvironmentProperties.envProperties.getProperty("KexAlgorithm"));
			
			config.put("StrictHostKeyChecking", "no");
			config.setProperty("PreferredAuthentications", "password");
			
			log.error("Config entries were added");
			
			session = jsch.getSession("srvDOHNyehmsScan", "10.88.158.26", 22);
			
			log.error("Set the User name and Host name");

			
			session.setPassword(CommonUtil.decryptValue(EnvironmentProperties.envProperties.getProperty("SCAN_PWD")));
			//n@cS9102
			
			
			//session = jsch.getSession("tracklod", "150.142.5.2", 22);

			//session.setPassword("gre5cli");
			
			session.setConfig(config);
			
			
			log.error("set the config to session");

			session.connect();
				
			log.error("Connecting to the session");
				
				
		}
			
		log.error("Connected to Schoit Session");
		
		
		obj_SFTPChannel = (ChannelSftp)session.openChannel("sftp");
		
		log.error("changed the channel to sftp");
		
		obj_SFTPChannel.connect();
		
		log.error("Connected to the Schoit SFTP Channel");
	}
}
