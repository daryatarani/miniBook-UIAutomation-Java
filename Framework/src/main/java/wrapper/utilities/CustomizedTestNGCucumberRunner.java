package wrapper.utilities;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.service.ExtentService;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import lombok.SneakyThrows;
import org.testng.annotations.*;
import wrapper.seleniumWrapper.TestConstants;
import wrapper.seleniumWrapper.ConfigHelper;

import java.io.File;
import java.util.HashMap;

@SuppressWarnings("NewClassNamingConvention")
public class CustomizedTestNGCucumberRunner extends TestContext {

    private TestNGCucumberRunner testNGCucumberRunner;

    @BeforeClass(alwaysRun = true)
    public void setUpClass(){
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @SuppressWarnings({"unused", "groupsTestNG"})
    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper)
    {
        testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
    }

    @DataProvider
    public Object[][] scenarios() {
        if (testNGCucumberRunner == null) {
            return new Object[0][0];
        }
        return testNGCucumberRunner.provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        if (testNGCucumberRunner == null) {
            return;
        }
        testNGCucumberRunner.finish();
    }

    @BeforeSuite(alwaysRun = true)
    public void setUpExtent()
    {
        SetupExtentReports();
    }

    @SneakyThrows
    private void SetupExtentReports(){
        var actualDate = CommonMethods.GetCustomDateTime(null,"dd-MM-yyy HH-mm-ss",0);
        var reportPath = TestConstants.PathVariables.HTML_REPORT_FOLDER + File.separator +"ExecutionReport "+actualDate + File.separator +
                TestConstants.PathVariables.EXTENT_REPORT_NAME +"_"+ actualDate + ".html";
        var reportConfigFilePath = TestConstants.PathVariables.FRAMEWORK_DIRECTORY + File.separator
                + TestConstants.PathVariables.EXTENT_CONFIG_FILE_PATH + File.separator +
                TestConstants.PathVariables.EXTENT_CONFIG_XML;
        ConfigHelper.setConfigValue(TestConstants.PathVariables.EXECUTION_REPORT_PATH, TestConstants.PathVariables.HTML_REPORT_FOLDER + File.separator +"ExecutionReport "+actualDate );
        var sparkReport = new ExtentSparkReporter(reportPath);
        ExtentService.getInstance().attachReporter(sparkReport);
        sparkReport.loadXMLConfig(reportConfigFilePath);
        sparkReport.config().thumbnailForBase64(true);
        var sysInfo = new HashMap<String,String>();
        sysInfo.put("Host Name", System.getenv("COMPUTERNAME"));
        sysInfo.put("Domain", System.getenv("USERDOMAIN"));
        sysInfo.put("OS", System.getProperty("os.name"));
        sysInfo.put("Username", System.getProperty("user.name"));
        sysInfo.forEach((k,v) -> ExtentService.getInstance().setSystemInfo(k, v));
    }
}
