package testng;

import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_07_Loop {

	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	String emailAddress = "afc";
	String password = "afc123456";

	@BeforeClass
	public void beforeClass() {
//		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
//		driver = new FirefoxDriver();
//
//		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	@Test(invocationCount = 100)
	public void TC_01_Register_To_System() {
		System.out.println(emailAddress + getRandomNumber() + "@mail.com");
		System.out.println(password);
	}

	public int getRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(9999);
	}

	@AfterClass
	public void afterClass() {
//		driver.quit();
	}
}
