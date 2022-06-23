package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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

public class OrderConfirmedController  {

	ArrayList<String> userinfo = new ArrayList<String>();
	
	
    @FXML
    private Button mainbtn;

    @FXML
    private Button exitbtn;

    @FXML
    private TextField datetxt;

    @FXML
    private TextField timetxt;

    @FXML
    private TextField idtxt;


	private boolean isEmployee;

    @FXML
    void GetExitBtn(ActionEvent event) {
    	System.exit(0);
    }

    /**
     * return after user is done with order
     * @param event
     * @throws IOException
     */
    @FXML
    void getMainBtn(ActionEvent event) throws IOException {
    	// Casual Employee
    			if (isEmployee) {
    				//FXMLLoader loader = new FXMLLoader();
    				((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
    				/*Stage primaryStage = new Stage();

    				Pane root = loader.load(getClass().getResource("/fxmlControl/casualEmployeeMenu.fxml").openStream());

    				Scene scene = new Scene(root);
    				primaryStage.setTitle("Casual Employee");

    				primaryStage.setScene(scene);
    				primaryStage.show();
*/
    			}
    			// Subscriber
    			else {
    				FXMLLoader loader = new FXMLLoader();
    				((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
    				Stage primaryStage = new Stage();

    				Pane root = loader.load(getClass().getResource("/fxmlControl/UserMenu.fxml").openStream());

    				UserMenuController userMenuController = loader.getController();
    				if (userinfo.get(7).equals("Guest"))
    					userMenuController.loadclient(userinfo, true);
    				else
    					userMenuController.loadclient(userinfo, false);
    				Scene scene = new Scene(root);
    				primaryStage.setTitle("User Menu");

    				primaryStage.setScene(scene);
    				primaryStage.show();
    			}

    }


/**
 * inisialize order info
 * @param userinfo user
 * @param details  user info
 * @param isEmployee flag if imployee
 */

	public void loadclient(ArrayList<String> userinfo, ArrayList<String> details, boolean isEmployee) {
		this.isEmployee = isEmployee;
		datetxt.setText(details.get(0));
		timetxt.setText(details.get(1));
		idtxt.setText(details.get(2));
		this.userinfo = userinfo;
		
	}

}

