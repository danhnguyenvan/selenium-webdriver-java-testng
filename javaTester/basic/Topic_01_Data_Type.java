package basic;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class Topic_01_Data_Type {

	public static void main(String[] args) {
		// Kiểu dữ liệu nguyên thủy (Primitive Type)
		// Number
		// Integer: Số nguyên có dấu
		// byte/ shotr/ int/ long
		
		byte bNumber = 5;
		short sNumber = 100;
		int studentNumber = 1000;
		long timeout = 20000;
		
		// Số thực (Có dấu)
		// float/ double
		float studentPoint = 8.5f;
		double employeeSalary = 28.5d;
		
		// Char
		// char (Kí tự)
		char c = 'Y';
		char specialChar = '$';
		
		// Boolean
		// boolean
		boolean status = true;
		status = false;
		
		
		// Kiểu dữ liệu tham chiếu (Reference Type)
		// String
		String studentName = "Automation FC";
		
		String studentAddress = new String("123 Le Loi");
		
		// Array (Tập hợp kiểu dữ liệu giống nhau)
		String[] studentNames = {"Nguyen Van Nam", "Nguyen Thi Hoang Yen", "Ngo Si Lien"};
		
		
		// Class
		WebDriver driver =  new FirefoxDriver();
		
		Actions action = new Actions(driver);
		
		// Interface
		JavascriptException jsExecutor = (JavascriptException) driver;
		
		
		// Collection (Set/ Queue/ List)
		// 1 element
		WebElement emailTextbox = driver.findElement(By.id(""));
		
		// n elements 
		List<WebElement> buttons = driver.findElements(By.tagName("button"));
		
		Set<String> allWindows =  driver.getWindowHandles();
		
	}

}
