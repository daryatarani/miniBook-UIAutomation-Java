package wrapper.driverHelper;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.Architecture;
import wrapper.utilities.CommonMethods;
import wrapper.seleniumWrapper.TestConstants;

public class ChromeDriverHelper
{
    public WebDriver CreateChromeDriver()
    {
        return SetupChromeDriver("");
    }

    public WebDriver CreateChromeHeadlessDriver()
    {
        return SetupChromeDriver("Headless");
    }

    public WebDriver CreateChromeIncognitoDriver()
    {
        return SetupChromeDriver("Incognito");
    }

    public WebDriver CreateChromeIncognitoHeadlessDriver()
    {
        return SetupChromeDriver("Incognito Headless");
    }

    public WebDriver CreateChromeGalaxyS20Driver()
    {
        return SetupChromeDriver("Samsung Galaxy S20");
    }


    private WebDriver SetupChromeDriver(String chromeType)
    {
        WebDriverManager.chromedriver().architecture(Architecture.DEFAULT).browserVersion("").setup();
        Optional<Path> browserPath = WebDriverManager.chromedriver().getBrowserPath();
        if(browserPath.isEmpty()) return null;
        var chromeOption = new ChromeOptions();
        switch (chromeType.toLowerCase()) {
            case "headless" -> chromeOption.addArguments("start-maximized", "--disable-gpu", "no-sandbox", "--headless=new","window-size=1280,800");
            case "incognito" -> chromeOption.addArguments("start-maximized", "--disable-gpu", "--no-sandbox", "--incognito");
            case "incognito headless" -> chromeOption.addArguments("start-maximized", "--disable-gpu", "--no-sandbox", "--incognito", "--headless=new","window-size=1280,800");
            case "samsung galaxy s20" -> chromeOption.setExperimentalOption("mobileEmulation", MobileEmulation(TestConstants.MobileDevice.SAMSUNG_GALAXY_S20_ULTRA));
            case "iphone 12 pro" -> chromeOption.setExperimentalOption("mobileEmulation", MobileEmulation(TestConstants.MobileDevice.IPHONE_12_PRO));
            case "ipad pro" -> chromeOption.setExperimentalOption("mobileEmulation", MobileEmulation(TestConstants.MobileDevice.IPAD_PRO));
            default -> chromeOption.addArguments("start-maximized", "--disable-gpu", "--no-sandbox");
        }
        chromeOption.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        Map<String, Object> prefs = new LinkedHashMap<>();
        prefs.put("credentials_enable_service", Boolean.FALSE);
        prefs.put("profile.password_manager_enabled", Boolean.FALSE);
        chromeOption.setExperimentalOption("prefs", prefs);
        chromeOption.setPageLoadStrategy(PageLoadStrategy.EAGER);
        return new ChromeDriver(chromeOption);
    }
}
