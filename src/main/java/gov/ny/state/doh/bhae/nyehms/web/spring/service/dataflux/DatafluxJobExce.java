package gov.ny.state.doh.bhae.nyehms.web.spring.service.dataflux;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;

@Service
public class DatafluxJobExce {

	private static final Logger log = LoggerFactory.getLogger(DatafluxJobExce.class);
	private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();

	private Session session = null;
	Channel shellChannel = null;
	PrintStream shellStream = null;
	
	public void runRemoteServerJob() {

		try {
				
				java.util.Properties config = new java.util.Properties();
				JSch jsch = new JSch();
				
	
				config.put("StrictHostKeyChecking", "no");
				config.setProperty("PreferredAuthentications", "password");
				
			
				session = jsch.getSession("ska03", "150.142.68.13", 22);
				
				log.error("Set the User name and Host name");
				
				session.setPassword("Albany8989");
				
				session.setConfig(config);
				
				session.connect(10000);
				
				log.error("session Connected");
				
				List<String> commands = new ArrayList<String>();
			  	commands.add("@echo off");
			  	commands.add("set THREADS=10");
			    commands.add("set RUNDATE=%DATE:~12,2%%DATE:~4,2%%DATE:~7,2%");
			    
			    commands.add("call \"c:\\Program Files\\SASHome\\DataFluxDataManagementServer\\2.7\\bin\\dmpexec\" -j \"c:\\Program Files\\SASHome\\DataFluxDataManagementServer\\2.7\\var\\batch_jobs\\ER_PROCESS_CONTROL.djf\" -l \"c:\\test\\ER_PROCESS_CONTROL_%RUNDATE%.log\" -o \"BASE/REPOS_FILE_ROOT= c:\\Program Files\\SASHome\\DataFluxDataManagementServer\\2.7\\var\" -i \"RUN_DATE=%RUNDATE%\" -i \"THREADS=%THREADS%\"");
			    
				shellChannel=session.openChannel("shell");//only shell  
				shellChannel.setOutputStream(System.out); 
		        shellStream = new PrintStream(shellChannel.getOutputStream());  // printStream for convenience 
		        shellChannel.connect(5000);
		        for(String command: commands) {
		            shellStream.println(command); 
		            shellStream.flush();
		            Thread.sleep(2000);
		        }
								
					
						
		} catch (JSchException e) {
			
			log.error("{}   : JSchException while creating the SFTP Channel   {}",nyehmsLog,e.getMessage());
			
			StringWriter sw = new StringWriter();
			
			PrintWriter pw = new PrintWriter(sw);
			
			e.printStackTrace(pw);
			
			String sStackTrace = sw.toString(); // stack trace as a string
			
			log.error(sStackTrace);
			
		}  catch (Exception e) {
			
			log.error("{}   : Exception while creating the SFTP Channel     {}",nyehmsLog ,e.getMessage());
			
			StringWriter sw = new StringWriter();
			
			PrintWriter pw = new PrintWriter(sw);
			
			e.printStackTrace(pw);
			
			String sStackTrace = sw.toString(); // stack trace as a string
			
			log.error(sStackTrace);

		
		} finally {
			if(session != null) {
				session.disconnect();
			}
			if(shellChannel != null) {
				shellChannel.disconnect();
			}
			try {
				if(shellStream != null) {
					shellStream.close();
				}
			
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		


	}
}
