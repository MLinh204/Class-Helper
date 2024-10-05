package com.example.Class_Helper.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddStudentObject {
    private WebDriver driver;
    private WebDriverWait wait;
    public AddStudentObject(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public WebElement getFormContainer(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='form-container']")));
    }
    public WebElement getInputElement(String name){
        String xpath = String.format("//div[@class='form-container']//input[@name='%s']", name);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }
    public WebElement getPowerOptions(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='form-container']//select")));
    }
    public WebElement getSaveButton(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type='submit']")));
    }
    public void selectOption(String inputName, String option){
        WebElement nameElement = getPowerOptions();
        Select select = new Select(nameElement);
        select.selectByVisibleText(option);
    }
}
