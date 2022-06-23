package gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * 
 * class that making canceletion report
 *
 */
public class CanceledReportControl implements Initializable {
	ArrayList<String> res1;
	ArrayList<String> res2;
	String cancelled="Cancelled orders:\n\n",notUsed="Confirmed but not used orders:\n\n",report="";
	int can=0,not=0;

	@FXML
	private TextArea txtReport;
	@FXML
	private Button btnClose=null;
	@FXML
	private Button btnSave=null;
	@FXML
	private TextField txtLocation;

	/**
	 * method that calculating cancellation report
	 * select data from order table
	 * get cancelled orders and preper string
	 * check if order canceled   by subscriber or instructor
	 * get orders that was confirmed but not used and preper string
	 * preper report and post on screen
	 * @param resultToSend arralist of arralist input from departman manager
	 */
	public void loadToDvisitRep(ArrayList<ArrayList<String>> resultToSend) {
		String[] Array=new String[20],time= new String[20],time2=new String[20],temp=new String[10];
		//
		//list of parameters that was chosen
		// 
		res1=resultToSend.get(0); 
		/*
		//list of data was chosen according res1
		 */
		res2=resultToSend.get(1);      
		int flag=0;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String data = df.format(new Date());

		time=data.split("/");
		int i=3;

		Array =res2.get(i).split("\\s+");
		//
		//select data from order table
		//
		while(Array.length==19) {             
			time2=Array[2].split("/");
			flag=0;
			//
			//get cancelled orders and preper string
			//
			if(((Array[6].equals("canceled"))&&Array[1].equals(res1.get(0))&&Array[13].equals(res1.get(1)))&&Array[17].equals(res1.get(2))) {
				can++;
				if(Array[10].equals("no"))         //check if order canceled   by subscriber or instructor
					cancelled=cancelled+"Order Number: "+Array[0]+" cancelled by Subscriptor, ID number: "+Array[15]+"\n";
				else cancelled=cancelled+"Order Number: "+Array[0]+" cancelled by instracture, ID number: "+Array[11]+"\n";

			}
			//
			//get orders that was confirmed but not used and preper string
			//
			else if((Array[6].equals("approved"))&&Array[1].equals(res1.get(0))&&Array[13].equals(res1.get(1))&&Array[17].equals(res1.get(2))) {
				if(Integer.parseInt(time2[2])>Integer.parseInt(time[2])) flag=1;
				else if(Integer.parseInt(time2[1])>Integer.parseInt(time[1])) flag=1;
				else if(Integer.parseInt(time2[0])>Integer.parseInt(time[0])) flag=1;

				if(flag==1) {
					not++;
					if(Array[10].equals("no")) notUsed=notUsed+"Order Number: "+Array[0]+" was confirmed by Subscriptor, ID number: "+Array[15]+" but not used\n";
					else notUsed=notUsed+"Order Number: "+Array[0]+" was confirmed by instracture\n";
				}
			}

			i++;
			Array =res2.get(i).split("\\s+");
		}

		//
		//preper report and post on screen
		//
		report= "Monthly Summary canceletion Report of park "+res1.get(0)+" "+res1.get(1)+"/"+res1.get(2)+"\n\n\n";
		report=report+"The amount of orders that was cancelled is: "+can+"\n\n";
		report=report+"The amount of orders that was confirmed but not used is: "+not+"\n\n";
		report=report+"detailed Summary:\n\n";

		txtReport.appendText(report);
		txtReport.appendText(cancelled);
		txtReport.appendText(notUsed);

	}

/**
 * 
 * @param event     hide the window
 * @throws Exception     
 */
	public void getCloselBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); 
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	}

	/**
	 * 
	 * @param event saving report to pc 
	 * @throws Exception
	 */
	public void getSaveBtn(ActionEvent event) throws Exception {
		String location=txtLocation.getText();
		try {
			File statText = new File(location);
			FileOutputStream is = new FileOutputStream(statText);
			OutputStreamWriter osw = new OutputStreamWriter(is);    
			Writer w = new BufferedWriter(osw);
			String report="Visit Report for park "+res1.get(0)+" at "+res1.get(1)+"/"+res1.get(2)+"\n\n";
			w.write(txtReport.getText());
			w.close();
			JOptionPane.showMessageDialog(null,"Report saved successfully ","error",JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Input is wrong, try again","error",JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
