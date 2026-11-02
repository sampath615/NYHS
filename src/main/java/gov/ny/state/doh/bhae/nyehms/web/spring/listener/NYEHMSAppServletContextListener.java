package gov.ny.state.doh.bhae.nyehms.web.spring.listener;

import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * This class is used to load the Required information while starting the
 * server.
 * 
 * @author ska03
 * 
 */
public class NYEHMSAppServletContextListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(NYEHMSAppServletContextListener.class);

	private final String STAGE_FILE_PATH = "/usr/local/bin/stage";

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// System.out.println("ServletContextListener destroyed");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		File fStage = null;
		FileReader fReader = null;
		BufferedReader buffReader = null;

		try {
			if (logger.isDebugEnabled())
				logger.debug("Loading the property file using STAGE {}", STAGE_FILE_PATH);
			
			//Reading the Stage(local/Dev/EVAL/PROD) from the file
			fStage = new File(STAGE_FILE_PATH);
			if (fStage.exists()) {
				if (fStage.canRead()) {
					fReader = new FileReader(fStage);
					buffReader = new BufferedReader(fReader);
					String stage = buffReader.readLine();
					//set the stage property
					System.setProperty("stage", stage);

					loadEnvironmentPropertiesFile(stage);

					if (logger.isDebugEnabled())
						logger.debug("Successfully loaded {} file and ", stage);
								
				} else {
					logger.error("Unable to read the file {}. Make sure the file is readable.", STAGE_FILE_PATH);
				}

			} else {
				logger.error("unable to find the file {}. Make sure the file exists.", STAGE_FILE_PATH);
			}

		} catch (FileNotFoundException fileNotFoundException) {

			logger.error("unable to find the file {}. Make sure the file exists.  ", STAGE_FILE_PATH, fileNotFoundException);
		} catch (IOException ioException) {
			logger.error("Exception while reading the file {}. ",STAGE_FILE_PATH, ioException);
		} finally {
			if (System.getProperty("stage") == null) {
				System.setProperty("stage", "PROD");
				loadEnvironmentPropertiesFile("PROD");
			}
			try {
				if (buffReader != null)
					buffReader.close();
				if (fReader != null)
					fReader.close();
			} catch (IOException ioException) {
				logger.error("could not close file reader object while reading STAGE. ", ioException);
			}
		}

		// used by the JSPs as a cache-busting version on static JS/CSS URLs (?v=${buildDate})
		arg0.getServletContext().setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));

	}

	/**
	 * This method is used to load the property file information in the
	 * EnvironmentProperties object
	 * 
	 * For every stage the few variables will differ
	 * 
	 * Example1: Once you logout the redirecting url will change for each
	 * environment Example2: For Local login page will use proxy url, but in
	 * Dev,EVAL,PROD will use Crypto card page.
	 * 
	 * For these kind of properties will define in Environmental property files
	 * will load the file depends on the environment
	 * 
	 * 
	 * @param stage
	 *            (LOCAL/DEV/EVAL/PROD)
	 */
	private void loadEnvironmentPropertiesFile(String stage) {
		try {

			InputStream inputStream = getClass().getClassLoader()
					.getResourceAsStream(stage + "_environment.properties");
			
			EnvironmentProperties.envProperties.load(inputStream);
			
			inputStream = getClass().getClassLoader()
					.getResourceAsStream("CommonEnvironment.properties");
			
			EnvironmentProperties.envProperties.load(inputStream);
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
           
			EnvironmentProperties.envProperties.setProperty("installationDate", simpleDateFormat.format(new Date()));

		} catch (IOException ioException) {
			logger.error("Exception occured while reading the propery file. ", ioException);
		} catch (Exception exception) {
			logger.error("Exception occured while reading the propery file. ", exception);
		}

	}
}
