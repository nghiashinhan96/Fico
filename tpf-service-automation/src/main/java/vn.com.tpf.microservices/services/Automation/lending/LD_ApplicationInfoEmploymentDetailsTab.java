package vn.com.tpf.microservices.services.Automation.lending;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import vn.com.tpf.microservices.models.Automation.EmploymentDTO;
import vn.com.tpf.microservices.utilities.Constant;
import vn.com.tpf.microservices.utilities.Utilities;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@Getter
public class LD_ApplicationInfoEmploymentDetailsTab {
    private WebDriver _driver;

    @FindBy(how = How.ID, using = "occupationType_chzn")
    @CacheLookup
    private WebElement occupationTypeElement;

    @FindBy(how = How.XPATH, using = "//*[contains(@id, 'occupationType_chzn_o_')]")
    @CacheLookup
    private List<WebElement> occupationTypeOptionElement;

    @FindBy(how = How.ID, using = "listitem_employerName0")
    @CacheLookup
    private WebElement occupationTypeOthersElement;

    @FindBy(how = How.ID, using = "salariedInfo")
    @CacheLookup
    private WebElement divSalariedInfoContentElement;

    @FindBy(how = How.ID, using = "Text_employerName")
    @CacheLookup
    private WebElement companyTaxCodeElement;

    @FindBy(how = How.ID, using = "natureOfBusiness_chzn")
    @CacheLookup
    private WebElement natureOfBusinessElement;

    @FindBy(how = How.XPATH, using = "//*[contains(@id, 'natureOfBusiness_chzn_o_')]")
    @CacheLookup
    private List<WebElement> natureOfBusinessOptionElement;

    @FindBy(how = How.ID, using = "natOfOccId_chzn")
    @CacheLookup
    private WebElement natureOfOccupationElement;

    @FindBy(how = How.XPATH, using = "//*[contains(@id, 'natOfOccId_chzn_o_')]")
    @CacheLookup
    private List<WebElement> natureOfOccupationOptionElement;

    @FindBy(how = How.ID, using = "industrySalaried_chzn")
    @CacheLookup
    private WebElement industryElement;

    @FindBy(how = How.XPATH, using = "//*[contains(@id, 'industrySalaried_chzn_o_')]")
    @CacheLookup
    private List<WebElement> industryOptionElement;

    @FindBy(how = How.ID, using = "departmentName")
    @CacheLookup
    private WebElement departmentNameElement;

    @FindBy(how = How.ID, using = "EmpStatId_chzn")
    @CacheLookup
    private WebElement employmentStatusElement;

    @FindBy(how = How.XPATH, using = "//*[contains(@id, 'EmpStatId_chzn_o_')]")
    @CacheLookup
    private List<WebElement> employmentStatusOptionElement;

    @FindBy(how = How.ID, using = "designation")
    @CacheLookup
    private WebElement levelElement;

    @FindBy(how = How.ID, using = "year1")
    @CacheLookup
    private WebElement durationYearsElement;

    @FindBy(how = How.ID, using = "month1")
    @CacheLookup
    private WebElement durationMonthsElement;

    @FindBy(how = How.ID, using = "employment_detail_salaried_address_check")
    @CacheLookup
    private WebElement employerAddressCheckElement;

    @FindBy(how = How.ID, using = "doneEmpButton")
    @CacheLookup
    private WebElement doneBtnElement;

    @FindBy(how = How.ID, using = "occupation_Info_Table_wrapper")
    @CacheLookup
    private WebElement tableAfterDoneElement;

    @FindBy(how = How.ID, using = "employmentsaveAndNextButton2")
    @CacheLookup
    private WebElement saveAndNextBtnElement;

    @FindBy(how = How.XPATH, using = "//*[contains(@id, 'applicantIdHeader')]/span")
    @CacheLookup
    private WebElement applicationId;

    public LD_ApplicationInfoEmploymentDetailsTab(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this._driver = driver;
    }

    public void setData(EmploymentDTO data) throws JsonParseException, JsonMappingException, IOException {

        await("occupationTypeElement loading timeout").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> occupationTypeElement.isDisplayed() && occupationTypeElement.isEnabled());
        occupationTypeElement.click();
        await("occupationTypeOptionElement loading timeout").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> occupationTypeOptionElement.size() > 0);
        for (WebElement element : occupationTypeOptionElement) {
            if (element.getText().equals(data.getOccupationType())) {
                element.click();
                break;
            }
        }
//		Select occupationTypeSelect = new Select(occupationTypeElement);
//		await("Occupation Type loading timeout").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
//				.until(() -> occupationTypeSelect.getOptions().size() > 0);
//		occupationTypeSelect.selectByVisibleText(data.getOccupationType());

        if (data.getOccupationType().equals("Others")) {
            await("natureOfOccupationElement loading timeout").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                    .until(() -> natureOfOccupationElement.isDisplayed() && natureOfOccupationElement.isEnabled());
            natureOfOccupationElement.click();
            await("occupationTypeOptionElement loading timeout").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                    .until(() -> natureOfOccupationOptionElement.size() > 0);
            for (WebElement element : natureOfOccupationOptionElement) {
                if (element.getText().equals(data.getNatureOfOccupation())) {
                    element.click();
                    break;
                }
            }
        } else {
            companyTaxCodeElement.sendKeys("%%%");
            await("Occupation Type option loading timeout").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                    .until(() -> occupationTypeOthersElement.isDisplayed());
            occupationTypeOthersElement.click();

            natureOfBusinessElement.click();
            await("natureOfBusinessOptionElement loading timeout").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                    .until(() -> natureOfBusinessOptionElement.size() > 0);
            for (WebElement element : natureOfBusinessOptionElement) {
                if (element.getText().equals(data.getNatureOfBussiness())) {
                    element.click();
                    break;
                }
            }

//		Select natureOfBusinessSelect = new Select(natureOfBusinessElement);
//		await("Nature of business loading timeout").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
//				.until(() -> natureOfBusinessSelect.getOptions().size() > 0);
//		natureOfBusinessSelect.selectByVisibleText(data.getNatureOfBussiness());

            industryElement.click();
            await("industryOptionElement loading timeout").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                    .until(() -> industryOptionElement.size() > 0);
            for (WebElement element : industryOptionElement) {
                if (element.getText().equals(data.getIndustry())) {
                    element.click();
                    break;
                }
            }
//		Select industrySelect = new Select(industryElement);
//		await("Industry loading timeout").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
//				.until(() -> industrySelect.getOptions().size() > 0);
//		industrySelect.selectByVisibleText(data.getIndustry());


            departmentNameElement.sendKeys(data.getDepartment());

            employmentStatusElement.click();
            await("employmentStatusOptionElement loading timeout").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                    .until(() -> employmentStatusOptionElement.size() > 0);
            for (WebElement element : employmentStatusOptionElement) {
                if (element.getText().equals(data.getEmploymentStatus())) {
                    element.click();
                    break;
                }
            }
//		Select employmentStatusSelect = new Select(employmentStatusElement);
//		await("Employment Status loading timeout").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
//				.until(() -> employmentStatusSelect.getOptions().size() > 0);
//		employmentStatusSelect.selectByVisibleText(data.getEmploymentStatus());

            levelElement.sendKeys(data.getLevel());
            durationYearsElement.sendKeys(data.getDurationYears());
            durationMonthsElement.sendKeys(data.getDurationMonths());

            employerAddressCheckElement.click();
        }
    }

    // TODO: compare original data vs element data and report if not equals
    public void validInOutData(Map<String, String> mapValue, String jsonObj) throws Exception {
        EmploymentDTO data = new ObjectMapper().readValue(jsonObj, EmploymentDTO.class);
        Utilities.checkInput(mapValue, "ApplicationInfo_EmploymentDetail_OccupationType", data.getOccupationType(), occupationTypeOptionElement);
        Utilities.checkInput(mapValue, "ApplicationInfo_EmploymentDetail_TaxCode", data.getTaxCode(), companyTaxCodeElement);
        Utilities.checkInput(mapValue, "ApplicationInfo_EmploymentDetail_NatureOfBussiness", data.getNatureOfBussiness(), natureOfBusinessOptionElement);
        Utilities.checkInput(mapValue, "ApplicationInfo_EmploymentDetail_Industry", data.getIndustry(), industryOptionElement);
        Utilities.checkInput(mapValue, "ApplicationInfo_EmploymentDetail_Department", data.getDepartment(), departmentNameElement);
        Utilities.checkInput(mapValue, "ApplicationInfo_EmploymentDetail_EmploymentStatus", data.getEmploymentStatus(), employmentStatusOptionElement);
        Utilities.checkInput(mapValue, "ApplicationInfo_EmploymentDetail_DurationYears", data.getDurationYears(), durationYearsElement);
        Utilities.checkInput(mapValue, "ApplicationInfo_EmploymentDetail_DurationMonths", data.getDurationMonths(), durationMonthsElement);
    }

}
