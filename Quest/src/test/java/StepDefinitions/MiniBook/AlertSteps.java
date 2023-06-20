package StepDefinitions.MiniBook;

import PageObject.MiniBook.Alert;
import io.cucumber.java.en.*;

public class AlertSteps {
    private final Alert _Alert;

    public AlertSteps(org.tfa.seleniumWrapper.TestContext testContext) {
        var driver = testContext.getDriver();
        _Alert= new Alert(driver);
    }

    @When("The user clicks on Alerts&Windows")
    public void theUserClicksOnAlertsWindows() {
       _Alert.ClickAlertsAndWindows();
    }

    @And("The user clicks on Alerts button")
    public void theUserClicksOnAlertsButton() {
        _Alert.ClickAlerts();
    }

    @Then("The user clicks on the click button to see alert then clicks on ok to close the alert")
    public void theUserClicksOnTheClickButtonToSeeAlertThenClicksOnOkToCloseTheAlert() {
        _Alert.ClickClickMeToSeeAlert();
    }
}
