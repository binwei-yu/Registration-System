package oop;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

public class RegistrationIDFactory implements IIDFactory {

	public static RegistrationIDFactory rf = null;
	HashSet<String> set;
	
	public RegistrationIDFactory getInstance() {
		if(rf == null) rf = new RegistrationIDFactory();
		return rf;
	}
	
	private RegistrationIDFactory() {
		set = new HashSet<String>();
	}

	@Override
	public String createId(String[] info) {
		// Generate date
		String pattern = "yyyyMMdd";
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
