package com.brownian.todo.shared;
import java.io.Serializable;
import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;


public class TodoEntry implements Serializable, IsSerializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2914291880627935102L;
	public String text;
	public Date date;
	
	public TodoEntry(){
		this("",null);
	}
	
	public TodoEntry(String text){
		this(text, null);
	}
	
	public TodoEntry(String text, Date date){
		this.text = text;
		this.date = date;
	}
	
	@Override
	public String toString(){
		if(date == null)
			return '['+text+']';
		return '['+text+" : by "+date+']';
	}
}
