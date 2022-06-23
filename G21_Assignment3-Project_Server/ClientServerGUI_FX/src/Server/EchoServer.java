package Server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import DB.mysqlConnection;
import ServerControl.QuaryManagmant;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import ocsf.server.*;

public class EchoServer extends AbstractServer
{	
	public static Connection conn;
	public static ResultSet rs;
	public EchoServer(int port) 
	{
		super(port);
	}
	
	public void handleMessageFromClient  (Object msg, ConnectionToClient client){
		  ArrayList <String> command = (ArrayList <String>)msg;
		  ArrayList<String> result = new ArrayList<String>();     //list(1)=flag,list(2)=flag,list(3)="name lastname
		  String flage=command.get(0);
		  
		  try {
				client.sendToClient(QuaryManagmant.checkUserControl(command));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	protected void serverStarted()
	{
	    System.out.println ("Server listening for connections on port " + getPort());
	}
	protected void serverStopped()
	{
		System.out.println ("Server has stopped listening for connections.");
	} 
}
