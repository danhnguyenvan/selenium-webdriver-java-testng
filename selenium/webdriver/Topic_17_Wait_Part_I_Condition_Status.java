package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Wait_Part_I_Condition_Status {
	WebDriver driver;
	WebDriverWait explicitWait;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();

		explicitWait = new WebDriverWait(driver, 20);
	}

	@Test
	public void TC_01_Visible() {
		driver.get("https://www.facebook.com/");

		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-testid='open-registration-form-button']"))).click();

		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='reg_email__']"))).sendKeys("danh@gmail.com");

		// Wait cho element được visible/ displayed
		Dimension confirmEmailSize = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='reg_email_confirmation__']"))).getSize();
		System.out.println("Confirm email high: " + confirmEmailSize.getHeight());
		System.out.println("Confirm email width: " + confirmEmailSize.getWidth());

	}

	@Test
	public void TC_02_Invisible_In_DOM() {
		driver.get("https://www.facebook.com/");

		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-testid='open-registration-form-button']"))).click();
		sleepInSecond(2);

		// Element ko hiển thị: Ko có tren UI + có trong DOM
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("input[name='reg_email_confirmation__']")));
	}

	@Test
	public void TC_02_Invisible_Not_In_DOM() {
		driver.get("https://www.facebook.com/");

		// Element ko hiển thị: Ko có tren UI + ko có trong DOM
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("input[name='reg_email_confirmation__']")));
	}

	@Test
	public void TC_03_Presence_In_UI() {
		driver.get("https://www.facebook.com/");

		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-testid='open-registration-form-button']"))).click();

		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='reg_email__']"))).sendKeys("danh@gmail.com");

		explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='reg_email_confirmation__']")));

	}

	@Test
	public void TC_03_Presence_Not_In_UI() {
		driver.get("https://www.facebook.com/");

		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-testid='open-registration-form-button']"))).click();

		explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='reg_email_confirmation__']")));
	}

	@Test
	public void TC_04_Staleness() {
		driver.get("https://www.facebook.com/");

		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-testid='open-registration-form-button']"))).click();
		sleepInSecond(2);

		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("input[name='reg_email_confirmation__']")));

		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='reg_email__']"))).sendKeys("danh@gmail.com");
		sleepInSecond(2);

		// Element confirm email đang có trong DOM (visible)
		WebElement confirmEmail = driver.findElement(By.cssSelector("input[name='reg_email_confirmation__']"));

		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Sign Up']/parent::div/preceding-sibling::img"))).click();

		// Wait cho confirm email staleness (ko còn trong DOM)
		explicitWait.until(ExpectedConditions.stalenessOf(confirmEmail));

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
