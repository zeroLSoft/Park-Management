package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.ChatIF;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class ServiceRepresentativeController implements Initializable { 
	private ArrayList<String> srInfo;
	
	@FXML
	private Button btnAddSub = null;
	
	@FXML
	private Button btnAddInst = null;
	
	@FXML
	private Button btnBack = null;
	
	/**
	 * get to add subscription
	 * @param event
	 * @throws Exception
	 */
	public void getAddSubBtn(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/ServiceRepresentativeSubSignUp.fxml").openStream());			
		ServiceRepresentativeSubSignUpController ServiceRepresentativeSubSignUpController = loader.getController();				
		ServiceRepresentativeSubSignUpController.load(srInfo);
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Subscriber Sign Up");

		primaryStage.setScene(scene);		
		primaryStage.show();
	}
	/**
	 * get to add instructor
	 * @param event
	 * @throws Exception
	 */
	public void getAddInstBtn(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/SRinstructorSignUp.fxml").openStream());			
		SRinstructorSignUpController SRinstructorSignUpController = loader.getController();				
		SRinstructorSignUpController.load(srInfo);
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Instructor Sign Up");

		primaryStage.setScene(scene);		
		primaryStage.show();
	}
		/**
		 * log out
		 * @param event
		 * @throws Exception
		 */
	public void getBackBtn(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/employeelogin.fxml").openStream());
		//deleting the employee from the logged in table in the database	
		ArrayList<String> parameters =new 	ArrayList<String>();
		String query="DELETE FROM gonature.loggedin WHERE ID = ? AND password = ?";
		parameters.add("UserControl");
		parameters.add("set to table");
		parameters.add(query);
		parameters.add(srInfo.get(0));
		parameters.add(srInfo.get(6));
		ClientUI.chat.accept(parameters);
		
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Employee Login");

		primaryStage.setScene(scene);		
		primaryStage.show();
	}

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

	public void load(ArrayList<String> res) {
		this.srInfo=res;
		
	}
}
