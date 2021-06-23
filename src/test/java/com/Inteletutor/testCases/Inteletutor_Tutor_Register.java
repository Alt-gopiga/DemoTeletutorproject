package com.Inteletutor.testCases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.Inteletutor.pages.LoginPage;
import com.Inteletutor.wrappers.BaseClassWrappers;

public class Inteletutor_Tutor_Register extends BaseClassWrappers{

	@BeforeClass
	public void setValues(){
		browserName="chrome";
		testCaseName="Inteletutor - Create Account";
		testDescription="Create New Account";
		category="Smoke";	
		dataSheetName="Register_Tutor";
	}
	
	@Test(dataProvider="fetchData")
	public void RegisterTutor(String Fname,String LName,
							  String Email,String Password,
							  String Confirm,String Mobile){
		new LoginPage(driver, test)
		.clickCreateAccount()
		.clickTutor()
		.enterFName(Fname)
		.enterLName(LName)
		.enterEmail(Email)
		.clickGender()
		.enterPassWord(Password)
		.enterConfirmPassword(Confirm)
		.enterMobile(Mobile)
		.clickAgree();
		//.clickSubmit();
		
	}
}
