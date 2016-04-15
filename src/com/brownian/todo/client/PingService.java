package com.brownian.todo.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ping")
public interface PingService extends RemoteService{
	String helloWorld();
}