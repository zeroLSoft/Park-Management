package gui;

import client.ChatClient;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class OrderController implements Initializable {
	enum Level {
		Instructor, Subscriber, Guest
	}

	int casual = 0;
	private Level user;
	private boolean IsEmployee;
	private boolean calculate_flag = false;
	private ArrayList<String> userinfo;
	ArrayList<String> parameters = new ArrayList<String>();
	ArrayList<String> s = new ArrayList<String>();
	LocalDate date;
	String query;
	String visitorsin;
	@FXML
	ObservableList<String> parklist = FXCollections.observableArrayList("haifa", "karmiel", "tel-aviv");
	@FXML
	ObservableList<String> timelist = FXCollections.observableArrayList("08:00", "09:00", "10:00", "11:00", "12:00",
			"13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00");
	@FXML
	ObservableList<String> no_of_travelers_list = FXCollections.observableArrayList();

	@FXML
	private Label lblpark;
	@FXML
	private Label lbldate;
	@FXML
	private Label lbltime;
	@FXML
	private Label lbltype_of_visit;
	@FXML
	private Label lblno_of_travelers;
	@FXML
	private Label lblemail;

	@FXML
	private ComboBox<String> parkbox;

	@FXML
	private DatePicker datepicker;

	@FXML
	private ComboBox<String> no_of_travelers;

	@FXML
	private ComboBox<String> combotime;
	@FXML
	private TextField txttotal;
	@FXML
	private TextField txtemail;
	@FXML
	private TextField txtuser;
	@FXML
	private TextField orderNumber;
	@FXML
	private ComboBox<String> type_of_visit;

	@FXML
	private Button btncalculate = new Button();
	@FXML
	private Button btnsubmit;

	@FXML
	private Button cancel;
	@FXML
	private Button parkpricebtn;

	@FXML
	private CheckBox cash;

	@FXML
	private CheckBox credit;

	private String user_number;
	private String id_passport;
	private String numOfTravelers;
	private double cost = 0;

	public void initialize(URL location, ResourceBundle resources) {
		parkbox.setItems(parklist);
		combotime.setItems(timelist);
		combotime.setValue("choose time");
		parkbox.setValue("choose park");
		no_of_travelers.setValue("1");
		datepicker.setValue(java.time.LocalDate.now());
		Random r = new Random();
		int low = 10000;
		int high = 99999;
		int res = r.nextInt(high - low) + low;
		orderNumber.setText(String.valueOf(res));

	}

	// Calculates the cost of the order, depending on time and user level
	public void calculate_cost(ActionEvent event) {
		s.clear();
		parameters.clear();
		if (parkbox.getValue().equals("choose park") || combotime.getValue().equals("choose time")) {
			JOptionPane.showMessageDialog(null, "You must enter all details", "error", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (java.time.LocalDate.now().isAfter(datepicker.getValue())) {
			JOptionPane.showMessageDialog(null, "The chosen date is already passed, Please choose a different date",
					"error", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (java.time.LocalTime.now().isAfter(LocalTime.parse(combotime.getValue()))
				&& java.time.LocalDate.now().isEqual(datepicker.getValue()) && !IsEmployee) {
			JOptionPane.showMessageDialog(null, "The Chosen time is already passed, please choose a different time",
					"error", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		date = datepicker.getValue();
		numOfTravelers = no_of_travelers.getValue();
		s.add(parkbox.getValue());

		// Getting discount from database
		query = "SELECT * FROM discount WHERE parkName=?";
		Talk_WithDB(parameters, s, query);

		cost = Prebooked(numOfTravelers);
		s.clear();
		parameters.clear();
		s.add(orderNumber.getText());
		query = "SELECT * FROM `gonature`.`order` WHERE orderID=?";
		Talk_WithDB(parameters, s, query);
		// Checks if the same order has been opened
		while (!ChatClient.result.get(0).equals("Not found")) {
			s.clear();
			parameters.clear();
			Random r = new Random();
			int low = 10000;
			int high = 99999;
			int res = r.nextInt(high - low) + low;
			orderNumber.setText(String.valueOf(res));
			s.add(orderNumber.getText());
			query = "SELECT * FROM `gonature`.`order` WHERE orderID=?";
			Talk_WithDB(parameters, s, query);
		}
		calculate_flag = true;
	}

	// Shows the table of prices to the user
	@FXML
	void get_park_price(ActionEvent event) throws IOException {
		if (parkbox.getValue().equals("choose park")) {
			JOptionPane.showMessageDialog(null, "You must enter park name", "error", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		FXMLLoader loader = new FXMLLoader();
		Stage primaryStage = new Stage();

		Pane root = loader.load(getClass().getResource("/fxmlControl/ParkPrice.fxml").openStream());
		ParkPriceController parkpricecontroller = loader.getController();
		parkpricecontroller.loadclient(parkbox.getValue());
		Scene scene = new Scene(root);
		primaryStage.setTitle("Park Price");

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	@FXML
	public void cancel_btn(ActionEvent event) throws IOException {
		if (IsEmployee) {
			/*FXMLLoader loader = new FXMLLoader();*/
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			/*Stage primaryStage = new Stage();

			Pane root = loader.load(getClass().getResource("/fxmlControl/casualEmployeeMenu.fxml").openStream());

			Scene scene = new Scene(root);
			primaryStage.setTitle("Casual Employee");

			primaryStage.setScene(scene);
			primaryStage.show();*/

		} else {

			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();

			Pane root = loader.load(getClass().getResource("/fxmlControl/UserMenu.fxml").openStream());
			UserMenuController subscribermenucontroller = loader.getController();
			if (txtuser.getText().equals("Guest")) {
				subscribermenucontroller.loadclient(userinfo, true);

			} else
				subscribermenucontroller.loadclient(userinfo, false);
			Scene scene = new Scene(root);
			primaryStage.setTitle("User Menu");

			primaryStage.setScene(scene);
			primaryStage.show();

		}
	}

	@FXML
	public void submit_btn(ActionEvent event) throws IOException {

		if (!calculate_flag) {
			JOptionPane.showMessageDialog(null, "You must calculate the price first!", "error",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (!no_of_travelers.getValue().equals(numOfTravelers)) {
			JOptionPane.showMessageDialog(null,
					"You changed the number of travelers, You must calculate the price first!", "error",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (!cash.isSelected() && !credit.isSelected()) {
			JOptionPane.showMessageDialog(null, "You must check one option", "error", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (cash.isSelected() && credit.isSelected()) {
			JOptionPane.showMessageDialog(null, "You must check one option", "error", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		// not enough space and employee added him

		// Not enough space for this order
		// Going to the waiting list or choose another time screen
		if (!CheckIfEnoughSpace() && !IsEmployee) {
			// There is no waiting list for the guest
			if (txtuser.getText().equals("Guest")) {
				JOptionPane.showMessageDialog(null, "Sorry not enough space for the this time", "error",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			insert_details("waitinglist");
			ArrayList<String> details = new ArrayList<String>();
			DateTimeFormatter formatter_2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String dateString = (datepicker.getValue()).format(formatter_2);
			details.add(dateString);
			details.add(combotime.getValue());
			details.add(orderNumber.getText());
			details.add(parkbox.getValue());
			details.add(no_of_travelers.getValue());

			FXMLLoader loader = new FXMLLoader();
			Stage primaryStage = new Stage();

			Pane root = loader.load(getClass().getResource("/fxmlControl/waitingList1.fxml").openStream());
			((Node) event.getSource()).getScene().getWindow().hide();
			WaitingListController waitinglistcontroller = loader.getController();
			waitinglistcontroller.loadclient(details, userinfo);
			Scene scene = new Scene(root);
			primaryStage.setTitle("waiting List");

			primaryStage.setScene(scene);
			primaryStage.show();
		}

		// There is enough space in the park for that order

		else if (cash.isSelected() && !credit.isSelected()) {
			if (!IsEmployee) {
				insert_details("pending");

				if (txtuser.getText().equals("Guest")) {
					JOptionPane.showMessageDialog(null, "The order has been confirmed\nAn invitation has been sent to this number: "+userinfo.get(2), "Simulation",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null,
							"The order has been confirmed\nAn invitation has been sent to this email: "
									+ userinfo.get(5) + "  \n and this number: " + userinfo.get(4),
									"Simulation", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				insert_details("used");
			}

			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();

			Pane root = loader.load(getClass().getResource("/fxmlControl/orderConfirmed.fxml").openStream());

			OrderConfirmedController orderConfirmed = loader.getController();

			ArrayList<String> details = new ArrayList<String>();
			details.add(datepicker.getValue().toString());
			details.add(combotime.getValue());
			details.add(orderNumber.getText());
			orderConfirmed.loadclient(userinfo, details, IsEmployee);
			Scene scene = new Scene(root);
			primaryStage.setTitle("Order Confirmed");

			primaryStage.setScene(scene);
			primaryStage.show();

			// Enough space and paying with credit card
		} else {
			insert_details("pending");
			if (txtuser.getText().equals("Guest")) {
				JOptionPane.showMessageDialog(null,
						"The order has been confirmed\nAn invitation has been sent to this " + userinfo.get(2)
						+ " number. You will be moved to credit card section ",
						"Simulation", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null,
						"The order has been confirmed\nAn invitation has been sent to this " + userinfo.get(5)
						+ " email \n and this " + userinfo.get(4)
						+ " number. You will be moved to credit card section ",
						"Simulation", JOptionPane.INFORMATION_MESSAGE);
			}

			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();

			Pane root = loader.load(getClass().getResource("/fxmlControl/creditcardorder.fxml").openStream());

			CreditCardOrderController creditcardorderController = loader.getController();

			ArrayList<String> details = new ArrayList<String>();
			details.add(datepicker.getValue().toString());
			details.add(combotime.getValue());
			details.add(orderNumber.getText());
			creditcardorderController.loadclient(userinfo, details, IsEmployee);
			Scene scene = new Scene(root);
			primaryStage.setTitle("Credit Card Payment");

			primaryStage.setScene(scene);
			primaryStage.show();

		}

	}

	public void loadclient(ArrayList<String> result, boolean IsEmployee) {
		this.IsEmployee = IsEmployee;
		userinfo = result;
		s.clear();
		parameters.clear();
		String query;
		id_passport = userinfo.get(2);
		query = "SELECT * FROM subinst WHERE ID = ?";
		parameters.add("UserControl");
		parameters.add("select from table");
		parameters.add(query);
		parameters.add(id_passport);
		ClientUI.chat.accept(parameters);
		if (ChatClient.result.get(0).equals("Not found") != true) {
			if (userinfo.get(7).equals("Guest")) {
				txtuser.setText("Guest");
				user = Level.Guest;
				user_number = userinfo.get(2);
				no_of_travelers.setDisable(true);
			} else {
				txtemail.setText(userinfo.get(5));
				for (int i = 1; i <= Integer.parseInt(userinfo.get(8)); i++) {
					no_of_travelers_list.add("" + i);

				}
				no_of_travelers.setItems(no_of_travelers_list);

				if (userinfo.get(7).equals("Yes")) {
					txtuser.setText("Instructor");
					user = Level.Instructor;
					user_number = userinfo.get(9);
				} else if (userinfo.get(7).equals("No")) {
					txtuser.setText("Subscriber");
					user = Level.Subscriber;
					user_number = userinfo.get(9);
				}

			}
		}
	}

	private void Talk_WithDB(ArrayList<String> parameters, ArrayList<String> s, String query) {
		parameters.add("UserControl");
		if (query.charAt(0) == 's' || query.charAt(0) == 'S')
			parameters.add("select from table");
		else
			parameters.add("set to table");
		parameters.add(query);
		parameters.addAll(s);
		ClientUI.chat.accept(parameters);

	}

	private boolean CheckIfEnoughSpace() {
		s.clear();
		parameters.clear();
		query = "SELECT * FROM park WHERE name = ?"; // Getting park data
		parameters.add("UserControl");
		parameters.add("select from table");
		parameters.add(query);
		parameters.add(parkbox.getValue());
		ClientUI.chat.accept(parameters);

		DateTimeFormatter formatter_2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dateString = (date).format(formatter_2);
		String VisitTime;
		int MaxOrderAmount;
		VisitTime = ChatClient.result.get(1);
		int Max = Integer.parseInt(ChatClient.result.get(2));
		int gap = Integer.parseInt(ChatClient.result.get(3));
		MaxOrderAmount = Max - gap;

		int min = calculateTime(combotime.getValue()) - calculateTime(VisitTime);
		int maximum = calculateTime(combotime.getValue()) + calculateTime(VisitTime);

		int orderTravelers = Integer.parseInt(no_of_travelers.getValue());
		parameters.clear();

		// Getting all the orders that are from the same park and same date and in the
		// time range

		query = "SELECT * FROM gonature.order WHERE parkName = '" + parkbox.getValue() + "' AND date = '" + dateString
				+ "' AND time < '" + calculateString(maximum) + "' AND time > '" + calculateString(min)
				+ "'  AND (status = 'pending' OR status='approved' OR status = 'ready' OR status='almostpending')";
		parameters.add("UserControl");
		parameters.add("select from table");
		parameters.add(query);

		ClientUI.chat.accept(parameters);

		// No orders in the time range
		if (ChatClient.result.get(0).equals("Not found")) {
			if (MaxOrderAmount - orderTravelers >= 0)
				return true;
			else
				return false;

		} else {
			s = ChatClient.result;
			int sum = 0;
			// Summing all the people from all the orders at the time range
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

	private void insert_details(String changestatus) {
		DateTimeFormatter formatter_2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dateString = (date).format(formatter_2);

		// inserting order data to order table in database

		s.clear();
		parameters.clear();
		s.add(orderNumber.getText());
		s.add(parkbox.getValue());

		s.add(dateString);
		s.add(combotime.getValue());
		s.add(no_of_travelers.getValue());
		s.add(changestatus);
		s.add("0");
		s.add(String.valueOf(txttotal.getText()));
		s.add("0");
		s.add(dateString.substring(3, 5));
		s.add(id_passport);
		s.add(dateString.substring(6, 10));
		query = "INSERT INTO `gonature`.`order` (`orderID`, `parkName`, `date`, `time`, `numberOfVisitors`, `status`,`totalEnterd`, `paymantSum`, `outTime`,`month`,ID,year) VALUES (?,?,?,?,?, ?, ?, ?, ?, ?,?,?);";
		Talk_WithDB(parameters, s, query);
		switch (user) {
		case Guest:
			parameters.clear();
			s.clear();
			s.add("irrelevant");
			s.add("irrelevant");
			query = "UPDATE gonature.order SET groupOrder = ? ,casualGroup=?, subscriber = 'no' WHERE orderID = ?;";
			s.add(orderNumber.getText());
			Talk_WithDB(parameters, s, query);
			break;

		case Instructor:
			if (IsEmployee) {
				ArrayList<String> res = new ArrayList<String>();
				res.add("park managmant");
				res.add("set to table");
				String visitors = String.valueOf(Integer.parseInt(visitorsin) + (Integer.parseInt(no_of_travelers.getValue())));
				System.out.println("UPDATE gonature.park " + " SET currentAmountOfVisitors = '" + visitors
						+ "' WHERE name  = '" + parkbox.getValue() + "'");
				res.add("UPDATE gonature.park " + " SET currentAmountOfVisitors = '" + visitors + "' WHERE name  = '"
						+ parkbox.getValue() + "'");
				ClientUI.chat.accept(res);

				parameters.clear();
				s.clear();
				s.add(txtemail.getText());
				s.add(no_of_travelers.getValue());
				s.add("no");
				s.add(user_number);
				s.add("yes");
				query = "UPDATE gonature.order SET Email=?,totalEnterd=?, groupOrder = ? ,instructureID=? ,casualGroup=? WHERE orderID = ?;";
				s.add(orderNumber.getText());
				Talk_WithDB(parameters, s, query);
			} else {
				parameters.clear();
				s.clear();
				s.add(txtemail.getText());
				s.add("yes");
				s.add(user_number);
				s.add("no");
				query = "UPDATE gonature.order SET Email=?,groupOrder = ? ,instructureID=?,casualGroup=? WHERE orderID = ?;";
				s.add(orderNumber.getText());
				Talk_WithDB(parameters, s, query);
			}
			break;

		case Subscriber:
			parameters.clear();
			s.clear();
			s.add(txtemail.getText());
			s.add("irrelevant");
			s.add("irrelevant");
			s.add("yes");
			s.add(user_number);
			query = "UPDATE gonature.order SET Email=?,groupOrder = ? ,casualGroup=? ,subscriber=?,subscribsionID=? WHERE orderID = ?;";
			s.add(orderNumber.getText());
			Talk_WithDB(parameters, s, query);
			break;

		}
	}

	private double Prebooked(String numOfTravelers) {
		DecimalFormat df = new DecimalFormat("#.##");
		if (IsEmployee) {
			cost = 15.0 - (15.0 * Double.parseDouble(ChatClient.result.get(5)));
			cost *= Double.parseDouble(numOfTravelers);
			txttotal.setText(df.format(cost));

		} else if (txtuser.getText().equals("Instructor")) {
			cost = 15.0 - (15.0 * Double.parseDouble(ChatClient.result.get(3)));
			cost = cost - (cost * Double.parseDouble(ChatClient.result.get(4)));
			cost *= Double.parseDouble(numOfTravelers);
			txttotal.setText(df.format(cost));
		} else if (txtuser.getText().equals("Subscriber")) {
			cost = 15.0 - (15.0 * Double.parseDouble(ChatClient.result.get(2)));
			cost = cost - (cost * Double.parseDouble(ChatClient.result.get(1)));
			cost *= Double.parseDouble(numOfTravelers);
			txttotal.setText(df.format(cost));
		} else if (txtuser.getText().equals("Guest")) {
			cost = 15 - (15 * Double.parseDouble(ChatClient.result.get(1)));
			txttotal.setText(df.format(cost));
		}
		return cost;
	}

	public void load(ArrayList<String> s2) {
		// s2.get(0)=park name, s2.get(1)= number of visitors, s2.get(2)= instructer id,

		String[] inst = s2.get(2).split("\\s+");
		user_number=inst[9];
		cash.setSelected(true);
		cash.setDisable(true);
		datepicker.setDisable(true);
		visitorsin = s2.get(4);
		id_passport = inst[2];
		this.IsEmployee = true;
		user = Level.Instructor;
		combotime.setValue(s2.get(3));
		combotime.setDisable(true);
		parkbox.setValue(s2.get(0));
		parkbox.setDisable(true);
		no_of_travelers.setValue(s2.get(1));
		no_of_travelers.setDisable(true);
		txtemail.setText(inst[5]);
		txtuser.setText("Instructor");
		credit.setDisable(true);

	}

}
