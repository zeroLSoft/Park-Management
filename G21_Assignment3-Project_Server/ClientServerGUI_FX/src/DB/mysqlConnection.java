package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class mysqlConnection 
{
	private static Connection conn = null;
	
	public static void main(String[] args) 
	{
		getConnection();
	}
	
	@SuppressWarnings("deprecation")
	public static Connection getConnection()
	{
		try 
		{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("Driver definition succeed");
        }
		catch (Exception ex)
		{
        	 System.out.println("Driver definition failed");
		}
        
        try 
        {
        	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gonature?serverTimezone=IST","root","zerolight");
        	
            System.out.println("SQL connection succeed");
            return conn;
     	}
        catch (SQLException ex)
        {
        	System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        
        return null;
   	}
	
	
}


