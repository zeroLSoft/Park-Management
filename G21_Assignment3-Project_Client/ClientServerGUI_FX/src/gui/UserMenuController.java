package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import client.ChatClient;
import client.ClientUI;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UserMenuController implements Initializable {
	private ArrayList<String> arr;
	@FXML
	private Button createorderbtn;

	@FXML
	private Button editexistorderbtn;

	@FXML
	private Button editpersonalinfobtn;

	@FXML
	private Button signoutbtn;
	private boolean Isguest;
/**
 * get to creat order
 * @param event
 * @throws IOException
 */
	@FXML
	void get_create_order(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();

		Pane root = loader.load(getClass().getResource("/fxmlControl/Order.fxml").openStream());
		OrderController orderOfsubscribercontroller = loader.getController();
		orderOfsubscribercontroller.loadclient(arr, false);
		Scene scene = new Scene(root);
		primaryStage.setTitle("Order");

		primaryStage.setScene(scene);
		primaryStage.show();

	}
/**
 * get to edit order
 * @param event
 * @throws IOException
 */
	@FXML
	void get_edit_exist_order(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();

		Pane root = loader.load(getClass().getResource("/fxmlControl/editExistOrder.fxml").openStream());
		editExistOrderController editexistordercontroller = loader.getController();
		editexistordercontroller.loadclient(arr, false, Isguest);
		Scene scene = new Scene(root);
		primaryStage.setTitle("Edit Exist Order");

		primaryStage.setScene(scene);
		primaryStage.show();
	}
/**
 * edit personal info
 * @param event
 * @throws IOException
 */
	@FXML
	void get_edit_personal_info(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();

		Pane root = loader.load(getClass().getResource("/fxmlControl/EditPersonalInfoSubscriber.fxml").openStream());
		EditPersonalInfoSubscriberController editpersonalinfosubscribercontroller = loader.getController();
		editpersonalinfosubscribercontroller.loadclient(arr);
		Scene scene = new Scene(root);
		primaryStage.setTitle("Edit Personal Info Subscriber");

		primaryStage.setScene(scene);
		primaryStage.show();
	}
/**
 * sign out to menu
 * @param event
 * @throws IOException
 */
	@FXML
	void get_sign_out(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		ArrayList<String> parameters =new 	ArrayList<String>();
		String query="DELETE FROM gonature.loggedin WHERE ID = ? AND password = ?";
		parameters.add("UserControl");
		parameters.add("set to table");
		parameters.add(query);
		parameters.add(arr.get(2));
		parameters.add(arr.get(3));
		ClientUI.chat.accept(parameters);
		Pane root = loader.load(getClass().getResource("/fxmlControl/mainScreen.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("Main Screen");

		primaryStage.setScene(scene);
		primaryStage.show();

	}
/**
 * loog for order and get info
 * @param result info of user
 * @param Isguest  flag if guest
 */
	public void loadclient(ArrayList<String> result, boolean Isguest) {
		this.Isguest=Isguest;
		if(Isguest) {
			editpersonalinfobtn.setVisible(false);
			
		}
		arr = result;
		ArrayList<String> parameters = new ArrayList<>();
		String query = "SELECT * FROM `gonature`.`order` WHERE (status='ready' OR status='almostpending') AND ID=?";
		parameters.add("UserControl");
		parameters.add("select from table");
		parameters.add(query);
		parameters.add(arr.get(2));
		ClientUI.chat.accept(parameters);
		ArrayList<String> order = ChatClient.result;
		if (order.get(0).equals("Not found") != true) {
			if(order.get(6).equals("ready"))
			JOptionPane.showMessageDialog(null, "You have a new order to approve, please go to 'Check Existing Orders'",
					"message", JOptionPane.INFORMATION_MESSAGE);
			if(order.get(6).equals("almostpending"))
				JOptionPane.showMessageDialog(null, "Your queue in waiting list has come.\nIn order to approve it please go to 'Check Existing Orders'",
						"message", JOptionPane.INFORMATION_MESSAGE);
					
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
