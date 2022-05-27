package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

public class Topic_08_Custom_Dropdown_List {
	WebDriver driver;
	WebDriverWait explicitWait;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		// Driver ID
		
		// Wait để apply cho các trạng thái của element (visible/ invisible/ presence/ clickable/..)
		explicitWait = new WebDriverWait(driver, 15);
		
		// Reference casting
		jsExecutor = (JavascriptExecutor) driver;
		
		// Wait để tìm element (findElement/ findElements)
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}
	
	
	@Test
	public void TC_01_JQuery() {
		driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
		
		selectItemInCustomDropdown("span#number-button", "ul#number-menu div", "5");
		sleepInSecond(2);
		Assert.assertTrue(isElementDisplayed(By.xpath("//span[@id='number-button']/span[@class='ui-selectmenu-text' and text()='5']")));
		
		selectItemInCustomDropdown("span#number-button", "ul#number-menu div", "15");
		sleepInSecond(2);
		Assert.assertTrue(isElementDisplayed(By.xpath("//span[@id='number-button']/span[@class='ui-selectmenu-text' and text()='15']")));
		
		selectItemInCustomDropdown("span#number-button", "ul#number-menu div", "10");
		sleepInSecond(2);
		Assert.assertTrue(isElementDisplayed(By.xpath("//span[@id='number-button']/span[@class='ui-selectmenu-text' and text()='10']")));
		
		selectItemInCustomDropdown("span#number-button", "ul#number-menu div", "19");
		sleepInSecond(2);
		Assert.assertTrue(isElementDisplayed(By.xpath("//span[@id='number-button']/span[@class='ui-selectmenu-text' and text()='19']")));	
	}

	@Test
	public void TC_02_ReactJS() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
		
		selectItemInCustomDropdown("i.dropdown.icon", "div[role='option']>span", "Stevie Feliciano");
		sleepInSecond(2);
		Assert.assertTrue(isElementDisplayed(By.xpath("//div[@role='alert' and text()='Stevie Feliciano']")));
		
		selectItemInCustomDropdown("i.dropdown.icon", "div[role='option']>span", "Jenny Hess");
		sleepInSecond(2);
		Assert.assertTrue(isElementDisplayed(By.xpath("//div[@role='alert' and text()='Jenny Hess']")));
		
		selectItemInCustomDropdown("i.dropdown.icon", "div[role='option']>span", "Matt");
		sleepInSecond(2);
		Assert.assertTrue(isElementDisplayed(By.xpath("//div[@role='alert' and text()='Matt']")));
	}
	
	@Test
	public void TC_03_VueJS() {
		driver.get("https://mikerodham.github.io/vue-dropdowns/");
		
		selectItemInCustomDropdown("li.dropdown-toggle", "ul.dropdown-menu a", "First Option");
		sleepInSecond(2);
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='dropdown-toggle' and contains(text(), 'First Option')]")));
		
		selectItemInCustomDropdown("li.dropdown-toggle", "ul.dropdown-menu a", "Second Option");
		sleepInSecond(2);
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='dropdown-toggle' and contains(text(), 'Second Option')]")));
		
		selectItemInCustomDropdown("li.dropdown-toggle", "ul.dropdown-menu a", "Third Option");
		sleepInSecond(2);
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='dropdown-toggle' and contains(text(), 'Third Option')]")));
	}
	
	@Test
	public void TC_04_Default_NopComerce() {
		driver.get("https://demo.nopcommerce.com/register");
		
		selectItemInCustomDropdown("select[name='DateOfBirthDay']", "select[name='DateOfBirthDay']>option", "10");
		Assert.assertTrue(driver.findElement(By.xpath("//select[@name='DateOfBirthDay']/option[text()='10']")).isSelected());
		
		selectItemInCustomDropdown("select[name='DateOfBirthDay']", "select[name='DateOfBirthDay']>option", "15");
		Assert.assertTrue(driver.findElement(By.xpath("//select[@name='DateOfBirthDay']/option[text()='15']")).isSelected());
	}
	
	@Test
	public void TC_05_Editable() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
		
		entertItemInCustomDropdown("input.search", "div.item>span", "Armenia");
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Armenia");
		
		entertItemInCustomDropdown("input.search", "div.item>span", "Australia");
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Australia");
		
		entertItemInCustomDropdown("input.search", "div.item>span", "Belgium");
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Belgium");
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
	
	public void selectItemInCustomDropdown(String parentLocator, String childLocator, String expectedTextItem) {
		// 1 - Click vào 1 element cho nó xổ ra tất cả item
		driver.findElement(By.cssSelector(parentLocator)).click();
		sleepInSecond(1);

		// 2 - Wait cho tất cả element được load ra (có trong HTML/ DOM)
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(childLocator)));
		// Store lại tất cả element (item của dropdown)
		List<WebElement> allDropdownItems = driver.findElements(By.cssSelector(childLocator));
		
		for (WebElement item : allDropdownItems) {
			String actualTextItem = item.getText();
			if (actualTextItem.equals(expectedTextItem)) {
				item.click();
				sleepInSecond(1);
				break;
			}
		}
	}
	
	public void entertItemInCustomDropdown(String editableLocator, String childLocator, String expectedTextItem) {
		// 1 - Click vào 1 element cho nó xổ ra tất cả item
		driver.findElement(By.cssSelector(editableLocator)).sendKeys(expectedTextItem);
		sleepInSecond(1);

		// 2 - Wait cho tất cả element được load ra (có trong HTML/ DOM)
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(childLocator)));
		// Store lại tất cả element (item của dropdown)
		List<WebElement> allDropdownItems = driver.findElements(By.cssSelector(childLocator));
		
		for (WebElement item : allDropdownItems) {
			String actualTextItem = item.getText();
			if (actualTextItem.equals(expectedTextItem)) {
				item.click();
				sleepInSecond(1);
				break;
			}
		}
	}
	
}
