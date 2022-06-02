package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_12_Popup {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	JavascriptExecutor jsExecutor;
	WebDriverWait explicitWait;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	@Test
	public void TC_01_Fixed_Popup_Ngoai_Ngu_24h() {
		driver.get("https://ngoaingu24h.vn/");

		By loginPopup = By.cssSelector("div#modal-login-v1");

		// Verfify login popup is not displayed
		Assert.assertFalse(driver.findElement(loginPopup).isDisplayed());

		// Click to Đăng nhập button
		driver.findElement(By.cssSelector("button.login_.icon-before")).click();
		sleepInSecond(2);

		// Verfify login popup is displayed
		Assert.assertTrue(driver.findElement(loginPopup).isDisplayed());

		driver.findElement(By.cssSelector("input#account-input")).sendKeys("automationfc");
		driver.findElement(By.cssSelector("input#password-input")).sendKeys("automationfc");
		driver.findElement(By.cssSelector("button.btn-login-v1")).click();
		sleepInSecond(2);

		Assert.assertEquals(driver.findElement(By.cssSelector("div.error-login-panel")).getText(), "Tài khoản không tồn tại!");
	}

	@Test
	public void TC_01_Fixed_JT_Express() {
		driver.get("https://jtexpress.vn/");

		By homeSliderPopup = By.cssSelector("img.sourceList");

		// Verify Home Slider popup is displayed
		Assert.assertTrue(driver.findElement(homeSliderPopup).isDisplayed());

		// Click the Close button
		driver.findElement(By.xpath("//button[contains(text(),'+')]")).click();

		// Verify Home Slider popup is not displayed
		Assert.assertFalse(driver.findElement(homeSliderPopup).isDisplayed());

	}

	@Test
	public void TC_02_Random_Popup_In_DOM() {
		// Step 1
		driver.get("https://blog.testproject.io/");

		By mailcPopup = By.cssSelector("div.mailch-wrap");

		// Wait for page loaded success
		Assert.assertTrue(isPageLoadedSuccess(driver));

		// Step 2: Nếu hiển thị thì close popup/ ko hiển thị thì qua step 3
		if (driver.findElement(mailcPopup).isDisplayed()) {
			driver.findElement(By.cssSelector("div#close-mailch")).click();
			sleepInSecond(2);
			Assert.assertFalse(driver.findElement(mailcPopup).isDisplayed());
		}

		// Step 3
		driver.findElement(By.cssSelector("section#search-2 input.search-field")).sendKeys("Selenium");
		driver.findElement(By.cssSelector("section#search-2 span.glass")).click();
		sleepInSecond(2);

		// Step 4
		List<WebElement> postTitles = driver.findElements(By.cssSelector("h3.post-title>a"));
		System.out.println("All post title = " + postTitles.size());

		for (WebElement postTitle : postTitles) {
			Assert.assertTrue(postTitle.getText().contains("Selenium"));
		}

	}

	@Test
	public void TC_03_Random_Popup_Not_In_DOM() {
		// Nếu như 1 element ko có trong HTML/ DOM thì nó xảy ra vấn đề gì?
		// findElement
		// implicitWait
		// wait
		// Step 1
		driver.get("https://dehieu.vn/");

		By dehieuPopupBy = By.cssSelector("section#popup div.popup-content");

		List<WebElement> dehieuPopupElement = driver.findElements(dehieuPopupBy);

		// Step 2: Nếu hiển thị thì close popup/ ko hiển thị thì qua step 3
		if (dehieuPopupElement.size() > 0) {
			System.out.println("-------------------Popup hiển thị và close đi-------------------");
			driver.findElement(By.cssSelector("div.popup-content>button#close-popup")).click();
			sleepInSecond(2);

			Assert.assertEquals(driver.findElements(dehieuPopupBy).size(), 0);
		} else {
			System.out.println("-------------------Popup ko hiển thị và qua step sau -------------------");
		}

		// Step 3
		driver.findElement(By.xpath("//div[@id='page-container']//li/a[text()='Đăng nhập']")).click();

		String homePageUrl = driver.getCurrentUrl();
		Assert.assertEquals(homePageUrl, "https://dehieu.vn/dang-nhap");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public boolean isPageLoadedSuccess(WebDriver driver) {
		explicitWait = new WebDriverWait(driver, 120);
		jsExecutor = (JavascriptExecutor) driver;
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return (Boolean) jsExecutor.executeScript("return (window.jQuery != null) && (jQuery.active ===0);");
			}
		};

		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
			}
		};
		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
	}

	public void sleepInSecond(long timeoutInSecond) {
		try {
			Thread.sleep(timeoutInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int getRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(99999);
	}

}
