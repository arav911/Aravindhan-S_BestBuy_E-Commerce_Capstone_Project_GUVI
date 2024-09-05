package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import pageObjects.HomePage;
import pageObjects.SignUpPage;

//This class contains Test Cases for the Sign Up page
public class SignUpTests extends BaseTest {
//	Declaring object for Sign Up page
	SignUpPage signUp;

//	Positive Test Case
	@Test(priority = 1, testName = "Validating whether given URL is Broken or not")
	public void validating_whether_given_URL_is_Broken_or_not() {
//		Opens Browser and checking the URL is broken or not
		landingPage.validateURL(URL);
	}
	
//	Negative Test Case
	@Test(priority = 2, testName = "Without entering User Details")
	public void without_entering_User_Details() {
//		Opens Sign Up Page from the Landing Page
		landingPage.openSignUpPage();

//		Creating object for SignUpPage to access methods from its class
		signUp = new SignUpPage(driver);

//		Signing up the application without entering the User Details 
		String[] data = { "", "", "", "" };
		signUp.enterUserDetails(data);

//		Validating the Errors populated in each of the fields with expected text
		signUp.validateErrorText("firstName", "Please enter a valid first name.");
		signUp.validateErrorText("lastName", "Please enter a valid last name.");
		signUp.validateErrorText("email", "This is not a valid email address.");
		signUp.validateErrorText("password", "Please review the password requirements.");
	}

//	Negative Test Case
	@Test(priority = 3, testName = "Entering blank Value in each field")
	public void entering_blank_value_in_each_Field() {

//		Opens Sign Up Page from the Landing Page
		landingPage.openSignUpPage();

//		Creating object for SignUpPage to access methods from this class
		signUp = new SignUpPage(driver);

//		Getting the User Details
		String firstName = signUp.getFirstName();
		String lastName = signUp.getLastName();
		String emailAddress = signUp.getEmail();
		String password = signUp.getPassword();

		String[] fields = { "firstName", "lastName", "email", "password" };
		String[] data = { firstName, lastName, emailAddress, password };
		String[] temp = new String[data.length];
		for (int i = 0; i < data.length; i++) {
			temp[i] = data[i];
		}
		
//		Entering the input in each of the fields and validating the error populated
		String[] errorTexts = { "Please enter a valid first name.", "Please enter a valid last name.",
				"This is not a valid email address.", "Please review the password requirements." };
		int counter = 0;

		while (counter < fields.length) {
			data[counter] = "";
			signUp.enterUserDetails(data);
			signUp.validateErrorText(fields[counter], errorTexts[counter]);
			signUp.clearData();
			data[counter] = temp[counter];
			counter++;
		}
	}

//	Negative Test Case
	@Test(priority = 4, testName = "Entering Numerical Value in each field")
	public void entering_numerical_value_in_each_Field() {

//		Opens Sign Up Page from the Landing Page
		landingPage.openSignUpPage();

//		Creating object for SignUpPage to access methods from this class
		signUp = new SignUpPage(driver);

//		Getting the User Details
		String firstName = signUp.getFirstName();
		String lastName = signUp.getLastName();
		String emailAddress = signUp.getEmail();
		String password = signUp.getPassword();

		String[] fields = { "firstName", "lastName", "email", "password" };
		String[] data = { firstName, lastName, emailAddress, password };
		String[] temp = new String[data.length];
		for (int i = 0; i < data.length; i++) {
			temp[i] = data[i];
		}
		
//		Entering the input in each of the fields and validating the error populated
		String[] errorTexts = { "Please enter a valid first name.", "Please enter a valid last name.",
				"This is not a valid email address.", "Please review the password requirements." };
		int counter = 0;

		while (counter < fields.length) {
			data[counter] = "1234567890";
			signUp.enterUserDetails(data);
			signUp.validateErrorText(fields[counter], errorTexts[counter]);
			signUp.clearData();
			data[counter] = temp[counter];
			counter++;
		}
	}

//	Negative Test Case
	@Test(priority = 5, testName = "Entering AlphaNumeric Value in each field")
	public void entering_AlphaNumeric_value_in_each_Field() {

//		Opens Sign Up Page from the Landing Page
		landingPage.openSignUpPage();

//		Creating object for SignUpPage to access methods from this class
		signUp = new SignUpPage(driver);

//		Getting the User Details
		String firstName = signUp.getFirstName();
		String lastName = signUp.getLastName();
		String emailAddress = signUp.getEmail();
		String password = signUp.getPassword();

		String[] fields = { "firstName", "lastName", "email", "password" };
		String[] data = { firstName, lastName, emailAddress, password };
		String[] temp = new String[data.length];
		for (int i = 0; i < data.length; i++) {
			temp[i] = data[i];
		}
		
//		Entering the input in each of the fields and validating the error populated
		String[] errorTexts = { "Please enter a valid first name.", "Please enter a valid last name.",
				"This is not a valid email address.", "Please review the password requirements." };
		int counter = 0;

		while (counter < fields.length) {
			data[counter] = "abcde12345";
			signUp.enterUserDetails(data);
			signUp.validateErrorText(fields[counter], errorTexts[counter]);
			signUp.clearData();
			data[counter] = temp[counter];
			counter++;
		}
	}

//	Negative Test Case
	@Test(priority = 6, testName = "Entering Symbolic Value in each field")
	public void entering_Symbolic_value_in_each_Field() {

//		Opens Sign Up Page from the Landing Page
		landingPage.openSignUpPage();

//		Creating object for SignUpPage to access methods from this class
		signUp = new SignUpPage(driver);

//		Getting the User Details
		String firstName = signUp.getFirstName();
		String lastName = signUp.getLastName();
		String emailAddress = signUp.getEmail();
		String password = signUp.getPassword();

		String[] fields = { "firstName", "lastName", "email", "password" };
		String[] data = { firstName, lastName, emailAddress, password };
		String[] temp = new String[data.length];
		for (int i = 0; i < data.length; i++) {
			temp[i] = data[i];
		}
		
//		Entering the input in each of the fields and validating the error populated
		String[] errorTexts = { "Please enter a valid first name.", "Please enter a valid last name.",
				"This is not a valid email address.", "Please review the password requirements." };
		int counter = 0;

		while (counter < fields.length) {
			data[counter] = "!@#$%^&*()";
			signUp.enterUserDetails(data);
			signUp.validateErrorText(fields[counter], errorTexts[counter]);
			signUp.clearData();
			data[counter] = temp[counter];
			counter++;
		}
	}

//	Positive Test Case
	@Test(priority = 7, testName = "Entering Valid User Details")
	public void entering_Valid_User_Details() {
//		Opens Sign Up Page from the Landing Page
		landingPage.openSignUpPage();

//		Creating object for SignUpPage to access methods from its class
		signUp = new SignUpPage(driver);

//		Getting the User Details
		String firstName = signUp.getFirstName();
		String lastName = signUp.getLastName();
		String emailAddress = signUp.getEmail();
		String password = signUp.getPassword();

		String[] data = { firstName, lastName, emailAddress, password };
		signUp.enterUserDetails(data);

//		Validating the Profile Name with First Name
		HomePage homePage = new HomePage(driver);
		homePage.validateProfileName(firstName);		
	}
	
//	Negative Test Case
	@Test(priority = 8, testName = "Already Existed User Details")
	public void already_Existed_User_Details() {
//		Opens Sign Up Page from the Landing Page
		landingPage.openSignUpPage();

//		Creating object for SignUpPage to access methods from its class
		signUp = new SignUpPage(driver);

//		Getting the User Details
		String firstName = signUp.getFirstName();
		String lastName = signUp.getLastName();
		String emailAddress = signUp.getEmail();
		String password = signUp.getPassword();

		String[] data = { firstName, lastName, emailAddress, password };
		signUp.enterUserDetails(data);

//		Validating the error populated with expected text
		signUp.validateErrorText("Pop-up",
				"An account with this email address already exists. Please use it to sign in or enter a new email to continue.");
	}
	
//	Positive Test Case
	@Test(priority = 9, testName = "Password Checklist Validation")
	public void password_Checklist_Validation() {
//		Opens Sign Up Page from the Landing Page
		landingPage.openSignUpPage();

//		Creating object for SignUpPage to access methods from its class
		signUp = new SignUpPage(driver);

//		Getting the User Details
		String password = signUp.getPassword();

//		Performing the Password Checklist validation
		String[] data = {"", "Tusr@1", "@1234567", "Testuser@", "Testuser123456", password};
		signUp.validatePasswordChecklist(data);
	}

}
