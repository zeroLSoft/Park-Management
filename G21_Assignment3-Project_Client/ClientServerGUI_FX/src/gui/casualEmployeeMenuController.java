package gui;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * 
 * menu of park employee
 *
 */
public class casualEmployeeMenuController implements Initializable{
	ArrayList<String> s;
	ArrayList<String> instrocture;
	ArrayList<String> s2;
	ObservableList<String> parks;
	@FXML
	private Button visitorbtn;
	@FXML
	private Button checkbtn;
	@FXML
	private Button groupbtn;
	@FXML
	private Button CExitbtn;
	@FXML
	private Button signoutbtn;

	@FXML
	private TextField IDtxt;

	@FXML
	private TextField orderTxt;
	@FXML
	private TextField visitorsTxt;
	@FXML
	private TextField ExitTxt;
	@FXML
	private TextField groupSTxt;
	@FXML
	private Button btnViewStats=null;
	@FXML
	private ComboBox park;
	String[] park1,park2,park3;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<String> parameters=new ArrayList<String>();
		String query="SELECT * FROM park";
		parameters.add("ManagmantControl");
		parameters.add("select for report");
		parameters.add("SELECT * FROM gonature.park");
		parameters.add("SELECT * FROM gonature.order ORDER BY ABS(year)");
		parameters.add("SELECT * FROM gonature.casual ORDER BY ABS(year)");
		ClientUI.chat.accept(parameters);
		s2=ChatClient.result;
		park1=ChatClient.result.get(0).split("\\s+");
		park2=ChatClient.result.get(1).split("\\s+");
		park3=ChatClient.result.get(2).split("\\s+");
		parks= FXCollections.observableArrayList("haifa","karmiel","tel-aviv");
		park.setItems(parks);

	}
	/**
	 * method that handling casual group,
	 * watching if there is space, finding instructor input and sanding to create order
	 * preper info for order
	 * time and total ppl at park
	 * and sand to order managmant
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void GetGroup(ActionEvent event) throws IOException {
		ArrayList<String> parameters=new ArrayList<String>();
		String query,id,num;
		boolean flag = true;
		id=IDtxt.getText();
		num=groupSTxt.getText();

		if(id.trim().isEmpty()||num.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null,"You must fill all the fields for group order","error",JOptionPane.INFORMATION_MESSAGE);
			flag = false;}

		if(flag) {
			query = "SELECT * FROM gonature.subinst WHERE ID = ?";          //find instructor id
			parameters.add("ManagmantControl");
			parameters.add("select from table");
			parameters.add(query);
			parameters.add(id);
			ClientUI.chat.accept(parameters);
			System.out.println(ChatClient.result.get(0));
			if(!(ChatClient.result.get(0).equals("Not found")||ChatClient.result.get(7).equals("Guest")||ChatClient.result.get(7).equals("no"))) {
				instrocture=ChatClient.result;
				parameters.clear();
				parameters.add("ManagmantControl");                                             //get park and total visitors at park
				parameters.add("get visit info");                                               //
				parameters.add("SELECT * FROM gonature.park WHERE name = '"+s.get(5)+"'");
				parameters.add("SELECT * FROM gonature.casual WHERE parkName = '" + s.get(5) + "' AND outTime = '0'");
				parameters.add("SELECT * FROM gonature.order WHERE parkName = '" + s.get(5) + "' AND outTime = '0' AND status = 'used'");
				ClientUI.chat.accept(parameters);

				ArrayList<String> info=ChatClient.result;
				String[] str;
				int order=0;
				int casualc=0;
				for(int i=1;i<info.size();i++) {          //Calculate space in park
					str=info.get(i).split("\\s+");
					if(str.length==19)
						order=order+Integer.parseInt(str[7]);
					if(str.length==9)
						casualc++;
				}

				String[] arr=info.get(0).split("\\s+");

				if(Integer.parseInt(arr[3])>=(order+casualc+Integer.parseInt(num))) {     //if there is space
					ArrayList<String> send =new ArrayList<String>(); 
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
					Date date = new Date();  
					String[] temp = formatter.format(date).split("\\s+");  //
					parameters.add(temp[0]);                               //preper info for order
					String[] tem2=temp[1].split(":");                      //park name, number of visitors
					String time = tem2[0]+":"+tem2[1];                     //instrocture id
					send.add(s.get(5));                                    //time and total ppl at park
					send.add(num);                                         //
					send.add(instrocture.get(0)+" "+instrocture.get(1)+" "+instrocture.get(2)+" "+instrocture.get(3)+" "+instrocture.get(4)+" "+instrocture.get(5)+" "+instrocture.get(6)+" "+instrocture.get(7)+" "+instrocture.get(8)+" "+instrocture.get(9));
					send.add(time);
					send.add(arr[4]);
					FXMLLoader loader = new FXMLLoader();
					//((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
					Stage primaryStage = new Stage();

					Pane root = loader.load(getClass().getResource("/fxmlControl/Order.fxml").openStream());

					OrderController OrderController = loader.getController();
					OrderController.load(send);

					Scene scene = new Scene(root);
					primaryStage.setTitle("Order(Of instructor)");

					primaryStage.setScene(scene);
					primaryStage.show();
				}
				else {	
					JOptionPane.showMessageDialog(null,"No space in park, all options disabled","error",JOptionPane.INFORMATION_MESSAGE);
				}

			}else
				JOptionPane.showMessageDialog(null,"No instructor with such ID exists","error",JOptionPane.INFORMATION_MESSAGE);


		}

	}
/**
 * 
 * @param result
 */
	public void loadWorker(ArrayList<String> result) {
		this.s=result;

	}

	/**
	 * method that handling casual visitor, send us to new window to handle casual visitor
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void GetVisitor(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();

		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();

		Pane root = loader.load(getClass().getResource("/fxmlControl/CasualWindow.fxml").openStream());

		CasualManagmantControl CasualManagmantControl = loader.getController();
		CasualManagmantControl.loadWorker(s);

		Scene scene = new Scene(root);
		primaryStage.setTitle("Casual entry");

		primaryStage.setScene(scene);
		primaryStage.show();

	}


	/**
	 * method that handling visitors with order
	 * calculating cost finding the order and updating status
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void getOrderOut(ActionEvent event) throws IOException {
		ArrayList<String> parameters2=new ArrayList<String>();
		String id=orderTxt.getText(),time,visit=visitorsTxt.getText();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");        // calculate todays date
		Date date = new Date();  
		String[] temp=formatter.format(date).split("\\s+");
		String[] tem2=temp[1].split(":");
		time=tem2[0]+":"+tem2[1];                                                        //
		parameters2.add("ManagmantControl");                                             //get discount info and park info
		parameters2.add("get visit info");                                               //
		parameters2.add("SELECT * FROM discount WHERE ParkName = '"+s.get(5)+"'");
		parameters2.add("SELECT * FROM gonature.park WHERE name = '"+s.get(5)+"'");
		ClientUI.chat.accept(parameters2);
		ArrayList<String> info = ChatClient.result;


		if(id.trim().isEmpty()||visit.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null,"ID or visitors fiels are empty","error",JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			String[] temp3=info.get(0).split("\\s+");
			double sub = 15.0 - (15.0 * Double.parseDouble(temp3[2]));           //calculate price for subscription
			sub=sub*Integer.parseInt(visit);
			String query;
			ArrayList<String> parameters=new ArrayList<String>();
			query = "SELECT * FROM gonature.order WHERE orderID = ?";          //find order id
			parameters.add("ManagmantControl");
			parameters.add("select from table");
			parameters.add(query);
			parameters.add(id);
			ClientUI.chat.accept(parameters);

			if(!ChatClient.result.get(0).equals("Not found")) {                                                           //
				if(!(Integer.parseInt(visit)>Integer.parseInt(ChatClient.result.get(4)))) {                               //each if input is valid, is id 
					if(!ChatClient.result.get(6).equals("used")) {                                                        //is id found, time is correct
						boolean after = LocalTime.now().isAfter( LocalTime.parse(ChatClient.result.get(3)));              //order not used
						//tem2[3] current time:hh:mm:ss, staytime park stay time:hh:mm 
						temp3=info.get(1).split("\\s+");
						String[] staytime=temp3[1].split(":"),ordert=ChatClient.result.get(3).split(":");
						boolean flag;
						if(Integer.parseInt(staytime[0])>(Integer.parseInt(tem2[0])-Integer.parseInt(ordert[0])))flag=true;
						else flag=false;

						if(ChatClient.result.get(2).equals(temp[0])&&ChatClient.result.get(6).equals("approved")&&flag) { 
							parameters.clear();
							parameters.add("option1");
							parameters.add("set entry");
							query="UPDATE gonature.order " + " SET status = 'used'  WHERE orderID  = '"+ChatClient.result.get(0)+"'";//
							parameters.add(query);                                                                                   // update orders paymant, status
							query="UPDATE gonature.order " + " SET totalEnterd = ?  WHERE orderID  = '"+ChatClient.result.get(0)+"'";// and how many enter and update counter of visitors in park
							parameters.add(query);
							parameters.add(visit);
							query="UPDATE gonature.order " + " SET paymantSum = ? WHERE orderID  = '"+ChatClient.result.get(0)+"'";
							parameters.add(query);
							parameters.add(String.valueOf(sub));
							temp3=info.get(1).split("\\s+");
							String visitors=String.valueOf(Integer.parseInt(temp3[4])+Integer.parseInt(visit));
							query="UPDATE gonature.park " + " SET currentAmountOfVisitors = ? WHERE name  = '"+s.get(5)+"'";
							parameters.add(query);
							parameters.add(visitors);

							ClientUI.chat.accept(parameters);
							JOptionPane.showMessageDialog(null,"Order confirmed, the visitor's may enter","error",JOptionPane.INFORMATION_MESSAGE);
						}
						else
							JOptionPane.showMessageDialog(null,"cant use order yet or the order already expired","error",JOptionPane.INFORMATION_MESSAGE);
					}
					else
						JOptionPane.showMessageDialog(null,"Order already used","error",JOptionPane.INFORMATION_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(null,"The amount of visitor selected bigger then the one in order which is: "+ChatClient.result.get(4)+" ","error",JOptionPane.INFORMATION_MESSAGE);
			}
			else
				JOptionPane.showMessageDialog(null,"No such Order exists","error",JOptionPane.INFORMATION_MESSAGE);
		}

	}

	
/**
 * method that handling costumers that exiting the park
 * updating exit time from casual or order
 * @param event
 * @throws IOException
 */
	@FXML
	void GetExit(ActionEvent event) throws IOException {
		ArrayList<String> parameters=new ArrayList<String>();
		String id=ExitTxt.getText();

		if(id.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null,"ID or visitors fiels are empty","error",JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			parameters.add("park managmant");
			parameters.add("get visit info");                                                  // find the costumer
			parameters.add("SELECT * FROM gonature.park WHERE name = '"+s.get(5)+"'");         //and park
			parameters.add("SELECT * FROM gonature.casual WHERE parkName = '" + s.get(5) + "' AND outTime = '0' AND ID = '"+id+"'");
			parameters.add("SELECT * FROM gonature.order WHERE parkName = '" + s.get(5) + "' AND outTime = '0' AND orderID = '"+id+"'");
			ClientUI.chat.accept(parameters);
			try {
				if(!(ChatClient.result.size()==1)) {
					parameters.clear();
					parameters.add("option2");
					parameters.add("set entry");
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
					Date date = new Date();  
					String[] temp = formatter.format(date).split("\\s+");               //get exit time
					String[] tem2=temp[1].split(":");
					String time = tem2[0]+":"+tem2[1];
					String[] Array =ChatClient.result.get(0).split("\\s+");
					String[] temp1 =ChatClient.result.get(1).split("\\s+");
					String visitors;
					if(temp1.length==19) {         //if with order update order exit
						System.err.println(time);
						parameters.add("UPDATE gonature.order " + " SET outTime = '"+time+"'  WHERE orderID  = '"+id+"'");
						visitors=String.valueOf(Integer.parseInt(Array[4])-Integer.parseInt(temp1[4]));
						System.err.println(visitors);
						parameters.add("UPDATE gonature.park " + " SET currentAmountOfVisitors = ? WHERE name  = '"+s.get(5)+"'");
						parameters.add(visitors);
					}
					else {                    //if casual. update his exit time
						parameters.add("UPDATE gonature.casual " + " SET outTime = '"+time+"'  WHERE ID  = '"+id+"'");
						visitors=String.valueOf(Integer.parseInt(Array[4])-1);
						parameters.add("UPDATE gonature.park " + " SET currentAmountOfVisitors = ? WHERE name  = '"+s.get(5)+"'");
						parameters.add(visitors);
					}
					ClientUI.chat.accept(parameters);
					JOptionPane.showMessageDialog(null,"Order confirmed, the costumer may EXIT","error",JOptionPane.INFORMATION_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(null,"ID is not exist or active at the park right now","error",JOptionPane.INFORMATION_MESSAGE);
			}catch (Exception e){
				JOptionPane.showMessageDialog(null,"ID is not exist or active at the park right now","error",JOptionPane.INFORMATION_MESSAGE);
			}
		}


	}

	/**
	 * sign out to employee sign out
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void getSignOut(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();

		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();

		Pane root = loader.load(getClass().getResource("/fxmlControl/employeelogin.fxml").openStream());
		ArrayList<String> parameters =new 	ArrayList<String>();
		String query="DELETE FROM gonature.loggedin WHERE ID = ? AND password = ?";
		parameters.add("UserControl");                         //delete user from log in
		parameters.add("set to table");
		parameters.add(query);
		parameters.add(s.get(0));
		parameters.add(s.get(6));
		ClientUI.chat.accept(parameters);
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Employee Login");

		primaryStage.setScene(scene);		
		primaryStage.show();

	}
	
	public void getViewStatesBtn(ActionEvent event) throws Exception {
		ArrayList<String> temp=new ArrayList<String>();
		try{
		FXMLLoader loader = new FXMLLoader();
		//((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/ViewParameters.fxml").openStream());
		String txt=(String) park.getSelectionModel().getSelectedItem();
		temp.add(txt);temp.add(txt);temp.add(txt);temp.add(txt);temp.add(txt);temp.add(txt);temp.add("not park");
		
		ViewParametersControl ViewParametersControl = loader.getController();
		ViewParametersControl.loadInfoToView(temp);
		
		Scene scene = new Scene(root);			
		primaryStage.setTitle("view Parameters report making");

		primaryStage.setScene(scene);		
		primaryStage.show();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,"You didnt chose park , select all and try again","error",JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}
	}
	

}
