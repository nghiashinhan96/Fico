package vn.com.tpf.microservices.utilities;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utilities {
    private static final Logger log = LoggerFactory.getLogger(Utilities.class);
    @Autowired
    static RestTemplate restTemplate;
    public static HashMap<String, Object> createMqObject(String userName, String password) {
        return new HashMap<String, Object>() {
            {
                put("userName", userName);
                put("password", password);
            }
        };
    }

    /**
     * capture Screen with Folder
     * @param driver
     * @param functionName
     * @param appId
     */

    public static void captureScreenShotWithFolder(WebDriver driver, String functionName, String appId, String stage){
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        SessionId session = ((RemoteWebDriver)driver).getSessionId();
        try {
            if (functionName != null && !functionName.isEmpty()) {
                if (appId != null && !appId.isEmpty()) { //appId maybe replace with the Last name and First name
                    FileUtils.copyFile(src, new File(Constant.SCREENSHOT_PRE_PATH_DOCKER  + functionName + "/" + appId + "/" + appId + "_" +stage+"_"+ session.toString() + "_"+ System.currentTimeMillis() + Constant.SCREENSHOT_EXTENSION));

                } else {
                    FileUtils.copyFile(src, new File(Constant.SCREENSHOT_PRE_PATH_DOCKER + functionName + "/" +stage+"_"+ session.toString() + "_"+ System.currentTimeMillis() + Constant.SCREENSHOT_EXTENSION));

                }

            } else {
                FileUtils.copyFile(src, new File(Constant.SCREENSHOT_PRE_PATH_DOCKER + session.toString() + "_"+ System.currentTimeMillis() + Constant.SCREENSHOT_EXTENSION));

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void chooseDropdownValue(String visibleTextValue, List<WebElement> optionElements) {
        for (WebElement element : optionElements) {
            if (element.getText().equals(visibleTextValue)) {
                element.click();
                break;
            }
        }
    }

    public static void chooseDropdownValueArray(String[] visibleTextValue, List<WebElement> optionElements) {
        for(String purpose : visibleTextValue) {
            System.out.println("purpose: " + purpose);
            for (WebElement element : optionElements) {
                if (element.getText().equals(purpose)) {
                    element.click();
                    //break;
                }
            }
        }
    }

    public static String getDropdownSelected(List<WebElement> optionElements) {
        for (WebElement element : optionElements) {
            if (element.getAttribute("class").contains("result-selected")) {
                return element.getText();
            }
        }
        return "";
    }

    public static void checkInput(Map<String, String> mapResult, String title, String input, WebElement element) {
        String _elementValue = element.getAttribute("value");
        if (StringUtils.isNotBlank(_elementValue) && StringUtils.isNotBlank(input) && !_elementValue.equals(input))
            mapResult.put(title, _elementValue);
    }

    public static void checkInput(Map<String, String> mapResult, String title, String input,
                                  List<WebElement> elements) {
        String _elementValue = getDropdownSelected(elements);
        if (StringUtils.isNotBlank(_elementValue) && StringUtils.isNotBlank(input) && !_elementValue.equals(input))
            mapResult.put(title, _elementValue);
    }

    public static void captureScreenShot(WebDriver ldriver) {
        File src = ((TakesScreenshot) ldriver).getScreenshotAs(OutputType.FILE);
        SessionId session = ((RemoteWebDriver)ldriver).getSessionId();
        try {
            FileUtils.copyFile(src, new File(Constant.SCREENSHOT_PRE_PATH_DOCKER + session.toString() + "_"+ System.currentTimeMillis() + Constant.SCREENSHOT_EXTENSION));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String readTextFromFile(String filePath) {
        try {
            System.out.println(System.getProperty("user.dir"));
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (Exception e) {
            return null;
        }
    }

    public static String getResourceFileContent(String filePath) {
        ClassLoader classLoader = Utilities.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(filePath)) {
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String sendApiPost(String jsonString, String url) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String jsonOutput = null;
        try {
            HttpPost postRequest = new HttpPost(url);
            StringEntity userEntity = new StringEntity(jsonString, "application/json", "utf-8");
            postRequest.setEntity(userEntity);

            HttpResponse response = null;
            try {
                response = httpClient.execute(postRequest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int statusCode = response.getStatusLine().getStatusCode();
            try {
                jsonOutput = EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return jsonOutput;
    }

    public static void checkValueSendkey(String inputValue, WebElement webElement) {
        int i = 0;
        String textSendkey = "";
        do {
            webElement.clear();
            webElement.sendKeys(inputValue);
            textSendkey = webElement.getAttribute("value");
            i++;
        } while (!textSendkey.equals(inputValue) &&  i < 5);
    }

    public static void checkValueSendkeyAmount(String inputValue, WebElement webElement) {
        int i = 0;
        String textSendkey = "";
        do {
//            webElement.click();
            webElement.clear();
            webElement.sendKeys(inputValue);
            textSendkey = webElement.getAttribute("value").replace(",","");
            i++;
        } while (!textSendkey.equals(inputValue) &&  i < 5);
    }

    private static final String WORD_SEPARATOR = " ";

    public static String convertToTitleCaseSplitting(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        return Arrays
                .stream(text.split(WORD_SEPARATOR))
                .map(word -> word.isEmpty()
                        ? word
                        : Character.toTitleCase(word.charAt(0)) + word
                        .substring(1)
                        .toLowerCase())
                .collect(Collectors.joining(WORD_SEPARATOR));
    }

}
