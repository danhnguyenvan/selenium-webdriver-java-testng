package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Wait_Part_IV_Dead {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
	}

	@Test
	public void TC_01_Timeout_Less_Than_Element_Display() {
		driver.get("https://automationfc.github.io/dynamic-loading/");

		driver.findElement(By.cssSelector("div#start>button")).click();
		sleepInSecond(3);

		// After click - take 5s to Helloword display
		Assert.assertTrue(driver.findElement(By.cssSelector("div#finish>h4")).isDisplayed());

		System.out.println(driver.findElement(By.cssSelector("div#finish>h4")).getText());
	}

	@Test
	public void TC_02_Timeout_Equal_Element_Display() {
		driver.get("https://automationfc.github.io/dynamic-loading/");

		driver.findElement(By.cssSelector("div#start>button")).click();
		sleepInSecond(5);

		// After click - take 5s to Helloword display
		Assert.assertTrue(driver.findElement(By.cssSelector("div#finish>h4")).isDisplayed());

		System.out.println(driver.findElement(By.cssSelector("div#finish>h4")).getText());
	}

	@Test
	public void TC_03_Timeout_Greater_Than_Element_Display() {
		driver.get("https://automationfc.github.io/dynamic-loading/");

		driver.findElement(By.cssSelector("div#start>button")).click();
		sleepInSecond(8);

		// After click - take 5s to Helloword display
		Assert.assertTrue(driver.findElement(By.cssSelector("div#finish>h4")).isDisplayed());

		System.out.println(driver.findElement(By.cssSelector("div#finish>h4")).getText());
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void sleepInSecond(long timeoutInSecond) {
		try {
			Thread.sleep(timeoutInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
