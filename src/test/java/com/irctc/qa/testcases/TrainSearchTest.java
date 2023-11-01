package com.irctc.qa.testcases;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.irctc.qa.base.Base;
import com.irctc.qa.listeners.MyListerners;
import com.irctc.qa.pages.LoginPage;
import com.irctc.qa.pages.TrainSearchPage;

import net.sourceforge.tess4j.TesseractException;

public class TrainSearchTest extends Base{
	public TrainSearchTest() {
		super();
	}

	public WebDriver driver;
	LoginPage loginPage;
	TrainSearchPage trainSearchPage;
	
	@BeforeTest
	public void navigateToBaseUrl() {
		driver = intializeBrowser(prop.getProperty("browser"));
		loginPage = new LoginPage(driver);
		trainSearchPage = new TrainSearchPage(driver);
	}
	
	@Test
	public void searchTrain() throws IOException, InterruptedException, TesseractException {
		//Thread.sleep(5000);
		loginPage.loginflow("DhruvJadav","7102000jdD@");
		trainSearchPage.selectJunction("Ahmedabad", "Pune");
		trainSearchPage.selectDate("30/10/2023");
		trainSearchPage.searchtrain();
		Assert.assertEquals(trainSearchPage.checkresult(), true);
		System.out.println("First");
		System.out.println("Second");
	}
	
	@Test(dependsOnMethods = "searchTrain")
	public void fetchTrains() {
		trainSearchPage.getTrainName();
	}
	
	@Test(dependsOnMethods = "searchTrain")
	public void getTrainData() throws InterruptedException {
		trainSearchPage.getTrainCoachData();
		trainSearchPage.refreshedCoach();
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
