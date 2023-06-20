package StepDefinitions.MiniBook;

import io.cucumber.java.en.*;
import wrapper.seleniumWrapper.ConfigHelper;
import wrapper.seleniumWrapper.TestConstants;
import wrapper.seleniumWrapper.WebHelper;
import static ApplicationConstants.TestConstants.LOGIN_URL;
import static ApplicationConstants.TestConstants.PathVariables.ConfigFilePath;
import static wrapper.seleniumWrapper.TestConstants.ConfigTypesKey.ENV;

public class MiniBookLoginSteps {

    private final WebHelper _webHelper;

    public MiniBookLoginSteps(TestContext testContext) {
        var driver = testContext.getDriver();
        _webHelper = new WebHelper(driver);
    }

    @And("user login to mini Book Store website")
    public void userLoginToMiniBookStoreWebsite() {
        _webHelper.Navigate(getUrl());
    }

    private String getProtocol() {
        return ConfigHelper.getConfigValue(TestConstants.ConfigTypesKey.PROTOCOL);
    }

    private String getUrl() {
        var url = ConfigHelper.getConfigValue(ConfigHelper.getConfigValue(ENV, ConfigFilePath) + LOGIN_URL, ConfigFilePath);
        var protocol = getProtocol();
        protocol = switch(protocol.toLowerCase())
                {
                    case "http" -> "http://";
                    case "https" -> "https://";
                    default -> protocol;
                };
        return protocol + url;
    }
}
