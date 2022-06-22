package testng;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import listener.ExtentReport;

@Listeners(ExtentReport.class)
public class Topic_08_Dependence {

	@Test
	public void TC_01_Create_Account() {
		System.out.println("TC_01_Create_Account");

		Assert.assertTrue(true);
	}

	@Test(dependsOnMethods = "TC_01_Create_Account")
	public void TC_02_Edit_Account() {
		System.out.println("TC_02_Edit_Account");
	}

	@Test(dependsOnMethods = "TC_02_Edit_Account")
	public void TC_03_View_Account() {
		System.out.println("TC_03_View_Account");

		Assert.assertTrue(false);
	}

	@Test(dependsOnMethods = "TC_03_View_Account")
	public void TC_04_Delete_Account() {
		System.out.println("TC_04_Delete_Account");
	}

}
