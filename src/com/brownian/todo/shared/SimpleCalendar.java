package com.brownian.todo.shared;

/**
 * Necessary because java.util.Calendar can't be used in GWT
 * @author Jack
 *
 */
public class SimpleCalendar {
	private int year, month, day;
	
	public SimpleCalendar(){
		year = month = day = 0;
	}
	
	public SimpleCalendar(int year, int month, int day){
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	public int getYear(){
		return year;
	}
	
	public int getMonth(){
		return month;
	}
	
	public int getDay(){
		return day;
	}
}
