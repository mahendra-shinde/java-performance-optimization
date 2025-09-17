package com.mahendra;

import java.util.concurrent.Callable;

// Task 
public class WordCounter implements Callable<Integer>{
	private String text;
	
	public WordCounter(String text) {
		super();
		this.text = text;
	}

	@Override
	public Integer call() throws Exception {
		int count = 0;
		for(int i=0; i<text.length(); i++) {
			if(Character.isWhitespace(text.charAt(i))) {
				count++;
			}
		}
		return count;
	}
}
