package ServerControl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DB.mysqlConnection;

public class QuaryManagmant {
	/**
	 * getting flag to see which method to use to extract data
	 * @param parameters arraylist of flags and data to or from DB
	 * 
	 * @return data from DM
	 */
	public static ArrayList<String> checkUserControl(ArrayList<String> parameters){
		ArrayList<String> result = new ArrayList<String>();
		String flage=parameters.get(1);
		//each flag representing diffrent kind of flag to get or set from tables
		if(flage.equals("set desition")) {           
			setDesition(parameters);
			result.add("done");
			return result;
		}
		else if(flage.equals("get visit info")) {
			return (result = getVisitInfo(parameters));
		}
		else if(flage.equals("add to dep report")) {
			setReport(parameters);
			result.add("done");
			return result;
		}
		else if(flage.equals("get desition")) {

			return (result = getDesitions(parameters));
		}
		else if(flage.equals("get edit parameters")) {
			return (result= getEditParameters(parameters));
		}
		else if(flage.equals("select for report")) {
			return (result = getForReport(parameters));
		}
		else if(flage.equals("set approvle")) {
			setApprovle(parameters);
			result.add("done");
			return result;
		}
		else if(flage.equals("get dep report")) {
			return (result = getDepReport(parameters));
		}
		if(flage.equals("select from table")) {
			return (result= getFromTable(parameters));
		}
		else if(flage.equals("set to table")) {
			setToTable(parameters);
			return result;
		}
		else if(flage.equals("set entry")) {
			setEntry(parameters);
			result.add("done");
			return result;
		}
		else
			return result;

	}
	
	//each method taking or getting in its on way, but most are the same, either its making list strings or just arraylist with index representing column 
	public static ArrayList<String> getFromTable(ArrayList<String> parameters)
	{
		Connection conn = mysqlConnection.getConnection();
		ArrayList<String> result = new ArrayList<String>();
		int Cnum;
		try{
			PreparedStatement ps = conn.prepareStatement(parameters.get(2));

			for(int i = 1; i <= parameters.size()-3; i++)
				ps.setString(i, parameters.get(i+2));

			ResultSet rs = ps.executeQuery();
			Cnum = rs.getMetaData().getColumnCount();
			if(rs.next()) {
				do {
					for( int j=1; j<=Cnum; j++)
						result.add(rs.getString(j));

				} while(rs.next());
			}
			else result.add("Not found");
			return result;
		}
		catch (SQLException e){
			System.err.println("problem at getFromTable");
			e.printStackTrace();
		}

		return null;
	}

	public static void setToTable(ArrayList<String> parameters){
		Connection conn = mysqlConnection.getConnection();

		try{
			PreparedStatement ps = conn.prepareStatement(parameters.get(2));
			for(int i = 1; i <= parameters.size()-3; i++)
				ps.setString(i, parameters.get(i+2));

			ps.executeUpdate();
		}
		catch (SQLException e)
		{
			System.err.println("problem at setToTable");
			e.printStackTrace();
		}
	}

	public static void setEntry(ArrayList<String> parameters){
		Connection conn = mysqlConnection.getConnection();
		PreparedStatement ps;
		try{
			if(parameters.get(0).equals("option1")) {
				ps = conn.prepareStatement(parameters.get(2));
				ps.executeUpdate();
				ps = conn.prepareStatement(parameters.get(3));
				ps.setString(1, parameters.get(4));
				ps.executeUpdate();
				ps = conn.prepareStatement(parameters.get(5));
				ps.setString(1, parameters.get(6));
				ps = conn.prepareStatement(parameters.get(7));
				ps.setString(1, parameters.get(8));
				ps.executeUpdate();
			}
			else if(parameters.get(0).equals("option2")){
				System.out.println(parameters.get(2));
				ps = conn.prepareStatement(parameters.get(2));
				ps.executeUpdate();
				ps = conn.prepareStatement(parameters.get(3));
				System.out.println(parameters.get(3));
				ps.setString(1, parameters.get(4));
				System.out.println(parameters.get(4));
				ps.executeUpdate();
			}	
			else if(parameters.get(0).equals("option3")){
				ps = conn.prepareStatement(parameters.get(2));
				ps.executeUpdate();
			}
			
		}
		catch (SQLException e)
		{
			System.err.println("problem at setToTable");
			e.printStackTrace();
		}
	}


	public static void setDesition(ArrayList<String> parameters){
		Connection conn = mysqlConnection.getConnection();
		int num = 1;

		try {
			ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM gonature.desition  ORDER BY ABS(number)");
			if(rs.next()) {
				while(rs.next()) {}
				rs.previous();
				num=rs.getInt(1);
			}

			System.out.println(num);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for(int i=3;i<(parameters.size());i++) {
			try{
				String[] str =new String[10];
				PreparedStatement ps = conn.prepareStatement(parameters.get(2));

				str=parameters.get(i).split("\\s+");
				num++;
				ps.setInt(1,num);
				ps.setString(2,str[0]);
				ps.setString(3,str[1]);
				ps.setString(4,str[2]);
				ps.setString(5,str[3]);
				ps.setString(6,str[4]);

				ps.executeUpdate();
			}
			catch (SQLException e)
			{
				System.err.println("problem at setToTable");
				e.printStackTrace();
			}
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static ArrayList<String> getVisitInfo(ArrayList<String> parameters){
		Connection conn = mysqlConnection.getConnection();
		ArrayList<String> result = new ArrayList<String>();
		int Cnum;
		String str="";
		try 
		{
			for(int j=2;j<(parameters.size());j++) {
				ResultSet rs = conn.createStatement().executeQuery(parameters.get(j));
				Cnum = rs.getMetaData().getColumnCount();
				while(rs.next()) {
					for(int i=0;i<Cnum;i++) {
						str= str + rs.getString(i+1);
						str = str +" ";
					}
					//System.out.println(str);
					result.add(str);
					str="";
				}
			}
		} 
		catch (SQLException e) 
		{
			System.err.println("there is a problem with getVisitInfo");
			e.printStackTrace();
		}
		return result;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void setReport(ArrayList<String> parameters) {
		Connection conn = mysqlConnection.getConnection();
		int num = 1;

		try {
			ResultSet rs = null;
			if(parameters.get(2).equals("1"))
				rs = conn.createStatement().executeQuery("SELECT * FROM gonature.visitreport  ORDER BY ABS(num)");
			if(parameters.get(2).equals("2"))
				rs = conn.createStatement().executeQuery("SELECT * FROM gonature.usagereport  ORDER BY ABS(num)");
			if(parameters.get(2).equals("3"))
				rs = conn.createStatement().executeQuery("SELECT * FROM gonature.profitreport  ORDER BY ABS(num)");
			if(rs.next()) {
				while(rs.next()) {
					num=rs.getInt(1);
				}
			}


		} catch (SQLException e) {
			e.printStackTrace();
		}

		for(int i=7;i<(parameters.size());i++) {
			try{
				String[] str =new String[10];
				PreparedStatement ps = conn.prepareStatement(parameters.get(6));
				num++;
				ps.setInt(1,num);
				ps.setString(2,parameters.get(i));
				ps.setString(3,parameters.get(3));
				ps.setString(4,parameters.get(4));
				ps.setString(5,parameters.get(5));

				ps.executeUpdate();
			}
			catch (SQLException e)
			{
				System.err.println("problem at setToTable");
				e.printStackTrace();
			}
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static ArrayList<String> getDesitions(ArrayList<String> parameters){
		Connection conn = mysqlConnection.getConnection();
		ArrayList<String> result = new ArrayList<String>();
		String str="";
		int Cnum;
		try {
			ResultSet rs = conn.createStatement().executeQuery(parameters.get(2));
			if(rs.next()) {
				do {
					Cnum = rs.getMetaData().getColumnCount();
					for(int i=1;i<Cnum;i++) {
						str = str + rs.getString(i+1);
						str = str +" ";
					}
					System.out.println(str);
					result.add(str);
					str="";
				}while(rs.next());
			}
			else result.add("empty");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static ArrayList<String> getForReport(ArrayList<String> parameters){
		Connection conn = mysqlConnection.getConnection();
		ArrayList<String> result = new ArrayList<String>();
		int Cnum;
		String str="";

		try 
		{
			for(int j=2;j<(parameters.size());j++) {
				ResultSet rs = conn.createStatement().executeQuery(parameters.get(j));
				Cnum = rs.getMetaData().getColumnCount();
				while(rs.next()) {
					for(int i=0;i<Cnum;i++) {
						str= str + rs.getString(i+1);
						str = str +" ";
					}
					result.add(str);
					//System.out.println(str);
					str="";
				}
			}
		} 
		catch (SQLException e) 
		{
			System.err.println("there is a problem with getForReport");
			e.printStackTrace();
		}


		return result;
	}
	/////////////////////////////////////////////////////////////////////////////////////
	public static void setApprovle(ArrayList<String> parameters){
		Connection conn = mysqlConnection.getConnection();
		String[] Array=new String[10];
		int j = 0;
		for(int i=3; !parameters.get(i).equals("stop") ;i++)
			try{
				PreparedStatement ps = conn.prepareStatement(parameters.get(2));
				Array =parameters.get(i).split("\\s+");
				ps.setString(1,Array[0]);
				ps.setString(2,Array[1]);
				ps.setString(3,Array[2]);
				ps.executeUpdate();
				j=i;
			}
		catch (SQLException e)
		{
			System.err.println("problem at setToTable");
			e.printStackTrace();
		}
		j=j+2;
		for(;j<parameters.size() ;j++)
			try{
				int rs = conn.createStatement().executeUpdate(parameters.get(j));
			}  catch (SQLException e)
		{
				System.err.println("problem at setToTable");
				e.printStackTrace();
		}

	}
	/////////////////////////////////////////////////////////////////////////////////////////
	public static ArrayList<String> getDepReport(ArrayList<String> parameters){
		Connection conn = mysqlConnection.getConnection();
		ArrayList<String> result = new ArrayList<String>();
		try 
		{
			ResultSet rs = conn.createStatement().executeQuery(parameters.get(2));
			while(rs.next()) { 
				result.add(rs.getString(2));
			}

		} 
		catch (SQLException e) 
		{
			System.err.println("there is a problem with getDepReport");
			e.printStackTrace();
		}
		return result;
	}
	public static ArrayList<String> getEditParameters(ArrayList<String> parameters)
	{
		Connection conn = mysqlConnection.getConnection();
		ArrayList<String> result = new ArrayList<String>();
		String str="";
		int Cnum;
		try{
			PreparedStatement ps1 = conn.prepareStatement(parameters.get(2));

			ps1.setString(1, parameters.get(3));
			ResultSet rs = ps1.executeQuery();
			Cnum = rs.getMetaData().getColumnCount();
			if(rs.next()) {
				do {
					for(int i=0;i<Cnum;i++) {
						str= str + rs.getString(i+1);
						str = str +" ";
					}
					System.out.println(str);
					result.add(str);
					str="";
				} while(rs.next());
			}


			PreparedStatement ps2 = conn.prepareStatement(parameters.get(4));

			ps2.setString(1, parameters.get(5));
			ResultSet rs1 = ps2.executeQuery();
			Cnum = rs1.getMetaData().getColumnCount();
			if(rs1.next()) {
				do {
					for(int i=0;i<Cnum;i++) {
						str= str + rs1.getString(i+1);
						str = str +" ";
					}
					System.out.println(str);
					result.add(str);
					str="";
				} while(rs1.next());
			}
			return result;
		}
		catch (SQLException e){
			System.err.println("problem at getFromTable");
			e.printStackTrace();
		}

		return null;
	}
}
