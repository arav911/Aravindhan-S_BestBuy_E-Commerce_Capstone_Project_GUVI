package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.SignUpPage;

//This class contains Test Cases for the Login page
public class LoginTests extends BaseTest {
//	Declaring object for Login, SignUp and Home page
	LoginPage login;
	HomePage homePage;
	SignUpPage signUp;

//	Negative Test Case
	@Test(priority = 1, testName = "Without entering User Details")
	public void without_entering_User_Details() {
//		Opens Login Page from the Landing Page
		landingPage.openLoginPage();

//		Creating object for LoginPage to access methods from its class
		login = new LoginPage(driver);

//		Login into the application without entering the User Details 
		String[] data = { "", "" };
		login.enterUserDetails(data);

//		Validating the errors populated with expected text
		login.validateErrorText("userName", "Please enter a valid email address.");
		login.validateErrorText("password",
				"Please enter your password. It must be 6 to 30 characters and contain at least one number and one letter.");
	}

//	Negative Test Case
	@Test(priority = 2, testName = "Entering blank Value in each field")
	public void entering_blank_value_in_each_Field() {

//		Opens Login Page from the Landing Page
		landingPage.openLoginPage();

//		Creating object for LoginPage to access methods from this class
		login = new LoginPage(driver);

//		Getting the User Details
		String userName = login.getUserName();
		String password = login.getPassword();

		String[] fields = { "userName", "password" };
		String[] data = { userName, password };
		String[] temp = new String[data.length];
		for (int i = 0; i < data.length; i++) {
			temp[i] = data[i];
		}
		
//		Entering the input in each of the fields and validating the error populated
		String[] errorTexts = { "Please enter a valid email address.",
				"Please enter your password. It must be 6 to 30 characters and contain at least one number and one letter." };
		int counter = 0;

		while (counter < fields.length) {
			data[counter] = "";
			login.enterUserDetails(data);
			login.validateErrorText(fields[counter], errorTexts[counter]);
			login.clearData();
			data[counter] = temp[counter];
			counter++;
		}
	}

//	Negative Test Case
	@Test(priority = 3, testName = "Entering Invalid User Name")
	public void entering_Invalid_User_Name() {
//		Opens Login Page from the Landing Page
		landingPage.openLoginPage();

//		Creating object for LoginPage to access methods from its class
		login = new LoginPage(driver);

//		Getting the User Details
		String userName = login.getUserName();
		String password = login.getPassword();

//		Entering the invalid User Name fields and validating the error populated
		String[] data = { "abcd" + userName, password };
		login.enterUserDetails(data);
		login.validateErrorText("Pop-Up",
				"Sorry, the e-mail address and password you entered don’t match. Please try again.");
	}

//	Negative Test Case
	@Test(priority = 4, testName = "Entering Invalid Password")
	public void entering_Invalid_Password() {
//		Opens Login Page from the Landing Page
		landingPage.openLoginPage();

//		Creating object for LoginPage to access methods from its class
		login = new LoginPage(driver);

//		Getting the User Details
		String userName = login.getUserName();
		String password = login.getPassword();

//		Entering the invalid Password and validating the error populated
		String[] data = { userName, "abcd" + password };
		login.enterUserDetails(data);
		login.validateErrorText("Pop-Up",
				"Sorry, the e-mail address and password you entered don’t match. Please try again.");
	}

	@Test(priority = 5, testName = "Entering Numerical Value in each field")
	public void entering_numerical_value_in_each_Field() {

//		Opens Login Page from the Landing Page
		landingPage.openLoginPage();

//		Creating object for LoginPage to access methods from this class
		login = new LoginPage(driver);

//		Getting the User Details
		String userName = login.getUserName();
		String password = login.getPassword();

		String[] fields = { "userName", "Pop-Up" };
		String[] data = { userName, password };
		String[] temp = new String[data.length];
		for (int i = 0; i < data.length; i++) {
			temp[i] = data[i];
		}
		
//		Entering the input in each of the fields and validating the error populated
		String[] errorTexts = { "Please enter a valid email address.",
				"Sorry, the e-mail address and password you entered don’t match. Please try again." };
		int counter = 0;

		while (counter < fields.length) {
			data[counter] = "1234567890";
			login.enterUserDetails(data);
			login.validateErrorText(fields[counter], errorTexts[counter]);
			login.clearData();
			data[counter] = temp[counter];
			counter++;
		}
	}

	@Test(priority = 6, testName = "Entering AlphaNumeric Value in each field")
	public void entering_AlphaNumeric_value_in_each_Field() {

//		Opens Login Page from the Landing Page
		landingPage.openLoginPage();

//		Creating object for LoginPage to access methods from this class
		login = new LoginPage(driver);

//		Getting the User Details
		String userName = login.getUserName();
		String password = login.getPassword();

		String[] fields = { "userName", "Pop-Up" };
		String[] data = { userName, password };
		String[] temp = new String[data.length];
		for (int i = 0; i < data.length; i++) {
			temp[i] = data[i];
		}
		
//		Entering the input in each of the fields and validating the error populated
		String[] errorTexts = { "Please enter a valid email address.",
				"Sorry, the e-mail address and password you entered don’t match. Please try again." };
		int counter = 0;

		while (counter < fields.length) {
			data[counter] = "abcde12345";
			login.enterUserDetails(data);
			login.validateErrorText(fields[counter], errorTexts[counter]);
			login.clearData();
			data[counter] = temp[counter];
			counter++;
		}
	}

	@Test(priority = 7, testName = "Entering Symbolic Value in each field")
	public void entering_Symbolic_value_in_each_Field() {

//		Opens Login Page from the Landing Page
		landingPage.openLoginPage();

//		Creating object for LoginPage to access methods from this class
		login = new LoginPage(driver);

//		Getting the User Details
		String userName = login.getUserName();
		String password = login.getPassword();

		String[] fields = { "userName", "Pop-Up" };
		String[] data = { userName, password };
		String[] temp = new String[data.length];
		for (int i = 0; i < data.length; i++) {
			temp[i] = data[i];
		}
		
//		Entering the input in each of the fields and validating the error populated
		String[] errorTexts = { "Please enter a valid email address.",
				"Sorry, the e-mail address and password you entered don’t match. Please try again." };
		int counter = 0;

		while (counter < fields.length) {
			data[counter] = "!@#$%^&*()";
			login.enterUserDetails(data);
			login.validateErrorText(fields[counter], errorTexts[counter]);
			login.clearData();
			data[counter] = temp[counter];
			counter++;
		}
	}

//	Positive Test Case
	@Test(priority = 8, testName = "Entering Valid User Details")
	public void entering_Valid_User_Details() {
//		Opens Login Page from the Landing Page
		landingPage.openLoginPage();

//		Creating objects for Login, SignUp and Home Page to access methods from their classes
		login = new LoginPage(driver);
		homePage = new HomePage(driver);
		signUp = new SignUpPage(driver);

//		Getting the User Details
		String userName = login.getUserName();
		String password = login.getPassword();

//		Validating the Profile Name with First Name
		String[] data = { userName, password };
		login.enterUserDetails(data);
		homePage.validateProfileName(signUp.getFirstName());
	}

}
