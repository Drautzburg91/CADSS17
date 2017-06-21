package cad.cep.db;

import java.sql.ResultSet;
import java.sql.Timestamp;

public interface WeatherRepository 

{
	
	java.sql.Connection getConnection();
	void setConnection(java.sql.Connection  databaseConnection);
	
	 String insertDefaultWeather(int WeatherID, String main, String description, String icon_referenz);
	 String insertCity(int ZipCode, String cityName, Double logitude, Double latitude, int cityID); 
	 String insertUser(String E_Mail, String userName, String passwort, int mainLocation);	  
	 String insertSubcribe(String E_Mail, int ZipCode);
	 String insertAlert(int ZipCode, Timestamp timestamp, String title, String code, String message);
	 String insertWeather(Timestamp TimeStamp, int ZipCode, int WeatherID, 
				double main_temp, double main_pressure, double main_humidity, double main_tempMin, double main_tempMax, double main_seaLevel, double main_grndLevel,
				String wind_direction, double wind_speed, double wind_deg, String clouds_desc, double rain_3h, double snow_3h, 
				Timestamp sys_sunset, Timestamp sys_sunrise
				);
	 
	 ResultSet selectUserByMail(String user_E_Mail); 
	 ResultSet selectCityByZipCode(int city_ZipCode);
	 ResultSet selectDefaultWeatherByID(int defaultWeather_ID);
	 ResultSet selectAllWeatherByCityZipCode(int city_ZipCode);
	 ResultSet selectWeatherByCityAndDay(int city_ZipCode, Timestamp weatherDay);
	 ResultSet selectWeatherByCityAndTimePeriod(int city_ZipCode, Timestamp fromDate, Timestamp toDate);
	 ResultSet selectSubscribeByUser(String user_E_Mail);
	 ResultSet selectSubscribeByCity(int ZipCode);
	 ResultSet selectUserPwByEMail(String E_Mail);

}
