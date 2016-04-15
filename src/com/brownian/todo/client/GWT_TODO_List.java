package com.brownian.todo.client;

import java.util.*;

import com.brownian.todo.shared.*;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWT_TODO_List implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	private final PingServiceAsync pingService = GWT.create(PingService.class);
	private final TodoListServiceAsync todoService = GWT.create(TodoListService.class);

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		try{
		final Button sendButton = new Button("Send to Server");
		final TextBox nameField = new TextBox();
		nameField.setText("GWT User");
		final Label errorLabel = new Label();

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");
		
		final CellTable<TodoEntry> table = new CellTable<TodoEntry>();
		TextColumn<TodoEntry> textColumn = new TextColumn<TodoEntry>(){
			@Override
			public String getValue(TodoEntry entry){
				return entry.text;
			}
		};
		TextColumn<TodoEntry> dateColumn = new TextColumn<TodoEntry>(){
			@Override
			public String getValue(TodoEntry entry){
				return (entry.date == null)?("<no date set>"):(entry.date.toString());
			}
		};
		
		table.addColumn(textColumn, "Todo");
		table.addColumn(dateColumn,"Due Date");
		
		final ListDataProvider<TodoEntry> dataProvider = new ListDataProvider<TodoEntry>();
		todoService.getTodoList(new AsyncCallback<List<TodoEntry>>(){
			@Override
			public void onFailure(Throwable caught){
				errorLabel.setText(caught.toString());
			}
			
			@Override
			public void onSuccess(List<TodoEntry> entries){
				List<TodoEntry> providerList = dataProvider.getList();
				providerList.clear();
				providerList.addAll(entries);
			}
		});
		
		dataProvider.addDataDisplay(table);
		RootPanel.get("todoListContainer").add(table);
		

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendTodoToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendTodoToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
			private void sendTodoToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = nameField.getText();
				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				sendButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
//				greetingService.greetServer(textToServer,
//				pingService.helloWorld(
//						new AsyncCallback<String>() {
				final TodoEntry entry = new TodoEntry(textToServer);
				todoService.addTodoEntry(
						entry,
						new AsyncCallback<Void>(){
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox
										.setText("Remote Procedure Call - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(caught.toString());
								dialogBox.center();
								closeButton.setFocus(true);
							}

//							public void onSuccess(String result) {
							public void onSuccess(Void result){
								dataProvider.getList().add(0,entry); //put it at the top
							}
						});
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
		} catch(Exception e){
			e.printStackTrace(System.err);
		}
	}
	
	void updateTodoList(List<TodoEntry> todoList){
		Label todoLabel = new Label(todoList.toString());
		RootPanel.get("todoListContainer").add(todoLabel);
	}
}
