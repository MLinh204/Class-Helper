package com.example.Class_Helper;

import com.example.Class_Helper.pageObject.ConfigProperties;
import com.example.Class_Helper.pageObject.HomePageObject;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class homePageTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private HomePageObject object;

    @BeforeClass
    public void setup(){
        WebDriverManager.firefoxdriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver();
        object = new HomePageObject(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().window().maximize();
    }
    @AfterClass
    public void tearDown(){
        if (driver!=null){
            driver.quit();
        }
    }
    @org.testng.annotations.Test
    public void TestNavigation(){
        driver.get(ConfigProperties.HOME_URL);
        wait.until(ExpectedConditions.urlToBe(ConfigProperties.HOME_URL));
        WebElement homeNav = object.findNavBtnByText("Home");
        System.out.println(homeNav.getTagName());
        System.out.println("Element class: " + homeNav.getAttribute("class"));
        System.out.println("Element href: " + homeNav.getAttribute("href"));
        System.out.println("Element text:" + homeNav.getText());
        assertEquals("home", homeNav.getText().toLowerCase());
    }
}
