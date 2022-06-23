package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SubscriberLoginController implements Initializable {
	ArrayList<String> u;
	@FXML
	private TextField txtusername;

	@FXML
	private TextField txtpass;

	@FXML
	private TextField txtid;

	@FXML
	private Button btnsubmit;

	@FXML
	private Button btnsubmit2;

	@FXML
	private Button btnback;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	/**
	 * method that looking for subscription
	 * cheking if exist in logging and db then if exists loging to his user
	 * @param event
	 * @throws IOException
	 * @throws SQLException
	 */
	public void get_submit_btn(ActionEvent event) throws IOException, SQLException {

		ArrayList<String> parameters = new ArrayList<String>();
		ArrayList<String> s = new ArrayList<String>();
		String pass, username;
		String query;
		username = txtusername.getText();
		pass = txtpass.getText();
		if (username.trim().isEmpty() || pass.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "You must enter User name  or password", "error",
					JOptionPane.INFORMATION_MESSAGE);

		} else {
			query="SELECT * FROM loggedin WHERE ID = ? AND password = ? ";
			parameters.add("UserControl");
			parameters.add("select from table");
			parameters.add(query);
			parameters.add(username);
			parameters.add(pass);
			ClientUI.chat.accept(parameters);
			if((ChatClient.result.get(0).equals("Not found"))) {
				parameters.clear();
				s.add(username);
				s.add(pass);
				query = "SELECT * FROM subinst WHERE Username = ? and pass=?";
				parameters.add("UserControl");
				parameters.add("select from table");
				parameters.add(query);
				parameters.addAll(s);
				ClientUI.chat.accept(parameters);
				u=ChatClient.result;
				if (ChatClient.result.get(0).equals("Not found") != true) {
					FXMLLoader loader = new FXMLLoader();
					((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
					Stage primaryStage = new Stage();
					ArrayList<String> ret = new ArrayList<String>();
					ret.clear();
					ret.add("ManagmantControl");
					ret.add("set to table");
					query="INSERT INTO `gonature`.`loggedin` (`ID`,`password`) VALUES (?,?);"; //2
					ret.add(query);
					ret.add(username);
					ret.add(pass);
					ClientUI.chat.accept(ret);
					Pane root = loader.load(getClass().getResource("/fxmlControl/UserMenu.fxml").openStream());
					UserMenuController subscribermenucontroller = loader.getController();
					subscribermenucontroller.loadclient(u, false);

					Scene scene = new Scene(root);
					primaryStage.setTitle("User Menu");

					primaryStage.setScene(scene);
					primaryStage.show();
				} else {
					JOptionPane.showMessageDialog(null, "Incorrect Username or password, please try again", "error",
							JOptionPane.INFORMATION_MESSAGE);
					txtusername.clear();
					txtpass.clear();
				}
			}
			else JOptionPane.showMessageDialog(null,"User already online","error",JOptionPane.INFORMATION_MESSAGE);
		}
	}
/**
 * search with orderID
 * 
 * @param event
 * @throws IOException
 * @throws SQLException
 */

	public void get_submit_btn2(ActionEvent event) throws IOException, SQLException {
		ArrayList<String> parameters = new ArrayList<String>();
		ArrayList<String> s = new ArrayList<String>();
		String id;
		id = txtid.getText();
		if (id.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "You must enter ID", "error", JOptionPane.INFORMATION_MESSAGE);

		} else {
			s.add(id);
			s.add(id);
			String query;
			query = "SELECT * FROM subinst WHERE ID = ? or Subscriber_ID=?";
			parameters.add("UserControl");
			parameters.add("select from table");
			parameters.add(query);
			parameters.addAll(s);
			ClientUI.chat.accept(parameters);
			if (ChatClient.result.get(0).equals("Not found") != true) {
				FXMLLoader loader = new FXMLLoader();
				((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
				Stage primaryStage = new Stage();

				Pane root = loader.load(getClass().getResource("/fxmlControl/EditsubscriberMenuFastEnter.fxml").openStream());
				EditsubscriberMenuFastEnterController editsubscribermenuController = loader.getController();
				editsubscribermenuController.loadclient(ChatClient.result);

				Scene scene = new Scene(root);
				primaryStage.setTitle("Edit Subscriber Menu");

				primaryStage.setScene(scene);
				primaryStage.show();
			} else {
				JOptionPane.showMessageDialog(null, "Incorrect Input", "error", JOptionPane.INFORMATION_MESSAGE);
				txtusername.clear();
				txtpass.clear();
			}
		}
	}
	public void get_back_btn(ActionEvent event) throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();

		Pane root = loader.load(getClass().getResource("/fxmlControl/mainScreen.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("Main Screen");

		primaryStage.setScene(scene);
		primaryStage.show();
	}


}
