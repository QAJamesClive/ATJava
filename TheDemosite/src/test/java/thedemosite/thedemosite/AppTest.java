package thedemosite.thedemosite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



public class AppTest 
{
	static ExtentReports extent;
	WebDriver myDriver;
	static String webAddress;
	static String userName;
	static String password;
	
	static String workbookPath;
	static XSSFWorkbook myBook;
	static FileInputStream file;
	
	static ArrayList<String> userNameList;
	static ArrayList<String> passwordList;
	static ArrayList<Boolean> resultList;
	
	@BeforeClass
	public static void init() {
		userNameList = new ArrayList<String>();
		passwordList = new ArrayList<String>();
		workbookPath = "C:\\Users\\Admin\\Desktop\\ATJava\\TheDemoSite.xlsx";
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Admin\\External Apps\\chromedriver.exe");
		extent = new ExtentReports("C:\\Users\\Admin\\Desktop\\theDemoSiteExtentReport.html", true);
		ExtentTest initialisationInformation = extent.startTest("Initialisation method");
		try  {
			file = new FileInputStream(workbookPath);
			myBook = new XSSFWorkbook(file);
			initialisationInformation.log(LogStatus.INFO, "Workbook found");
			webAddress = "http://thedemosite.co.uk/addauser.php";
			for(int i = 0; i < 2;i++) {
				userName = myBook.getSheetAt(0).getRow(i).getCell(0).getStringCellValue();
				userNameList.add(userName);
				initialisationInformation.log(LogStatus.INFO, "Web userName found, value: " + userName);
				password = myBook.getSheetAt(0).getRow(i).getCell(1).getStringCellValue();
				passwordList.add(password);
				initialisationInformation.log(LogStatus.INFO, "Web password found, value: " + password);
			}
			initialisationInformation.log(LogStatus.INFO, "Web Address found, value: " + webAddress);
		} catch (FileNotFoundException e) {
			initialisationInformation.log(LogStatus.FATAL, "Workbook File not found");
			initialisationInformation.log(LogStatus.INFO, "workbook path: "+workbookPath);
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
	public void testConnection() {
		resultList = new ArrayList<Boolean>();
		
		ExtentTest extentOGTest = extent.startTest("Create user test");
		myDriver.get(webAddress);
		for(int i = 0;i < userNameList.size();i++) {
			myDriver.findElement(By.name("username")).sendKeys(userNameList.get(i));
			myDriver.findElement(By.name("password")).sendKeys(passwordList.get(i));
			myDriver.findElement(By.name("FormsButton2")).click();
			myDriver.findElement(By.cssSelector("body > table > tbody > tr > td.auto-style1 > form > div > center > table > tbody > tr > td:nth-child(2) > small > a")).click();
			myDriver.findElement(By.name("username")).sendKeys(userNameList.get(i));
			myDriver.findElement(By.name("password")).sendKeys(passwordList.get(i));
			myDriver.findElement(By.name("FormsButton2")).click();
			try {
				assertEquals("Successful login",myDriver.findElement(By.cssSelector("body > table > tbody > tr > td.auto-style1 > big > blockquote > blockquote > font > center > b")).getText(),"**Successful Login**");
				extentOGTest.log(LogStatus.INFO, "Testing the creation of the user");
				myDriver.findElement(By.cssSelector("body > div > center > table > tbody > tr:nth-child(2) > td > div > center > table > tbody > tr > td:nth-child(2) > p > small > a:nth-child(6)")).click();
				resultList.add(true);
			} catch(AssertionError e) {
				extentOGTest.log(LogStatus.FAIL, "User not created");
				resultList.add(false);
				fail();
			}
		}
		
		XSSFWorkbook myBookOut = new XSSFWorkbook();
		XSSFSheet workSheet = myBookOut.createSheet("Misc");
		for(int i = 0; i<2;i++) {
			XSSFRow row = workSheet.createRow(i);
			row.createCell(0).setCellValue(userNameList.get(i));
			row.createCell(1).setCellValue(passwordList.get(i));
			row.createCell(2).setCellValue(resultList.get(i));
		}
		try {
			file.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        FileOutputStream file = null;
		try {
			file = new FileOutputStream("C:\\Users\\Admin\\Desktop\\ATJava\\TheDemoSite.xlsx");
			myBookOut.write(file);
			myBookOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	}
	@After
	public void teardown() {
		myDriver.quit();
		extent.flush();
	}
}
