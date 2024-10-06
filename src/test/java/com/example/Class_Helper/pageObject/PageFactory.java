package com.example.Class_Helper.pageObject;

import org.openqa.selenium.WebDriver;

public class PageFactory {
    private WebDriver driver;
    private HomePageObject homePage;
    private StudentListObject studentList;
    private StudentDetailObject studentDetail;

    public PageFactory(WebDriver driver) {
        this.driver = driver;
    }

    public HomePageObject homePage() {
        if (homePage == null) {
            homePage = new HomePageObject(driver);
        }
        return homePage;
    }

    public StudentListObject studentList() {
        if (studentList == null) {
            studentList = new StudentListObject(driver);
        }
        return studentList;
    }

    public StudentDetailObject studentDetail(){
        if (studentDetail == null){
            studentDetail = new StudentDetailObject(driver);
        }
        return studentDetail;
    }
}
