package com.example.Class_Helper.Hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class HomepageHooks {
    private static WebDriver driver;

    @Before("@Homepage")
    public void setUp(Scenario scenario) {
        if (driver == null) {
            driver = new ChromeDriver();
        }
    }

    @After("@Homepage")
    public void tearDown(Scenario scenario) {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
