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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.testng.Assert.assertTrue;

public class Validate {
    private WebDriver driver;
    private HomePageObject object;
    private MainFunction function;
    private WebDriverWait wait;


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
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
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
        wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("Home")));
        assertFalse(object.isHomePageElementVisible());
        assertEquals(ConfigProperties.STUDENT_LIST_URL, driver.getCurrentUrl());
    }

    @When("user clicks Quiz button")
    public void clickQuizBtn(){
        object.findNavBtnByText("Quiz").click();
    }
    @Then("user should be redirected to Quiz page")
    public void redirectToQuizPage(){
        wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("Home")));
        assertFalse(object.isHomePageElementVisible());
        assertEquals(ConfigProperties.QUIZ_URL, driver.getCurrentUrl());
    }

    @When("user clicks Play Game button")
    public void userClicksPlayGameButton() {
        object.findNavBtnByText("Play Game").click();
    }

    @Then("user should be redirected to Game page")
    public void userShouldBeRedirectedToGamePage() {
        wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("Home")));
        assertFalse(object.isHomePageElementVisible());
        assertEquals(ConfigProperties.NEW_GAME_URL, driver.getCurrentUrl());
    }

    @When("user clicks Functions button")
    public void userClicksFunctionsButton() {
        object.clickFunctionsBtn();
    }

    @Then("dropdown list of functions should be displayed")
    public void dropdownListOfFunctionsShouldBeDisplayed() {
        assertTrue(object.isDropdownFunctionsDisplayed());
    }

    @And("user clicks Add Student button")
    public void userClicksAddStudentButton() {
        object.clickFunctionsByLinkText("Add Student");
    }

    @Then("user should be redirected to Add Student Page")
    public void userShouldBeRedirectedToAddStudentPage() {
        wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("Home")));
        assertFalse(object.isHomePageElementVisible());
        assertEquals(ConfigProperties.CREATE_STUDENT_URL, driver.getCurrentUrl());
    }

    @And("user clicks Random Student button")
    public void userClicksRandomStudentButton() {
        object.clickFunctionsByLinkText("Random Student");
    }

    @Then("user should be redirected to Random Student Page")
    public void userShouldBeRedirectedToRandomStudentPage() {
        wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("Home")));
        assertFalse(object.isHomePageElementVisible());
        assertEquals(ConfigProperties.RANDOM_STUDENT_URL, driver.getCurrentUrl());
    }

    @And("user clicks White Board button")
    public void userClicksWhiteBoardButton() {
        object.clickFunctionsByLinkText("White Board");
    }

    @Then("user should be redirected to White Board Page")
    public void userShouldBeRedirectedToWhiteBoardPage() {
        wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("Home")));
        assertFalse(object.isHomePageElementVisible());
        assertEquals(ConfigProperties.WHITEBOARD_URL, driver.getCurrentUrl());
    }

    @And("user clicks Clock button")
    public void userClicksClockButton() {
        object.clickFunctionsByLinkText("Clock");
    }

    @Then("user should be redirected to Clock Page")
    public void userShouldBeRedirectedToClockPage() {
        wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("Home")));
        assertFalse(object.isHomePageElementVisible());
        assertEquals(ConfigProperties.CLOCK_URL, driver.getCurrentUrl());
    }

    @When("user scrolls to footer")
    public void userScrollsToFooter() {
        object.scrollToFooter();
    }

    @And("user clicks Scroll to top button")
    public void userClicksScrollToTopButton() {
        object.clickScrollToTop();
    }

    @Then("user should move to the top")
    public void userShouldMoveToTheTop() {
        assertTrue(object.isMainBunnerTitleVisible());
    }
}
