package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Wait_Part_V_Explicit {
	WebDriver driver;
	WebDriverWait explicitWait;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();

		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().window().maximize();

		explicitWait = new WebDriverWait(driver, 25);
	}

	@Test
	public void TC_01_Invisible() {
		driver.get("https://automationfc.github.io/dynamic-loading/");

		driver.findElement(By.cssSelector("div#start>button")).click();

		// Wait cho loading icon biến mất (5s)
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#loading")));

		Assert.assertTrue(driver.findElement(By.cssSelector("div#finish>h4")).isDisplayed());

		System.out.println(driver.findElement(By.cssSelector("div#finish>h4")).getText());
	}

	@Test
	public void TC_02_Visible() {
		driver.get("https://automationfc.github.io/dynamic-loading/");

		driver.findElement(By.cssSelector("div#start>button")).click();

		// Wait cho loading icon biến mất (5s)
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#finish>h4")));

		Assert.assertTrue(driver.findElement(By.cssSelector("div#finish>h4")).isDisplayed());

		System.out.println(driver.findElement(By.cssSelector("div#finish>h4")).getText());
	}

	@Test
	public void TC_03_Date_Picker() {
		driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");

		scrollToElementOnDown("//div[@id='ctl00_ContentPlaceholder1_Panel1']");

		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#ctl00_ContentPlaceholder1_Panel1")));

		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='25']")));

		driver.findElement(By.xpath("//a[text()='25']")).click();

		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[id$='RadCalendar1']>div.raDiv")));

		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='25']/parent::td[@class='rcSelected']")));

		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='25']/parent::td[@class='rcSelected']")).isDisplayed());

		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span#ctl00_ContentPlaceholder1_Label1")));

		Assert.assertEquals(driver.findElement(By.cssSelector("span#ctl00_ContentPlaceholder1_Label1")).getText(), "Saturday, June 25, 2022");

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public WebElement getElement(String locator) {
		return driver.findElement(By.xpath(locator));
	}

	public void scrollToElementOnTop(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
	}

	public void scrollToElementOnDown(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(false);", getElement(locator));
	}

	public void sleepInSecond(long timeoutInSecond) {
		try {
			Thread.sleep(timeoutInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean isJQueryAndAjaxIconLoadedSuccess(WebDriver driver) {
		return true;
	}
}
