package com.example.Class_Helper.pageObject;

import com.example.Class_Helper.Hooks.StudentListHooks;
import com.example.Class_Helper.config.ConfigElement;
import com.example.Class_Helper.function.MainFunction;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class StudentListObject {
    private WebDriver driver;
    private WebDriverWait wait;
    private MainFunction function;
    private Actions actions;

    @FindBy(xpath = "//div[@class='row row-50']//div[@class='col-md-6 col-lg-4 wow-outer']")
    private List<WebElement> listStudent;

    @FindBy(xpath = "//h3")
    private WebElement titleBunner;


    public StudentListObject(WebDriver driver){
        this.driver = driver;
        this.function = new MainFunction(driver);
        this.actions = new Actions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
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

    public boolean isStudentListElementDisplayed(){
        for (WebElement student : listStudent){
            try {
                WebElement studentName = wait.until(ExpectedConditions.visibilityOf(student.findElement(By.xpath(".//h4//a"))));
                WebElement profilePicture = wait.until(ExpectedConditions.visibilityOf(student.findElement(By.xpath(".//img"))));
                WebElement studentPower = wait.until(ExpectedConditions.visibilityOf(student.findElement(By.xpath(".//p"))));
                boolean isStudentNameDisplayed = studentName.isDisplayed();
                boolean isProPicDisplayed = profilePicture.isDisplayed();
                boolean isPowerDisplayed = studentPower.isDisplayed();
                return isStudentNameDisplayed && isProPicDisplayed && isPowerDisplayed;
            } catch (NoSuchElementException | TimeoutException e){
                return false;
            }
        }
        return true;
    }

    public boolean isViewBtnDisplayed(){
        for(WebElement student : listStudent){
            try {
                WebElement viewBtn = student.findElement(By.xpath(".//a[@class='button button-sm button-primary']"));
                actions.moveToElement(viewBtn).perform();
                wait.until(ExpectedConditions.visibilityOf(viewBtn));
                return viewBtn.isDisplayed();
            } catch (NoSuchElementException | TimeoutException e){
                return false;
            }
        }
        return true;
    }

    public boolean isNewStudentCreated(String studentName, String powerType){
        try{
            WebElement name = wait.until(ExpectedConditions.visibilityOf(getStudentName(studentName)));
            WebElement power = wait.until(ExpectedConditions.visibilityOf(getStudentPower(studentName)));
            boolean isNameCorrect = name.getText().equalsIgnoreCase(studentName);
            boolean isPowerTypeCorrect = power.getText().equalsIgnoreCase(powerType);
            return isNameCorrect && isPowerTypeCorrect;
        } catch (NoSuchElementException | TimeoutException e){
            return false;
        }
    }

    public List<WebElement> getListStudent() {
        return listStudent;
    }

    public WebElement getTitleBunner() {
        return wait.until(ExpectedConditions.visibilityOf(titleBunner));
    }
}
