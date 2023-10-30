package com.irctc.qa.testcases;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.beust.jcommander.Parameter;
import com.irctc.qa.base.Base;
import com.irctc.qa.pages.LoginPage;

import net.sourceforge.tess4j.TesseractException;

public class LoginTest extends Base {

	public LoginTest() {
		super();
	}

	public WebDriver driver;
	LoginPage loginPage;

	@BeforeMethod
	public void navigateToBaseUrl() {
		driver = intializeBrowser(prop.getProperty("browser"));
		loginPage = new LoginPage(driver);
	}

	@Test (priority=1)
	public void verifyLoginWithValidCred() throws InterruptedException, IOException, TesseractException {
		Thread.sleep(5000);
		loginPage.navigateToLogin();
		Thread.sleep(5000);
		loginPage.enterEmailandPassword("DhruvJadav","7102000jdD@");
		loginPage.getCaptcha();
		loginPage.clickSignIn();
		loginPage.get_welcome_text();
		while (loginPage.get_welcome_text() == false) {
			loginPage.getCaptcha();
			loginPage.clickSignIn();
		}
		Assert.assertEquals(loginPage.get_welcome_text(),true);
	}
	
	@Test (priority=2)
	public void loginwithinvalidcred() throws IOException, InterruptedException, TesseractException {
		loginPage.navigateToLogin();
		loginPage.enterEmailandPassword("DhruvJadavv","7102000jdD@");
		loginPage.getCaptcha();
		loginPage.clickSignIn();
		Boolean validation=loginPage.check_validation();
		Assert.assertEquals(validation, true);
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
