package webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class Topic_02_Run_On_Browser {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@Test
	public void TC_01_Firefox_Latest() {
		// Firefox latest: 99
		// Selenium 3.141.59
		// TestNG 6.14.3
		// Gecko Driver
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();

		driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");
		driver.quit();
	}

	@Test
	public void TC_02_Chrome() {
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();
		
		driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");
		driver.quit();
	}
	
	@Test
	public void TC_03_Edge() {
		System.setProperty("webdriver.edge.driver", projectPath + "\\browserDrivers\\msedgedriver.exe");
		driver = new EdgeDriver();
		
		driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");
		driver.quit();
	}
}
