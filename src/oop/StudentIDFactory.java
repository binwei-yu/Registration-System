package oop;

import java.util.HashSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class StudentIDFactory implements IIDFactory {
	private static StudentIDFactory sf = null;
	HashSet<String> set;
	
	public static StudentIDFactory getInstance() {
		if(sf == null) sf = new StudentIDFactory();
		return sf;
	}
	
	private StudentIDFactory() {
		set = new HashSet<String>();
	}

	@Override
	public String createId(String[] info) {
		// Generate date
		String pattern = "yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		
		// Generate sequence
		String sequence = "";
		do {
			Random rand = new Random();
			sequence = String.valueOf(Math.abs(rand.nextInt() % 1000000));
			for(int i = 0; i < 6-sequence.length(); i++)
				sequence = '0' + sequence;
		} while(set.contains(date + sequence));
		
		set.add(date + sequence);
		
		return date + sequence;
	}

}
