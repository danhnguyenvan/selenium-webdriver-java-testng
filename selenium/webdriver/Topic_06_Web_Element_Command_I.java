package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_06_Web_Element_Command_I {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	TakesScreenshot scrShot;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		scrShot = ((TakesScreenshot) driver);
		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
	}
	
	
	@Test
	public void TC_01_() {
		// WebBrowser command/ method/ API
		// driver instance/ variable
		
		
		// WebElement command/ method/ API
		// 1
		driver.findElement(By.name("login")).click();
		
		// 2
		WebElement emailTextbox = driver.findElement(By.id("email"));
		emailTextbox.clear();
		emailTextbox.sendKeys("afc@gmail.com");
		emailTextbox.isDisplayed();
		
		WebElement element = driver.findElement(By.id(""));
		
		// Xóa dữ liệu trong editable field (textbox/ textarea/ dropdown)
		element.clear();	//**
		
		// Nhập dữ liệu trong editable field (textbox/ textarea/ dropdown)
		element.sendKeys("...");
		element.sendKeys(Keys.ENTER);	//**
		
		// Click vào button/ link/ radio/ checkbox/ image/..
		element.click();	//**
		
		// Trả về dữ liệu nằm trong attribute của element
		element.getAttribute("placeholder");
		element.getAttribute("value");	//**
		// Email hoặc số điện thoại
		// afc@gmail.com
		
		// Lấy thuộc tính của element: font size/ size/ color/ font style/..
		element.getCssValue("background-color");	//**
		// #4ab2f1
		// hexa
		// rgba
		
		
		// GUI
		element.getLocation();
		element.getRect();
		element.getSize();
		
		
		// Take screenshot -> Attach to HTML report
		// Java Generic
		element.getScreenshotAs(OutputType.FILE);	//**
		element.getScreenshotAs(OutputType.BASE64);
		element.getScreenshotAs(OutputType.BYTES);
		
		
		// Tên thẻ HTML
		// Dùng By.id/ class/ css/ name
		// Đầu ra của step này -> đầu vào của step kia
		element = driver.findElement(By.cssSelector("#save-info-button"));
		String saveButtonTagname = element.getTagName();
		driver.findElement(By.xpath("//" + saveButtonTagname + "[@name='email'"));
		
		String addressName = "40/12 Ap Bac, Phuong 13";
		String cityName = "Saigon";
		
		System.out.println(addressName + cityName);
		System.out.println(addressName.concat(cityName));
		System.out.println(addressName + " - " + cityName);
		
		// Lấy text của header/ link/ label/ error/ success message
		element.getText();	//**
		
		// Kiểm tra 1 element có hiển thị hay không (hiển thị: người dùng nhìn thấy được và thao tác được)
		element.isDisplayed();	//**
		Assert.assertTrue(element.isDisplayed());
		
		// Kiểm tra 1 element có thể thao tác được hay không (không bị disable/ k phải là readonly field)
		element.isEnabled();	//**
		
		// Kiểm tra 1 element đã được chọn hay chưa (radio/ checkbox/ dropdown)
		element.isSelected();	//**
		
		
		// Submit vào 1 form
		element.submit();
	}

	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
}
