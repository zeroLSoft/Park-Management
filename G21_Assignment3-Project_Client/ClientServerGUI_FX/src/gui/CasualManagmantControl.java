package gui;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * 
 * class for casual entering at park
 *
 */
public class CasualManagmantControl  implements Initializable{
	ArrayList<String> res;
	ArrayList<String> info;
	double sub;
	int res2;

	@FXML
	private Label IDtxt;
	@FXML
	private Label casual;
	@FXML
	private Label subs;
	@FXML
	private Label Ordertxt;
	@FXML
	private CheckBox yes;
	@FXML
	private CheckBox no;
	@FXML
	private TextField idtxt;
	@FXML
	private Button btnCancel=null;
	@FXML
	private Button btnEnter=null;

	/**
	 * method that calculating space and and start the interface with data needed
	 * @param s park worker parameters
	 */
	public void loadWorker(ArrayList<String> s) {
		this.res=s;
		int order=0, casualc=0;
		ArrayList<String> parameters=new ArrayList<String>();
		parameters.add("ManagmantControl");
		parameters.add("get visit info");
		parameters.add("SELECT * FROM park WHERE name = '"+res.get(5)+"'");
		parameters.add("SELECT * FROM discount WHERE ParkName = '"+res.get(5)+"'");         //get all the current visitors inside with park and discount info
		parameters.add("SELECT * FROM gonature.casual WHERE parkName = '" + s.get(5) + "' AND outTime = '0'");
		parameters.add("SELECT * FROM gonature.order WHERE parkName = '" + res.get(5) + "' AND outTime = '0' AND casualGroup = 'yes'");
		ClientUI.chat.accept(parameters);
		info=ChatClient.result;
        String[] str; 
		for(int i=1;i<info.size();i++) {             //Calculate space in park
			str=info.get(i).split("\\s+");
			System.out.println(info.get(i));
			if(str.length==19)
				order=order+Integer.parseInt(str[4]);
			else if(str.length==9)
				casualc++;
		}
		
		System.out.println(casualc);
		String[] temp=info.get(1).split("\\s+");

		sub = 15.0 - (15.0 * Double.parseDouble(temp[2]));
		System.out.println(sub);
		String[] arr=info.get(0).split("\\s+");
		if(Integer.parseInt(arr[3])>(casualc)) {       //if there is space
			Random r = new Random();
			int low = 10000;
			int high = 99999;
			res2 = r.nextInt(high - low) + low;          //generate order id

			IDtxt.setTextFill(Color.GREEN);
			IDtxt.setText("yes");                         
			subs.setTextFill(Color.BLACK);                         //preper the interface
			subs.setText(String.valueOf(sub)+" ILS");
			casual.setTextFill(Color.BLACK);
			casual.setText("15 ILS");
			Ordertxt.setTextFill(Color.BLACK);
			Ordertxt.setText(String.valueOf(res2));

		}
		else {
			IDtxt.setTextFill(Color.RED);
			IDtxt.setText("no");
			btnEnter.setDisable(true);
			yes.setDisable(true);
			no.setDisable(true);
			idtxt.setDisable(true);
			JOptionPane.showMessageDialog(null,"No space in park, all options disabled","error",JOptionPane.INFORMATION_MESSAGE);
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	/**
	 * get id and look if order exist and let order carrier enter enter with updated
	 * @param event
	 * @throws Exception
	 */
	
	public void getEnterBtn(ActionEvent event) throws Exception {
		String id=idtxt.getText(),time;
		ArrayList<String> parameters=new ArrayList<String>();
		if(id.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null,"You need to enter ID","error",JOptionPane.INFORMATION_MESSAGE);
		}
		else if(yes.isSelected() && !no.isSelected()) {           //if subscriber
			String query;
			query = "SELECT * FROM subinst WHERE Subscriber_ID = '"+id+"'";
			parameters.add("ManagmantControl");                //find the subscriber
			parameters.add("select from table");
			parameters.add(query);
			ClientUI.chat.accept(parameters);

			if(!ChatClient.result.get(0).equals("Not found")) {
				parameters.clear();
				String[] temp=info.get(0).split("\\s+");
				parameters.add("ManagmantControl");
				parameters.add("set to table");                //insert data     
				query="INSERT INTO `gonature`.`casual` (`parkName`,`cost`, `ID`, `date`, `enterTime`, `outTime`, `month`, `year`, `subscriber`) VALUES (?,?,?,?,?,'0',?,?,'yes');"; //2
				parameters.add(query);
				parameters.add(temp[0]);
				parameters.add(String.valueOf(sub));
				parameters.add(id);
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");               //insert data 
				Date date = new Date();                                                                 //all
				temp=formatter.format(date).split("\\s+");                                              //the
				parameters.add(temp[0]);                                                                //data
				String[] tem2=temp[1].split(":");                                                       //for                                          
				time=tem2[0]+":"+tem2[1];                                                               //casual with sunscription
				parameters.add(time);
				tem2=temp[0].split("/");
				parameters.add(tem2[1]);
				parameters.add(tem2[2]);
				ClientUI.chat.accept(parameters);
				ArrayList<String> res2=new ArrayList<String>();
				res2.add("park managmant");
				res2.add("set to table");
				String[] arr=info.get(0).split("\\s+");
				String visitors = String.valueOf(Integer.parseInt(arr[4])+1);
				res2.add("UPDATE gonature.park " + " SET currentAmountOfVisitors = '"+visitors+"' WHERE name  = '"+arr[0]+"'");
				JOptionPane.showMessageDialog(null,"Done! Costumer may enter","ready!",JOptionPane.INFORMATION_MESSAGE);
				ClientUI.chat.accept(res2);
			}
			else
				JOptionPane.showMessageDialog(null,"No such Subscription exists","error",JOptionPane.INFORMATION_MESSAGE);
		}
		else if(!yes.isSelected() && no.isSelected()) {       //just casual
			String query;
			parameters.clear();
			System.out.println("HERRE");
			String[] temp=info.get(0).split("\\s+");
			parameters.add("ManagmantControl");
			parameters.add("set to table");
			query="INSERT INTO `gonature`.`casual` (`parkName`,`cost`, `ID`, `date`, `enterTime`, `outTime`, `month`, `year`, `subscriber`) VALUES (?,?,?,?,?,'0',?,?,'no');"; //2
			parameters.add(query);
			parameters.add(temp[0]);
			parameters.add("15.0");
			parameters.add(id);
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");               //insert data   
			Date date = new Date();                                                                   //all
			temp=formatter.format(date).split("\\s+");                                              //the
			parameters.add(temp[0]);                                                                //data
			String[] tem2=temp[1].split(":");                                                       //for   
			time=tem2[0]+":"+tem2[1];                                                               //casual
			parameters.add(time);
			tem2=temp[0].split("/");
			parameters.add(tem2[1]);
			parameters.add(tem2[2]);
			ClientUI.chat.accept(parameters);
			ArrayList<String> res2=new ArrayList<String>();
			res2.add("park managmant");
			res2.add("set to table");
			String[] arr=info.get(0).split("\\s+");
			String visitors = String.valueOf(Integer.parseInt(arr[4])+1);
			res2.add("UPDATE gonature.park " + " SET currentAmountOfVisitors = '"+visitors+"' WHERE name  = '"+arr[0]+"'");
			ClientUI.chat.accept(res2);
			JOptionPane.showMessageDialog(null,"All done, the visitor may enter","error",JOptionPane.INFORMATION_MESSAGE);
		}
		else
			JOptionPane.showMessageDialog(null,"Both or none checkBox's are selected, select only one","error",JOptionPane.INFORMATION_MESSAGE);
		loadWorker(res);
	}

/**
 * return to employee manu
 * @param event
 * @throws Exception
 */
	public void getReturnBtn(ActionEvent event) throws Exception {
		
		 FXMLLoader loader = new FXMLLoader();
			((Node)event.getSource()).getScene().getWindow().hide(); 
			Stage primaryStage = new Stage();
			Pane root = loader.load(getClass().getResource("/fxmlControl/casualEmployeeMenu.fxml").openStream());
			casualEmployeeMenuController casualEmployeeMenuController = loader.getController();				
			casualEmployeeMenuController.loadWorker(res);
			
			
			Scene scene = new Scene(root);			
			primaryStage.setTitle("Park employer managmant");

			primaryStage.setScene(scene);		
			primaryStage.show();
	}

}
