package gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;

import Logic.Casual;
import Logic.GroupOrder;
import Logic.Report;
import Logic.SubscriptionOrder;
import Logic.UsageReport;
import Logic.VisitReport;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * 
 * class that prepering usage report for park mannager and departmant
 *
 */
public class UsageReportControl implements Initializable {
	private ArrayList<String> s;
	private ArrayList<String> result;
	private ArrayList<String> reportF= new ArrayList<String>() ;
	int month;
	int year;
	String[][] reportTable= new String[35][10];
	ObservableList<String> monthC=FXCollections.observableArrayList("01","02","03","04","05","06","07","08","09","10","11","12");
	ObservableList<String> yearC = FXCollections.observableArrayList();
	String report,Dsub,Dgroup,Dsingle,Ndays;
	int[] daysInMonthA;
	int daysInMonth,maxVisitorstForDay;
	String y;
	String m;


	@FXML
	private Button btnSend=null;
	@FXML
	private Button btnSave=null;
	@FXML
	private Button btnReturn=null;
	@FXML
	private Button btnMakeRport=null;

	@FXML
	private TextField txtLocation;

	@FXML
	private TableView<UsageReport> tableview;
	@FXML
	private TableColumn<UsageReport,String> IndexCol;
	@FXML
	private TableColumn<UsageReport,String> DayCol;
	@FXML
	private TableColumn<UsageReport,String> CameCol;
	@FXML
	private TableColumn<UsageReport,String> CapCol;
	@FXML
	private TableColumn<UsageReport,String> UsageCol;


	@FXML
	private ComboBox date;
	@FXML
	private ComboBox yearc;
	/**
	 * get info, it will be "dep" if we coming from instractor
	 * @param s1 meneger info
	 */
	public void loadParkMamager(ArrayList<String> s1) {
		this.s=s1;
		if(s.get(0).equals("dep"))
			btnSend.setVisible(false);

	}

	/**
	 * initialize string and counter for reports and profit
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		yearC.add("2019");
		yearC.add("2020");
		yearC.add("2021");
		report="";
		Ndays="The days the park was not full:\n\n";
		date.setItems(monthC);
		yearc.setItems(yearC);                                      //initialize string and counter for reports and usage
		for(int i=0;i<35;i++)
			for(int j=0;j<10;j++)
				reportTable[i][j]="0";
	}

	/**
	 * that getting info for calculation, if its  we getting prepered report 
	 * @param event
	 * @throws Exception
	 */
	public void getMakeReportBtn(ActionEvent event) throws Exception {
		ArrayList<String> parameters=new ArrayList<String>();
		String[] Array = null,time = null;
		int h;
		y=(String) yearc.getSelectionModel().getSelectedItem();
		m=(String) date.getSelectionModel().getSelectedItem();

		if(y==null||m==null){
			JOptionPane.showMessageDialog(null,"You didnt chose year or month, make sure both selected","error",JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			if(!s.get(0).equals("dep")) {         //if the park manger entered then get info from table to calculate
				parameters.add("ManagmantControl");
				parameters.add("get visit info");
				String quary="SELECT * FROM gonature.park WHERE name = '"+s.get(5)+"'";
				parameters.add(quary );
				quary="SELECT * FROM gonature.order WHERE parkName = '"+s.get(5)+"' AND status = 'used' AND month = '" +m+"' AND year = '" +y+"'";
				parameters.add(quary );
				quary="SELECT * FROM gonature.casual WHERE parkName = '"+s.get(5)+"' AND month = '" +m+"' AND year = '" +y+"'";
				parameters.add(quary);
				ClientUI.chat.accept(parameters);
				result=ChatClient.result;
				if(result.size()==1) {
					JOptionPane.showMessageDialog(null,"No data for the selected date","error",JOptionPane.INFORMATION_MESSAGE);
				}
				else {                                          //if department entered just get time and get info from report table
					Array =result.get(0).split("\\s+");
					time=Array[1].split(":");
					h=Integer.parseInt(time[0]);
					maxVisitorstForDay=(int) (((12/h)*Integer.parseInt(Array[2]))*0.95);
					YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(y),Integer.parseInt(m));
					daysInMonth = yearMonthObject.lengthOfMonth(); 

					daysInMonthA=new int[daysInMonth];
					getSingleTable();
				}
			}
			else 
				SetDepTable();	
		}

	}


	/**method for department report
	 * Initialize table with the info from park meneger
	 * 
	 */
	public void SetDepTable() {
		ObservableList<UsageReport> reportList = FXCollections.observableArrayList();
		ArrayList<String> parameters=new ArrayList<String>();
		String quary="SELECT * FROM gonature.usagereport WHERE parkName = '"+s.get(1)+"' AND month = '" +m+"' AND year = '" +y+"' ORDER BY ABS(num)";
		parameters.add("ManagmantControl");           //get info for report from profit report table
		parameters.add("get visit info");
		parameters.add(quary);
		ClientUI.chat.accept(parameters);
		int len=ChatClient.result.size();
		String[] Array = null;
			IndexCol.setCellValueFactory(new PropertyValueFactory<UsageReport,String>("Index"));
			DayCol.setCellValueFactory(new PropertyValueFactory<UsageReport,String>("DayNum"));            //Initialize table
			CameCol.setCellValueFactory(new PropertyValueFactory<UsageReport,String>("TotalVisitor"));
			CapCol.setCellValueFactory(new PropertyValueFactory<UsageReport,String>("Capacity"));
			UsageCol.setCellValueFactory(new PropertyValueFactory<UsageReport,String>("usage"));
			try {        
			for(int i=0;i<len;i++) {                         //extract info from string
				Array =ChatClient.result.get(i).split("\\s+");
				reportList.add(new UsageReport(Array[1],Array[2],Array[3],Array[4],Array[5]));
				Ndays=Ndays+ "Date : "+m+"/"+(i+1)+"/"+y+"number of visitors was "+Array[3]+" out of "+Array[4]+" usage in %:"+Array[5]+"\n";
			}
			tableview.setItems(reportList);
			report= "Monthly Usage Report of park "+s.get(1)+" "+m+"/"+y+"\n\n\n";
			report=report+"Usage Summary:\n\n";
			report=report+"Total days the park was not full: "+len+"\n\n\n";
			report=report+"Detailed Summary:\n\n";
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,"No data for the selected date","error",JOptionPane.INFORMATION_MESSAGE);
		}
	}


	/**
	 * method for park manager report
	 * calculating for every kind of visitors total usage
	 * and preperingg string to save to pc
	 */
	public void getSingleTable(){
		String[] Array = null;
		String[] DSplit;
		String str="";
		int totalFullDays =0;
		int length=result.size();
		ObservableList<UsageReport> reportList = FXCollections.observableArrayList();

		for(int i=0;i<length;i++) {
			Array =result.get(i).split("\\s+");      //in each i there us a sentence of info
			if(Array.length==19){                     //with order
				DSplit=Array[2].split("/");
				daysInMonthA[Integer.parseInt(DSplit[0])-1]=daysInMonthA[Integer.parseInt(DSplit[0])-1]+Integer.parseInt(Array[4]);
			}
			else if(Array.length==9) {  // only casual
				DSplit=Array[3].split("/");
				daysInMonthA[Integer.parseInt(DSplit[0])-1]=daysInMonthA[Integer.parseInt(DSplit[0])-1]+1;
			}
		}

		IndexCol.setCellValueFactory(new PropertyValueFactory<UsageReport,String>("Index"));
		DayCol.setCellValueFactory(new PropertyValueFactory<UsageReport,String>("DayNum"));
		CameCol.setCellValueFactory(new PropertyValueFactory<UsageReport,String>("TotalVisitor"));
		CapCol.setCellValueFactory(new PropertyValueFactory<UsageReport,String>("Capacity"));
		UsageCol.setCellValueFactory(new PropertyValueFactory<UsageReport,String>("usage"));


		int j=0;
		float temp;
		for(int i=0;i<daysInMonth;i++) {  //Usage calculating and makinbg strings
			if(!(daysInMonthA[i]>=maxVisitorstForDay)) {
				totalFullDays++;
				j++;
				temp=(float)daysInMonthA[i]/(float)maxVisitorstForDay;
				reportTable[i][6]=daysInMonthA[i]+"/"+maxVisitorstForDay;
				reportList.add(new UsageReport(String.valueOf(j),String.valueOf(i+1),String.valueOf(daysInMonthA[i]),String.valueOf(maxVisitorstForDay),String.format("%.2f", temp)));
				reportF.add(String.valueOf(j)+" "+String.valueOf(i+1)+" "+String.valueOf(daysInMonthA[i])+" "+String.valueOf(maxVisitorstForDay)+" "+String.format("%.2f", temp));
				Ndays=Ndays+ "Date : "+m+"/"+(i+1)+"/"+y+"number of visitors was "+daysInMonthA[i]+" out of "+maxVisitorstForDay+"\n";
			}
		}


		tableview.setItems(reportList);

		Ndays=Ndays+"\n";;
		report= "Monthly Usage Report of park "+s.get(5)+" "+m+"/"+y+"\n\n\n";
		report=report+"Usage Summary:\n\n";
		report=report+"Total days the park was not full: "+totalFullDays+"\n\n\n";


	}
	/**
	 * saving the report that was made into pc
	 * @param event
	 * @throws Exception
	 */
	public void getSaveBtn(ActionEvent event) throws Exception {
		String location=txtLocation.getText();
		try {
			File statText = new File(location);
			FileOutputStream is = new FileOutputStream(statText);
			OutputStreamWriter osw = new OutputStreamWriter(is);    
			Writer w = new BufferedWriter(osw);
			w.write(report);
			w.write(Ndays);

			w.close();
			JOptionPane.showMessageDialog(null,"Report saved successfully ","error",JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Input is wrong, try again","error",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	/**
	 * sending report to department
	 * checking if we can send
	 * and then if we approve the report going to db
	 * @param event
	 * @throws Exception
	 */
	public void getSendBtn(ActionEvent event) throws Exception {
		ArrayList<String> parameters=new ArrayList<String>();
		ArrayList<String> parameters2=new ArrayList<String>();
		int flag=1;
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month = localDate.getMonthValue();
		int year = localDate.getYear();
		int day=localDate.getDayOfMonth();

		//check if we can send by checking if its the end of the month and id the manger want to send at all
		if(year==Integer.parseInt(y)&&month==Integer.parseInt(m)) {
			if(day!=daysInMonth) {
				JOptionPane.showMessageDialog(null,"You can send report about the selected date on "+month+"/"+daysInMonth+"/"+year+"","error",JOptionPane.INFORMATION_MESSAGE);
				flag=0;
			}
		}
		else if(year<Integer.parseInt(y)||year==Integer.parseInt(y)&&month<Integer.parseInt(m)) {
			if(day!=daysInMonth) {
				JOptionPane.showMessageDialog(null,"you cant make this report,its too far ahead ","error",JOptionPane.INFORMATION_MESSAGE);
				flag=0;
			}
		}
		if(flag==1) {
			String[] options = new String[] {"Yes", "Cancel"};
			int option =  JOptionPane.showOptionDialog(null, "are you sure you want to send this report?", "Warning!", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
					null, options, options[0]);

			if (option != JOptionPane.CLOSED_OPTION) {
				System.out.println(options[option]);
			} else {
				System.out.println("No option selected");
			}
			if(options[option].equals("Yes")) {
				//check if report already send
				parameters2.add("ManagmantControl");
				parameters2.add("get visit info");
				String quary="SELECT * FROM gonature.usagereport WHERE parkName = '"+s.get(5)+"' AND month = '" +m+"' AND year = '" +y+"'";
				parameters2.add(quary);
				ClientUI.chat.accept(parameters2);
				String[] Array = null;
				if(ChatClient.result.size()==0) {           //if not preper to send and send
					parameters.add("ManagmantControl");
					parameters.add("add to dep report");
					parameters.add("2");
					parameters.add(s.get(5));
					parameters.add(m);
					parameters.add(y);
					quary="INSERT INTO `gonature`.`usagereport` (`num`, `line`, `parkName`, `month`, `year`) VALUES (?,?,?,?,?);"; 

					parameters.add(quary);
					for(int i=0;i<reportF.size();i++)
						parameters.add(reportF.get(i));

					ClientUI.chat.accept(parameters);
					JOptionPane.showMessageDialog(null,"Report was send successfully, you may return or make more reports ","error",JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null,"a report for this month was made already, wait for next month","error",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}


	public void getReturnBtn(ActionEvent event) throws Exception {
		if(s.get(0).equals("dep")) 
			((Node)event.getSource()).getScene().getWindow().hide(); 
		else {
			FXMLLoader loader = new FXMLLoader();
			((Node)event.getSource()).getScene().getWindow().hide(); 
			Stage primaryStage = new Stage();
			Pane root = loader.load(getClass().getResource("/fxmlControl/ParkReportMenu.fxml").openStream());
			ReportMenuControl ReportMenuControl = loader.getController();				
			ReportMenuControl.loadParkMamager(s);


			Scene scene = new Scene(root);			
			primaryStage.setTitle("Report Managment");

			primaryStage.setScene(scene);		
			primaryStage.show();
		}
	}
}
