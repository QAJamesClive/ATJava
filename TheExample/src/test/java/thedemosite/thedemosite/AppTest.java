package thedemosite.thedemosite;

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
		myDriver.get("https://www.google.com/");
		WebElement myElement = myDriver.findElement(By.cssSelector("<img alt='Google' height='92' id='hplogo' src='/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png' srcset='/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png 1x, /images/branding/googlelogo/2x/googlelogo_color_272x92dp.png 2x' style='padding-top:109px' width='272' onload='window.lol&amp;&amp;lol()' data-atf='3'>"));
		assert(myElement.isDisplayed());
	}
	@After
	public void teardown() {
		myDriver.quit();
	}
}
