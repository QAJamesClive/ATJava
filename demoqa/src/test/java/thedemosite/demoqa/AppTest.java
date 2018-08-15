package thedemosite.demoqa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
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
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest
{
	WebDriver myDriver;

	static ExtentReports extent;
	static XSSFWorkbook myBook;
	static String webAddress;
	
	@BeforeClass
	public static void init() {
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Admin\\External Apps\\chromedriver.exe");
		extent = new ExtentReports("C:\\Users\\Admin\\Desktop\\ATJava\\demoQAExtentReport.html", true);
		ExtentTest initialisationInformation = extent.startTest("Initialisation method");
		try  {
			FileInputStream file = new FileInputStream("C:\\Users\\Admin\\Desktop\\ATJava\\DemoQA.xlsx");
			myBook = new XSSFWorkbook(file);
			initialisationInformation.log(LogStatus.INFO, "Workbook found");
			webAddress = myBook.getSheetAt(0).getRow(1).getCell(1).getStringCellValue();
			initialisationInformation.log(LogStatus.INFO, "Web Address found, value: " + webAddress);
			initialisationInformation.log(LogStatus.PASS, "DDT Success");
		} catch (FileNotFoundException e) {
			initialisationInformation.log(LogStatus.FATAL, "Workbook File not found");
			initialisationInformation.log(LogStatus.INFO, "workbook path: C:\\Users\\Admin\\Desktop\\ATJava\\DemoQA.xlsx");
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
	public void testDraggable() {
		ExtentTest extentOGTest = extent.startTest("Open Google Test");
		myDriver.get(webAddress);
		extentOGTest.log(LogStatus.INFO, "Navigating to " + webAddress);
		myDriver.findElement(By.id("menu-item-141")).click();
		Actions mouseOverHome = new Actions(myDriver);
		mouseOverHome.dragAndDrop(myDriver.findElement(By.id("draggableview")),	myDriver.findElement(By.id("droppableview"))).perform();
		try {
			assertTrue(myDriver.findElement(By.id("droppableview")).getText().equals("Dropped!"));
			extentOGTest.log(LogStatus.INFO, "Testing the drag drop ting");
		} catch(AssertionError e) {
			extentOGTest.log(LogStatus.FAIL, "Not dragged not dropped!!");
			fail();
		}	
	}
	@Test
	public void testSelectable() {
		ExtentTest extentOGTest = extent.startTest("Open Google Test");
		myDriver.get(webAddress);
		extentOGTest.log(LogStatus.INFO, "Navigating to " + webAddress);
		myDriver.findElement(By.id("menu-item-142")).click();
		myDriver.findElement(By.cssSelector("#ui-id-3")).click();
		Actions mouseOverHome = new Actions(myDriver);
		mouseOverHome.click(myDriver.findElement(By.cssSelector("#selectable-serialize > li.ui-widget-content.ui-corner-left.ui-selectee"))).perform();
		try {
			assertTrue(myDriver.findElement(By.cssSelector("#select-result")).getText().equals("#1"));
			extentOGTest.log(LogStatus.INFO, "Testing the select ting");
		} catch(AssertionError e) {
			extentOGTest.log(LogStatus.FAIL, "Not selected!!");
			fail();
		}	
	}
	@After
	public void teardown() {
		myDriver.quit();
		extent.flush();
	}

}
