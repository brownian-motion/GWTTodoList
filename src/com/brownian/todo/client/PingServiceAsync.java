package com.brownian.todo.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PingServiceAsync {
	void helloWorld(AsyncCallback<String> callback);
}
