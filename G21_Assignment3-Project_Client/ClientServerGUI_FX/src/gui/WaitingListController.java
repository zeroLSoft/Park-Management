package gui;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class WaitingListController implements Initializable {
	ArrayList<String> details = new ArrayList<String>();
	ArrayList<String> parameters = new ArrayList<String>();
	ArrayList<String> UserInfo = new ArrayList<String>();
	ArrayList<String> OrderInfo = new ArrayList<String>();

	@FXML
	ObservableList<String> combotimelist = FXCollections.observableArrayList();
	@FXML
	private Button ok_btn;

	@FXML
	private Button cancel_btn;

	@FXML
	private TextField date_timetxt;

	@FXML
	private DatePicker availabledate;

	@FXML
	private ComboBox<String> combotime;

	@FXML
	private Button submit_btn;

	@FXML
	private Button select_btn;

	
	// Deleting the order completely from order database
	@FXML
	void get_cancelorer(ActionEvent event) throws IOException {
		String query = "DELETE FROM `gonature`.`order` WHERE (`orderID` = ?);";
		parameters.clear();
		parameters.add("UserControl");
		parameters.add("set to table");
		parameters.add(query);
		parameters.add(OrderInfo.get(0));
		ClientUI.chat.accept(parameters);
		JOptionPane.showMessageDialog(null, "Your order has been canceled!", "Canceled",
				JOptionPane.INFORMATION_MESSAGE);
		FXMLLoader loader = new FXMLLoader();
		Stage primaryStage = new Stage();

		Pane root = loader.load(getClass().getResource("/fxmlControl/UserMenu.fxml").openStream());
		((Node) event.getSource()).getScene().getWindow().hide();
		UserMenuController usermenucontroller = loader.getController();
		usermenucontroller.loadclient(UserInfo, false);
		Scene scene = new Scene(root);
		primaryStage.setTitle("User Menu");

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	
	//Entering the waiting list with the order time and date
	@FXML
	void get_ok_btn(ActionEvent event) throws IOException {
		String query = "SELECT * FROM `gonature`.`waitinglist` WHERE date=? AND time=?;";
		parameters.clear();
		parameters.add("UserControl");
		parameters.add("select from table");
		parameters.add(query);
		parameters.add(details.get(0));
		parameters.add(details.get(1));
		ClientUI.chat.accept(parameters);
		//This is the first order to be in the waiting list for that time and date
		//result has all the orders in the waiting list
		if (ChatClient.result.get(0).equals("Not found")) {

			query = "INSERT INTO `gonature`.`waitinglist` (`Order_id`,`Queue`, `date`, `time`,`park`,`visitors`) VALUES (?,?,?, ?, ?, ?);";
			parameters.clear();
			parameters.add("UserControl");
			parameters.add("set to table");
			parameters.add(query);
			parameters.add(details.get(2));
			parameters.add("1");
			parameters.add(details.get(0));
			parameters.add(details.get(1));
			parameters.add(details.get(3));
			parameters.add(details.get(4));
			ClientUI.chat.accept(parameters);
			JOptionPane.showMessageDialog(null, "You have entered to waiting list", "Success",
					JOptionPane.INFORMATION_MESSAGE);
			FXMLLoader loader = new FXMLLoader();
			Stage primaryStage = new Stage();

			Pane root = loader.load(getClass().getResource("/fxmlControl/UserMenu.fxml").openStream());
			((Node) event.getSource()).getScene().getWindow().hide();
			UserMenuController usermenucontroller = loader.getController();
			usermenucontroller.loadclient(UserInfo, false);
			Scene scene = new Scene(root);
			primaryStage.setTitle("User Menu");

			primaryStage.setScene(scene);
			primaryStage.show();

			//There are other orders in the waiting list for that time and date
			//s = has all the waiting list orders
		} else {
			ArrayList<String> s = ChatClient.result;
			ArrayList<Integer> queue = new ArrayList<Integer>();
			for (int i = 0; i < s.size(); i += 6) {
				queue.add(Integer.parseInt(s.get(i + 1)));
			}
			//sorting the array of orders by their queue
			queue.sort(null);

			query = "INSERT INTO `gonature`.`waitinglist` (`Order_id`,`Queue`, `date`, `time`,`park`,`visitors`) VALUES (?,?,?, ?, ?, ?);";
			parameters.clear();
			parameters.add("UserControl");
			parameters.add("set to table");
			parameters.add(query);
			parameters.add(details.get(2)); //order id
			parameters.add(String.valueOf(queue.get(queue.size() - 1) + 1)); // current queue
			parameters.add(details.get(0)); //date
			parameters.add(details.get(1)); //time
			parameters.add(details.get(3)); //park
			parameters.add(details.get(4)); //amount of visitors
			ClientUI.chat.accept(parameters);
			JOptionPane.showMessageDialog(null, "You have entered to waiting list", "Success",
					JOptionPane.INFORMATION_MESSAGE);
			FXMLLoader loader = new FXMLLoader();
			Stage primaryStage = new Stage();

			Pane root = loader.load(getClass().getResource("/fxmlControl/UserMenu.fxml").openStream());
			((Node) event.getSource()).getScene().getWindow().hide();
			UserMenuController usermenucontroller = loader.getController();
			usermenucontroller.loadclient(UserInfo, false);
			Scene scene = new Scene(root);
			primaryStage.setTitle("User Menu");

			primaryStage.setScene(scene);
			primaryStage.show();

		}

	}

	// the user chooses different time
	@FXML
	void get_select(ActionEvent event) {
		combotimelist.clear();
		if (availabledate.getValue() == null) {
			JOptionPane.showMessageDialog(null, "You must choose date first", "error", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		String time = "08:00";
		int value;
		//Filling the timebox with the times that are available
		for (int i = 0; i < 13; i++) { 
			if (CheckIfEnoughSpace(time)) {
				combotimelist.add("" + time);
			}
			value = calculateTime(time) + 60;
			time = calculateString(value);
		}

	}

	@FXML
	void get_submitbtn(ActionEvent event) throws IOException {

		if (combotime.getValue() != null) {
			String query = "UPDATE gonature.order SET status = ?,date=?,time=? WHERE orderID = ?;";
			parameters.clear();
			parameters.add("UserControl");
			parameters.add("set to table");
			parameters.add(query);
			parameters.add("pending");
			DateTimeFormatter formatter_2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String dateString = (availabledate.getValue()).format(formatter_2);

			parameters.add(dateString);
			parameters.add(combotime.getValue());
			parameters.add(OrderInfo.get(0));
			ClientUI.chat.accept(parameters);

			JOptionPane.showMessageDialog(null, "The order has been confirmed\n", "Simulation",
					JOptionPane.INFORMATION_MESSAGE);
			
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();

			Pane root = loader.load(getClass().getResource("/fxmlControl/orderConfirmed.fxml").openStream());

			OrderConfirmedController orderConfirmed = loader.getController();

			ArrayList<String> details = new ArrayList<String>();
			details.add(dateString);
			details.add(combotime.getValue());
			details.add(OrderInfo.get(0));
			orderConfirmed.loadclient(UserInfo, details, false);
			Scene scene = new Scene(root);
			primaryStage.setTitle("Order Confirmed");

			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			
			
			/*/FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();

			Pane root = loader.load(getClass().getResource("/fxmlControl/orderConfirmed.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle("Order Confirmed");

			primaryStage.setScene(scene);
			primaryStage.show();/*/
		} else {
			JOptionPane.showMessageDialog(null, "You must choose a valid time\n", "Simulation",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	public void loadclient(ArrayList<String> result, ArrayList<String> UserInfo) {
		details = result;
		date_timetxt.setText(details.get(0) + " " + details.get(1));
		this.UserInfo = UserInfo;
		String query = "SELECT * FROM `gonature`.`order` WHERE orderID=?;";
		parameters.clear();
		parameters.add("UserControl");
		parameters.add("select from table");
		parameters.add(query);
		parameters.add(details.get(2));
		ClientUI.chat.accept(parameters);
		OrderInfo = ChatClient.result;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		combotime.setItems(combotimelist);

	}

	private boolean CheckIfEnoughSpace(String time) {
		parameters.clear();
		String query = "SELECT * FROM park WHERE name = ?";
		parameters.add("UserControl");
		parameters.add("select from table");
		parameters.add(query);
		parameters.add(OrderInfo.get(1));
		ClientUI.chat.accept(parameters);
		DateTimeFormatter formatter_2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dateString = (availabledate.getValue()).format(formatter_2);
		String VisitTime;

		VisitTime = ChatClient.result.get(1);
		int Max = Integer.parseInt(ChatClient.result.get(2));
		int gap = Integer.parseInt(ChatClient.result.get(3));
		int MaxOrderAmount = Max - gap;

		int min = calculateTime(time) - calculateTime(VisitTime);
		int maximum = calculateTime(time) + calculateTime(VisitTime);

		int orderTravelers = Integer.parseInt(OrderInfo.get(4));
		parameters.clear();

		query = "SELECT * FROM gonature.order WHERE parkName = '" + OrderInfo.get(1) + "' AND date = '" + dateString
				+ "' AND time < '" + calculateString(maximum) + "' AND time > '" + calculateString(min)
				+ "'  AND (status = 'pending' OR status = 'approved' OR status = 'ready' OR status = 'almostpending')";
		parameters.add("UserControl");
		parameters.add("select from table");
		parameters.add(query);
		ClientUI.chat.accept(parameters);

		if (ChatClient.result.get(0).equals("Not found")) {
			return true;
		} else {
			ArrayList<String> s = ChatClient.result;
			int sum = 0;
			for (int i = 0; i < s.size(); i += 19) {
				sum += Integer.parseInt(s.get(i + 4));
			}
			sum += orderTravelers;
			if (MaxOrderAmount - sum >= 0)
				return true;
			else
				return false;
		}
	}

	private int calculateTime(String value) {
		String[] time = value.split(":");
		int hour = Integer.parseInt(time[0]);
		int minutes = Integer.parseInt(time[1]);
		return hour * 60 + minutes;
	}

	private String calculateString(int value) {

		int hour = value / 60;
		int minutes = value % 60;
		String time = "";
		if (hour < 10 && minutes < 10)
			time = "0" + hour + ":" + "0" + minutes;
		if (hour < 10 && minutes >= 10)
			time = "0" + hour + ":" + minutes;
		if (hour >= 10 && minutes < 10)
			time = hour + ":" + "0" + minutes;
		if (hour >= 10 && minutes >= 10)
			time = hour + ":" + minutes;
		return time;
	}
}
