package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_08_Default_Dropdown_List {
	WebDriver driver;
	Select select;
	String firstName, lastName, emailAddress, company, password;
	String projectPath = System.getProperty("user.dir");

	By genderMaleBy = By.id("gender-male");
	By firstNameBy = By.id("FirstName");
	By lastNameBy = By.id("LastName");
	By dayDropdownBy = By.name("DateOfBirthDay");
	By monthDropdownBy = By.name("DateOfBirthMonth");
	By yearDropdownBy = By.name("DateOfBirthYear");
	By emailBy = By.id("Email");
	By companyBy = By.id("Company");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();

		firstName = "Automation";
		lastName = "FC";
		emailAddress = "autofc" + getRandomNumber() + "@gmail.net";
		company = "Automation VN";
		password = "123456";

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@Test
	public void TC_01_NopCommerce_Dropdown_List_01() {
		driver.get("https://demo.nopcommerce.com/");

		String day = "15";
		String month = "December";
		String year = "1999";

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

	@Test
	public void TC_01_NopCommerce_Dropdown_List_02() {
		driver.get("https://demo.nopcommerce.com/register");

		String day = "1";
		String month = "May";
		String year = "1980";

		driver.findElement(By.cssSelector("a.ico-register")).click();
		driver.findElement(genderMaleBy).click();
		driver.findElement(firstNameBy).sendKeys(firstName);
		driver.findElement(lastNameBy).sendKeys(lastName);

		select = new Select(driver.findElement(dayDropdownBy));
		select.selectByVisibleText(day);

		// Get ra tổng số item trong dropdown là bao nhiêu => Verify nó bằng bao nhiêu
		Assert.assertEquals(select.getOptions().size(), 32);

		select = new Select(driver.findElement(monthDropdownBy));
		select.selectByVisibleText(month);

		// Get ra tổng số item trong dropdown là bao nhiêu => Verify nó bằng bao nhiêu
		Assert.assertEquals(select.getOptions().size(), 13);

		select = new Select(driver.findElement(yearDropdownBy));
		select.selectByVisibleText(year);

		// Get ra tổng số item trong dropdown là bao nhiêu => Verify nó bằng bao nhiêu
		Assert.assertEquals(select.getOptions().size(), 112);

		driver.findElement(emailBy).sendKeys(emailAddress);
		driver.findElement(companyBy).sendKeys(company);
		driver.findElement(By.id("Password")).sendKeys(password);
		driver.findElement(By.id("ConfirmPassword")).sendKeys(password);

		// Click REGISTER button
		driver.findElement(By.id("register-button")).click();

		// Verify text xuất hiện khi đăng kí thành công
		Assert.assertEquals(driver.findElement(By.cssSelector("div.result")).getText(), "Your registration completed");

		// Click vào trang My Account
		driver.findElement(By.cssSelector("a.ico-account")).click();

		// Verify ngày, tháng, năm nhập vào là đúng
		select = new Select(driver.findElement(dayDropdownBy));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), day);

		select = new Select(driver.findElement(monthDropdownBy));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), month);

		select = new Select(driver.findElement(yearDropdownBy));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), year);

	}

	@Test
	public void TC_02_Rode() {
		driver.get("https://rode.com/en/support/where-to-buy");

		select = new Select(driver.findElement(By.id("country")));
		select.selectByVisibleText("Vietnam");

		driver.findElement(By.xpath("//button[@class='btn btn-default']")).click();

		List<WebElement> storeName = driver.findElements(
				By.xpath("//div[@id='where_to_buy_map']//div[@class='align-self-stretch']/div[@class='p-1']/h4"));
		Assert.assertEquals(storeName.size(), 37);

		for (WebElement store : storeName) {
			System.out.println(store.getText());
		}
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
