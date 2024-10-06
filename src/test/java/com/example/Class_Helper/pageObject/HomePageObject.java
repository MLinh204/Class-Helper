package com.example.Class_Helper.pageObject;

import com.example.Class_Helper.function.MainFunction;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class HomePageObject {
    private WebDriver driver;
    private WebDriverWait wait;
    private MainFunction function;
    public HomePageObject(WebDriver driver){
        this.driver = driver;
        this.function = new MainFunction(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//img[@class='brand-logo-light']")
    private WebElement logo;

    @FindBy(xpath = "//div//button[@class='button button-primary button-sm']")
    private WebElement functionsBtn;

    @FindBy(xpath = "//footer[@class='section footer-minimal context-dark']")
    private WebElement footer;

    @FindBy(xpath = "//a[@id='ui-to-top']")
    private WebElement scrollToTopBtn;

    @FindBy(xpath = "//header")
    private WebElement header;

    @FindBy (xpath = "//h1[@class='main-bunner-title']")
    private WebElement mainBunnerTitle;

    public WebElement findNavBtnByText(String text){
        String xpath = String.format("//a[@class='rd-nav-link' and text()='%s']", text);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public WebElement findFunctionBtnByText(String text){
        String xpath = String.format("//ul//a[@class='button button-primary button-sm' and text()='%s']", text);
        WebElement functionBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        return functionBtn;
    }
    public boolean isHomePageElementVisible(){
        try {
            boolean title = driver.getTitle().equalsIgnoreCase("Home");
            boolean isLogoDisplayed = logo.isDisplayed();
            boolean isHomeBtnDisplayed = findNavBtnByText("Home").isDisplayed();
            boolean isStudentListBtnDisplayed = findNavBtnByText("Student List").isDisplayed();
            boolean isQuizBtnDisplayed = findNavBtnByText("Quiz").isDisplayed();
            boolean isPlayGameBtnDisplayed = findNavBtnByText("Play Game").isDisplayed();
            boolean isFunctionListDisplayed = functionsBtn.isDisplayed();

            boolean isBodyElementDisplayed = isLogoDisplayed && isHomeBtnDisplayed && isStudentListBtnDisplayed && isQuizBtnDisplayed && isPlayGameBtnDisplayed && isFunctionListDisplayed;
            function.scrollIntoView(footer);
            boolean isFooterDisplayed = footer.isDisplayed();

            return title && isBodyElementDisplayed && isFooterDisplayed;
        } catch (NoSuchElementException | TimeoutException e){
            return false;
        }

    }
    public boolean isBodyVisible(){
        return driver.findElement(By.xpath("//body")).isDisplayed();
    }
    public void clickLogo(){
        logo.click();
    }

    public boolean isDropdownFunctionsDisplayed(){
        try {
            boolean isAddStudentDisplayed = findFunctionBtnByText("Add Student").isDisplayed();
            boolean isRandomStudentDisplayed = findFunctionBtnByText("Random Student").isDisplayed();
            boolean isWhiteBoardDisplayed = findFunctionBtnByText("White Board").isDisplayed();
            boolean isClockDisplayed = findFunctionBtnByText("Clock").isDisplayed();
            return isClockDisplayed && isAddStudentDisplayed && isWhiteBoardDisplayed && isRandomStudentDisplayed;
        } catch (NoSuchElementException | TimeoutException e){
            return false;
        }
    }
    public void clickFunctionsBtn(){
        function.forceClick(functionsBtn);
    }

    public void clickFunctionsByLinkText(String linkText){
        WebElement functionBtn = findFunctionBtnByText(linkText);
        function.forceClick(functionBtn);
    }
    public boolean isMainBunnerTitleVisible(){
        WebElement mainTitle = wait.until(ExpectedConditions.visibilityOf(mainBunnerTitle));
        return mainTitle.isDisplayed();
    }
    public void scrollToFooter(){
        function.scrollIntoView(footer);
    }
    public void clickScrollToTop(){
        function.forceClick(scrollToTopBtn);
    }
}
