package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Logic.Casual;
import Logic.Desition;
import Logic.GroupOrder;
import Logic.SubscriptionOrder;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewDesitionsPending implements Initializable {
	ArrayList<String> s;
	private ArrayList<String> result;
	@FXML
	private TableView<Desition> DesitionsTable;
	@FXML
	private TableColumn<Desition,String> descriptionCol;
	@FXML
	private TableColumn<Desition,String> beforCol;
	@FXML
	private TableColumn<Desition,String> afterCol;
	@FXML
	private TableColumn<Desition,String> statusCol;
	@FXML
	private Button btnReturn=null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
	}

/**
 * load the desitions that was sand to departmant
 * @param s1 park manager info
 */
	public void loadDesitions(ArrayList<String> s1) {
	ArrayList<String> parameters=new ArrayList<String>();
	String query;
	this.s=s1;
	parameters.add("ManagmantControl");
	parameters.add("get desition");
	query="SELECT * FROM gonature.desition WHERE parkName ='"+s.get(5) +"' ORDER BY ABS(number)";
	parameters.add(query);
	ClientUI.chat.accept(parameters);
	result=ChatClient.result;
	getTable();
	}

	/**
	 * preper the table with desitions
	 */
	public void getTable(){
		ObservableList<Desition> DesitionList = FXCollections.observableArrayList();
		String[] Array = null;
		String str;
		int length=result.size();
		
		for(int i=0;i<length;i++) {
			Array =result.get(i).split("\\s+");      //in each i there us a sentence of info
			DesitionList.add(new Desition(Array[1],Array[2],Array[3],Array[4]));
		}
		
		
		descriptionCol.setCellValueFactory(new PropertyValueFactory<Desition,String>("description"));
		beforCol.setCellValueFactory(new PropertyValueFactory<Desition,String>("befor"));
		afterCol.setCellValueFactory(new PropertyValueFactory<Desition,String>("after"));
		statusCol.setCellValueFactory(new PropertyValueFactory<Desition,String>("status"));
		
		
		DesitionsTable.setItems(DesitionList);
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
			primaryStage.setTitle("Edit Parameters");

			primaryStage.setScene(scene);		
			primaryStage.show();
		
	}
}
