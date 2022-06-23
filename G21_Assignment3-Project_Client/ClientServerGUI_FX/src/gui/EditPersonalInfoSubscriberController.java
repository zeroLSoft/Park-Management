package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EditPersonalInfoSubscriberController implements Initializable {
	private ArrayList<String> arr;

	@FXML
	private TextField txtfirstname;

	@FXML
	private TextField txtemail;

	@FXML
	private TextField txtlastname;

	@FXML
	private TextField txtid;

	@FXML
	private TextField txtphone1;

	@FXML
	private PasswordField txtpass;

	@FXML
	private PasswordField txtconfirmpass;

	@FXML
	private TextField txtphone2;
	@FXML
	private Button updatebtn;

	@FXML
	private Button backbtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	/**
	 * return to user
	 * @param event
	 * @throws IOException
	 */
	
	@FXML
	void get_back_btn(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();

		Pane root = loader.load(getClass().getResource("/fxmlControl/UserMenu.fxml").openStream());
		UserMenuController subscribermenucontroller = loader.getController();
		subscribermenucontroller.loadclient(arr, false);
		Scene scene = new Scene(root);
		primaryStage.setTitle("Subscriber Menu");

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	/**
	 * get the selected info and check id legal changes then update to db
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void get_update_btn(ActionEvent event) throws IOException {
		if (txtfirstname.getText().trim().isEmpty() || txtlastname.getText().trim().isEmpty()
				|| txtphone1.getText().trim().isEmpty() || txtphone2.getText().trim().isEmpty()
				|| txtemail.getText().trim().isEmpty() || txtpass.getText().trim().isEmpty()
				|| txtconfirmpass.getText().trim().isEmpty())
			JOptionPane.showMessageDialog(null, "You must enter all details", "error", JOptionPane.INFORMATION_MESSAGE);
		else if (!txtpass.getText().equals(txtconfirmpass.getText()))
			JOptionPane.showMessageDialog(null, "The passwords do not match ", "error",
					JOptionPane.INFORMATION_MESSAGE);
		else if (txtphone1.getText().length() != 3 || txtphone2.getText().length() != 7)
			JOptionPane.showMessageDialog(null, "Put Legal Phone Format XXX-XXXXXXX ", "error",
					JOptionPane.INFORMATION_MESSAGE);
		else if (!txtemail.getText().matches("^[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}"))
			JOptionPane.showMessageDialog(null, "Not a valid Email, try again", "error",
					JOptionPane.INFORMATION_MESSAGE);
		else if (!txtphone1.getText().matches("[0-9]+")||!txtphone2.getText().matches("[0-9]+")) {
			JOptionPane.showMessageDialog(null, "You must enter phone right", "error", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		else {
			ArrayList<String> parameters = new ArrayList<String>();
			ArrayList<String> s = new ArrayList<String>();
			String lastname, firstname, phone, email, pass, id;
			firstname = txtfirstname.getText();
			lastname = txtlastname.getText();
			phone = txtphone1.getText();
			phone += txtphone2.getText();
			email = txtemail.getText();
			pass = txtpass.getText();
			id = txtid.getText();
			s.add(firstname);
			s.add(lastname);
			s.add(pass);
			s.add(phone);
			s.add(email);
			s.add(id);
			String query;
			query = "UPDATE gonature.subinst SET firstname = ? ,lastname=?,pass=?,phone=?,email=? WHERE ID = ?;";
			parameters.add("UserControl");
			parameters.add("set to table");
			parameters.add(query);
			parameters.addAll(s);
			ClientUI.chat.accept(parameters);
			JOptionPane.showMessageDialog(null, "Details are changed", "Update Successful",
					JOptionPane.INFORMATION_MESSAGE);
			loadclient1(s);
		}
	}

	/**
	 * starting user info on interface
	 * @param result user information
	 */
	public void loadclient(ArrayList<String> result) {
		arr = result;
		txtfirstname.setText(arr.get(0));
		txtlastname.setText(arr.get(1));
		txtid.setText(arr.get(2));
		txtpass.setText(arr.get(3));
		txtconfirmpass.setText(arr.get(3));
		txtphone1.setText(arr.get(4).substring(0, 3));
		txtphone2.setText(arr.get(4).substring(3));
		txtemail.setText(arr.get(5));
	}

	public void loadclient1(ArrayList<String> result) {

		txtfirstname.setText(result.get(0));
		txtlastname.setText(result.get(1));
		txtid.setText(result.get(5));
		txtpass.setText(result.get(2));
		txtconfirmpass.setText(result.get(2));
		txtphone1.setText(result.get(3).substring(0, 3));
		txtphone2.setText(result.get(3).substring(3));
		txtemail.setText(result.get(4));
		ArrayList<String> newResult = new ArrayList<String>();
		newResult.add(result.get(0));
		newResult.add(result.get(1));
		newResult.add(result.get(5));
		newResult.add(result.get(2));
		newResult.add(result.get(3));
		newResult.add(result.get(4));
		newResult.add(arr.get(6));
		newResult.add(arr.get(7));
		newResult.add(arr.get(8));
		newResult.add(arr.get(9));
		arr = newResult;
	}

}
