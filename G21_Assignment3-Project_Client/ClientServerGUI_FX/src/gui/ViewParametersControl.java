package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewParametersControl implements Initializable {
	ArrayList<String> s;
	String[] discount =new String[10];
	String[] parkParameters =new String[10];
	@FXML
	private TextField txtStayTime;
	@FXML
	private TextField txtVisitOrderGap;
	@FXML
	private TextField txtMaxVisitsPerDay;
	@FXML
	private TextField txtSingleFamilyWithOrder;
	@FXML
	private TextField txtSubscription;
	@FXML
	private TextField txtGroupWithOrder;
	@FXML
	private TextField txtAdvancePay;
	@FXML
	private TextField txtGroupNoOrder;
	@FXML
	private TextField CurrentVisitors;
	@FXML
	private TextField fullPrice;
	@FXML
	private TextField open;
	@FXML
	private TextField close;
	@FXML
	private Button btnReturn=null;
/**
 * park parameters view
 * get parameters for park and set to table
 * @param s1 park info
 */
	public void loadInfoToView(ArrayList<String> s1) {
		ArrayList<String> parameters=new ArrayList<String>();
		String query;
		FXMLLoader loader = new FXMLLoader();
		this.s=s1;
		parameters.add("ParkControl");
		parameters.add("get edit parameters");
		query="SELECT * FROM discount WHERE ParkName = ?";
		parameters.add(query);
		parameters.add(s.get(5));
		query="SELECT * FROM park WHERE name = ?";
		parameters.add(query);
		parameters.add(s.get(5));
		ClientUI.chat.accept(parameters);
		
		this.discount= ChatClient.result.get(0).split("\\s+");
		this.parkParameters = ChatClient.result.get(1).split("\\s+");
		
		this.txtStayTime.setText(parkParameters[1]);
		this.txtMaxVisitsPerDay.setText(parkParameters[2]);
		this.txtVisitOrderGap.setText(parkParameters[3]);
		this.CurrentVisitors.setText(parkParameters[4]);
		
		this.txtSingleFamilyWithOrder.setText(discount[1]);
		this.txtSubscription.setText(discount[2]);
		this.txtGroupWithOrder.setText(discount[3]);
		this.txtAdvancePay.setText(discount[4]);
		this.txtGroupNoOrder.setText(discount[5]);
		
		this.open.setText("08:00");
		this.close.setText("20:00");
		this.fullPrice.setText("15 Shekel");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}
	/**
	 * return to menue or close window depending if coming from park mamager or departmant and park worker
	 * @param event
	 * @throws Exception
	 */
	public void getReturnBtn(ActionEvent event) throws Exception {
		if(!(s.get(6).equals("not park"))) {
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
			else
				((Node)event.getSource()).getScene().getWindow().hide(); 
	}

	public void getCloselBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); 
}
}
