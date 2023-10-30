package com.irctc.qa.pages;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.irctc.qa.utils.Utilities;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class LoginPage extends BasePage {

	By userNameInput = By.xpath("//*[@formcontrolname='userid']");
	By passwordInput = By.xpath("//*[@formcontrolname='password']");
	By loginbtn = By.xpath("//a[@aria-label='Click here to Login in application']");
	By captchaimage = By.xpath("//img[@class='captcha-img']");
	By captchaInputField = By.xpath("//input[@id='captcha']");
	By signin_btn = By.xpath("//button[normalize-space()='SIGN IN']");
	By captcha_error = By.xpath("//div[@class='loginError']");
	By refreshcaptcha = By.xpath("//span[@class='glyphicon glyphicon-repeat']");
	By welcometext = By.xpath("//span[contains(normalize-space(),'Welcome')]");
	By validationtext=By.xpath("//*[text()='Invalid User']");
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void navigateToLogin() {
		click(loginbtn);
		
	}

	public void enterEmailandPassword(String Username , String Password) {
		sendKeys(userNameInput, Username);
		sendKeys(passwordInput, Password);
	}

	public void getCaptcha() throws IOException, InterruptedException, TesseractException {
		Utilities.getScreenshotAndSave(findElement(captchaimage));
		String CapturedText = Utilities.getCaptchaText();
		Thread.sleep(3000);
		sendKeys(captchaInputField, CapturedText);
	}

	public void clickSignIn() {
		click(signin_btn);
	}

	public Boolean get_welcome_text() {
		Boolean get_status = check_element_present(welcometext);
		return get_status;
	}
	
	public Boolean check_validation() {
		Boolean get_status = check_element_present(validationtext);
		return get_status;
	}
	

	public void loginflow(String Username , String Password) throws IOException, InterruptedException, TesseractException {
		navigateToLogin();
		enterEmailandPassword(Username ,Password);
		getCaptcha();
		clickSignIn();
		while (get_welcome_text() == false) {
			getCaptcha();
			clickSignIn();
		}
		Assert.assertEquals(get_welcome_text(), true, "Login Successful");
	}

}
