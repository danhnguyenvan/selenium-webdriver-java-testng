package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_09_Handle_Custom_Button_Radio_Checkbox {
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		jsExecutor = (JavascriptExecutor) driver;
	}

	@Test
	public void TC_01_Custom_Checkbox() {
		driver.get("https://material.angular.io/components/checkbox/examples");

		// Hàm isSelected() chỉ work vs loại element là checkbox/ radio (phải là thẻ //
		// input)
		// This operation only applies to inputelements such as checkboxes, options in a
		// select and radio buttons.

		// Case 1:
		// - Thẻ input ko click được -> Failed
		// - Thẻ input dùng verify được -> Passed
		// driver.findElement(By.xpath("//span[text()='Checked']/preceding-sibling::span/input")).click();

		// Case 2:
		// - Ko dùng thẻ input để click - thẻ span chứa text -> Passed
		// - Ko dùng thẻ input để verify -> Failed
		// By checkedCheckbox = By.xpath("//span[text()='Checked']");
		// driver.findElement(checkedCheckbox).click();
		// sleepInSecond(3);

		// Assert.assertTrue(driver.findElement(checkedCheckbox).isSelected());

		// Case 3:
		// - Ko dùng thẻ input để click - thẻ span chứa text -> Passed
		// - Thẻ input dùng verify được -> Passed
		// By checkedCheckboxText = By.xpath("//span[text()='Checked']");
		// driver.findElement(checkedCheckboxText).click();
		// sleepInSecond(3);
		//
		// By checkedCheckbox =
		// By.xpath("//span[text()='Checked']/preceding-sibling::span/input");
		// Assert.assertTrue(driver.findElement(checkedCheckbox).isSelected());

		// Case 4: Thỏa mãn điều kiện (Vừa click/ vừa verify được)
		// - Thẻ input để click -> Passed (JavascriptExecutor)
		// - Thẻ input dùng để verify được -> Passed
		// JavascriptExecutor: Ko quan tâm element bị che hay ko (Vẫn click được)

		By checkedCheckbox = By.xpath("//span[text()='Checked']/preceding-sibling::span/input");
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(checkedCheckbox));
		Assert.assertTrue(driver.findElement(checkedCheckbox).isSelected());

		By beforeRadio = By.xpath("//span[text()='Before']/preceding-sibling::span/input");
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(beforeRadio));
		Assert.assertTrue(driver.findElement(beforeRadio).isSelected());
	}

	@Test
	public void TC_02_Google_Docs() {
		driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");
		sleepInSecond(2);
		
		// Radio
		By hanoiRadio = By.xpath("//div[@aria-label='Hà Nội']");
		driver.findElement(hanoiRadio).click();
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(hanoiRadio).getAttribute("aria-checked"), "true");

		// Checkbox
		// By miquangCheckbox = By.xpath("//div[@aria-label='Mì Quảng']/parent::div");
		// driver.findElement(miquangCheckbox).click();
		// sleepInSecond(3);
		// Assert.assertEquals(driver.findElement(By.xpath("//div[@aria-label='Mì Quảng']")).getAttribute("aria-checked"),"true");

		// Using JavascriptExecutor -> click
		By miquangCheckboxJs = By.xpath("//div[@aria-label='Mì Quảng']");
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(miquangCheckboxJs));
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(miquangCheckboxJs).getAttribute("aria-checked"),"true");
	}
	
	@Test
	public void TC_03_Custom_Checkbox_Radio_Button_Angular() {
		// Radio
		driver.get("https://material.angular.io/components/radio/examples");
		sleepInSecond(2);
		
		By summerRadio = By.xpath("//span[text()=' Summer ']/preceding-sibling::span/input");
		
		if (!driver.findElement(summerRadio).isSelected()) { // Radio button is not selected
			// Selected
			jsExecutor.executeScript("arguments[0].click();", driver.findElement(summerRadio));
			Assert.assertTrue(driver.findElement(summerRadio).isSelected());
		} else { // Radio button is selected
			Assert.assertTrue(driver.findElement(summerRadio).isSelected());
		}
		
		// Checkbox
		driver.get("https://material.angular.io/components/checkbox/examples");
		sleepInSecond(2);
		
		// Selected 
		By checkedCheckbox = By.xpath("//span[text()='Checked']/preceding-sibling::span/input");
		By indeterminateCheckbox = By.xpath("//span[text()='Indeterminate']/preceding-sibling::span/input");
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(checkedCheckbox));
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(indeterminateCheckbox));
		
		Assert.assertTrue(driver.findElement(checkedCheckbox).isSelected());
		Assert.assertTrue(driver.findElement(indeterminateCheckbox).isSelected());
		
		// Deselected
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(checkedCheckbox));
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(indeterminateCheckbox));
		
		Assert.assertFalse(driver.findElement(checkedCheckbox).isSelected());
		Assert.assertFalse(driver.findElement(indeterminateCheckbox).isSelected());	
		
	}


	@Test
	public void TC_04_Custom_Radio_Button_Google_Docs() {
		driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");
		sleepInSecond(2);
		
		By canThoRadio = By.xpath("//div[@aria-label='Cần Thơ']");
		Assert.assertFalse(driver.findElement(canThoRadio).isSelected());
		
		driver.findElement(canThoRadio).click();
		Assert.assertEquals(driver.findElement(canThoRadio).getAttribute("aria-checked"), "true");
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
