package gov.ny.state.doh.bhae.nyehms.web.spring.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v25.datatype.TS;

@Component
public class CommonUtil {

	private static Logger log = LoggerFactory.getLogger(CommonUtil.class);
	private static final Pattern spanishRegExPattern = Pattern.compile("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}]+");
	private static final Pattern unknownCharactersPattern = Pattern.compile("[\\p{IsSk}]+");
	private static final Pattern notAsciiCharactersPattern = Pattern.compile("[^\\p{ASCII}]+");
	private static final Pattern dinpattern = Pattern.compile("[0-9][0-9][A-Za-z][0-9][0-9][0-9][0-9]");
	private static final Pattern nysidpattern = Pattern.compile("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][A-Za-z]");
	private static final Pattern cinPattern = Pattern.compile("[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][A-Z]");
	private static final String ZIP_ZEROS = "00000";
	private static final String ZIP_NINES = "99999";

	public String trimCounty(String county) {
		if (county != null && county.toUpperCase().endsWith(" CO.")) {
			county = county.substring(0, county.length() - 4);
		}
		return county;
	}

	public String convertToCommaSeparated(long[] array) {
		String commaSeparated = "";
		if (array != null && array.length > 0) {
			for (long value : array) {
				commaSeparated += value + ",";
			}
			commaSeparated = commaSeparated.substring(0, commaSeparated.length() - 1);
		}
		return commaSeparated;
	}

	public String convertToCommaSeparated(List<Long> list) {
		String commaSeparated = "";
		if (list != null && list.size() > 0) {
			for (long value : list) {
				commaSeparated += value + ",";
			}
			commaSeparated = commaSeparated.substring(0, commaSeparated.length() - 1);
		}
		return commaSeparated;
	}

	public static String cnsTrainingDateStatusMessage(int noOfHoursDueForTraining, Calendar expiryDate) {
		StringBuilder statusMessage = new StringBuilder();
		int noOfDaysDueForTraining = (int) (noOfHoursDueForTraining / 24);

		if (noOfHoursDueForTraining % 24 > 0) {
			noOfDaysDueForTraining++;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
		String expirationDate_customFormat = dateFormat.format(expiryDate.getTime());
		if (noOfDaysDueForTraining == 1) {
			statusMessage.append("Your Confidentiality and Security Training expires Tommorrow."
					+ " Please contact BHAE at BHAE.Training@health.ny.gov to schedule training."
					+ "  You will lose access to NYEHMS on " + expirationDate_customFormat
					+ " if you are not retrained.");
		} else if (noOfDaysDueForTraining > 1 && noOfDaysDueForTraining < 40) {
			statusMessage.append("Your Confidentiality and Security Training expires in  ")
					.append(noOfDaysDueForTraining).append(" days ")
					.append(". Please contact BHAE at BHAE.Training@health.ny.gov to schedule training."
							+ "  You will lose access to NYEHMS on " + expirationDate_customFormat
							+ " if you are not retrained.");
		} else if (noOfDaysDueForTraining <= 0) {
			statusMessage.append(
					"The Validity of Confidentiality & Security Training is already expired. Please contact BHAE at BHAE.Training@health.ny.gov to schedule training.");
		}
		return statusMessage.toString();
	}

	/*
	 * Placeholder that search screens send when the phone field was left empty
	 */
	public static final String EMPTY_PHONE_MASK = "xxx-xxx-xxxx";

	/**
	 * Converts a phone number entered on a search screen into a position-level
	 * search pattern: <code>-</code> is removed and <code>x</code>/<code>X</code>
	 * become the single-character wildcard <code>?</code>. The masking is applied
	 * only when the value contains a lower-case <code>x</code> or a <code>-</code>.
	 *
	 * @param phone the phone number as entered, may be <code>null</code>
	 * @return an empty string for {@link #EMPTY_PHONE_MASK}, the masked pattern,
	 *         or the value unchanged
	 */
	public static String toPhoneSearchPattern(String phone) {
		if (EMPTY_PHONE_MASK.equals(phone)) {
			return "";
		}
		if (phone != null && (phone.indexOf('x') != -1 || phone.indexOf('-') != -1)) {
			return phone.replace("-", "").replace('x', '?').replace('X', '?');
		}
		return phone;
	}

	public static String getUnMaskedPhoneNumber(List<String> phoneNumberList) {
		String phoneNumber = null;

		if (phoneNumberList != null && phoneNumberList.size() == 1) {
			phoneNumber = phoneNumberList.get(0);
			return EMPTY_PHONE_MASK.equals(phoneNumber) ? null : toPhoneSearchPattern(phoneNumber);
		} else {
			return null;
		}
	}

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional '-' and decimal.
	}

	public static String decryptValue(String _inputValue) throws UnsupportedEncodingException {
		return new String(Base64.getDecoder().decode(_inputValue.getBytes()), "UTF-8");
	}

	public static String encryptValue(String _inputValue) throws UnsupportedEncodingException {
		return new String(Base64.getEncoder().encode(_inputValue.getBytes()), "UTF-8");
	}

	
	public static String convertDateToStringFormat(Date date, String dateFormat) {
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		    return sdf.format(date);
		} catch (Exception timeFormatException) {
			log.error("Unable to convert the String to Std Date");
			return null;
		}
	}


	public static boolean isNotNullAndEmpty(String _inputValue) {
		if (_inputValue != null && !"".equals(_inputValue.trim())) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNullOrEmpty(String _inputValue) {
		if (_inputValue == null || "".equals(_inputValue.trim())) {
			return true;
		} else {
			return false;
		}
	}

	public static String formatZipCode(String _inputZip) {
		if (isNotNullAndEmpty(_inputZip)) {
			_inputZip = _inputZip.replaceAll("[^\\d]", "");
			if (_inputZip.length() > 5) {
				_inputZip = _inputZip.substring(0, 5);
			}

			if (ZIP_ZEROS.equals(_inputZip.trim()) || ZIP_NINES.equals(_inputZip.trim())) {
				_inputZip = "";
			}
		}
		return _inputZip;
	}

	public static String formatNumbericField(String _fieldValue, int fieldLength) {
		if (isNotNullAndEmpty(_fieldValue)) {
			
			_fieldValue = _fieldValue.replaceAll("[^\\d]", "");
			
			if(_fieldValue.length() >= fieldLength) {
				
				return _fieldValue.substring(0, fieldLength);
			}else {
				return null;
			}
			
		}
		return _fieldValue;
	}

	public static String formatZipAndPlus4Code(String _inputZip) {
		if (isNotNullAndEmpty(_inputZip)) {
			_inputZip = _inputZip.replaceAll("[^\\d]", "");
		}
		return _inputZip;
	}

	public static String formatSSNValue(String ssn) {
		String outputSSN = "";

		if (isNotNullAndEmpty(ssn)) {
			ssn = ssn.replaceAll("[^\\d]", "");
			if (ssn.length() > 9) {
				ssn = ssn.substring(0, 9);
			}
			if (ssn.length() == 9 && (!"012345678".equals(ssn) && !"123456789".equals(ssn))) {
				Pattern pattern = Pattern.compile("([0-9])\\1{3}");
				Matcher matcher = pattern.matcher(ssn);
				if (!matcher.find()) {
					outputSSN = ssn;
				}
			}
		}
		return outputSSN;
	}

	public static String convertCalenderToString(Calendar calender) {
		int _year = calender.get(Calendar.YEAR);
		int _month = calender.get(Calendar.MONTH) + 1;
		int _day = calender.get(Calendar.DATE);

		StringBuilder _returnDate = new StringBuilder();
		if (_month < 10) {
			_returnDate.append(0);
		}
		_returnDate.append(_month).append("/");
		if (_day < 10) {
			_returnDate.append(0);
		}
		_returnDate.append(_day).append("/");
		_returnDate.append(_year);
		return _returnDate.toString();
	}

	public static Calendar resetTimeToZero(Timestamp _currentTimeStampValue, int comparisonYear) {

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(_currentTimeStampValue);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		if (calendar.get(Calendar.YEAR) > comparisonYear) {
			return calendar;
		} else {
			return null;
		}
	}

	public static String removeSingleQuote(String inputStr) {
		if (isNotNullAndEmpty(inputStr)) {
			return inputStr.trim().replaceAll("\'", "");
		}
		return inputStr;
	}

	public static String NameValidation(String inputStr) {

		if (isNotNullAndEmpty(inputStr)) {
			return normalizeString(inputStr).replaceAll("[^A-Za-z0-9-',. ]+", " ");
		}
		return inputStr;
	}

	public static String GetNumberFromString(String inputStr) {

		if (isNotNullAndEmpty(inputStr)) {
			return normalizeString(inputStr).replaceAll("\\D+", "");
		}
		return inputStr;
	}

	public static String addressValidation(String inputStr) {

		if (isNotNullAndEmpty(inputStr)) {
			
			String address = inputStr.replaceAll("\\s+", " ");
			
			
			return normalizeString(address).replaceAll("[^-/A-Za-z0-9*@#&;:()\"\',. ]+", " ");
		}
		return inputStr;
	}

	public static String dinValidation(String inputStr) {

		if (isNotNullAndEmpty(inputStr)) {
			inputStr = inputStr.replaceAll("[^A-Za-z0-9]+", "");
			Matcher matcher = dinpattern.matcher(inputStr);
			if (!matcher.matches()) {
				return null;
			} else {
				return inputStr;
			}
		} else {
			return inputStr;
		}
	}

	public static String zipValidation(String inputStr) {

		if (isNotNullAndEmpty(inputStr) && !ZIP_ZEROS.equals(inputStr.trim()) && !ZIP_NINES.equals(inputStr.trim())) {
			return inputStr;
		} else {
			return null;
		}
	}

	public static String nysidValidation(String inputStr) {

		if (isNotNullAndEmpty(inputStr)) {
			inputStr = inputStr.replaceAll("[^A-Za-z0-9]+", "");
			Matcher matcher = nysidpattern.matcher(inputStr);
			if (!matcher.matches()) {
				return null;
			} else {
				return inputStr;
			}
		} else {
			return inputStr;
		}
	}

	public static String checkMedicaidNumberFormat(String inputStr) {

		if (isNotNullAndEmpty(inputStr)) {

			inputStr = inputStr.trim().toUpperCase();

			inputStr = inputStr.replaceAll("[^A-Z0-9]+", "");

			Matcher matcher = cinPattern.matcher(inputStr);

			if (!matcher.matches()) {
				return null;
			} else {
				return inputStr;
			}
		} else {
			return inputStr;
		}
	}

	public static String normalizeString(String inputStr) {

		if (CommonUtil.isNotNullAndEmpty(inputStr)) {
			String _formattedString = Normalizer.normalize(inputStr, Normalizer.Form.NFD);
			return spanishRegExPattern.matcher(_formattedString).replaceAll("");
		} else {
			return null;
		}
	}

	public static String normalizeStringForExport01(String inputStr) {

		String _formattedString = Normalizer.normalize(inputStr, Normalizer.Form.NFD);
		return notAsciiCharactersPattern.matcher(unknownCharactersPattern
				.matcher(spanishRegExPattern.matcher(_formattedString).replaceAll("")).replaceAll(" ")).replaceAll(" ");
	}

	/**
	 * Captures the Throwable stacktrace in a String to allow logging, writing to
	 * files, etc...
	 * 
	 * @param t
	 *            Throwable for which the stacktrace is desired.
	 */
	public static String getStackTrace(Throwable t) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		t.printStackTrace(out);
		out.flush();
		return baos.toString();
	}

	/**
	 * Method to join an array of strings into a single string with the original
	 * strings separated by a given separator string. Mimics the perl "join"
	 * function.
	 * 
	 * @param theSeparator
	 *            the String to insert between the various segments to be joined.
	 * @param theStringArrayIn
	 *            The array of elements to be joined.
	 *
	 */
	public static String join(String theSeparator, String[] theStringArrayIn) {
		boolean first = true;
		StringBuilder theStringBuffer = new StringBuilder();
		for (int i = 0; i < theStringArrayIn.length; i++) {
			if (first) {
				first = false;
			} else {
				theStringBuffer.append(theSeparator);
			}
			theStringBuffer.append(theStringArrayIn[i]);
		}
		return theStringBuffer.toString();
	} // end method

	public static String join(String theSeparator, List l) {
		if (l == null)
			return "";
		String[] lArray = (String[]) (l.toArray((new String[] {})));
		return join(theSeparator, lArray);
	}

	public static java.util.Date convertTimeStampToDate(TS timestamp) {

		try {
			return timestamp.getTime().getValueAsDate();
		} catch (DataTypeException dataTypeException) {
			log.error("unable to parse the timestamp for");
		}
		return null;
	}

	// Code Added by MXM69 for CCD and HL7 Name Standarization

	public static boolean containsWhiteSpace(final String testCode) {
		if (testCode != null) {
			for (int i = 0; i < testCode.length(); i++) {
				if (Character.isWhitespace(testCode.charAt(i))) {
					return true;
				}
			}
		}
		return false;
	}


	public static String NameValidationHit(String inputStr) {

		String inputStrHit = "";
		if (isNotNullAndEmpty(inputStr)) {
			inputStrHit = normalizeString(inputStr).replaceAll("[^A-Za-z0-9' ]+", " ");
			inputStrHit = inputStrHit.replace("'", "").trim();
		}
		return inputStrHit;
	}
	
	/*Added by MXM69 for Phone Number Validation*/
	
	public static String verifythephone(String person_phone) {
		person_phone = GetNumberFromString(person_phone);
		if (isNotNullAndEmpty(person_phone)) {
			if (allSameLetter(person_phone)) {
				return null;
			} else if ((person_phone.length() == 11
					&& (person_phone.startsWith("0") || person_phone.startsWith("1")))) {
				return person_phone.substring(1, 11);
			} else {
				return person_phone;
			}
		}return null;
	}

	public static boolean allSameLetter(String str) {
		String firstChar = str.substring(0, 1);
		String formatedString = str.replaceAll(firstChar, "");
		if (isNullOrEmpty(formatedString)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String removeAllCharAfterLastComma(String address) {
		
		if(isNotNullAndEmpty(address) && address.contains(",")) {
			address = address.substring(0, address.lastIndexOf(",")).trim();
		}
		
		return address;
	}
	
	public static boolean isZipValid(String zipcode) {
		
		String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
		Pattern pattern = Pattern.compile(regex);
		 Matcher matcher = pattern.matcher(zipcode);
		    
		 return matcher.matches();
	}
	
	public static String blankOutUnknown(String dataVal) {
		
		if(isNotNullAndEmpty(dataVal) && dataVal.toUpperCase().contains("UNKNOWN")) {
			dataVal = "";
		}
		
		return dataVal;
	}
	
	public static String parseDefaultDateValue(String _dateValue) {
		if(CommonUtil.isNotNullAndEmpty(_dateValue) && "../../....".equals(_dateValue)) {
			return null;
		}else {
			return _dateValue;
		}
	}
	
	public static String convertIntToStr(Integer intValue) {
		if(intValue == null) {
			return "";
		}else {
			return intValue.toString();
		}
	}	
	
	public static String convertFloatToStr(Float floatValue) {
		if(floatValue == null) {
			return "";
		}else {
			return floatValue.toString();
		}
	}
	
	public static String truncatedToByteLength(String resultValue, int byteLimit) {
		
		byte[] bytes = resultValue.getBytes(StandardCharsets.UTF_8);
			
			if(bytes.length <= byteLimit) {
				return resultValue;
			}
			
			int endIndex = byteLimit;
			
			while(endIndex > 0 && (bytes[endIndex] & 0xC0) == 0x80) {
				endIndex--;
			}
			
			
		return new String(bytes,0,endIndex, StandardCharsets.UTF_8).trim();
	}

	/**
	 * Encodes a message so it can be used as a cookie value. Tomcat rejects
	 * spaces and some punctuation in cookie values; getCookie() in
	 * commonUtils.js decodes it again.
	 */
	public static String encodeCookieValue(String value) {
		if (value == null) {
			return "";
		}
		return java.net.URLEncoder.encode(value, StandardCharsets.UTF_8).replace("+", "%20");
	}
}
