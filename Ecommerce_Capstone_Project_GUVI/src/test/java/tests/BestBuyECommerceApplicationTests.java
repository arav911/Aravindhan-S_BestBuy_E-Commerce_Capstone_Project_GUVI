package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import pageObjects.HomePage;
import pageObjects.PaymentPage;
import pageObjects.ReviewOrderPage;

//This class contains Test Cases for the Home page of the BestBuy ECommerce Application
public class BestBuyECommerceApplicationTests extends BaseTest{
//	Declaring object for Home, Payment and Reviewing Order pages
	HomePage homePage;
	PaymentPage paymentPage;
	ReviewOrderPage reviewOrderPage;

//	Positive Test Case
	@Test(priority = 1, testName = "Navigation of all Menu and Validation of Title in each Page")
	public void navigation_of_all_Menu_and_Validation_of_Title_in_each_Page() {
//		Creating object for HomePage to access methods from its class
		homePage = new HomePage(driver);
		
//		Opening the HomePage and exploring all the Menu items available there
		landingPage.openHomePage();
		homePage.navigateToAllTheMenu();
	}
	
//	Positive Test Case
	@Test(priority = 2, testName = "Validating Bottom Links")
	public void validating_Bottom_Links() {
//		Creating object for HomePage to access methods from its class
		homePage = new HomePage(driver);
		
//		Opening the HomePage and validating all the bottom links available there
		landingPage.openHomePage();
		homePage.validateBottomLinks();
	}
	
//	Positive Test Case
	@Test(priority = 3, testName = "Search for an Item filtering the Results")
	public void search_for_an_Item_filtering_the_Results() {
//		Creating object for HomePage to access methods from its class
		homePage = new HomePage(driver);
		
//		Opening the HomePage		
		landingPage.openHomePage();
//		Searching for an Item
		homePage.searchForProduct(1);
//		Filtering the Search Item Results
		homePage.filteringTheResults(1);
	}
	
//	Positive Test Case
	@Test(priority = 4, testName = "Search for and Add an Item to Shopping Cart")
	public void search_for_and_Add_an_Item_to_Shopping_Cart() {
//		Creating object for HomePage to access methods from its class
		homePage = new HomePage(driver);
		
//		Opening the Home Page
		landingPage.openHomePage();
//		Searching for an Item
		homePage.searchForProduct(1);
//		Adding an Item to the Cart 
		homePage.addToCart();
	}
	
//	Positive Test Case
	@Test(priority = 5, testName = "Search for and Add an Item to Shopping Cart from Shop by Department Menu")
	public void search_for_and_Add_an_Item_to_Shopping_Cart_from_Shop_by_Department_Menu() {
//		Creating object for HomePage to access methods from its class
		homePage = new HomePage(driver);
		
//		Opening the Home Page
		landingPage.openHomePage();
//		Searching for an Item based on Department Category from the Menu
		homePage.goToShopByDepartmentMenu();
//		Adding an item to the Cart
		homePage.addToCart();
	}
	
//	Positive Test Case
	@Test(priority = 6, testName = "Search for and Add an Item to Shopping Cart from Any Brands Menu")
	public void search_for_and_Add_an_Item_to_Shopping_Cart_from_Any_Brands_Menu() {
//		Creating object for HomePage to access methods from its class
		homePage = new HomePage(driver);
		
//		Opening the Home Page
		landingPage.openHomePage();
//		Searching for an Item based on Brands from the Menu
		homePage.goToBrandsMenu();
//		Adding an Item to the Cart
		homePage.addToCart();
	}
	
//	Positive Test Case
	@Test(priority = 7, testName = "Proceed to Payment with Valid Details after Items added to Cart")
	public void proceed_to_Payment_with_Valid_Details_after_Items_added_to_Cart() {
//		Creating object for Home, Payment and ReviewOrder Pages to access methods from its class
		homePage = new HomePage(driver);
		paymentPage = new PaymentPage(driver);
		reviewOrderPage = new ReviewOrderPage(driver);
		
//		Opening the Home Page
		landingPage.openHomePage();
//		Searching for an Item
		homePage.searchForProduct(1);
//		Adding an Item to the Cart
		homePage.addToCart();
//		Searching for an Item based on Department Category from the Menu
		homePage.goToShopByDepartmentMenu();
//		Adding an Item to the Cart
		homePage.addToCart();
//		Searching for an Item based on Brands from the Menu
		homePage.goToBrandsMenu();
//		Adding an Item to the Cart
		homePage.addToCart();
//		Proceeding to Payment Process using Valid Card Details
		paymentPage.proceedToPayment(1);
//		Reviewing and Placing the order after Payment is successful
		reviewOrderPage.reviewOrderDetails(1);
		
	}
	
//	Negative Test Case
	@Test(priority = 8, testName = "Proceed to Payment with Invalid Details after Items added to Cart")
	public void proceed_to_Payment_with_Invalid_Details_after_Items_added_to_Cart() {
//		Creating object for Home and Payment Pages to access methods from its class
		homePage = new HomePage(driver);
		paymentPage = new PaymentPage(driver);
		
//		Opening the Home Page
		landingPage.openHomePage();
//		Searching for an Item
		homePage.searchForProduct(1);
//		Adding an Item to the Cart
		homePage.addToCart();
//		Searching for an Item based on Department Category from the Menu
		homePage.goToShopByDepartmentMenu();
//		Adding an Item to the Cart
		homePage.addToCart();
//		Searching for an Item based on Brands from the Menu
		homePage.goToBrandsMenu();
//		Adding an Item to the Cart
		homePage.addToCart();
//		Proceeding to Payment Process using Invalid Card Details
		paymentPage.proceedToPayment(2);
		
	}

}
