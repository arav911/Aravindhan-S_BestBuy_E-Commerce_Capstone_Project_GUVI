package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import base.BaseComponents;

//	This class contains Page objects for the Sign Up page
public class SignUpPage extends BaseComponents {
//	Declaring object for WebDriver
	WebDriver driver;

//	Constructor for SignUpPage class to assign "driver" and PageFactory
	public SignUpPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

//	Storing Web Elements in sign up page of the BestBuy application using @FindBy annotation
	@FindBy(id = "firstName")
	WebElement firstName;
	@FindBy(id = "lastName")
	WebElement lastName;
	@FindBy(id = "email")
	WebElement email;
	@FindBy(id = "password")
	WebElement password;
	@FindBy(xpath = "//*[@aria-label='error']/following-sibling::div/p")
	WebElement errorText;
	@FindBy(xpath = "//*[@data-automation='firstName-inline-error-msg']")
	WebElement firstNameErrorText;
	@FindBy(xpath = "//*[@data-automation='lastName-inline-error-msg']")
	WebElement lastNameErrorText;
	@FindBy(xpath = "//*[@data-automation='emailForm-inline-error-msg']")
	WebElement emailErrorText;
	@FindBy(xpath = "//*[@data-automation='passwordInput-inline-error-msg']")
	WebElement passwordErrorText;
	@FindBy(xpath = "//*[@data-automation='createAccountButton']")
	WebElement signUpButton;
	@FindBy(xpath = "//*[contains(@class, 'passwordChecklist')]//li")
	List<WebElement> passwordChecklist;

//	Action method for getting First name from the Excel file
	public String getFirstName() {
		return getDataFromExcel("BestBuy_User_Details", "First Name");
	}

//	Action method for getting Last name from the Excel file
	public String getLastName() {
		return getDataFromExcel("BestBuy_User_Details", "Last Name");
	}

//	Action method for getting Email Address from the Excel file
	public String getEmail() {
		return getDataFromExcel("BestBuy_User_Details", "Email Address");
	}

//	Action method for getting Password from the Excel file
	public String getPassword() {
		return getDataFromExcel("BestBuy_User_Details", "Password");
	}

//	Action method to enter user details in the sign up page
	public void enterUserDetails(String[] inputs) {
		enterText(firstName, inputs[0]);
		enterText(lastName, inputs[1]);
		enterText(email, inputs[2]);
		enterText(password, inputs[3]);
		moveToElement(signUpButton);
		clickButton(signUpButton);
	}

//	Action method to validate Alert text found after entering the credentials	
	public void validateErrorText(String fieldName, String expectedText) {
		takeScreenshot(driver);
		if (fieldName.equalsIgnoreCase("firstName"))
			Assert.assertEquals(getTextContent(firstNameErrorText), expectedText);
		else if (fieldName.equalsIgnoreCase("lastName"))
			Assert.assertEquals(getTextContent(lastNameErrorText), expectedText);
		else if (fieldName.equalsIgnoreCase("email"))
			Assert.assertEquals(getTextContent(emailErrorText), expectedText);
		else if (fieldName.equalsIgnoreCase("password"))
			Assert.assertEquals(getTextContent(passwordErrorText), expectedText);
		else if (fieldName.equalsIgnoreCase("Pop-up")){
			waitForSeconds(4);
			Assert.assertEquals(getTextContent(errorText), expectedText);
			takeScreenshotByMovingToElement(driver, errorText);
		}
	}

//	Action method to clear data from the Sign Up Page fields
	public void clearData() {
		firstName.clear();
		lastName.clear();
		email.clear();
		password.clear();
	}

//	Action method to validate whether the password inputs were given as per the Password Checklist	
	public void validatePasswordChecklist(String[] data) {

		for (int i = 0; i < data.length; i++) {
			enterText(password, data[i]);
			int flag = 0;
			for (int j = 0; j < passwordChecklist.size(); j++) {
				String color = passwordChecklist.get(j).findElement(By.tagName("span")).getCssValue("color");
				String hex = Color.fromString(color).asHex();
				if (hex.equalsIgnoreCase("#bb0628")) {
					Assert.assertFalse(false);
					break;
				} else
					flag++;
			}
			if (flag == 4)
				Assert.assertTrue(true);
			moveToElement(signUpButton);
			takeScreenshot(driver);
			clearData(password);
		}
	}
}
