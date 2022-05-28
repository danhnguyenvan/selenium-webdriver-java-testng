package webdriver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_09_Handle_Alert {
	WebDriver driver;
	Alert alert;
	String projectPath = System.getProperty("user.dir");
	String autoITFirefox = projectPath + "\\autoITAuthentication\\authen_firefox.exe";

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}
	
	
	@Test
	public void TC_01_Accept_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
		sleepInSecond(2);
		
		// Nó chỉ nên khởi tạo khi element/ alert đó xuất hiện
		// Muốn thao tác được với Alert đang bật lên đó thì cần phải switch vào nó
		// Switch vào Alert sau lúc alert này bật lên
		alert = driver.switchTo().alert();
		
		// Verify title của Alert
		Assert.assertEquals(alert.getText(), "I am a JS Alert");
		
		// Accept cái Alert này
		alert.accept();
		
		// Verify alert được accept thành công
		Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You clicked an alert successfully");
	}

	@Test
	public void TC_02_Confirm_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
		sleepInSecond(2);
		
		alert = driver.switchTo().alert();
		
		Assert.assertEquals(alert.getText(), "I am a JS Confirm");
		
		// Cancel alert
		alert.dismiss();
		
		// Verify alert được Cancel thành công
		Assert.assertEquals(driver.findElement(By.cssSelector("p#result")), "You clicked: Cancel");
	}
	
	@Test
	public void TC_03_Prompt_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
		sleepInSecond(2);
		
		alert = driver.switchTo().alert();
		
		Assert.assertEquals(alert.getText(), "I am a JS prompt");
		
		String text = "AutomationFC";
		
		alert.sendKeys(text);
		sleepInSecond(2);
		
		alert.accept();
		
		// Verify alert được Accept thành công
		Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You entered: " + text);
	}
	
	@Test
	public void TC_04_Authentication_Alert_Trick() {
		// Format: https://Username:Password@domain
		String username = "admin";
		String password = "admin";
		String url = "http://" + username + ":" + password + "@" + "the-internet.herokuapp.com/basic_auth";
		driver.get(url);
		
		// So sánh tương đối trong case dữ liệu ko gọn
		String contentText = driver.findElement(By.xpath("//div[@id='content']//p")).getText();
		Assert.assertTrue(contentText.contains("Congratulations! You must have the proper credentials."));
	}
	
	@Test
	public void TC_05_Authentication_Alert_Trick_Navigation_From_Other_Page() {
		String username = "admin";
		String password = "admin";
		
		driver.get("http://the-internet.herokuapp.com/");
		
		// Ko nên Click vào link để nó show Dialog ra
		// Nên get cái Url của link đó
		
		String basicAuthenLink = driver.findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href");
		// http://the-internet.herokuapp.com/basic_auth
		
		// 1 - Tách link này ra thành nhiều chuỗi
		String[] authenLinkArray = basicAuthenLink.split("//");
		// http:
		// the-internet.herokuapp.com/basic_auth
		
		// 2 - Lấy chuỗi cộng vào
		String newAuthenLinkUrl = authenLinkArray[0] + "//" + username + ":" + password + "@" + authenLinkArray[1];
		
		driver.get(newAuthenLinkUrl);
		
		// So sánh tương đối trong case dữ liệu ko gọn
		String contentText = driver.findElement(By.xpath("//div[@id='content']//p")).getText();
		Assert.assertTrue(contentText.contains("Congratulations! You must have the proper credentials."));
	}
	
	@Test
	public void TC_06_Authentication_Alert() throws IOException {
		String username = "admin";
		String password = "admin";
		
		Runtime.getRuntime().exec(new String[] {autoITFirefox, username, password});
		
		driver.get("http://the-internet.herokuapp.com/basic_auth");
		sleepInSecond(2);
		
		String contentText = driver.findElement(By.xpath("//div[@id='content']//p")).getText();
		Assert.assertTrue(contentText.contains("Congratulations! You must have the proper credentials."));
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
