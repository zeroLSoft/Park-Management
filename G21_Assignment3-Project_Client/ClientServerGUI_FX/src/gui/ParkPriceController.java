package gui;

import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ParkPriceController {

    @FXML
    private Label parkname;

    @FXML
    private Label PersonalFamilyVisit;

    @FXML
    private Label Prebooked;

    @FXML
    private Label Prebooked1;

    @FXML
    private Label Groupvisit;

    @FXML
    private Label Prebookedinstructor;

    @FXML
    private Label Casualvisitorinstructor;

    
    /**
     * method that calculating prices and setting for view
     * @param nameofpark name of the selected park
     */
	public void loadclient(String nameofpark) {
		ArrayList<String> parameters = new ArrayList<String>();

		String query = "SELECT * FROM discount WHERE parkName=?";
		parameters.add("UserControl");
		parameters.add("select from table");
		parameters.add(query);
		parameters.add(nameofpark);
		ClientUI.chat.accept(parameters);

		ArrayList<String> parkinfo = new ArrayList<String>();

		parkinfo = ChatClient.result;

		parkname.setText(parkinfo.get(0));
		double percent = 100 * Double.parseDouble(parkinfo.get(1));
		PersonalFamilyVisit.setText(String.valueOf((int) percent));
		percent = 100 * Double.parseDouble(parkinfo.get(2));
		Prebooked.setText(String.valueOf((int) percent));
		Prebooked1.setText(String.valueOf((int) percent));
		percent = 100 * Double.parseDouble(parkinfo.get(3));
		Groupvisit.setText(String.valueOf((int) percent));
		percent = 100 * Double.parseDouble(parkinfo.get(4));
		Prebookedinstructor.setText(String.valueOf((int) percent));
		percent = 100 * Double.parseDouble(parkinfo.get(5));
		Casualvisitorinstructor.setText(String.valueOf((int) percent));

	}


}
