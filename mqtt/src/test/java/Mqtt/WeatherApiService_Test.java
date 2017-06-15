package Mqtt;

import Mqtt.Model.WeatherData;
import Mqtt.Service.WeatherApiService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for MqttService.
 */
public class WeatherApiService_Test extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public WeatherApiService_Test(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(WeatherApiService_Test.class);
	}

	/**
	 * Test ApiCall for Topic "Today"
	 */
	public void testAPICallTodayWithoutWindDeg() {
		String today = "{\"message\":\"accurate\",\"cod\":\"200\",\"count\":1,\"list\":[{\"id\":2806382,\"name\":\"Wollmatingen\",\"coord\":{\"lat\":47.6923,\"lon\":9.1459},\"main\":{\"temp\":23.25,\"pressure\":1020,\"humidity\":44,\"temp_min\":21,\"temp_max\":25},\"dt\":1497110400,\"wind\":{\"speed\":1.5},\"sys\":{\"country\":\"DE\"},\"rain\":null,\"snow\":null,\"clouds\":{\"all\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"Sky is Clear\",\"icon\":\"01d\"}]}]}";

		JsonParser jp = new JsonParser();

		JsonElement root = jp.parse(today);

		WeatherData actualResult = WeatherApiService.dailyToWeatherData(root, "78467");

		WeatherData expectedDailyResult = new WeatherData();

		expectedDailyResult.setCityName("Wollmatingen");
		expectedDailyResult.setLongitude(9.1459);
		expectedDailyResult.setLatitude(47.6923);
		expectedDailyResult.setHumidity(44);
		expectedDailyResult.setPressure(1020);
		expectedDailyResult.setTemperature(23.25);
		expectedDailyResult.setTemperatureMax(25);
		expectedDailyResult.setTemperatureMin(21);
		expectedDailyResult.setWindspeed(1.5);
		expectedDailyResult.setCurrentWeather("Sky is Clear");
		expectedDailyResult.setCurrentWeatherId(800);
		expectedDailyResult.setWeatherIcon("01d");
		expectedDailyResult.setPlz("78467");

		expectedDailyResult.setWindDeg(0.0);
		assertEquals(actualResult, expectedDailyResult);
	}

	public void testAPICallTodayWithWindDeg() {

		String today = "{\"message\":\"accurate\",\"cod\":\"200\",\"count\":1,\"list\":[{\"id\":2806382,\"name\":\"Wollmatingen\",\"coord\":{\"lat\":47.6923,\"lon\":9.1459},\"main\":{\"temp\":23.25,\"pressure\":1020,\"humidity\":44,\"temp_min\":21,\"temp_max\":25},\"dt\":1497110400,\"wind\":{\"speed\":1.5,\"deg\":200},\"sys\":{\"country\":\"DE\"},\"rain\":null,\"snow\":null,\"clouds\":{\"all\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"Sky is Clear\",\"icon\":\"01d\"}]}]}";

		JsonParser jp = new JsonParser();

		JsonElement root = jp.parse(today);

		WeatherData actualResult = WeatherApiService.dailyToWeatherData(root, "78467");

		WeatherData expectedDailyResult = new WeatherData();

		expectedDailyResult.setCityName("Wollmatingen");
		expectedDailyResult.setLongitude(9.1459);
		expectedDailyResult.setLatitude(47.6923);
		expectedDailyResult.setHumidity(44);
		expectedDailyResult.setPressure(1020);
		expectedDailyResult.setTemperature(23.25);
		expectedDailyResult.setTemperatureMax(25);
		expectedDailyResult.setTemperatureMin(21);
		expectedDailyResult.setWindspeed(1.5);
		expectedDailyResult.setCurrentWeather("Sky is Clear");
		expectedDailyResult.setCurrentWeatherId(800);
		expectedDailyResult.setWeatherIcon("01d");
		expectedDailyResult.setPlz("78467");

		expectedDailyResult.setWindDeg(200);
		assertEquals(actualResult, expectedDailyResult);
	}

	/* in Work ... 
	public String json3hrs(String hr){
		return "[{\"dt\":1497117600,\"main\":{\"temp\":21.4,\"temp_min\":20,\"temp_max\":21.4,\"pressure\":957.44,\"sea_level\":1033.31,\"grnd_level\":957.44,\"humidity\":63,\"temp_kf\":1.4},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":64},\"wind\":{\"speed\":1.06,\"deg\":24.0006},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":"+hr+"}";
	}
	
	public String add3hours(String hr) {
	
		return "" ;
	}
	
	public String jsonWeekly(){
		String result = "{\"cod\":\"200\",\"message\":0.0128,\"cnt\":40,\"list\":[";
		for (int i=0; i<=5 ;i++){
			result = result+json3hrs(add3hours(""));
		}
		return result;
	}
	
	public void testApiCallWeekly() {
		String weekly = "{\"cod\":\"200\",\"message\":0.0128,\"cnt\":40,\"list\":[{\"dt\":1497117600,\"main\":{\"temp\":21.4,\"temp_min\":20,\"temp_max\":21.4,\"pressure\":957.44,\"sea_level\":1033.31,\"grnd_level\":957.44,\"humidity\":63,\"temp_kf\":1.4},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":64},\"wind\":{\"speed\":1.06,\"deg\":24.0006},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-06-10 18:00:00\"},{\"dt\":1497128400,\"main\":{\"temp\":15.07,\"temp_min\":14.02,\"temp_max\":15.07,\"pressure\":958.04,\"sea_level\":1034.49,\"grnd_level\":958.04,\"humidity\":78,\"temp_kf\":1.05},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.23,\"deg\":58.5002},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-06-10 21:00:00\"},{\"dt\":1497139200,\"main\":{\"temp\":11.73,\"temp_min\":11.03,\"temp_max\":11.73,\"pressure\":957.96,\"sea_level\":1034.8,\"grnd_level\":957.96,\"humidity\":88,\"temp_kf\":0.7},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.05,\"deg\":95.0014},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-06-11 00:00:00\"},{\"dt\":1497150000,\"main\":{\"temp\":9.66,\"temp_min\":9.31,\"temp_max\":9.66,\"pressure\":957.51,\"sea_level\":1034.45,\"grnd_level\":957.51,\"humidity\":88,\"temp_kf\":0.35},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":0.86,\"deg\":101.501},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-06-11 03:00:00\"},{\"dt\":1497160800,\"main\":{\"temp\":15.8,\"temp_min\":15.8,\"temp_max\":15.8,\"pressure\":957.33,\"sea_level\":1034.02,\"grnd_level\":957.33,\"humidity\":72,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.36,\"deg\":100.505},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-06-11 06:00:00\"},{\"dt\":1497171600,\"main\":{\"temp\":21.61,\"temp_min\":21.61,\"temp_max\":21.61,\"pressure\":956.69,\"sea_level\":1032.69,\"grnd_level\":956.69,\"humidity\":64,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.61,\"deg\":31.5097},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-06-11 09:00:00\"},{\"dt\":1497182400,\"main\":{\"temp\":24.98,\"temp_min\":24.98,\"temp_max\":24.98,\"pressure\":955.3,\"sea_level\":1030.86,\"grnd_level\":955.3,\"humidity\":59,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.42,\"deg\":20.5011},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-06-11 12:00:00\"},{\"dt\":1497193200,\"main\":{\"temp\":26.18,\"temp_min\":26.18,\"temp_max\":26.18,\"pressure\":954.33,\"sea_level\":1029.62,\"grnd_level\":954.33,\"humidity\":53,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.47,\"deg\":353.502},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-06-11 15:00:00\"},{\"dt\":1497204000,\"main\":{\"temp\":24.26,\"temp_min\":24.26,\"temp_max\":24.26,\"pressure\":953.99,\"sea_level\":1029.32,\"grnd_level\":953.99,\"humidity\":57,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"clouds\":{\"all\":24},\"wind\":{\"speed\":1.3,\"deg\":257.511},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-06-11 18:00:00\"},{\"dt\":1497214800,\"main\":{\"temp\":17.93,\"temp_min\":17.93,\"temp_max\":17.93,\"pressure\":953.93,\"sea_level\":1029.85,\"grnd_level\":953.93,\"humidity\":78,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":80},\"wind\":{\"speed\":1.12,\"deg\":207.001},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-06-11 21:00:00\"},{\"dt\":1497225600,\"main\":{\"temp\":15.44,\"temp_min\":15.44,\"temp_max\":15.44,\"pressure\":953.68,\"sea_level\":1029.76,\"grnd_level\":953.68,\"humidity\":83,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.16,\"deg\":189},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-06-12 00:00:00\"},{\"dt\":1497236400,\"main\":{\"temp\":14.59,\"temp_min\":14.59,\"temp_max\":14.59,\"pressure\":953.88,\"sea_level\":1030.03,\"grnd_level\":953.88,\"humidity\":85,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.62,\"deg\":224},\"rain\":{\"3h\":0.0025000000000001},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-06-12 03:00:00\"},{\"dt\":1497247200,\"main\":{\"temp\":21.81,\"temp_min\":21.81,\"temp_max\":21.81,\"pressure\":954.47,\"sea_level\":1030.59,\"grnd_level\":954.47,\"humidity\":70,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.66,\"deg\":227.006},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-06-12 06:00:00\"},{\"dt\":1497258000,\"main\":{\"temp\":25.81,\"temp_min\":25.81,\"temp_max\":25.81,\"pressure\":954.87,\"sea_level\":1030.61,\"grnd_level\":954.87,\"humidity\":57,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":3.06,\"deg\":257.004},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-06-12 09:00:00\"},{\"dt\":1497268800,\"main\":{\"temp\":27.3,\"temp_min\":27.3,\"temp_max\":27.3,\"pressure\":954.87,\"sea_level\":1030.21,\"grnd_level\":954.87,\"humidity\":53,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":3.87,\"deg\":268},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-06-12 12:00:00\"},{\"dt\":1497279600,\"main\":{\"temp\":27.08,\"temp_min\":27.08,\"temp_max\":27.08,\"pressure\":954.73,\"sea_level\":1030.07,\"grnd_level\":954.73,\"humidity\":51,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"02d\"}],\"clouds\":{\"all\":8},\"wind\":{\"speed\":4.47,\"deg\":273.501},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-06-12 15:00:00\"},{\"dt\":1497290400,\"main\":{\"temp\":25.05,\"temp_min\":25.05,\"temp_max\":25.05,\"pressure\":954.85,\"sea_level\":1030.3,\"grnd_level\":954.85,\"humidity\":53,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":3.54,\"deg\":268.001},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-06-12 18:00:00\"},{\"dt\":1497301200,\"main\":{\"temp\":20.27,\"temp_min\":20.27,\"temp_max\":20.27,\"pressure\":955.11,\"sea_level\":1031.07,\"grnd_level\":955.11,\"humidity\":62,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.77,\"deg\":265.002},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-06-12 21:00:00\"},{\"dt\":1497312000,\"main\":{\"temp\":15.86,\"temp_min\":15.86,\"temp_max\":15.86,\"pressure\":955.02,\"sea_level\":1031.36,\"grnd_level\":955.02,\"humidity\":77,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"02n\"}],\"clouds\":{\"all\":8},\"wind\":{\"speed\":0.86,\"deg\":295.5},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-06-13 00:00:00\"},{\"dt\":1497322800,\"main\":{\"temp\":13.71,\"temp_min\":13.71,\"temp_max\":13.71,\"pressure\":954.69,\"sea_level\":1031.25,\"grnd_level\":954.69,\"humidity\":82,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":0.77,\"deg\":327.501},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-06-13 03:00:00\"},{\"dt\":1497333600,\"main\":{\"temp\":18.19,\"temp_min\":18.19,\"temp_max\":18.19,\"pressure\":954.92,\"sea_level\":1031.25,\"grnd_level\":954.92,\"humidity\":67,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.61,\"deg\":60.5003},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-06-13 06:00:00\"},{\"dt\":1497344400,\"main\":{\"temp\":22.85,\"temp_min\":22.85,\"temp_max\":22.85,\"pressure\":954.89,\"sea_level\":1030.66,\"grnd_level\":954.89,\"humidity\":60,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.61,\"deg\":14.0035},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-06-13 09:00:00\"},{\"dt\":1497355200,\"main\":{\"temp\":25.54,\"temp_min\":25.54,\"temp_max\":25.54,\"pressure\":954.63,\"sea_level\":1030.12,\"grnd_level\":954.63,\"humidity\":54,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"clouds\":{\"all\":24},\"wind\":{\"speed\":1.55,\"deg\":345.002},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-06-13 12:00:00\"},{\"dt\":1497366000,\"main\":{\"temp\":26.39,\"temp_min\":26.39,\"temp_max\":26.39,\"pressure\":954.09,\"sea_level\":1029.46,\"grnd_level\":954.09,\"humidity\":48,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"clouds\":{\"all\":24},\"wind\":{\"speed\":1.61,\"deg\":305.001},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-06-13 15:00:00\"},{\"dt\":1497376800,\"main\":{\"temp\":24.7,\"temp_min\":24.7,\"temp_max\":24.7,\"pressure\":953.38,\"sea_level\":1028.83,\"grnd_level\":953.38,\"humidity\":51,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":1.22,\"deg\":341.503},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-06-13 18:00:00\"},{\"dt\":1497387600,\"main\":{\"temp\":20.61,\"temp_min\":20.61,\"temp_max\":20.61,\"pressure\":953.86,\"sea_level\":1029.83,\"grnd_level\":953.86,\"humidity\":57,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":1.17,\"deg\":31.5015},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-06-13 21:00:00\"},{\"dt\":1497398400,\"main\":{\"temp\":18.92,\"temp_min\":18.92,\"temp_max\":18.92,\"pressure\":954.24,\"sea_level\":1030.43,\"grnd_level\":954.24,\"humidity\":56,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":76},\"wind\":{\"speed\":1.61,\"deg\":36.0009},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-06-14 00:00:00\"},{\"dt\":1497409200,\"main\":{\"temp\":17.35,\"temp_min\":17.35,\"temp_max\":17.35,\"pressure\":953.73,\"sea_level\":1030.1,\"grnd_level\":953.73,\"humidity\":75,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":68},\"wind\":{\"speed\":1.18,\"deg\":101.5},\"rain\":{\"3h\":0.36},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-06-14 03:00:00\"},{\"dt\":1497420000,\"main\":{\"temp\":17.44,\"temp_min\":17.44,\"temp_max\":17.44,\"pressure\":954.11,\"sea_level\":1030.4,\"grnd_level\":954.11,\"humidity\":93,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":64},\"wind\":{\"speed\":0.87,\"deg\":63.0001},\"rain\":{\"3h\":0.86},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-06-14 06:00:00\"},{\"dt\":1497430800,\"main\":{\"temp\":19.69,\"temp_min\":19.69,\"temp_max\":19.69,\"pressure\":955.53,\"sea_level\":1031.18,\"grnd_level\":955.53,\"humidity\":81,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":80},\"wind\":{\"speed\":1.61,\"deg\":24.5068},\"rain\":{\"3h\":0.5},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-06-14 09:00:00\"},{\"dt\":1497441600,\"main\":{\"temp\":22.34,\"temp_min\":22.34,\"temp_max\":22.34,\"pressure\":953.39,\"sea_level\":1028.91,\"grnd_level\":953.39,\"humidity\":73,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":56},\"wind\":{\"speed\":1.66,\"deg\":98.5},\"rain\":{\"3h\":0.7},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-06-14 12:00:00\"},{\"dt\":1497452400,\"main\":{\"temp\":24.38,\"temp_min\":24.38,\"temp_max\":24.38,\"pressure\":952.49,\"sea_level\":1027.72,\"grnd_level\":952.49,\"humidity\":64,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":88},\"wind\":{\"speed\":1.41,\"deg\":50.0002},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-06-14 15:00:00\"},{\"dt\":1497463200,\"main\":{\"temp\":21.72,\"temp_min\":21.72,\"temp_max\":21.72,\"pressure\":952.22,\"sea_level\":1027.52,\"grnd_level\":952.22,\"humidity\":74,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":76},\"wind\":{\"speed\":1.26,\"deg\":356.002},\"rain\":{\"3h\":0.4625},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-06-14 18:00:00\"},{\"dt\":1497474000,\"main\":{\"temp\":18.93,\"temp_min\":18.93,\"temp_max\":18.93,\"pressure\":951.97,\"sea_level\":1027.7,\"grnd_level\":951.97,\"humidity\":84,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03n\"}],\"clouds\":{\"all\":36},\"wind\":{\"speed\":0.68,\"deg\":160.505},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-06-14 21:00:00\"},{\"dt\":1497484800,\"main\":{\"temp\":17.06,\"temp_min\":17.06,\"temp_max\":17.06,\"pressure\":950.76,\"sea_level\":1026.77,\"grnd_level\":950.76,\"humidity\":89,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":64},\"wind\":{\"speed\":1.06,\"deg\":161.505},\"rain\":{\"3h\":0.0875},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-06-15 00:00:00\"},{\"dt\":1497495600,\"main\":{\"temp\":16.21,\"temp_min\":16.21,\"temp_max\":16.21,\"pressure\":951.53,\"sea_level\":1027.6,\"grnd_level\":951.53,\"humidity\":98,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":80},\"wind\":{\"speed\":0.86,\"deg\":198},\"rain\":{\"3h\":2.15},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-06-15 03:00:00\"},{\"dt\":1497506400,\"main\":{\"temp\":17.53,\"temp_min\":17.53,\"temp_max\":17.53,\"pressure\":951.89,\"sea_level\":1027.96,\"grnd_level\":951.89,\"humidity\":99,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":76},\"wind\":{\"speed\":1.51,\"deg\":197.001},\"rain\":{\"3h\":2.875},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-06-15 06:00:00\"},{\"dt\":1497517200,\"main\":{\"temp\":20.68,\"temp_min\":20.68,\"temp_max\":20.68,\"pressure\":953.22,\"sea_level\":1028.85,\"grnd_level\":953.22,\"humidity\":98,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":48},\"wind\":{\"speed\":2.86,\"deg\":259.001},\"rain\":{\"3h\":2.125},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-06-15 09:00:00\"},{\"dt\":1497528000,\"main\":{\"temp\":22.57,\"temp_min\":22.57,\"temp_max\":22.57,\"pressure\":953.59,\"sea_level\":1029.08,\"grnd_level\":953.59,\"humidity\":85,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":12},\"wind\":{\"speed\":3.57,\"deg\":268.505},\"rain\":{\"3h\":0.074999999999999},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-06-15 12:00:00\"},{\"dt\":1497538800,\"main\":{\"temp\":21.79,\"temp_min\":21.79,\"temp_max\":21.79,\"pressure\":954.21,\"sea_level\":1029.66,\"grnd_level\":954.21,\"humidity\":86,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":12},\"wind\":{\"speed\":1.6,\"deg\":288.5},\"rain\":{\"3h\":1.45},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-06-15 15:00:00\"}],\"city\":{\"name\":\"Konstanz\",\"coord\":";

		JsonParser jp = new JsonParser();

		JsonElement root = jp.parse(weekly);
		ArrayList<WeatherDataWeekly> actualResult = MqttService.weeklyToWeatherDataWeekly(root, "78467");

		WeatherDataWeekly expectedWeeklyResult = new WeatherDataWeekly();

		for (JsonElement daily : jsonArray) {

			WeatherDataWeekly dailyResult = new WeatherDataWeekly();

		}*/
	}


