package testng;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_03_Group {

	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	
	@BeforeClass
	public void beforeClass() {
		 // Firefox
		 System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		 driver = new FirefoxDriver();
		 
		 Assert.assertTrue(false);
	}

	@Test(groups = "user")
	public void TC_01() {
		System.out.println("Run Testcase 01");
	}

	@Test(groups = "user")
	public void TC_02() {
		System.out.println("Run Testcase 02");
	}

	@Test(groups = { "user", "admin" })
	public void TC_03() {
		System.out.println("Run Testcase 03");
	}

	@Test(groups = { "user", "super" })
	public void TC_04() {
		System.out.println("Run Testcase 04");
	}

	@Test(groups = { "super" })
	public void TC_05() {
		System.out.println("Run Testcase 05");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
