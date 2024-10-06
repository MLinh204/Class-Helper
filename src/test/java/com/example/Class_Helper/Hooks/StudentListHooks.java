package com.example.Class_Helper.Hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class StudentListHooks {
    private static WebDriver driver;

    @Before("@StudentList")
    public void setUp(Scenario scenario) {
        if (driver == null) {
            driver = new ChromeDriver();
        }
    }

    @After("@StudentList")
    public void tearDown(Scenario scenario) {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            driver = new ChromeDriver();
        }
        return driver;
    }
}
