package com.brownian.todo.client;

import com.google.gwt.user.client.rpc.*;
import java.util.*;
import com.brownian.todo.shared.TodoEntry;


@RemoteServiceRelativePath("todo")
public interface TodoListService extends RemoteService {
	List<TodoEntry> getTodoList();
	int getNumTodoEntries();
	void addTodoEntry(TodoEntry entry);
}
