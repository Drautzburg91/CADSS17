package cad.cep.db;


import java.sql.*;
import java.text.SimpleDateFormat;

public class WeatherRepositoryImpl implements WeatherRepository {
	
	
	
    Connection defaultConnection = null;

    public WeatherRepositoryImpl(Connection databaseConnection)
    {
        this.defaultConnection = databaseConnection;
    }

    public WeatherRepositoryImpl(){
        this.defaultConnection = connectDefaultConnection();
}
    
    
    //Default Connection
    private Connection connectDefaultConnection()
    {
        Connection connection = null;

        try {
//            connection 	 =	DriverManager.getConnection(
//                    		"jdbc:mysql://"
//                            + System.getenv("CAD_DB_HOST") + ":"
//                            + "3306" + "/"
//                            + "WeatherSystemDatabase",
//                    System.getenv("CAD_DB_USER"),
//                    System.getenv("CAD_DB_PASSWORD"));
            
           connection 	 =	DriverManager.getConnection("jdbc:mysql://" + "cad-weather-api-database.cc1ormgk3ins.us-east-2.rds.amazonaws.com" + ":" + "3306" + "/" + "WeatherSystemDatabase", "CAD_MASTER_JDBC", "HTWGhtwg");
        }
        catch (SQLException e)
        {
            System.out.println("Connection failed!:\n" + e.getMessage());
        }

        if (connection != null)
        {
            System.out.println("SUCCESS! Connection available");
        }
        else
        {
            System.out.println("FAILURE! Connection couldn't be established");
        }

        return connection;

    }

    //Check Connection
    public String checkDatabaseConnection()
    {
        boolean isConnected = false;

        try {
            isConnected = defaultConnection.isValid(5);

        } catch (SQLException | NullPointerException e)
        {
            return ("Connection failed: " + e.getMessage());
        }

        if(isConnected)
        {
            return "Connection works";
        }

        return "Connection failed";

}
    
	//Set Connection
	public void setConnection(java.sql.Connection  database_connection)
	{
		this.defaultConnection = database_connection;
	}
	
	
//Get Connection
	public java.sql.Connection getConnection()
	{
		return this.defaultConnection;
	}
    

  //DefaultWeather Table
  		public String insertDefaultWeather(int WeatherID, String main, String description, String icon_referenz)
  		{
  			//Check Incoming Data
  				if(WeatherID <= 0)
  				{
  					return ("Attribute WeatherID - primary Key - expected value bigger than 0");
  				}
  				
  				if(main == null)
  				{
  					return ("Attribute main - not null - max length: 50");
  				}	
  				
  				if(description == null)
  				{
  					return ("Attribute description - not null - max length: 100");
  				}
  				
  				if(icon_referenz.length() > 10)
  				{
  					return ("Attribute icon_referenz -  max length: 10");
  				}
  			
  			//MYSQL Query 
  				Statement statement;
  				String query;
  				int resultSet;
  				try 
  				{
  					statement = (Statement) defaultConnection.createStatement();	
  					
  					query = "INSERT INTO " + 
  							"DefaultWeather "+																	//Table Name
  							"(WeatherID, main, description, icon_referenz)"+
  					        "VALUES"+
  							"("+WeatherID+",'"+main+"','"+description+"','"+icon_referenz+"');";						//Values for Insert
  					
  					
  					resultSet  = statement.executeUpdate(query);
  				
  					
  				} catch (SQLException e) 
  				{
  					//System.out.println("Insert Operation failed:\n" + e.getMessage());						//Internal
  					return("Insert Operation failed:\n" + e.getMessage());
  				}
  				
  	
  		
  			
  			return "Successfull Insert Operation - Table DefaultWeather - Rowamount: "+ resultSet;
  		}
  		
  		
  	//City Table
  		public String insertCity(int ZipCode, String cityName, Double logitude, Double latitude, int cityID)
  		{
  			//Check Incoming Data
  				if(ZipCode <= 0)
  				{
  					return ("Attribute ZipCode - primary Key - expected value bigger than 0");
  				}
  				
  				
  			
  			//MYSQL Query 
  				Statement statement;
  				String query;
  				int resultSet;
  				try 
  				{
  					statement = (Statement) defaultConnection.createStatement();	
  					
  					query = "INSERT INTO " + 
  							"City "+																	//Table Name
  							"(ZipCode, cityName , longitude, latitude, cityID)"+
  					        "VALUES"+
  							"("+ZipCode+",'"+cityName+"',"+logitude+","+latitude+","+cityID+");";		//Values for Insert
  					
  					
  					resultSet  = statement.executeUpdate(query);
  				
  					
  				} catch (SQLException e) 
  				{
  					//System.out.println("Insert Operation failed:\n" + e.getMessage());						//Internal
  					return("Insert Operation failed:\n" + e.getMessage());
  				}
  				

  			return "Successfull Insert Operation - Table City - Rowamount: "+ resultSet;
  		}
  	

  	//User Table
  		public String insertUser(String E_Mail, String userName, String passwort, int mainLocation)
  		{
  			//Check Incoming Data
  				if(E_Mail == null)
  				{
  					return ("Attribute E_Mail - primary Key - expected value");
  				}
  				
  				if(userName == null  || userName.length() > 100)
  				{
  					return ("Attribute countryCode - not null - max length: 100");
  				}	
  				
  				if(passwort == null)
  				{
  					return ("Attribute passwort - not null - max length: 100");
  				}
  				
  			
  			//MYSQL Query 
  				Statement statement;
  				String query;
  				int resultSet;
  				try 
  				{
  					statement = (Statement) defaultConnection.createStatement();	
  					
  					query = "INSERT INTO " + 
  							"User "+																	//Table Name
  							"(E_Mail, userName, passwort , mainLocation)"+
  					        "VALUES"+
  							"('"+E_Mail+"','"+userName+"','"+passwort+"',"+mainLocation+");";				//Values for Insert
  					
  					
  					resultSet  = statement.executeUpdate(query);
  				
  					
  				} catch (SQLException e) 
  				{
  					//System.out.println("Insert Operation failed:\n" + e.getMessage());						//Internal
  					return("Insert Operation failed:\n" + e.getMessage());
  				}
  				

  		
  			
  			return "Successfull Insert Operation - Table User - Rowamount: " + resultSet;
  		}
  	
  				
  	//Subscribe Table
  		public String insertSubcribe(String E_Mail, int ZipCode)
  			{
  				//Check Incoming Data
  					if(E_Mail == null)
  					{
  						return ("Attribute E_Mail - primary Key & foreign Key - expected value");
  					}
  					
  					if(ZipCode <= 0)
  					{
  						return ("Attribute ZipCode - primary Key & foreign Key - expected value");
  					}	

  				//MYSQL Query 
  					Statement statement;
  					String query;
  					int resultSet;
  					try 
  					{
  						statement = (Statement) defaultConnection.createStatement();	
  						
  						query = "INSERT INTO " + 
  								"Subscribe "+																	//Table Name
  								"(E_Mail, ZipCode, activationDate)"+
  						        "VALUES "+
  								"('"+E_Mail+"',"+ZipCode+","+"NOW()"+");";				//Values for Insert
  						
  						
  						resultSet  = statement.executeUpdate(query);
  					
  						
  					} catch (SQLException e) 
  					{
  						//System.out.println("Insert Operation failed:\n" + e.getMessage());						//Internal
  						return("Insert Operation failed:\n" + e.getMessage());
  					}
  					
  	
  			
  				
  				return "Successfull Insert Operation - Table Subscribe - Rowamount: "+ resultSet;
  			}
  			
  			
  	//Weather Table
  		public String insertWeather(Timestamp TimeStamp, int ZipCode, int WeatherID, 
  											double main_temp, double main_pressure, double main_humidity, double main_tempMin, double main_tempMax, double main_seaLevel, double main_grndLevel,
  											String wind_direction, double wind_speed, double wind_deg, String clouds_desc, double rain_3h, double snow_3h, 
  											Timestamp sys_sunset, Timestamp sys_sunrise
  											)
  			{
  				
  				//Variables (temp)
  					double temp_main_temp			= -1.0;
  					double temp_main_pressure 		= -1.0;
  					double temp_main_humidity 		= -1.0;
  					double temp_main_tempMin 		= -1.0;
  					double temp_main_tempMax 		= -1.0;
  					double temp_main_seaLevel 		= -1.0;
  					double temp_main_grndLevel		= -1.0;
  					
  					String temp_wind_direction 		= "";
  					double temp_wind_speed 			= -1.0;
  					double temp_wind_deg			= -1.0;
  					String temp_clouds_desc			= "";
  					double temp_rain_3h				= -1.0;
  					double temp_snow_3h				= -1.0;
  					
  					Timestamp	temp_sys_sunset = null ;
  					Timestamp	temp_sys_sunrise = null;
  					
  	
  				
  				//Check Incoming Data
  					if(TimeStamp == null)
  					{
  						return ("Attribute TimeStamp - primary Key  - expected value");
  					}
  					
  					if(ZipCode <= 0)
  					{
  						return ("Attribute ZipCode - primary Key & foreign Key - expected value");
  					}	
  					
  					if(WeatherID <= 0)
  					{
  						return ("Attribute WeatherID - primary Key & foreign Key - expected value");
  					}
  					
  					//Use Temp Variables
  					if(main_temp > 0) {temp_main_temp =	main_temp;}
  					if(main_pressure > 0) {temp_main_pressure =	main_pressure;}
  					if(main_humidity > 0) {temp_main_humidity =	main_humidity;}
  					if(main_tempMin  > 0) {temp_main_tempMin =	main_tempMin;}
  					if(main_tempMax  > 0) {temp_main_tempMax =	main_tempMax;}
  					if(main_seaLevel > 0) {temp_main_seaLevel =	main_seaLevel;}
  					if(main_grndLevel > 0) {temp_main_grndLevel =	main_grndLevel;}
  						
  					if(wind_direction != null ) {temp_wind_direction =	wind_direction;}
  					if(wind_speed > 0) {temp_wind_speed =	wind_speed;}
  					if(wind_deg > 0) {temp_wind_deg =	wind_deg;}
  					if(clouds_desc != null ) {temp_clouds_desc = clouds_desc;}
  					if(rain_3h > 0) {temp_rain_3h =	rain_3h;}
  					if(snow_3h > 0) {temp_snow_3h =	snow_3h;}
  					
  					if(sys_sunset != null ) {temp_sys_sunset = sys_sunset;}
  					if(sys_sunrise != null ) {temp_sys_sunrise = sys_sunrise;}
  					

  				//MYSQL Query 
  					Statement statement;
  					String query;
  					int resultSet;
  					try 
  					{
  						statement = (Statement) defaultConnection.createStatement();	
  						
  						query = "INSERT INTO " + 
  								"Weather "+																	//Table Name
  						        "VALUES "+
  								"('"		+ TimeStamp
  								+ "',"	+ ZipCode
  								+ ","	+ WeatherID
  								+ ","	+ temp_main_temp 
  								+ ","	+ temp_main_pressure 
  								+ ","	+ temp_main_humidity 
  								+ ","	+ temp_main_tempMin 
  								+ ","	+ temp_main_tempMax 
  								+ ","	+ temp_main_seaLevel 
  								+ "," 	+ temp_main_grndLevel 
  								+ ",'"  + temp_wind_direction 
  								+ "',"	+ temp_wind_speed 
  								+ ","	+ temp_wind_deg 
  								+ ",'"	+ temp_clouds_desc 
  								+ "',"  + temp_rain_3h
  								+ "," 	+ temp_snow_3h
  								+ "," 	+ temp_sys_sunset
  								+ "," 	+ temp_sys_sunrise 				
  								+ ");";				//Values for Insert
  						
  						
  						resultSet  = statement.executeUpdate(query);
  					
  						
  					} catch (SQLException e) 
  					{
  						//System.out.println("Insert Operation failed:\n" + e.getMessage());						//Internal
  						return("Insert Operation failed:\n" + e.getMessage());
  					}
  					
  	
  			
  				
  				return "Successfull Insert Operation - Table Weather - Rowamount:" + resultSet;
  			}
  	//Alert
  			public String insertAlert(int ZipCode, Timestamp timestamp, String title, String code, String message)
  			{
  				//Check Incoming Data
  				if(ZipCode <= 0)
  				{
  					return ("Attribute ZipCode - primary Key - expected value bigger than 0");
  				}
  					
  					if(timestamp == null)
  					{
  						return ("Attribute timestamp - primary Key - expected value");
  					}
  					
  					
  					
  				
  				//MYSQL Query 
  					Statement statement;
  					String query;
  					int resultSet;
  					try 
  					{
  						statement = (Statement) defaultConnection.createStatement();	
  						
  						query = "INSERT INTO " + 
  								"Alert "+																	//Table Name
  								"(ZipCode, Timestamp, title, code, message) "+
  						        "VALUES"+
  								"("+ZipCode+",'"+timestamp+"','"+title+"','"+code+"','"+message+"');";	//Values for Insert
  						
  						
  						resultSet  = statement.executeUpdate(query);
  					
  						
  					} catch (SQLException e) 
  					{
  						//System.out.println("Insert Operation failed:\n" + e.getMessage());						//Internal
  						return("Insert Operation failed:\n" + e.getMessage());
  					}
  					
  		
  			
  				
  				return "Successfull Insert Operation - Table Alert - Rowamount: " + resultSet;
  			}				
  			
  //SELECT MYSQL Operations
  	//SELECT User Data by E-Mail (pk E-Mail)
  		public ResultSet selectUserByMail(String user_E_Mail)
  			{
  				
  				//Variables
  				ResultSet temp_resultSet = null;
  				
  				
  				//Check Incoming Data
  					if(user_E_Mail == null)
  					{
  						return null;
  					}
  					
  					
  				//MYSQL Query 
  					Statement statement;
  					String query;
  					
  					try 
  					{
  						statement = (Statement) defaultConnection.createStatement();	
  						
  						query = "SELECT * " + 
  								"FROM User "+																	//Table Name
  								"WHERE E_Mail = '" + user_E_Mail + "' ;" ;
  												
  						temp_resultSet  = statement.executeQuery(query);
  					
  						
  					} catch (SQLException e) 
  					{
  						System.out.println("Insert Operation failed:\n" + e.getMessage());						//Internal
  						//return("Insert Operation failed:\n" + e.getMessage());
  					}

  				return temp_resultSet;
  			}
  			

  	//SELECT City Data by ZipCode (pk ZipCode)
  		public ResultSet selectCityByZipCode(int city_ZipCode)
  			{
  				
  				//Variables	
  				ResultSet temp_resultSet = null;
  				
  				
  				//Check Incoming Data
  					if(city_ZipCode <= 0)
  					{
  						return null;
  					}
  					
  					
  				//MYSQL Query 
  					Statement statement;
  					String query;
  					
  					try 
  					{
  						statement = (Statement) defaultConnection.createStatement();	
  						
  						query = "SELECT * " + 
  								"FROM City "+																	//Table Name
  								"WHERE ZipCode = '" + city_ZipCode + "' ;" ;
  												
  						temp_resultSet  = statement.executeQuery(query);
  					
  						
  					} catch (SQLException e) 
  					{
  						System.out.println("Insert Operation failed:\n" + e.getMessage());						//Internal
  						//return("Insert Operation failed:\n" + e.getMessage());
  					}

  				return temp_resultSet;
  			}			

  			
  	//SELECT DefaultWeather (pk WeatherID)
  		public ResultSet selectDefaultWeatherByID(int defaultWeather_ID)
  		{
  			
  			//Variables	
  			ResultSet temp_resultSet = null;
  			
  			
  			//Check Incoming Data
  				if(defaultWeather_ID <= 0)
  				{
  					return null;
  				}
  				
  				
  			//MYSQL Query 
  				Statement statement;
  				String query;
  				
  				try 
  				{
  					statement = (Statement) defaultConnection.createStatement();	
  					
  					query = "SELECT * " + 
  							"FROM DefaultWeather "+																	//Table Name
  							"WHERE WeatherID = '" + defaultWeather_ID + "' ;" ;
  											
  					temp_resultSet  = statement.executeQuery(query);
  				
  					
  				} catch (SQLException e) 
  				{
  					System.out.println("Insert Operation failed:\n" + e.getMessage());						//Internal
  			
  				}

  			return temp_resultSet;
  		}			
  		

  	//SELECT All Weather City Data by ZipCode
  		public ResultSet selectAllWeatherByCityZipCode(int city_ZipCode)
  			{
  				
  				//Variables	
  				ResultSet temp_resultSet = null;
  				
  				
  				//Check Incoming Data
  					if(city_ZipCode <= 0)
  					{
  						return null;
  					}
  					
  					
  				//MYSQL Query 
  					Statement statement;
  					String query;
  					
  					try 
  					{
  						statement = (Statement) defaultConnection.createStatement();	
  						
  						query = "SELECT * " + 
  								"FROM Weather "+																	//Table Name
  								"WHERE ZipCode = '" + city_ZipCode + "' ;" ;
  												
  						temp_resultSet  = statement.executeQuery(query);
  					
  						
  					} catch (SQLException e) 
  					{
  						System.out.println("Insert Operation failed:\n" + e.getMessage());						//Internal
  						//return("Insert Operation failed:\n" + e.getMessage());
  					}

  				return temp_resultSet;
  			}			
  		
  			
  	//SELECT Weather City Data for a certain day (default NOW())
  		public ResultSet selectWeatherByCityAndDay(int city_ZipCode, Timestamp weatherDay)
  			{
  				
  				//Variables	
  				ResultSet temp_resultSet	= null;
  				String temp_weatherDay 		= " DATE_FORMAT(NOW(),'%Y %m %d')"; 								//NOW equals Oracle SYSDATE		
  				
  				
  				//Check Incoming Data
  					if(city_ZipCode <= 0)
  					{
  						return null;
  					}
  					
  					if(weatherDay != null)
  					{
  						temp_weatherDay = new SimpleDateFormat("yyyy-MM-dd").format(weatherDay)  ;
  					}
  					
  				//MYSQL Query 
  					Statement statement;
  					String query;
  					
  					try 
  					{
  						statement = (Statement) defaultConnection.createStatement();	
  						
  						query = "SELECT * " + 
  								"FROM Weather "+																	//Table Name
  								"WHERE ZipCode = '" + city_ZipCode + "' and " +
  								"DATE(Timestamp) = '" + temp_weatherDay +"';" ;
  												
  						temp_resultSet  = statement.executeQuery(query);
  					
  						
  					} catch (SQLException e) 
  					{
  						System.out.println("Insert Operation failed:\n" + e.getMessage());								//Internal
  						//return("Insert Operation failed:\n" + e.getMessage());
  					}

  				return temp_resultSet;
  				
  			
  			}	
  		
  		
  	//SELECT Weather City Data for a certain time period
  		public ResultSet selectWeatherByCityAndTimePeriod(int city_ZipCode, Timestamp fromDate, Timestamp toDate)
  		{
  			//Variables	
  			ResultSet temp_resultSet	= null;
  			
  			String temp_fromDate  = new SimpleDateFormat("yyyy-MM-dd").format(fromDate);
  			String temp_toDate	  = new SimpleDateFormat("yyyy-MM-dd").format(toDate);
  			
  			//Check Incoming Data
  				if(city_ZipCode <= 0)
  				{
  					return null;
  				}
  				
  				
  			//MYSQL Query 
  				Statement statement;
  				String query;
  				
  				try 
  				{
  					statement = (Statement) defaultConnection.createStatement();	
  					
  					query = "SELECT * " + 
  							"FROM Weather "+																	//Table Name
  							"WHERE ZipCode = " + city_ZipCode + " and " +
  							"DATE(Timestamp) BETWEEN '" + temp_fromDate + "' and '" + temp_toDate + "' ;" ;
  											
  					temp_resultSet  = statement.executeQuery(query);
  				
  					
  				} catch (SQLException e) 
  				{
  					System.out.println("Insert Operation failed:\n" + e.getMessage());								//Internal
  					//return("Insert Operation failed:\n" + e.getMessage());
  				}

  			return temp_resultSet;

  		}
  		
  		
  	//SELECT Subscribed City(s) by User 
  		public ResultSet selectSubscribeByUser(String user_E_Mail)
  			{
  				
  				//Variables	
  				ResultSet temp_resultSet	= null;
  	
  				
  				//Check Incoming Data		
  					if(user_E_Mail == null)
  					{
  						return null;
  					}
  					
  				//MYSQL Query 
  					Statement statement;
  					String query;
  					
  					try 
  					{
  						statement = (Statement) defaultConnection.createStatement();	
  						
  						query = "SELECT ZipCode " + 
  								"FROM Subscribe "+																	//Table Name
  								"WHERE E_Mail = '" + user_E_Mail + "';" ;
  												
  						temp_resultSet  = statement.executeQuery(query);
  					
  						
  					} catch (SQLException e) 
  					{
  						System.out.println("Insert Operation failed:\n" + e.getMessage());								//Internal
  						//return("Insert Operation failed:\n" + e.getMessage());
  					}

  				return temp_resultSet;
  			}
  			
  			
  	//SELECT Subscribed Users by City
  		public ResultSet select_SubscribeByCity(int ZipCode)
  		{
  			
  			//Variables	
  			ResultSet temp_resultSet	= null;

  			
  			//Check Incoming Data		
  				if(ZipCode <= 0)
  				{
  					return null;
  				}
  				
  			//MYSQL Query 
  				Statement statement;
  				String query;
  				
  				try 
  				{
  					statement = (Statement) defaultConnection.createStatement();	
  					
  					query = "SELECT E_Mail " + 
  							"FROM Subscribe "+																	//Table Name
  							"WHERE ZipCode = '" + ZipCode + "';" ;
  											
  					temp_resultSet  = statement.executeQuery(query);
  				
  					
  				} catch (SQLException e) 
  				{
  					System.out.println("Insert Operation failed:\n" + e.getMessage());							//Internal
  					//return("Insert Operation failed:\n" + e.getMessage());
  				}

  			return temp_resultSet;
  		}			
  	
  		
  	//SELECT User Passwort by EMail for LogIN
  		public ResultSet select_UserPwByEMail(String E_Mail)
  		{
  			//Variables	
  			ResultSet temp_resultSet	= null;

  			
  			//Check Incoming Data		
  				if(E_Mail == null)
  				{
  					return null;
  				}
  				
  			//MYSQL Query 
  				Statement statement;
  				String query;
  				
  				try 
  				{
  					statement = (Statement) defaultConnection.createStatement();	
  					
  					query = "SELECT passwort " + 
  							"FROM User "+																	//Table Name
  							"WHERE E_Mail = '" + E_Mail + "';" ;
  											
  					temp_resultSet  = statement.executeQuery(query);
  				
  					
  				} catch (SQLException e) 
  				{
  					System.out.println("Insert Operation failed:\n" + e.getMessage());							//Internal
  					//return("Insert Operation failed:\n" + e.getMessage());
  				}

  			return temp_resultSet;
  			
  		}


		@Override
		public ResultSet selectSubscribeByCity(int ZipCode) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ResultSet selectUserPwByEMail(String E_Mail) {
			// TODO Auto-generated method stub
			return null;
		}
  		
			
			


}
