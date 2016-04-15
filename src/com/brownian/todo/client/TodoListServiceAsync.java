package com.brownian.todo.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.*;

import com.brownian.todo.shared.TodoEntry;

public interface TodoListServiceAsync {
	void getTodoList(AsyncCallback<List<TodoEntry>> callback);
	void getNumTodoEntries(AsyncCallback<Integer> callback);
	void addTodoEntry(TodoEntry entry, AsyncCallback<Void> callback);
}
