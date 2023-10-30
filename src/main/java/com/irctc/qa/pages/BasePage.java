package com.irctc.qa.pages;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

public class BasePage {
	public static WebDriver driver;

	protected WebElement findElement(By by) {
		WebElement element;
		try {
			element = driver.findElement(by);
		} catch (Exception e) {
			return null;
		}
		return element;
	}

	protected void click(By by) {
		WebElement element = waitForElementToBeVisible(by);
		element.click();
	}

	protected void clickByElement(WebElement element) {
		element.click();
	}

	protected void clear(By by) {
		WebElement element = findElement(by);
		element.sendKeys(Keys.CONTROL + "a"); 
		element.sendKeys(Keys.DELETE);
		//element.clear();
	}

	protected void sendKeys(By by, String inputtext) {
		WebElement element = waitForElementToBeVisible(by);
		element.sendKeys(inputtext);
	}

	protected void doubleClick(By by) {
		Actions actions = new Actions(driver);
		actions.doubleClick(findElement(by)).perform();
	}

	public WebElement waitForElementToBeVisible(By locator) throws NoSuchElementException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public Boolean isdisplayed(By locator) {
		Boolean elementpresent = false;
		try {
			elementpresent = driver.findElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {
		}
		return elementpresent;
	}

	protected String getText(By locator) {
		String gettext = null;
		try {
			WebElement element = waitForElementToBeVisible(locator);
			gettext = element.getText();
		} catch (NoSuchElementException | StaleElementReferenceException e) {
		}
		return gettext;
	}

	protected String getTextOfElement(WebElement element) {
		String gettext = element.getText();
		return gettext;
	}

	protected Boolean check_element_present(By locator) {
		Boolean element_present = false;
		try {
			Optional<WebElement> optionalElement = Optional.ofNullable(driver.findElement(locator));
			if (optionalElement.isPresent()) {
				WebElement element = optionalElement.get();
				element_present = true;
			}
		} catch (NoSuchElementException e) {
		}
		return element_present;
	}

	protected List<WebElement> findElementByList(By locator) throws NoSuchElementException {
		List<WebElement> elements = driver.findElements(locator);
		return elements;
	}

	protected static void ScrollToElement(WebElement element) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});",
				element);
	}

	protected void hoverAndClick(WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();
		element.click();
	}
	
	protected void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
