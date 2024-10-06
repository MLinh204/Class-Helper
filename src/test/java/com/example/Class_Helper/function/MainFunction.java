package com.example.Class_Helper.function;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainFunction {
    private WebDriver driver;
    public WebDriverWait wait;
    public Actions actions;
    public MainFunction(WebDriver driver){
        this.driver = driver;
        this.actions = new Actions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    public void waitLoadingElement(){
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }
    public void forceClick(WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", element);
    }
    public void scrollIntoView(WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        jsExecutor.executeScript("arguments[0].scrollIntoView(false);", element);

        int navigationBarHeight = 100;
        int elementPosition = element.getLocation().getY();
        int viewportHeight = driver.manage().window().getSize().getHeight();

        int scrollPosition = elementPosition - (viewportHeight / 2) + (element.getSize().getHeight() / 2) + navigationBarHeight;

        jsExecutor.executeScript("window.scrollTo(0, " + scrollPosition + ");");
    }
    public void hoverOnElement(WebElement element){
        actions.moveToElement(element).perform();
    }

}
