package gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainScreenController implements Initializable {
	@FXML
	private Button subscriberbtn;
	@FXML
	private ImageView iv;
	@FXML
	private Button employeebtn;

	@FXML
	private Button guestbtn;

	@FXML
	private Button exitbtn;

	/**
	 * get to employee log in
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void get_employee(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();

		Pane root = loader.load(getClass().getResource("/fxmlControl/employeelogin.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("Employee Login");

		primaryStage.setScene(scene);
		primaryStage.show();

	}
/**
 * get to subscriber log in
 * @param event
 * @throws IOException
 */
	@FXML
	void get_subscriber(ActionEvent event) throws IOException {
		

		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();

		Pane root = loader.load(getClass().getResource("/fxmlControl/subscriberLogIn.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("Subscriber LogIn");

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * get to guest log in
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void get_guest(ActionEvent event) throws IOException {

		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();

		Pane root = loader.load(getClass().getResource("/fxmlControl/GuestLogin.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("Guest Login");

		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		
		

	}

	@FXML
	void get_exit(ActionEvent event) {
		System.exit(0);
	}
	
	/**
	 * get to reader simulator
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void get_Reader(ActionEvent event) throws IOException {
	FXMLLoader loader = new FXMLLoader();
	((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
	Stage primaryStage = new Stage();

	Pane root = loader.load(getClass().getResource("/fxmlControl/CardReader.fxml").openStream());
	Scene scene = new Scene(root);
	primaryStage.setTitle("Card Reader");

	primaryStage.setScene(scene);
	primaryStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/fxmlControl/mainScreen.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Main Screen");
		primaryStage.setScene(scene);

		primaryStage.show();
	}

}
