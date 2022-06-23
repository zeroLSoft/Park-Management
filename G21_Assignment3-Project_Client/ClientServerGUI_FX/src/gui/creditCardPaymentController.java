package gui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class creditCardPaymentController implements Initializable {

	@FXML
	private Button btnSubmit = null;
	
	@FXML
	private Button btnWithout = null;
	
	@FXML
	private TextField card1txt; 
	
	@FXML
	private TextField card2txt;
	
	@FXML
	private TextField card3txt;
	
	@FXML
	private TextField card4txt;
	
	@FXML
	private TextField expiretxt;
	
	@FXML
	private TextField cvvtxt;
	
	private String ID;

	private ArrayList<String> srInfo;
	
	

	
	
	// Getting ID from the sub sign up
	public void loadID(ArrayList<String> subInfo, ArrayList<String> srInfo) {
		this.ID = subInfo.get(2); 
		this.srInfo=srInfo;
	}


	public void getSignUpBtn(ActionEvent event) throws Exception {
		ArrayList<String> parameters=new ArrayList<String>();
		ArrayList<String> creditcardInfo = new ArrayList<String>();
		String query1,card1,card2,card3,card4,expire,cvv;
		boolean flag = true;
		card1=card1txt.getText();
		card2=card2txt.getText();
		card3=card3txt.getText();
		card4=card4txt.getText();
		expire=expiretxt.getText();
		cvv=cvvtxt.getText();
		
		creditcardInfo.add(this.ID); //Setting the foreign key
		
		if(card1.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null,"You must enter the card number","error",JOptionPane.INFORMATION_MESSAGE);
			flag = false;}
		else if(!card1.matches("[0-9]+")) {
			JOptionPane.showMessageDialog(null,"You can only enter digits","error",JOptionPane.INFORMATION_MESSAGE);
			flag = false;}
		else if(card1.length()!=4) {
			JOptionPane.showMessageDialog(null,"Input Must be 4 digits","error",JOptionPane.INFORMATION_MESSAGE);
			flag = false;}
		else creditcardInfo.add(card1);
		
		if(!flag) return;
		
		if(card2.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null,"You must enter the card number","error",JOptionPane.INFORMATION_MESSAGE);
			flag = false;}
		else if(!card2.matches("[0-9]+")) {
			JOptionPane.showMessageDialog(null,"You can only enter digits","error",JOptionPane.INFORMATION_MESSAGE);
			flag = false;}
		else if(card2.length()!=4) {
			JOptionPane.showMessageDialog(null,"Input Must be 4 digits","error",JOptionPane.INFORMATION_MESSAGE);
			flag = false;}
		else  creditcardInfo.add(card2);
		
		if(!flag) return;
		
		
		if(card3.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null,"You must enter the card number","error",JOptionPane.INFORMATION_MESSAGE);
			flag = false;}
		else if(!card3.matches("[0-9]+")) {
			JOptionPane.showMessageDialog(null,"You can only enter digits","error",JOptionPane.INFORMATION_MESSAGE);
			flag = false;}
		else if(card3.length()!=4) {
			JOptionPane.showMessageDialog(null,"Input Must be 4 digits","error",JOptionPane.INFORMATION_MESSAGE);
			flag = false;}
		else  creditcardInfo.add(card3);
		
		if(!flag) return;
			

		if(card4.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null,"You must enter the card number","error",JOptionPane.INFORMATION_MESSAGE);
			flag = false;}
		else if(!card4.matches("[0-9]+")) {
			JOptionPane.showMessageDialog(null,"You can only enter digits","error",JOptionPane.INFORMATION_MESSAGE);
			flag = false;}
		else if(card4.length()!=4) {
			JOptionPane.showMessageDialog(null,"Input Must be 4 digits","error",JOptionPane.INFORMATION_MESSAGE);
			flag = false;}
		else creditcardInfo.add(card4);
		
		if(!flag) return;
		
		if(expire.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null,"You must enter the expiration date","error",JOptionPane.INFORMATION_MESSAGE);
			flag = false;}
		else creditcardInfo.add(expire);
		
		if(!flag) return;
		
		
			
		if(cvv.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null,"You must enter the cvv","error",JOptionPane.INFORMATION_MESSAGE);
			flag = false;}
		else if(!cvv.matches("[0-9]+")) {
			JOptionPane.showMessageDialog(null,"The cvv only must only contain digits","error",JOptionPane.INFORMATION_MESSAGE);
			flag = false;}
		else if(cvv.length()!=3) {
			JOptionPane.showMessageDialog(null,"The cvv Must be 3 digits","error",JOptionPane.INFORMATION_MESSAGE);
			flag = false;}
		else creditcardInfo.add(cvv);
		
			
		
		if(flag) {
	 
			    //Adding the credit card to the database with the sub id as the foreign key
				query1 = "INSERT INTO creditcard " + "VALUES (?,?,?,?,?,?,?);";
				parameters.add("UserControl");
				parameters.add("set to table");
				parameters.add(query1);
				parameters.addAll(creditcardInfo);
				ClientUI.chat.accept(parameters);
				
				JOptionPane.showMessageDialog(null,"New Subscriber is created With a credit card!","update successful",JOptionPane.INFORMATION_MESSAGE);
				
				FXMLLoader loader = new FXMLLoader();
				
				((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
				Stage primaryStage = new Stage();
				Pane root = loader.load(getClass().getResource("/fxmlControl/ServiceRepresentative.fxml").openStream());
				
				ServiceRepresentativeController SR = loader.getController();
				SR.load(srInfo);
				
				Scene scene = new Scene(root);			
				primaryStage.setTitle("Service Representative");

				primaryStage.setScene(scene);		
				primaryStage.show();
			}
		}	
	

	//The SR can choose to create the subscriber without credit card
	public void getWithoutBtn(ActionEvent event) throws Exception {
		
		JOptionPane.showMessageDialog(null,"New Subscriber is created without a credit card, press OK to return","update successful",JOptionPane.INFORMATION_MESSAGE);
		FXMLLoader loader = new FXMLLoader();
		
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/ServiceRepresentative.fxml").openStream());
		
		ServiceRepresentativeController SR = loader.getController();
		SR.load(srInfo);
		
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Service Representative");

		primaryStage.setScene(scene);		
		primaryStage.show();
	}
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {	
		
	}

	
}
