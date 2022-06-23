package gui;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * park manger menu
 * @author oleg
 *
 */
public class ParkMamagerControl implements Initializable {
	
	private ArrayList<String> s;
	
	@FXML
	private Button btnExit=null;
	@FXML
	private Button btnEditParkParameters=null;
	@FXML
	private Button btnViewParkStats=null;
	@FXML
	private Button btnViewPendingDesitions=null;
	@FXML
	private Button btnReport=null;
	
		
	public void loadParkMamager(ArrayList<String> s1) throws SQLException {
		this.s=s1;
		
	}
	
	/**
	 * open edit parameters control
	 * @param event
	 * @throws Exception
	 */
	public void getEditParkParametersBtn(ActionEvent event) throws Exception {
		ArrayList<String> parameters=new ArrayList<String>();
		String query;
		FXMLLoader loader = new FXMLLoader();
		
		
		((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/EditParameters.fxml").openStream());
		EditParametersControl EditParametersControl = loader.getController();				
		EditParametersControl.loadEditFram(s);
		
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Edit Parameters");

		primaryStage.setScene(scene);		
		primaryStage.show();
	}
	
	/**
	 * open open make reports control
	 * @param event
	 * @throws Exception
	 */
   public void getReportBtn(ActionEvent event) throws Exception {
	   FXMLLoader loader = new FXMLLoader();
		((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/ParkReportMenu.fxml").openStream());
		
		ReportMenuControl ReportMenuControl = loader.getController();				
		ReportMenuControl.loadParkMamager(s);
		
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Report Managmant");

		primaryStage.setScene(scene);		
		primaryStage.show();
	}
   /**
    * open  view parameters control
    * @param event
    * @throws Exception
    */
   public void getViewParameterstBtn(ActionEvent event) throws Exception {
	   FXMLLoader loader = new FXMLLoader();
		((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/ViewParameters.fxml").openStream());
		
		ViewParametersControl ViewParametersControl = loader.getController();				
		ViewParametersControl.loadInfoToView(s);
		
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Edit Parameters");

		primaryStage.setScene(scene);		
		primaryStage.show();
	}

   /**
    * open pending desitions
	//
    * @param event
    * @throws Exception
    */
   public void getViewDesitionsBtn(ActionEvent event) throws Exception {
	   FXMLLoader loader = new FXMLLoader();
		((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/viewDesitionsPark.fxml").openStream());
		
		ViewDesitionsPending ViewDesitionsPending = loader.getController();				
		ViewDesitionsPending.loadDesitions(s);
		
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Edit Parameters");

		primaryStage.setScene(scene);		
		primaryStage.show();
	}
   
   /**
    * log out
    * @param event
    * @throws Exception
    */
   public void getLogOutBtn(ActionEvent event) throws Exception {
	   FXMLLoader loader = new FXMLLoader();
		((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/employeelogin.fxml").openStream());
		
		ArrayList<String> parameters =new 	ArrayList<String>();
		String query="DELETE FROM gonature.loggedin WHERE ID = ? AND password = ?";
		parameters.add("UserControl");
		parameters.add("set to table");
		parameters.add(query);
		parameters.add(s.get(0));
		parameters.add(s.get(6));
		ClientUI.chat.accept(parameters);

		
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Edit Parameters");

		primaryStage.setScene(scene);		
		primaryStage.show();
	}
   
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		
	}

	public void load(ArrayList<String> res) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
