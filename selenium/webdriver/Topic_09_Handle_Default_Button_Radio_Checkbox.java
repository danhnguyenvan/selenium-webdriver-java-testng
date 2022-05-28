package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_09_Handle_Default_Button_Radio_Checkbox {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
	}
	
	
	@Test
	public void TC_01_Button() {
		driver.get("https://www.fahasa.com/customer/account/create");
		
		// Xử lý Popup thì ko cần sleep cứng
		sleepInSecond(5);
		
		driver.findElement(By.cssSelector("li.popup-login-tab-login")).click();
		By loginButtonBy = By.cssSelector("button.fhs-btn-login");
		
		// Verify login button is disabled
		Assert.assertFalse(driver.findElement(loginButtonBy).isEnabled());
		
		driver.findElement(By.id("login_username")).sendKeys("johnweek@gmail.net");
		driver.findElement(By.id("login_password")).sendKeys("123456");
		
		// Verify login button is enabled
		Assert.assertTrue(driver.findElement(loginButtonBy).isEnabled());
		
		// Verify background color is RED
		// rgb(201, 33, 39)
		String loginButtonBackgroundRgba = driver.findElement(loginButtonBy).getCssValue("background-color");
		System.out.println("loginButtonBackgroundRgba: " + loginButtonBackgroundRgba);
		String loginButtonBackgroundHexa = Color.fromString(loginButtonBackgroundRgba).asHex().toUpperCase();
		Assert.assertEquals(loginButtonBackgroundHexa, "#C92127");	
	}

	@Test
	public void TC_02_Default_Radio_Checkbox() {
		driver.get("https://automationfc.github.io/multiple-fields/");
		
		// Checkbox
		By emotionalCheckbox = By.xpath("//input[@value='Emotional Disorder']");
		By digestiveCheckbox = By.xpath("//input[@value='Digestive Problems']");
		By venerealCheckbox = By.xpath("//input[@value='Venereal Disease']");
		
		// Radio button
		By fiveDayRadio = By.xpath("//input[@value='5+ days']");
		By dietPlanRadio = By.xpath("//input[@value=\"I don't have a diet plan\"]");
		By glassRadio = By.xpath("//input[@value='3-4 glasses/day']");
		
		// 1 - Chọn (Select) - Click
		// Checkbox
		// Emotional Discoder
		driver.findElement(emotionalCheckbox).click();
		// Digestive Problems
		driver.findElement(digestiveCheckbox).click();
		// Venereal Disease
		driver.findElement(venerealCheckbox).click();
		
		
		// Radio button
		// 5+ days
		driver.findElement(fiveDayRadio).click();
		// I don't have a diet plan
		driver.findElement(dietPlanRadio).click();
		// 3-4 glasses/day
		driver.findElement(glassRadio).click();
		
		// 2 - Verify (Chọn rồi hay chọn thành công)
		Assert.assertTrue(driver.findElement(emotionalCheckbox).isSelected());
		Assert.assertTrue(driver.findElement(digestiveCheckbox).isSelected());
		Assert.assertTrue(driver.findElement(venerealCheckbox).isSelected());
		Assert.assertTrue(driver.findElement(fiveDayRadio).isSelected());
		Assert.assertTrue(driver.findElement(dietPlanRadio).isSelected());
		Assert.assertTrue(driver.findElement(glassRadio).isSelected());
		
		// 3 - Bỏ chọn (Deselect) - Click
		// Checkbox
		driver.findElement(emotionalCheckbox).click();
		driver.findElement(digestiveCheckbox).click();
		driver.findElement(venerealCheckbox).click();
		
		driver.findElement(fiveDayRadio).click();
		driver.findElement(dietPlanRadio).click();
		driver.findElement(glassRadio).click();
		
		// 4 - Verify (Chọn rồi hay chọn thành công)
		Assert.assertFalse(driver.findElement(emotionalCheckbox).isSelected());
		Assert.assertFalse(driver.findElement(digestiveCheckbox).isSelected());
		Assert.assertFalse(driver.findElement(venerealCheckbox).isSelected());
		Assert.assertTrue(driver.findElement(fiveDayRadio).isSelected());
		Assert.assertTrue(driver.findElement(dietPlanRadio).isSelected());
		Assert.assertTrue(driver.findElement(glassRadio).isSelected());
	}
	
	@Test
	public void TC_03_Select_All_Checkbox() {
		driver.get("https://automationfc.github.io/multiple-fields/");
		
		// Select all checkboxes
		List<WebElement> allCheckboxes = driver.findElements(By.xpath("//input[@type='checkbox']"));
		for (WebElement checkbox : allCheckboxes) {
			if (!checkbox.isSelected()) {
				checkbox.click();
			}
		}
		
		// Verify all checkboxes are selected
		for (WebElement checkbox : allCheckboxes) {
			Assert.assertTrue(checkbox.isSelected());
		}
		
		// Deselect all checkboxes
		for (WebElement checkbox : allCheckboxes) {
			if (checkbox.isSelected()) {
				checkbox.click();
			}
		}
		
		// Verify all checkboxes are deselected
		for (WebElement checkbox : allCheckboxes) {
			Assert.assertFalse(checkbox.isSelected());
		}
	}
	
	@Test
	public void TC_04_Telerik_Checkbox() {
		driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
		sleepInSecond(3);
		
		By airCondtionCheckbox = By.id("eq5");
		
		// Select - Click 
		driver.findElement(airCondtionCheckbox).click();
		
		// Verify (đã chọn thành công)
		Assert.assertTrue(driver.findElement(airCondtionCheckbox).isSelected());
		
		// Deselected - Click
		driver.findElement(airCondtionCheckbox).click();
		
		// Verify đã bỏ chọn
		Assert.assertFalse(driver.findElement(airCondtionCheckbox).isSelected()); 
	}
	
	@Test
	public void TC_05_Telerik_Radio_Checkbox() {
		driver.get("http://demos.telerik.com/kendo-ui/styling/radios");
		sleepInSecond(3);
		
		By engineThreeRadio = By.xpath("//input[@id='engine1']");
		
		// Select - Click 
		driver.findElement(engineThreeRadio).click();
		
		// Check Radio button is selected whether or not
		// Verify Radio button is selected
		if (driver.findElement(engineThreeRadio).isSelected()) {
			Assert.assertTrue(driver.findElement(engineThreeRadio).isSelected()); 
		} else {
			driver.findElement(engineThreeRadio).click();
			Assert.assertTrue(driver.findElement(engineThreeRadio).isSelected()); 
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
