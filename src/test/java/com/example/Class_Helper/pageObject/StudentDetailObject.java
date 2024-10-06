package com.example.Class_Helper.pageObject;

import com.example.Class_Helper.function.MainFunction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class StudentDetailObject {
    private WebDriver driver;
    private WebDriverWait wait;
    private MainFunction function;
    public StudentDetailObject(WebDriver driver){
        this.driver = driver;
        this.function = new MainFunction(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class='row g-0']")
    private WebElement inforCard;

    public WebElement getInforCard() {
        return wait.until(ExpectedConditions.visibilityOf(inforCard));
    }
}
