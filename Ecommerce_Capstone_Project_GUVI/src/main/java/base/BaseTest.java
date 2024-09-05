package base;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;
import pageObjects.LandingPage;

//This class is the base to this Framework and initializes the driver instance
public class BaseTest {

//	Declaring variable for URL
	public static String URL;
	public static int screenShotCounter = 1;
	
//	Declaring objects for WebDriver and HomePage 
	public WebDriver driver;
	public LandingPage landingPage;

//	Declaring String Literals to generate credentials
	String fName = "", lName = "", email = "", pwd = "";
	String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

//	Initializing the WebDriver
	public WebDriver initializeDriver() throws IOException {
//		Loading the properties file to get "Browser" and "URL" parameter values
		Properties properties = new Properties();
		FileInputStream file = new FileInputStream("./src/main/resources/GlobalData.properties");
		properties.load(file);
		String headless = properties.getProperty("headless");
		String browser = properties.getProperty("browser");
		URL = properties.getProperty("url");
		
//		driver assigned as "ChromeDriver", if browser value is "chrome"
		if (browser.equalsIgnoreCase("chrome")) {
			ChromeOptions options=new ChromeOptions();
//			invokes when headless option is given as 'yes' 
			if(headless.equalsIgnoreCase("yes")) {
				options.addArguments("headless");
			}
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(options);
		} else if (browser.equalsIgnoreCase("firefox")) {  // else "FirefoxDriver" would be assigned
			FirefoxOptions options=new FirefoxOptions();
			if(headless.equalsIgnoreCase("yes")) {
//				invokes when headless option is given as 'yes'
				options.addArguments("headless");
			}
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver(options);
		} else if(browser.equalsIgnoreCase("edge")) { // else "EdgeDriver" would be assigned
			EdgeOptions options=new EdgeOptions();
			if(headless.equalsIgnoreCase("yes")) {
//				invokes when headless option is given as 'yes'
				options.addArguments("headless");
			}
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver(options);
		}
		
//		Adding implicit wait for 5 seconds
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
//		Maximizing the window
		driver.manage().window().maximize();
		return driver;
	}

//	This method will be invoked before every @Test method
	@BeforeMethod
	public void launchApplication() throws IOException {
//		getting the driver
		driver = initializeDriver();
		
//		creating object for HomePage to access the methods in it
		landingPage = new LandingPage(driver);
		
//		Opening the URL from HomePage
		landingPage.goToURL(URL);
	}

//	This method will be invoked after every @Test method
	@AfterMethod
	public void quitBrowser() {
//		Quits the driver instance and the browser tabs
		driver.quit();
	}

//	Action method to generate the User Details
	void generateUserDetails() throws Exception {
		Random random = new Random();
//	    Generate random First name
		for (int i = 0; i < 10; i++) {
			int index = random.nextInt(alphabet.length());
			char randomChar = alphabet.charAt(index);
			fName += randomChar;
		}
		
//	    Generate random Last name
		for (int i = 0; i < 5; i++) {
			int index = random.nextInt(alphabet.length());
			char randomChar = alphabet.charAt(index);
			lName += randomChar;
		}
		
//	    Generate Email Address
		email = fName.toLowerCase() + lName.toLowerCase() + "@gmail.com";

//		Generate Random Password
		for (int i = 0; i < 7; i++) {
			int index = random.nextInt(alphabet.length());
			char randomChar = alphabet.charAt(index);
			pwd += randomChar;
		}
		int num = random.nextInt(99999);
		pwd = pwd + "@" + String.valueOf(num);

		System.out.println("First name: " + fName + "\nLast name: " + lName + "\nEmail Address: " + email + "\nPassword: " + pwd);
//		Storing the User name and Password in the excel file by passing File name, user name and password as arguments
		storeUserDetails("BestBuy_User_Details", fName, lName, email, pwd);
	}

//	Action method to store the User Details in the Excel sheet
	void storeUserDetails(String fileName, String fName, String lName, String email, String pwd) throws Exception {
//		creating object of workbook
		XSSFWorkbook wb = new XSSFWorkbook();

//		Giving file path where the Excel file will create
		String filePath = "./src/main/resources/" + fileName + ".xlsx";
//		creating object of FileOutputStream
		FileOutputStream fos = new FileOutputStream(filePath);

//		creating object of work sheet
		XSSFSheet sheet = wb.createSheet("Sheet1");

//		Creating ArrayList
		ArrayList<Object[]> inputData = new ArrayList<Object[]>();
		inputData.add(new Object[] { "First Name", "Last Name", "Email Address", "Password" });
		inputData.add(new Object[] { fName, lName, email, pwd });

		int rowNum = 0;
//		Outer loop for rows
		for (Object[] input : inputData) {
			XSSFRow row = sheet.createRow(rowNum++);
			int cellNum = 0;
//			Inner loop for columns
			for (Object inp : input) {
				XSSFCell cell = row.createCell(cellNum++);
				if (inp instanceof String)
					cell.setCellValue((String) inp);
			}
		}

//		Writing data to excel
		wb.write(fos);
		
//		Closing FileOutputStream
		fos.close();
		
//		Closing workbook
		wb.close();
		System.out.println("User Details successfully stored in Excel!!!");
	}
}
