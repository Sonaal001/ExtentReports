package com.app.at.testcases;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class AutomationTestWebsite {
	public WebDriver driver;
	public ExtentReports extent;
	public ExtentTest logger;
	
	@BeforeTest
	public void setExtent(){
	extent=new ExtentReports(System.getProperty("E:\\Selenium\\Selenium-Workspace\\ExtentReportsTest")+"/test-output/ExtentReports.html", true);
	extent.addSystemInfo("User Name", "WELCOME");
	extent.addSystemInfo("Host Name", "HP");
	extent.addSystemInfo("OS", "Windows 8.1");
	}
	
	@AfterTest
	public void endReport() {
		extent.flush();
		extent.close();
	}
	
	public String takeScreenshot(WebDriver driver, String ScreenshotName) throws IOException {
		
		String datename=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts= (TakesScreenshot) driver;
		File source=ts.getScreenshotAs(OutputType.FILE);
		String destination=System.getProperty("user.dir")+"/FailedTestsScreenshots/"+ScreenshotName + datename+".png";
		File finalDestination =new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}
	
	
	
	@BeforeMethod
	public void setup() {
	System.setProperty("webdriver.chrome.driver","E:\\ChromeD\\chromedriver.exe");
	driver = new ChromeDriver(); 
	driver.manage().window().maximize();
	driver.manage().deleteAllCookies();
	driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	driver.get("http://automationpractice.com/index.php");
	}
	
	@Test
	public void atTest() {
		String title=driver.getTitle();
		
		Assert.assertEquals(title, "My Store");
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) {
		if(result.getStatus()==ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, "Test Case failed is :"+result.getName());
		}
		driver.quit();
		
	}
}
