package gov.ny.state.doh.bhae.nyehms.utils;

import java.util.Map;
import java.util.HashMap;

/**
 * Utility methods to perform fast translations involving county codes, both
 * Gazetteer and FIPS, and county names, without querying the database.
 * 
 * @author cdf02
 *
 */
public class County {
        
    private final static String[] fipsIndexedByGaz = {
           null, // 0 index
    /*1*/  "001", "003", "007", "009", "011",
           "013", "015", "017", "019", "021",
    /*11*/ "023", "025", "027", "029", "031",
           "033", "035", "037", "039", "041",
    /*21*/ "043", "045",  null, "049", "051",
           "053", "055", "057", "059", null,
    /*31*/ "063", "065", "067", "069", "071",
           "073", "075", "077", "079", null,
    /*41*/ "083",  null, "087", "089", "091",
           "093", "095", "097", "099", "101",
    /*51*/ "103", "105", "107", "109", "111",
           "113", "115", "117", "119", "121",
    /*61*/ "123",  null,  null,  null,  null,
            null,  null,  null,  null,  null,
    /*71*/  null,  null,  null,  null,  null,
            null,  null,  null,  null, "998", //Gaz 80 -> "Out of State" -> FIPS 998 (non-standard)
    /*81*/  null,  null,  null,  null,  null,
            null,  null,  null,  null,  null,
    /*91*/  null,  null, "061", "005", "047",
           "081", "085"
    };
    
    
    private final static String[] countyNamesIndexedByFips = {
        null, "Albany",
        null, "Allegany",
        null, "Bronx",
        null, "Broome",
        null, "Cattaraugus",
        null, "Cayuga",
        null, "Chautauqua",
        null, "Chemung",
        null, "Chenango",
        null, "Clinton",
        null, "Columbia",
        null, "Cortland",
        null, "Delaware",
        null, "Dutchess",
        null, "Erie",
        null, "Essex",
        null, "Franklin",
        null, "Fulton",
        null, "Genesee",
        null, "Greene",
        null, "Hamilton",
        null, "Herkimer",
        null, "Jefferson",
        null, "Kings",
        null, "Lewis",
        null, "Livingston",
        null, "Madison",
        null, "Monroe",
        null, "Montgomery",
        null, "Nassau",
        null, "New York",
        null, "Niagara",
        null, "Oneida",
        null, "Onondaga",
        null, "Ontario",
        null, "Orange",
        null, "Orleans",
        null, "Oswego",
        null, "Otsego",
        null, "Putnam",
        null, "Queens",
        null, "Rensselaer",
        null, "Richmond",
        null, "Rockland",
        null, "St. Lawrence",
        null, "Saratoga",
        null, "Schenectady",
        null, "Schoharie",
        null, "Schuyler",
        null, "Seneca",
        null, "Steuben",
        null, "Suffolk",
        null, "Sullivan",
        null, "Tioga",
        null, "Tompkins",
        null, "Ulster",
        null, "Warren",
        null, "Washington",
        null, "Wayne",
        null, "Westchester",
        null, "Wyoming",
        null, "Yates"
    };
    
    public static final Map<String, String> nycGazToNameMap = new HashMap<String, String>();
    
    static {
        nycGazToNameMap.put("93", "MANHATTAN");
        nycGazToNameMap.put("94", "BRONX");
        nycGazToNameMap.put("95", "BROOKLYN");
        nycGazToNameMap.put("96", "QUEENS");
        nycGazToNameMap.put("97", "STATEN ISLAND");     
    }
    
    public static String getNycBoroughNameFromGaz(String gaz) {
        return nycGazToNameMap.get(gaz);
    }
    
    
    /**
     * Given a String Gazetteer code for a New York State county, returns
     * the FIPS code for that county.
     * @param gazCountyCodeString A string containing "01"-"61" (omitting
     * 23, 30, 40, 42), 80, 93-97; presence or absence of leading zeros
     * do not affect the result.
     * @return For valid input codes, returns the 3-character string FIPS code,
     * except that 80 returns 998, a non-standard code for "Out of State";
     * otherwise returns "999".
     */
    public static String getFipsFromGaz(String gazCountyCodeString) {
        String fipsString = null;
        if (gazCountyCodeString == null) {
                fipsString = "999";
        } else {
                int gazCountyCodeInt = Integer.parseInt(gazCountyCodeString);
                fipsString = getFipsFromGaz(gazCountyCodeInt);
                if (fipsString == null) {fipsString = "999";}
        }
        return fipsString;
    }
    
    /**
     * Given an int Gazetteer code for a New York State county, returns
     * the FIPS code for that county.
     * @param gazCountyCodeString An integer of valuec 1-61 (omitting
     * 23, 30, 40, 42), 80, 93-97.
     * @return For valid input codes, returns the 3-character string FIPS code,
     * except that 80 returns "998", a non-standard code for "Out of State";
     * otherwise returns null.
     */
    public static String getFipsFromGaz(int gazCountyCodeInt) {
        String fipsCode = null;
        if (gazCountyCodeInt < 1 || gazCountyCodeInt > 97) {
                fipsCode = null;
        } else {
                fipsCode = fipsIndexedByGaz[gazCountyCodeInt];
        }
        return fipsCode;
    }
   
    /**
     * Given the FIPS code for an NYC county, returns the name of the County
     * @param countyCode An integer to be interpreted as a FIPS code
     * of an NYS/NYC county.
     * @return If the integer is an odd integer from 1 to 123 then the
     * name of the county; otherwise null.
     */
    public static String getNameFromFips(int countyCode) {
        if(countyCode==805) return "Out Of State";
        if (countyCode < 1 || countyCode > 123 || countyCode%2 == 0) {
            return (String.valueOf(countyCode + ": invalid."));
        }
        return countyNamesIndexedByFips[countyCode];
    }
    
    /**
     * 
     * @param countyCodeString A string to be interpreted as a FIPS code
     * for a NYS/NYC county.
     * @return If the String parses to an odd integer from 1 to 123
     * then the name of the county; otherwise null.
     */
    public static String getNameFromFips(String countyCodeString) {
        int countyCode;
       try {
           countyCode = Integer.parseInt(countyCodeString);
       } catch (NumberFormatException nfe) {
           return countyCodeString;
       }
        return getNameFromFips(countyCode);
    }
    

    
}
