import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.PreparedStatement;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;


/*
 * ###################################################################################################################################
 * -----------------------------------------------------------------------------------------------------------------------------------
 * CAD SS2017 - Weather API MOM System - MYSQL Database Operation API 
 * ----------------------------------------------------------------------------------------------------------------------------------- 
 * Amazon Web Services - Aurora MYSQL
 * 	Database Operation User: 	CAD_MASTER_JDBC
 * 	PW:							HTWGhtwg
 * 
 * Default AWS Connection
 * 	Writer Endpoint IP:			database4cad.cc1ormgk3ins.us-east-2.rds.amazonaws.com	
 * 	Reader Endpoint IP:			database4cad-us-east-2b.cc1ormgk3ins.us-east-2.rds.amazonaws.com
 * 
 * Default AWS Connection Strings
 *	default_writer 				"jdbc:mysql://database4cad.cc1ormgk3ins.us-east-2.rds.amazonaws.com:3306/WeatherSystemDatabase,CAD_MASTER_JDBC,HTWGhtwg";
 *	default_reader 				"jdbc:mysql://database4cad-us-east-2b.cc1ormgk3ins.us-east-2.rds.amazonaws.com:3306/WeatherSystemDatabase,CAD_MASTER_JDBC,HTWGhtwg";
 *	
 *
 * 
 * ####################################################################################################################################
 * ------------------------------------------------------------------------------------------------------------------------------------
 * MYSQL Database Operation Overview
 * ------------------------------------------------------------------------------------------------------------------------------------
 * 
 * [GENERAL]
 * 		1) Constructor
 * 				Arguments: SQL Connection for Reader Connection and Writer Connection
 * 						   NULL Objects - use default connection
 * 
 * 		2) check_database_connection()
 * 				return:		Connection Test Feedback (
 * 
 * [CEP]
 * 		[INSERT]
 * 		visible:	public
 * 		return:		String	(Data Check and Operation Feedback)
 * 
 * 		1) insert_DefaultWeather(int WeatherID, String main, String description, String icon_referenz)
 * 
 * 		2) insert_City(int ZipCode, String countryCode, String cityName, Double logitude, Double latitude, int cityID)
 * 
 * 		3) insert_User(String E_Mail, String userName, String passwort, int mainLocation)  
 * 
 *		4) insert_Subcribe(String E_Mail, int ZipCode)
 *  
 *  	5) insert_Weather(Timestamp TimeStamp, int ZipCode, int WeatherID, 
 *						Double main_temp, Double main_pressure, Double main_humidity, Double main_tempMin, Double main_tempMax, Double main_seaLevel, Double main_grndLevel,
 *						String wind_direction, Double wind_speed, Double wind_deg, String clouds_desc, Double rain_3h, Double snow_3h, 
 *						Timestamp sys_sunset, Timestamp sys_sunrise)			
 *			Note:	Only Timestamp, ZipCode, WeatherID need a valid value
 *					Double Values can be > 0 and String can be null
 *						
 * 		[SELECT]
 * 		visible:	public
 * 		return:		ResultSet (Result of Select statement query)											
 * 		
 * 		1)	select_UserByMail(String user_E_Mail)
 * 
 * 		2)	select_CityByZipCode(int city_ZipCode)
 * 
 * 		3)	select_DefaultWeatherByID(int defaultWeather_ID)
 * 
 * 		4) 	select_all_WeatherByCityZipCode(int city_ZipCode)
 * 
 * 		5)	select_WeatherByCityAndDay(int city_ZipCode, Timestamp weatherDay)
 * 
 * 		6)	select_WeatherByCityAndTimePeriod(int city_ZipCode, Timestamp fromDate, Timestamp toDate)
 * 
 * 		7)	select_SubscribeByUser(String user_E_Mail)
 * 
 * 		8)	select_SubscribeByCity(int ZipCode)
 * 
 * 		9)  select_UserPwByEMail(String E_Mail)
 * 
 * 
 * [RABIT MQ]
 * 		[INSERT]
 * 		visible:	public
 * 		return:		String	(Data Check and Operation Feedback)
 * 
 * 		1)	insert_SystemUser(String userName, String passwort, String additionalDescription)
 * 
 * 		2)	insert_VHost(Sting vHostName, String additionalDescription)
 * 
 * 		3) 	insert_Assigned(String systemUser_userName, String vHost_name, String additionalInformation)
 * 
 * 
 * 		[SELECT]
 * 
 * 		1) select_VHost_all()
 * 
 * 		2) select_SystemUser_all() 
 * 
 * 		3) select_SystemUser_PWByUserName(String userName)
 * 
 * 		4) select_Assigned_all()
 * 		
 * 		5) select_Assigned_UserByVHost(String vHost_name)

 * 
 * 
 * 
 * ####################################################################################################################################
 */


public class CAD_WEATHER_SYSTEM_DATABASE_API 
{
	
//Variables&Refernces	
	java.sql.Connection  writer_connection = null;
	java.sql.Connection  reader_connection = null;



//[GENERAL]
//--Constructor	
	public CAD_WEATHER_SYSTEM_DATABASE_API(java.sql.Connection  writer_connection, java.sql.Connection  reader_connection)
	{
		this.writer_connection = writer_connection;
		this.reader_connection = reader_connection;
		
		
		if(writer_connection == null)
		{
			this.writer_connection = connect_default_writer();
		}
		
		if(reader_connection == null)
		{
			this.reader_connection = connect_default_reader();
		}
		
	}
	
	
	//Default Connection 
		//Writer Connection
			private java.sql.Connection connect_default_writer()
			{
				java.sql.Connection connection = null;
				
				try {
					
					//connection =  DriverManager.getConnection(default_writer);
					connection	 =	DriverManager.getConnection("jdbc:mysql://" + "database4cad.cc1ormgk3ins.us-east-2.rds.amazonaws.com" + ":" + "3306" + "/" + "WeatherSystemDatabase", "CAD_MASTER_JDBC", "HTWGhtwg");
				} 
				catch (SQLException e) 
				{
			        System.out.println("Connection Failed!:\n" + e.getMessage());
			    }

			    if (connection != null) 
			    {
			        System.out.println("SUCCESS! WRITER is available");
			    } 
			    else 
			    {
			        System.out.println("FAILURE! WRITER couldn't be established");
			    }
				
				return connection;
			}
		
		//Reader Connection
			private java.sql.Connection connect_default_reader()
			{
				java.sql.Connection connection = null;
				
				try {
					//connection =  DriverManager.getConnection(default_reader);
					connection 	 =	DriverManager.getConnection("jdbc:mysql://" + "database4cad-us-east-2b.cc1ormgk3ins.us-east-2.rds.amazonaws.com" + ":" + "3306" + "/" + "WeatherSystemDatabase", "CAD_MASTER_JDBC", "HTWGhtwg");
			    } 
				catch (SQLException e) 
				{
			        System.out.println("Connection Failed!:\n" + e.getMessage());
			    }

			    if (connection != null) 
			    {
			        System.out.println("SUCCESS! Reader is available");
			    } 
			    else 
			    {
			        System.out.println("FAILURE! Reader couldn't be established");
			    }
				
				return connection;
				
			}
			
		//Check Connection
			public String check_database_connection()
			{
			    boolean isConnected = false;
			    
			    try {
			    	isConnected = reader_connection.isValid(5);

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
	
//################################################################################################################
//[CEP]
//################################################################################################################			
//INSERT MYSQL Operations
	//DefaultWeather Table
		public String insert_DefaultWeather(int WeatherID, String main, String description, String icon_referenz)
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
					statement = (Statement) writer_connection.createStatement();	
					
					query = "INSERT INTO " + 
							"DefaultWeather "+																	//Table Name
							"(WeatherID, main, description, icon_refernz)"+
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
		public String insert_City(int ZipCode, String countryCode, String cityName, Double logitude, Double latitude, int cityID)
		{
			//Check Incoming Data
				if(ZipCode <= 0)
				{
					return ("Attribute ZipCode - primary Key - expected value bigger than 0");
				}
				
				if(countryCode == null  || countryCode.length() != 2)
				{
					return ("Attribute countryCode - not null - length: 2");
				}	
				
				if(cityName == null)
				{
					return ("Attribute cityName - not null - max length: 100");
				}
				
				if(logitude <= 0)
				{
					return ("Attribute logitude -  not null");
				}
				if(latitude <= 0)
				{
					return ("Attribute latitude -  not null");
				}
				
			
			//MYSQL Query 
				Statement statement;
				String query;
				int resultSet;
				try 
				{
					statement = (Statement) writer_connection.createStatement();	
					
					query = "INSERT INTO " + 
							"City "+																	//Table Name
							"(ZipCode, countryCode, cityName , longitude, latitude, cityID)"+
					        "VALUES"+
							"("+ZipCode+",'"+countryCode+"','"+cityName+"',"+logitude+","+latitude+","+cityID+");";						//Values for Insert
					
					
					resultSet  = statement.executeUpdate(query);
				
					
				} catch (SQLException e) 
				{
					//System.out.println("Insert Operation failed:\n" + e.getMessage());						//Internal
					return("Insert Operation failed:\n" + e.getMessage());
				}
				

		
			
			return "Successfull Insert Operation - Table City - Rowamount: "+ resultSet;
		}
	

	//User Table
		public String insert_User(String E_Mail, String userName, String passwort, int mainLocation)
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
					return ("Attribute passwort - not null - max length: 10");
				}
				
			
			//MYSQL Query 
				Statement statement;
				String query;
				int resultSet;
				try 
				{
					statement = (Statement) writer_connection.createStatement();	
					
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
		public String insert_Subcribe(String E_Mail, int ZipCode)
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
						statement = (Statement) writer_connection.createStatement();	
						
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
		public String insert_Weather(Timestamp TimeStamp, int ZipCode, int WeatherID, 
											Double main_temp, Double main_pressure, Double main_humidity, Double main_tempMin, Double main_tempMax, Double main_seaLevel, Double main_grndLevel,
											String wind_direction, Double wind_speed, Double wind_deg, String clouds_desc, Double rain_3h, Double snow_3h, 
											Timestamp sys_sunset, Timestamp sys_sunrise
											)
			{
				
				//Variables (temp)
					Double temp_main_temp			= -1.0;
					Double temp_main_pressure 		= -1.0;
					Double temp_main_humidity 		= -1.0;
					Double temp_main_tempMin 		= -1.0;
					Double temp_main_tempMax 		= -1.0;
					Double temp_main_seaLevel 		= -1.0;
					Double temp_main_grndLevel		= -1.0;
					
					String temp_wind_direction 		= "";
					Double temp_wind_speed 			= -1.0;
					Double temp_wind_deg			= -1.0;
					String temp_clouds_desc			= "";
					Double temp_rain_3h				= -1.0;
					Double temp_snow_3h				= -1.0;
					
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
					ResultSet resultSet;
					try 
					{
						statement = (Statement) writer_connection.createStatement();	
						
						query = "INSERT INTO " + 
								"Weather "+																	//Table Name
								"(Timestamp, ZipCode, WeatherID,"+
								"main_temp, main_pressure, main_humidity, main_tempMin, main_tempMax, main_seaLevel, main_grndLevel"+
								"wind_direction, wind_speed, wind_deg, clouds_desc, rain_3h, snow_3h, sys_sunset, sys_sunrise)"+
						        "VALUES"+
								"("+TimeStamp+","+ZipCode+","+","+WeatherID+","+
								temp_main_temp +","+ temp_main_pressure +","+  temp_main_humidity +","+ temp_main_tempMin +","+ temp_main_tempMax +","+ temp_main_seaLevel + "," + temp_main_grndLevel + "," +
								temp_wind_direction +","+ temp_wind_speed +","+ temp_wind_deg +","+ temp_clouds_desc + "," + temp_rain_3h + "," + temp_snow_3h	+ "," + temp_sys_sunset + "," + temp_sys_sunrise +
								");";				//Values for Insert
						
						
						resultSet  = statement.executeQuery(query);
					
						
					} catch (SQLException e) 
					{
						//System.out.println("Insert Operation failed:\n" + e.getMessage());						//Internal
						return("Insert Operation failed:\n" + e.getMessage());
					}
					
	
			
				
				return "Successfull Insert Operation - Table Default Weather";
			}
			
			
//SELECT MYSQL Operations
	//SELECT User Data by E-Mail (pk E-Mail)
		public ResultSet select_UserByMail(String user_E_Mail)
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
						statement = (Statement) reader_connection.createStatement();	
						
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
		public ResultSet select_CityByZipCode(int city_ZipCode)
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
						statement = (Statement) reader_connection.createStatement();	
						
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
		public ResultSet select_DefaultWeatherByID(int defaultWeather_ID)
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
					statement = (Statement) reader_connection.createStatement();	
					
					query = "SELECT * " + 
							"FROM DefaultWeather "+																	//Table Name
							"WHERE WeatherID = '" + defaultWeather_ID + "' ;" ;
											
					temp_resultSet  = statement.executeQuery(query);
				
					
				} catch (SQLException e) 
				{
					System.out.println("Insert Operation failed:\n" + e.getMessage());						//Internal
					//return("Insert Operation failed:\n" + e.getMessage());
				}

			return temp_resultSet;
		}			
		

	//SELECT All Weather City Data by ZipCode
		public ResultSet select_all_WeatherByCityZipCode(int city_ZipCode)
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
						statement = (Statement) reader_connection.createStatement();	
						
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
		public ResultSet select_WeatherByCityAndDay(int city_ZipCode, Timestamp weatherDay)
			{
				
				//Variables	
				ResultSet temp_resultSet	= null;
				String temp_weatherDay 		= " DATE_FORMAT(NOW(),'%d %m %Y')"; 								//NOW equals Oracle SYSDATE		
				
				
				//Check Incoming Data
					if(city_ZipCode <= 0)
					{
						return null;
					}
					
					if(weatherDay != null)
					{
						temp_weatherDay = " DATE_FORMAT("+weatherDay+",'%d %m %Y')";
					}
					
				//MYSQL Query 
					Statement statement;
					String query;
					
					try 
					{
						statement = (Statement) reader_connection.createStatement();	
						
						query = "SELECT * " + 
								"FROM Weather "+																	//Table Name
								"WHERE ZipCode = '" + city_ZipCode + "' and " +
								"DATE_FORMAT(Timestamp, '%d %m %Y') = " + temp_weatherDay +";" ;
												
						temp_resultSet  = statement.executeQuery(query);
					
						
					} catch (SQLException e) 
					{
						System.out.println("Insert Operation failed:\n" + e.getMessage());								//Internal
						//return("Insert Operation failed:\n" + e.getMessage());
					}

				return temp_resultSet;
				
			
			}	
		
		
	//SELECT Weather City Data for a certain time period
		public ResultSet select_WeatherByCityAndTimePeriod(int city_ZipCode, Timestamp fromDate, Timestamp toDate)
		{
			//Variables	
			ResultSet temp_resultSet	= null;
			String temp_fromDate 		= " DATE_FORMAT("+fromDate+",'%d %m %Y')"; 								//NOW equals Oracle SYSDATE		
			String temp_toDate 			= " DATE_FORMAT("+toDate+",'%d %m %Y')"; 	
			
			
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
					statement = (Statement) reader_connection.createStatement();	
					
					query = "SELECT * " + 
							"FROM Weather "+																	//Table Name
							"WHERE ZipCode = '" + city_ZipCode + "' and " +
							"DATE_FORMAT(Timestamp, '%d %m %Y') BETWEEN " + temp_fromDate + " and " + temp_toDate + " ;" ;
											
					temp_resultSet  = statement.executeQuery(query);
				
					
				} catch (SQLException e) 
				{
					System.out.println("Insert Operation failed:\n" + e.getMessage());								//Internal
					//return("Insert Operation failed:\n" + e.getMessage());
				}

			return temp_resultSet;

		}
		
		
	//SELECT Subscribed City(s) by User 
		public ResultSet select_SubscribeByUser(String user_E_Mail)
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
						statement = (Statement) reader_connection.createStatement();	
						
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
					statement = (Statement) reader_connection.createStatement();	
					
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
					statement = (Statement) reader_connection.createStatement();	
					
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
		
		

		
		
//################################################################################################################
//[RabbitMQ]
//################################################################################################################		
//Insert MYSQL Operations

	//SystemUser
		public String insert_SystemUser(String userName, String passwort, String additionalDescription)
		{
			//Check Incoming Data
				if(userName == null)
				{
					return ("Attribute userName - primary Key - expected value");
				}
				
				if(passwort == null)
				{
					return ("Attribute passwort - not null - max length: 10");
				}
				
			
			//MYSQL Query 
				Statement statement;
				String query;
				int resultSet;
				try 
				{
					statement = (Statement) writer_connection.createStatement();	
					
					query = "INSERT INTO " + 
							"SystemUser "+																	//Table Name
							"(userName, passwort , description)"+
					        "VALUES"+
							"('"+userName+"','"+passwort+"','"+additionalDescription+"');";				//Values for Insert
					
					
					resultSet  = statement.executeUpdate(query);
				
					
				} catch (SQLException e) 
				{
					//System.out.println("Insert Operation failed:\n" + e.getMessage());						//Internal
					return("Insert Operation failed:\n" + e.getMessage());
				}
				

		
			
			return "Successfull Insert Operation - Table User - Rowamount: " + resultSet;
		}
		
		
	//VHost
		public String insert_VHost(String vHostName, String additionalDescription)
		{
			//Check Incoming Data
				if(vHostName == null)
				{
					return ("Attribute userName - primary Key - expected value");
				}
				
				
			
			//MYSQL Query 
				Statement statement;
				String query;
				int resultSet;
				try 
				{
					statement = (Statement) writer_connection.createStatement();	
					
					query = "INSERT INTO " + 
							"VHost "+																	//Table Name
							"(vHost_name, description)"+
					        "VALUES"+
							"('"+vHostName+"','"+additionalDescription+"');";				//Values for Insert
					
					
					resultSet  = statement.executeUpdate(query);
				
					
				} catch (SQLException e) 
				{
					//System.out.println("Insert Operation failed:\n" + e.getMessage());						//Internal
					return("Insert Operation failed:\n" + e.getMessage());
				}
				
	
		
			
			return "Successfull Insert Operation - Table User - Rowamount: " + resultSet;
		}
		
		
	//Assigned
		public String insert_Assigned(String systemUser_userName, String vHost_name, String additionalInformation)
		{
			//Check Incoming Data
				if(systemUser_userName == null)
				{
					return ("Attribute System UserName - primary Key - expected value");
				}
				
				if(vHost_name == null)
				{
					return ("Attribute vHost Name - primary Key - expected value");
				}
				
				
				
			
			//MYSQL Query 
				Statement statement;
				String query;
				int resultSet;
				try 
				{
					statement = (Statement) writer_connection.createStatement();	
					
					query = "INSERT INTO " + 
							"Assigned "+																	//Table Name
							"(userName, vHost_name, information)"+
					        "VALUES"+
							"('"+systemUser_userName+"','"+vHost_name+"','"+additionalInformation+"');";	//Values for Insert
					
					
					resultSet  = statement.executeUpdate(query);
				
					
				} catch (SQLException e) 
				{
					//System.out.println("Insert Operation failed:\n" + e.getMessage());						//Internal
					return("Insert Operation failed:\n" + e.getMessage());
				}
				
	
		
			
			return "Successfull Insert Operation - Table User - Rowamount: " + resultSet;
		}		

		
//SELECT MYSQL Operations	
	//SELECT vHost - all
		public ResultSet select_VHost_all()
		{
			
			//Variables	
			ResultSet temp_resultSet	= null;

			//MYSQL Query 
				Statement statement;
				String query;
				
				try 
				{
					statement = (Statement) reader_connection.createStatement();	
					
					query = "SELECT * " + 
							"FROM VHost;";																		//Table Name
							
											
					temp_resultSet  = statement.executeQuery(query);
				
					
				} catch (SQLException e) 
				{
					System.out.println("Insert Operation failed:\n" + e.getMessage());							//Internal
					//return("Insert Operation failed:\n" + e.getMessage());
				}

			return temp_resultSet;
		}			
	
		
	//SELECT System User - all 
		public ResultSet select_SystemUser_all()
		{
			
			//Variables	
			ResultSet temp_resultSet	= null;

			//MYSQL Query 
				Statement statement;
				String query;
				
				try 
				{
					statement = (Statement) reader_connection.createStatement();	
					
					query = "SELECT * " + 
							"FROM SystemUser;";																	//Table Name
							
											
					temp_resultSet  = statement.executeQuery(query);
				
					
				} catch (SQLException e) 
				{
					System.out.println("Insert Operation failed:\n" + e.getMessage());							//Internal
					
				}

			return temp_resultSet;
		}	


	//SELECT  SystemUser Passwort
		public ResultSet select_SystemUser_PWByUserName(String userName)
		{
			
			//Variables	
				ResultSet temp_resultSet	= null;
			
			//Check incoming data
				if(userName == null)
				{return null;}
			
			//MYSQL Query 
				Statement statement;
				String query;
				
				try 
				{
					statement = (Statement) reader_connection.createStatement();	
					
					query = "SELECT passwort " + 
							"FROM SystemUser " +
							"WHERE	userName = '"+userName+"';";																
							
											
					temp_resultSet  = statement.executeQuery(query);
				
					
				} catch (SQLException e) 
				{
					System.out.println("Insert Operation failed:\n" + e.getMessage());							//Internal
				
				}

			return temp_resultSet;
		}
		
		
	//SELECT  Assigned - all
		public ResultSet select_Assigned_all() 
		{
			
			//Variables	
				ResultSet temp_resultSet	= null;

			//MYSQL Query 
				Statement statement;
				String query;
				
				try 
				{
					statement = (Statement) reader_connection.createStatement();	
					
					query = "SELECT * " + 
							"FROM Assigned;" ;
																
							
											
					temp_resultSet  = statement.executeQuery(query);
				
					
				} catch (SQLException e) 
				{
					System.out.println("Insert Operation failed:\n" + e.getMessage());							//Internal
				
				}

			return temp_resultSet;
		}	
		
			
		//SELECT  Assigned - all
			public ResultSet select_Assigned_UserByVHost(String vHost_name)
			{
				
				//Variables	
					ResultSet temp_resultSet	= null;
				
				//Check Incoming Data
					if(vHost_name == null)
					{
						return null;
					}

				//MYSQL Query 
					Statement statement;
					String query;
					
					try 
					{
						statement = (Statement) reader_connection.createStatement();	
						
						query = "SELECT userName " + 
								"FROM Assigned "+
								"WHERE vHost_name = '" +vHost_name+ "';";
																	
								
												
						temp_resultSet  = statement.executeQuery(query);
					
						
					} catch (SQLException e) 
					{
						System.out.println("Insert Operation failed:\n" + e.getMessage());							//Internal
					
					}

				return temp_resultSet;
			}			
	
}


