package com.brownian.todo.server;

import java.util.*;

import com.brownian.todo.client.TodoListService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.brownian.todo.shared.TodoEntry;

@SuppressWarnings("serial")
public class TodoListServiceImpl extends RemoteServiceServlet implements
		TodoListService {
	public List<TodoEntry> mySet;
	{
		mySet = new ArrayList<TodoEntry>();
		mySet.add(new TodoEntry("helo!"));
		mySet.add(new TodoEntry("wurld"));
	}

	@Override
	public List<TodoEntry> getTodoList() {
		return mySet;
	}

	@Override
	public int getNumTodoEntries() {
		return mySet.size();
	}
	
	@Override
	public void addTodoEntry(TodoEntry entry){
		mySet.add(0,entry);
	}

}
