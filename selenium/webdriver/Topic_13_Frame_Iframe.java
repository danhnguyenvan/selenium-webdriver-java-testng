package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_13_Frame_Iframe {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
	}
	
	
	@Test
	public void TC_01_Iframe() {
		// HTML - A (Parent)
		driver.get("https://kyna.vn/");
		
		// Switch vào iframe chứa element trước (HTML - B)
		driver.switchTo().frame(driver.findElement(By.cssSelector("div.fanpage iframe"))); 
		
		String kynaFanpageLike = driver.findElement(By.xpath("//a[@title='Kyna.vn']/parent::div/following-sibling::div")).getText();
		System.out.println("Lượt like: " + kynaFanpageLike);
		
		// Back to parent page (A)
		driver.switchTo().defaultContent();
		
		// Login button thuộc A (parent)
		Assert.assertTrue(driver.findElement(By.cssSelector("a.login-btn")).isDisplayed());
	}
	
	@Test
	public void TC_02_Iframe() {
		driver.get("https://automationfc.com/2020/02/18/training-online-automation-testing/");
		
		driver.switchTo().frame(driver.findElement(By.cssSelector("div.post-inner iframe.youtube-player")));
		
		String bottomVideoTitle = driver.findElement(By.cssSelector("a.ytp-title-link")).getText();
		Assert.assertEquals(bottomVideoTitle, "[Online 23] - Topic 01 (Introduction about Course and Target)");
		
		// Switch về parent page
		driver.switchTo().defaultContent();
		
		// Switch vào iframe của sidebar youtube
		driver.switchTo().frame(driver.findElement(By.cssSelector("div.sidebar span.embed-youtube>iframe")));
		
		String topVideoTitle = driver.findElement(By.cssSelector("a.ytp-title-link")).getText();
		Assert.assertEquals(topVideoTitle, "[Online 25] - Topic 01 (Introduction about Course and Target)");
		
		// Switch về parent page 
		driver.switchTo().defaultContent();
		
		Assert.assertEquals(driver.findElement(By.xpath("//h1[@class='post-title']")).getText(), "[Training] – Fullstack Selenium in Java (Livestream)");
	}

	@Test
	public void TC_03_Frame() {
		driver.get("https://netbanking.hdfcbank.com/netbanking/");
		
		// Switch vào frame của login page
		driver.switchTo().frame(driver.findElement(By.name("login_page")));
		
		driver.findElement(By.cssSelector("")).sendKeys("automationfc");
		sleepInSecond(2);
		
		driver.findElement(By.cssSelector("a.login-btn")).click();
		
		Assert.assertTrue(driver.findElement(By.cssSelector("input[id='fldPasswordDispId']")).isDisplayed());
		
		driver.findElement(By.xpath("//a[text()='Terms and Conditions']")).click();
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
