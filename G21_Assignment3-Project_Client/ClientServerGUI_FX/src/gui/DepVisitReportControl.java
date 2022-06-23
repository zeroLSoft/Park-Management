package gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;

/**
 * 
 * class that making visit window
 *
 */
public class DepVisitReportControl implements Initializable {
	ArrayList<String> res1;
	ArrayList<String> res2;
	String[] time= {"08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00"};
	ArrayList<String> timeStrings=new ArrayList<String>();
	ArrayList<String> subavg=new ArrayList<String>();
	ArrayList<String> singleavg=new ArrayList<String>();
	ArrayList<String> groupbavg=new ArrayList<String>();
	ArrayList<String> single=new ArrayList<String>();
	ArrayList<String> group=new ArrayList<String>();
	ArrayList<String> sub=new ArrayList<String>();
	String singlep="",groupp="",subp="";
	@FXML
	private LineChart<String,Integer> chart;
	@FXML
	private CategoryAxis x;
	@FXML
	private NumberAxis y;
	@FXML
	private Button btnClose=null;
	@FXML
	private TextField txtLocation;
	@FXML
	private Button btnSave=null;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	/**
	 * method that prepering chart of average stay time for chosen date
	 * getting info from DB, and caclueting the time between out and in
	 * @param resultToSend
	 */
	public void loadToDvisitRep(ArrayList<ArrayList<String>> resultToSend) {
		XYChart.Series s1=new XYChart.Series<>();
		XYChart.Series s2=new XYChart.Series<>();
		XYChart.Series s3=new XYChart.Series<>();
		String[] Array=new String[20];

		s1.setName("Group");
		s2.setName("Single");
		s3.setName("Subscription");

		String timeEnter = null,stayOut = null,str,str2;
		long timeStay;
		res1=resultToSend.get(0);      //parameters that we chose
		res2=resultToSend.get(1);      //DB data that was collected after we chose parameters 
		int flag=0;
		Array =res1.get(0).split("\\s+");
		chart.setTitle("Visit chart for park "+Array[0]);

		for(int i=0;i<12;i++) {
			sub.add("");
			group.add("");
			single.add("");
		}

		for(int i=3;i<res2.size();i++) {
			flag=0;
			// get info from DB
			Array =res2.get(i).split("\\s+");
			if(((Array.length==19)&&(Array[1].equals(res1.get(0)))&&(Array[13].equals(res1.get(1)))&&(Array[17].equals(res1.get(2))))&&!(Array[9].equals("0"))) {
				timeEnter=Array[3];
				stayOut=Array[9];
				flag=1;
			}
			else if(Array.length==9&&Array[0].equals(res1.get(0))&&Array[6].equals(res1.get(1))&&Array[7].equals(res1.get(2))&&!(Array[5].equals("0"))) {
				timeEnter=Array[4];
				stayOut=Array[5];
				flag=1;
			}

			// if found info we need, process the stay time
			if(flag==1) {
				SimpleDateFormat format = new SimpleDateFormat("HH:mm");
				String[] hhmmss = timeEnter.split(":");
				int hour=Integer.parseInt(hhmmss[0]);
				int j=0;
				while(j<(hour-8)) j++;
				j++;
				Date date1 = null,date2 = null;
				try {
					date1 = format.parse(timeEnter);
					date2 = format.parse(stayOut);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				timeStay = date2.getTime() - date1.getTime(); 

				long differenceMinutes = timeStay / (60 * 1000) % 60;
				long differenceHours = timeStay / (60 * 60 * 1000) % 24;

				str=String.format("%02d:%02d", differenceHours,differenceMinutes);

				//add stay time that processed to his kind: single, group, subscription
				if(Array.length==9) {
					if(Array[8].equals("no")) {
						str2=single.get(j);
						str2=str2+str;
						str2=str2+" ";
						single.set(j, str2);
					}
					else if(Array[8].equals("yes")){
						str2=sub.get(j);
						str2=str2+str;
						str2=str2+" ";
						sub.set(j, str2);
					}
				}
				else {
					if(Array[10].equals("yes")) {
						str2=group.get(j);
						str2=str2+str;
						str2=str2+" ";

						group.set(j, str2);
					}
					else if(Array[14].equals("yes")) {
						str2=sub.get(j);
						str2=str2+str;
						str2=str2+" ";
						sub.set(j, str2);
					}
					else {
						str2=single.get(j);
						str2=str2+str;
						str2=str2+" ";
						single.set(j, str2);
					}
				}
			}

		}
		//calculat average time for every time : 08:00...... 20:00 and making report string
		for(int s=0;s<12;s++) {
			if(!(single.get(s).equals(""))) {
				str=calculateAverageOfTime(single.get(s));
				if(s>=1)
					singlep=singlep+"From "+time[s-1]+" to "+time[s]+" the average stay time was: "+str+"\n"; 
				singleavg.add(str);
			}
			else { 
				if(s>=1)
					singlep=singlep+"From "+time[s-1]+" to "+time[s]+" the average stay time was: 00:00\n"; 
				singleavg.add("00:00");
			}

			if(!(group.get(s).equals(""))) {
				str=calculateAverageOfTime(group.get(s));
				if(s>=1)
					groupp=groupp+"From "+time[s-1]+" to "+time[s]+" the average stay time was: "+str+"\n";
				groupbavg.add(str);
			}
			else {
				if(s>=1)
					groupp=groupp+"From "+time[s-1]+" to "+time[s]+" the average stay time was: 00:00\n"; 
				groupbavg.add("00:00");
			}

			if(!(sub.get(s).equals(""))) {
				str=calculateAverageOfTime(sub.get(s));
				if(s>=1)
					subp=subp+"From "+time[s-1]+" to "+time[s]+" the average stay time was: "+str+"\n";
				subavg.add(str);
			}
			else {
				if(s>=1)
					subp=subp+"From "+time[s-1]+" to "+time[s]+" the average stay time was: 00:00\n"; 
				subavg.add("00:00");
			}

		}
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		String[] temp;
		String floats = null;
		//set the lineChart with data
		for(int s=0;s<12;s++) {
			temp=groupbavg.get(s).split(":");
			floats=temp[0]+"."+temp[1];
			Float.parseFloat(floats);
			s1.getData().add(new XYChart.Data<>(time[s],Float.parseFloat(floats)));
			temp=singleavg.get(s).split(":");
			floats=temp[0]+"."+temp[1];
			Float.parseFloat(floats);
			s2.getData().add(new XYChart.Data<>(time[s],Float.parseFloat(floats)));	
			temp=subavg.get(s).split(":");
			floats=temp[0]+"."+temp[1];
			Float.parseFloat(floats);
			s3.getData().add(new XYChart.Data<>(time[s],Float.parseFloat(floats)));
		}
		chart.getData().addAll(s1,s2,s3);
	}

	//method that calculating avarege time
	/**
	 * method that calculating avarege time
	 * @param timeInHHmmss full time
	 * @return avg time
	 */
	public static String calculateAverageOfTime(String timeInHHmmss) {
		String[] split = timeInHHmmss.split(" ");
		long seconds = 0;
		for (String timestr : split) {
			String[] hhmmss = timestr.split(":");
			seconds += Integer.valueOf(hhmmss[0]) * 60 * 60;
			seconds += Integer.valueOf(hhmmss[1]) * 60;

		}
		seconds /= split.length;
		long hh = seconds / 60 / 60;
		long mm = (seconds / 60) % 60;

		return String.format("%02d:%02d", hh,mm);
	}

	public void getCloselBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); 
	}


	/**
	 * method that saving the prepered report to pc
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
			String report="Visit Report for park "+res1.get(0)+" at "+res1.get(1)+"/"+res1.get(2)+"\n\n";
			w.write(report);
			w.write("Single visits summary\n\n");
			w.write(singlep);
			w.write("\nGroup visits summary\n\n");
			w.write(groupp);
			w.write("\nSubscription visits summary\n\n");
			w.write(subp);

			w.close();
			JOptionPane.showMessageDialog(null,"Report saved successfully ","error",JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Input is wrong, try again","error",JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
