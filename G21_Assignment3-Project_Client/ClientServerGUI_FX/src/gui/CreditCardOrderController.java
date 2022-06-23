package gui;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 * 
 * class that hendeling credit card input
 *
 */
public class CreditCardOrderController {
	ArrayList<String> details = new ArrayList<String>();
	ArrayList<String> userinfo = new ArrayList<String>();
	@FXML
	private TextField card1txt;

	@FXML
	private TextField card4txt;

	@FXML
	private TextField card3txt;

	@FXML
	private TextField card2txt;

	@FXML
	private TextField expiretxt1;

	@FXML
	private TextField expiretxt2;

	@FXML
	private TextField cvvtxt;

	@FXML
	private Button submit;

	@FXML
	private Button paywithcashbtn;
	private boolean isEmployee;

	/**
	 * option to chose cash over card and go to confirmed
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void get_paywithcash(ActionEvent event) throws IOException {

		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();

		Pane root = loader.load(getClass().getResource("/fxmlControl/orderConfirmed.fxml").openStream());

		OrderConfirmedController orderConfirmed = loader.getController();

		orderConfirmed.loadclient(userinfo, details,isEmployee);

		Scene scene = new Scene(root);
		primaryStage.setTitle("Order Confirmed");

		primaryStage.setScene(scene);
		primaryStage.show();

	}
/**
 * check if credit info is lagit and send to confirmed
 * @param event
 * @throws IOException
 */
	@FXML
	void getsubmit(ActionEvent event) throws IOException {
		if (card1txt.getText().isEmpty() || card2txt.getText().isEmpty() || card3txt.getText().isEmpty()
				|| card4txt.getText().isEmpty() || expiretxt1.getText().isEmpty() || expiretxt2.getText().isEmpty()
				|| cvvtxt.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "You need to fill all the details ", "error",
					JOptionPane.INFORMATION_MESSAGE);

		} else if (card1txt.getText().length() != 4 || card2txt.getText().length() != 4
				|| card3txt.getText().length() != 4 || card4txt.getText().length() != 4
				|| expiretxt1.getText().length() != 2 || expiretxt2.getText().length() != 2
				|| cvvtxt.getText().length() != 3) {
			JOptionPane.showMessageDialog(null, "You need to fill the details in the correct format", "error",
					JOptionPane.INFORMATION_MESSAGE);
		} else {

			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();

			Pane root = loader.load(getClass().getResource("/fxmlControl/orderConfirmed.fxml").openStream());

			OrderConfirmedController orderConfirmed = loader.getController();

			orderConfirmed.loadclient(userinfo, details,isEmployee);

			Scene scene = new Scene(root);
			primaryStage.setTitle("Order Confirmed");

			primaryStage.setScene(scene);
			primaryStage.show();
		}
	}
/**
 * 
 * @param userinfo user 
 * @param details     user info
 * @param isEmployee   look if imployee, flag
 */
	public void loadclient(ArrayList<String> userinfo, ArrayList<String> details,boolean isEmployee) {
		this.isEmployee = isEmployee;
		this.details = details;
		this.userinfo = userinfo;
	}

}
