package com.irctc.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.irctc.qa.utils.ExcelUtility;

public class TrainSearchPage extends BasePage {

	By fromsearchinput = By.xpath("//span[@class='ng-tns-c57-8 ui-autocomplete ui-widget']/input");
	By tosearchinput = By.xpath("//span[@class='ng-tns-c57-9 ui-autocomplete ui-widget']/input");
	By origindata = By.xpath("//p-autocomplete[@id='origin']/span/div/ul/li");
	By destinationdata = By.xpath("//p-autocomplete[@id='destination']/span/div/ul/li");
	By dateInput = By.xpath("//p-calendar[@id='jDate']/span/input");
	By searchBtn = By.xpath("//button[@type='submit']");
	By modifysearchBtn = By.xpath("(//span[contains(text(), 'Modify Search')])[1]");
	By trainsResult = By.xpath("//div[contains(@class,'train-heading')]");
	By trainCoach = By.xpath("(//div[contains(@class,'white-back col-xs-12 ng-star-inserted')])[1]/table/tr/td/div/div[2]/span");
	By refreshedcoach = By.xpath("//li//div[@class='ng-star-inserted']");
	By daysavail = By.xpath("//td[@class='link ng-star-inserted']/div/div/strong");
	ExcelUtility excelUtility = new ExcelUtility("output.xlsx");
	
	public TrainSearchPage(WebDriver driver) {
		BasePage.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void selectJunction(String origin, String destination) throws InterruptedException {
		sendKeys(fromsearchinput, origin);
		List<WebElement> originelements = findElementByList(origindata);
		selectStation(originelements);
		sendKeys(tosearchinput, destination);
		List<WebElement> destinationelements = findElementByList(destinationdata);
		selectStation(destinationelements);
	

	}

	public void selectStation(List<WebElement> elementlist) throws InterruptedException {
		for (WebElement element : elementlist) {
			Thread.sleep(1000);
			ScrollToElement(element);
			String station = getTextOfElement(element);

			if (station.contains("JN")) {
				hoverAndClick(element);
				break;
			}
		}
	}

	public void selectDate(String date) {
		clear(dateInput);
		sendKeys(dateInput, date);
	}

	public void searchtrain() {
		click(searchBtn);
	}

	public Boolean checkresult() {
		Boolean elementfound = check_element_present(modifysearchBtn);
		return elementfound;
	}

	public void getTrainName() {
		List<WebElement> TrainsAvl = findElementByList(trainsResult);
		for (WebElement element : TrainsAvl) {
			ScrollToElement(element);
			String trainname = getTextOfElement(element);
			System.out.println(trainname);
		}
	}

	public void getTrainCoachData() {
		click(trainCoach);
	}

	public void refreshedCoach() throws InterruptedException {
		List<WebElement> Coaches = findElementByList(refreshedcoach);
		for (WebElement element : Coaches) {
			Thread.sleep(1000);
			clickByElement(element);
			String caochesname = getTextOfElement(element);
			excelUtility.appendDataToExcel(caochesname,true);
			Thread.sleep(1000);
			List<WebElement> availdateTrain = findElementByList(daysavail);
			for (WebElement element_train : availdateTrain) {
				Thread.sleep(1000);
				String availtrain = getTextOfElement(element_train);
				excelUtility.appendDataToExcel(availtrain,false);
			}
			}
		excelUtility.saveExcelFile(System.getProperty("user.dir") + "/output.xlsx");
	}
	
}
