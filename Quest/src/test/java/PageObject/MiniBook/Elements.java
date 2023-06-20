package PageObject.MiniBook;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import wrapper.seleniumWrapper.WebHelper;
import static ApplicationConstants.TestConstants.*;
import static ApplicationConstants.TestConstants.Selectors.*;
import static wrapper.seleniumWrapper.TestConstants.LocatorType.*;
import static wrapper.seleniumWrapper.TestConstants.WebDriverAction.*;

public class Elements {
    protected WebElement Btn_Elements;
    protected WebElement Btn_TextBox;
    protected WebElement Btn_CheckBox;
    protected WebElement Btn_RadioButton;
    protected WebElement Btn_WebTables;
    protected WebElement Btn_Buttons;
    protected WebElement Btn_Links;
    protected WebElement Btn_BrokenLinks;
    protected WebElement Btn_UploadsAndDownloads;
    protected WebElement Btn_DynamicProperties;
    protected WebElement RadioBtn_Yes;
    protected WebElement RadioBtn_No;
    protected WebElement RadioBtn_Impressive;
    protected WebElement Label_RadioButtonText;
    protected WebElement Btn_DoubleClickMe;
    protected WebElement Label_DoubleClickText;
    private final WebHelper _webHelper;

    public Elements(WebDriver driver) {
       _webHelper = new WebHelper(driver);
    }

    public void ClickElements() {
       _webHelper.PerformWebDriverAction(getBtn_Elements(), CLICK, null);
    }

    public void ClickFeedBack(String FeedBack) {
        var radioBtnToClick = FeedBack.equalsIgnoreCase(YES) ? getRadioBtn_Yes() :
                FeedBack.equalsIgnoreCase(NO) ? getRadioBtn_No() :
                        getRadioBtn_Impressive();
        _webHelper.PerformWebDriverAction(radioBtnToClick,CLICK, null);
    }

    public String ReturnRadioMessage() {
        return _webHelper.ReturnVisibleText(getLabel_RadioButtonText());
    }

    public void ClickRadioButton() {
       _webHelper.PerformWebDriverAction(getBtn_RadioButton(), CLICK, null);
    }

    public void ClickTextBox() {
        _webHelper.PerformWebDriverAction(getBtn_TextBox(), CLICK, null);
    }

    public void ClickDoubleClickMe() {
        _webHelper.PerformWebDriverAction(getBtn_DoubleClickMe(), DOUBLE_CLICK, null);
    }

    public String ReturnDoubleClickMessage() {
        return _webHelper.ReturnVisibleText(getLabel_DoubleClickText());
    }

    public void ClickButtons() {
        _webHelper.PerformWebDriverAction(getBtn_Buttons(), CLICK, null);
    }

    private WebElement getBtn_Elements() {
        return Btn_Elements= _webHelper.InitialiseDynamicWebElement(LINK_TEXT , Elements);
    }

    private WebElement getBtn_TextBox() {
        return Btn_TextBox= _webHelper.InitialiseDynamicWebElement(XPATH, TextBox);
    }

    private WebElement getBtn_CheckBox() {
        return Btn_CheckBox=_webHelper.InitialiseDynamicWebElement(XPATH, CheckBox);
    }

    private WebElement getBtn_RadioButton() {
        return Btn_RadioButton=_webHelper.InitialiseDynamicWebElement(XPATH, RadioButton);
    }

    private WebElement getBtn_WebTables() {
        return Btn_WebTables=_webHelper.InitialiseDynamicWebElement(XPATH, WebTables);
    }

    private WebElement getBtn_Buttons() {
        return Btn_Buttons=_webHelper.InitialiseDynamicWebElement(CSS_SELECTOR, Buttons);
    }

    private WebElement getBtn_Links() {
        return Btn_Links=_webHelper.InitialiseDynamicWebElement(CSS_SELECTOR, Links);
    }

    private WebElement getBtn_BrokenLinks() {
        return Btn_BrokenLinks=_webHelper.InitialiseDynamicWebElement(XPATH, BrokenLinks);
    }

    private WebElement getBtn_UploadsAndDownloads() {
        return Btn_UploadsAndDownloads=_webHelper.InitialiseDynamicWebElement(XPATH, UploadsAndDownloads);
    }

    private WebElement getBtn_DynamicProperties() {
        return Btn_DynamicProperties=_webHelper.InitialiseDynamicWebElement(XPATH, DynamicProperties);
    }

    private WebElement getRadioBtn_Yes() {
        return RadioBtn_Yes=_webHelper.InitialiseDynamicWebElement(CSS_SELECTOR, DynamicProperties);
    }

    private WebElement getRadioBtn_No() {
        return RadioBtn_No=_webHelper.InitialiseDynamicWebElement(CSS_SELECTOR, DynamicProperties);
    }

    private WebElement getRadioBtn_Impressive() {
        return RadioBtn_Impressive=_webHelper.InitialiseDynamicWebElement(CSS_SELECTOR, DynamicProperties);
    }

    private WebElement getLabel_RadioButtonText() {
       return Label_RadioButtonText=_webHelper.InitialiseDynamicWebElement(XPATH, RadioButtonText);
    }

    private WebElement getBtn_DoubleClickMe() {
        return Btn_DoubleClickMe=_webHelper.InitialiseDynamicWebElement(CSS_SELECTOR, DoubleClickMe);
    }

    private WebElement getLabel_DoubleClickText() {
        return Label_DoubleClickText=_webHelper.InitialiseDynamicWebElement(CSS_SELECTOR, DoubleClickText);
    }
}