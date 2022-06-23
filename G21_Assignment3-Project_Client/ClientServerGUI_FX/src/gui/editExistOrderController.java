package gui;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Logic.Order;
import client.ChatClient;
import client.ClientUI;

public class editExistOrderController implements Initializable {
	private ArrayList<String> arr;

	@FXML
	private TableView<Order> ordertable;
	@FXML
	private TableColumn<Order, String> orderid;

	@FXML
	private TableColumn<Order, String> parkname;

	@FXML
	private TableColumn<Order, String> date;

	@FXML
	private TableColumn<Order, String> time;
	@FXML
	private TableColumn<Order, String> nooftravelers;
	@FXML
	private TableColumn<Order, String> status;

	@FXML
	private TableColumn<Order, String> total;
	@FXML
	private TextField ordertxt;

	@FXML
	private Button cancelbtn;

	@FXML
	private Button approvebtn;

	@FXML
	private Button backbtn;

	private boolean isFastEntry;

	private boolean flag;

	private boolean Isguest;

	/**
	 * initialize table
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		orderid.setCellValueFactory(new PropertyValueFactory<Order, String>("orderid"));
		parkname.setCellValueFactory(new PropertyValueFactory<Order, String>("parkname"));
		date.setCellValueFactory(new PropertyValueFactory<Order, String>("date"));
		time.setCellValueFactory(new PropertyValueFactory<Order, String>("time"));
		nooftravelers.setCellValueFactory(new PropertyValueFactory<Order, String>("nooftravelers"));
		status.setCellValueFactory(new PropertyValueFactory<Order, String>("status"));
		total.setCellValueFactory(new PropertyValueFactory<Order, String>("total"));

	}

	/**
	 * method that checking who the user and getting his details
	 * @return return order details if the chosen user
	 */
	public ObservableList<Order> getdetails() {
		ArrayList<String> parameters = new ArrayList<>();
		if (arr.get(7).equals("Yes")) {// if instructor
			String query = "SELECT * FROM `gonature`.`order` WHERE instructureID=?";
			parameters.add("UserControl");
			parameters.add("select from table");
			parameters.add(query);
			parameters.add(arr.get(9));// arr.get(9)=id
			ClientUI.chat.accept(parameters);
		} else if (arr.get(7).equals("No")) {// if subscriber
			String query = "SELECT * FROM `gonature`.`order` WHERE subscribsionID=?";
			parameters.add("UserControl");
			parameters.add("select from table");
			parameters.add(query);
			parameters.add(arr.get(9));// arr.get(9)=id
			ClientUI.chat.accept(parameters);

		} else if (arr.get(7).equals("Guest")) {// if guest
			String query = "SELECT * FROM `gonature`.`order` WHERE ID=?";
			parameters.add("UserControl");
			parameters.add("select from table");
			parameters.add(query);
			parameters.add(arr.get(2));// arr.get(9)=id
			ClientUI.chat.accept(parameters);

		}
		if (ChatClient.result.get(0).equals("Not found")) {
			JOptionPane.showMessageDialog(null, "You don't have orders,Please open a new order", "error",
					JOptionPane.INFORMATION_MESSAGE);

		} else {
			ArrayList<String> s = ChatClient.result;

			ObservableList<Order> orders = FXCollections.observableArrayList();
			for (int i = 0; i < s.size(); i = i + 19) {
				if (!(s.get(i + 6).equals("canceled")||s.get(i+6).equals("used")))
					orders.add(new Order(s.get(i), s.get(i + 1), s.get(i + 2), s.get(i + 3), s.get(i + 4), s.get(i + 6),
							s.get(i + 8)));
			}
			return orders;
		}
		return null;
	}

	/**
	 * method that canceling order
	 * getting order
	 * Updating the order status to canceled
	 * if Order is in Waiting list
	 * and Updating the queue of all the waiting list orders
	 * @param event
	 */
	@FXML
	public void getcancelbtn(ActionEvent event) {// cancel order
		ArrayList<String> parameters = new ArrayList<>();
		String query = "SELECT * FROM `gonature`.`order` WHERE orderID=? AND ID=?";
		parameters.add("UserControl");
		parameters.add("select from table");
		parameters.add(query);
		parameters.add(ordertxt.getText());
		parameters.add(arr.get(2));
		ClientUI.chat.accept(parameters);
		ArrayList<String> order = ChatClient.result;
		if (order.get(0).equals("Not found")) {
			JOptionPane.showMessageDialog(null, "The order doesn't exist", "error", JOptionPane.INFORMATION_MESSAGE);
			ordertxt.clear();
			return;
		}

		// Updating the order status to canceled
		parameters.clear();
		query = "UPDATE gonature.order SET status = 'canceled' WHERE orderID = ?;";
		parameters.add("UserControl");
		parameters.add("set to table");
		parameters.add(query);
		parameters.add(ordertxt.getText());
		ClientUI.chat.accept(parameters);

		parameters.clear();
		query = "SELECT * FROM `gonature`.`waitinglist` WHERE Order_id=?;";
		parameters.clear();
		parameters.add("UserControl");
		parameters.add("select from table");
		parameters.add(query);
		parameters.add(ordertxt.getText());
		ClientUI.chat.accept(parameters);

		// Order is in Waiting list
		if (ChatClient.result.get(0).equals("Not found") != true) {
			ArrayList<String> orderInWaitingList = ChatClient.result;
			int queue = Integer.parseInt(orderInWaitingList.get(1));

			// Getting all the orders from waiting list for that time and date
			query = "SELECT * FROM `gonature`.`waitinglist` WHERE date = ? AND time = ? AND park = ?;";
			parameters.clear();
			parameters.add("UserControl");
			parameters.add("select from table");
			parameters.add(query);
			parameters.add(orderInWaitingList.get(2)); // date
			parameters.add(orderInWaitingList.get(3)); // time
			parameters.add(orderInWaitingList.get(4)); // park name
			ClientUI.chat.accept(parameters);
			ArrayList<String> s = ChatClient.result;

			// Updating the queue of all the waiting list orders
			for (int i = 0; i < s.size(); i += 6) {
				if (queue < Integer.parseInt(s.get(i + 1))) {
					int num = Integer.parseInt(s.get(i + 1)) - 1;
					parameters.clear();
					query = "UPDATE gonature.waitinglist SET Queue = ? WHERE Order_id=?";
					parameters.add("UserControl");
					parameters.add("set to table");
					parameters.add(query);
					parameters.add(String.valueOf(num));
					parameters.add(s.get(i));
					ClientUI.chat.accept(parameters);
				}
			}
			// Deleting order from waiting list
			query = "DELETE FROM `gonature`.`waitinglist` WHERE (`Order_id` = ?);";
			parameters.clear();
			parameters.add("UserControl");
			parameters.add("set to table");
			parameters.add(query);
			parameters.add(orderInWaitingList.get(0));
			ClientUI.chat.accept(parameters);

			// Deleting order from order table
			parameters.clear();
			query = "DELETE FROM gonature.order WHERE orderID=?";
			parameters.add("UserControl");
			parameters.add("set to table");
			parameters.add(query);
			parameters.add(ordertxt.getText());
			ClientUI.chat.accept(parameters);
			ordertable.setItems(getdetails());
			ordertxt.clear();

		}

		// Order was pending
		// amountOfPeople = the people that were in this order
		else {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date1 = new Date();
			String[] temp = formatter.format(date1).split("\\s+");
			String[] tem2 = temp[1].split(":");
			String currentTime = tem2[0] + ":" + tem2[1];

			flag = false;
			int amountOfPeople = Integer.parseInt(order.get(4));
			String date = order.get(2);
			String time = order.get(3);
			String park = order.get(1);
			query = "SELECT * FROM `gonature`.`waitinglist` WHERE date = ? AND time = ? AND park = ?;";
			parameters.clear();
			parameters.add("UserControl");
			parameters.add("select from table");
			parameters.add(query);
			parameters.add(date);
			parameters.add(time);
			parameters.add(park);
			ClientUI.chat.accept(parameters);

			// There are orders in waiting list for that time and date
			if (ChatClient.result.get(0).equals("Not found") != true) {
				ArrayList<String> result = ChatClient.result;

				while (amountOfPeople > 0 && !result.get(0).equals("Not found")) {
					for (int j = 0; j < result.size(); j += 6) {
						// looking for the First in the queue
						if (result.get(j + 1).equals("1")) {
							// number of people in the current first place waiting list order
							int current = Integer.parseInt(result.get(j + 5));
							// Enough space for the first order to become ready
							if (amountOfPeople - current >= 0) {
								parameters.clear();
								query = "UPDATE gonature.order SET status=? , readytime=? WHERE orderID=?";
								parameters.add("UserControl");
								parameters.add("set to table");
								parameters.add(query);
								parameters.add("almostpending");// Setting the time when the order left the waiting list
								parameters.add(currentTime);
								parameters.add(result.get(j));
								ClientUI.chat.accept(parameters);

								// updating the waiting list queue
								for (int i = 0; i < result.size(); i += 6) {
									int num = Integer.parseInt(result.get(i + 1)) - 1;
									parameters.clear();
									query = "UPDATE gonature.waitinglist SET Queue = ? WHERE Order_id=?";
									parameters.add("UserControl");
									parameters.add("set to table");
									parameters.add(query);
									parameters.add(String.valueOf(num));
									parameters.add(result.get(i));
									ClientUI.chat.accept(parameters);
								}
								// Deleting order from waiting list
								query = "DELETE FROM `gonature`.`waitinglist` WHERE (`Order_id` = ?);";
								parameters.clear();
								parameters.add("UserControl");
								parameters.add("set to table");
								parameters.add(query);
								parameters.add(result.get(j));
								ClientUI.chat.accept(parameters);

								// updating the amount of people after the new order
								amountOfPeople -= current;
								flag = true;
							} else {
								ordertable.setItems(getdetails());
								ordertxt.clear();
								return;
							}

						}

						if (flag)
							break;
					}

					// Updating the result after taking order from waiting list
					query = "SELECT * FROM `gonature`.`waitinglist` WHERE date = ? AND time = ? AND park = ?;";
					parameters.clear();
					parameters.add("UserControl");
					parameters.add("select from table");
					parameters.add(query);
					parameters.add(date);
					parameters.add(time);
					parameters.add(park);
					ClientUI.chat.accept(parameters);
					result = ChatClient.result;
					flag = false;

				}
			}
			ordertable.setItems(getdetails());
			ordertxt.clear();
		}

	}

	/**
	 * The user approves ready or almost pending orders
	 * updating status too approved
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void getApprove(ActionEvent event) throws IOException {
		ArrayList<String> parameters = new ArrayList<>();
		String query = "SELECT * FROM `gonature`.`order` WHERE orderID=? AND ID=?";
		parameters.add("UserControl");
		parameters.add("select from table");
		parameters.add(query);
		parameters.add(ordertxt.getText());
		parameters.add(arr.get(2));
		ClientUI.chat.accept(parameters);
		ArrayList<String> order = ChatClient.result;

		// order was not found
		if (order.get(0).equals("Not found")) {
			JOptionPane.showMessageDialog(null, "Incorrent order ID", "error", JOptionPane.INFORMATION_MESSAGE);
			ordertxt.clear();
			return;
		}

		if (order.get(6).equals("ready")) {
			query = "UPDATE gonature.order SET status='approved' WHERE orderID=? AND ID=?";
			parameters.clear();
			parameters.add("UserControl");
			parameters.add("set to table");
			parameters.add(query);
			parameters.add(ordertxt.getText());
			parameters.add(arr.get(2));
			ClientUI.chat.accept(parameters);
			ordertable.setItems(getdetails());
			JOptionPane.showMessageDialog(null, "Order approved, you may return", "Approved", JOptionPane.INFORMATION_MESSAGE);
		}

		else if (order.get(6).equals("almostpending")) {
			query = "UPDATE gonature.order SET status='pending' WHERE orderID=? AND ID=?";
			parameters.clear();
			parameters.add("UserControl");
			parameters.add("set to table");
			parameters.add(query);
			parameters.add(ordertxt.getText());
			parameters.add(arr.get(2));
			ClientUI.chat.accept(parameters);
			ordertable.setItems(getdetails());
			JOptionPane.showMessageDialog(null, "Order activated", "Approved", JOptionPane.INFORMATION_MESSAGE);
			
		} else
			JOptionPane.showMessageDialog(null, "You can't approve a non ready or non almostpending order", "error",
					JOptionPane.INFORMATION_MESSAGE);
		ordertxt.clear();
	
	}
/**
 * back 
 * @param event
 * @throws IOException
 */
	@FXML
	void get_back(ActionEvent event) throws IOException {
		if (!isFastEntry) {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();

			Pane root = loader.load(getClass().getResource("/fxmlControl/UserMenu.fxml").openStream());
			UserMenuController usermenucontroller = loader.getController();
			usermenucontroller.loadclient(arr, Isguest);
			Scene scene = new Scene(root);
			primaryStage.setTitle("User Menu");
			primaryStage.setScene(scene);
			primaryStage.show();
		} else {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();

			Pane root = loader
					.load(getClass().getResource("/fxmlControl/EditsubscriberMenuFastEnter.fxml").openStream());
			EditsubscriberMenuFastEnterController controller = loader.getController();
			controller.loadclient(arr);
			Scene scene = new Scene(root);
			primaryStage.setTitle("Fast Entry");
			primaryStage.setScene(scene);
			primaryStage.show();

		}

	}

	public void loadclient(ArrayList<String> result, boolean isFastEntry, boolean Isguest) {
		arr = result;
		this.isFastEntry = isFastEntry;
		this.Isguest = Isguest;

		ordertable.setItems(getdetails());
	}
}
