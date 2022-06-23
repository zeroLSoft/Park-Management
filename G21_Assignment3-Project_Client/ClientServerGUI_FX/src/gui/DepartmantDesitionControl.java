package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import Logic.Desition;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * 
 * class that preparing decisions from parks for approve 
 *
 */
public class DepartmantDesitionControl implements Initializable{
	ObservableList<Desition> DesitionList = FXCollections.observableArrayList();
	ArrayList<String> s;
	private ArrayList<String> result;
	@FXML
	private TableView<Desition> DesitionsTable;
	@FXML
	private TableColumn<Desition,String> parkCol;
	@FXML
	private TableColumn<Desition,String> descriptionCol;
	@FXML
	private TableColumn<Desition,String> beforCol;
	@FXML
	private TableColumn<Desition,String> afterCol;
	@FXML
	private TableColumn<Desition,ComboBox> statusCol;
	@FXML
	private Button btnSave=null;
	@FXML
	private Button btnCancel=null;
	

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			ArrayList<String> parameters=new ArrayList<String>();
			String query;
			parameters.add("ManagmantControl");
			parameters.add("get desition");
			query="SELECT * FROM gonature.desition WHERE status = 'pending' ORDER BY ABS(parkName)";
			parameters.add(query);
			ClientUI.chat.accept(parameters);
			result=ChatClient.result;
			if(!result.get(0).equals("empty"))
			getTable();
			else
			JOptionPane.showMessageDialog(null,"There is no desitions for now, you may return","error",JOptionPane.INFORMATION_MESSAGE);
		}
		/**
		 * method that Initialize decisions table
		 * Initialize data from DB to table
		 * 
		 */
		public void getTable(){
			String[] Array = new String[10];
			String str;
			int length=result.size();
			
			//
			//Initialize data from DB to table
			//
			for(int i=0;i<length;i++) {
				Array =result.get(i).split("\\s+");      //in each i there us a sentence of info
				DesitionList.add(new Desition(Array[0],Array[1],Array[2],Array[3],FXCollections.observableArrayList("pending","decline","approve")));
				}
		
			parkCol.setCellValueFactory(new PropertyValueFactory<Desition,String>("park"));
			descriptionCol.setCellValueFactory(new PropertyValueFactory<Desition,String>("description"));
			beforCol.setCellValueFactory(new PropertyValueFactory<Desition,String>("befor"));
			afterCol.setCellValueFactory(new PropertyValueFactory<Desition,String>("after"));
			statusCol.setCellValueFactory(new PropertyValueFactory<Desition,ComboBox>("combo"));
			
			DesitionsTable.setItems(DesitionList);
			
			
		}
		/**
		 * method that saving department decisions
		 * checking decisions in every line on table
		 * update decisions statues
		 * @param event
		 * @throws Exception
		 */
		public void getSaveBtn(ActionEvent event) throws Exception {
			String[] Array = new String[10];
			ObservableList<Desition> dataRow=FXCollections.observableArrayList();
			ArrayList<String> parameters =new ArrayList<String>();
			ArrayList<String> temp =new ArrayList<String>();
			String query, str="";
			int i=0;
			//
			//ask if user want to save
			//
			String[] options = new String[] {"Yes", "Cancel"};
			int option =  JOptionPane.showOptionDialog(null, "Are you sure you want to save change's? this action is unfixble", "Warning!", 
			      JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
			      null, options, options[0]);

			if (option != JOptionPane.CLOSED_OPTION) {
			  System.out.println(options[option]);
			} else {
			  System.out.println("No option selected");
			}
			//
			//if yes start updating DB
			//
			if(options[option].equals("Yes")) {
			parameters.add("ManagmantControl");
			parameters.add("set approvle");
			query="UPDATE desition " + " SET status = ? WHERE parkName = ? AND changeDescription = ?";
			parameters.add(query);
			temp.add("stop");
			//
			//check decisions in every line on table
			//
			for(Desition bean : DesitionList) {
				if((bean.getCombo().getSelectionModel().getSelectedItem())!=null) {
					//
					//update decisions statues
					//
				if(bean.getCombo().getSelectionModel().getSelectedItem().equals("approve")||bean.getCombo().getSelectionModel().getSelectedItem().equals("decline")) {
					str=(String) bean.getCombo().getSelectionModel().getSelectedItem()+" "+bean.getPark()+" "+bean.getDescription();
					parameters.add(str);
					bean.getCombo().setDisable(true);
					//
					//if approved update states of selected park
					//
					if(bean.getCombo().getSelectionModel().getSelectedItem().equals("approve")) {
						if(bean.getDescription().equals("Visit_time_change")) {
							temp.add("UPDATE gonature.park " + " SET visitTime = '"+bean.getAfter()+"' WHERE name = '"+bean.getPark()+"'");
						}
						else if(bean.getDescription().equals("Maximum_visitors_per_hour_change")) {
							temp.add("UPDATE gonature.park " + " SET maxVisitors = '"+bean.getAfter()+"' WHERE name = '"+bean.getPark()+"'");
						}
						else if(bean.getDescription().equals("Visit-time_gap_change")) {
							temp.add("UPDATE gonature.park " + " SET visitOrderGap = '"+bean.getAfter()+"' WHERE name = '"+bean.getPark()+"'");
						}
						else if(bean.getDescription().equals("Discount_change_-_Single_or_family_With_Order")) {
							temp.add("UPDATE gonature.discount " + " SET singleFamilyWithOrder = '"+bean.getAfter()+"' WHERE parkName = '"+bean.getPark()+"'");
						}
						else if(bean.getDescription().equals("\"Discount_change_-_Subscription")) {
							temp.add("UPDATE gonature.discount " + " SET subscription = '"+bean.getAfter()+"' WHERE parkName = '"+bean.getPark()+"'");
						}
						else if(bean.getDescription().equals("Discount_change_-_Group_with_order")) {
							temp.add("UPDATE gonature.discount " + " SET groupWithOrder = '"+bean.getAfter()+"' WHERE parkName = '"+bean.getPark()+"'");
						}
						else if(bean.getDescription().equals("Discount_change_-_Advance_pay")) {
							temp.add("UPDATE gonature.discount " + " SET advancePay = '"+bean.getAfter()+"' WHERE parkName = '"+bean.getPark()+"'");
						}
						else if(bean.getDescription().equals("Discount_change_-_Group_with_no_order")) {
							temp.add("UPDATE gonature.discount " + " SET groupNoOrder = '"+bean.getAfter()+"' WHERE parkName = '"+bean.getPark()+"'");
						}
					}
				}
				i++;
			}
			}
			//
			//add to arraylist the approved list of states
			//
			for(i=0;i<temp.size();i++) {
				parameters.add(temp.get(i));
			}
			ClientUI.chat.accept(parameters);
			   JOptionPane.showMessageDialog(null,"Desitions was updated successfully","error",JOptionPane.INFORMATION_MESSAGE);
			
		}
		}
		
		public void getCancelBtn(ActionEvent event) throws Exception {
			 FXMLLoader loader = new FXMLLoader();
				((Node)event.getSource()).getScene().getWindow().hide(); 
				Stage primaryStage = new Stage();
				Pane root = loader.load(getClass().getResource("/fxmlControl/DepartmentMenu.fxml").openStream());
	
				Scene scene = new Scene(root);			
				primaryStage.setTitle("Department Managment");

				primaryStage.setScene(scene);		
				primaryStage.show();
		}


}
