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


public class ReportMenuControl implements Initializable {
	private ArrayList<String> s;
	
	@FXML
	private Button btnReturn=null;
	@FXML
	private Button btnMakeVisit=null;
	@FXML
	private Button btnMakeUsage=null;
	@FXML
	private Button btnMakeProfit=null;
	
		/**
		 * 
		 * @param s1 park manger report
		 * @throws SQLException
		 */
	public void loadParkMamager(ArrayList<String> s1) throws SQLException {
		this.s=s1;
		
	}
	
	/**
	 * sending to visit report
	 * @param event
	 * @throws Exception
	 */
	public void getVisitReportBtn(ActionEvent event) throws Exception {
		ArrayList<String> parameters=new ArrayList<String>();
		String query;
		FXMLLoader loader = new FXMLLoader();
		
		
		((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/VisitReport.fxml").openStream());
		VisitReportControl VisitReportControl = loader.getController();				
		VisitReportControl.loadParkMamager(s);
		
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Visit Report Maker");

		primaryStage.setScene(scene);		
		primaryStage.show();
	}
	/**
	 * sending to usage report
	 * @param event
	 * @throws Exception
	 */
   public void getUsageReportBtn(ActionEvent event) throws Exception {
	   FXMLLoader loader = new FXMLLoader();
		((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/UsageReport.fxml").openStream());
		
		UsageReportControl UsageReportControl = loader.getController();				
		UsageReportControl.loadParkMamager(s);
		
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Usage Report Maker");

		primaryStage.setScene(scene);		
		primaryStage.show();
	}
	
   /**
    * sending to profit report
    * @param event
    * @throws Exception
    */
   public void getProfitReportBtn(ActionEvent event) throws Exception {
	   FXMLLoader loader = new FXMLLoader();
		((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/ProfitReport.fxml").openStream());
		
		ProfitReportControl ProfitReportControl = loader.getController();				
		ProfitReportControl.loadParkMamager(s);
		
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Profit Report Maker");

		primaryStage.setScene(scene);		
		primaryStage.show();
	}
   
   /**
    * return to menu
    * @param event
    * @throws Exception
    */
   public void getReturnBtn(ActionEvent event) throws Exception {
		 FXMLLoader loader = new FXMLLoader();
			((Node)event.getSource()).getScene().getWindow().hide(); 
			Stage primaryStage = new Stage();
			Pane root = loader.load(getClass().getResource("/fxmlControl/ParkManagerControl.fxml").openStream());
			ParkMamagerControl ParkMamagerControl = loader.getController();				
			ParkMamagerControl.loadParkMamager(s);
			
			
			Scene scene = new Scene(root);			
			primaryStage.setTitle("park Managmant");

			primaryStage.setScene(scene);		
			primaryStage.show();
		
	}
   
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		
	}
	
	
}
