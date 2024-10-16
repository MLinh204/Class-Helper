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
        SharedWebDriver.getDriver();
    }

    @After("@AddStudent")
    public void tearDown(Scenario scenario) {
        SharedWebDriver.quitDriver();
    }

    public static WebDriver getDriver() {
        return SharedWebDriver.getDriver();
    }
}
