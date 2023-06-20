package org.tfa.seleniumWrapper;

import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

public class TestContext
{
    private WebDriver driver;
    private Scenario scenario;
    private ScenarioContext scenarioContext;

    public WebDriver getDriver() {
        return driver;
    }

    public Scenario getScenario(){return scenario;}

    public ScenarioContext getScenarioContext() {
        return scenarioContext;
    }

    public void setTestContext(Scenario scenario, WebDriver driver)
    {
        this.driver = driver;
        this.scenario = scenario;
        this.scenarioContext = new ScenarioContext();
    }
}
