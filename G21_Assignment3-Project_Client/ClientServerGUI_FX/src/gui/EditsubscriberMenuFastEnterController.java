package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EditsubscriberMenuFastEnterController implements Initializable {
	private ArrayList<String> arr;
	@FXML
	private Button editexistorderbtn;

	@FXML
	private Button signoutbtn;

	/**
	 * enter to order that exist
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void edit_exist_order(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();

		Pane root = loader.load(getClass().getResource("/fxmlControl/editExistOrder.fxml").openStream());
		editExistOrderController  editexistordercontroller= loader.getController();
		editexistordercontroller.loadclient(arr,true, false);
		Scene scene = new Scene(root);
		primaryStage.setTitle("Edit Exist Order");

		primaryStage.setScene(scene);
		primaryStage.show();
		
		

	}
/**
 * return
 * @param event
 * @throws IOException
 */
	@FXML
	void sign_out(ActionEvent event) throws IOException {
		arr = null;
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();

		Pane root = loader.load(getClass().getResource("/fxmlControl/mainScreen.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("Main Screen");

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
/**
 * 
 * @param result client info
 */
	public void loadclient(ArrayList<String> result) {
		arr = result;

	}

}
