package gui;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GuestLoginController {
	ArrayList<String> parameters = new ArrayList<String>();
	ArrayList<String> guestinfo = new ArrayList<String>();

	@FXML
	private Button backbtn;

	@FXML
	private TextField phonetxt;

	@FXML
	private Button ok;

	/**
	 * return to menu
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void getBackBtn(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();

		Pane root = loader.load(getClass().getResource("/fxmlControl/mainScreen.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("Main Screen");

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	/**
	 * select id see if new user, if new insert to db
	 * else use existing
	 * 
	 * @param event
	 * @throws IOException
	 */

	@FXML
	void get_okbtn(ActionEvent event) throws IOException {

		if (phonetxt.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "You must enter phone", "error", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (!phonetxt.getText().matches("[0-9]+")) {
			JOptionPane.showMessageDialog(null, "You must enter phone", "error", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String query = "SELECT * FROM subinst WHERE ID = ?";
		parameters.add("UserControl");
		parameters.add("select from table");
		parameters.add(query);
		parameters.add(phonetxt.getText());
		ClientUI.chat.accept(parameters);
		guestinfo = ChatClient.result;
		if (ChatClient.result.get(0).equals("Not found")) {
			parameters.clear();
			query = "INSERT INTO `gonature`.`subinst` (`firstname`,`ID`,`Username`,`Is instructor`,`Subscriber_ID`) VALUES (?,?,?,?,?);";
			parameters.add("UserControl");
			parameters.add("set to table");
			parameters.add(query);
			parameters.add("irrelavnt");
			parameters.add(phonetxt.getText());
			parameters.add(phonetxt.getText());
			parameters.add("Guest");
			parameters.add(phonetxt.getText());
			ClientUI.chat.accept(parameters);
			
			parameters.clear();
			query = "SELECT * FROM subinst WHERE ID = ?";
			parameters.add("UserControl");
			parameters.add("select from table");
			parameters.add(query);
			parameters.add(phonetxt.getText());
			ClientUI.chat.accept(parameters);
			guestinfo = ChatClient.result;
		}

		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window

		Stage primaryStage = new Stage();
		Parent root = loader.load(getClass().getResource("/fxmlControl/UserMenu.fxml").openStream());
		UserMenuController usermenu = loader.getController();

		usermenu.loadclient(guestinfo, true);

		Scene scene = new Scene(root);
		primaryStage.setTitle("User Menu");
		primaryStage.setScene(scene);

		primaryStage.show();

	}

}
