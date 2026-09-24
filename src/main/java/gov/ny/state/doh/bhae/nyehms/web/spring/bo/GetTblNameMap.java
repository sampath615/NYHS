
package gov.ny.state.doh.bhae.nyehms.web.spring.bo;


import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;

import org.springframework.stereotype.Component;

@Component
public class GetTblNameMap {
	
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	
	/*
	public Map getTableMap(String stateNo, String fieldName){
		
		Map map = new HashMap();
				
		String tblField="";
		String tblFieldId="";
		String tbl = "";
		String bestTbl = "";
						
		if(fieldName.equalsIgnoreCase("STATENO")){
			tblField=DbTbl.stateNoTblField.getName();
			tbl=DbTbl.stateNoTbl.getName();			
		}else if(fieldName.equalsIgnoreCase("FIRST_NAME")){			
			tblField=DbTbl.firstNameTblField.getName();
			tblFieldId=DbTbl.firstNameTblFieldId.getName();
			tbl = DbTbl.firstNameTbl.getName();
			bestTbl=DbTbl.firstNameBestTbl.getName();		
		}else if(fieldName.equalsIgnoreCase("LAST_NAME"))	{		
			tblField=DbTbl.lastNameTblField.getName();
			tblFieldId=DbTbl.lastNameTblFieldId.getName();
			tbl = DbTbl.lastNameTbl.getName();
			bestTbl=DbTbl.lastNameBestTbl.getName();			
		}else if(fieldName.equalsIgnoreCase("MIDDLE_NAME"))	{		
			tblField=DbTbl.middleNameTblField.getName();
			tblFieldId=DbTbl.middleNameTblFieldId.getName();
			tbl = DbTbl.middleNameTbl.getName();
			bestTbl=DbTbl.middleNameBestTbl.getName();			
		}else if(fieldName.equalsIgnoreCase("SSN"))	{		
			tblField=DbTbl.ssnTblField.getName();
			tblFieldId=DbTbl.ssnTblFieldId.getName();
			tbl = DbTbl.ssnTbl.getName();
			bestTbl=DbTbl.ssnBestTbl.getName();			
		}else if(fieldName.equalsIgnoreCase("DIN"))	{		
			tblField=DbTbl.dinTblField.getName();
			tblFieldId=DbTbl.dinTblFieldId.getName();
			tbl = DbTbl.dinTbl.getName();
			bestTbl=DbTbl.dinBestTbl.getName();			
		}else if(fieldName.equalsIgnoreCase("SUFFIX"))	{		
			tblField=DbTbl.suffixTblField.getName();
			tblFieldId=DbTbl.suffixTblFieldId.getName();
			tbl = DbTbl.suffixTbl.getName();
			bestTbl=DbTbl.suffixBestTbl.getName();			
		}else if(fieldName.equalsIgnoreCase("GENDER"))	{		
			tblField=DbTbl.genderTblField.getName();
			tblFieldId=DbTbl.genderTblFieldId.getName();
			tbl = DbTbl.genderTbl.getName();
			bestTbl=DbTbl.genderBestTbl.getName();			
		}else if(fieldName.equalsIgnoreCase("BIRTH_SEX"))	{		
			tblField=DbTbl.birthSexTblField.getName();
			tblFieldId=DbTbl.birthSexTblFieldId.getName();
			tbl = DbTbl.birthSexTbl.getName();
			bestTbl=DbTbl.birthSexBestTbl.getName();			
		}else if(fieldName.equalsIgnoreCase("STATE_OF_DEATH"))	{		
			tblField=DbTbl.stateOfDeathTblField.getName();
			tblFieldId=DbTbl.stateOfDeathTblFieldId.getName();
			tbl = DbTbl.stateOfDeathTbl.getName();
			bestTbl=DbTbl.stateOfDeathBestTbl.getName();			
		}else if(fieldName.equalsIgnoreCase("DATE_OF_DEATH"))	{		
			tblField=DbTbl.dateOfDeathTblField.getName();
			tblFieldId=DbTbl.dateOfDeathTblFieldId.getName();
			tbl = DbTbl.dateOfDeathTbl.getName();
			bestTbl=DbTbl.dateOfBirthBestTbl.getName();			
		}else if(fieldName.equalsIgnoreCase("NYSID"))	{		
			tblField=DbTbl.nysidTblField.getName();
			tblFieldId=DbTbl.nysidTblFieldId.getName();
			tbl = DbTbl.nysidTbl.getName();
			bestTbl=DbTbl.nysidBestTbl.getName();			
		}else if(fieldName.equalsIgnoreCase("MED_RECORD_NUMER"))	{		
			tblField=DbTbl.medRecordNumberTblField.getName();
			tblFieldId=DbTbl.medRecordNumberTblFieldId.getName();
			tbl = DbTbl.medRecordNumberTbl.getName();
			bestTbl=DbTbl.medRecordNumberBestTbl.getName();			
		}else if(fieldName.equalsIgnoreCase("DATE_OF_BIRTH"))	{		
			tblField=DbTbl.dateOfBirthTblField.getName();
			tblFieldId=DbTbl.dateOfBirthTblFieldId.getName();
			tbl = DbTbl.dateOfBirthTbl.getName();
			bestTbl=DbTbl.dateOfBirthBestTbl.getName();			
		}else if(fieldName.equalsIgnoreCase("BIRTH_COUNTRY"))	{		
			tblField=DbTbl.birthCountryTblField.getName();
			tblFieldId=DbTbl.birthCountryTblFieldId.getName();
			tbl = DbTbl.birthCountryTbl.getName();
			bestTbl=DbTbl.birthCountryBestTbl.getName();			
		}else if(fieldName.equalsIgnoreCase("ETHNICITY"))	{		
			tblField=DbTbl.ethnicityTblField.getName();
			tblFieldId=DbTbl.ethnicityTblFieldId.getName();
			tbl = DbTbl.ethnicityTbl.getName();
			bestTbl=DbTbl.ethnicityBestTbl.getName();			
		}else if(fieldName.equalsIgnoreCase("RACE"))	{		
			tblField=DbTbl.raceTblField.getName();
			tblFieldId=DbTbl.raceTblFieldId.getName();
			tbl = DbTbl.raceTbl.getName();
			bestTbl=DbTbl.raceBestTbl.getName();			
		}
		
		log.debug(nyehmsLog+ " stateNo:"+ stateNo+" tblField:"+tblField+" tblFieldId :"+tblFieldId+" tbl :"+tbl+" bestTbl :"+bestTbl);
		
		map.put("stateNo", stateNo);
		map.put("tblField", tblField);
		map.put("tblFieldId", tblFieldId);
		map.put("tbl", tbl);
		map.put("bestTbl", bestTbl);	
				
		return map;
	}*/
}
