package webdriver;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_14_Windows_Tab {
	WebDriver driver;
	Alert alert;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	@Test
	public void TC_01_Page_ID() {
		// Tab A: Parent page
		driver.get("https://automationfc.github.io/basic-form/index.html");

		// Get ra ID của window/ tab mà driver đang active
		String parentPageID = driver.getWindowHandle();
		System.out.println("parentPageID: " + parentPageID);

		// Tab B: Google page
		// Click vào Google link
		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
		sleepInSecond(2);

		// Set ko cho lưu trùng dữ liệu
		// Set<String> allWindows = driver.getWindowHandles();

		// Switch qua Google page
		switchToWindowByID(parentPageID);

		String googlePageID = driver.getWindowHandle();
		System.out.println("Google page ID: " + googlePageID);

		driver.findElement(By.name("q")).sendKeys("Selenium");
		sleepInSecond(2);
		driver.findElement(By.name("btnK")).click();

		// Switch qua Parent page
		switchToWindowByID(googlePageID);
		sleepInSecond(3);
	}

	@Test
	public void TC_02_Page_Title() {
		driver.get("https://automationfc.github.io/basic-form/index.html");

		// Click vào Google link
		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();

		// Switch qua Google Tab
		switchToWindowByPageTitle("Google");
		sleepInSecond(2);

		driver.findElement(By.name("q")).sendKeys("Selenium");
		sleepInSecond(2);
		driver.findElement(By.name("btnK")).click();

		// Switch về Parent Tab
		switchToWindowByPageTitle("SELENIUM WEBDRIVER FORM DEMO");
		sleepInSecond(2);

		// Click vào Facebook link
		driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();

		// Switch qua Facebook Tab
		switchToWindowByPageTitle("Facebook – log in or sign up");
		sleepInSecond(2);

		// Switch về Parent Tab
		switchToWindowByPageTitle("SELENIUM WEBDRIVER FORM DEMO");
		sleepInSecond(2);

		// Click vào Tiki link
		driver.findElement(By.xpath("//a[text()='TIKI']")).click();

		// Switch về Tiki Tab
		switchToWindowByPageTitle("Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");
		sleepInSecond(2);
	}

	@Test
	public void TC_02_Kyna_Title() {
		driver.get("https://kyna.vn/");
		String parentPageID = driver.getWindowHandle();

		// Click vào Facebook link
		driver.findElement(By.xpath("//div[@class='hotline']//img[@alt='facebook']")).click();

		switchToWindowByPageTitle("Kyna.vn - Home | Facebook");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@role='banner']/following-sibling::div//span[@dir='auto']/h1")).getText(), "Kyna.vn");

		switchToWindowByPageTitle("Kyna.vn - Học online cùng chuyên gia");

		// Click vào Youtube link
		driver.findElement(By.xpath("//div[@class='hotline']//img[@alt='youtube']")).click();

		switchToWindowByPageTitle("Kyna.vn - YouTube");
		Assert.assertEquals(driver.findElement(By.cssSelector("div#channel-header-container div#text-container>yt-formatted-string#text")).getText(), "Kyna.vn");

		closeAllWindowWithoutParent(parentPageID);
		sleepInSecond(3);
	}

	@Test
	public void TC_03_Live_TechPanda() {
		driver.get("http://live.techpanda.org/");

		String parentPageID = driver.getWindowHandle();

		By addCompareSony = By.xpath("//a[@title='Sony Xperia']/parent::h2/following-sibling::div[@class='actions']//a[@class='link-compare']");
		By addCompareSamsung = By.xpath("//a[@title='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']//a[@class='link-compare']");

		// Click vào Mobile tab
		driver.findElement(By.xpath("//a[text()='Mobile']")).click();

		// Add sản phẩm Sony Xperia vào để Compare
		driver.findElement(addCompareSony).click();

		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(), "The product Sony Xperia has been added to comparison list.");

		// Add sản phẩm Samsung Galaxy vào để Compare
		driver.findElement(addCompareSamsung).click();

		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(), "The product Samsung Galaxy has been added to comparison list.");

		// Click to Compare button
		driver.findElement(By.xpath("//span[text()='Compare']")).click();

		switchToWindowByPageTitle("Products Comparison List - Magento Commerce");

		String homePageTitle = driver.getTitle();
		// Verify title "Products Comparison List - Magento Commerce"
		Assert.assertEquals(homePageTitle, "Products Comparison List - Magento Commerce");

		closeAllWindowWithoutParent(parentPageID);

		// Click 'Clear All' link và accept alert
		driver.findElement(By.xpath("//a[text()='Clear All']")).click();
		alert = driver.switchTo().alert();
		alert.accept();
		sleepInSecond(2);

		// Verify message: "The comparison list was cleared."
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(), "The comparison list was cleared.");
	}

	@Test
	public void TC_04_Dictionary_Cambridge() {
		driver.get("https://dictionary.cambridge.org/vi/");

		By emailTextbox = By.cssSelector("form#gigya-login-form span[data-bound-to='loginID']");
		By passwordTextbox = By.cssSelector("form#gigya-login-form span[data-bound-to='password']");

		// Click vào Đăng nhập link
		driver.findElement(By.xpath("//header[@id='header']//span[text()='Đăng nhập']")).click();

		// Switch qua cửa sổ mới
		switchToWindowByPageTitle("Login");

		String loginPageID = driver.getWindowHandle();

		// Click Login button
		driver.findElement(By.xpath("//div[@id='login_content']//input[@class='gigya-input-submit']")).click();

		Assert.assertEquals(driver.findElement(emailTextbox).getText(), "This field is required");
		Assert.assertEquals(driver.findElement(passwordTextbox).getText(), "This field is required");
		sleepInSecond(2);

		// Nhập dữ liệu vào Email và Password
		driver.findElement(By.cssSelector("div#login_content input[name='username']")).sendKeys("automationfc.com@gmail.com");
		driver.findElement(By.cssSelector("div#login_content input[name='password']")).sendKeys("Automation000***");
		sleepInSecond(2);

		// Click Login button
		driver.findElement(By.xpath("//div[@id='login_content']//input[@class='gigya-input-submit']")).click();

		switchToWindowByID(loginPageID);

		// Verify đã login thành công
		Assert.assertEquals(driver.findElement(By.cssSelector("span.cdo-username")).getText(), "Automation FC");
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

	// = 2 Windows/ Tabs
	public void switchToWindowByID(String windowPageID) {
		// Lấy ra tất cả các ID đang có của các Tab/ Window
		Set<String> allWindows = driver.getWindowHandles();

		// Dùng vòng lặp duyệt qua từng ID
		for (String window : allWindows) {
			// Nếu như ID nào khác với ID truyền vào
			if (!window.equals(windowPageID)) {
				// Switch qua
				driver.switchTo().window(window);
			}
		}
	}

	// >= 2 Windows/ Tabs
	public void switchToWindowByPageTitle(String expectedPageTitle) {
		// Lấy ra tất cả các ID đang có của các Tab/ Window
		Set<String> allWindows = driver.getWindowHandles();

		// Dùng vòng lặp duyệt qua từng ID
		for (String window : allWindows) {
			// Cho switch qua từng Tab trước
			driver.switchTo().window(window);
			sleepInSecond(2);

			// Kiểm tra sau
			String actualPageTitle = driver.getTitle().trim();
			if (actualPageTitle.equals(expectedPageTitle)) {
				break;
			}
		}
	}

	public void closeAllWindowWithoutParent(String parentPageID) {
		// Lấy ra tất cả các ID đang có của các Tab/ Window
		Set<String> allWindows = driver.getWindowHandles();

		// Dùng vòng lặp duyệt qua từng ID
		for (String window : allWindows) {
			// Nếu như ID nào khác với ID truyền vào
			if (!window.equals(parentPageID)) {
				// Switch qua
				driver.switchTo().window(window);
				sleepInSecond(1);

				// Nó chỉ đóng Window/ Tab đang active
				driver.close(); // Driver vẫn đang đứng như cái Window/ Tab cuối cùng bị đóng
			}
		}
		// Switch qua parent id
		driver.switchTo().window(parentPageID);
		sleepInSecond(1);
	}

}
