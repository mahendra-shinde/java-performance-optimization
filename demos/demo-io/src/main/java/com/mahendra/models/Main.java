package com.mahendra.models;

import java.util.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
	// Translating the classpath (relative to source) to absolute path
	static final URL DATAFILE = Main.class.getResource("/products.csv");
	static final URL DATA2 = Main.class.getResource("/products-large.csv");
	public static void main(String[] args) {
		System.out.println("Perform Basic/Legacy IO Operation on smaller file");
		testBufferedReader(DATAFILE);

		System.out.println("Non block IO Operations on smaller file");
		testNIO(DATAFILE);

		System.out.println("With Large file");
		System.out.println("Perform Basic/Legacy IO Operation");
		testBufferedReader(DATA2);

		System.out.println("Non block IO Operations");
		testNIO(DATA2);
		
	}

	private static void testBufferedReader(URL filePath) {
		System.out.println("Loading with BufferedReader:");
		long startMem = getUsedMemory();
		long startTime = System.nanoTime();
		List<Product> products = loadWithBufferedReader(filePath.getPath());
		long endTime = System.nanoTime();
		long endMem = getUsedMemory();
		// products.forEach(System.out::println);
		System.out.printf("Time taken (BufferedReader): %.2f ms\n", (endTime - startTime) / 1_000_000.0);
		System.out.printf("Memory used (BufferedReader): %.2f KB\n", (endMem - startMem) / (1024.0));
	}

	private static long getUsedMemory() {
		Runtime runtime = Runtime.getRuntime();
		runtime.gc(); // Request for JVM to launch GC ( JVM Might ignore it !!)
		return (runtime.totalMemory() - runtime.freeMemory());
	}

	public static List<Product> loadWithBufferedReader(String filePath) {
		List<Product> products = new ArrayList<>();
		/// try-with-resources : Java-7
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line = br.readLine(); // skip header
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",", 5);
				if (parts.length == 5) {
					products.add(new Product(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4]));
				}
			}
		} catch (IOException ex) {
			System.out.println("Error :" + ex.getMessage());
		}
		return products;
	}

	public static List<Product> loadWithNIO(URI filePath) throws IOException {
		List<Product> products = new ArrayList<>();
		List<String> lines = Files.readAllLines(Path.of(filePath));
		for (int i = 1; i < lines.size(); i++) { // skip header
			String[] parts = lines.get(i).split(",", 5);
			if (parts.length == 5) {
				products.add(new Product(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4]));
			}
		}
		return products;
	}

	private static void testNIO(URL filePath) {
		System.out.println("\nLoading with NIO:");
		long startMem = getUsedMemory();
		long startTime = System.nanoTime();
		
		try {
			URI path = filePath.toURI();
			List<Product> products = loadWithNIO(path);
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.nanoTime();
		long endMem = getUsedMemory();
		// products.forEach(System.out::println);
		System.out.printf("Time taken (NIO): %.2f ms\n", (endTime - startTime) / 1_000_000.0);
		System.out.printf("Memory used (NIO): %.2f KB\n", (endMem - startMem) / (1024.0));
	}
}
