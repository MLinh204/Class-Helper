package com.example.Class_Helper;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class homePageTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setup(){
        WebDriverManager.firefoxdriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver();
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
        driver.get("http://localhost:8080/home");
        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/home"));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
}
