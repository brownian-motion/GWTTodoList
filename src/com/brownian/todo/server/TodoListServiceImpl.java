package com.brownian.todo.server;

import java.util.*;
import java.util.concurrent.*;
import com.brownian.todo.client.TodoListService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.brownian.todo.shared.TodoEntry;

@SuppressWarnings("serial")
public class TodoListServiceImpl extends RemoteServiceServlet implements
		TodoListService {
	public ConcurrentHashMap<String,List<TodoEntry>> todoListsByIP;
	{
		todoListsByIP = new ConcurrentHashMap<String, List<TodoEntry>>();
	}

	@Override
	public List<TodoEntry> getTodoList() {
		return getList();
	}

	@Override
	public int getNumTodoEntries() {
		return getList().size();
	}
	
	@Override
	public void addTodoEntry(TodoEntry entry){
		getList().add(0,entry);
	}
	
	@Override
	public void markAsDone(int index, boolean isDone){
		getList().get(index).done = isDone;
	}
	
	@Override
	public List<TodoEntry> clearDoneEntries(){
		List<TodoEntry> list = getList();
		for(int i = 0 ; i < list.size() ; ){
			if(list.get(i).done){
				list.remove(i);
			} else {
				i++;
			}
		}
		return list;
	}
	
	public List<TodoEntry> getList(){
		String ip = getThreadLocalRequest().getRemoteAddr();
		if(todoListsByIP.containsKey(ip)){
			return todoListsByIP.get(ip);
		}else{
			List<TodoEntry> list = new ArrayList<TodoEntry>();
			todoListsByIP.put(ip, list);
			return list;
		}
	}

}
