package com.example.Class_Helper.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class StudentListObject {
    private WebDriver driver;
    private WebDriverWait wait;
    public StudentListObject(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public List<WebElement> getListStudent(){
        WebElement container = driver.findElement(By.xpath("//div[@class='row row-50']"));
        return container.findElements(By.xpath("//div[@class='col-md-6 col-lg-4 wow-outer']"));
    }
}
