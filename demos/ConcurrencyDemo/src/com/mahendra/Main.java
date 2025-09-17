package com.mahendra;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

	public static void main(String[] args) {
		WordCounter counter1 = new WordCounter("The quick brown fox jumps over the lazy dog. ");
		WordCounter counter2 = new WordCounter("The quick brown fox jumps over the lazy dog. ");
		WordCounter counter4 = new WordCounter("The quick brown fox jumps over the lazy dog. ");
		WordCounter counter3 = new WordCounter("The quick brown fox jumps over the lazy dog. ");
		
		// Define "Workers" (Threads) which could be used to complete the Jobs
		ExecutorService exService = Executors.newFixedThreadPool(2);
		
		Future<Integer> result1 =  exService.submit(counter1);
		Future<Integer> result2 = exService.submit(counter2);
		Future<Integer> result4 = exService.submit(counter3);
		Future<Integer> result3 = exService.submit(counter4);
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Do you want to terminate this: ");
		String choice = sc.nextLine();
		if(choice.trim().equalsIgnoreCase("yes")) {
			exService.shutdown();
		}
		
		try {
		if(exService.isShutdown()) {
			System.out.println("Result 1 : "+ result1.get());
			System.out.println("Result 2 : "+ result2.get());
			System.out.println("Result 3 : "+ result3.get());
			System.out.println("Result 4 : "+ result4.get());
		}
		}catch(ExecutionException|InterruptedException ex) {
			System.out.println(ex.getMessage());
		}
		
	}
	
	static void sleepFor(int min) {
		try {
			Thread.sleep(1000*60*min); 
		}catch(InterruptedException ex) {}
	}
}
