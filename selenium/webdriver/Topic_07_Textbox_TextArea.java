package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_07_Textbox_TextArea {
	WebDriver driver;
	String loginPageUrl, userID, password, name, gender, dateOfBirthInput, dateOfBirthOutput, addressInput, addressOutput, city, state, pin, phone, email;
	String customerID, editAddressInput, editAddressOutput, editCity, editState, editPin, editPhone, editEmail;
	String projectPath = System.getProperty("user.dir");
	JavascriptExecutor jsExecutor;

	By nameTextboxBy = By.name("name");
	By genderRadioBy = By.xpath("//input[@value='f']");
	By genderTextboxBy = By.name("gender");
	By dateOfBirthTextboxBy = By.name("dob");
	By addressTextAreaBy = By.name("addr");
	By cityTextboxBy = By.name("city");
	By stateTextboxBy = By.name("state");
	By pinTextboxBy = By.name("pinno");
	By phoneTextboxBy = By.name("telephoneno");
	By emailTextboxBy = By.name("emailid");
	By passwordTextboxBy = By.name("password");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		jsExecutor = (JavascriptExecutor) driver;

		name = "Angela Jolie";
		gender = "female";
		dateOfBirthInput = "01/01/1990";
		dateOfBirthOutput = "1990-01-01";
		addressInput = "234 PO Bridge\nNew York";
		addressOutput = "234 PO Bridge New York";
		city = "Los Angeles";
		state = "California";
		pin = "225588";
		phone = "0989678123";
		email = "angela" + getRandomNumber() + "@mail.net";
		
		editAddressInput = "245 PO Bridge\nChicago";
		editAddressOutput = "245 PO Bridge Chicago"; 
		editCity = "Atlanta"; 
		editState = "Texas"; 
		editPin = "229900";
		editPhone = "0989123654"; 
		editEmail = "angela" + getRandomNumber() + "@hotmail.com";

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Register() {
		driver.get("https://demo.guru99.com/v4/");
		
		loginPageUrl = driver.getCurrentUrl();
		driver.findElement(By.xpath("//a[text()='here']")).click();

		driver.findElement(By.name("emailid")).sendKeys(email);
		driver.findElement(By.name("btnLogin")).click();

		userID = driver.findElement(By.xpath("//td[text()='User ID :']/following-sibling::td")).getText();
		password = driver.findElement(By.xpath("//td[text()='Password :']/following-sibling::td")).getText();
	}

	@Test
	public void TC_02_Login() {
		driver.get(loginPageUrl);

		driver.findElement(By.name("uid")).sendKeys(userID);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("btnLogin")).click();

		Assert.assertTrue(driver.findElement(By.xpath("//marquee[@class='heading3' and text()=\"Welcome To Manager's Page of Guru99 Bank\"]")).isDisplayed());
	}

	@Test
	public void TC_03_New_Customer() {
		driver.findElement(By.xpath("//a[text()='New Customer']")).click();

		driver.findElement(nameTextboxBy).sendKeys(name);
		driver.findElement(genderRadioBy).click();
		
		jsExecutor.executeScript("arguments[0].removeAttribute('type')", driver.findElement(dateOfBirthTextboxBy));
		sleepInSecond(5);
		driver.findElement(dateOfBirthTextboxBy).sendKeys(dateOfBirthInput);
		driver.findElement(addressTextAreaBy).sendKeys(addressInput);
		driver.findElement(cityTextboxBy).sendKeys(city);
		driver.findElement(stateTextboxBy).sendKeys(state);
		driver.findElement(pinTextboxBy).sendKeys(pin);
		driver.findElement(phoneTextboxBy).sendKeys(phone);
		driver.findElement(emailTextboxBy).sendKeys(email);
		driver.findElement(passwordTextboxBy).sendKeys(password);
		driver.findElement(By.name("sub")).click();
		
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='heading3' and text()='Customer Registered Successfully!!!']")).isDisplayed());
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), name);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText(), gender);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(), dateOfBirthOutput);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(), addressOutput);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), city);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(), state);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), pin);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(), phone);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), email);
		
		customerID = driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();
	}

	@Test
	public void TC_04_Edit_Customer() {
		driver.findElement(By.xpath("//a[text()='Edit Customer']")).click();
		driver.findElement(By.xpath("//input[@name='cusid']")).sendKeys(customerID);
		driver.findElement(By.name("AccSubmit")).click();
		
		Assert.assertEquals(driver.findElement(nameTextboxBy).getAttribute("value"), name);
		Assert.assertEquals(driver.findElement(genderTextboxBy).getAttribute("value"), gender);
		Assert.assertEquals(driver.findElement(dateOfBirthTextboxBy).getAttribute("value"), dateOfBirthOutput);
		Assert.assertEquals(driver.findElement(addressTextAreaBy).getAttribute("value"), addressInput);
		Assert.assertEquals(driver.findElement(cityTextboxBy).getAttribute("value"), city);
		Assert.assertEquals(driver.findElement(stateTextboxBy).getAttribute("value"), state);
		Assert.assertEquals(driver.findElement(pinTextboxBy).getAttribute("value"), pin);
		Assert.assertEquals(driver.findElement(phoneTextboxBy).getAttribute("value"), phone);
		Assert.assertEquals(driver.findElement(emailTextboxBy).getAttribute("value"), email);
		
		driver.findElement(addressTextAreaBy).clear();
		driver.findElement(addressTextAreaBy).sendKeys(editAddressInput);
		driver.findElement(cityTextboxBy).clear();
		driver.findElement(cityTextboxBy).sendKeys(editCity);
		driver.findElement(stateTextboxBy).clear();
		driver.findElement(stateTextboxBy).sendKeys(editState);
		driver.findElement(pinTextboxBy).clear();
		driver.findElement(pinTextboxBy).sendKeys(editPin);
		driver.findElement(phoneTextboxBy).clear();
		driver.findElement(phoneTextboxBy).sendKeys(editPhone);
		driver.findElement(emailTextboxBy).clear();
		driver.findElement(emailTextboxBy).sendKeys(editEmail);
//		driver.findElement(By.name("sub")).click();
		
//		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='heading3' and text()='Customer details updated Successfully!!!']")).isDisplayed());
//		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText(), customerID);
//		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), name);
//		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText(), gender);
//		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(), dateOfBirthOutput);
//		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(), editAddressOutput);
//		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), editCity);
//		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(), editState);
//		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), editPin);
//		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(), editPhone);
//		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), editEmail);
		
	}
	
//	@Test
	public void TC_05_Login_Page_OrangeHRM() {
		// Access vào trang Orange HRM
		driver.get("https://opensource-demo.orangehrmlive.com/");
		
		// Login bằng account userName vs password
		driver.findElement(By.cssSelector("input#txtUsername")).sendKeys("Admin");
		driver.findElement(By.cssSelector("input#txtPassword")).sendKeys("admin123");
		driver.findElement(By.cssSelector("input#btnLogin")).click();
		
	}
	
//	@Test
	public void TC_06_Add_Employee_Page_OrangeHRM() {
		By firstNameBy, lastNameBy, employeeIDBy, buttonSaveBy; 
		
		firstNameBy = By.xpath("//input[@id='personal_txtEmpFirstName']");
		lastNameBy =  By.xpath("//input[@id='personal_txtEmpLastName']");
		employeeIDBy = By.xpath("//input[@id='personal_txtEmployeeId']");
		buttonSaveBy = By.xpath("//input[@id='btnSave']");
		
		// Mở trang Add Employee
		driver.get("https://opensource-demo.orangehrmlive.com/index.php/pim/addEmployee");
		
		// Nhập thông tin vào các textbox firstName và lastName
		driver.findElement(By.xpath("//input[@id='firstName']")).sendKeys("Luis");
		driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys("Suares");
		
		// Lấy Id data từ EmployeeID textbox 
		String employeeId = driver.findElement(By.xpath("//input[@id='employeeId']")).getAttribute("value");
		
		driver.findElement(buttonSaveBy).click();
		
		// Verify data đã nhập ở màn hình Add Employee với data hiển thị ở trang Personal Detail
		Assert.assertEquals(driver.findElement(firstNameBy).getAttribute("value"), "Luis");
		Assert.assertEquals(driver.findElement(lastNameBy).getAttribute("value"), "Suares");
		Assert.assertEquals(driver.findElement(employeeIDBy).getAttribute("value"), employeeId);
		
		Assert.assertFalse(driver.findElement(firstNameBy).isEnabled());
		Assert.assertFalse(driver.findElement(lastNameBy).isEnabled());
		Assert.assertFalse(driver.findElement(employeeIDBy).isEnabled());
		
		// Click vào Edit button để edit new data 
		driver.findElement(buttonSaveBy).click();
		
		driver.findElement(firstNameBy).clear();
		driver.findElement(firstNameBy).sendKeys("Ronaldo");
		driver.findElement(lastNameBy).clear();
		driver.findElement(lastNameBy).sendKeys("Messi");
		
		driver.findElement(buttonSaveBy).click();
		
		// Verify data tại 2 firstName và lastName được được update giá trị mới thành công
		Assert.assertEquals(driver.findElement(firstNameBy).getAttribute("value"), "Ronaldo");
		Assert.assertEquals(driver.findElement(lastNameBy).getAttribute("value"), "Messi");
		
		Assert.assertFalse(driver.findElement(firstNameBy).isEnabled());
		Assert.assertFalse(driver.findElement(lastNameBy).isEnabled());
		Assert.assertFalse(driver.findElement(employeeIDBy).isEnabled());
	}
	
	public int getRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(99999);
	}
	
	public void sleepInSecond(long timeoutInSecond) {
		try {
			Thread.sleep(timeoutInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public void afterClass() {
//		driver.quit();
	}
}
