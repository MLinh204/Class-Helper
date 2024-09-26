package com.example.Class_Helper.pageObject;

import com.example.Class_Helper.pages.MainFunction;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePageObject {
    private WebDriver driver;
    private WebDriverWait wait;
    private MainFunction function;
    public HomePageObject(WebDriver driver){
        this.driver = driver;
        this.function = new MainFunction(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebElement getLogo(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@class='brand-logo-light']")));
    }
    public WebElement findNavBtnByText(String text){
        String xpath = String.format("//a[@class='rd-nav-link' and text()='%s']", text);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }
    public WebElement getFunctionButton(){
        WebElement functions = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div//button[@class='button button-primary button-sm']")));
        function.scrollIntoView(functions);
        return functions;
    }
    public WebElement getFooter(){
        WebElement footer = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//footer[@class='section footer-minimal context-dark']")));
        function.scrollIntoView(footer);
        return footer;
    }

    public WebElement findFunctionBtnByText(String text){
        String xpath = String.format("//ul//a[@class='button button-primary button-sm' and text()='%s']", text);
        WebElement functionBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        return functionBtn;
    }

    public WebElement scrollToTopBtn(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='ui-to-top']")));
    }
    public WebElement getHeader(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//header")));
    }
}
