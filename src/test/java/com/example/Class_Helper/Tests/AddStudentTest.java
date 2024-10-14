package com.example.Class_Helper.Tests;

import com.example.Class_Helper.Hooks.AddStudentHooks;
import com.example.Class_Helper.Hooks.HomepageHooks;
import com.example.Class_Helper.config.ConfigElement;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddStudentTest {
    private WebDriver driver;
    private PageFactory pages;
    private MainFunction function;
    private WebDriverWait wait;

    @Given("the browser is opened for Create Student")
    public void theBrowserIsOpen() {
        driver = AddStudentHooks.getDriver();
        pages = new PageFactory(driver);
        function = new MainFunction(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @And("the user is on the Create Student page")
    public void theUserIsOnTheCreateStudentPage() {
        driver.get(ConfigURL.CREATE_STUDENT_URL);
        driver.manage().window().maximize();
    }

    @When("user looks at the create student page")
    public void userLooksAtTheCreateStudentPage() {
        assertTrue(driver.getTitle().contains("Add Student"));
    }

    @Then("the user should see the needed create student UI elements")
    public void theUserShouldSeeTheNeededCreateStudentUIElements() {
        assertTrue(pages.addStudentObject().isCreateStudentVisible());
    }

    @When("user clicks Return To Student List button")
    public void userClicksReturnToStudentListButton() {
        pages.addStudentObject().getReturnToStudentListBtn().click();
    }

    @Then("system redirects to Student List page")
    public void systemRedirectsToStudentListPage() {
        assertTrue(wait.until(ExpectedConditions.visibilityOf(pages.studentList().getTitleBunner())).isDisplayed());
    }

    @When("user fills in Name")
    public void userFillsInName() {
        pages.addStudentObject().getInputElement("name").sendKeys(ConfigElement.TEST_USER);
    }

    @And("user chooses Profile picture")
    public void userChoosesProfilePicture() {
        pages.addStudentObject().getChooseFileBtn().sendKeys(ConfigElement.UPLOAD_FILE_PATH);
    }

    @And("user chooses power type")
    public void userChoosesPowerType() {
        pages.addStudentObject().selectOption(ConfigElement.POWER_TYPE);
    }

    @And("user clicks Save button")
    public void userClicksSaveButton() {
        pages.addStudentObject().getSubmitBtn().click();
    }

    @Then("new student should be created")
    public void newStudentShouldBeCreated() {
        assertTrue(pages.studentList().isNewStudentCreated(ConfigElement.TEST_USER, ConfigElement.POWER_TYPE));
    }
}
