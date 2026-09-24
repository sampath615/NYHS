package gov.ny.state.doh.bhae.nyehms.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HtmlMethods {

    private static int numberOfCharacters = 256;
    private static String[] neutralizeList = new String[numberOfCharacters];

    static {
        try {
            neutralizeList['"'] = "&#34;";
            neutralizeList['%'] = "&#37";
            neutralizeList['&'] = "&#38;";
            neutralizeList['\''] = "&#39;";
            neutralizeList['('] = "&#40;";
            neutralizeList[')'] = "&#41;";
            neutralizeList['+'] = "&#43;";
            neutralizeList[';'] = "&#59;";
            neutralizeList['<'] = "&#60;";
            neutralizeList['>'] = "&#62;";
            
        } catch (Exception e) {
            // Will not happen since the pattern is hard-coded.
        }
    }

	  
	  /**
	   * This method returns the HTML to display a table based on the 
	   * results of a MyBatis select (a List of HashMaps) 
	   * @param results
	   * @return String
	   * @throws Exception
	   */
	  public static String makeHTMLTable (List<Map<String, String>> results)
	    throws Exception { 
		  
		  StringBuffer sb = new StringBuffer();
		  Map firstDataRow = results.get(0);
		  Set<String> keys = firstDataRow.keySet();
		  String[] columnNames = keys.toArray(new String[0]);
			  return makeHTMLTable(results, columnNames);
		}

	  /**
	   * This method returns the HTML to display a table based on the 
	   * results of a MyBatis select (a List of HashMaps) and an
	   * String array of column names
	   * @param results
	   * @param columnNames 
	   * @return String
	   * @throws Exception
	   */
	  public static String makeHTMLTable (
			  List<Map<String, String>> results,
			  String[] columnNames )
	    throws Exception { 
		  
		  StringBuffer sb = new StringBuffer();
		  int numberOfColumns = columnNames.length;
		  sb.append("<table border='2' cellpadding='2' cellspacing='0'>\n");
		  // Heading
		  sb.append("<tr>\n"); // begin Header row
		  for (int i = 0; i < numberOfColumns; i++) {
		    String colName = columnNames[i];
		    sb.append("<th>"+colName );
		    sb.append("</th>\n");
		  }
		  sb.append("</tr>\n"); // end Header row
		  Iterator<Map<String, String>> itResults = results.iterator();
		  while(itResults.hasNext()) { // each row
			Map<String, String> rowData = itResults.next();  
		    sb.append("<tr>\n"); // begin row
		    for (int j=0; j < numberOfColumns; j++) { // each cell
		      Object o = rowData.get(columnNames[j]);
		      sb.append("<td>&nbsp;"+
		              ((o==null)?"":neutralize(o.toString()))+
		              "</td>"); // one cell
		    }
		    sb.append("</tr>\n"); // end row
		  }
		  sb.append("</table>\n"); // end table
		  return sb.toString();
		}

	    /**
	     * Removes hazardous characters from an output string by replacing them
	     * with their HTML equivalent.
	     */
	    public static String neutralize (String output){
	        if((output == null)){
	            return ""; // Necessary to translate null to empty string
	            // because a PrintWriter will translate it to "null".
	        }
	        StringBuffer cleanOutput = null;
	        String arrayVal = "";
	        cleanOutput = new StringBuffer();
	        for (int i=0; i<output.length(); i++){
	        	char c = output.charAt(i);
	        	if (c > 256){
	        	   arrayVal = new String(" ");	
	        	}else {
	               arrayVal = neutralizeList[output.charAt(i)];
	        	}   
	            if (arrayVal == null){
	                cleanOutput.append(output.charAt(i));
	            } else {
	                cleanOutput.append(arrayVal);
	            }
	        }
	        String cleanString = cleanOutput.toString();
	        //Replace any "!br!" that was inserted by the system when
	        //the NTE Segments were concatenated with <br>. This will allow
	        //the NTEs to be formatted with carriage returns
	        cleanString = cleanString.replaceAll("!br!", "<br>");
	        return (cleanString);
	    }

}
