package webdriver;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Wait_Part_VI_Mixing {
	WebDriver driver;
	WebDriverWait explicitWait;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		driver.manage().window().maximize();
	}
	
	
	@Test // Happy Path case
	public void TC_01_Found() {
		driver.get("https://www.facebook.com/");
		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		explicitWait = new WebDriverWait(driver, 15);
		
		System.out.println("1.1 - Start implicit wait: " + getDateTimeNow());
		driver.findElement(By.id("email"));
		System.out.println("1.2 - End implicit wait: " + getDateTimeNow());
		
		System.out.println("2.1 - Start explicit wait: " + getDateTimeNow());
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
		System.out.println("2.2 - End explicit wait: " + getDateTimeNow());
	}

	@Test // Unhappy Path case
	public void TC_02_Not_Found_Only_Implicit() {
		driver.get("https://www.facebook.com/");
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		System.out.println("1.1 - Start implicit wait: " + getDateTimeNow());
		try {
			driver.findElement(By.id("selenium"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("1.2 - End implicit wait: " + getDateTimeNow());
	}
	
	@Test // Unhappy Path case
	public void TC_03_Not_Found_Implicit_And_Explicit() {
		driver.get("https://www.facebook.com/");
		
		// Equal
		// >
		// <
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		explicitWait = new WebDriverWait(driver, 5);
		
		System.out.println("1.1 - Start implicit wait: " + getDateTimeNow());
		try {
			driver.findElement(By.id("selenium"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("1.2 - End implicit wait: " + getDateTimeNow());
		
		System.out.println("2.1 - Start explicit wait: " + getDateTimeNow());
		try {
			explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("selenium")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("2.2 - End explicit wait: " + getDateTimeNow());
		
	}
	
	@Test // Unhappy Path case
	public void TC_04_Not_Found_Only_Explicit_By() {
		driver.get("https://www.facebook.com/");
		
		explicitWait = new WebDriverWait(driver, 2);
		
		System.out.println("2.1 - Start explicit wait: " + getDateTimeNow());
		try {
			explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("selenium")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("2.2 - End explicit wait: " + getDateTimeNow());
	}
	
	@Test // Unhappy Path case
	public void TC_05_Not_Found_Only_Explicit_WebElement() {
		driver.get("https://www.facebook.com/");
		
		explicitWait = new WebDriverWait(driver, 2);
		
		System.out.println("2.1 - Start explicit wait: " + getDateTimeNow());
		try {
			explicitWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("selenium"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("2.2 - End explicit wait: " + getDateTimeNow());
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public String getDateTimeNow() {
		Date date = new Date();
		return date.toString();
	}
	
	public void sleepInSecond(long timeoutInSecond) {
		try {
			Thread.sleep(timeoutInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
