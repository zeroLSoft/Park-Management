package gui;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import DB.mysqlConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ocsf.client.ObservableClient;
import Server.EchoServer;
import Server.ServerUI;

public class ServerPortFrameController implements Initializable {
	@FXML
	private Button btnconnect = null;
	@FXML
	private Button btndisconnect = null;
	@FXML
	private Label lbllist;
	
	@FXML
	private TextField hosttxt;
	@FXML
	private TextField iptxt;
	@FXML
	private TextField statusetxt;
	
	ObservableList<String> list;
	
/**
 * method that connect to server and DB
 * @param event
 * @throws Exception
 */
	
	public void connectToDB(ActionEvent event) throws Exception {
		
		try
		{
		    String port = "5555";
		    
		    if(port.trim().isEmpty())
			{
		    	JOptionPane.showMessageDialog(null,"you most enter IP","error",JOptionPane.INFORMATION_MESSAGE);			
			}
			else
			{
			    ServerUI.runServer(port);
				statusetxt.setText("connected"); 
			    System.out.println("server started listening");
			} 
		}
		catch(Exception e)
		{
			System.out.println("server did not connect");
		}
		
		
		try
		{
			Connection con = null;
			con =mysqlConnection.getConnection();;
			
			if(con != null)
			{
				System.out.println("server connected to mySQL");
			}
		}
		catch(Exception e)
		{
			System.err.println("server not connected to mySQL");
		}
	}
	public void diconnectfromDB(ActionEvent event) throws Exception {
		System.exit(0);
}
	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ServerPort.fxml"));
				
		Scene scene = new Scene(root);
		primaryStage.setTitle("Server Managment");
		primaryStage.setScene(scene);
		
		primaryStage.show();		
	}
	
	/**
	 * initialize interface with data
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		
		String ip=GetIp();
		String host= GetHost();
		hosttxt.setText(host);
		iptxt.setText(ip);
		statusetxt.setText("disconnected"); 
		
	}
	/**
	 * get id address
	 * @return ip adress
	 */
	private String GetHost() {
		InetAddress ip;
        String hostname;
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
           return hostname;
 
        } catch (UnknownHostException e) {
 
            e.printStackTrace();
        }
		return null;
	}
	private String GetIp() {
		   try (final DatagramSocket socket = new DatagramSocket()) {
		      socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
		      return socket.getLocalAddress().getHostAddress();
		   } catch (UnknownHostException e) {
		       // TODO Auto-generated catch block
		      e.printStackTrace();
		   } catch (SocketException e1) {
		      // TODO Auto-generated catch block
		    	  e1.printStackTrace();
	         }
		    	return null;
	 }	    

}