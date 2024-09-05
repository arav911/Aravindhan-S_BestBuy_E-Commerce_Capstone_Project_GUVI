package base;

//import java.io.File;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;

//This class contains Action methods and Common utility methods
public class BaseComponents {
//	Declaring object for WebDriver, WebDriverWait, Alert, Actions, HttpURLConnection, ArrayList<String>, HashMap<String, String>, String[] and String
	WebDriver driver;
	WebDriverWait wait;
	Alert alert;
	Actions action;
	HttpURLConnection httpURLConnection;
	protected ArrayList<String> windows;
	private HashMap<String, String> data;
	String[] keys, values;
	String reportInfo;

//	Constructor for BaseComponents class to assign "driver" and PageFactory
	public BaseComponents(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		action = new Actions(driver);
	}

//	Action method to select dropdown value using visible text
	protected void chooseDropdownValue(WebElement element, String text) {
		waitForElementToAppear(element);
		Select dropdown = new Select(element);
		dropdown.selectByValue(text);
	}

//	Action method to select dropdown value using index
	protected void chooseDropdownValue(WebElement element, int index) {
		waitForElementToAppear(element);
		Select dropdown = new Select(element);
		dropdown.selectByIndex(index);
	}

//	Action method to switch to Child Window from the browser
	protected ArrayList<String> switchToChildWindow(WebDriver driver) {
		Set<String> windowHandles = driver.getWindowHandles();
		Iterator<String> iterator = windowHandles.iterator();
		while (iterator.hasNext()) {
			windows.add(iterator.next());
		}
		driver.switchTo().window(windows.get(windows.size() - 1));
		return windows;
	}
	
//	Action method to switch from Child Window to the Parent Window
	protected void switchToParentWindow() {
		driver.close();
		driver.switchTo().window(windows.get(0));
	}
	
//	Action method to switch to Frame
	protected void switchToFrame(WebElement element) {
		waitForSeconds(5);
		driver.switchTo().frame(element);
	}
	
//	Action method to switch from Frame to the Default Window
	protected void switchToDefaultWindow() {
		driver.switchTo().defaultContent();
	}

//	Action method to get the Current Page Title
	protected String getPageTitle() {
		return driver.getTitle();
	}

//	Action method to move to particular WebElement
	protected void moveToElement(WebElement element) {
		try {
			waitForSeconds(5);
			waitForElementToAppear(element);
			action.moveToElement(element).build().perform();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	Action method to perform Keyboard Actions
	protected void keyBoardActions(String key) {
		if (key.equalsIgnoreCase("Tab"))
			action.sendKeys(Keys.TAB).build().perform();
		if (key.equalsIgnoreCase("Enter"))
			action.sendKeys(Keys.ENTER).build().perform();
	}

//	Action method to get text from a WebElement
	protected String getTextContent(WebElement element) {
		try {
			waitForSeconds(5);
			waitForElementToAppear(element);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return element.getText();
	}

//	Action method to Handle the Alert and return the Text captured
	protected String getAlertText() {
		String alertText = "";
		try {
			waitForSeconds(6);
			alert = driver.switchTo().alert();
			alertText = alert.getText();
			alert.accept();
			reportInfo = "The Alert Text is \"" + alertText + "\"";

//		Setting the attribute for alert text
			ITestResult itr = Reporter.getCurrentTestResult();
			itr.setAttribute("reportInfo", reportInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return alertText;
	}

//	Action method to perform sendKeys operation in a Text box WebElement
	protected void enterText(WebElement element, String text) {
		waitForElementToAppear(element);
		element.sendKeys(text);
	}

//	Action method to perform click operation in a button WebElement
	protected void clickButton(WebElement element) {
		waitForElementToAppear(element);
		moveToElement(element);
		element.click();
	}

//	Action method to find the single element which are located under given Xpath
	protected WebElement findSubElementUsingXpath(WebElement element, String xpath) {
		if (isElementPresent(element, xpath))
			return element.findElement(By.xpath(xpath));
		return null;
	}

//	Action method to find the list of elements which are located under given Xpath
	protected List<WebElement> findSubElementListUsingXpath(WebElement element, String xpath) {
		if (isElementPresent(element, xpath))
			return element.findElements(By.xpath(xpath));
		return null;
	}

//	Action method to take screenshot on the web page	
	protected void takeScreenshot(WebDriver driver) {
		waitForSeconds(3);
		try {
			// Get the stack trace
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

//      The stack trace element at index 3 represents the caller of this method
			StackTraceElement caller = stackTrace[3];

//      Extract the method name
			String testName = caller.getMethodName();
			String clsFileName = caller.getFileName();

//	      Taking Screenshot as mentioned
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			String destPath = "./Test_Results/Screenshots/" + clsFileName + "/" + testName + "/" + BaseTest.screenShotCounter + "_" + testName
					+ ".png";
			File finalDestination = new File(destPath);
			ITestResult itr = Reporter.getCurrentTestResult();
			itr.setAttribute("Current Screenshot Path", destPath);
			FileUtils.copyFile(source, finalDestination);
			BaseTest.screenShotCounter++;
			System.out.println("Screenshot captured successfully and placed in below path:\n" + destPath);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

//	Action method to take screenshot on particular of the web page	
	protected void takeScreenshotAsElement(WebElement element, WebDriver driver) {
		try {
			// Get the stack trace
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

//      The stack trace element at index 3 represents the caller of this method
			StackTraceElement caller = stackTrace[3];

//      Extract the method name
			String testName = caller.getMethodName();
			String clsFileName = caller.getFileName();

//      Taking Screenshot as mentioned
			File source = element.getScreenshotAs(OutputType.FILE);
			String destPath = "./Test_Results/Screenshots/" + clsFileName + "/" + testName + "/" + BaseTest.screenShotCounter + "_" + testName
					+ ".png";
			File finalDestination = new File(destPath);
			ITestResult itr = Reporter.getCurrentTestResult();
			itr.setAttribute("Current Screenshot Path", destPath);
			FileUtils.copyFile(source, finalDestination);
			BaseTest.screenShotCounter++;
			System.out.println("Screenshot captured successfully and placed in below path:\n" + destPath);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

//	Action method to take screenshot by moving to particular element on the web page	
	protected void takeScreenshotByMovingToElement(WebDriver driver, WebElement element) {
		try {
			waitForElementToAppear(element);
			action.moveToElement(element).build().perform();
//		Get the stack trace
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

//      The stack trace element at index 3 represents the caller of this method
			StackTraceElement caller = stackTrace[3];

//      Extract the method name
			String testName = caller.getMethodName();
			String clsFileName = caller.getFileName();

//	    Taking Screenshot as mentioned
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			String destPath = "./Test_Results/Screenshots/" + clsFileName + "/" + testName + "/" + BaseTest.screenShotCounter + "_" + testName
					+ ".png";
			File finalDestination = new File(destPath);
			ITestResult itr = Reporter.getCurrentTestResult();
			itr.setAttribute("Current Screenshot Path", destPath);
			FileUtils.copyFile(source, finalDestination);
			BaseTest.screenShotCounter++;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

//	Action method to perform clearing of data in a Text box WebElement
	protected void clearData(WebElement element) {
		waitForElementToAppear(element);
		element.clear();
	}

//	Action method to interrupt the execution by waiting for a particular seconds
	protected void waitForSeconds(int time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	Action method to explicitly wait for a WebElement to appear
	protected void waitForElementToAppear(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
	}

//	Action method to explicitly wait for a WebElement to appear
	protected void waitForElementToBeClickable(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

//	Action method to explicitly wait for a WebElement to disappear
	protected void waitForElementToDisappear(WebElement element) {
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

//	Action method to explicitly wait for a WebElement to appear
	protected void waitForAllElementsToAppear(List<WebElement> elements) {
		wait.until(ExpectedConditions.visibilityOfAllElements(elements));
	}

//	Action method to explicitly wait for a WebElement to disappear
	protected void waitForAllElementsToDisappear(List<WebElement> elements) {
		wait.until(ExpectedConditions.invisibilityOfAllElements(elements));
	}

//	Action method to check whether an element is available in the application
	protected boolean isElementPresent(WebElement element) {
		try {
			element.isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

//	Action method to check whether an element is available in the application and performing the specified action
	protected boolean isElementPresent(String xpath, String action) {
		try {
			WebElement element = driver.findElement(By.xpath(xpath));
			if (!action.isEmpty()) {
				if (action.equalsIgnoreCase("Click Field"))
					clickButton(element);
			}
			if (element.isDisplayed())
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

//	Action method to check whether an element is available in the application
	protected boolean isElementPresent(WebElement element, String xpath) {
		try {
			element.findElement(By.xpath(xpath));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

//	Action method to validate the text content with expected text
	protected void validateTextContent(WebElement element, String expectedText) {
		waitForElementToAppear(element);
		takeScreenshot(driver);
		Assert.assertEquals(getTextContent(element), expectedText);
	}

//	Action method to extract data from the Excel resource sheet and storing it as HashMap
	protected HashMap<String, String> getDataFromExcel(String fileName, int rowNumber) {
		try {
//			Specify the location of file
			String filePath = "./src/main/resources/" + fileName + ".xlsx";
			File file = new File(filePath);

//			Load file
			FileInputStream fis = new FileInputStream(file);

//			Load workbook
			XSSFWorkbook wb = new XSSFWorkbook(fis);

//			Load work sheet
			XSSFSheet sheet = wb.getSheet("Sheet1");

//			get total number of columns
			int columns = sheet.getRow(0).getPhysicalNumberOfCells();

//			print all the values from the excel
			data = new HashMap<String, String>();
			keys = new String[columns];
			values = new String[columns];
			for (int j = 0; j < columns; j++) {
				try {
					if (sheet.getRow(0).getCell(j).getCellType().equals(CellType.BLANK))
						keys[j] = null;
					if (sheet.getRow(0).getCell(j).getCellType().equals(CellType.STRING))
						keys[j] = sheet.getRow(0).getCell(j).getStringCellValue();
					if (sheet.getRow(0).getCell(j).getCellType().equals(CellType.NUMERIC))
						keys[j] = String.valueOf((int) sheet.getRow(0).getCell(j).getNumericCellValue());
					if (sheet.getRow(rowNumber).getCell(j).getCellType().equals(CellType.BLANK))
						values[j] = null;
					if (sheet.getRow(rowNumber).getCell(j).getCellType().equals(CellType.STRING))
						values[j] = sheet.getRow(rowNumber).getCell(j).getStringCellValue();
					if (sheet.getRow(rowNumber).getCell(j).getCellType().equals(CellType.NUMERIC))
						values[j] = String.valueOf((int) sheet.getRow(rowNumber).getCell(j).getNumericCellValue());
					data.put(keys[j], values[j]);
				} catch (NullPointerException e) {
					values[j] = null;
				}
			}
//			Below loop is performed to replace the value containing <> symbol with original value which is referring to another Excel sheet 
			for (int i = 0; i < columns; i++) {
				if (data.get(keys[i]) != null && data.get(keys[i]).contains("<>")) {
					String[] splittedValue = data.get(keys[i]).split("<>");
					data.put(keys[i], getDataFromExcel(splittedValue[0], splittedValue[1]));
				}
			}

//			Close workbook
			wb.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
//			returning the HashMap data
		return data;

	}

//	Action method to get data from the Excel file by specifying file name and column name	
	protected String getDataFromExcel(String fileName, String columnName) {
		String readData = "";
		try {
//			Specify the location of file
			String filePath = "./src/main/resources/" + fileName + ".xlsx";
			File file = new File(filePath);

//			Load file
			FileInputStream fis = new FileInputStream(file);

//			Load workbook
			XSSFWorkbook wb = new XSSFWorkbook(fis);

//			Load work sheet
			XSSFSheet sheet = wb.getSheet("Sheet1");

			int columns = sheet.getRow(0).getPhysicalNumberOfCells();

//			Getting value from the respective cell
			for (int i = 0; i < columns; i++) {
				if (sheet.getRow(0).getCell(i).getStringCellValue().equalsIgnoreCase(columnName))
					readData = sheet.getRow(1).getCell(i).getStringCellValue();
			}

			readData = (readData != null) ? readData : "";

//			Close workbook
			wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return readData;
	}

//	Action method to check for the provided link is Broken or not
	protected String checkBrokenLink(String linkUrl) {
		try {
			URL link = new URL(linkUrl);
			httpURLConnection = (HttpURLConnection) link.openConnection();
			httpURLConnection.setConnectTimeout(5000);
			httpURLConnection.setReadTimeout(30000);
			httpURLConnection.setRequestMethod("HEAD");
			httpURLConnection.connect();
			if (httpURLConnection.getResponseCode() == 200) {
				System.out
						.println("URL - " + linkUrl + "\nResponse Message - " + httpURLConnection.getResponseMessage());
				return ("URL - " + linkUrl + "\nResponse Message - " + httpURLConnection.getResponseMessage());
			} else {
				System.out.println("URL - " + linkUrl + " - " + "is a broken link" + "\nResponse Message - "
						+ httpURLConnection.getResponseMessage());
				return ("URL - " + linkUrl + " - " + "is a broken link" + "\nResponse Message - "
						+ httpURLConnection.getResponseMessage());
			}
		} catch (Exception e) {
			System.out.println("URL - " + linkUrl + " - " + "is a broken link" + "\nResponse Message - "
					+ e.getMessage() + " after 30 seconds...");
			return ("URL - " + linkUrl + " - " + "is a broken link" + "\nResponse Message - "
					+ e.getMessage() + " after 30 seconds...");
		} finally {
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}
	}
}