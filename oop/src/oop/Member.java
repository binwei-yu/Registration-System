package oop;

import java.util.Random;
import java.lang.Math;

public class Member {
	Integer id;
	String name;
	String division;
	protected String password;
	
	public Member(String name, String division, String password) {
		// Generate ID
		Random rand = new Random();
		this.id = Math.abs(rand.nextInt());
		
		// Add other information
		this.name = name;
		this.division = division;
		this.password = password;
	}
	
	// Change password
	public void changePassword(String password) {
		
	}
}
