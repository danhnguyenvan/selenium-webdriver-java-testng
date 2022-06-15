package webdriver;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_16_Upload_File_Part_II {
	WebDriver driver;
	WebDriverWait explicitWait;
	String projectPath = System.getProperty("user.dir");

	String dalatName = "DaLat.jpg";
	String hueName = "Hue.jpg";
	String sapaName = "SaPa.jpg";
	String dalatPath = projectPath + "\\uploadFiles\\" + dalatName;
	String huePath = projectPath + "\\uploadFiles\\" + hueName;
	String sapaPath = projectPath + "\\uploadFiles\\" + sapaName;
	String singleFirefox = projectPath + "\\autoITAuthentication\\" + "Single_Firefox.exe";
	String singleChrome = projectPath + "\\autoITAuthentication\\" + "Single_Chrome.exe";
	String multipleFirefox = projectPath + "\\autoITAuthentication\\" + "Multiple_Firefox.exe";
	String multipleChrome = projectPath + "\\autoITAuthentication\\" + "Multiple_Chrome.exe";

	String browserDriverName;

	@BeforeClass
	public void beforeClass() {
		// Firefox
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();

		// Chrome
		// System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		// driver = new ChromeDriver();

		browserDriverName = driver.toString().toLowerCase();

		explicitWait = new WebDriverWait(driver, 20);

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	@Test
	public void TC_01_AutoIT_Single_File() throws IOException {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");

		// Load file
		driver.findElement(By.cssSelector("span.fileinput-button")).click();
		sleepInSecond(2);

		// Load file via AutoIT
		// Firefox
		Runtime.getRuntime().exec(new String[] { singleFirefox, dalatPath });
		// Chrome
		// Runtime.getRuntime().exec(new String[] { singleChrome, dalatPath });
		sleepInSecond(2);

		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + dalatName + "']")).isDisplayed());
	}

	@Test
	public void TC_02_AutoIT_Multiple_Files() throws IOException {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");

		driver.findElement(By.cssSelector("span.fileinput-button")).click();
		sleepInSecond(2);

		// Load file via AutoIT
		if (browserDriverName.contains("firefox")) {
			Runtime.getRuntime().exec(new String[] { multipleFirefox, dalatPath, sapaPath });
		} else if (browserDriverName.contains("chrome") || browserDriverName.contains("opera") || browserDriverName.contains("edge")) {
			Runtime.getRuntime().exec(new String[] { multipleChrome, dalatPath, sapaPath });
		}
		sleepInSecond(2);

		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + dalatName + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + sapaName + "']")).isDisplayed());
	}

	@Test
	public void TC_03_Robot() throws IOException, AWTException {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");

		driver.findElement(By.cssSelector("span.fileinput-button")).click();
		sleepInSecond(2);

		// Specify the file location with extension
		StringSelection select = new StringSelection(dalatPath);

		// Copy to clipboard
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(select, null);

		Robot robot = new Robot();
		sleepInSecond(1);

		// Nhan phim Enter
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		// Nhan xuong Ctrl - V
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);

		// Nha Ctrl - V
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
		sleepInSecond(1);

		// Nhan Enter
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + dalatName + "']")).isDisplayed());
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
