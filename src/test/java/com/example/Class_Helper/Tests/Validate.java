package com.example.Class_Helper.Tests;

import com.example.Class_Helper.config.ConfigProperties;
import com.example.Class_Helper.function.MainFunction;
import com.example.Class_Helper.pageObject.HomePageObject;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.asserts.SoftAssert;

public class Validate {
    private WebDriver driver;
    private HomePageObject object;
    private MainFunction function;

    @Given("The homepage is accessible")
    public void accessHomepage(){
        driver = new ChromeDriver();
        object = new HomePageObject(driver);
        function = new MainFunction(driver);
        driver.get(ConfigProperties.HOME_URL);

    }
    @Given(": The user is on the homepage")
    public void waitElementLoaded(){
        function.waitLoadingElement();
    }
    @Then(": The user should see the needed UI element")
    public void checkUIElement(){
        object.isHomePageElementVisible();
    }
}
