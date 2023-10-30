package com.irctc.qa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Utilities {

	public static final int IMPLICIT_WAIT_TIME = 10;
	public static final int PAGE_WAIT_TIME = 5;

	public static String captureScreenshot(WebDriver driver, String testName) {
		File srsScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String destinationScreenshotPath = System.getProperty("user.dir") + "\\Screenshots\\" + testName + ".png";
		try {
			FileHandler.copy(srsScreenshot, new File(destinationScreenshotPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destinationScreenshotPath;
	}
	
	public static String captureScreenshotBase64(WebDriver driver, String testName) throws IOException {
		File srsScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String destinationScreenshotPath = System.getProperty("user.dir") + "\\Screenshots\\" + testName + ".png";
		FileHandler.copy(srsScreenshot, new File(destinationScreenshotPath));
		IOUtils.toByteArray(new FileInputStream(destinationScreenshotPath));
		byte[] imageBytes = IOUtils.toByteArray(new FileInputStream(destinationScreenshotPath));
		return Base64.getEncoder().encodeToString(imageBytes);
	}


	public static String getCaptchaText() {
		ITesseract tesseract = new Tesseract();
		tesseract.setDatapath(System.getProperty("user.dir")+"\\tessdata");
		tesseract.setLanguage("eng");
		String result = null;
		try {
			//add captured screenshot path
			File imageFile = new File(System.getProperty("user.dir")+ "\\Captcha\\captcha.png");
			result = tesseract.doOCR(imageFile);
		} catch (TesseractException e) {
			System.err.println("Error: " + e.getMessage());
		}
		return result;
	}

	public static void getScreenshotAndSave(WebElement element) {
		try {

			File screenshotFile = element.getScreenshotAs(OutputType.FILE);
			File destinationFile = new File("C:\\Users\\dhruv\\Desktop\\Hybrid Framework\\irctc\\Captcha\\captcha.png");
			FileUtils.copyFile(screenshotFile, destinationFile);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
