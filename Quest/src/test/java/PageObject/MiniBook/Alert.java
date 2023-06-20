package PageObject.MiniBook;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import wrapper.seleniumWrapper.WebHelper;
import static ApplicationConstants.TestConstants.Selectors.*;
import static wrapper.seleniumWrapper.TestConstants.WebDriverAction.*;
import static wrapper.seleniumWrapper.TestConstants.LocatorType.*;

public class Alert {
    protected WebElement Btn_AlertsAndWindows;
    protected WebElement Btn_Alerts;
    protected WebElement Btn_ClickMeToSeeAlert;
    private final WebHelper _webHelper;

    public Alert(WebDriver driver) {
        _webHelper = new WebHelper(driver);
    }

    public void ClickAlertsAndWindows() {
        _webHelper.PerformWebDriverAction(getBtn_AlertsAndWindows(), CLICK, null);
    }

    public void ClickAlerts() {
        _webHelper.PerformWebDriverAction(getBtn_Alerts(), CLICK, null);
    }

    public void ClickClickMeToSeeAlert() {
        _webHelper.PerformWebDriverAction(getBtn_ClickMeToSeeAlert(), CLICK, null);
        _webHelper.ReadAndHandleAlert();
    }

    private WebElement getBtn_AlertsAndWindows() {
        return Btn_AlertsAndWindows=_webHelper.InitialiseDynamicWebElement(LINK_TEXT, AlertsAndWindows);
    }

    private WebElement getBtn_Alerts() {
       return Btn_Alerts=_webHelper.InitialiseDynamicWebElement(XPATH, Alerts);
    }

    private WebElement getBtn_ClickMeToSeeAlert() {
        return Btn_ClickMeToSeeAlert=_webHelper.InitialiseDynamicWebElement(CSS_SELECTOR, ClickMeToSeeAlert);
    }
}
