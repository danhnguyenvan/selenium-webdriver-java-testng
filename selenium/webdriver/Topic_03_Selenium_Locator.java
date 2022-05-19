package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_03_Selenium_Locator {
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

//	@Test
	public void TC_01_FindElement() {

		// Single element: WebElement
		WebElement loginButton = driver.findElement(By.className(""));
		loginButton.click();

		// findElement: tìm element
		// By.xxx với locator nào
		// Action gì lên element đó: click/ sendkey/ getText/ ...

		// Multiple elements: List<WebElement>
		List<WebElement> buttons = driver.findElements(By.className(""));
		buttons.get(0).click();

	}

	@Test
	public void TC_02_ID() {
		driver.findElement(By.id("send2")).click();

		// Verify email error message xuất hiện
		Assert.assertTrue(driver.findElement(By.id("advice-required-entry-email")).isDisplayed());
	}

	@Test
	public void TC_03_Class() {
		driver.navigate().refresh();

		driver.findElement(By.className("validate-password")).sendKeys("1234556788");
	}

	@Test
	public void TC_04_Name() {
		driver.navigate().refresh();

		driver.findElement(By.name("send")).click();
		// Verify email error message xuất hiện
		Assert.assertTrue(driver.findElement(By.id("advice-required-entry-email")).isDisplayed());
	}

	@Test
	public void TC_05_Tagname() {
		// Refresh current page
		driver.navigate().refresh();

		// Hiển thị hết tất cả đường link tại màn hình này sau đó getText ra
		List<WebElement> loginPageLinks = driver.findElements(By.tagName("a"));

		for (WebElement webElement : loginPageLinks) {
			System.out.println(webElement.getText());
		}
	}

	@Test
	public void TC_06_LinkText() {
		driver.navigate().refresh();

		driver.findElement(By.linkText("Forgot Your Password?")).click();

		Assert.assertTrue(driver.findElement(By.id("email_address")).isDisplayed());
	}

	@Test
	public void TC_07_PartialLinkText() {
		driver.findElement(By.partialLinkText("Back to")).click();

		Assert.assertTrue(driver.findElement(By.id("email")).isDisplayed());
	}

	@Test
	public void TC_08_Css() {
		driver.findElement(By.cssSelector("#email")).sendKeys("danh@gmail.com");
		driver.findElement(By.cssSelector("input[name='login[password]']")).sendKeys("123456789");
	}

	@Test
	public void TC_09_Xpath() {
		driver.navigate().refresh();

		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("danh@gmail.com");
		driver.findElement(By.xpath("//label[contains(text(), 'Password')]/following-sibling::div/input"))
				.sendKeys("123456789");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}