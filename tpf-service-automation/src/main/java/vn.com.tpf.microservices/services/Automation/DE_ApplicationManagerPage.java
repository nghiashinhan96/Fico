package vn.com.tpf.microservices.services.Automation;

import lombok.Getter;
import org.apache.commons.lang.StringEscapeUtils;
import org.awaitility.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import vn.com.tpf.microservices.utilities.Constant;
import vn.com.tpf.microservices.utilities.Utilities;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.awaitility.Awaitility.with;
@Getter
public class DE_ApplicationManagerPage {
    private WebDriver _driver;

    @FindBy(how = How.ID, using = "applicationManagerForm1")
    private WebElement applicationManagerFormElement;

    @FindBy(how = How.ID, using = "appManager_lead_application_number")
    private WebElement applicationNumberElement;

    @FindBy(how = How.XPATH, using = "//*[contains(@id, 'applicationManagerForm1')]//input[@type='button']")
    private WebElement searchApplicationElement;

    @FindBy(how = How.XPATH, using = "//table[@id='applicationTable']//tbody//tr")
    private List<WebElement> tdApplicationElement;

    @FindBy(how = How.ID, using = "showTasks")
    private WebElement showTaskElement;

    @FindBy(how = How.ID, using = "taskTableDiv")
    private WebElement taskTableDivElement;

    @FindBy(how = How.XPATH, using = "//*[contains(@id, 'edit_button0')]//input[@type='button']")
    private WebElement editElement;

    @FindBy(how = How.ID, using = "Text_selected_user0")
    private WebElement textSelectUserElement;

    @FindBy(how = How.ID, using = "holder")
    private WebElement textSelectUserContainerElement;

    @FindBy(how = How.XPATH, using = "//a[contains(@id, 'listitem_selected_user')]")
    private List<WebElement> textSelectUserOptionElement;

    @FindBy(how = How.XPATH, using = "//*[contains(@id, 'with_branch')]//input[@type='submit']")
    private WebElement saveTaskElement;

    @FindBy(how = How.XPATH, using = "//*[contains(@class,'applications-li')]//li[contains(@class,'application-column-loan')]//span[contains(text(),'Applications')]")
    private WebElement applicationElement;

    @FindBy(how = How.XPATH, using = "//*[contains(@class,'applications-li')]")
    private WebElement menuApplicationElement;


    @FindBy(how = How.XPATH, using = "//*[contains(@class,'backSaveBtns')]//input[@type='button']")
    private WebElement backBtnElement;

    @FindBy(how = How.XPATH, using = "//table[@id='applicationTable']//tbody//tr[1]//td[1]")
    private WebElement applicationTableAppIDElement;

    public DE_ApplicationManagerPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        _driver = driver;
    }

    //teamName
    @FindBy(how = How.ID, using = "Text_team_Branch0")
    private WebElement textSelectTeamNameElement;

    @FindBy(how = How.XPATH, using = "//div[@id = 'content_team_Branch0']//ul[@id = 'holder']")
    private WebElement textSelectTeamNameContainerElement;

    @FindBy(how = How.XPATH, using = "//li[contains(@id, 'listitem_team_Branch')]")
    private List<WebElement> liSelectTeamNameOptionElement;

    @FindBy(how = How.XPATH, using = "//a[contains(@id, 'listitem_team_Branch')]")
    private List<WebElement> textSelectTeamNameOptionElement;

    public void setData(String appId,String user) {
        await("appManager_lead_application_number visibale Timeout!").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> applicationManagerFormElement.isDisplayed());

        applicationNumberElement.sendKeys(appId);
        searchApplicationElement.click();

        await("tdApplicationElement visibale Timeout!").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> tdApplicationElement.size() > 0);

        await("showTaskElement visibale Timeout!").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> showTaskElement.isDisplayed());

        showTaskElement.click();

        await("taskTableDivElement visibale Timeout!").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> taskTableDivElement.isDisplayed());

        await("editElement visibale Timeout!").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> editElement.isDisplayed());

        editElement.click();

        await("textSelectUserElement enable Timeout!").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> textSelectUserElement.isEnabled());

        textSelectUserElement.clear();
        textSelectUserElement.sendKeys(user);
        await("textSelectUserContainerElement displayed timeout").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> textSelectUserContainerElement.isDisplayed());

        await("textSelectUserOptionElement loading timeout").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> textSelectUserOptionElement.size() > 0);
        for (WebElement e : textSelectUserOptionElement) {
            if (!Objects.isNull(e.getAttribute("title")) && StringEscapeUtils.unescapeJava(e.getAttribute("title")).equals(user)) {
                e.click();
                break;
            }
        }
        Utilities.captureScreenShot(_driver);
        saveTaskElement.click();
    }

    public String getAppID(String leadApp) {
        String appID="";
        await("appManager_lead_application_number visibale Timeout!").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> applicationManagerFormElement.isDisplayed());

        applicationNumberElement.sendKeys(leadApp);
        searchApplicationElement.click();

        await("tdApplicationElement visibale Timeout!").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> tdApplicationElement.size() > 0);

        await("showTaskElement visibale Timeout!").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> showTaskElement.isDisplayed());

        showTaskElement.click();

        await("applicationTableAppIDElement visibale Timeout!").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> applicationTableAppIDElement.isDisplayed());

        appID=applicationTableAppIDElement.getText();

        Utilities.captureScreenShot(_driver);

        return appID;
    }

    public void setAssignedHasTeamName(String appId,String user, String teamName) {
        await("appManager_lead_application_number visibale Timeout!").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> applicationManagerFormElement.isDisplayed());

        applicationNumberElement.sendKeys(appId);
        searchApplicationElement.click();

        await("tdApplicationElement visibale Timeout!").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> tdApplicationElement.size() > 0);

        await("showTaskElement visibale Timeout!").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> showTaskElement.isDisplayed());

        showTaskElement.click();

        await("taskTableDivElement visibale Timeout!").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> taskTableDivElement.isDisplayed());

        await("editElement visibale Timeout!").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> editElement.isDisplayed());

        editElement.click();
        await("Textbox select Team enable Timeout!").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> textSelectTeamNameElement.isEnabled());

        textSelectTeamNameElement.clear();
        textSelectTeamNameElement.sendKeys(teamName);

        with().pollInterval(Duration.FIVE_SECONDS).
                await("Select Team Name Container displayed timeout").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> textSelectTeamNameContainerElement.isDisplayed());

        int listTeamNameOption = liSelectTeamNameOptionElement.size();

        await("No Team Name Found timeout").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> listTeamNameOption > 0);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (WebElement eTeamName : liSelectTeamNameOptionElement) {
            if (!Objects.isNull(eTeamName.getAttribute("username")) && StringEscapeUtils.unescapeJava(eTeamName.getAttribute("username")).equals(teamName)) {
                eTeamName.click();
                break;
            }
        }

        Utilities.captureScreenShot(_driver);
        await("textSelectUserElement enable Timeout!").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> textSelectUserElement.isEnabled());

        textSelectUserElement.clear();
        textSelectUserElement.sendKeys(user);


        await("textSelectUserOptionElement loading timeout").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> textSelectUserOptionElement.size() > 0);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (WebElement e : textSelectUserOptionElement) {
            if (!Objects.isNull(e.getAttribute("title")) && StringEscapeUtils.unescapeJava(e.getAttribute("title")).equals(user)) {
                e.click();
                break;
            }
        }
        Utilities.captureScreenShot(_driver);
        saveTaskElement.click();
    }
    public void setAssignedRaise(String appId,String user, String teamName) {
        await("appManager_lead_application_number visibale Timeout!").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> applicationManagerFormElement.isDisplayed());

        applicationNumberElement.sendKeys(appId);
        searchApplicationElement.click();

        await("tdApplicationElement visibale Timeout!").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> tdApplicationElement.size() > 0);

        await("showTaskElement visibale Timeout!").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> showTaskElement.isDisplayed());

        showTaskElement.click();

        await("taskTableDivElement visibale Timeout!").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> taskTableDivElement.isDisplayed());

        await("editElement visibale Timeout!").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> editElement.isDisplayed());

        editElement.click();
        await("Textbox select Team enable Timeout!").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> textSelectTeamNameElement.isEnabled());

        textSelectTeamNameElement.clear();
        textSelectTeamNameElement.sendKeys(teamName);

        with().pollInterval(Duration.FIVE_SECONDS).
                await("Select Team Name Container displayed timeout").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> textSelectTeamNameContainerElement.isDisplayed());

        int listTeamNameOption = liSelectTeamNameOptionElement.size();

        await("No Team Name Found timeout").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> listTeamNameOption > 0);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (WebElement eTeamName : liSelectTeamNameOptionElement) {
            if (!Objects.isNull(eTeamName.getAttribute("username")) && StringEscapeUtils.unescapeJava(eTeamName.getAttribute("username")).equals(teamName)) {
                eTeamName.click();
                break;
            }
        }

        Utilities.captureScreenShot(_driver);
        await("textSelectUserElement enable Timeout!").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> textSelectUserElement.isEnabled());

        textSelectUserElement.clear();
        textSelectUserElement.sendKeys(user);


        await("textSelectUserOptionElement loading timeout").atMost(Constant.TIME_OUT_S, TimeUnit.SECONDS)
                .until(() -> textSelectUserOptionElement.size() > 0);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (WebElement e : textSelectUserOptionElement) {
            if (!Objects.isNull(e.getAttribute("title")) && StringEscapeUtils.unescapeJava(e.getAttribute("title")).equals(user)) {
                e.click();
                break;
            }
        }
        Utilities.captureScreenShot(_driver);
        saveTaskElement.click();
    }
}
