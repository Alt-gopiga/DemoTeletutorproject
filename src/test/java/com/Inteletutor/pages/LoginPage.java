package com.Inteletutor.pages;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.Inteletutor.wrappers.BaseClassWrappers;
import com.relevantcodes.extentreports.ExtentTest;

public class LoginPage extends BaseClassWrappers{

	public LoginPage (RemoteWebDriver driver, ExtentTest test){

		this.driver=driver;
		this.test=test;
		loadObjects();
	}

	public LoginPage clickCreateAccount(){
		ClickByCssSelector(prop.getProperty("Login.Click.CreateAccount.CSS"),"Create Account");
		return this;
	}

	public TutorRegisterPage clickTutor(){
		MouseHoverClickByXpath(prop.getProperty("Login.Click.Tutor.Xpath"));
		return new TutorRegisterPage(driver, test);
	}

	public LoginPage clickStudentAndParent(){
		ClickByCssSelector(prop.getProperty("Login.Click.StudentStudent.Xpath"), "Student And Parent");
		return this;
	}
}
