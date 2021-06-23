package com.Inteletutor.wrappers;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GenericWrappers extends com.Inteletutor.utils.Reporter{

	public RemoteWebDriver driver;
	public Properties prop;
	public String Url,HubUrl,HubPort;
	public int WAIT_TIME=30;

	/** This method is used to load configuration properties file
	 * @author Musthaja - ConcertCare 
	 */
	public GenericWrappers(){

		Properties prop=new Properties();
		try {
			prop.load(new FileInputStream(new File("config.properties")));
			HubUrl=prop.getProperty("HUB");
			HubPort=prop.getProperty("PORT");
			Url=prop.getProperty("URL");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** This method is used to load webelement objects
	 * @author Musthaja - ConcertCare 
	 */

	public void loadObjects() {
		prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("object.properties")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/** This method is used to launch in local machine
	 * @author Musthaja - ConcertCare 
	 */
	public void invokeApp(String browser) {
		//loadObjects();
		invokeBrowser(browser,false);
	}

	/** This method is used to launch in grid node (if remote) is true
	 * 	@author Musthaja - ConcertCare 
	 */ 
	public void invokeBrowser(String browser, boolean bRemote){

		try {
			DesiredCapabilities cap=new DesiredCapabilities();
			cap.setBrowserName(browser);
			cap.setPlatform(Platform.WINDOWS);
			//Grid Run if need
			if(bRemote)
				driver=new RemoteWebDriver(new URL("http://"+HubUrl+":"+HubPort+"/wd/hub"),cap);

			else{//Local Run
				if(browser.equalsIgnoreCase("chrome")){
					System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
					driver=new ChromeDriver();
				}else{
					System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
					driver=new FirefoxDriver();
				}
			}

			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.get(Url);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**This method is used to close current browser
	 * @author Musthaja - ConcertCare
	 */
	public void closeBrowser() {
		try {
			driver.close();
		} catch (Exception e) {
			reportStep("The browser:"+driver.getCapabilities().getBrowserName()+" could not be closed.", "FAIL");
		}

	}

	/**
	 * This method will close all the browsers
	 * @author Musthaja - ConcertCare
	 */
	public void quitBrowser() {
		try {
			driver.quit();
		} catch (Exception e) {
			reportStep("The browser:"+driver.getCapabilities().getBrowserName()+" could not be closed.", "FAIL");
		}

	}

	/**This method is used to take screenshot
	 * @author Musthaja - ConcertCare
	 */
	@Override
	public long takeSnap() {
		long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L;
		try {
			FileUtils.copyFile(driver.getScreenshotAs(OutputType.FILE), new File("./reports/images/"+number+".jpg"));
		} catch (WebDriverException e) {
			reportStep("The browser has been closed.", "FAIL");
		}catch (IOException e) {
			reportStep("The snapshot could not be taken", "WARN");
		}
		return number;
	}

	/**Below methods are used for enter value in text field
	 * @param Locators - ID, Name, ClassName, Xpath and CssSelector
	 * @param Value - Passing value
	 * @author Musthaja - ConcertCare
	 */
	public void EnterByID(String ID, String Value, String Element){

		try {
			WebDriverWait wait=new WebDriverWait(driver, WAIT_TIME);
			WebElement element=wait.
					until(ExpectedConditions.visibilityOfElementLocated(By.id(ID)));
			element.clear();
			element.sendKeys(Value);
			reportStep(""+Element+" Value Is "+Value+" Entered Successfully", "PASS");
		} catch (Exception e) {
			reportStep("Could Not Enter Value In:" +Element+" Field ", "FAIL");
		}
	}

	public void EnterByName(String Name, String Value, String Element){

		try {
			WebDriverWait wait=new WebDriverWait(driver, WAIT_TIME);
			WebElement element=wait.
					until(ExpectedConditions.visibilityOfElementLocated(By.name(Name)));
			element.clear();
			element.sendKeys(Value);
			reportStep(""+Element+" Value Is "+Value+" Entered Successfully", "PASS");
		} catch (Exception e) {
			reportStep("Could Not Enter Value In:" +Element+" Field ", "FAIL");
		}
	}

	public void EnterByClassName(String ClassName, String Value, String Element){

		try {
			WebDriverWait wait=new WebDriverWait(driver, WAIT_TIME);
			WebElement element=wait.
					until(ExpectedConditions.visibilityOfElementLocated(By.className(ClassName)));
			element.clear();
			element.sendKeys(Value);
			reportStep(""+Element+" Value Is "+Value+" Entered Successfully", "PASS");
		} catch (Exception e) {
			reportStep("Could Not Enter Value In:" +Element+" Field ", "FAIL");
		}
	}

	public void EnterByXpath(String Xpath, String Value, String Element){

		try {
			WebDriverWait wait=new WebDriverWait(driver, WAIT_TIME);
			WebElement element=wait.
					until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Xpath)));
			element.clear();
			element.sendKeys(Value);
			reportStep(""+Element+" Value Is "+Value+" Entered Successfully", "PASS");
		} catch (Exception e) {
			reportStep("Could Not Enter Value In:" +Element+" Field ", "FAIL");
		}
	}

	public void EnterByCssSelector(String CssSelector, String Value, String Element){

		try {
			WebDriverWait wait=new WebDriverWait(driver, WAIT_TIME);
			WebElement element=wait.
					until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CssSelector)));
			element.clear();
			element.sendKeys(Value);
			reportStep(""+Element+" Value Is "+Value+" Entered Successfully", "PASS");
		} catch (Exception e) {
			reportStep("Could Not Enter Value In:" +Element+" Field ", "FAIL");
		}
	}

	/**Below methods are used for click operation
	 * @param Locators - ID, Name, ClassName, Xpath and CssSelector
	 * @author Musthaja - ConcertCare
	 */
	public void ClickByID(String ID, String Element){
		try {
			WebDriverWait wait=new WebDriverWait(driver, WAIT_TIME);
			WebElement element=wait.
					until(ExpectedConditions.visibilityOfElementLocated(By.id(ID)));
			element.click();
			reportStep("Clicked on "+Element+"", "PASS");
		} catch (Exception e) {
			reportStep("Unable To Click On "+Element+"", "FAIL");
		}
	}

	public void ClickByName(String Name, String Element){
		try {
			WebDriverWait wait=new WebDriverWait(driver, WAIT_TIME);
			WebElement element=wait.
					until(ExpectedConditions.visibilityOfElementLocated(By.name(Name)));
			element.click();
			reportStep("Clicked on "+Element+"", "PASS");
		} catch (Exception e) {
			reportStep("Unable To Click On "+Element+"", "FAIL");
		}
	}

	public void ClickByClassName(String ClassName, String Element){
		try {
			WebDriverWait wait=new WebDriverWait(driver, WAIT_TIME);
			WebElement element=wait.
					until(ExpectedConditions.visibilityOfElementLocated(By.className(ClassName)));
			element.click();
			reportStep("Clicked on "+Element+"", "PASS");
		} catch (Exception e) {
			reportStep("Unable To Click On "+Element+"", "FAIL");
		}
	}

	public void ClickByXpath(String Xpath, String Element){
		try {
			WebDriverWait wait=new WebDriverWait(driver, WAIT_TIME);
			WebElement element=wait.
					until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Xpath)));
			element.click();
			reportStep("Clicked on "+Element+"", "PASS");
		} catch (Exception e) {
			reportStep("Unable To Click On "+Element+"", "FAIL");
		}
	}

	public void ClickByCssSelector(String CssSelector, String Element){
		try {
			WebDriverWait wait=new WebDriverWait(driver, WAIT_TIME);
			WebElement element=wait.
					until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CssSelector)));
			element.click();
			reportStep("Clicked on "+Element+"", "PASS");
		} catch (Exception e) {
			reportStep("Unable To Click on "+Element+"", "FAIL");
		}
	}

	/**Below methods are used for verify text on web page
	 * @param Locators - ID, Xpath and CssSelector
	 * @author Musthaja - ConcertCare
	 */
	public void verifyTextbyXpath(String Xpath, String Expected){
		try {
			String Actual=driver.findElement(By.xpath(Xpath)).getText();
			System.out.println(Actual);
			if(Actual.equals(Expected)){
				reportStep("The Actual Result: "+Actual+" Is Matching With Expected Result: "+Expected+"", "PASS");
			}else{
				reportStep("The Actual Result: "+Actual+" Is Not Matched With Expected Result: "+Expected+"", "FAIL");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void verifyTextbyID(String ID, String Expected){
		try {
			String Actual=driver.findElement(By.id(ID)).getText();
			if(Actual.equals(Expected)){
				reportStep("The Actual Result: "+Actual+" Is Matching With Expected Result: "+Expected+"", "PASS");
			}else{
				reportStep("The Actual Result: "+Actual+" Is Not Matched With Expected Result: "+Expected+"", "FAIL");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void verifyTextbyClassName(String ClassName, String Expected){
		try {
			String Actual=driver.findElement(By.className(ClassName)).getText();
			if(Actual.equals(Expected)){
				reportStep("The Actual Result: "+Actual+" Is Matching With Expected Result: "+Expected+"", "PASS");
			}else{
				reportStep("The Actual Result: "+Actual+" Is Not Matched With Expected Result: "+Expected+"", "FAIL");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**Below methods are used for verify title on web page
	 * @author Musthaja - ConcertCare
	 */
	public void verifyTitle(String Title){
		try {
			String ActualTitle=driver.getTitle();
			System.out.println(ActualTitle);
			if(ActualTitle.equalsIgnoreCase(Title)){
				reportStep("The Actual Title: "+ActualTitle+" Is Matching With Expected Title: "+Title+"", "PASS");
			}else{
				reportStep("The Actual Title: "+ActualTitle+" Is Not Matched With Expected Title: "+Title+"", "FAIL");
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**Below methods are used to handle MouseHover operation on web page
	 * @param Locator - Xpath
	 * @author Musthaja - ConcertCare
	 */
	public void MouseHoverByXpath(String Xpath){
		try {
			Actions action=new Actions(driver);
			action.moveToElement(driver.findElement(By.xpath(Xpath))).build().perform();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void MouseHoverClickByXpath(String Xpath){
		try {
			new Actions(driver).moveToElement(driver.findElement(By.xpath(Xpath)))
			.click().build().perform();
		} catch (Exception e) {

		}
	}

	/**Below methods are used to handle dropdown menu on web page
	 * @param Locator - ID, Name, CssSelector and Xpath
	 * @author Musthaja - ConcertCare
	 */
	public void SelectIndexbyID(String ID,int Value){
		try {
			new Select(driver.findElement(By.id(ID))).selectByIndex(Value);
		} catch (Exception e) {

		}
	}

	public void SelectIndexbyName(String Name,int Value){
		try {
			new Select(driver.findElement(By.name(Name))).selectByIndex(Value);
		} catch (Exception e) {

		}
	}


	public void SelectIndexbyXapth(String Xpath,int Value){
		try {
			new Select(driver.findElement(By.xpath(Xpath))).selectByIndex(Value);
		} catch (Exception e) {

		}
	}

	public void SelectIndexbyCssSelector(String CssSelector,int Value){
		try {
			new Select(driver.findElement(By.cssSelector(CssSelector))).selectByIndex(Value);
		} catch (Exception e) {

		}
	}

	public void SelectVisibleTextByID(String ID,String Text){
		try {
			new Select(driver.findElement(By.id(ID))).selectByVisibleText(Text);
		} catch (Exception e) {

		}
	}

	public void SelectVisibleTextByXpath(String Xapth,String Text){
		try {
			new Select(driver.findElement(By.xpath(Xapth))).selectByVisibleText(Text);
		} catch (Exception e) {

		}
	}

	public void SelectValuebyID(String ID,String Value){
		try {
			new Select(driver.findElement(By.id(ID))).selectByValue(Value);
		} catch (Exception e) {

		}
	}

	public void SelectValuebyXapth(String Xpath,String Value){
		try {
			new Select(driver.findElement(By.xpath(Xpath))).selectByValue(Value);
		} catch (Exception e) {

		}
	}

	/**Below methods are used to handle Alert Popup on web page
	 * @author Musthaja - ConcertCare
	 * @throws InterruptedException 
	 */
	public void acceptAlert() throws InterruptedException{
		try {
			driver.switchTo().alert().accept();
		} catch (NoAlertPresentException e) {
			// TODO: handle exception
		}catch(Exception e){

		}
	}

	public void dismissAlert(){
		try {
			driver.switchTo().alert().dismiss();
		} catch (NoAlertPresentException e) {
			// TODO: handle exception
		}catch(Exception e){

		}
	}

	public String getAlertText(){
		String text=null;
		try {
			String texts=driver.switchTo().alert().getText();
		} catch (NoAlertPresentException e) {
			// TODO: handle exception
		}catch(Exception e){

		}
		return text;
	}

	/**Below method is used to scroll web page
	 * @author Musthaja - ConcertCare
	 */
	public void scrollDownPage(){

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,270)", "");

	}
	
	/**Below method is used to click on element 
	 * @author Musthaja - ConcertCare
	 */
	public void clickArgumentByJavaScript(WebElement element){

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();",element);

	}

	/**Below method is used to handle windows
	 * @author Musthaja - ConcertCare
	 */
	public void WindowHandles(){
		try {
			Set<String> win=driver.getWindowHandles();
			Iterator<String> it=win.iterator();
			while(it.hasNext()){
				String ChildWin=it.next();
				driver.switchTo().window(ChildWin);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**Below method is used to get text for an element
	 * @author Musthaja - ConcertCare
	 */
	public void getText(String XPath){
		try {
			String text=driver.findElement(By.xpath(XPath)).getText();
			System.out.println(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getTodayDate(){

		Date Date=new Date();
		DateFormat format=new SimpleDateFormat("MM/dd/yyyy");
		String TodayDate=format.format(Date);
		System.out.println(TodayDate);
		return TodayDate;
	}
	
	public String addDate(){
		
		DateFormat format=new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 10);
		String newDate=format.format(cal.getTime());
		System.out.println(newDate);
		return newDate;
	}


	public void zoomOutPage(){
		try {
			Robot robot=new Robot();
			for (int i = 0; i < 5; i++) {
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_SUBTRACT);
				robot.keyRelease(KeyEvent.VK_ADD);
				robot.keyRelease(KeyEvent.VK_CONTROL);
			}
			Thread.sleep(2000);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void zoomInPage() throws AWTException, InterruptedException{

		Robot robot=new Robot();
		for (int i = 0; i < 2; i++) {
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ADD);
			robot.keyRelease(KeyEvent.VK_ADD);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		}
		Thread.sleep(2000);
	}
}

