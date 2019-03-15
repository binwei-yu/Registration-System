package oop;

import java.util.HashSet;
import java.util.Random;

public class CourseIDFactory implements IIDFactory {

	public static CourseIDFactory cf = null;
	HashSet<String> set;
	
	public static CourseIDFactory getInstance() {
		if(cf == null) cf = new CourseIDFactory();
		return cf;
	}
	
	private CourseIDFactory() {
		set = new HashSet<String>();
	}

	@Override
	public String createId(String[] info) {
		if(info.length != 1) return null;
		
		// Generate sequence
		String sequence = "";
		do {
			Random rand = new Random();
			sequence = String.valueOf(Math.abs(rand.nextInt() % 1000000));
			for(int i = 0; i < 6-sequence.length(); i++)
				sequence = '0' + sequence;
		} while(set.contains(sequence));
		
		set.add(sequence);
		return info[0] + sequence;
	}

}
