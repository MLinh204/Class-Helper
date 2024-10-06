package com.example.Class_Helper.Tests;

import com.example.Class_Helper.Hooks.HomepageHooks;
import com.example.Class_Helper.config.ConfigURL;
import com.example.Class_Helper.function.MainFunction;
import com.example.Class_Helper.pageObject.PageFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.testng.Assert.assertTrue;

public class HomepageTesting {
    private WebDriver driver;
    private PageFactory pages;
    private MainFunction function;
    private WebDriverWait wait;

    @Given("the browser is open")
    public void theBrowserIsOpen() {
        driver = HomepageHooks.getDriver();
        pages = new PageFactory(driver);
        function = new MainFunction(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }
    @And("the user is on the Class Helper homepage")
    public void theUserIsOnTheClassHelperHomepage() {
        driver.get(ConfigURL.HOME_URL);
        driver.manage().window().maximize();
    }
    @When("the user looks at the page")
    public void userLookAtTheHomePage(){
        pages.homePage().isBodyVisible();
    }
    @Then("the user should see the needed UI elements")
    public void userSeeHomePageElement(){
        pages.homePage().isHomePageElementVisible();
    }

    @When("the user clicks the Home navigation button")
    public void userClicksHomeBtn(){
        pages.homePage().findNavBtnByText("Home").click();
    }
    @Then("user should be redirected to Home page")
    public void redirectToHomepage(){
        pages.homePage().isHomePageElementVisible();
        assertEquals(ConfigURL.HOME_URL, driver.getCurrentUrl());
    }
    @When("user clicks logo")
    public void userClicksLogo(){
        pages.homePage().clickLogo();
    }

    @When("user clicks Student List button")
    public void userClicksStudentListButton() {
        pages.homePage().findNavBtnByText("Student List").click();
    }

    @Then("user should be redirected to Student List page")
    public void userShouldBeRedirectedToStudentListPage() {
        wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("Home")));
        assertFalse(pages.homePage().isHomePageElementVisible());
        assertEquals(ConfigURL.STUDENT_LIST_URL, driver.getCurrentUrl());
    }

    @When("user clicks Quiz button")
    public void clickQuizBtn(){
        pages.homePage().findNavBtnByText("Quiz").click();
    }
    @Then("user should be redirected to Quiz page")
    public void redirectToQuizPage(){
        wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("Home")));
        assertFalse(pages.homePage().isHomePageElementVisible());
        assertEquals(ConfigURL.QUIZ_URL, driver.getCurrentUrl());
    }

    @When("user clicks Play Game button")
    public void userClicksPlayGameButton() {
        pages.homePage().findNavBtnByText("Play Game").click();
    }

    @Then("user should be redirected to Game page")
    public void userShouldBeRedirectedToGamePage() {
        wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("Home")));
        assertFalse(pages.homePage().isHomePageElementVisible());
        assertEquals(ConfigURL.NEW_GAME_URL, driver.getCurrentUrl());
    }

    @When("user clicks Functions button")
    public void userClicksFunctionsButton() {
        pages.homePage().clickFunctionsBtn();
    }

    @Then("dropdown list of functions should be displayed")
    public void dropdownListOfFunctionsShouldBeDisplayed() {
        assertTrue(pages.homePage().isDropdownFunctionsDisplayed());
    }

    @And("user clicks Add Student button")
    public void userClicksAddStudentButton() {
        pages.homePage().clickFunctionsByLinkText("Add Student");
    }

    @Then("user should be redirected to Add Student Page")
    public void userShouldBeRedirectedToAddStudentPage() {
        wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("Home")));
        assertFalse(pages.homePage().isHomePageElementVisible());
        assertEquals(ConfigURL.CREATE_STUDENT_URL, driver.getCurrentUrl());
    }

    @And("user clicks Random Student button")
    public void userClicksRandomStudentButton() {
        pages.homePage().clickFunctionsByLinkText("Random Student");
    }

    @Then("user should be redirected to Random Student Page")
    public void userShouldBeRedirectedToRandomStudentPage() {
        wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("Home")));
        assertFalse(pages.homePage().isHomePageElementVisible());
        assertEquals(ConfigURL.RANDOM_STUDENT_URL, driver.getCurrentUrl());
    }

    @And("user clicks White Board button")
    public void userClicksWhiteBoardButton() {
        pages.homePage().clickFunctionsByLinkText("White Board");
    }

    @Then("user should be redirected to White Board Page")
    public void userShouldBeRedirectedToWhiteBoardPage() {
        wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("Home")));
        assertFalse(pages.homePage().isHomePageElementVisible());
        assertEquals(ConfigURL.WHITEBOARD_URL, driver.getCurrentUrl());
    }

    @And("user clicks Clock button")
    public void userClicksClockButton() {
        pages.homePage().clickFunctionsByLinkText("Clock");
    }

    @Then("user should be redirected to Clock Page")
    public void userShouldBeRedirectedToClockPage() {
        wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("Home")));
        assertFalse(pages.homePage().isHomePageElementVisible());
        assertEquals(ConfigURL.CLOCK_URL, driver.getCurrentUrl());
    }

    @When("user scrolls to footer")
    public void userScrollsToFooter() {
        pages.homePage().scrollToFooter();
    }

    @And("user clicks Scroll to top button")
    public void userClicksScrollToTopButton() {
        pages.homePage().clickScrollToTop();
    }

    @Then("user should move to the top")
    public void userShouldMoveToTheTop() {
        assertTrue(pages.homePage().isMainBunnerTitleVisible());
    }
}
