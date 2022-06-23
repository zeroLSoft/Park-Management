package client;

import java.io.*;

import client.ChatClient;
import common.ChatIF;

public class ClientController implements ChatIF
{
	
	public static int DEFAULT_PORT ;
	ChatClient client;
	 
	public ClientController(String host, int port) 
	{
		try 
	    {
			client = new ChatClient(host, port, this);
	    } 
	    catch(IOException exception) 
	    {
	        System.out.println("Error: Can't setup connection!" + " Terminating client.");
	        System.exit(1);
	    }
	}
	public ChatClient getClient() {
		return client;
	}
	public void accept(Object str) 
	{
		client.handleMessageFromClientUI(str);
	}
	public void display(String message) 
	{
	    System.out.println("> " + message);
	}
}
