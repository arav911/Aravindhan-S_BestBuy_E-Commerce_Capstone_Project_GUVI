package pageObjects;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BaseComponents;

//This class contains Page objects for the Payment page
public class PaymentPage extends BaseComponents {
//	Declaring object for WebDriver
	WebDriver driver;
	HashMap<String, String> data;

//	Constructor for PaymentPage class to assign "driver" and PageFactory
	public PaymentPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

//	Storing Web Elements in Payment page of the BestBuy application using @FindBy annotation
	@FindBy(xpath = "//*[@data-automation='lineitem-parentproduct']")
	List<WebElement> productLists;
	@FindBy(xpath = "//*[@data-automation='go-to-cart']")
	WebElement goToCartButton;
	@FindBy(xpath = "//*[@data-automation='continue-shopping']")
	WebElement continueShoppingButton;
	@FindBy(xpath = "//*[@data-automation='continue-to-checkout']")
	WebElement continueToCheckOutButton;
	@FindBy(xpath = "//*[@data-automation='paypal-button']")
	WebElement payPalButton;
	@FindBy(xpath = "//*[@data-automation='guest-continue']")
	WebElement guestContinueButton;
	@FindBy(id = "onetrust-close-btn-container")
	WebElement privacyPolicyCloseButton;
	@FindBy(id = "email")
	WebElement checkOutEmailID;
	@FindBy(xpath = "//label[@for='email']//following-sibling::div[@class='error-msg']")
	WebElement checkOutEmailIDError;
	@FindBy(id = "firstName")
	WebElement checkOutFirstName;
	@FindBy(xpath = "//*[@data-automation='firstNameInput-inline-error-msg']")
	WebElement checkOutFirstNameError;
	@FindBy(id = "lastName")
	WebElement checkOutLastName;
	@FindBy(xpath = "//*[@data-automation='lastNameInput-inline-error-msg']")
	WebElement checkOutLastNameError;
	@FindBy(id = "phoneNumber")
	WebElement checkOutPhoneNumber;
	@FindBy(xpath = "//*[@data-automation='phoneInput-inline-error-msg']")
	WebElement checkOutPhoneNumberError;
	@FindBy(id = "addressLine1")
	WebElement checkOutAddress;
	@FindBy(xpath = "//*[@data-automation='addressInput-inline-error-msg']")
	WebElement checkOutAddressError;
	@FindBy(id = "city")
	WebElement checkOutCity;
	@FindBy(xpath = "//*[@data-automation='cityInput-inline-error-msg']")
	WebElement checkOutCityError;
	@FindBy(id = "regionCode")
	WebElement checkOutProvince;
	@FindBy(xpath = "//*[@data-automation='regionCodeSelector-inline-error-msg']")
	WebElement checkOutProvinceError;
	@FindBy(id = "postalCode")
	WebElement checkOutPostalCode;
	@FindBy(xpath = "//*[@data-automation='postalCodeInput-inline-error-msg']")
	WebElement checkOutPostalCodeError;
	@FindBy(xpath = "//*[@data-automation='continue-to-payment']")
	WebElement continueToPaymentButton;
	
	@FindBy(id = "shownCardNumber")
	WebElement cardNumber;
	@FindBy(id = "expirationMonth")
	WebElement cardExpMonth;
	@FindBy(id = "expirationYear")
	WebElement cardExpYear;
	@FindBy(id = "cvv")
	WebElement cardCVV;
	@FindBy(xpath = "//*[@data-automation='continue-to-review']")
	WebElement continueToReviewButton;
	@FindBy(xpath = "//*[@data-automation='review-order']")
	WebElement continueToReviewOrderButton;
	@FindBy(xpath = "//*[@for='shownCardNumber']//following-sibling::div[@class='error-msg']")
	WebElement cardNumberError;
	
//	Action method to get Payment details to place an order successfully using the application 
	void getCheckOutDetails(int rowNum) {
		data = getDataFromExcel("BestBuy_Order_Details", rowNum);
	}

//	Action method to fill the payment details with provided Card Details
	public void proceedToPayment(int rowNum) {
		getCheckOutDetails(rowNum);
		waitForSeconds(7);
		for(WebElement product: productLists)
			takeScreenshotByMovingToElement(driver, product);
		clickButton(continueToCheckOutButton);
		
//		Continuing as Guest to place an order
		clickButton(guestContinueButton);
		if(isElementPresent(privacyPolicyCloseButton))
			clickButton(privacyPolicyCloseButton);
		
//		Filling out the personal details in Checkout Section
		enterText(checkOutEmailID, data.get("Email ID"));
		if(!data.get("Expected Error").isEmpty())
			validateTextContent(checkOutEmailIDError, data.get("Expected Error"));
		
		enterText(checkOutFirstName, data.get("First Name"));
		if(!data.get("Expected Error").isEmpty())
			validateTextContent(checkOutFirstNameError, data.get("Expected Error"));
		
		enterText(checkOutLastName, data.get("Last Name"));
		if(!data.get("Expected Error").isEmpty())
			validateTextContent(checkOutLastNameError, data.get("Expected Error"));
		
		enterText(checkOutPhoneNumber, data.get("Phone Number"));
		if(!data.get("Expected Error").isEmpty())
			validateTextContent(checkOutPhoneNumberError, data.get("Expected Error"));
		
//		if postal code is entered, it automatically suggests the remaining Address Details
		enterText(checkOutPostalCode, data.get("Postal Code"));
		if(!data.get("Expected Error").isEmpty())
			validateTextContent(checkOutPostalCodeError, data.get("Expected Error"));
		keyBoardActions("Enter");
		keyBoardActions("Enter");
		
		enterText(checkOutAddress, data.get("Address"));
		if(!data.get("Expected Error").isEmpty())
			validateTextContent(checkOutAddressError, data.get("Expected Error"));
		
		if(checkOutCity.getAttribute("value").isEmpty())
			enterText(checkOutCity, data.get("City"));
		if(!data.get("Expected Error").isEmpty())
			validateTextContent(checkOutCityError, data.get("Expected Error"));
		
		chooseDropdownValue(checkOutProvince, data.get("Province"));
		if(!data.get("Expected Error").isEmpty())
			validateTextContent(checkOutProvinceError, data.get("Expected Error"));
		
//		Proceeding to payment section after the checkout details were filled successfully
		clickButton(continueToPaymentButton);
		waitForSeconds(7);
		takeScreenshot(driver);
		if(isElementPresent(continueToReviewButton))
			clickButton(continueToReviewButton);
//		Filling the Payment details
		enterText(cardNumber, data.get("Card Number"));
		chooseDropdownValue(cardExpMonth, data.get("Card Expiry Month"));
		chooseDropdownValue(cardExpYear, data.get("Card Expiry Year"));
		enterText(cardCVV, data.get("CVV"));
		clickButton(continueToReviewOrderButton);
//		Validating the error message populated after Payment details filled
		if(!data.get("Expected Error").isEmpty())
			validateTextContent(cardNumberError, data.get("Expected Error"));
	}
	
}
