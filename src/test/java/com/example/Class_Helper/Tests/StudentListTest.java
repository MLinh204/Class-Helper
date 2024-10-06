package com.example.Class_Helper.Tests;

import com.example.Class_Helper.Hooks.StudentListHooks;
import com.example.Class_Helper.config.ConfigElement;
import com.example.Class_Helper.config.ConfigURL;
import com.example.Class_Helper.pageObject.PageFactory;
import com.example.Class_Helper.pageObject.StudentListObject;
import com.example.Class_Helper.function.MainFunction;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentListTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private PageFactory pages;
    private MainFunction function;

    @Given("the browser is opened")
    public void theBrowserIsOpen() {
        driver = StudentListHooks.getDriver();
        pages = new PageFactory(driver);
        function = new MainFunction(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @And("the user is on the Student List page")
    public void theUserIsOnTheStudentListPage() {
        driver.get(ConfigURL.STUDENT_LIST_URL);
        driver.manage().window().maximize();
    }

    @When("the user looks at the student list page")
    public void theUserLooksAtTheStudentListPage() {
        assertTrue(pages.studentList().getTitleBunner().isDisplayed());
    }

    @Then("the title should be Student List")
    public void theTitleShouldBeStudentList() {
        assertEquals("Student List", driver.getTitle());
    }

    @Then("the user should see the needed student list UI elements")
    public void checkUIElements(){
        assertTrue(pages.studentList().isStudentListElementDisplayed());
    }

    @Then("the View buttons should display")
    public void theViewButtonShouldDisplay() {
        assertTrue(pages.studentList().isViewBtnDisplayed());
    }

    @When("the user hovers on each student")
    public void theUserHoversOnEachStudent() {
        System.out.println("Start hovering each student...");
    }

    @When("user hovers on the student")
    public void userHoversOnTheStudent() {
        function.hoverOnElement(pages.studentList().getViewButton(ConfigElement.DEFINED_TEST_USER));
    }

    @Then("View button should be displayed")
    public void viewButtonShouldBeDisplayed() {
        assertTrue(pages.studentList().getViewButton(ConfigElement.DEFINED_TEST_USER).isDisplayed());
    }

    @And("user clicks View button")
    public void userClicksViewButton() {
        function.forceClick(pages.studentList().getViewButton(ConfigElement.DEFINED_TEST_USER));
    }

    @Then("user should be redirected to student detail page")
    public void userShouldBeRedirectedToStudentDetailPage() {
        assertTrue(pages.studentDetail().getInforCard().isDisplayed());
    }

    @When("user clicks the name of the student")
    public void userClicksTheNameOfTheStudent() {
        pages.studentList().getStudentName(ConfigElement.DEFINED_TEST_USER).click();
    }
}

