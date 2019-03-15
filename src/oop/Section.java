package oop;
import java.util.ArrayList;

public class Section {
	String[] location;
	TimePeriod period;
	Integer days;
	String lab;
	
	public Section(String[] location, TimePeriod period, Integer days, String lab) {
		this.location = location;
		this.period = period;
		this.days = days;
		
		this.lab = lab;
	}
	
	public Section(String location, Integer start, Integer end, Integer days, String lab, String course) {
		this.location = location.split(", ");
		this.period = new TimePeriod(start, end);
		this.days = days;
		this.lab = lab;
	}
	
	public WeekDay[] getDays() {
		Integer tmpDays = days;
		ArrayList<WeekDay> tmp = new ArrayList<WeekDay>();
		for(int i = 0; i < 7; i++) {
			if((tmpDays & 1) == 1) {
				WeekDay day = WeekDay.values()[i];
				tmp.add(day);
			}
			tmpDays >>= 1;
		}
		WeekDay[] res = new WeekDay[tmp.size()];
		return tmp.toArray(res);
	}
}
