package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_06_Web_Element_Command_II {
	// Khai báo biến driver
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	By emailTextboxBy = By.id("mail");
	By educationTextareaBy = By.id("edu");
	By user5TextBy = By.xpath("//h5[text()='Name: User5']");

	By passwordTextboxBy = By.id("disable_password");
	By slider2By = By.id("slider-2");

	By ageUnder18RadioBy = By.id("under_18");
	By developmentCheckboxBy = By.id("development");
	By javaCheckboxBy = By.id("java");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");

		// Khởi tạo driver
		driver = new FirefoxDriver();

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Is_Displayed() {
		driver.get("https://automationfc.github.io/basic-form/index.html");

		WebElement emailTextbox = driver.findElement(By.id("mail"));

		if (emailTextbox.isDisplayed()) {
			emailTextbox.sendKeys("Automation Testing");
			System.out.println("Email textbox is displayed");

		} else {
			System.out.println("Email textbox is not displayed");
		}

		WebElement ageUnder18Radio = driver.findElement(By.id("under_18"));

		if (ageUnder18Radio.isDisplayed()) {
			ageUnder18Radio.click();
			System.out.println("Age under 18 is displayed");

		} else {
			System.out.println("Age under 18 is not displayed");
		}

		WebElement educationTextarea = driver.findElement(By.id("edu"));

		if (educationTextarea.isDisplayed()) {
			educationTextarea.sendKeys("Automation Testing");
			System.out.println("Education Textarea is displayed");

		} else {
			System.out.println("Education Textarea is not displayed");
		}

		WebElement user5Text = driver.findElement(By.xpath("//h5[text()='Name: User5']"));
		if (user5Text.isDisplayed()) {
			System.out.println("User 5 text is displayed");
		} else {
			System.out.println("User 5 text is not displayed");
		}

	}

	@Test
	public void TC_02_Is_Displayed_Refactor() {
		driver.get("https://automationfc.github.io/basic-form/index.html");

		if (isElementDisplayed(emailTextboxBy)) {
			sendkeyToElement(emailTextboxBy, "Automation Testing");
		}

		if (isElementDisplayed(ageUnder18RadioBy)) {
			clickToElement(ageUnder18RadioBy);
		}

		if (isElementDisplayed(educationTextareaBy)) {
			sendkeyToElement(educationTextareaBy, "Automation Testing");
		}

		Assert.assertFalse(isElementDisplayed(user5TextBy));

	}

	@Test
	public void TC_03_Is_Enabled() {
		driver.get("https://automationfc.github.io/basic-form/index.html");

		// Mong đợi 1 element enabled
		Assert.assertTrue(isElementEnabled(emailTextboxBy));
		Assert.assertTrue(isElementEnabled(ageUnder18RadioBy));
		Assert.assertTrue(isElementEnabled(educationTextareaBy));

		// Mong đợi 1 element disabled
		Assert.assertFalse(isElementEnabled(passwordTextboxBy));
		Assert.assertFalse(isElementEnabled(slider2By));
	}

	@Test
	public void TC_04_Is_Selected() {
		driver.get("https://automationfc.github.io/basic-form/index.html");

		clickToElement(ageUnder18RadioBy);
		clickToElement(developmentCheckboxBy);

		Assert.assertTrue(isElementSelected(ageUnder18RadioBy));
		Assert.assertTrue(isElementSelected(developmentCheckboxBy));
		Assert.assertFalse(isElementSelected(javaCheckboxBy));

		clickToElement(ageUnder18RadioBy);
		clickToElement(developmentCheckboxBy);

		Assert.assertTrue(isElementSelected(ageUnder18RadioBy));
		Assert.assertFalse(isElementSelected(developmentCheckboxBy));
		Assert.assertFalse(isElementSelected(javaCheckboxBy));

	}

	public boolean isElementDisplayed(By by) {
		WebElement element = driver.findElement(by);
		if (element.isDisplayed()) {
			System.out.println("Element[" + by + "] is displayed");
			return true;
		} else {
			System.out.println("Element[" + by + "] is not displayed");
			return false;
		}
	}

	public boolean isElementEnabled(By by) {
		WebElement element = driver.findElement(by);
		if (element.isEnabled()) {
			System.out.println("Element[" + by + "] is enabled");
			return true;
		} else {
			System.out.println("Element[" + by + "] is disabled");
			return false;
		}
	}

	public boolean isElementSelected(By by) {
		WebElement element = driver.findElement(by);
		if (element.isSelected()) {
			System.out.println("Element[" + by + "] is selected");
			return true;
		} else {
			System.out.println("Element[" + by + "] is deselected");
			return false;
		}
	}

	public void sendkeyToElement(By by, String value) {
		WebElement element = driver.findElement(by);
		element.clear();
		element.sendKeys(value);
	}

	public void clickToElement(By by) {
		WebElement element = driver.findElement(by);
		element.click();
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
