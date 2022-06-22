package testng;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Topic_06_Parameter {
	WebDriver driver;
	Select select;
	String projectPath = System.getProperty("user.dir");

	@Parameters({ "browser", "environment" })
	@BeforeClass
	public void beforeClass(String browserName, @Optional("LOCAL") String environmentName) {

		System.out.println(environmentName);
		switch (browserName) {
		case "firefox": {
			// Firefox
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		}

		case "chrome": {
			// Chrome
			System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
			driver = new ChromeDriver();
			break;
		}

		case "edge": {
			// Edge
			System.setProperty("webdriver.edge.driver", projectPath + "\\browserDrivers\\msedgedriver.exe");
			driver = new EdgeDriver();
			break;
		}

		default:
			throw new RuntimeException("Browser is not supported");
		}

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	@Parameters("url")
	@Test
	public void TC_01_NopCommerce(String url) {
		driver.get(url);

		String firstName = "Automation";
		String lastName = "FC";
		String emailAddress = "autofc" + getRandomNumber() + "@gmail.net";
		String company = "Automation VN";
		String password = "123456";

		String day = "15";
		String month = "December";
		String year = "1999";

		By genderMaleBy = By.id("gender-male");
		By firstNameBy = By.id("FirstName");
		By lastNameBy = By.id("LastName");
		By dayDropdownBy = By.name("DateOfBirthDay");
		By monthDropdownBy = By.name("DateOfBirthMonth");
		By yearDropdownBy = By.name("DateOfBirthYear");
		By emailBy = By.id("Email");
		By companyBy = By.id("Company");

		driver.findElement(By.cssSelector("a.ico-register")).click();
		driver.findElement(genderMaleBy).click();
		driver.findElement(firstNameBy).sendKeys(firstName);
		driver.findElement(lastNameBy).sendKeys(lastName);

		select = new Select(driver.findElement(dayDropdownBy));

		// Chọn 1 item A
		select.selectByVisibleText(day);

		// Kiểm tra dropdown này có phải là multiple select hay ko
		Assert.assertFalse(select.isMultiple());

		// Kiểm tra xem đã chọn đúng item A chưa
		Assert.assertEquals(select.getFirstSelectedOption().getText(), day);

		// Get ra tổng số item trong dropdown là bao nhiêu => Verify nó bằng bao nhiêu
		Assert.assertEquals(select.getOptions().size(), 32);

		select = new Select(driver.findElement(monthDropdownBy));
		select.selectByVisibleText(month);

		select = new Select(driver.findElement(yearDropdownBy));
		select.selectByVisibleText(year);

		driver.findElement(emailBy).sendKeys(emailAddress);
		driver.findElement(companyBy).sendKeys(company);
		driver.findElement(By.id("Password")).sendKeys(password);
		driver.findElement(By.id("ConfirmPassword")).sendKeys(password);

		driver.findElement(By.id("register-button")).click();

		Assert.assertEquals(driver.findElement(By.cssSelector("div.result")).getText(), "Your registration completed");

		driver.findElement(By.cssSelector("a.ico-account")).click();

		Assert.assertTrue(driver.findElement(genderMaleBy).isSelected());
		Assert.assertEquals(driver.findElement(firstNameBy).getAttribute("value"), firstName);
		Assert.assertEquals(driver.findElement(lastNameBy).getAttribute("value"), lastName);

		select = new Select(driver.findElement(dayDropdownBy));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), day);

		select = new Select(driver.findElement(monthDropdownBy));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), month);

		select = new Select(driver.findElement(yearDropdownBy));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), year);

		Assert.assertEquals(driver.findElement(emailBy).getAttribute("value"), emailAddress);
		Assert.assertEquals(driver.findElement(companyBy).getAttribute("value"), company);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public int getRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(99999);
	}

	public void sleepInSecond(long timeoutInSecond) {
		try {
			Thread.sleep(timeoutInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
