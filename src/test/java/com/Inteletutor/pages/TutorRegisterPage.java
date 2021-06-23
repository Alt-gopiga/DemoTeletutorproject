package com.Inteletutor.pages;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.Inteletutor.wrappers.BaseClassWrappers;
import com.relevantcodes.extentreports.ExtentTest;

public class TutorRegisterPage extends BaseClassWrappers{

	public TutorRegisterPage (RemoteWebDriver driver, ExtentTest test){

		this.driver=driver;
		this.test=test;
		loadObjects();
	}

	public TutorRegisterPage enterFName(String FName){
		EnterByCssSelector(prop.getProperty("TutorReg.Enter.FirstName.CSS"), FName, "First Name");
		return this;
	}

	public TutorRegisterPage enterLName(String LName){

		EnterByCssSelector(prop.getProperty("TutorReg.Enter.LastName.CSS"), LName, "Last Name");
		return this;
	}

	public TutorRegisterPage enterEmail(String Email){

		EnterByCssSelector(prop.getProperty("TutorReg.Enter.Email.CSS"), Email, "Email");
		return this;
	}

	public TutorRegisterPage clickGender(){
		ClickByXpath(prop.getProperty("TutorReg.Radio.Male.Xpath"), "Gender");
		return this;
	}

	public TutorRegisterPage enterPassWord(String Password){
		EnterByCssSelector(prop.getProperty("TutorReg.Enter.password.CSS"), Password, "Password");
		return this;
	}

	public TutorRegisterPage enterConfirmPassword(String ConfirmPassword){
		EnterByCssSelector(prop.getProperty("TutorReg.Enter.confirmPassword.CSS"), ConfirmPassword, "Confirm Password");
		return this;
	}

	public TutorRegisterPage clickCountryCode(){
		ClickByXpath(prop.getProperty("TutorReg.Click.CountryCode.Xpath"), "Country Code");
		return this;
	}

	public TutorRegisterPage clickCountryIndia(){
		ClickByCssSelector(prop.getProperty("TutorReg.Click.India.Xpath"), "Country");
		return this;
	}

	public TutorRegisterPage enterMobile(String Mobile){
		EnterByCssSelector(prop.getProperty("TutorReg.Enter.mobile.CSS"), Mobile, "Mobile");
		return this;
	}

	public TutorRegisterPage clickAgree(){
		ClickByCssSelector(prop.getProperty("TutorReg.Click.Agree.CSS"),"Agree");
		return this;
	}

	public TutorRegisterPage clickSubmit(){
		ClickByCssSelector(prop.getProperty("TutorReg.Click.Submit.Xpath"), "Submit");
		return this;
	}

}
