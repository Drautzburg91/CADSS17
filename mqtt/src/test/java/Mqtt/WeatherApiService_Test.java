package Mqtt;

import Mqtt.Model.WeatherData;
import Mqtt.Model.WeatherDataWeekly;
import Mqtt.Service.WeatherApiService;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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

	/**
	 * Test ApiCall for Topic "Weekly"
	 */

	public String json3hrsWithWindDeg(String hr) {
		// dt_txt\":\"2017-06-10 21:00:00
		return "{\"dt\":1497117600,\"main\":{\"temp\":21.4,\"temp_min\":20,\"temp_max\":21.4,\"pressure\":957,\"sea_level\":1033.31,\"grnd_level\":957.44,\"humidity\":63,\"temp_kf\":1.4},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":64},\"wind\":{\"speed\":1.06,\"deg\":24.0006},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":"
				+ hr + "}";
	}

	public Calendar add3hours(Calendar cal) {

		cal.add(Calendar.HOUR_OF_DAY, 3);
		return cal;

	}

	public void testApiCallWeeklyWithWindDeg() {

		ArrayList<WeatherDataWeekly> expectedWeeklyResult = new ArrayList<>();
		String input = "{\"cod\":\"200\",\"message\":0.0025,\"cnt\":40,\"list\":[";

		Calendar cal = Calendar.getInstance();
		cal.set(2017, 06, 10, 21, 00, 00);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		for (int i = 0; i <= 10; i++) {
			cal = add3hours(cal);
			String seg = format.format(cal.getTime());
			if (i == 0) {
				input = input + "\n" + json3hrsWithWindDeg("\"" + seg + "\"");
			} else {
				input = input + "\n," + json3hrsWithWindDeg("\"" + seg + "\"");
			}

			WeatherDataWeekly dailyResult = new WeatherDataWeekly();
			dailyResult.setCityName("Konstanz");
			dailyResult.setLatitude(47.6744);
			dailyResult.setLongitude(9.1649);
			dailyResult.setDate(seg);
			dailyResult.setTemperatureMax(21.4);
			dailyResult.setTemperatureMin(20);
			dailyResult.setHumidity(63);
			dailyResult.setPressure(957);
			dailyResult.setCurrentWeather("broken clouds");
			dailyResult.setCurrentWeatherId(803);
			dailyResult.setWeatherIcon("04d");
			dailyResult.setTemperature(21.4);
			dailyResult.setWindspeed(1.06);
			dailyResult.setPlz("78467");
			dailyResult.setWindDeg(24.0006);

			expectedWeeklyResult.add(dailyResult);

		}

		input = input + "],\"city\":{\"name\":\"Konstanz\",\"coord\":{\"lat\":47.6744,\"lon\":9.1649},\"country\":\"DE\"}}";
		JsonParser jp = new JsonParser();
		System.out.println(input);
		JsonElement root = jp.parse(input);

		ArrayList<WeatherDataWeekly> actualResult = WeatherApiService.weeklyToWeatherDataWeekly(root, "78467");

		assertEquals(actualResult, expectedWeeklyResult);

	}

	public String json3hrsWithoutWindDeg(String hr) {
		// dt_txt\":\"2017-06-10 21:00:00
		return "{\"dt\":1497117600,\"main\":{\"temp\":21.4,\"temp_min\":20,\"temp_max\":21.4,\"pressure\":957,\"sea_level\":1033.31,\"grnd_level\":957.44,\"humidity\":63,\"temp_kf\":1.4},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":64},\"wind\":{\"speed\":1.06},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":"
				+ hr + "}";
	}

	public void testApiCallWeeklyWithoutWindDeg() {

		ArrayList<WeatherDataWeekly> expectedWeeklyResult = new ArrayList<>();
		String input = "{\"cod\":\"200\",\"message\":0.0025,\"cnt\":40,\"list\":[";

		Calendar cal = Calendar.getInstance();
		cal.set(2017, 06, 10, 21, 00, 00);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		for (int i = 0; i <= 10; i++) {
			cal = add3hours(cal);
			String seg = format.format(cal.getTime());
			if (i == 0) {
				input = input + "\n" + json3hrsWithoutWindDeg("\"" + seg + "\"");
			} else {
				input = input + "\n," + json3hrsWithoutWindDeg("\"" + seg + "\"");
			}

			WeatherDataWeekly dailyResult = new WeatherDataWeekly();
			dailyResult.setCityName("Konstanz");
			dailyResult.setLatitude(47.6744);
			dailyResult.setLongitude(9.1649);
			dailyResult.setDate(seg);
			dailyResult.setTemperatureMax(21.4);
			dailyResult.setTemperatureMin(20);
			dailyResult.setHumidity(63);
			dailyResult.setPressure(957);
			dailyResult.setCurrentWeather("broken clouds");
			dailyResult.setCurrentWeatherId(803);
			dailyResult.setWeatherIcon("04d");
			dailyResult.setTemperature(21.4);
			dailyResult.setWindspeed(1.06);
			dailyResult.setPlz("78467");
			dailyResult.setWindDeg(0.0);

			expectedWeeklyResult.add(dailyResult);

		}

		input = input + "],\"city\":{\"name\":\"Konstanz\",\"coord\":{\"lat\":47.6744,\"lon\":9.1649},\"country\":\"DE\"}}";
		JsonParser jp = new JsonParser();
		System.out.println(input);
		JsonElement root = jp.parse(input);

		ArrayList<WeatherDataWeekly> actualResult = WeatherApiService.weeklyToWeatherDataWeekly(root, "78467");

		assertEquals(actualResult, expectedWeeklyResult);

	}

}
