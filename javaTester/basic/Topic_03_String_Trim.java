package basic;

import org.testng.Assert;

public class Topic_03_String_Trim {
	public static void main(String[] args) {
		String text = "\r\n"
				+ "      First Option\r\n"
				+ "      ";
		
		System.out.println(text);
		System.out.println(text.trim());
		Assert.assertEquals(text.trim(), "First Option");
	}
}
