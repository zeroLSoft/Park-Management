package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.stage.Stage;

//
//class that editing park parameted
//
public class EditParametersControl implements Initializable {

	private ArrayList<String> s,st;
	private ArrayList<String> pendingAprovles =new ArrayList<String>();
	private ArrayList<String> original =new ArrayList<String>();
	String str,msg="";
	String[] discount =new String[10];
	String[] parkParameters =new String[10];
	String[] dsesitionSTR= {
			"Visit_time_change",
			"Maximum_visitors_per_hour_change",
			"Visit-time_gap_change",
			"Discount_change_-_Single_or_family_With_Order",
			"Discount_change_-_Subscription",
			"Discount_change_-_Group_with_order",
			"Discount_change_-_Advance_pay",
			"Discount_change_-_Group_with_no_order",
	};


	ObservableList<String> timelist=FXCollections.observableArrayList("01:00","02:00","03:00","04:00","05:00","06:00","07:00","08:00","09:00","10:00");

	@FXML
	private TextField txtStayTime;
	@FXML
	private TextField txtVisitOrderGap;
	@FXML
	private TextField txtMaxVisitsPerDay;
	@FXML
	private TextField txtSingleFamilyWithOrder;
	@FXML
	private TextField txtSubscription;
	@FXML
	private TextField txtGroupWithOrder;
	@FXML
	private TextField txtAdvancePay;
	@FXML
	private TextField txtGroupNoOrder;
	@FXML
	private Button btnSendForAprovle=null;
	@FXML
	private Button btnCancel=null;
	@FXML
	private ComboBox timeB;

	/**
	 * getting park info and checking if there is pending desitions and inisialize
	 * Interface accordingly
	 * @param s1
	 */
	public void loadEditFram(ArrayList<String> s1) {
		this.st=s1;
		System.out.println(st.get(0));
		ArrayList<String> parameters=new ArrayList<String>();
		String query;
		int[] desitionIndex = new int[15];

		parameters.add("ParkControl");
		parameters.add("get edit parameters");
		query="SELECT * FROM discount WHERE ParkName = ?";
		parameters.add(query);
		parameters.add(st.get(5));                                                  //
		query="SELECT * FROM park WHERE name = ?";                                  //get park info
		parameters.add(query);                                                      //discount info
		parameters.add(st.get(5));                                                  //and pending desitions
		ClientUI.chat.accept(parameters);                                           //
		this.s=ChatClient.result;
		parameters.clear();

		parameters.add("ManagmantControl");
		parameters.add("get desition");
		query="SELECT * FROM gonature.desition WHERE parkName ='"+st.get(5) +"' AND status = 'pending'";
		parameters.add(query);
		ClientUI.chat.accept(parameters);
		String[] arry=new String[20];

		if(!ChatClient.result.get(0).equals("empty")) {                  
			for(int i=0;i<ChatClient.result.size();i++) {                 // marking desitions that pending
				arry=ChatClient.result.get(i).split("\\s+");
				for(int j=0;j<8;j++) {
					if(dsesitionSTR[j].equals(arry[1])) {
						desitionIndex[j]=1;
					}
				}
			}
		}

		this.discount= s.get(0).split("\\s+");
		this.parkParameters = s.get(1).split("\\s+");

		//
		//inisialize interface and block desitions that pending
		//
		timeB.setValue(parkParameters[1]);
		timeB.setItems(timelist);
		if(desitionIndex[0]==1)
			timeB.setDisable(true);
		this.txtMaxVisitsPerDay.setText(parkParameters[2]);
		if(desitionIndex[1]==1)
			txtMaxVisitsPerDay.setDisable(true);
		this.txtVisitOrderGap.setText(parkParameters[3]);
		if(desitionIndex[2]==1)
			txtVisitOrderGap.setDisable(true);
		this.txtSingleFamilyWithOrder.setText(discount[1]);
		if(desitionIndex[3]==1)
			txtSingleFamilyWithOrder.setDisable(true);
		this.txtSubscription.setText(discount[2]);
		if(desitionIndex[4]==1)
			txtSubscription.setDisable(true);
		this.txtGroupWithOrder.setText(discount[3]);
		if(desitionIndex[5]==1)
			txtGroupWithOrder.setDisable(true);
		this.txtAdvancePay.setText(discount[4]);
		if(desitionIndex[6]==1)
			txtAdvancePay.setDisable(true);
		this.txtGroupNoOrder.setText(discount[5]);
		if(desitionIndex[7]==1)
			txtGroupNoOrder.setDisable(true);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	/**
	 * method that prepering desitions and sending to departmant
	 *  checking if legal, and sending parameters
	 * @param event
	 * @throws Exception
	 */
	public void getSendForAprovleBtn(ActionEvent event) throws Exception {
		ArrayList<String> parameters=new ArrayList<String>();
		String query;
		String[] output= new String[16],arry=new String[20];
		boolean flag1=true, flag2=true;
		int i;
		int[] changeIndex = new int[15];

		//get input
		output[0]=(String) timeB.getSelectionModel().getSelectedItem();
		output[1]=txtMaxVisitsPerDay.getText();
		output[2]=txtVisitOrderGap.getText();

		output[3]=txtSingleFamilyWithOrder.getText();
		output[4]=txtSubscription.getText();
		output[5]=txtGroupWithOrder.getText();
		output[6]=txtAdvancePay.getText();
		output[7]=txtGroupNoOrder.getText();




		for(i=1;i<8;i++) { //check empty fields
			if(output[i].trim().isEmpty()) {
				JOptionPane.showMessageDialog(null,"there is an empty fiels, please make sure all fiels are writen","error",JOptionPane.INFORMATION_MESSAGE);
				flag1=false;
			}
		}



		if(flag1) { // check  if change was made
			for(i=0;i<3;i++) {
				if(!(output[i].equals(parkParameters[i+1]))) {
					changeIndex[i]=1;
					flag2=false;
				}
			}
			for(i=0;i<5;i++) {
				if(!(output[i+3].equals(discount[i+1]))) {
					changeIndex[i+3]=1;
					flag2=false;
				}
			}
			if(flag2) {
				JOptionPane.showMessageDialog(null,"No change was made,desition was not send for approvle","error",JOptionPane.INFORMATION_MESSAGE);
				flag1=false;
			}

		}
		if(flag1) {//check if legal change
			try{
				int gap=Integer.parseInt(txtVisitOrderGap.getText());
				int maxvisit=Integer.parseInt(txtMaxVisitsPerDay.getText());
				for(i=0;i<5;i++) {
					if(!(output[i+3].matches("([0]*\\.?[0-9]+)|([1]*\\.?[0])"))) {
						JOptionPane.showMessageDialog(null,"wrong input,either you put char's or not legal numbers","error",JOptionPane.INFORMATION_MESSAGE);
						flag2=false;
						break;
					}
				}
				if(maxvisit<gap) {
					JOptionPane.showMessageDialog(null,"gap if bigger then max visit, it should be the opposite","error",JOptionPane.INFORMATION_MESSAGE);
					flag1=false;
				}
			} catch (NumberFormatException e) {
				flag1=false;
				JOptionPane.showMessageDialog(null,"wrong input,either you put char's or not legal numbers","error",JOptionPane.INFORMATION_MESSAGE);
			}
		}

		if(flag1) {  //if everything was ok we prepering to sand
			String[] options = new String[] {"Yes", "Cancel"};
			int option =  JOptionPane.showOptionDialog(null, "Are you sure you want to send new desitions? the moment you send you will not be able to change the selected desitions until approved or declined", "Warning!", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
					null, options, options[0]);

			if (option != JOptionPane.CLOSED_OPTION) {
				System.out.println(options[option]);
			} else {
				System.out.println("No option selected");
			}

			if(options[option].equals("Yes")) {  //if we sure, we preparing string of the desitions that made and sending
				parameters.add("ManagmantControl");
				parameters.add("set desition");
				query="INSERT INTO `gonature`.`desition` (`number`,`parkName`, `changeDescription`, `before`, `after`, `status`) VALUES (?,?,?,?,?,?);"; //2
				parameters.add(query);

				for(i=0;i<3;i++) {
					if(changeIndex[i]==1) {
						str=parkParameters[0]+" "+dsesitionSTR[i]+" "+parkParameters[i+1] + " " +output[i]+ " Pending";
						parameters.add(str);
						desibleDesitions(i);
					}
				}
				for(i=0;i<5;i++) {
					if(changeIndex[i+3]==1) {
						str=parkParameters[0]+" "+dsesitionSTR[i+3]+" "+discount[i+1] + " " +output[i+3]+ " Pending";
						parameters.add(str);
						desibleDesitions(i+3);
					}
				}
				ClientUI.chat.accept(parameters);

			}
		}
	}

/**
 * 
 * @param i indext of the desition we want to disable
 */
	public void desibleDesitions(int i){    //disable all the desitiond that made and send
		if(i==0)
			timeB.setDisable(true);
		else if(i==1)
			txtMaxVisitsPerDay.setDisable(true);
		else if(i==2)
			txtVisitOrderGap.setDisable(true);
		else if(i==3) 
			txtSingleFamilyWithOrder.setDisable(true);
		else if(i==4)
			txtSubscription.setDisable(true);
		else if(i==5)
			txtGroupWithOrder.setDisable(true);
		else if(i==6)
			txtAdvancePay.setDisable(true);
		else if(i==7)
			txtGroupNoOrder.setDisable(true);
	}
	
	/**
	 * return to menu
	 * @param event
	 * @throws Exception
	 */
	public void getCancelBtn(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlControl/ParkManagerControl.fxml").openStream());
		ParkMamagerControl ParkMamagerControl = loader.getController();				
		ParkMamagerControl.loadParkMamager(st);


		Scene scene = new Scene(root);			
		primaryStage.setTitle("Edit Parameters");

		primaryStage.setScene(scene);		
		primaryStage.show();
	}


}
