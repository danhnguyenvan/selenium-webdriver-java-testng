package webdriver;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_11_Actions {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	Actions action;
	String osName = System.getProperty("os.name");
	Keys key;
	JavascriptExecutor jsExecutor;
	String jsHelperPath = projectPath + "\\dragAndDrop\\drag_and_drop_helper.js";

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();

		jsExecutor = (JavascriptExecutor) driver;
		action = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		if (osName.contains("Windows")) {
			key = Keys.CONTROL;
		} else {
			key = Keys.COMMAND;
		}
	}

	@Test
	public void TC_01_Hover_Textbox() {
		driver.get("https://automationfc.github.io/jquery-tooltip/");
		
		WebElement ageTextbox = driver.findElement(By.cssSelector("input#age"));
		action.moveToElement(ageTextbox).perform();
		sleepInSecond(2);
		
		Assert.assertEquals(driver.findElement(By.className("ui-tooltip-content")).getText(), "We ask for your age only for statistical purposes.");
	}
	
	@Test
	public void TC_01_Hover_Menu_Myntra() {
		driver.get("http://www.myntra.com/");
		
		WebElement kidLink = driver.findElement(By.xpath("//header[@id='desktop-header-cnt']//a[text()='Kids']"));
		action.moveToElement(kidLink).perform();
		sleepInSecond(2);
		
		action.click(driver.findElement(By.xpath("//header[@id='desktop-header-cnt']//a[text()='Home & Bath']"))).perform();
		sleepInSecond(2);
		
		Assert.assertTrue(driver.findElement(By.xpath("//h1[@class='title-title']")).isDisplayed());	
	}

	@Test
	public void TC_01_Hover_Menu_Fahasa() {
		driver.get("https://www.fahasa.com/");

		// Close Iframe
		driver.switchTo().frame(driver.findElement(By.id("preview-notification-frame")));
		driver.findElement(By.id("NC_IMAGE1")).click();


		// Switch về Parent page
		driver.switchTo().defaultContent();
		
		// Hover chuột vào Menu
		WebElement fahasaLink = driver.findElement(By.xpath("//span[@class='icon_menu']"));
		action.moveToElement(fahasaLink).perform();
		sleepInSecond(2);
		
		// Hover chuột vào FOREIGN BOOKS
		action.moveToElement(driver.findElement(By.xpath("//a[@title='FOREIGN BOOKS']"))).perform();
		sleepInSecond(2);
		
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='fhs_stretch_stretch']/div[@class='fhs_column_stretch']//a[text()='Popular Psychology']")).isDisplayed());
		
	}
	
	@Test
	public void TC_02_Hover_Menu_FPT_Shop() {
		driver.get("https://fptshop.com.vn/");
		
		action.moveToElement(driver.findElement(By.xpath("//a[@title='ĐIỆN THOẠI']"))).perform();
		sleepInSecond(2);
		
		action.click(driver.findElement(By.xpath("//a[@title='Apple (iPhone)']"))).perform();
		sleepInSecond(2);
		
		Assert.assertEquals(driver.getCurrentUrl(), "https://fptshop.com.vn/dien-thoai/apple-iphone");
	}

	@Test
	public void TC_03_Hover_World_Health() {
		driver.get("https://covid19.who.int/region/wpro/country/vn");
		sleepInSecond(3);
		
		WebElement mapVietnam = driver.findElement(By.xpath("//div[@class='leaflet-pane leaflet-map-pane']//div[text()='Viet Nam']"));
		action.moveToElement(mapVietnam).perform();
		sleepInSecond(2);
		
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='leaflet-pane leaflet-tooltip-pane']//div[text()='Viet Nam']")).isDisplayed());
	}
	
	@Test
	public void TC_04_Click_And_Hold_Block() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		
		List<WebElement> allNumbers = driver.findElements(By.cssSelector("ol#selectable>li"));
		
		// Click and hold (1 - 4)
		action.clickAndHold(allNumbers.get(0)) // Click vào số 1 và giữ chuột
			.moveToElement(allNumbers.get(3)) // Di chuyển đến số 4
			.release() // Nhả chuột trái ra
			.perform(); // Thực hiện các hành động
		
		List<WebElement> numberSelected = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
		Assert.assertEquals(numberSelected.size(), 4);
	}
	
	@Test
	public void TC_05_Click_And_Hold_Random() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		
		List<WebElement> allNumbers = driver.findElements(By.cssSelector("ol#selectable>li"));
		
		// Nhấn phím Ctrl xuống
		action.keyDown(key).perform();
		
		// Chọn random các số 
		action.click(allNumbers.get(0)).click(allNumbers.get(2)) //
				.click(allNumbers.get(4)).click(allNumbers.get(6)) //
				.click(allNumbers.get(8)).perform();
		
		// Nhả phím Ctrl 
		action.keyDown(key).perform();
		
		Assert.assertEquals(driver.findElements(By.cssSelector("ol#selectable>li.ui-selected")).size(), 5);
	}
	
	@Test
	public void TC_06_Double_Click() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		// Scroll to element
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//button[text()='Double click me']")));
		
		action.doubleClick(driver.findElement(By.xpath("//button[text()='Double click me']"))).perform();
		sleepInSecond(2);
		
		Assert.assertEquals(driver.findElement(By.cssSelector("p#demo")).getText(), "Hello Automation Guys!");
	}
	
	@Test
	public void TC_07_Right_Click() {
		driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");
		
		action.contextClick(driver.findElement(By.cssSelector("span.context-menu-one"))).perform(); 
		sleepInSecond(2);
		
		action.moveToElement(driver.findElement(By.cssSelector("li.context-menu-icon-quit"))).perform();
		sleepInSecond(2);
		
		Assert.assertTrue(driver.findElement(By.cssSelector("li.context-menu-icon-quit.context-menu-visible.context-menu-hover")).isDisplayed());
		
		action.click(driver.findElement(By.cssSelector("li.context-menu-icon-quit"))).perform();
		sleepInSecond(2);
		
		driver.switchTo().alert().accept();
		sleepInSecond(2);
		
		Assert.assertFalse(driver.findElement(By.cssSelector("li.context-menu-icon-quit")).isDisplayed());
	}
	
	@Test
	public void TC_08_Drag_Drop_Element_HTML4() {
		driver.get("https://automationfc.github.io/kendo-drag-drop/");
		
		WebElement smallCircle = driver.findElement(By.id("draggable"));
		WebElement bigCircle = driver.findElement(By.id("droptarget"));
		
		Assert.assertEquals(bigCircle.getText(), "Drag the small circle here.");
		
		action.dragAndDrop(smallCircle, bigCircle).perform();
		sleepInSecond(2);
		
		Assert.assertEquals(bigCircle.getText(), "You did great!");
	}

	@Test
	public void TC_09_Drag_Drop_Element_HTML5_Css() throws IOException {
		driver.get("https://automationfc.github.io/drag-drop-html5/");
		
		String sourceCss = "#column-a";
		String targetCss = "#column-b";
		
		String jsHelperFileContent = getContentFile(jsHelperPath);
		System.out.println("jsHelperFileContent: " + jsHelperFileContent);
		
		// A to B
		jsHelperFileContent = jsHelperFileContent + "$(\"" + sourceCss + "\").simulateDragDrop({ dropTarget: \"" + targetCss + "\"});";
		jsExecutor.executeScript(jsHelperFileContent);
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-a']/header[text()='B']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-b']/header[text()='A']")).isDisplayed());

		// B to A
		jsExecutor.executeScript(jsHelperFileContent);
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-a']/header[text()='A']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-b']/header[text()='B']")).isDisplayed());
		
	}
	
	@Test
	public void TC_09_Drag_Drop_Element_HTML5_Xpath() throws IOException, AWTException {
		driver.get("https://automationfc.github.io/drag-drop-html5/");
		
		dragAndDropHTML5("//div[@id='column-a']", "//div[@id='column-b']");
		sleepInSecond(3);
		
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-a']/header[text()='B']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-b']/header[text()='A']")).isDisplayed());
		
		dragAndDropHTML5("//div[@id='column-a']", "//div[@id='column-b']");
		sleepInSecond(3);
		
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-a']/header[text()='A']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-b']/header[text()='B']")).isDisplayed());
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
	
	public String getContentFile(String filePath) throws IOException {
		Charset cs = Charset.forName("UTF-8");
		FileInputStream stream = new FileInputStream(filePath);
		try {
			Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
			StringBuilder builder = new StringBuilder();
			char[] buffer = new char[8192];
			int read;
			while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
				builder.append(buffer, 0, read);
			}
			return builder.toString();
		} finally {
			stream.close();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void dragAndDropHTML5(String sourceLocator, String targetLocator) throws AWTException {

		WebElement source = driver.findElement(By.xpath(sourceLocator));
		WebElement target = driver.findElement(By.xpath(targetLocator));

		// Setup robot
		Robot robot = new Robot();
		robot.setAutoDelay(500);

		// Get size of elements
		Dimension sourceSize = source.getSize();
		Dimension targetSize = target.getSize();

		// Get center distance
		int xCentreSource = sourceSize.width / 2;
		int yCentreSource = sourceSize.height / 2;
		int xCentreTarget = targetSize.width / 2;
		int yCentreTarget = targetSize.height / 2;

		Point sourceLocation = source.getLocation();
		Point targetLocation = target.getLocation();
		System.out.println(sourceLocation.toString());
		System.out.println(targetLocation.toString());

		// Make Mouse coordinate center of element
		sourceLocation.x += 20 + xCentreSource;
		sourceLocation.y += 110 + yCentreSource;
		targetLocation.x += 20 + xCentreTarget;
		targetLocation.y += 110 + yCentreTarget;

		System.out.println(sourceLocation.toString());
		System.out.println(targetLocation.toString());

		// Move mouse to drag from location
		robot.mouseMove(sourceLocation.x, sourceLocation.y);

		// Click and drag
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(((sourceLocation.x - targetLocation.x) / 2) + targetLocation.x, ((sourceLocation.y - targetLocation.y) / 2) + targetLocation.y);

		// Move to final position
		robot.mouseMove(targetLocation.x, targetLocation.y);

		// Drop
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

}
