package client;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;


import common.ChatIF;
import ocsf.client.AbstractClient;

public class ChatClient extends AbstractClient
{
	ChatIF clientUI;

	public static ResultSet rs;
	public static boolean awaitResponse = false;
	public static ArrayList<String> result =new ArrayList<String>();
	
	public ChatClient(String host, int port, ChatIF clientUI) throws IOException 
	{
		super(host, port);
		this.clientUI = clientUI;
	}
	
	
	public void handleMessageFromServer(Object msg) {
		awaitResponse = false;
		 ArrayList <String> messege = (ArrayList <String>)msg;
		 result=messege;
	}

	public void handleMessageFromClientUI(Object message)  
	{
		try
	    {
			openConnection();//in order to send more than one message
	       	awaitResponse = true;
	    	sendToServer(message);
			// wait for response
			while (awaitResponse) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	    }
	    catch(IOException e)
	    {
	    	e.printStackTrace();
	        clientUI.display("Could not send message to server: Terminating client." + e);
	        quit();
	    }
	}
	
	public void quit()
	{
		try
	    {
	      closeConnection();
	    }
	    catch(IOException e)
		{
	    	;
		}
		
	    System.exit(0);
	}
}
