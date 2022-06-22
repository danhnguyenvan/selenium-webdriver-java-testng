package testng;

import org.testng.annotations.Test;

public class Topic_04_Priority_Skip_Description {


	@Test(description = "JIRA#0989 - Create a new User and Verify user created success")
	public void User_01_Create_New_User() {
		System.out.println("Run Testcase 01");
	}

	@Test(enabled = false)
	public void User_02_View_User() {
		System.out.println("Run Testcase 02");
	}

	@Test()
	public void User_03_Edit_User() {
		System.out.println("Run Testcase 03");
	}

	@Test()
	public void User_04_Move_User() {
		System.out.println("Run Testcase 04");
	}

	@Test()
	public void User_05_Delete_User() {
		System.out.println("Run Testcase 05");
	}

}
