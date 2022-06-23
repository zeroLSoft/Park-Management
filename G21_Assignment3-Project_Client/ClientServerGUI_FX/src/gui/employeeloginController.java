package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.ChatIF;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * employee log in menu
 * @author oleg
 *
 */
public class employeeloginController implements Initializable {

	ArrayList<String> res;
	@FXML
	private Button enterbtn = null;

	@FXML
	private Button backbtn = null;

	@FXML
	private TextField idtxt;

	@FXML
	private TextField passtxt;

	
	
	/**
	 * get and check what id was enterd
	 * check if alreasy logged in,exist, then deside where to send
	 * @param event
	 * @throws Exception
	 */
	public void getEnterBtn(ActionEvent event) throws Exception {

		ArrayList<String> parameters=new ArrayList<String>();
		String query,id,pass;
		boolean flag = true;
		id=idtxt.getText();
		pass=passtxt.getText();


		if(id.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null,"You must enter the id","error",JOptionPane.INFORMATION_MESSAGE);
			flag = false;}

		if(pass.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null,"You must enter the password","error",JOptionPane.INFORMATION_MESSAGE);
			flag = false;}

		if(flag) {
			query="SELECT * FROM loggedin WHERE ID = ? AND password = ? ";
			parameters.add("UserControl");
			parameters.add("select from table");             //check if user logged in 
			parameters.add(query);
			parameters.add(id);
			parameters.add(pass);
			ClientUI.chat.accept(parameters);
    
			if((ChatClient.result.get(0).equals("Not found"))) {
				ChatClient.result.clear();
				parameters.clear();                       
				query="SELECT * FROM employee WHERE workerID = ? ";             //check if exist
				parameters.add("UserControl");
				parameters.add("select from table");
				parameters.add(query);
				parameters.add(id);
				ClientUI.chat.accept(parameters);


				if(ChatClient.result.get(0).equals("Not found")!=true) {

					if(ChatClient.result.get(6).equals(pass)) {
						res=ChatClient.result;
						parameters.clear();
						parameters.add("ManagmantControl");
						parameters.add("set to table");                         //if found insert to logged in
						query="INSERT INTO `gonature`.`loggedin` (`ID`,`password`) VALUES (?,?);"; //2
						parameters.add(query);
						parameters.add(id);
						parameters.add(pass);
						ClientUI.chat.accept(parameters);

						if(res.get(4).equals("SR")) {                     //  and then check what his role and open window accordingly
							FXMLLoader loader = new FXMLLoader();

							((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
							Stage primaryStage = new Stage();
							Pane root = loader.load(getClass().getResource("/fxmlControl/ServiceRepresentative.fxml").openStream());
							ServiceRepresentativeController ServiceRepresentativeController = loader.getController();				
							ServiceRepresentativeController.load(res);

							Scene scene = new Scene(root);			
							primaryStage.setTitle("Client Managment Tool");

							primaryStage.setScene(scene);		
							primaryStage.show();
						}
						if(res.get(4).equals("PM")) {
							FXMLLoader loader = new FXMLLoader();

							((Node)event.getSource()).getScene().getWindow().hide(); 
							Stage primaryStage = new Stage();
							Pane root = loader.load(getClass().getResource("/fxmlControl/ParkManagerControl.fxml").openStream());
							ParkMamagerControl ParkMamagerControl = loader.getController();				
							ParkMamagerControl.loadParkMamager(res);

							Scene scene = new Scene(root);			
							primaryStage.setTitle("Park Managment");

							primaryStage.setScene(scene);		
							primaryStage.show();
						}

						if(res.get(4).equals("DM")) {
							FXMLLoader loader = new FXMLLoader();

							((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
							Stage primaryStage = new Stage();
							Pane root = loader.load(getClass().getResource("/fxmlControl/DepartmentMenu.fxml").openStream());
							
							Scene scene = new Scene(root);			
							primaryStage.setTitle("Departmant Managment Tool");

							primaryStage.setScene(scene);		
							primaryStage.show();

						}

						if(res.get(4).equals("CE")) {
							FXMLLoader loader = new FXMLLoader();

							((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
							Stage primaryStage = new Stage();
							Pane root = loader.load(getClass().getResource("/fxmlControl/casualEmployeeMenu.fxml").openStream());

							casualEmployeeMenuController casualEmployeeMenuController = loader.getController();				
							casualEmployeeMenuController.loadWorker(res);

							Scene scene = new Scene(root);			
							primaryStage.setTitle("Casual Employee");

							primaryStage.setScene(scene);		
							primaryStage.show();

						}
					}
					else JOptionPane.showMessageDialog(null,"Incorrent Password","error",JOptionPane.INFORMATION_MESSAGE);
				}
				else JOptionPane.showMessageDialog(null,"The worker ID does not exist","error",JOptionPane.INFORMATION_MESSAGE);
			}
			else JOptionPane.showMessageDialog(null,"User already online","error",JOptionPane.INFORMATION_MESSAGE);
		}

	}	
	public void getBackBtn(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();

		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/mainScreen.fxml").openStream());


		Scene scene = new Scene(root);			
		primaryStage.setTitle("Main Screen");

		primaryStage.setScene(scene);		
		primaryStage.show();
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
}
