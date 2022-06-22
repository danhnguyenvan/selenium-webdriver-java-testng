package basic;

import java.util.Calendar;

public class Topic_02_Random {
	public static void main(String[] args) {
		System.out.println(getRandomNumberByDateTime());
		System.out.println(getRandomNumberByDateTime());
		System.out.println(getRandomNumberByDateTime());
		System.out.println(getRandomNumberByDateTime());
		System.out.println(getRandomNumberByDateTime());
		System.out.println(getRandomNumberByDateTime());
	}
	
	public static long getRandomNumberByDateTime() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return Calendar.getInstance().getTimeInMillis() %100000;
		
	}
	
}
