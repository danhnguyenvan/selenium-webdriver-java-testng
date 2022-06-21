package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Wait_Part_II_Find_Element {
	WebDriver driver;
	WebDriverWait explicitWait;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();

		driver.get("http://live.techpanda.org/");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@Test
	public void TC_01_Find_Element() {
		WebElement element = null;

		// 1 - Ko có element nào tìm thấy
		// element = driver.findElement(By.id("selenium"));
		// Khi 1 step mà fail thì sẽ fail cả testcase luôn - ko chạy các step còn lại nữa

		// 2 - Có 1 element được tìm thấy
		// element = driver.findElement(By.id("email"));
		// element.sendKeys("0900888111");

		// 3 - Có nhiều hơn 1 element được tìm thấy
		// Lấy cái đầu tiên để thao tác
		// Ko quan tâm element đó visible/ invisible
		element = driver.findElement(By.xpath("//a[@title='My Account']"));
		element.click();
	}

	@Test
	public void TC_02_Find_Elements() {
		List<WebElement> links = null;

		// 1 - Ko có element nào tìm thấy
		// links = driver.findElements(By.tagName("selenium"));
		// System.out.println("Element size: " + links.size());
		// 2 - Có 1 element được tìm thấy
		// links = driver.findElements(By.id("email"));
		// System.out.println("Element size: " + links.size());
		// links.get(0).sendKeys("danh@gmail.com");

		// 3 - Có nhiều hơn 1 element được tìm thấy
		// Lấy hết toàn bộ đưa vào List
		links = driver.findElements(By.xpath("//a[@title='My Account']"));
		System.out.println("Element size: " + links.size());

		for (WebElement link : links) {
			System.out.println(link.getText());
			System.out.println(link.getAttribute("href"));
		}
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
