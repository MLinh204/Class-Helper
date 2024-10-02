package com.example.Class_Helper.pageObject;

import com.example.Class_Helper.function.MainFunction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class HomePageObject {
    private WebDriver driver;
    private WebDriverWait wait;
    private MainFunction function;
    private SoftAssert softAssert;
    public HomePageObject(WebDriver driver){
        this.driver = driver;
        this.function = new MainFunction(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.softAssert = new SoftAssert();
    }
    @FindBy(xpath = "//img[@class='brand-logo-light']")
    private WebElement logo;

    @FindBy(xpath = "//div//button[@class='button button-primary button-sm']")
    private WebElement functionList;

    @FindBy(xpath = "//footer[@class='section footer-minimal context-dark']")
    private WebElement footer;

    @FindBy(xpath = "//a[@id='ui-to-top']")
    private WebElement scrollToTopBtn;

    @FindBy(xpath = "//header")
    private WebElement header;

    public WebElement findNavBtnByText(String text){
        String xpath = String.format("//a[@class='rd-nav-link' and text()='%s']", text);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public WebElement findFunctionBtnByText(String text){
        String xpath = String.format("//ul//a[@class='button button-primary button-sm' and text()='%s']", text);
        WebElement functionBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        return functionBtn;
    }
    public void isHomePageElementVisible(){
        softAssert.assertTrue(logo.isDisplayed());
        softAssert.assertTrue(findNavBtnByText("Home").isDisplayed());
        softAssert.assertTrue(findNavBtnByText("Student List").isDisplayed());
        softAssert.assertTrue(findNavBtnByText("Quiz").isDisplayed());
        softAssert.assertTrue(findNavBtnByText("Play Game").isDisplayed());
        softAssert.assertTrue(functionList.isDisplayed());
        function.scrollIntoView(footer);
        softAssert.assertTrue(footer.isDisplayed());
    }
}
