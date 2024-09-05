package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.Reporter;

import base.BaseComponents;

//This class contains Page objects for the Landing page
public class LandingPage extends BaseComponents {
//	Declaring object for WebDriver
	WebDriver driver;

//	Constructor for LandingPage class to assign "driver" and PageFactory
	public LandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

//	Storing Web Elements in Landing page of the BestBuy application using @FindBy annotation
	@FindBy(linkText = "Canada")
	WebElement country;
	@FindBy(linkText = "Account")
	WebElement account;
	@FindBy(linkText = "Create an account")
	WebElement createAccount;

//	Action method to launch the URL
	public void goToURL(String URL) {
		driver.get(URL);
	}

//	Action method to open the Sign up page
	public void openSignUpPage() {
		if(country.isDisplayed())
			clickButton(country);
		clickButton(account);
		clickButton(createAccount);
	}

//	Action method to open the Login page
	public void openLoginPage() {
		if(country.isDisplayed())
			clickButton(country);
		clickButton(account);
	}
	
//	Action method to open the Home Page
	public void openHomePage() {
		while(isElementPresent(country))
			clickButton(country);
	}
	
//	Action method to validate the BestBuy Application URL is broken or not 
	public void validateURL(String URL) {
		String reportInfo = checkBrokenLink(URL);
		ITestResult itr = Reporter.getCurrentTestResult();
		itr.setAttribute("reportInfo", reportInfo);
	}
}
