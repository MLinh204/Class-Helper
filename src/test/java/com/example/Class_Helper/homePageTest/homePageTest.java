package com.example.Class_Helper.homePageTest;

import com.example.Class_Helper.pageObject.ConfigProperties;
import com.example.Class_Helper.pageObject.HomePageObject;
import com.example.Class_Helper.pages.MainFunction;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class homePageTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private HomePageObject object;
    private MainFunction function;
    private Actions actions;

    @BeforeClass
    public void setup(){
        WebDriverManager.firefoxdriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver();
        object = new HomePageObject(driver);
        function = new MainFunction(driver);
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().window().maximize();
    }
    @AfterClass
    public void tearDown(){
        if (driver!=null){
            driver.quit();
        }
    }

    //Home Page Accessing
    @org.testng.annotations.Test
    public void HomePageAccessing(){
        driver.get(ConfigProperties.HOME_URL);
        assertEquals("http://localhost:8080/home", driver.getCurrentUrl());
    }

    //Home Page UI check
    @Test
    public void UICheck(){
        driver.get(ConfigProperties.HOME_URL);
        wait.until(ExpectedConditions.urlToBe(ConfigProperties.HOME_URL));
        function.waitLoadingElement();
        assertTrue(object.getLogo().isDisplayed(), "Logo should be displayed");
        assertTrue(object.findNavBtnByText("Home").isDisplayed(), "Home button should be displayed");
        assertTrue(object.findNavBtnByText("Student List").isDisplayed(), "Student List button should be displayed");
        assertTrue(object.findNavBtnByText("Quiz").isDisplayed(), "Quiz button should be displayed");
        assertTrue(object.findNavBtnByText("Play Game").isDisplayed(), "Play Game button should be displayed");
        assertTrue(object.getFunctionButton().isDisplayed(), "Functions button should be displayed");
        assertTrue(object.getFooter().isDisplayed(), "Footer should be displayed");
    }

    //Select Logo
    @Test
    public void selectLogo(){
        driver.get(ConfigProperties.HOME_URL);
        wait.until(ExpectedConditions.urlToBe(ConfigProperties.HOME_URL));
        function.waitLoadingElement();
        object.getLogo().click();

        assertEquals("http://localhost:8080/home", driver.getCurrentUrl());
    }

    //Select Home button
    @Test
    public void selectHomeBtn(){
        driver.get(ConfigProperties.HOME_URL);
        wait.until(ExpectedConditions.urlToBe(ConfigProperties.HOME_URL));
        function.waitLoadingElement();
        object.findNavBtnByText("Home").click();
        assertEquals("http://localhost:8080/home", driver.getCurrentUrl());
    }

    //Select Student List button
    @Test
    public void selectStudentListBtn() {
        driver.get(ConfigProperties.HOME_URL);
        wait.until(ExpectedConditions.urlToBe(ConfigProperties.HOME_URL));
        function.waitLoadingElement();
        object.findNavBtnByText("Student List").click();
        wait.until(ExpectedConditions.titleIs("Student List"));
        assertEquals("http://localhost:8080/students", driver.getCurrentUrl());
    }

    //Select Quiz button
    @Test
    public void selectQuizBtn() {
        driver.get(ConfigProperties.HOME_URL);
        wait.until(ExpectedConditions.urlToBe(ConfigProperties.HOME_URL));
        function.waitLoadingElement();
        object.findNavBtnByText("Quiz").click();
        wait.until(ExpectedConditions.titleIs("Quizzes"));
        assertEquals(ConfigProperties.QUIZ_URL, driver.getCurrentUrl());
    }

    //Select Play Game button
    @Test
    public void selectPlayGameBtn() {
        driver.get(ConfigProperties.HOME_URL);
        wait.until(ExpectedConditions.urlToBe(ConfigProperties.HOME_URL));
        function.waitLoadingElement();
        object.findNavBtnByText("Play Game").click();
        wait.until(ExpectedConditions.titleIs("New Tic-Tac-Toe Quiz Game"));
        assertEquals(ConfigProperties.NEW_GAME_URL, driver.getCurrentUrl());
    }

    //Select Functions button
    @Test
    public void selectFunctionsBtn(){
        driver.get(ConfigProperties.HOME_URL);
        wait.until(ExpectedConditions.urlToBe(ConfigProperties.HOME_URL));
        function.waitLoadingElement();
        function.forceClick(object.getFunctionButton());
        assertTrue(object.findFunctionBtnByText("Add Student").isDisplayed());
        assertTrue(object.findFunctionBtnByText("Random Student").isDisplayed());
        assertTrue(object.findFunctionBtnByText("White Board").isDisplayed());
        assertTrue(object.findFunctionBtnByText("Clock").isDisplayed());
    }

    //Select Add Student button
    @Test
    public void selectAddStudentBtn() throws InterruptedException {
        driver.get(ConfigProperties.HOME_URL);
        wait.until(ExpectedConditions.urlToBe(ConfigProperties.HOME_URL));
        function.waitLoadingElement();
        function.forceClick(object.getFunctionButton());
        wait.until(ExpectedConditions.visibilityOf(object.findFunctionBtnByText("Add Student")));
        function.forceClick(object.findFunctionBtnByText("Add Student"));
        Thread.sleep(2000);
        assertEquals(ConfigProperties.CREATE_STUDENT_URL, driver.getCurrentUrl());
    }

    //Select Random Student button
    @Test
    public void selectRandomBtn() throws InterruptedException {
        driver.get(ConfigProperties.HOME_URL);
        wait.until(ExpectedConditions.urlToBe(ConfigProperties.HOME_URL));
        function.waitLoadingElement();
        function.forceClick(object.getFunctionButton());
        wait.until(ExpectedConditions.visibilityOf(object.findFunctionBtnByText("Random Student")));
        function.forceClick(object.findFunctionBtnByText("Random Student"));
        Thread.sleep(2000);
        assertEquals(ConfigProperties.RANDOM_STUDENT_URL, driver.getCurrentUrl());
    }

    ////Select White Board button
    @Test
    public void selectWhiteBoardBtn() throws InterruptedException {
        driver.get(ConfigProperties.HOME_URL);
        wait.until(ExpectedConditions.urlToBe(ConfigProperties.HOME_URL));
        function.waitLoadingElement();
        function.forceClick(object.getFunctionButton());
        wait.until(ExpectedConditions.visibilityOf(object.findFunctionBtnByText("White Board")));
        function.forceClick(object.findFunctionBtnByText("White Board"));
        Thread.sleep(2000);
        assertEquals(ConfigProperties.WHITEBOARD_URL, driver.getCurrentUrl());
    }

    ////Select Clock button
    @Test
    public void selectClockBtn() throws InterruptedException {
        driver.get(ConfigProperties.HOME_URL);
        wait.until(ExpectedConditions.urlToBe(ConfigProperties.HOME_URL));
        function.waitLoadingElement();
        function.forceClick(object.getFunctionButton());
        wait.until(ExpectedConditions.visibilityOf(object.findFunctionBtnByText("Clock")));
        function.forceClick(object.findFunctionBtnByText("Clock"));
        Thread.sleep(2000);
        assertEquals(ConfigProperties.CLOCK_URL, driver.getCurrentUrl());
    }

    //Select Scroll to top button
    @Test
    public void selectScrollToTopBtn() throws InterruptedException {
        driver.get(ConfigProperties.HOME_URL);
        wait.until(ExpectedConditions.urlToBe(ConfigProperties.HOME_URL));
        function.waitLoadingElement();
        object.getFooter();
        Thread.sleep(500);
        object.scrollToTopBtn().click();
        assertTrue(object.getHeader().isDisplayed());
    }
}
