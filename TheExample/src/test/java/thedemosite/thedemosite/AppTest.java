package thedemosite.thedemosite;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class AppTest
{
	static WebDriver myDriver;
	static ExtentReports extent;
	static String webAddress;
	
	@BeforeClass
	public static void init() {
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Admin\\External Apps\\chromedriver.exe");
		extent = new ExtentReports("C:\\Users\\Admin\\Desktop\\ATJava\\exampleExtentReport.html", true);
		ExtentTest initialisationInformation = extent.startTest("Initialisation method");
		try  {
			FileInputStream file = new FileInputStream("C:\\Users\\Admin\\Desktop\\ATJava\\test.xlsx");
			XSSFWorkbook myBook = new XSSFWorkbook(file);
			initialisationInformation.log(LogStatus.INFO, "Workbook found");
			webAddress = myBook.getSheetAt(0).getRow(1).getCell(1).getStringCellValue();
			initialisationInformation.log(LogStatus.INFO, "Web Address found, value: " + webAddress);
			initialisationInformation.log(LogStatus.PASS, "DDT Success");
		} catch (FileNotFoundException e) {
			initialisationInformation.log(LogStatus.FATAL, "Workbook File not found");
			initialisationInformation.log(LogStatus.INFO, "workbook path: C:\\Users\\Admin\\Desktop\\test.xlsx");
		}catch (IOException e) {
			initialisationInformation.log(LogStatus.FATAL, "Could not create workbook");
		}
	}
	
	@Before
	public void setup() {
		myDriver = new ChromeDriver();
        myDriver.manage().window().maximize();
	}
	@Test
	public void openGoogleTest() {
		ExtentTest extentOGTest = extent.startTest("Open Google Test");
		myDriver.get(webAddress);
		extentOGTest.log(LogStatus.INFO, "Navigating to " + webAddress);
		WebElement myElement = myDriver.findElement(By.cssSelector("#tsf > div.tsf-p > div.jsb > center > input[type=\"submit\"]:nth-child(1)"));
		extentOGTest.log(LogStatus.INFO, "Found the element");

		try {
			assertTrue(myElement.isDisplayed());
			extentOGTest.log(LogStatus.PASS, "Element is displayed, navigated to the correct location");
		} catch (AssertionError e) {
			extentOGTest.log(LogStatus.FAIL, "Element not displayed");
			fail();
		}
	}	
	
	@After
	public void teardown() {
		myDriver.quit();
		extent.flush();
	}
}
