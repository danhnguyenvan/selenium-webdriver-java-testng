package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.server.handler.GetPageSource;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_05_Web_Browser_Command_II {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	String firstName, lastName, fullName, email, password;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();

		firstName = "John";
		lastName = "Wick";
		fullName = firstName + " " + lastName;
		email = "john" + getRandomNumber() + "@gmail.us";
		password = "123456";

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	@Test
	public void TC_01_Url() {
		driver.get("http://live.techpanda.org/");

		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();

		String loginPageUrl = driver.getCurrentUrl();
		Assert.assertEquals(loginPageUrl, "http://live.techpanda.org/index.php/customer/account/login/");

		driver.findElement(By.xpath("//div[@class='buttons-set']//span[text()='Create an Account']")).click();
		String registerPageUrl = driver.getCurrentUrl();
		Assert.assertEquals(registerPageUrl, "http://live.techpanda.org/index.php/customer/account/create/");

	}

	@Test
	public void TC_02_Title() {
		driver.get("http://live.techpanda.org/");

		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();

		String loginPageTitle = driver.getTitle();
		Assert.assertEquals(loginPageTitle, "Customer Login");

		driver.findElement(By.xpath("//div[@class='buttons-set']//span[text()='Create an Account']")).click();
		String registerPageTitle = driver.getTitle();
		Assert.assertEquals(registerPageTitle, "Create New Customer Account");

	}

	@Test
	public void TC_03_Navigation() {
		driver.get("http://live.techpanda.org/");

		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		driver.findElement(By.xpath("//div[@class='buttons-set']//span[text()='Create an Account']")).click();

		String registerPageUrl = driver.getCurrentUrl();
		Assert.assertEquals(registerPageUrl, "http://live.techpanda.org/index.php/customer/account/create/");

		driver.navigate().back();

		String loginPageUrl = driver.getCurrentUrl();
		Assert.assertEquals(loginPageUrl, "http://live.techpanda.org/index.php/customer/account/login/");

		driver.navigate().forward();

		String registerPageTitle = driver.getTitle();
		Assert.assertEquals(registerPageTitle, "Create New Customer Account");
	}

	@Test
	public void TC_04_Page_Source() {
		driver.get("http://live.techpanda.org/");

		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();

		String loginPageSource = driver.getPageSource();
		Assert.assertTrue(loginPageSource.contains("Login or Create an Account"));

		driver.findElement(By.xpath("//div[@class='buttons-set']//span[text()='Create an Account']")).click();

		// String class
		String registerPageSource = driver.getPageSource();
		Assert.assertTrue(registerPageSource.contains("Create an Account"));

	}

	@Test
	public void TC_05_LiveGuru_Register() {
		// Mở application lên (AUT/ SUT)
		driver.get("http://live.techpanda.org/");

		// Click vào link "My Account"
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();

		// Click CREATE AN ACCOUNT button
		driver.findElement(By.xpath("//a[@title='Create an Account' and @class='button']")).click();

		driver.findElement(By.id("firstname")).sendKeys(firstName);
		driver.findElement(By.id("lastname")).sendKeys(lastName);
		driver.findElement(By.id("email_address")).sendKeys(email);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("confirmation")).sendKeys(password);

		driver.findElement(By.xpath("//button[@title='Register']")).click();
		Assert.assertTrue(driver.findElement(By
				.xpath("//li[@class='success-msg']//span[text()='Thank you for registering with Main Website Store.']"))
				.isDisplayed());

		// Cách 1
		String contactInformation = driver.findElement(By
				.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div[@class='box-content']/p"))
				.getText();
		Assert.assertTrue(contactInformation.contains(fullName));
		Assert.assertTrue(contactInformation.contains(email));

		// Cách 2
		Assert.assertTrue(driver.findElement(By.xpath("//h3[text()='Contact Information']"
				+ "/parent::div/following-sibling::div[@class='box-content']/p[contains(string(),'" + fullName + "')]"))
				.isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//h3[text()='Contact Information']"
				+ "/parent::div/following-sibling::div[@class='box-content']/p[contains(string(),'" + email + "')]"))
				.isDisplayed());

		driver.findElement(By.xpath("//a/span[text()='Account']")).click();
		driver.findElement(By.xpath("//a[@title='Log Out']")).click();
	}

	@Test
	public void TC_06_LiveGuru_Login() {
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();

		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.name("login[password]")).sendKeys(password);
		driver.findElement(By.id("send2")).click();

		Assert.assertTrue(driver.findElement(By.xpath("//h1[text()='My Dashboard']")).isDisplayed());
		String contactInformation = driver.findElement(By
				.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div[@class='box-content']/p"))
				.getText();
		Assert.assertTrue(contactInformation.contains(fullName));
		Assert.assertTrue(contactInformation.contains(email));

	}

	public int getRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(99999);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
