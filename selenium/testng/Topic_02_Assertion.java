package testng;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Topic_02_Assertion {
	
	@Test
	public void TC_01_Assert() {
		// Tham số nhận vào của assert true/ false là boolean
		// AssertTrue: mong đợi 1 điều kiện trả về là đúng 
		// AssertFalse: mong đợi 1 điều kiện trả về là đúng
		
		// Hàm có kiểu dữ liệu trả về là boolean 
		// Selenium: isDisplayed/ isSelected/ isEnabled/ isMultiple (Built-in)
		// Custom: hàm tự viết có kiểu trả về là boolean
		boolean status = 3 > 5;
		System.out.println(status);
		
		// Assert.assertTrue(status);
		// Assert.assertTrue(status, "3 less than 5");
		
		Assert.assertFalse(status);
		
		
		// Assert Equal: mong đợi 2 điều kiện như nhau
		// Các kiểu dữ liệu còn lại: Number/ String/ Collection (List/ Set/ ...) Array
		// Selenium: getText/ getTagname/ getAttribute/ getUrl/ getTitle/ size() (Built-in)
	}
}
