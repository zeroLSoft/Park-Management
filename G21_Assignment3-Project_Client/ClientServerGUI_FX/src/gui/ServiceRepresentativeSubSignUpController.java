package gui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import client.ChatClient;
import client.ClientUI;
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

public class ServiceRepresentativeSubSignUpController implements Initializable {
	private ArrayList<String> srInfo;
	@FXML
	private Button btnContinue = null;

	@FXML
	private Button btnCancel = null; 

	@FXML
	private TextField Fnametxt;

	@FXML
	private TextField Lnametxt;

	@FXML
	private TextField IDtxt;

	@FXML
	private TextField phonetxt;

	@FXML
	private TextField Emailtxt;

	@FXML
	private TextField familynumtxt;

	public void loadclient(ArrayList<String> s1) throws SQLException {

	}

	/**
	 * sign up subscription
	 * get text, check if input is ok and update the DB
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void getSignUpBtn(ActionEvent event) throws Exception {
		ArrayList<String> parameters = new ArrayList<String>();
		ArrayList<String> subInfo = new ArrayList<String>();
		String query1, email, id, phone, fname, lname, fnum, Sub_ID;
		boolean flag = true;
		email = Emailtxt.getText();
		phone = phonetxt.getText();
		id = IDtxt.getText();
		fnum = familynumtxt.getText();
		fname = Fnametxt.getText();
		lname = Lnametxt.getText();
		Random r = new Random();
		int low = 10000;
		int high = 99999;
		int result = r.nextInt(high - low) + low;
		Sub_ID = String.valueOf(result);

		if (fname.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "You must enter the first name", "error",
					JOptionPane.INFORMATION_MESSAGE);
			flag = false;
			return;
		} else
			subInfo.add(fname);

		if (lname.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "You must enter the last name", "error",
					JOptionPane.INFORMATION_MESSAGE);
			flag = false;
			return;
		} else
			subInfo.add(lname);

		if (id.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "You must enter the ID", "error", JOptionPane.INFORMATION_MESSAGE);
			flag = false;
			return;}

		 else {
			subInfo.add(id);
		}

		if (phone.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "You must enter a phone number", "error",
					JOptionPane.INFORMATION_MESSAGE);
			flag = false;
			return;}
		else if(!phone.matches("[0-9]+")) {
			JOptionPane.showMessageDialog(null,"The phone number must only contain digits","error",JOptionPane.INFORMATION_MESSAGE);
			flag = false;
			return;}
		else if(phone.length()!=10) {
			JOptionPane.showMessageDialog(null,"The phone number Must be 10 digits","error",JOptionPane.INFORMATION_MESSAGE);
			flag = false;
			return;}
		else {
			subInfo.add(phone);
			subInfo.add(phone);
		} // Setting password to be phone

		if (email.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "You must enter an Email", "error", JOptionPane.INFORMATION_MESSAGE);
			flag = false;
		} else {
			subInfo.add(email);
			subInfo.add(id); // Setting Username to be the ID
			subInfo.add("No"); // Not instructor	
		}

		if (fnum.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "You must enter the number of family members", "error",
					JOptionPane.INFORMATION_MESSAGE);
			flag = false;
			return;}
		else if(!fnum.matches("[0-9]+")) {
			JOptionPane.showMessageDialog(null,"The family number must must only contain digits","error",JOptionPane.INFORMATION_MESSAGE);
			flag = false;
			return;}
		else if(fnum.equals("0")) {
			JOptionPane.showMessageDialog(null,"The family number has to be more than 1","error",JOptionPane.INFORMATION_MESSAGE);
			flag = false;
			return;}
		 else {
			subInfo.add(fnum);
		    subInfo.add(Sub_ID);
		    }
		//Checking that the ID does not already exists
		if (flag) {
			query1 = "SELECT * FROM subinst WHERE ID = ?";
			parameters.add("UserControl");
			parameters.add("select from table");
			parameters.add(query1);
			parameters.add(id);
			ClientUI.chat.accept(parameters);
			if(ChatClient.result.get(0).equals("Not found")!=true) {
				JOptionPane.showMessageDialog(null, "This ID already exists!", "error",
						JOptionPane.INFORMATION_MESSAGE);
				flag=false;
			}
			parameters.clear();
		}

		if (flag) {

			if (email.matches("^[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}")) {
				//Creating a new subscriber in database
				query1 = "INSERT INTO subinst " + "VALUES (?,?,?,?,?,?,?,?,?,?);";
				parameters.add("UserControl");
				parameters.add("set to table");
				parameters.add(query1);
				parameters.addAll(subInfo);
				ClientUI.chat.accept(parameters);

				FXMLLoader loader = new FXMLLoader();

				((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
				Stage primaryStage = new Stage();
				Pane root = loader.load(getClass().getResource("/fxmlControl/creditCardPayment.fxml").openStream());
				creditCardPaymentController c = loader.getController();
				c.loadID(subInfo,srInfo);

				Scene scene = new Scene(root);
				primaryStage.setTitle("Email Managment Tool");

				primaryStage.setScene(scene);
				primaryStage.show();
			} else {
				JOptionPane.showMessageDialog(null, "Not a valid Email, try again", "error",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	public void getCancelBtn(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();

		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/ServiceRepresentative.fxml").openStream());
		ServiceRepresentativeController ServiceRepresentativeController = loader.getController();				
		ServiceRepresentativeController.load(srInfo);
		Scene scene = new Scene(root);
		primaryStage.setTitle("Client Managment Tool");

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void load(ArrayList<String> s) {
		this.srInfo=s;
		
	}

}
