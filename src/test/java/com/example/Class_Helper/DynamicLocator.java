package com.example.Class_Helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DynamicLocator {
    private WebDriver driver;
    public DynamicLocator(WebDriver driver){
        this.driver = driver;
    }
    public WebElement findButtonByText(String text){
        String xpathFormat = "//button[contains(text(), '%s')]";
        String xpath = String.format(xpathFormat, text);
        return driver.findElement(By.xpath(xpath));
    }
}
