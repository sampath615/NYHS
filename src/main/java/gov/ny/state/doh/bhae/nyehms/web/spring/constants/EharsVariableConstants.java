package gov.ny.state.doh.bhae.nyehms.web.spring.constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Created the file for common Constant variables to access across the NYEHMS application
 * 
 * @author ska03
 *
 */
public class EharsVariableConstants {
	
	
	public final static Map<String, Integer> EHARS_VARIABLES ;
	
	public final static Map<String, String> EHARS_VAR_NAME_MAP = new HashMap<>();
	
	static {
		 Map<String, Integer> eharsFieldMap = new HashMap<>();
		 	eharsFieldMap.put("first_name", 2);
			eharsFieldMap.put("middle_name", 3);
			eharsFieldMap.put("last_name", 1);
			eharsFieldMap.put("hcw_risk", 1);
			
			eharsFieldMap.put("hf_facility_name1", 1);
			eharsFieldMap.put("hf_facility_addr1", 3);
			eharsFieldMap.put("hf_facility_addr2", 4);
			eharsFieldMap.put("hf_facility_city", 5);
			eharsFieldMap.put("hf_facility_cnty", 6);
			eharsFieldMap.put("hf_facility_state", 7);
			eharsFieldMap.put("hf_facility_zip", 8);
			eharsFieldMap.put("af_facility_name1", 1);
			eharsFieldMap.put("af_facility_addr1", 3);
			eharsFieldMap.put("af_facility_addr2", 4);
			eharsFieldMap.put("af_facility_city", 5);
			eharsFieldMap.put("af_facility_cnty", 6);
			eharsFieldMap.put("af_facility_state", 7);
			eharsFieldMap.put("af_facility_zip", 8);
			
			
			eharsFieldMap.put("cur_address1", 4);
			eharsFieldMap.put("cur_address2", 5);
			eharsFieldMap.put("cur_city", 6);
			eharsFieldMap.put("cur_cnty_name", 7);
			eharsFieldMap.put("cur_state", 8);
			eharsFieldMap.put("cur_zip", 9);
			eharsFieldMap.put("cur_dt", 11);
			
			
			eharsFieldMap.put("rsh_address1", 3);
			eharsFieldMap.put("rsh_address2", 4);
			eharsFieldMap.put("rsh_city", 5);
			eharsFieldMap.put("rsh_cnty_name", 6);
			eharsFieldMap.put("rsh_state", 7);
			eharsFieldMap.put("rsh_zip", 8);
			
			eharsFieldMap.put("rsa_address1", 3);
			eharsFieldMap.put("rsa_address2", 4);
			eharsFieldMap.put("rsa_city", 5);
			eharsFieldMap.put("rsa_cnty_name", 6);
			eharsFieldMap.put("rsa_state", 7);
			eharsFieldMap.put("rsa_zip", 8);
		EHARS_VARIABLES = Collections.unmodifiableMap(eharsFieldMap);
    }
	
	static {
		EHARS_VAR_NAME_MAP.put("stateno", "STATENO");
		EHARS_VAR_NAME_MAP.put("first_name", "First Name");
		EHARS_VAR_NAME_MAP.put("middle_name", "Middle Name");
		EHARS_VAR_NAME_MAP.put("last_name", "Last Name");
		EHARS_VAR_NAME_MAP.put("dob", "Date of Birth");
		EHARS_VAR_NAME_MAP.put("ssn", "SSN");
		EHARS_VAR_NAME_MAP.put("birth_sex", "Sex");
		EHARS_VAR_NAME_MAP.put("current_gender", "Current Gender");
		EHARS_VAR_NAME_MAP.put("vital_status", "Vital Status");
		EHARS_VAR_NAME_MAP.put("dod", "Date of Death");
		EHARS_VAR_NAME_MAP.put("swm", "Sex with Male");
		EHARS_VAR_NAME_MAP.put("swf", "Sex with Female");
		EHARS_VAR_NAME_MAP.put("idu", "Injected Non-Prescription Drug");
		EHARS_VAR_NAME_MAP.put("clotting_factor", "Received Clotting Factor");
		EHARS_VAR_NAME_MAP.put("sex_idu", "Het Contact with IDU");
		EHARS_VAR_NAME_MAP.put("sex_bisexual_male", "Het Sex w Bisexual Male");
		EHARS_VAR_NAME_MAP.put("sex_hemo", "Het Sex w HIV Hemophiliac");
		EHARS_VAR_NAME_MAP.put("sex_transfusion", "Het Sex w HIV Transfusion Recip");
		EHARS_VAR_NAME_MAP.put("sex_transplant", "Het Sex w HIV Transplant Recip");
		EHARS_VAR_NAME_MAP.put("sex_hiv", "Het Sex w HIV Person");
		EHARS_VAR_NAME_MAP.put("transfusion", "Received Transfusion");
		EHARS_VAR_NAME_MAP.put("transplant", "Received Transplant or AI");
		EHARS_VAR_NAME_MAP.put("hcw_risk", "Health Care Worker Risk");
		EHARS_VAR_NAME_MAP.put("oth_risk", "Other Risks");
		EHARS_VAR_NAME_MAP.put("nir", "No Identified Risk Factor");
		EHARS_VAR_NAME_MAP.put("hf_facility_name1", "HIV DX - Facility Name");
		EHARS_VAR_NAME_MAP.put("hf_facility_addr1", "HIV DX - Facility Address");
		//eharsFieldVariableNameMap.put("hf_facility_addr2", "HIV Facility Address 2");
		EHARS_VAR_NAME_MAP.put("hf_facility_city", "HIV DX - Facility City");
		EHARS_VAR_NAME_MAP.put("hf_facility_state", "HIV DX - Facility State");
		EHARS_VAR_NAME_MAP.put("hf_facility_cnty", "HIV DX - Facility County");
		EHARS_VAR_NAME_MAP.put("hf_facility_zip", "HIV DX - Facility Zip Code");
		EHARS_VAR_NAME_MAP.put("af_facility_name1", "AIDS DX - Facility Name");
		EHARS_VAR_NAME_MAP.put("af_facility_addr1", "AIDS DX - Facility Address");
		//eharsFieldVariableNameMap.put("af_facility_addr2", "AIDS Facility Addrress2");
		EHARS_VAR_NAME_MAP.put("af_facility_city", "AIDS DX - Facility City");
		EHARS_VAR_NAME_MAP.put("af_facility_state", "AIDS DX - Facility State");
		EHARS_VAR_NAME_MAP.put("af_facility_cnty", "AIDS DX - Facility County");
		EHARS_VAR_NAME_MAP.put("af_facility_zip", "AIDS DX - Facility Zip Code");
		EHARS_VAR_NAME_MAP.put("hiv_cdc","Meets CDC HIV definition?");
		EHARS_VAR_NAME_MAP.put("hiv_cdc_elig","Meets CDC HIV eligibility?");
		EHARS_VAR_NAME_MAP.put("hiv_dx_dt", "HIV DX Date");
		EHARS_VAR_NAME_MAP.put("aids_cdc","Meets CDC AIDS definition?");
		EHARS_VAR_NAME_MAP.put("aids_cdc_elig","Meets CDC AIDS eligibility?");
		EHARS_VAR_NAME_MAP.put("aids_dx_dt", "AIDS DX Date");
		EHARS_VAR_NAME_MAP.put("hiv_aids_dx_dt", "HIV or AIDS Dx Date - Earlier");
		EHARS_VAR_NAME_MAP.put("cur_address1", "Current Residence Address");
		//eharsFieldVariableNameMap.put("cur_address2", "Current Address2");
		EHARS_VAR_NAME_MAP.put("cur_city", "Current Residence City");
		EHARS_VAR_NAME_MAP.put("cur_cnty_name", "Current Residence County");
		EHARS_VAR_NAME_MAP.put("cur_state", "Current Residence State");
		EHARS_VAR_NAME_MAP.put("cur_zip", "Current Residence Zip");
		EHARS_VAR_NAME_MAP.put("cur_dt", "Current Residence Date");
		EHARS_VAR_NAME_MAP.put("rsh_address1", "Residence at HIV DX Address");
		//eharsFieldVariableNameMap.put("rsh_address2", "RSH Address2");
		EHARS_VAR_NAME_MAP.put("rsh_city", "Residence at HIV DX City");
		EHARS_VAR_NAME_MAP.put("rsh_state", "Residence at HIV DX State");
		EHARS_VAR_NAME_MAP.put("rsh_zip", "Residence at HIV DX Zip");
		EHARS_VAR_NAME_MAP.put("rsh_cnty_name", "Residence at HIV DX County");
		EHARS_VAR_NAME_MAP.put("rsa_address1", "Residence at AIDS DX Address");
		//eharsFieldVariableNameMap.put("rsa_address2", "RSA Address2");
		EHARS_VAR_NAME_MAP.put("rsa_city", "Residence at AIDS DX City");
		EHARS_VAR_NAME_MAP.put("rsa_state", "Residence at AIDS DX State");
		EHARS_VAR_NAME_MAP.put("rsa_cnty_name", "Residence at AIDS DX County");
		EHARS_VAR_NAME_MAP.put("rsa_zip", "Residence at AIDS DX Zip");
	    //adding new fields
        EHARS_VAR_NAME_MAP.put("birth_country","Country of Birth");
        EHARS_VAR_NAME_MAP.put("death_state","Jurisdiction of Death");
        EHARS_VAR_NAME_MAP.put("race1","Race 1");
        EHARS_VAR_NAME_MAP.put("race2","Race 2");
        EHARS_VAR_NAME_MAP.put("race3","Race 3");
        EHARS_VAR_NAME_MAP.put("race4","Race 4");
        EHARS_VAR_NAME_MAP.put("race5","Race 5");
        EHARS_VAR_NAME_MAP.put("ethnicity","Ethnicity");
        EHARS_VAR_NAME_MAP.put("calculated_race","Calculated Race");
        EHARS_VAR_NAME_MAP.put("child_swm", "Child Sex w Male");
        EHARS_VAR_NAME_MAP.put("child_swf", "Child Sex w female");
        EHARS_VAR_NAME_MAP.put("mother_infection_status", "Mother HIV Infection Status");
        EHARS_VAR_NAME_MAP.put("mother_perinatal_hiv", "Mother Perinatally HIV infection");
        EHARS_VAR_NAME_MAP.put("mother_idu", "Mother Injected Non-Prescription Drug");
        EHARS_VAR_NAME_MAP.put("mother_sex_idu", "Mother Het Contact with IDU");
        EHARS_VAR_NAME_MAP.put("mother_sex_bisexual_male", "Mother Het Contact with bisexual male");
        EHARS_VAR_NAME_MAP.put("mother_sex_hemo", "Mother Het Sex w HIV Hemophiliac");
        EHARS_VAR_NAME_MAP.put("mother_sex_transfusion", "Mother Het Sex w HIV Transfusion Recip");
        EHARS_VAR_NAME_MAP.put("mother_sex_transplant", "Mother Het Sex w HIV Transplant Recip");
        EHARS_VAR_NAME_MAP.put("mother_swm","Mother Sex w Male");
        EHARS_VAR_NAME_MAP.put("mother_transfusion","Mother Received Transfusion");
        EHARS_VAR_NAME_MAP.put("mother_transplant","Mother Received Transplant");
        EHARS_VAR_NAME_MAP.put("child_idu", "Child Injected Non-Prescription Drug");
        EHARS_VAR_NAME_MAP.put("child_clotting_factor", "Child Received Clotting Factor");
        EHARS_VAR_NAME_MAP.put("child_transfusion", "Child Received Transfusion");
        EHARS_VAR_NAME_MAP.put("child_transplant", "Child Received Transplant");
        EHARS_VAR_NAME_MAP.put("child_oth_risk", "Child Other Risks");
        EHARS_VAR_NAME_MAP.put("child_nir", "Child No Identified Risk Factor");
        EHARS_VAR_NAME_MAP.put("physician_diagnosis_dt", "Physician Diagnosis Date");
		//added on 05/19
        EHARS_VAR_NAME_MAP.put("hiv_dx_type","HIV DX Type");
        EHARS_VAR_NAME_MAP.put("cd4_vl_first_hiv_dt", "CD4 Viral Load First HIV Date");
		EHARS_VAR_NAME_MAP.put("cd4_vl_first_hiv_type", "CD4 Viral Load First HIV Type"); //adding
        EHARS_VAR_NAME_MAP.put("first_det_vl_dt", "First Detect Viral Load Date"); //adding
        EHARS_VAR_NAME_MAP.put("first_det_vl_result", "First Detect Viral Load Result"); //adding
        EHARS_VAR_NAME_MAP.put("cd4_first_hiv_dt", "CD4 First HIV Date"); //adding
        EHARS_VAR_NAME_MAP.put("cd4_first_hiv_value", "CD4 First HIV Value"); //adding
        EHARS_VAR_NAME_MAP.put("most_recent_test_dt", "Most Recent Test Date");
        EHARS_VAR_NAME_MAP.put("most_recent_test_name", "Most Recent Test Name");
        EHARS_VAR_NAME_MAP.put("most_recent_test_result", "Most Recent Test Result");
        EHARS_VAR_NAME_MAP.put("most_recent_vl_result", "Most Recent Viral Load Result");
        EHARS_VAR_NAME_MAP.put("most_recent_vl_dt", "Most Recent Viral Load Date");
        EHARS_VAR_NAME_MAP.put("most_recent_nondet_vl_dt", "Most Recent Non Detected Viral load Date");
        EHARS_VAR_NAME_MAP.put("most_recent_cd4_cnt", "Most Recent CD4 Count");
        EHARS_VAR_NAME_MAP.put("most_recent_cd4_cnt_dt", "Most Recent CD4 Count Date");
        EHARS_VAR_NAME_MAP.put("most_recent_cd4_pct", "Most Recent CD4 Percentage");
        EHARS_VAR_NAME_MAP.put("most_recent_cd4_pct_dt", "Most Recent CD4 Percentage Date");
        EHARS_VAR_NAME_MAP.put("aids_dx_cnt_dt", "AIDS Dianosis Count Date");
        EHARS_VAR_NAME_MAP.put("aids_dx_cnt_result", "AIDS Diagnosis Count Result");
        EHARS_VAR_NAME_MAP.put("aids_dx_pct_dt", "AIDS Diagnosis Percentage Date");
        EHARS_VAR_NAME_MAP.put("aids_dx_pct_result", "AIDS Diagnosis Percentage Result");
       
        EHARS_VAR_NAME_MAP.put("bacterial_infection", "Bacterial infection, multiple or recurrent");
        EHARS_VAR_NAME_MAP.put("cand_lung", "Candidiasis, bronchi, trachea, or lungs");
        EHARS_VAR_NAME_MAP.put("cand_esoph", "Candidiasis, esophageal");
        EHARS_VAR_NAME_MAP.put("cervical_cancer", "Carcinoma, invasive cervical");
        EHARS_VAR_NAME_MAP.put("cocci", "Coccidioidomycosis, disseminated or extrapulmonary");
        EHARS_VAR_NAME_MAP.put("cryptoco", "Cryptococcosis, extrapulmonary");
        EHARS_VAR_NAME_MAP.put("cryptosp", "Cryptosporidiosis, chronic intestinal (>1 mo. duration)");
        EHARS_VAR_NAME_MAP.put("cmv", "Cytomegalovirus disease (other than in liver, spleen, or nodes)");
        EHARS_VAR_NAME_MAP.put("cmv_retinitis", "Cytomegalovirus retinitis (with loss of vision)");
        EHARS_VAR_NAME_MAP.put("hiv_enceph", "HIV encephalopathy");
        EHARS_VAR_NAME_MAP.put("herpes_simplex", "Herpes simplex: chronic ulcer(s) (>1 mo. duration) or bronchitis, pneumonitis, or esophagitis");
        EHARS_VAR_NAME_MAP.put("histo", "Histoplasmosis, disseminated or extrapulmonary");
        EHARS_VAR_NAME_MAP.put("iso", "Isosporiasis, chronic intestinal (>1 mo. duration)");
        EHARS_VAR_NAME_MAP.put("ks", "Kaposi's sarcoma");
        EHARS_VAR_NAME_MAP.put("lip", "Lymphoid interstitial pneumonia and/or pulmonary lymphoid");
        EHARS_VAR_NAME_MAP.put("lymph_burkitts", "Lymphoma, Burkitt's (or equivalent term)");
        EHARS_VAR_NAME_MAP.put("lymph_immuno", "Lymphoma, immunoblastic (or equivalent term)");
        EHARS_VAR_NAME_MAP.put("lymph_brain", "Lymphoma, primary in brain");
        EHARS_VAR_NAME_MAP.put("myco_avium", "Mycobacterium avium complex or M. kansasii, disseminated or extrapulmonary");
        EHARS_VAR_NAME_MAP.put("myco_tb_pulm", "M. tuberculosis, pulmonary");
        EHARS_VAR_NAME_MAP.put("myco_tb_dissem", "M. tuberculosis, disseminated or extrapulmonary");
        EHARS_VAR_NAME_MAP.put("myco_other", "Mycobacterium, of other species or unidentified species, disseminated or extrapulmonary");
        EHARS_VAR_NAME_MAP.put("pcp", "Pneumocystis carinii pneumonia");
        EHARS_VAR_NAME_MAP.put("pneumonia_recurrent", "Pneumonia, recurrent, in 12 mo. period");
        EHARS_VAR_NAME_MAP.put("pml", "Progressive multifocal leukoencephalopathy");
        EHARS_VAR_NAME_MAP.put("salmonella", "Salmonella septicemia, recurrent");
        EHARS_VAR_NAME_MAP.put("toxo_brain", "Toxoplasmosis of brain, onset at >1 mo. of age");
        EHARS_VAR_NAME_MAP.put("wasting", "Wasting syndrome due to HIV");
		EHARS_VAR_NAME_MAP.put("ridr_values", "RIDR Values");//adding new variable (05/19/21)
		//new variables 06/01/2021
		EHARS_VAR_NAME_MAP.put("hiv_dx_wb1", "HIV-1 Western Blot Test Name");
		EHARS_VAR_NAME_MAP.put("hiv_dx_wb1_dt", "HIV-1 Western Blot Test Date");
		EHARS_VAR_NAME_MAP.put("hiv_dx_wb1_result", "HIV-1 Western Blot Test Result");
		EHARS_VAR_NAME_MAP.put("hiv_dx_wb2", "HIV-2 Western Blot Test Name");
		EHARS_VAR_NAME_MAP.put("hiv_dx_wb2_dt", "HIV-2 Western Blot Test Date");
		EHARS_VAR_NAME_MAP.put("hiv_dx_wb2_result", "HIV-2 Western Blot Test Result");
		EHARS_VAR_NAME_MAP.put("hiv_dx_ifa", "HIV-1 IFA Test Name");
		EHARS_VAR_NAME_MAP.put("hiv_dx_ifa_dt", "HIV-1 IFA Test Date");
		EHARS_VAR_NAME_MAP.put("hiv_dx_ifa_result","HIV-1 IFA Test Result");
		EHARS_VAR_NAME_MAP.put("hiv_dx_screen", "Screening Test Name");
		EHARS_VAR_NAME_MAP.put("hiv_dx_screen_dt", "Screening Test Date");
		EHARS_VAR_NAME_MAP.put("hiv_dx_screen_result", "Screening Test Result");
		EHARS_VAR_NAME_MAP.put("hiv_dx_diff", "Differentiating Test Name");
		EHARS_VAR_NAME_MAP.put("hiv_dx_diff_dt", "Differentiating Test Date");
		EHARS_VAR_NAME_MAP.put("hiv_dx_diff_result", "Differentiating Test Result");
		EHARS_VAR_NAME_MAP.put("hiv_dx_qual1", "HIV-1 Qualitative Viral Load Test Name");
		EHARS_VAR_NAME_MAP.put("hiv_dx_qual1_dt", "HIV-1 Qualitative Viral Load Test Date");
		EHARS_VAR_NAME_MAP.put("hiv_dx_qualt1_result", "HIV-1 Qualitative Viral Load Test Result");
		EHARS_VAR_NAME_MAP.put("hiv_dx_qual2", "HIV-2 Qualitative Viral Load Test Name");
		EHARS_VAR_NAME_MAP.put("hiv_dx_qual2_dt", "HIV-2 Qualitative Viral Load Test Date");
		EHARS_VAR_NAME_MAP.put("hiv_dx_qual2_result", "HIV-2 Qualitative Viral Load Test Result");
		EHARS_VAR_NAME_MAP.put("hiv_dx_quan1", "HIV-1 Quantitative Viral Load Test Name");
		EHARS_VAR_NAME_MAP.put("hiv_dx_quan1_dt", "HIV-1 Quantitative Viral Load Test Date");
		EHARS_VAR_NAME_MAP.put("hiv_dx_quan1_result", "HIV-1 Quantitative Viral Load Test Result");
		EHARS_VAR_NAME_MAP.put("hiv_dx_quan1_units", "HIV-1 Quantitative Viral Load Units");
		EHARS_VAR_NAME_MAP.put("hiv_dx_quan1_interp", "HIV-1 Quantitative Viral Load Interpretation");
		EHARS_VAR_NAME_MAP.put("hiv_dx_quan2", "HIV-2 Quantitative Viral Load Test Name");
		EHARS_VAR_NAME_MAP.put("hiv_dx_quan2_dt", "HIV-2 Quantitative Viral Load Test Date");
		EHARS_VAR_NAME_MAP.put("hiv_dx_quan2_result", "HIV-2 Quantitative Viral Load Test Result");
		EHARS_VAR_NAME_MAP.put("hiv_dx_quan2_units", "HIV-2 Quantitative Viral Load Units");
		EHARS_VAR_NAME_MAP.put("hiv_dx_quan2_interp", "HIV-2 Quantitative Viral Load Interpretation");
		EHARS_VAR_NAME_MAP.put("hiv_dx_geno", "Genotype Test Name");
		EHARS_VAR_NAME_MAP.put("hiv_dx_geno_dt", "Genotype Test Date");
		EHARS_VAR_NAME_MAP.put("hiv_dx_geno_result", "Genotype Test Result");
	}
	
		
}
