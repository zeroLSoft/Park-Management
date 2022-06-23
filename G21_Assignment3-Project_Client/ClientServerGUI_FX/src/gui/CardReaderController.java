package gui;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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

public class CardReaderController implements Initializable {
	@FXML
	private Button backbtn;

	@FXML
	private TextField IDnumber;

	@FXML
	private Button submitbtn;

	@FXML
	void get_back1(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();

		Pane root = loader.load(getClass().getResource("/fxmlControl/mainScreen.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("Main Screen");
 
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	void get_submit1(ActionEvent event) {
		ArrayList<String> ordersParam = new ArrayList<String>();
		ArrayList<String> parkParam = new ArrayList<String>();
		LocalDate today = LocalDate.now(); // gets the current date
		LocalTime time = LocalTime.now(); // gets the current time
		String formattedDate = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String Oquery = "SELECT * FROM `gonature`.`order` WHERE (`orderID` = ? OR subscribsionID = ? OR instructureID = ? OR ID = ?) AND date =?;";
		ordersParam.clear();
		ordersParam.add("UserControl");
		ordersParam.add("select from table");
		ordersParam.add(Oquery);
		ordersParam.add(IDnumber.getText());
		ordersParam.add(IDnumber.getText());
		ordersParam.add(IDnumber.getText());
		ordersParam.add(IDnumber.getText());
		ordersParam.add(formattedDate);
		ClientUI.chat.accept(ordersParam);

		if (ChatClient.result.get(0).equals("Not found")) // in case there is no such order
		{
			JOptionPane.showMessageDialog(null, "There is no order on this card today\n", "Error",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			String numOfVisitors = ChatClient.result.get(4);
			String status = ChatClient.result.get(6);
			String OrderID = ChatClient.result.get(0); // gets the order id
			String Dtime = ChatClient.result.get(3); // gets the reservation time
			String OutTime = ChatClient.result.get(9); // gets the out time of the order
			char t1 = Dtime.charAt(0);
			char t2 = Dtime.charAt(1);
			int Dhour = Integer.parseInt(String.valueOf(t1)) * 10 + Integer.parseInt(String.valueOf(t2));// gets the
																											// hour in
																											// the order
																											// database
			String Pquery = "SELECT * FROM `gonature`.`park` WHERE `name` = ?";
			parkParam.clear();
			parkParam.add("UserControl");
			parkParam.add("select from table");
			parkParam.add(Pquery);
			parkParam.add(ChatClient.result.get(1));
			ClientUI.chat.accept(parkParam);
			int Ahour = time.getHour(); // gets the time the visitor actually came
			String visitTime = ChatClient.result.get(1); // gets the visit time from park database
			t1 = visitTime.charAt(0);
			t2 = visitTime.charAt(1);
			int Vhour = Integer.parseInt(String.valueOf(t1)) * 10 + Integer.parseInt(String.valueOf(t2));
			t1 = visitTime.charAt(3);
			t2 = visitTime.charAt(4);
			int Vmin = Integer.parseInt(String.valueOf(t1)) * 10 + Integer.parseInt(String.valueOf(t2));
			String CurrentAmountVisitors = ChatClient.result.get(4);
			int CAV = Integer.parseInt(CurrentAmountVisitors);

			if (status.equals("approved")) // in case the visitor gets in the park
			{
				if (Ahour < Dhour) {
					JOptionPane.showMessageDialog(null, "You cant enter yet\nPlease return at " + Dtime, "Error",
							JOptionPane.INFORMATION_MESSAGE);
				}

				if (Ahour < Dhour + Vhour && Ahour >= Dhour) {
					int temp = Dhour + Vhour;
					String leave = String.valueOf(temp) + ":00";
					JOptionPane.showMessageDialog(null,
							"You may enter the park! Enjoy\nPlease leave the park at " + leave, "Approval",
							JOptionPane.INFORMATION_MESSAGE);
					int num = Integer.parseInt(numOfVisitors); // gets the amount of visitors
					int total = num + CAV; // calculates the new amount of current visitors
					String s = String.valueOf(total);

					Pquery = "UPDATE gonature.park SET currentAmountOfVisitors = ? WHERE name = ?;";
					parkParam.clear();
					parkParam.add("UserControl");
					parkParam.add("set to table");
					parkParam.add(Pquery);
					parkParam.add(s);
					parkParam.add(ChatClient.result.get(0));
					ClientUI.chat.accept(parkParam);

					Oquery = "UPDATE gonature.order SET status = ? , totalEnterd = ? WHERE orderID = ? ;";
					ordersParam.clear();
					ordersParam.add("UserControl");
					ordersParam.add("set to table");
					ordersParam.add(Oquery);
					ordersParam.add("used");
					ordersParam.add(numOfVisitors);
					ordersParam.add(OrderID);
					ClientUI.chat.accept(ordersParam);
				}

				if (Ahour > Dhour + Vhour) {
					String str = Dhour + Vhour + ":" + Vmin;
					JOptionPane.showMessageDialog(null, "Sorry You missed your trip\nThe trip was until " + str,
							"Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
            // in case the visitor gets out of the park
			if (status.equals("used")) 
			{
				//In case the user already left and tries to leave again
				if(!OutTime.equals("0")) {
					JOptionPane.showMessageDialog(null, "You already confirmed that you left the park, Have a great day", "Approval",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				JOptionPane.showMessageDialog(null, "Hope you enjoyed the visit\nGood Bye", "Approval",
						JOptionPane.INFORMATION_MESSAGE);
				int num = Integer.parseInt(numOfVisitors); // gets the amount of visitors
				int total = CAV - num;
				String s = String.valueOf(total);
				
				Pquery = "UPDATE gonature.park SET currentAmountOfVisitors = ? WHERE name = ?;";
				parkParam.clear();
				parkParam.add("UserControl");
				parkParam.add("set to table");
				parkParam.add(Pquery);
				parkParam.add(s);
				parkParam.add(ChatClient.result.get(0));
				ClientUI.chat.accept(parkParam); //Updates the current amount of visitors in the park
				parkParam.clear();
				parkParam.add("option3");
				parkParam.add("set entry");
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
				Date date = new Date();  
				String[] temp = formatter.format(date).split("\\s+");
				String[] tem2=temp[1].split(":");
				String time1 = tem2[0]+":"+tem2[1];
				parkParam.add("UPDATE gonature.order " + " SET outTime = '"+time1+"'  WHERE orderID  = '"+OrderID+"'");
				ClientUI.chat.accept(parkParam);
				
			}

			if (status.equals("canceled")) {
				JOptionPane.showMessageDialog(null, "This visit has been canceled\nGood Bye", "error",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
