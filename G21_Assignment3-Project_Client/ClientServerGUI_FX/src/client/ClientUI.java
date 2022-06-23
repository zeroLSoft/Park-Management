package client;

import javafx.application.Application;
import javafx.stage.Stage;

public class ClientUI extends Application
{
	public static ClientController chat;
	
	public static void main( String args[] ) throws Exception
	{
		launch(args);  
	} 
	
	public void start(Stage primaryStage) throws Exception
	{
		chat = new ClientController("localhost", 5555);
		
		gui.MainScreenController aFrame = new gui.MainScreenController();
		
		aFrame.start(primaryStage);
	}
}
