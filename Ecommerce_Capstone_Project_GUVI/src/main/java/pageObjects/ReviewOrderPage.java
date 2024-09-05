package pageObjects;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import base.BaseComponents;

//This class contains ReviewOrderPage objects for the Home page
public class ReviewOrderPage extends BaseComponents {
//	Declaring object for WebDriver and HashMap<String, String>
	WebDriver driver;
	HashMap<String, String> data;

//	Constructor for ReviewOrderPage class to assign "driver" and PageFactory
	public ReviewOrderPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

//	Storing Web Elements in ReviewOrder page of the BestBuy application using @FindBy annotation
	@FindBy(xpath = "//h2/following-sibling::summary")
	WebElement personalDetailsSummary;
	@FindBy(xpath = "//*[@class='shipment-details']")
	List<WebElement> productListsSummary;

	@FindBy(xpath = "//*[@class='card-details']")
	WebElement cardDetailsSummary;
	@FindBy(xpath = "//*[text()='Place Order']")
	WebElement placeOrderButton;
	@FindBy(xpath = "//*[@data-automation='error']//p")
	WebElement confirmationMessage;
	@FindBy(id = "onetrust-close-btn-container")
	WebElement privacyPolicyCloseButton;

//	Action method to get Product details to validate with the Review Page 
	void getOrderDetails(int rowNum) {
		data = getDataFromExcel("BestBuy_Order_Details", rowNum);
	}

//	Action method to validate the Personal and Payment Details
	public void reviewOrderDetails(int rowNum) {
		getOrderDetails(rowNum);
		waitForSeconds(7);
		
//		Validating Personal details filled in the Checkout Section
		if ((getTextContent(personalDetailsSummary).contains(data.get("First Name")))
				&& (getTextContent(personalDetailsSummary).contains(data.get("Last Name")))
				&& (getTextContent(personalDetailsSummary).contains(data.get("Address")))
				&& (getTextContent(personalDetailsSummary).contains(data.get("City")))
				&& (getTextContent(personalDetailsSummary).contains(data.get("Postal Code")))) {
			Assert.assertTrue(true, "Shipping Address Details are Matching successfully");
		} else {
			Assert.assertTrue(false, "Shipping Address Details are Not Matching");
		}
		
//		Validating Product Details which were added to the Cart based on the Quantity
		for (int i=0; i<productListsSummary.size(); i++) {
			takeScreenshotByMovingToElement(driver, productListsSummary.get(i));
			WebElement productName = findSubElementUsingXpath(productListsSummary.get(i),
					"//div[@class='detail']//div[@class='name']");
			validateTextContent(productName, data.get("Selected Product Name"+(i+1)));
			WebElement productPrice = findSubElementUsingXpath(productListsSummary.get(i),
					"//div[@class='detail']//div[@class='priceValue isOnSale']");
			validateTextContent(productPrice, data.get("Selected Product Price"+(i+1)));
		}
		
//		Validating the Card Details filled in the Payment Page
		String digits = data.get("Card Number");
		digits = digits.substring(digits.length() - 4);
		if (getTextContent(cardDetailsSummary).contains(digits)) {
			Assert.assertTrue(true, "Shipping Address Details are Matching successfully");
		} else {
			Assert.assertTrue(false, "Shipping Address Details are Not Matching");
		}
		
//		Placing the Order after successfully reviewed the details
		clickButton(placeOrderButton);
		waitForElementToAppear(confirmationMessage);
		takeScreenshot(driver);
//		Validating the Order Confirmation messasge 
		validateTextContent(confirmationMessage, data.get("Expected Message"));
	}

}
