package StepDefinitions.MiniBook;

import io.cucumber.java.en.*;
import org.testng.Assert;
import PageObject.MiniBook.Elements;

public class ElementSteps {
    private final Elements _Elements;

    public ElementSteps(org.tfa.seleniumWrapper.TestContext testContext) {
        var driver = testContext.getDriver();
        _Elements = new Elements(driver);
    }
    @When("The user selects radio button from the list of elements")
    public void theUserSelectsRadioButtonFromTheListOfElements() {
        _Elements.ClickRadioButton();
    }

    @When("the user clicks on Element button")
    public void theUserClicksOnElementButton() {
        _Elements.ClickElements();
    }

    @And("The user answers Do you like the site question as {string}")
    public void theUserAnswersDoYouLikeTheSiteQuestionAsYes(String Feedback) {
      _Elements.ClickFeedBack(Feedback);
    }

    @Then("A proper message should be displayed based on the user's answer")
    public void aProperMessageShouldBeDisplayedBasedOnTheUserSAnswer() {
        Assert.assertEquals(_Elements.ReturnRadioMessage(), "You have selected Yes");
    }

    @When("The user selects buttons from the list of elements")
    public void theUserSelectsButtonsFromTheListOfElements() {
        _Elements.ClickButtons();
    }

    @And("The user clicks on double click on me button")
    public void theUserClicksOnDoubleClickOnMeButton() {
        _Elements.ClickDoubleClickMe();
    }

    @Then("A proper message based on the user selection should be displayed")
    public void aProperMessageBasedOnTheUserSelectionShouldBeDisplayed() {
        Assert.assertEquals(_Elements.ReturnDoubleClickMessage(), "You have done a double click");
    }
}