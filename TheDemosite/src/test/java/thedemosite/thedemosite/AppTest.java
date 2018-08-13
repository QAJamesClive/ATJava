package thedemosite.thedemosite;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class AppTest 
{
	WebDriver myDriver;
	
	@BeforeClass
	public static void init() {
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Admin\\External Application\\chromedriver.exe");
	}
	
	@Before
	public void setup() {
		myDriver = new ChromeDriver();
        myDriver.manage().window().maximize();
	}
	@Test
	public void testConnection() {
		myDriver.get("http://thedemosite.co.uk/addauser.php");
		myDriver.findElement(By.name("username")).sendKeys("username");
		myDriver.findElement(By.name("password")).sendKeys("password");
		myDriver.findElement(By.name("FormsButton2")).click();
		myDriver.findElement(By.cssSelector("body > table > tbody > tr > td.auto-style1 > form > div > center > table > tbody > tr > td:nth-child(2) > small > a")).click();
		myDriver.findElement(By.name("username")).sendKeys("username");
		myDriver.findElement(By.name("password")).sendKeys("password");
		myDriver.findElement(By.name("FormsButton2")).click();
		assertEquals("Successful login",myDriver.findElement(By.cssSelector("body > table > tbody > tr > td.auto-style1 > big > blockquote > blockquote > font > center > b")).getText(),"**Successful Login**");
	}
	@After
	public void teardown() {
		myDriver.quit();
	}
}
