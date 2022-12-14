package vn.com.tpf.microservices.services.Automation.lending;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import vn.com.tpf.microservices.models.Automation.ReferenceDTO;
import vn.com.tpf.microservices.utilities.Constant;
import vn.com.tpf.microservices.utilities.Utilities;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@Getter
public class LD_ReferencesPage {
    private WebDriver _driver;
    @FindBy(how = How.ID, using = "applicationChildTabs_referencesInfo")
    @CacheLookup
    private WebElement tabReferencesElement;

    //references_details_table_wrapper
    @FindBy(how = How.ID, using = "referenceDetailList")
    @CacheLookup
    private WebElement tabReferencesContainerElement;

    @FindBy(how = How.XPATH, using = "//input[contains(@id, 'customer_references_name_')]")
    @CacheLookup
    private List<WebElement> fullNameElement;

    @FindBy(how = How.ID, using = "create_new_reference_detail_row")
    @CacheLookup
    private WebElement btnCreateNewRowElement;

    @FindBy(how = How.XPATH, using = "//button[@class='saveBtnHkeys btn btn-primary']")
    @CacheLookup
    private WebElement saveBtnElement;

    public LD_ReferencesPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        _driver = driver;
    }

    public void setData(List<ReferenceDTO> datas) throws IOException {
        int index = 0;
        for (ReferenceDTO data : datas) {
        	_driver.findElement(By.id("customer_references_name_"+ index)).sendKeys(data.getFullName());
//            fullNameElement.get(index).sendKeys(data.getFullName());

            WebElement relationship = _driver.findElement(By.id("customer_references_relationship_" + index + "_chzn"));
            relationship.click();
            List<WebElement> relationships = _driver.findElements(By.xpath("//*[contains(@id, 'customer_references_relationship_" + index + "_chzn_o_')]"));
            await("relationships loading timeout").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                    .until(() -> relationships.size() > 0);
            Utilities.chooseDropdownValue(data.getRelationShip(), relationships);

            WebElement mobilePhone = _driver.findElement(By.id("mobilenumber_" + index + "_phoneNumber"));
            mobilePhone.sendKeys(data.getMobilePhoneNumber());
            if (index < datas.size() - 1) {
                await("Btn Add Reference not enabled - Timeout!").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                        .until(() -> btnCreateNewRowElement.isEnabled());
                btnCreateNewRowElement.click();
            }
            index++;
        }
    }

    public void validInOutData(Map<String, String> mapValue, String jsonObj) throws Exception {
        ReferenceDTO[] datas = new ObjectMapper().readValue(jsonObj, ReferenceDTO[].class);
        int index = 0;
        for (ReferenceDTO data : datas) {
            Utilities.checkInput(mapValue, "References_FullName_" + index, data.getFullName(), fullNameElement.get(index));
            List<WebElement> relationships = _driver.findElements(By.xpath("//*[contains(@id, 'customer_references_relationship_" + index + "_chzn_o_')]"));
            Utilities.checkInput(mapValue, "References_Relationship_" + index, data.getRelationShip(), relationships);
            WebElement mobilePhoneElement = _driver.findElement(By.id("mobilenumber_" + index + "_phoneNumber"));
            Utilities.checkInput(mapValue, "References_MobilePhone_" + index, data.getMobilePhoneNumber(), mobilePhoneElement);
            index++;
        }
    }
}
