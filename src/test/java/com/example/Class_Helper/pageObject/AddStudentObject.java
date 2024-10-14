package com.example.Class_Helper.pageObject;

import org.hibernate.annotations.processing.Find;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddStudentObject {
    private WebDriver driver;
    private WebDriverWait wait;
    @FindBy(xpath = "//div[@class='form-container']")
    private WebElement createForm;
    @FindBy(xpath = "//div[@class='form-container']//select")
    private WebElement powerOption;
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitBtn;
    @FindBy(xpath = "(//a[@class='button button-primary button-sm'])[2]")
    private WebElement returnToStudentListBtn;
    @FindBy(xpath = "//input[@type='file']")
    private WebElement chooseFileBtn;
    public AddStudentObject(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    public WebElement getInputElement(String name){
        String xpath = String.format("//div[@class='form-container']//input[@name='%s']", name);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }
    public void selectOption(String option){
        WebElement nameElement = powerOption;
        Select select = new Select(nameElement);
        select.selectByVisibleText(option);
    }
    public boolean isCreateStudentVisible() {
        try {
            boolean isPageVisible = createForm.isDisplayed() && returnToStudentListBtn.isDisplayed();
        } catch (NoSuchElementException e) {
            System.out.println("Not found element");
            return false;
        } catch (TimeoutException e) {
            System.out.println("Time out");
            return false;
        }
        return true;
    }

    public WebElement getReturnToStudentListBtn() {
        return returnToStudentListBtn;
    }

    public WebElement getSubmitBtn() {
        return submitBtn;
    }

    public WebElement getPowerOption() {
        return powerOption;
    }

    public WebElement getChooseFileBtn() {
        return chooseFileBtn;
    }
}
