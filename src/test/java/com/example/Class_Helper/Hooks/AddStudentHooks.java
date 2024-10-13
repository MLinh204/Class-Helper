package com.example.Class_Helper.Hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class AddStudentHooks {
    private static WebDriver driver;

    @Before("@AddStudent")
    public void setUp(Scenario scenario) {
        if (driver == null) {
            driver = new ChromeDriver();
        }
    }

    @After("@AddStudent")
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
