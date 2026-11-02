package gov.ny.state.doh.bhae.nyehms.web.spring.util;

import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DateUtil {
	
	private static final Logger log = LoggerFactory.getLogger(DateUtil.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	
	private final DateFormat nyehmsTimeStampFromatter = new SimpleDateFormat("MM/dd/yyyy");

	private static final String DATE_FORMAT_NOW = "MM/dd/yyyy HH:mm:ss";
	
	
	private static java.util.Date dt =null;
	
	
	public java.util.Date getNyehmsDateFromString(String date){
		try{
			dt= (java.util.Date)nyehmsTimeStampFromatter.parse(date);
		}catch(ParseException e){
			log.debug(nyehmsLog+" Parse ERROR "+e.getLocalizedMessage());
		}catch(Exception e){
			log.debug(nyehmsLog+" ERROR "+e.getLocalizedMessage());
		}
		return dt;		
	}
	
	public  String getSystemDateTime() {
		String systemTime="";
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
			Calendar cal = Calendar.getInstance();
			systemTime=sdf.format(cal.getTime());		
		}catch(IllegalArgumentException e){
			log.debug(nyehmsLog+" Parse ERROR "+e.getLocalizedMessage());
		}catch(Exception e){
			log.debug(nyehmsLog+" ERROR "+e.getLocalizedMessage());
		}
		return systemTime;
	}
	
	public Timestamp getSqlDateTimeStamp() {
		String systemTime="";
		Timestamp sqlTimeStamp= new Timestamp(new Date().getTime());
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
			Calendar cal = Calendar.getInstance();
			systemTime=sdf.format(cal.getTime());	
			sqlTimeStamp=(Timestamp)sdf.parse(systemTime);
		}catch(IllegalArgumentException e){
			log.debug(nyehmsLog+" Parse ERROR "+e.getLocalizedMessage());
		}catch(Exception e){
			log.debug(nyehmsLog+" ERROR "+e.getLocalizedMessage());
		}
		return sqlTimeStamp;
	}
	
	public Timestamp getSqlDateTimeStamp(String date) {
	
		
		Timestamp sqlTimeStamp = null;
		try{
			SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat("MM/dd/yyyy");
			Date dateformat = datetimeFormatter1.parse(date);
			System.out.println("DateUtil dateformat :" + dateformat);
			sqlTimeStamp = new Timestamp(dateformat.getTime());
			System.out.println("DateUtil sqlTimeStamp :" + sqlTimeStamp);
		}catch(IllegalArgumentException e){
			log.debug(nyehmsLog+" Parse ERROR "+e.getLocalizedMessage());
		}catch(Exception e){
			log.debug(nyehmsLog+" ERROR "+e.getLocalizedMessage());
		}
		return sqlTimeStamp;
	}
	
	public  String get30DaysPriorDate() {
		String priorDate = "";
		
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_MONTH, -30);
		
		try{
			priorDate = nyehmsTimeStampFromatter.format(cal.getTime());	
		}catch(Exception e){
			log.debug(nyehmsLog+" ERROR "+e.getLocalizedMessage());
		}
		
		return priorDate;
	}
	
	public  String getCurrentDate() {
		String currentDate = "";
		
		try{
			currentDate = nyehmsTimeStampFromatter.format(new Date());	
		}catch(Exception e){
			log.debug(nyehmsLog+" ERROR "+e.getLocalizedMessage());
		}
		
		return currentDate;
	}
	
	
	
	public  static String convertStringToTimeStampInCustomFormat(String customDate, String outputDateFormat){
		String customFormattedDate = null;
		String inputDateFormat = null;
		if(customDate != null && !"".equals(customDate) ){
			if(customDate.indexOf('-')!=-1){
				inputDateFormat = "yyyy-MM-dd";
			}else if(customDate.indexOf('/')!=-1){
				inputDateFormat = "MM/dd/yyyy";
			}
			try {
				SimpleDateFormat inputFormat = new SimpleDateFormat(inputDateFormat);
				SimpleDateFormat outputFormat = new SimpleDateFormat(outputDateFormat);
				customFormattedDate = outputFormat.format(inputFormat.parse(customDate));
			} catch (ParseException parseException) {
				// TODO Auto-generated catch block
				log.debug("Unable to parse String to TimeStamp   {}",parseException.getMessage());
				return customFormattedDate;
			}
			return customFormattedDate;
		}else
			return customFormattedDate;
	}
	
	
	public static boolean isValidDate(String strDate)	{
		
	    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	    Calendar calendar = Calendar.getInstance();

	    // declare and initialize testDate variable, this is what will hold our converted string
	    Date testDate = null;
	    Date minDate = null;
	    Date maxDate = calendar.getTime();
	    try {
	    	testDate = sdf.parse(strDate);
	    	minDate = sdf.parse("01/01/1900");
	    }
	    // if the format of the string provided doesn't match the format we declared in SimpleDateFormat() we will get an exception
	    catch (ParseException e) {
	      return false;
	    }

	    /* dateformat.parse will accept any date as long as it's in the format you defined, it simply rolls dates over, for example, 
	     * december 32 becomes jan 1 and december 0 becomes november 30. This statement will make sure that once the string has been 
	     * checked for proper formatting that the date is still the date that was entered, if it's not, we assume that the date is invalid
		*/
	    if (!sdf.format(testDate).equals(strDate)) {
	      return false;
	    }
	    
	    // if we make it to here without getting an error it is assumed that the date was a valid one and that it's in the proper format
	    if (testDate.compareTo(minDate) < 0) {
	    	return false;
	    }
	    if (testDate.compareTo(maxDate) > 0) {
	    	return false;
	    }
	    return true;

	}
	
	public static String convertDateMMDDYYYY(String inputDate) {
		
		DateTimeFormatter formatterBuilder = new DateTimeFormatterBuilder()
			    .appendOptional(DateTimeFormatter.ofPattern("M/dd/yyyy"))
			    .appendOptional(DateTimeFormatter.ofPattern("M/d/yyyy"))
			    .appendOptional(DateTimeFormatter.ofPattern("MM/d/yyyy"))
			    .appendOptional(DateTimeFormatter.ofPattern(("MM/dd/yyyy")))
			    .toFormatter();

			LocalDate inputDateLocal = LocalDate.parse(inputDate, formatterBuilder);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY"); 
			
			return formatter.format(inputDateLocal);
		
	}
	

}
