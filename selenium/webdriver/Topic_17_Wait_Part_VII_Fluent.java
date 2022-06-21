package webdriver;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.base.Function;

@Test
public class Topic_17_Wait_Part_VII_Fluent {
	WebDriver driver;
	WebDriverWait explicitWait;
	FluentWait<WebDriver> fluentWaitDriver;
	FluentWait<WebElement> fluentWaitElement;
	JavascriptExecutor jsExecutor;

	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
	}

	public void TC_01_() {
		driver.get("https://automationfc.github.io/fluent-wait/");

		WebElement countDownTime = driver.findElement(By.id("javascript_countdown_time"));

		fluentWaitElement = new FluentWait<WebElement>(countDownTime);

		// Wait với tổng thời gian là 15 seconds
		fluentWaitElement.withTimeout(Duration.ofSeconds(15))

				// Cở chế tìm lại nếu chưa thỏa mãn điều kiện là nữa s tìm lại 1 lần
				.pollingEvery(Duration.ofMillis(500))

				// Nếu như trong thời gian tìm lại mà ko thấy element
				.ignoring(NoSuchElementException.class)

				// Xử lí điều kiện
				.until(new Function<WebElement, Boolean>() {
					@Override
					public Boolean apply(WebElement element) {
						String text = element.getText();
						System.out.println("Time = " + text);
						return text.endsWith("00");
					}
				});
	}

	public void TC_02_() {
		driver.get("https://automationfc.github.io/dynamic-loading/");

		driver.findElement(By.cssSelector("div#start>button")).click();

		fluentWaitDriver = new FluentWait<WebDriver>(driver);

		fluentWaitDriver.withTimeout(Duration.ofSeconds(6)).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class);

		WebElement helloworldText = fluentWaitDriver.until(new Function<WebDriver, WebElement>() {

			@Override
			public WebElement apply(WebDriver arg0) {
				return driver.findElement(By.cssSelector("div#finish>h4"));
			}
		});

		Assert.assertEquals(helloworldText.getText(), "Hello World!");
	}

	public void TC_03_() {
		driver.get("https://automationfc.github.io/dynamic-loading/");

		driver.findElement(By.cssSelector("div#start>button")).click();

		fluentWaitDriver = new FluentWait<WebDriver>(driver);

		fluentWaitDriver.withTimeout(Duration.ofSeconds(6)).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class).until(new Function<WebDriver, Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				return driver.findElement(By.cssSelector("div#finish>h4")).getText().equals("Hello World!");
			}
		});
	}

	public void TC_04_() {
		driver.get("https://automationfc.github.io/dynamic-loading/");

		waitForElementAndClick(By.cssSelector("div#start>button"));

		Assert.assertEquals(getWebElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");

		Assert.assertTrue(waitForElementAndDisplayed(By.xpath("//div[@id='finish']/h4[text()='Hello World!']")));
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

	// findElement (Custom)
	public WebElement getWebElement(By locator) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class);

		WebElement element = wait.until(new Function<WebDriver, WebElement>() {

			@Override
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		});

		return element;
	}

	public void waitForElementAndClick(By locator) {
		// FluentWait<WebDriver> wait = new FluentWait<WebDriver> (driver)
		// .withTimeout(Duration.ofSeconds(30))
		// .pollingEvery(Duration.ofSeconds(1))
		// .ignoring(NoSuchElementException.class);
		//
		// WebElement element = wait.until(new Function<WebDriver, WebElement>() {
		//
		// @Override
		// public WebElement apply(WebDriver driver) {
		// return driver.findElement(locator);
		// }
		// });
		WebElement element = getWebElement(locator);
		element.click();
	}

	public boolean waitForElementAndDisplayed(By locator) {
		WebElement element = getWebElement(locator);
		// FluentWait<WebElement> wait = new FluentWait<WebElement> (element)
		// .withTimeout(Duration.ofSeconds(30))
		// .pollingEvery(Duration.ofSeconds(1))
		// .ignoring(NoSuchElementException.class);
		//
		//
		// boolean isDisplayed = wait.until(new Function<WebElement, Boolean> () {
		//
		// @Override
		// public Boolean apply(WebElement element) {
		// boolean flag = element.isDisplayed();
		// return flag;
		// }
		// });
		return element.isDisplayed();
	}
}
