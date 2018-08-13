package thedemosite.automationpractice;

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
		myDriver.get("http://automationpractice.com/index.php");
		myDriver.findElement(By.id("search_query_top")).sendKeys("dress");
		myDriver.findElement(By.name("submit_search")).click();
		
		
		assertEquals("Find a dress",myDriver.findElement(By.cssSelector("#best-sellers_block_right > div > ul > li:nth-child(1) > div > h5 > a")).getText(),"Printed Chiffon Dress");
	}
	@After
	public void teardown() {
		myDriver.quit();
	}
}
