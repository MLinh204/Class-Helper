package com.example.Class_Helper.studentListTest;

import com.example.Class_Helper.pageObject.ConfigProperties;
import com.example.Class_Helper.pageObject.HomePageObject;
import com.example.Class_Helper.pageObject.StudentListObject;
import com.example.Class_Helper.pages.MainFunction;
import io.github.bonigarcia.wdm.WebDriverManager;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void getChildrenByName() throws InterruptedException {
        driver.get(ConfigProperties.STUDENT_LIST_URL);
        function.waitLoadingElement();

        WebElement studentDiv = object.findStudentByName("Trang");
        WebElement studentName = object.getStudentName("Trang");
        WebElement studentPower = object.getStudentPower("Ngoc");
        WebElement profilePicture = object.getProfilePicture("Ngoc");
        actions.moveToElement(profilePicture).perform();
        Thread.sleep(500);
        function.forceClick(object.getViewButton("Ngoc"));
        Thread.sleep(5000);
    }
}
