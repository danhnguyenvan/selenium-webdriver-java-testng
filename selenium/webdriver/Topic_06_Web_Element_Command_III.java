package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_06_Web_Element_Command_III {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@Test
	public void TC_01_Signup_Validate() {
		driver.get("https://login.mailchimp.com/signup/");
		
		driver.findElement(By.cssSelector("#email")).sendKeys("automation@gmail.vn");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.cssSelector("#new_username")).click();
		driver.findElement(By.cssSelector("#new_username")).clear();
		driver.findElement(By.cssSelector("#new_username")).sendKeys("automationclub");

		
		By passwordTextbox = By.id("new_password");
		By signupButton = By.xpath("//button[@id='create-account']");
		By newsletterCheckbox = By.id("marketing_newsletter");

		// Click Newsletter checkbox
		driver.findElement(newsletterCheckbox).click();

		// Lowercase
		driver.findElement(By.id("new_password")).sendKeys("auto");
		sleepInSecond(1);

		// Verify label of lowercase -> Update
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char completed' and text()='One lowercase character']")).isDisplayed());
		Assert.assertFalse(driver.findElement(signupButton).isEnabled());

		// Uppercase
		driver.findElement(passwordTextbox).clear();
		driver.findElement(passwordTextbox).sendKeys("AUTO");
		sleepInSecond(1);

		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char completed' and text()='One uppercase character']")).isDisplayed());
		Assert.assertFalse(driver.findElement(signupButton).isEnabled());

		// Number
		driver.findElement(passwordTextbox).clear();
		driver.findElement(passwordTextbox).sendKeys("123456");
		sleepInSecond(1);

		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char completed' and text()='One number']")).isDisplayed());
		Assert.assertFalse(driver.findElement(signupButton).isEnabled());

		// Special
		driver.findElement(passwordTextbox).clear();
		driver.findElement(passwordTextbox).sendKeys("@@@###");
		sleepInSecond(1);

		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char completed' and text()='One special character']")).isDisplayed());
		Assert.assertFalse(driver.findElement(signupButton).isEnabled());
		
		// >= 8 chars
		driver.findElement(passwordTextbox).clear();
		driver.findElement(passwordTextbox).sendKeys("😍😍😍😍😍😍😍😍");
		sleepInSecond(1);
		
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char completed' and text()='8 characters minimum']")).isDisplayed());
		Assert.assertFalse(driver.findElement(signupButton).isEnabled());

		// Full valid data
		driver.findElement(passwordTextbox).clear();
		driver.findElement(passwordTextbox).sendKeys("Auto12345!!!");

		Assert.assertFalse(driver.findElement(By.xpath("//li[@class='lowercase-char completed' and text()='One lowercase character']")).isDisplayed());
		Assert.assertFalse(driver.findElement(By.xpath("//li[@class='uppercase-char completed' and text()='One uppercase character']")).isDisplayed());
		Assert.assertFalse(driver.findElement(By.xpath("//li[@class='special-char completed' and text()='One special character']")).isDisplayed());
		Assert.assertFalse(driver.findElement(By.xpath("//li[@class='number-char completed' and text()='One number']")).isDisplayed());
		Assert.assertFalse(driver.findElement(By.xpath("//li[@class='8-char completed' and text()='8 characters minimum']")).isDisplayed());
		
		Assert.assertTrue(driver.findElement(signupButton).isEnabled());
		Assert.assertTrue(driver.findElement(newsletterCheckbox).isSelected());
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
