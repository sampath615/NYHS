package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawIccrDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawIccrTest;

/**
 * The ICCR data entry form as the browser posts it (iccrDeDoc.js: {@code $("#ICCRDataEntry").serialize()}, test
 * rows cloned by {@code addTestRow}) bound to the {@link RawIccrDoc} entity, as in
 * {@code ICCRDocumentController.saveICCRDataForm}, and the row saved from it.
 */
class IccrDataEntryFormTest {

	@Controller
	static class FormController {
		final AtomicReference<RawIccrDoc> posted = new AtomicReference<>();

		@PostMapping("/saveICCRDataForm")
		@ResponseBody
		String save(@ModelAttribute("ICCRDataEntryVO") RawIccrDoc form, BindingResult result) {
			posted.set(form);
			return "ok";
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
				.param("iccrTestList[0].dateOfTest", "1/2/2023").param("iccrTestList[0].testType", "D1")
				.param("iccrTestList[0].testResults", "DET").param("iccrTestList[0].testResultCopies", "200")
				.param("iccrTestList[0].testResultLogs", "2.3")
				.param("iccrTestList[1].dateOfTest", "").param("iccrTestList[1].testType", "E1")
				.param("iccrTestList[1].testResults", "").param("iccrTestList[1].testResultCopies", "")
				.param("iccrTestList[1].testResultLogs", "")
				.param("resultsReceived", "Y").param("comments", "seen"))
				.andExpect(status().isOk());

		RawIccrDoc form = controller.posted.get();
		assertThat(form.getFirstName()).isEqualTo("Jane");
		assertThat(form.getMiddleName()).isEmpty();
		assertThat(form.getDob()).isEqualTo(LocalDate.of(1980, 3, 4));
		assertThat(form.getPersonCounty()).isEqualTo("01");
		assertThat(form.getJurisdictionProvidingInfo()).isEqualTo("NY");
		assertThat(form.getResultsReceived()).isEqualTo("Y");
		assertThat(form.getComments()).isEqualTo("seen");
		assertThat(form.getIccrTestList()).hasSize(2);
		RawIccrTest first = form.getIccrTestList().get(0);
		assertThat(first.getDateOfTest()).isEqualTo(LocalDate.of(2023, 1, 2));   // one-digit month and day, as FN_TO_DATE
		assertThat(first.getTestType()).isEqualTo("D1");
		assertThat(first.getTestResults()).isEqualTo("DET");
		assertThat(first.getTestResultCopies()).isEqualTo("200");
		assertThat(first.getTestResultLogs()).isEqualTo("2.3");
		RawIccrTest second = form.getIccrTestList().get(1);
		assertThat(second.getDateOfTest()).isNull();
		assertThat(second.getTestType()).isEqualTo("E1");
	}

	@Test
	void aDateThatIsNotValidIsLeftEmpty() throws Exception {
		mvc.perform(post("/saveICCRDataForm").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("firstName", "Jane").param("dob", "02/30/2023")
				.param("iccrTestList[0].dateOfTest", "../../....").param("iccrTestList[0].testType", "VL")
				.param("iccrTestList[1].dateOfTest", "bad").param("iccrTestList[1].testType", "CD4"))
				.andExpect(status().isOk());

		RawIccrDoc form = controller.posted.get();
		assertThat(form.getFirstName()).isEqualTo("Jane");
		assertThat(form.getDob()).isNull();
		assertThat(form.getIccrTestList()).extracting(RawIccrTest::getDateOfTest).containsOnlyNulls();
		assertThat(form.getIccrTestList()).extracting(RawIccrTest::getTestType).containsExactly("VL", "CD4");
	}

	@Test
	void theSavedRowTakesOnlyTheFieldsOfTheForm() throws Exception {
		mvc.perform(post("/saveICCRDataForm").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("firstName", "Jane").param("documentId", "123").param("createUid", "1").param("currentProcessStatus", "5")
				.param("createDate", "2020-01-01 00:00:00"))
				.andExpect(status().isOk());

		RawIccrDoc saved = IccrDocMapping.toRawDoc(controller.posted.get(), 12, 900);

		assertThat(saved.getFirstName()).isEqualTo("JANE");
		assertThat(saved.getDocumentId()).isEqualTo(12);
		assertThat(saved.getCreateUid()).isEqualTo(900);
		assertThat(saved.getCurrentProcessStatus()).isEqualTo(1);
		assertThat(saved.getCreateDate()).isAfter("2024-01-01T00:00:00");
		assertThat(saved.getIccrTestList()).isEmpty();                              // no test rows posted
	}
}
