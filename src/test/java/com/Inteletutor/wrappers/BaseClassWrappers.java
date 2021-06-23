package com.Inteletutor.wrappers;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.Inteletutor.utils.DataInputProvider;

public class BaseClassWrappers extends GenericWrappers{

	public String browserName;
	public String dataSheetName;
	
	@BeforeSuite
	public void beforeSuite(){
		startResult();
	}

	@BeforeMethod
	public void beforeMethod(){
		test = startTestCase(testCaseName, testDescription);
		test.assignCategory(category);
		invokeApp(browserName);
	}
	
	@AfterMethod
	public void afterMethos(){
		endTestcase();
		quitBrowser();
	}
	
	@AfterTest
	public void afterTest(){

	}
	
	@AfterSuite
	public void aftersuite(){
		endResult();
	}
	
	@DataProvider(name="fetchData")
	public Object[][] getExcel(){
		return DataInputProvider.getSheet(dataSheetName);
	}
	

}

