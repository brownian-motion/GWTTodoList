package com.brownian.todo.server;

import com.brownian.todo.client.PingService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class PingServiceImpl extends RemoteServiceServlet implements
		PingService {

	@Override
	public String helloWorld() {
		return "Hello, world!";
	}

}
