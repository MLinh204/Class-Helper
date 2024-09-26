package com.example.Class_Helper.studentListTest;

import com.example.Class_Helper.pageObject.ConfigProperties;
import com.example.Class_Helper.pageObject.HomePageObject;
import com.example.Class_Helper.pageObject.StudentListObject;
import com.example.Class_Helper.pages.MainFunction;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentListTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private StudentListObject object;
    private MainFunction function;
    private Actions actions;

    @BeforeClass
    public void setup(){
        WebDriverManager.firefoxdriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver();
        object = new StudentListObject(driver);
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

    @Test
    public void checkTitle() {
        driver.get(ConfigProperties.STUDENT_LIST_URL);
        function.waitLoadingElement();
        System.out.println("Title: " + driver.getTitle());
        assertEquals("Student List", driver.getTitle());
    }
    @Test
    public void checkUI(){
        driver.get(ConfigProperties.STUDENT_LIST_URL);
        function.waitLoadingElement();
        assertEquals("Student List", object.getH3().getText());
        assertEquals(3, object.getListStudent().size());
    }
    @Test
    public void checkStudentElement() throws InterruptedException {
        driver.get(ConfigProperties.STUDENT_LIST_URL);
        for (WebElement student : object.getListStudent()){
            WebElement studentName = student.findElement(By.xpath(".//h4//a"));
            WebElement profilePicture = student.findElement(By.xpath(".//img"));
            WebElement studentPower = student.findElement(By.xpath(".//p"));
            Thread.sleep(500);
            assertTrue(studentName.isDisplayed(), "Student name should be displayed");
            System.out.println("Student name: " + studentName.getText());
            assertTrue(profilePicture.isDisplayed(), "profile picture should be displayed");
            System.out.println("Profile tag: " + profilePicture.getTagName());
            assertTrue(studentPower.isDisplayed(), "Power should be displayed");
            System.out.println("Power: " + studentPower.getText());
        }
    }
    @Test
    public void checkDisplayOfViewBtn() throws InterruptedException {
        driver.get(ConfigProperties.STUDENT_LIST_URL);
        for (WebElement student : object.getListStudent()){
            WebElement viewBtn = student.findElement(By.xpath(".//a[@class='button button-sm button-primary']"));
            actions.moveToElement(viewBtn).perform();
            Thread.sleep(1000);
            assertTrue(viewBtn.isDisplayed(), "View button should be displayed");
        }
    }
    @Test
    public void selectViewBtn() throws InterruptedException {
        driver.get(ConfigProperties.STUDENT_LIST_URL);
        WebElement viewBtn = object.getViewButton("Cat");
        actions.moveToElement(viewBtn).perform();
        wait.until(ExpectedConditions.visibilityOf(viewBtn));
        function.forceClick(viewBtn);
        wait.until(ExpectedConditions.invisibilityOf(viewBtn));
        assertEquals("Home", driver.getTitle());
    }
}
