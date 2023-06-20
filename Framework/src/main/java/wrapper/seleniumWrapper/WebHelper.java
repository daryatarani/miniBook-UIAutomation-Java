package wrapper.seleniumWrapper;

import com.google.common.base.Strings;
import org.apache.commons.lang3.EnumUtils;
import org.apache.logging.log4j.*;
import wrapper.seleniumWrapper.TestConstants.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import java.time.Duration;
import java.util.Objects;
import static wrapper.seleniumWrapper.TestConstants.ConfigTypesKey.*;
;

public class WebHelper {
    protected WebDriver driver;
    private LocatorType locator;
    private String locatorInfo;
    @SuppressWarnings("FieldCanBeLocal")
    private String webElementName;
    @SuppressWarnings("FieldCanBeLocal")
    private String webElementValue;
    private int counter = 0;
    private String elementDisplayedText;
    private static final String NAME = "name";
    private static final String VALUE = "value";
    private static final String HTML_XPATH = "//html";
    private final JavascriptExecutor executor;

    private static final Logger Log = LogManager.getLogger(WebHelper.class.getName());

    public WebHelper(WebDriver driver)
    {
        this.driver = driver;
        this.executor = (JavascriptExecutor)this.driver;
    }

    public void Navigate(String url)
    {
        try {
            driver.navigate().to(url);
            Log.info("Driver successfully navigated to : %s".formatted(url));
        }
        catch (Exception ex)
        {
            Log.error("Driver failed to navigate to url due to %s".formatted(ex.getMessage()));
        }
    }

    public WebElement InitialiseDynamicWebElement(LocatorType locatorType, String locatorInfo)
    {
        locator = locatorType;
        this.locatorInfo = locatorInfo;
        var dWait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(
                Objects.requireNonNull(ConfigHelper.getConfigValue(ConfigTypesKey.OBJECT_IDENTIFICATION_TIMEOUT)))));
        dWait.ignoring(NoSuchElementException.class);
        dWait.ignoring(StaleElementReferenceException.class);
        dWait.ignoring(ElementNotInteractableException.class);
        dWait.ignoring(ElementClickInterceptedException.class);
        try
        {
            WebElement dynamicElement;
            switch (locatorType) {
                case ID -> dynamicElement = dWait.until(ExpectedConditions.elementToBeClickable(By.id(locatorInfo)));
                case CLASS_NAME -> dynamicElement =
                        dWait.until(ExpectedConditions.elementToBeClickable(By.className(locatorInfo)));
                case NAME -> dynamicElement = dWait.until(ExpectedConditions.elementToBeClickable(By.name(locatorInfo)));
                case XPATH -> dynamicElement = dWait.until(ExpectedConditions.elementToBeClickable(By.xpath(locatorInfo)));
                case CSS_SELECTOR -> dynamicElement =
                        dWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locatorInfo)));
                case LINK_TEXT -> dynamicElement =
                        dWait.until(ExpectedConditions.elementToBeClickable(By.linkText(locatorInfo)));
                case PARTIAL_LINK_TEXT -> dynamicElement =
                        dWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(locatorInfo)));
                case TAG_NAME -> dynamicElement =
                        dWait.until(ExpectedConditions.elementToBeClickable(By.tagName(locatorInfo)));
                default -> throw new TypeNotPresentException(locator.getClass().getName(),null);
            }
            var elementName = dynamicElement.getAttribute(NAME);
            var elementValue = dynamicElement.getAttribute(VALUE);
            var elementText = (Strings.isNullOrEmpty(elementValue)) ? elementName : elementValue;
            Log.info("WebElement %s is identified successfully".formatted(elementText));
            this.webElementName = dynamicElement.getAttribute(NAME);
            this.webElementValue = dynamicElement.getAttribute(VALUE);
            this.elementDisplayedText = (Strings.isNullOrEmpty(this.webElementValue)) ? this.webElementName : this.webElementValue;
            return dynamicElement;
        }
        catch (Exception ex)
        {
            var strTemp = locatorInfo + " - failed to identify.";
            Log.error("%s%s%s".formatted(strTemp, System.lineSeparator(), ex.getMessage()));
            elementDisplayedText = "WebElement with locator " + locator + " and locator info as " + locatorInfo;
            return null;
        }
    }

    public void PerformWebDriverAction(WebElement objWebElement, WebDriverAction webDriverAction, String actionData)
    {
        if (objWebElement == null || counter >= Integer.parseInt(Objects.requireNonNull(ConfigHelper.getConfigValue(IMPLICIT_WAIT_TIMEOUT))))
        {
            Log.error("%s Web Element is not identified so no action is performed".formatted(elementDisplayedText));
            Assert.fail(elementDisplayedText + " Web Element is not identified so no action is performed");
        }
        boolean boolExecStep;
        var actFocus = new Actions(driver);
        try
        {
            switch (webDriverAction) {
                case CLEAR -> {
                    Objects.requireNonNull(objWebElement).click();
                    Objects.requireNonNull(objWebElement).clear();
                    boolExecStep = true;
                    Log.info("Clearing TextBox %s".formatted(actionData));
                }
                case INPUT -> {
                    Objects.requireNonNull(objWebElement).click();
                    Objects.requireNonNull(objWebElement).clear();
                    Objects.requireNonNull(objWebElement).sendKeys(actionData);
                    boolExecStep = true;
                    Log.info("Entering text %s to TextBox %s".formatted(actionData, elementDisplayedText));
                }
                case SELECT -> {
                    Objects.requireNonNull(objWebElement).click();
                    var selector = new Select(Objects.requireNonNull(objWebElement));
                    selector.selectByVisibleText(actionData);
                    boolExecStep = true;
                    Log.info("Selecting option %s from Selector %s".formatted(actionData, elementDisplayedText));
                }
                case CLICK -> {
                    Objects.requireNonNull(objWebElement).click();
                    boolExecStep = true;
                    Log.info("Clicking on button %s".formatted(elementDisplayedText));
                }
                case FOCUS -> {
                    actFocus.moveToElement(Objects.requireNonNull(objWebElement)).build().perform();
                    boolExecStep = true;
                    Log.info("Focussing on button %s".formatted(elementDisplayedText));
                }
                case DOUBLE_CLICK -> {
                    actFocus.moveToElement(Objects.requireNonNull(objWebElement)).doubleClick().build().perform();
                    boolExecStep = true;
                    Log.info("Double clicking on button %s".formatted(elementDisplayedText));
                }
                default -> boolExecStep = false;
            }
            counter =0;
        }
        catch (Exception ex)
        {
            var strException = ex.getClass().getName();
            strException = strException.split("org.openqa.selenium.")[1];
            switch (EnumUtils.getEnumIgnoreCase(TestConstants.SeleniumExceptionType.class,strException)) {
                case UnhandledAlertException -> {
                    ReadAndHandleAlert();
                    driver.findElement(By.xpath(HTML_XPATH)).click();
                    WebElementExceptionHandler(webDriverAction, actionData);
                    Log.info("Unhandled Alert Exception for WebElement %s Handled".formatted(elementDisplayedText));
                    return;
                }
                default -> {
                    boolExecStep = false;
                    Objects.requireNonNull(Log).error("Unable to perform Action on WebElement %s due to %s".formatted(elementDisplayedText, strException));
                    Assert.fail("Unable to perform Action on WebElement " + elementDisplayedText + " due to " + strException);
                }
            }
        }
        if (!boolExecStep) { Log.info("No Action was performed"); }
    }

    public String ReadAndHandleAlert()
    {
        try
        {
            var wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(
                    Objects.requireNonNull(ConfigHelper.getConfigValue(ConfigTypesKey.OBJECT_IDENTIFICATION_TIMEOUT)))));
            wait.until(ExpectedConditions.alertIsPresent());
            var alert = driver.switchTo().alert();
            var strWarning = alert.getText();
            alert.accept();
            Log.info("Alert displayed as %s".formatted(strWarning));
            return strWarning;
        }

        catch (Exception e)
        {
            Log.error("Alert not displayed due to %s".formatted(e.getMessage()));
            return "Alert not displayed";
        }
    }

    public void GetPageReady()
    {
        try
        {
            new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(
                    Objects.requireNonNull(ConfigHelper.getConfigValue(ConfigTypesKey.OBJECT_IDENTIFICATION_TIMEOUT))))).
                    until(d -> executor.executeScript("return document.readyState").equals("complete"));
        }
        catch (UnhandledAlertException e)
        {
            ReadAndHandleAlert();
            GetPageReady();
        }
        catch (Exception ex)
        {
            Log.error("Unable to get Page Ready state due to %s".formatted(ex.getMessage()));
        }
    }

      public String ReturnVisibleText(WebElement element)
    {
        String visibleText;
        try
        {
            visibleText = element.getText();
        }
        catch (Exception ex)
        {
            Log.error("WebElement %s threw exception %s while fetching the visible text".formatted(element, ex.getMessage()));
            visibleText = InitialiseDynamicWebElement(locator, locatorInfo).getText();
        }
        Log.info("The visible text for the webElement is %s".formatted(visibleText));
        return visibleText;
    }

    private void WebElementExceptionHandler(WebDriverAction webDriverAction, String strData)
    {
        var objWebElement = InitialiseDynamicWebElement(locator, locatorInfo);
        counter++;
        PerformWebDriverAction(objWebElement, webDriverAction, strData);
    }
}