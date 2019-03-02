package oop;

public class Lab extends Course {
	public Lab(String name) {
		super(name);
	}
	
	// One course section only has one lab section
	@Override
	public void addSection(Section section) {
		if(this.sectionSize() == 0) {
			super.addSection(section);
		}
	}
}
