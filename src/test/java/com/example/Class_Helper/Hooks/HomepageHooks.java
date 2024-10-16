package com.example.Class_Helper.Hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class HomepageHooks {
    private static WebDriver driver;

    @Before("@Homepage")
    public void setUp() {
        SharedWebDriver.getDriver();
    }

    @After("@Homepage")
    public void tearDown() {
        SharedWebDriver.quitDriver();
    }

    public static WebDriver getDriver() {
        return SharedWebDriver.getDriver();
    }
}
