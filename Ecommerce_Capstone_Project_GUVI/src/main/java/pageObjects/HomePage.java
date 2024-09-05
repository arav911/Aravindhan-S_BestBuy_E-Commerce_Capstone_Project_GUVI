package pageObjects;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import base.BaseComponents;

//This class contains Page objects for the Home page
public class HomePage extends BaseComponents {
//	Declaring objects for WebDriver, Random and HashMap<String, String>
	WebDriver driver;
	Random random = new Random();
	HashMap<String, String> data;

//	Creating object for SoftAssert to execute a Test Case without getting interrupted even the Assertion gets failed in the middle
	SoftAssert softAssert = new SoftAssert();

//	Constructor for HomePage class to assign "driver" and PageFactory
	public HomePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

//	Storing Web Elements in Home page of the BestBuy application using @FindBy annotation
	@FindBy(linkText = "Sign up")
	WebElement signUpPage;
	@FindBy(linkText = "Log in")
	WebElement logInPage;
	@FindBy(xpath = "//*[@data-automation='account-greeting']/span")
	WebElement profileName;
	@FindBy(xpath = "//*[@data-automation='x-search-input']")
	WebElement searchBar;
	@FindBy(xpath = "//*[@data-automation='x-search-submit']")
	WebElement searchButton;
	@FindBy(xpath = "//*[contains(@class,'productsRow')]//a//p[contains(@class,'shippingAvailability')]//*[contains(text(),'Available')]")
	List<WebElement> searchItems;

	@FindBy(xpath = "//*[contains(@class,'style-module_globalFooter')]//a")
	List<WebElement> bottomLinks;
	@FindBy(tagName = "h1")
	WebElement productSelectedName;
	@FindBy(xpath = "//div[contains(@class,'pricingContainer')]//div[contains(@class,'salePrice')]")
	WebElement productSelectedPrice;
	@FindBy(xpath = "//*[@data-automation='addToCartButton']")
	WebElement addToCartButton;
	@FindBy(xpath = "//div[contains(@class, 'item-added-message')]//p")
	WebElement confirmMessage;
	@FindBy(xpath = "//*[@data-automation='go-to-cart']")
	WebElement goToCartButton;
	@FindBy(xpath = "//*[@data-automation='continue-shopping']")
	WebElement continueShoppingButton;

	@FindBy(xpath = "//*[contains(@class, 'flyoutNavigationGroup')]//img//parent::span")
	List<WebElement> mainMenu;
	@FindBy(xpath = "//h2[text()='Shop by Category']//following-sibling::a[contains(@class,'menuLink')]")
	List<WebElement> categoryMenuList;
	@FindBy(linkText = "Brands")
	WebElement brandsMenu;

	@FindBy(xpath = "//*[contains(@class,'facetsContainer')]//button[@data-automation='expandable-button']")
	List<WebElement> filterOptions;
	@FindBy(xpath = "//*[contains(@class,'compactButton')]")
	WebElement categoryShowAllButton;
	@FindBy(xpath = "//*[@id='availability-toggle']//following-sibling::span")
	WebElement inStockOption;
	@FindBy(xpath = "//*[@id='bestbuy-only-toggle']//following-sibling::span")
	WebElement bestBuyOnlyOption;
	@FindBy(id = "Sort")
	WebElement sortFilter;

//	Action method to get Product details to perform Shopping and place an order in the BestBuy application 
	void getOrderDetails(int rowNum) {
		data = getDataFromExcel("BestBuy_Order_Details", rowNum);
	}

//	Action method to validate the Profile name under profile section, after successfully logged in
	public void validateProfileName(String expectedText) {
		waitForSeconds(4);
		takeScreenshot(driver);
		expectedText = "Hi, " + expectedText.substring(0, 1) + expectedText.substring(1).toLowerCase() + "!";
//		Explicitly waiting for the profileName element to appear
		waitForElementToAppear(profileName);
		softAssert.assertEquals(getTextContent(profileName), expectedText);
		softAssert.assertAll();
	}

//	Actions method to perform validating the Bottom Links available in the Home Page
	String reportInfo = "";

	public void validateBottomLinks() {
//		collecting all the Request Status of each bottom link
		for (WebElement bottomLink : bottomLinks) {
			String url = bottomLink.getAttribute("href");
			reportInfo = "\n" + reportInfo + checkBrokenLink(url);
			System.out.println("----------------------------------");
		}
//		Storing the Request Status as a Report Info to attach and display them in the report 
		ITestResult itr = Reporter.getCurrentTestResult();
		itr.setAttribute("reportInfo", reportInfo);
	}

//	Action method to explore all the Item under all the Menu options
	String expectedTitle;

	public void navigateToAllTheMenu() {
		for (int i = 1; i < mainMenu.size(); i++) {
//			clicks on the Main Menu
			clickButton(mainMenu.get(i));
//			collecting the Inner Menu options
			List<WebElement> innerMenuList = findSubElementListUsingXpath(mainMenu.get(i),
					"ancestor::li//a[contains(@class,'menuLink')]");
			for (int j = 0; j < innerMenuList.size(); j++) {
				expectedTitle = innerMenuList.get(j).getText();
				System.out.println("Main Menu: " + (i + 1) + " - " + mainMenu.get(i).getText() + "\nInner Menu: "
						+ (j + 1) + " - " + expectedTitle);
				clickButton(innerMenuList.get(j));
				if (i != mainMenu.size() - 1)
					waitForElementToAppear(productSelectedName);
//				Validating the Page title with the Title under Menu option
				if (getPageTitle().contains(expectedTitle))
					softAssert.assertTrue(true);
				else
					softAssert.assertTrue(false,
							"\"" + getPageTitle() + "\"" + " not Matches with " + "\"" + expectedTitle + "\"" + "...");
				takeScreenshot(driver);
				if (!isElementPresent(mainMenu.get(i), "//ancestor::li[contains(@class, 'Visible')]")) {
					clickButton(mainMenu.get(i));
				}
				innerMenuList = findSubElementListUsingXpath(mainMenu.get(i),
						"ancestor::li//a[contains(@class,'menuLink')]");
			}
		}
//		Used Soft Assertion to not interrupt the test execution flow
		softAssert.assertAll();
	}

//	Action method to search for a product Item
	public void searchForProduct(int rowNum) {
		getOrderDetails(rowNum);
		enterText(searchBar, data.get("Product Name"));
		clickButton(searchButton);
		waitForSeconds(20);
		takeScreenshot(driver);

	}

//	Action method to explore the Items which were available in Category wise
	int index;

	public void goToShopByDepartmentMenu() {
//		Opening Shop Main menu
		for (WebElement menu : mainMenu) {
			if (getTextContent(menu).equalsIgnoreCase("Shop")) {
				menu.click();
				break;
			}
		}
//		Selecting a Category menu randomly
		index = random.nextInt(categoryMenuList.size());
		String title = categoryMenuList.get(index).getText().trim();
		if (title.contains(","))
			title = title.toLowerCase().replace(",", "");
		title = title.toLowerCase().replace(" ", "-");
		String listXpath = "//following-sibling::div[contains(@class,'menuInner')]//div[contains(@data-automation,'"
				+ title + "')]";
//		if a Category is found to be having Inner Menu, this block will be executed
		if (isElementPresent(categoryMenuList.get(index), listXpath)) {
			System.out.println("Category Menu: " + (index + 1) + " - " + categoryMenuList.get(index).getText());
			clickButton(categoryMenuList.get(index));
			takeScreenshot(driver);
//			collecting the Inner Menu List under selected Category
			List<WebElement> subMenuList = findSubElementListUsingXpath(categoryMenuList.get(index),
					"//following-sibling::div[contains(@class,'menuInnerActive')]//a[contains(@class,'menuLink')]");
//			Selecting a Inner menu randomly from the selected Category
			index = random.nextInt(subMenuList.size());
			title = subMenuList.get(index).getText().trim();
			if (title.contains(","))
				title = title.toLowerCase().replace(",", "");
			title = title.toLowerCase().replace(" ", "-");
			listXpath = "//a[contains(@class,'menuLink')]//following-sibling::div[contains(@class,'menuInner')]//div[contains(@data-automation,'"
					+ title + "')]";
//			if the Inner Menu is found to be having Sub Inner Menu, this block will be executed
			if (isElementPresent(subMenuList.get(index), listXpath)) {
				System.out.println("Sub Category Menu: " + (index + 1) + " - " + subMenuList.get(index).getText());
				clickButton(subMenuList.get(index));
				takeScreenshot(driver);
//				collecting the Sub Inner Menu List under selected Category
				List<WebElement> subSubMenuList = findSubElementListUsingXpath(subMenuList.get(index),
						"//following-sibling::div[contains(@class,'menuInnerActive')]//a[contains(@class,'menuLink')]"
								+ "//following-sibling::div[contains(@class,'menuInnerActive')]//a[contains(@class,'menuLink')]");
//				Selecting a Sub Inner menu randomly
				index = random.nextInt(subSubMenuList.size());
				System.out
						.println("Sub Sub Category Menu: " + (index + 1) + " - " + subSubMenuList.get(index).getText());
				clickButton(subSubMenuList.get(index));
				waitForElementToAppear(productSelectedName);
				takeScreenshot(driver);
			} else {
//				this block would be executed when the Inner Menu is not having Sub Inner Menu
				clickButton(subMenuList.get(index));
				waitForElementToAppear(productSelectedName);
				takeScreenshot(driver);
			}
		} else {
//			this block would be executed when the Category is not having Inner Menu
			clickButton(categoryMenuList.get(index));
			waitForElementToAppear(productSelectedName);
			takeScreenshot(driver);
		}
	}

//	Action method explore the Product Items in Alphabetical order 
	public void goToBrandsMenu() {
//		Opening Shop Main menu
		for (WebElement menu : mainMenu) {
			if (getTextContent(menu).equalsIgnoreCase("Shop")) {
				menu.click();
				break;
			}
		}
		System.out.println("Brands Menu...");
//		Opening the Brands section under Shop Main menu
		clickButton(brandsMenu);
		takeScreenshot(driver);
//		collecting the Alphabet Index List under Brands section
		List<WebElement> subMenuList = findSubElementListUsingXpath(brandsMenu,
				"//following-sibling::div[contains(@class,'menuInnerActive')]//*[local-name()='svg']/parent::a[contains(@class,'menuLink')]");
//		Selecting an Alphabet Index randomly
		index = random.nextInt(subMenuList.size());
		System.out.println("Sub Category Menu: " + (index + 1) + " - " + subMenuList.get(index).getText());
		clickButton(subMenuList.get(index));
		takeScreenshot(driver);
//		collecting the Product Item Names List under Brands section
		List<WebElement> subSubMenuList = findSubElementListUsingXpath(subMenuList.get(index),
				"//following-sibling::div[contains(@class,'menuInnerActive')]//a[contains(@class,'menuLink')]");
//		Selecting a Product Item randomly
		index = random.nextInt(subSubMenuList.size());
		System.out.println("Sub Sub Category Menu: " + (index + 1) + " - " + subSubMenuList.get(index).getText());
		clickButton(subSubMenuList.get(index));
		waitForElementToAppear(productSelectedName);
		takeScreenshot(driver);

	}

//	Action method to perform Filtering the Product Items Search results
	public void filteringTheResults(int rowNum) {
		getOrderDetails(rowNum);
		waitForAllElementsToAppear(searchItems);
		takeScreenshot(driver);
//		this block gets executed if "In Stock" option is needs to be selected
		if (data.get("In Stock") != null && data.get("In Stock").equalsIgnoreCase("Yes")) {
			clickButton(inStockOption);
			waitForAllElementsToAppear(searchItems);
			takeScreenshot(driver);
		}
//		this block gets executed if "Best Buy Only" option is needs to be selected
		if (data.get("Best Buy Only") != null && data.get("Best Buy Only").equalsIgnoreCase("Yes")) {
			clickButton(bestBuyOnlyOption);
			waitForAllElementsToAppear(searchItems);
			takeScreenshot(driver);
		}
//		this block gets executed if Product search results needs to be Sorted
		if (data.get("Sort") != null && data.get("Sort").equalsIgnoreCase("Yes")) {
			List<WebElement> dropDownOptions = findSubElementListUsingXpath(sortFilter, "//option");
			index = random.nextInt(dropDownOptions.size());
			chooseDropdownValue(sortFilter, index);
			waitForAllElementsToAppear(searchItems);
			takeScreenshot(driver);
		}
//		Selecting an Filter Option randomly		
		index = random.nextInt(filterOptions.size());
		if (index != 0 || index != 1 || index != 2)
			clickButton(filterOptions.get(index));
		if (index == 0 && isElementPresent(categoryShowAllButton))
			clickButton(categoryShowAllButton);
//		collecting the Inner Filter List options
		List<WebElement> subFilterOptions = findSubElementListUsingXpath(filterOptions.get(index),
				"following-sibling::div[contains(@class,'bodyContainer')]//*[contains(@class,'productName')]");
		index = random.nextInt(subFilterOptions.size());
		clickButton(subFilterOptions.get(index));
		waitForAllElementsToAppear(searchItems);
		takeScreenshot(driver);
	}

//	Action method to Add a chosen Product Item to the Cart  
	int count;
	public static int productIndex = 1;
	public void addToCart() {
		waitForAllElementsToAppear(searchItems);
		int index = random.nextInt(searchItems.size());
		clickButton(searchItems.get(index));
		waitForSeconds(10);
		takeScreenshot(driver);
		data.put("Selected Product Name" + productIndex, getTextContent(productSelectedName));
		data.put("Selected Product Price" + productIndex, getTextContent(productSelectedPrice));
		if (data.get("Quantity") != null)
			count = Integer.parseInt(data.get("Quantity"));
		int i = 1;
		do {
			clickButton(addToCartButton);
			i++;
		} while (i < count);
		waitForSeconds(7);
		takeScreenshot(driver);
		productIndex++;
		validateTextContent(confirmMessage, "This item has been added to your cart.");
	}

//	Action method to continue to shopping page after item is added to cart
	public void continueTheShopping() {
		clickButton(continueShoppingButton);
	}
//	Action method to go to Cart section
	public void goToCartSection() {
		clickButton(goToCartButton);
	}

}
