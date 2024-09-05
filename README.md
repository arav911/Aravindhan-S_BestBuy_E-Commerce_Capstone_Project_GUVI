# Aravindhan-S_BestBuy_E-Commerce_Capstone_Project_GUVI

This project involves End to End Automation Testing of Sign Up, Login functionalities and Shopping for a Product using BestBuy Application, 
developed using tools such as Selenium WebDriver, TestNG, ExtentReport, Maven

Please find the below points to get an overview of the framework designed.

  1. Identified the web pages on https://www.bestbuy.com/ and created separate Java classes for each page by adding test suites and test classes to it.

  2. Base Package and Classes consist of methods to perform actions on each web element, such as clicking a button or entering text into a text field, waiting for a particular element,
     extracting the alert text or text from a particular element, configuring the report setup and WebDrivers.

      ***Location: src/main/java/base***

      ***Files: BaseComponents.java, BaseTests.java, Listeners.java***

  3. Data were imported in the form of an Excel sheet and properties file to configure the Browser and URL, which can be viewed under the Main Resources folder.

      ***Location: src/main/resources***

      ***Files: GlobalData.properties, BestBuy_Order_Details.xlsx, BestBuy_User_Details.xlsx***

  4. Page Objects creation defined the web elements using the @FindBy annotation from the Selenium WebDriver library, which also used the PageFactory class to initialize the web elements.

      ***Location: src/main/java/pageObjects***

      ***Files: SignUpPage.java, LoginPage.java, LandingPage.java, HomePage.java, PaymentPage.java, ReviewOrderPage.java***

  5. Written both the Positive and Negative Test Cases, and used assertions to verify that the expected results are achieved. Also performed the below steps in this project:

      - Performing Sign-Up and Login functionality.
      - Navigate to all of the Menu and validating their titles in each page.
      - Validating the bottom links on the homepage.
      - Searching for and adding an item to the shopping cart.
      - Selecting and adding an item from (Menu à Shop by Department).
      - Selecting and adding an item from (Menu à Brands à Select Any Brand).
      - Navigating to the checkout page and filling out the form with dummy payment information.
      - Verifying that the order was placed successfully by checking the resulting web page for the order confirmation message.
      - Closing the web browser when the testing is complete.
        
      ***Location: src/test/java/tests***

      ***Files: SignUpTests.java, LoginTests.java, BestBuyECommerceApplicationTests.java***

  6. Extent Reports and Screenshots were generated under the Test_Results folder.

      ***Report Location: Test_Results/Reports***

      ***Screenshots Location: Test_Results/Screenshots***
