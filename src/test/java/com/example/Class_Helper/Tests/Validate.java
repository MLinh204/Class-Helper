package com.example.Class_Helper.Tests;

import com.example.Class_Helper.config.ConfigProperties;
import com.example.Class_Helper.function.MainFunction;
import com.example.Class_Helper.pageObject.HomePageObject;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.asserts.SoftAssert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class Validate {
    private WebDriver driver;
    private HomePageObject object;
    private MainFunction function;


    @Before
    public void setUp() {
        driver = new ChromeDriver();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("the browser is open")
    public void theBrowserIsOpen() {
        object = new HomePageObject(driver);
        function = new MainFunction(driver);
    }
    @And("the user is on the Class Helper homepage")
    public void theUserIsOnTheClassHelperHomepage() {
        driver.get(ConfigProperties.HOME_URL);
        driver.manage().window().maximize();
    }
    @When("the user looks at the page")
    public void userLookAtTheHomePage(){
        object.isBodyVisible();
    }
    @Then("the user should see the needed UI elements")
    public void userSeeHomePageElement(){
        object.isHomePageElementVisible();
    }

    @When("the user clicks the Home navigation button")
    public void userClicksHomeBtn(){
        object.findNavBtnByText("Home").click();
    }
    @Then("user should be redirected to Home page")
    public void redirectToHomepage(){
        object.isHomePageElementVisible();
        assertEquals(ConfigProperties.HOME_URL, driver.getCurrentUrl());
    }
    @When("user clicks logo")
    public void userClicksLogo(){
        object.clickLogo();
    }

    @When("user clicks Student List button")
    public void userClicksStudentListButton() {
        object.findNavBtnByText("Student List").click();
    }

    @Then("user should be redirected to Student List page")
    public void userShouldBeRedirectedToStudentListPage() {
        assertFalse(object.isHomePageElementVisible());
        assertEquals(ConfigProperties.STUDENT_LIST_URL, driver.getCurrentUrl());
    }
}
