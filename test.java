import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;


public class test {
	
	 public static void main(String[] args) 
	 {
		 CAD_WEATHER_SYSTEM_DATABASE_API testObj = new CAD_WEATHER_SYSTEM_DATABASE_API(null,null);    
		 
		 ResultSet rs;
		 
		//String testResult = testObj.insert_User("kim.desouza@web.de", "Kim De Souza", "htwg", 78467);
		 rs = testObj.select_SubscribeByCity(78467);
		 
		 try {
			while (rs.next()) 
			{
				 
			     //Retrieve by column name
			     String e_mail;
				 e_mail = rs.getString("E_Mail");
				

			     //Display values             
			     System.out.println("Answear: " + e_mail);
			 }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
	 }
	 
	 
	 //Use Test Query to Check your connection
	    private static void runTestQuery(java.sql.Connection connection) {
	        Statement statement = null;
	        try {

	            System.out.println("Creating statement...");
	            statement = (Statement) connection.createStatement();
	            String sql;
	            sql = "SELECT * FROM City";
	            ResultSet rs = statement.executeQuery(sql);

	            //STEP 5: Extract data from result set
	            while (rs.next()) {
	                //Retrieve by column name
	                int zipCode = rs.getInt("ZipCode");
	                String countryCode = rs.getString("countryCode");

	                //Display values
	                System.out.print("ZipCode: " + zipCode);
	                System.out.print("CountyCode: " + countryCode);
	            }
	            //STEP 6: Clean-up environment
	            rs.close();
	            statement.close();
	            connection.close();
	        } catch (SQLException se) {
	            //Handle errors for JDBC
	            se.printStackTrace();
	        } catch (Exception e) {
	            //Handle errors for Class.forName
	            e.printStackTrace();
	        } finally {
	            //finally block used to close resources
	            try {
	                if (statement != null)
	                    statement.close();
	            } catch (SQLException se2) {
	            }// nothing we can do
	            try {
	                if (connection != null)
	                    connection.close();
	            } catch (SQLException se) {
	                se.printStackTrace();
	            }//end finally try
	        }//end try
	    }

}


