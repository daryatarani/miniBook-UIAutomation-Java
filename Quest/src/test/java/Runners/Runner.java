package Runners;

import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.*;
import wrapper.seleniumWrapper.ConfigHelper;
import wrapper.seleniumWrapper.TestConstants;
import wrapper.utilities.CustomizedTestNGCucumberRunner;
import static ApplicationConstants.TestConstants.*;

@SuppressWarnings("NewClassNamingConvention")
@CucumberOptions(
        features = "src/test/resources/TestFeatures/MiniBook/Elements.feature",
        glue = {"StepDefinition","StepDefinitions", "wrapper/hooks/webHooks", "ApiHook"},
        plugin = {"pretty:target/cucumber-pretty.txt",
                "html:target/cucumber-html-report.html",
                "json:target/cucumber.json",
                "rerun:target/rerun.txt",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        }
)
public final class Runner extends CustomizedTestNGCucumberRunner {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @Parameters({"BrowserType","Env"})
    @BeforeTest
    public void beforeTest(String browser, String env) {
        ConfigHelper.setConfigValue(TestConstants.ConfigTypesKey.BROWSER, browser);
        ConfigHelper.setConfigValue(ENV,env, PathVariables.ConfigFilePath);
    }

    @AfterTest
    public void afterTest() {
        ConfigHelper.setConfigValue(TestConstants.ConfigTypesKey.BROWSER, "");
    }
}