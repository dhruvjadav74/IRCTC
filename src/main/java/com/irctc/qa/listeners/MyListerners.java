package com.irctc.qa.listeners;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.model.Media;
import com.irctc.qa.utils.ExtentReporter;
import com.irctc.qa.utils.Utilities;

public class MyListerners implements ITestListener {

	ExtentReports extentreport;
	ExtentTest extentTest;

	@Override
	public void onStart(ITestContext context) {
		extentreport = ExtentReporter.generateExtentReport();
	}

	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getName();
		extentTest = extentreport.createTest(testName);
		extentTest.log(Status.INFO, testName + " Started execution");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String testName = result.getName();
		extentTest.pass(MarkupHelper.createLabel(testName + " Successfully executed", ExtentColor.GREEN));
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testName = result.getName();
		WebDriver driver = null;
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver")
					.get(result.getInstance());
			String destinationScreenshotPath = Utilities.captureScreenshot(driver, testName);
			extentTest.fail(MediaEntityBuilder
					.createScreenCaptureFromBase64String(Utilities.captureScreenshotBase64(driver, testName)).build());
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException
				| IOException e) {
			e.printStackTrace();
		}
		extentTest.log(Status.INFO, result.getThrowable());
		extentTest.fail(MarkupHelper.createLabel(testName + " Got failed", ExtentColor.RED));
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String testName = result.getName();
		extentTest.log(Status.INFO, result.getThrowable());
		extentTest.log(Status.SKIP, testName + " Got skipped");
	}

	@Override
	public void onFinish(ITestContext context) {
		extentreport.flush();
		String pathofExtentReport = System.getProperty("user.dir") + "\\test-output\\ExtentReports\\extentReport.html";
		File extentReport = new File(pathofExtentReport);

		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
