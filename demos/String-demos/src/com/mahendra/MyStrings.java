package com.mahendra;

public class MyStrings {

	public static void main(String[] args) {
		String name = new String("mahendra");
		String name2 = "mahendra"; // String pool 
		String name3 = "mahendra"; // Re-use OBject
		String name4 = name3.toUpperCase();
		
		waitAMin(1);
		System.out.println("Are they same? "+ (name == name2));
		System.out.println("Are they same? "+ (name2 == name3));
		waitAMin(2);
		System.out.println("Are they same? "+ (name3 == name4));
		waitAMin(2);
		System.out.println("Hello World".toLowerCase());
		
	}

	static void waitAMin(int min) {
		try {
			Thread.sleep(min*1000*60);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
