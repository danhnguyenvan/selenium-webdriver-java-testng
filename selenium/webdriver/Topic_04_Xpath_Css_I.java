package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_04_Xpath_Css_I {
	// Khai báo 1 biến đại diện cho Selenium WebDriver
	WebDriver driver;
	// Khai báo 1 biến để lấy đường dẫn hiện tại của project
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {

		// Firefox
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		// Mở trình duyệt Firefox lên
		driver = new FirefoxDriver();

		// Set timeout để tìm element
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		// Mở application lên (AUT/ SUT)
		driver.get("http://live.techpanda.org/index.php/customer/account/login/");

	}

	@Test
	public void TC_01_Login_Empty_Email_And_Password() {

		// Nhập dữ liệu vào 1 textbox
		driver.findElement(By.id("email")).sendKeys("");
		driver.findElement(By.name("login[password]")).sendKeys("");

		// Click vào button
		driver.findElement(By.xpath("//button[@title='Login']")).click();

		// Get error message text of Email/ Password
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-email")).getText(), "This is a required field.");
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(), "This is a required field.");

	}

	@Test
	public void TC_02_Login_Invalid_Email() {
		// Refresh current page
		driver.navigate().refresh();

		// Nhập dữ liệu vào 1 textbox
		driver.findElement(By.id("email")).sendKeys("1231234@234.1214");
		driver.findElement(By.name("login[password]")).sendKeys("123456789");

		// Click vào button
		driver.findElement(By.xpath("//button[@title='Login']")).click();

		// Get error message text of Email
		Assert.assertEquals(driver.findElement(By.id("advice-validate-email-email")).getText(), "Please enter a valid email address. For example johndoe@domain.com.");
	}

	@Test
	public void TC_03_Login_Invalid_Password() {
		// Refresh current page
		driver.navigate().refresh();

		// Nhập dữ liệu vào 1 textbox
		driver.findElement(By.id("email")).sendKeys("automation@gmail.com");
		driver.findElement(By.name("login[password]")).sendKeys("123");

		// Click vào button
		driver.findElement(By.xpath("//button[@title='Login']")).click();

		// Get error message text of Password
		Assert.assertEquals(driver.findElement(By.id("advice-validate-password-pass")).getText(), "Please enter 6 or more characters without leading or trailing spaces.");

	}

	@Test
	public void TC_04_Login_Incorrect_Email() {
		// Refresh current page
		driver.navigate().refresh();

		// Nhập dữ liệu vào 1 textbox
		driver.findElement(By.id("email")).sendKeys("automation@gmail.com");
		driver.findElement(By.name("login[password]")).sendKeys("123456789");

		// Click vào button
		driver.findElement(By.xpath("//button[@title='Login']")).click();

		// Get error message text of Password
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='error-msg']//span")).getText(), "Invalid login or password.");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
