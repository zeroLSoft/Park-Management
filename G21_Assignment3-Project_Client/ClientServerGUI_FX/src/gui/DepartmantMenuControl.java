package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import client.ChatClient;
import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * 
 * class that presenting options for department user
 *
 */
public class DepartmantMenuControl implements Initializable {
	ObservableList<String> parks;
	ArrayList<String> result;
	@FXML
	private Button btnReport=null;
	@FXML
	private Button btnDesitiond=null;
	@FXML
	private Button btnViewReports=null;
	@FXML
	private Button btnlogOut=null;
	@FXML
	private Button btnViewStats=null;
	@FXML
	private ComboBox park;
	String[] park1,park2,park3;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<String> parameters=new ArrayList<String>();
		String query="SELECT * FROM park";
		parameters.add("ManagmantControl");
		parameters.add("select for report");
		parameters.add("SELECT * FROM gonature.park");
		parameters.add("SELECT * FROM gonature.order ORDER BY ABS(year)");
		parameters.add("SELECT * FROM gonature.casual ORDER BY ABS(year)");
		ClientUI.chat.accept(parameters);
		result=ChatClient.result;
		park1=ChatClient.result.get(0).split("\\s+");
		park2=ChatClient.result.get(1).split("\\s+");
		park3=ChatClient.result.get(2).split("\\s+");
		parks= FXCollections.observableArrayList(park1[0],park2[0],park3[0]);
		park.setItems(parks);
	}
	


	/**
	 * method that sanding user to report making control
	 * @param event
	 * @throws Exception
	 */
	public void getReportBtn(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/DepartmantReport.fxml").openStream());

		Scene scene = new Scene(root);			
		primaryStage.setTitle("Department report making");

		primaryStage.setScene(scene);		
		primaryStage.show();
	}

/**
 * method that sanding user to decision control
 * @param event
 * @throws Exception
 */
	public void getDesitionBtn(ActionEvent event) throws Exception {

		FXMLLoader loader = new FXMLLoader();
		((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/DepartmantDesitionView.fxml").openStream());


		Scene scene = new Scene(root);			
		primaryStage.setTitle("Decision Managment");

		primaryStage.setScene(scene);		
		primaryStage.show();
	}

/**
 * method that sanding user to view park reports control
 * @param event
 * @throws Exception
 */
	public void getViewReportsBtn(ActionEvent event) throws Exception {

		FXMLLoader loader = new FXMLLoader();
		((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/DepViewParkReport.fxml").openStream());


		Scene scene = new Scene(root);			
		primaryStage.setTitle("Park reports managmant");

		primaryStage.setScene(scene);		
		primaryStage.show();
	}
	
	
	public void getViewStatesBtn(ActionEvent event) throws Exception {
		ArrayList<String> temp=new ArrayList<String>();
		try{
		FXMLLoader loader = new FXMLLoader();
		//((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/ViewParameters.fxml").openStream());
		String txt=(String) park.getSelectionModel().getSelectedItem();
		temp.add(txt);temp.add(txt);temp.add(txt);temp.add(txt);temp.add(txt);temp.add(txt);temp.add("not park");
		
		ViewParametersControl ViewParametersControl = loader.getController();
		ViewParametersControl.loadInfoToView(temp);
		
		Scene scene = new Scene(root);			
		primaryStage.setTitle("view Parameters report making");

		primaryStage.setScene(scene);		
		primaryStage.show();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,"You didnt chose park , select all and try again","error",JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}
	}
	/**
	 * method that log out and deleting user from logged in
	 * @param event
	 * @throws Exception
	 */
	public void getLogOutBtn(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/employeelogin.fxml").openStream());
		ArrayList<String> parameters =new 	ArrayList<String>();
		parameters.add("UserControl");
		parameters.add("select from table");
		parameters.add("SELECT * FROM gonature.employee WHERE role = 'DM'");
		ClientUI.chat.accept(parameters);
		parameters.clear();
		ArrayList<String> parameters2 =new 	ArrayList<String>();
		String query="DELETE FROM gonature.loggedin WHERE ID = ? AND password = ?";
		parameters.add("UserControl");
		parameters.add("set to table");
		parameters.add(query);
		parameters.add(ChatClient.result.get(0));
		parameters.add(ChatClient.result.get(6));
		ClientUI.chat.accept(parameters);

		Scene scene = new Scene(root);			
		primaryStage.setTitle("Employee Login");

		primaryStage.setScene(scene);		
		primaryStage.show();
	}
}
