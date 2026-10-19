package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawIccrDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawIccrTest;

/**
 * The ICCR data entry form as the browser posts it (iccrDeDoc.js: {@code $("#ICCRDataEntry").serialize()}, test
 * rows cloned by {@code addTestRow}) bound to the {@link RawIccrDoc} entity with {@link IccrDataEntryBinding},
 * as in {@code ICCRDocumentController.saveICCRDataForm}.
 */
class IccrDataEntryBindingTest {

	@Controller
	static class FormController {
		final AtomicReference<RawIccrDoc> posted = new AtomicReference<>();

		@InitBinder("ICCRDataEntryVO")
		public void initDataEntryBinder(WebDataBinder binder) {
			IccrDataEntryBinding.configure(binder);
		}

		@PostMapping("/saveICCRDataForm")
		@ResponseBody
		String save(@ModelAttribute("ICCRDataEntryVO") RawIccrDoc form, BindingResult result) {
			posted.set(form);
			return result.hasErrors() ? "errors" : "ok";
		}
	}

	private final FormController controller = new FormController();
	private final MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

	@Test
	void bindsThePostedFormWithItsTestRows() throws Exception {
		mvc.perform(post("/saveICCRDataForm").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("firstName", "Jane").param("lastName", "Doe").param("middleName", "")
				.param("dob", "03/04/1980").param("personPhone", "518-555-1234").param("personAddress", "1 Main St")
				.param("personCity", "Albany").param("personState", "NY").param("personZip", "12207").param("personCounty", "01")
				.param("provName", "Clinic").param("provPhone", "").param("jurisdictionProvidingInfo", "NY")
				.param("provAddress", "").param("provCity", "").param("provState", "").param("provZip", "").param("provCounty", "")
				.param("iccrTestList[0].dateOfTest", "01/02/2023").param("iccrTestList[0].testType", "D1")
				.param("iccrTestList[0].testResults", "DET").param("iccrTestList[0].testResultCopies", "200")
				.param("iccrTestList[0].testResultLogs", "2.3")
				.param("iccrTestList[1].dateOfTest", "../../....").param("iccrTestList[1].testType", "E1")
				.param("iccrTestList[1].testResults", "").param("iccrTestList[1].testResultCopies", "")
				.param("iccrTestList[1].testResultLogs", "")
				.param("resultsReceived", "Y").param("comments", "seen"))
				.andExpect(status().isOk())
				.andExpect(content().string("ok"));

		RawIccrDoc form = controller.posted.get();
		assertThat(form.getFirstName()).isEqualTo("Jane");
		assertThat(form.getMiddleName()).isEmpty();
		assertThat(form.getDob()).isEqualTo(Timestamp.valueOf("1980-03-04 00:00:00"));   // FN_TO_DATE(..., 'MM/DD/YYYY')
		assertThat(form.getPersonCounty()).isEqualTo("01");
		assertThat(form.getJurisdictionProvidingInfo()).isEqualTo("NY");
		assertThat(form.getResultsReceived()).isEqualTo("Y");
		assertThat(form.getComments()).isEqualTo("seen");
		assertThat(form.getIccrTestList()).hasSize(2);
		RawIccrTest first = form.getIccrTestList().get(0);
		assertThat(first.getDateOfTest()).isEqualTo(Timestamp.valueOf("2023-01-02 00:00:00"));
		assertThat(first.getTestType()).isEqualTo("D1");
		assertThat(first.getTestResults()).isEqualTo("DET");
		assertThat(first.getTestResultCopies()).isEqualTo("200");
		assertThat(first.getTestResultLogs()).isEqualTo("2.3");
		RawIccrTest second = form.getIccrTestList().get(1);
		assertThat(second.getDateOfTest()).isNull();                                       // the empty date mask
		assertThat(second.getTestType()).isEqualTo("E1");
	}

	@Test
	void anInvalidDateIsNullNotABindingError() throws Exception {
		mvc.perform(post("/saveICCRDataForm").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("dob", "02/30/2023").param("iccrTestList[0].dateOfTest", "bad"))
				.andExpect(content().string("ok"));

		assertThat(controller.posted.get().getDob()).isNull();
		assertThat(controller.posted.get().getIccrTestList().get(0).getDateOfTest()).isNull();
	}

	@Test
	void takesOnlyTheFieldsOfTheForm() throws Exception {
		mvc.perform(post("/saveICCRDataForm").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("firstName", "Jane").param("documentId", "123").param("createUid", "1").param("currentProcessStatus", "5")
				.param("createDate", "01/01/2020"))
				.andExpect(content().string("ok"));

		RawIccrDoc form = controller.posted.get();
		assertThat(form.getFirstName()).isEqualTo("Jane");
		assertThat(form.getDocumentId()).isNull();
		assertThat(form.getCreateUid()).isNull();
		assertThat(form.getCurrentProcessStatus()).isNull();
		assertThat(form.getCreateDate()).isNull();
		assertThat(form.getIccrTestList()).isNull();                                      // no test rows posted
	}
}
