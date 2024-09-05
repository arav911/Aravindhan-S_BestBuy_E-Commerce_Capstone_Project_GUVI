package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import base.BaseComponents;

//This class contains Page objects for the Login page
public class LoginPage extends BaseComponents {
//	Declaring object for WebDriver
	WebDriver driver;

//	Constructor for Login class to assign "driver" and PageFactory
	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

//	Storing Web Elements in Login page of the BestBuy application using @FindBy annotation
	@FindBy(id = "username")
	WebElement userName;
	@FindBy(name = "password")
	WebElement password;
	@FindBy(xpath = "//*[@data-automation='sign-in-button']")
	WebElement loginButton;
	@FindBy(xpath = "//*[@data-automation='sign-in-email-inline-error-msg']")
	WebElement userNameErrorText;
	@FindBy(xpath = "//*[@data-automation='sign-in-password-inline-error-msg']")
	WebElement passwordErrorText;
	@FindBy(xpath = "//*[@aria-label='error']/following-sibling::div/p")
	WebElement errorText;

//	Action method for getting User name from the Excel file
	public String getUserName() {
		return getDataFromExcel("BestBuy_User_Details", "Email Address");
	}

//	Action method for getting Password from the Excel file
	public String getPassword() {
		return getDataFromExcel("BestBuy_User_Details", "Password");
	}

//	Action method to enter user details in the login page
	public void enterUserDetails(String[] inputs) {
		enterText(userName, inputs[0]);
		enterText(password, inputs[1]);
		moveToElement(loginButton);
		clickButton(loginButton);
	}

//	Action method to validate Alert text found after entering the credentials	
	public void validateErrorText(String Name, String expectedText) {
		takeScreenshot(driver);
		if (Name.equalsIgnoreCase("userName"))
			Assert.assertEquals(getTextContent(userNameErrorText), expectedText);
		else if (Name.equalsIgnoreCase("password"))
			Assert.assertEquals(getTextContent(passwordErrorText), expectedText);
		else if (Name.equalsIgnoreCase("Pop-up")){
			waitForSeconds(3);
			Assert.assertEquals(getTextContent(errorText), expectedText);
			takeScreenshotByMovingToElement(driver, errorText);
		}
	}
	
//	Action method to clear data from the Login Page fields
	public void clearData() {
		userName.clear();
		password.clear();
	}
}
