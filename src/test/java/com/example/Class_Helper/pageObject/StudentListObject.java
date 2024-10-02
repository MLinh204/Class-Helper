package com.example.Class_Helper.pageObject;

import com.example.Class_Helper.function.MainFunction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class StudentListObject {
    private WebDriver driver;
    private WebDriverWait wait;
    private MainFunction function;
    private Actions actions;


    public StudentListObject(WebDriver driver){
        this.driver = driver;
        this.function = new MainFunction(driver);
        this.actions = new Actions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public List<WebElement> getListStudent(){
        WebElement container = driver.findElement(By.xpath("//div[@class='row row-50']"));
        return container.findElements(By.xpath("//div[@class='col-md-6 col-lg-4 wow-outer']"));
    }
    public WebElement findStudentByName(String name){
        WebElement container = driver.findElement(By.xpath("//div[@class='row row-50']"));
        String xpath = String.format("//div[@class='col-md-6 col-lg-4 wow-outer'][.//a[text()='%s']]", name);
        WebElement elementName = container.findElement(By.xpath(xpath));
        return wait.until(ExpectedConditions.visibilityOf(elementName));
    }
    public WebElement getStudentName(String name){
        WebElement container = findStudentByName(name);
        String xpath = String.format("//a[text()='%s']",name);
        WebElement studentName = container.findElement(By.xpath(xpath));
        return wait.until(ExpectedConditions.visibilityOf(studentName));
    }
    public WebElement getStudentPower(String name){
        WebElement container = findStudentByName(name);
        WebElement studentPower = container.findElement(By.xpath(".//p[@class='box-causes-donate']"));
        return wait.until(ExpectedConditions.visibilityOf(studentPower));
    }
    public WebElement getProfilePicture(String name){
        WebElement container = findStudentByName(name);
        WebElement profilePicture = container.findElement(By.xpath(".//div[@class='box-causes-img']"));
        return wait.until(ExpectedConditions.visibilityOf(profilePicture));
    }
    public WebElement getViewButton(String name){
        WebElement container = findStudentByName(name);
        WebElement viewBtn = container.findElement(By.xpath(".//a[@class='button button-sm button-primary']"));
        function.scrollIntoView(viewBtn);
        actions.moveToElement(viewBtn).perform();
        wait.until(ExpectedConditions.visibilityOf(viewBtn));
        return viewBtn;
    }
    public WebElement getH3(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
    }
}
