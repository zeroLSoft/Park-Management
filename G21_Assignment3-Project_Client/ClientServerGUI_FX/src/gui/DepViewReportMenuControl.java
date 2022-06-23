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
 * class that show report options and give the ability to chose parameters for it
 */

public class DepViewReportMenuControl implements Initializable {
	DepVisitReportControl s;
	CanceledReportControl s1;
	ArrayList<String> result=new ArrayList<String>();
	ArrayList<ArrayList<String>> resultToSend=new ArrayList<ArrayList<String>>();
	
	ObservableList<String> parks;
	ArrayList<String> parameters3=new ArrayList<String>();
	
	@FXML
	private Button btnVisit=null;
	@FXML
	private Button btnUsage=null;
	@FXML
	private Button btnProfit=null;
	@FXML
	private ComboBox park;
	
	
	/**
	 * in this method we getting from DB all the info necessary for reports
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<String> parameters=new ArrayList<String>();
		ArrayList<String> parameters2=new ArrayList<String>();
		String query;
		String[] park1,park2,park3;
		query="SELECT * FROM park";
		parameters.add("ManagmantControl");
		parameters.add("select for report");
		parameters.add("SELECT * FROM gonature.park");
		ClientUI.chat.accept(parameters);
		result=ChatClient.result;
		park1=ChatClient.result.get(0).split("\\s+");
		park2=ChatClient.result.get(1).split("\\s+");
		park3=ChatClient.result.get(2).split("\\s+");
		parks= FXCollections.observableArrayList(park1[0],park2[0],park3[0]);
		
		park.setItems(parks);
		
	}
	
	/**
	 * method that getting the selected parameters from user, and sending user to watch visit states
	 * @param event
	 * @throws Exception
	 */
	public void getViewVisit(ActionEvent event) throws Exception {
		ArrayList<String> parameters3=new ArrayList<String>();
		String txt;
		FXMLLoader loader = new FXMLLoader();
		//((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/VisitReport.fxml").openStream());
		try{
		txt=(String) park.getSelectionModel().getSelectedItem();
		parameters3.add("dep");
		parameters3.add(txt);
		VisitReportControl VisitReportControl = loader.getController();				
		VisitReportControl.loadParkMamager(parameters3);
		
		Scene scene = new Scene(root);			
		primaryStage.setTitle("View visit report");

		primaryStage.setScene(scene);		
		primaryStage.show();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,"You have to chose park first to continue","error",JOptionPane.INFORMATION_MESSAGE);
		}
		}
	
	/**
	 * method that getting the selected parameters from user, and sending user to watch usage states
	 * @param event
	 * @throws Exception
	 */
	public void getViewUsage(ActionEvent event) throws Exception {
		ArrayList<String> parameters3=new ArrayList<String>();
		String txt;
		FXMLLoader loader = new FXMLLoader();
		//((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/UsageReport.fxml").openStream());
		try{
		txt=(String) park.getSelectionModel().getSelectedItem();
		parameters3.add("dep");
		parameters3.add(txt);
		UsageReportControl UsageReportControl = loader.getController();				
		UsageReportControl.loadParkMamager(parameters3);
		
		Scene scene = new Scene(root);			
		primaryStage.setTitle("View Usage report");

		primaryStage.setScene(scene);		
		primaryStage.show();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,"You have to chose park first to continue","error",JOptionPane.INFORMATION_MESSAGE);
		}
		}
	/**
	 * method that getting the selected parameters from user, and sending user to watch profit states
	 * @param event
	 * @throws Exception
	 */
	
	public void getViewProfit(ActionEvent event) throws Exception {
		ArrayList<String> parameters3=new ArrayList<String>();
		String txt;
		FXMLLoader loader = new FXMLLoader();
		//((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/ProfitReport.fxml").openStream());
		
		try{
		txt=(String) park.getSelectionModel().getSelectedItem();
		parameters3.add("dep");
		parameters3.add(txt);
		ProfitReportControl ProfitReportControl = loader.getController();				
		ProfitReportControl.loadParkMamager(parameters3);
		
		Scene scene = new Scene(root);			
		primaryStage.setTitle("View Usage report");

		primaryStage.setScene(scene);		
		primaryStage.show();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,"You have to chose park first to continue","error",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	/**
	 * method brings the user back to department menu
	 * @param event
	 * @throws Exception
	 */
	public void getReturnBtn(ActionEvent event) throws Exception {
		 FXMLLoader loader = new FXMLLoader();
			((Node)event.getSource()).getScene().getWindow().hide(); 
			Stage primaryStage = new Stage();
			Pane root = loader.load(getClass().getResource("/fxmlControl/DepartmentMenu.fxml").openStream());
			
			Scene scene = new Scene(root);			
			primaryStage.setTitle("Department Managment");

			primaryStage.setScene(scene);		
			primaryStage.show();
		
	}
}
