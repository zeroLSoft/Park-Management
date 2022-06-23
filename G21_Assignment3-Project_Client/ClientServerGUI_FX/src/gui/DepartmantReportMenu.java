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
 * class that show report options and give the ability to chose parameters for it
 *
 */
public class DepartmantReportMenu implements Initializable {
	DepVisitReportControl s;
	CanceledReportControl s1;
	ArrayList<String> result=new ArrayList<String>();
	ArrayList<ArrayList<String>> resultToSend=new ArrayList<ArrayList<String>>();
	
	ObservableList<String> month=FXCollections.observableArrayList("01","02","03","04","05","06","07","08","09","10","11","12");
	ObservableList<String> parks;
	ObservableList<String> year = FXCollections.observableArrayList();
	ArrayList<String> parameters3=new ArrayList<String>();
	
	@FXML
	private Button btnVisit=null;
	@FXML
	private Button btnCancelled=null;
	@FXML
	private ComboBox date;
	@FXML
	private ComboBox park;
	@FXML
	private ComboBox yearc;
	
	
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
		parameters.add("SELECT * FROM gonature.order ORDER BY ABS(year)");
		parameters.add("SELECT * FROM gonature.casual ORDER BY ABS(year)");
		ClientUI.chat.accept(parameters);
		result=ChatClient.result;
		park1=ChatClient.result.get(0).split("\\s+");
		park2=ChatClient.result.get(1).split("\\s+");
		park3=ChatClient.result.get(2).split("\\s+");
		parks= FXCollections.observableArrayList(park1[0],park2[0],park3[0]);
		year.add("2019");
		year.add("2020");
		year.add("2021");
	   
		date.setItems(month);
		park.setItems(parks);
		yearc.setItems(year);
		
		result=ChatClient.result;
		
	}
	
	/**
	 * method that getting the selected parameters from user, and sending user to watch visit states
	 * @param event
	 * @throws Exception
	 */
	public void getVisit(ActionEvent event) throws Exception {
		ArrayList<String> parameters3=new ArrayList<String>();
		String txt;
		FXMLLoader loader = new FXMLLoader();
		//((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/ReportD.fxml").openStream());
		try{
		txt=(String) park.getSelectionModel().getSelectedItem();
		parameters3.add(txt);
		txt=(String) date.getSelectionModel().getSelectedItem();
		parameters3.add(txt);
		txt=(String) yearc.getSelectionModel().getSelectedItem();
		parameters3.add(txt);
		resultToSend.clear();
		resultToSend.add(parameters3);
		resultToSend.add(result);
		DepVisitReportControl DepVisitReportControl = loader.getController();				
		DepVisitReportControl.loadToDvisitRep(resultToSend);
		
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Visits report");

		primaryStage.setScene(scene);		
		primaryStage.show();
		}catch(Exception e) {
			//JOptionPane.showMessageDialog(null,"You didnt chose all the fields, select all and try again","error",JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}
		}
	
	/**
	 * method that getting the selected parameters from user, and sending user to watch cancel report
	 * @param event
	 * @throws Exception
	 */
	public void getCanceled(ActionEvent event) throws Exception {
		ArrayList<String> parameters3=new ArrayList<String>();
		String txt;
		FXMLLoader loader = new FXMLLoader();
		//((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/CancelReport.fxml").openStream());
		try{
		txt=(String) park.getSelectionModel().getSelectedItem();
		parameters3.add(txt);
		txt=(String) date.getSelectionModel().getSelectedItem();
		parameters3.add(txt);
		txt=(String) yearc.getSelectionModel().getSelectedItem();
		parameters3.add(txt);
		resultToSend.clear();
		resultToSend.add(parameters3);
		resultToSend.add(result);
		CanceledReportControl CanceledReportControl = loader.getController();				
		CanceledReportControl.loadToDvisitRep(resultToSend);
		
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Cancel report");

		primaryStage.setScene(scene);		
		primaryStage.show();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,"You didnt chose all the fields, select all and try again","error",JOptionPane.INFORMATION_MESSAGE);
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
