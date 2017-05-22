package Mqtt;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by Sebastian Thümmel on 22.05.2017.
 */

@Component
public class WeatherFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return WeatherData.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        WeatherData data = (WeatherData) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "longitude", "notEmpty.weatherData.longitude");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "latitude", "notEmpty.weatherData.latitude");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cityName", "notEmpty.weatherData.cityName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "plz", "notEmpty.weatherData.plz");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "weatherIcon", "notEmpty.weatherData.weatherIcon");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentWeather", "notEmpty.weatherData.currentWeather");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentWeatherId", "notEmpty.weatherData.currentWeatherId");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pressure", "notEmpty.weatherData.pressure");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "humitidy", "notEmpty.weatherData.humitidy");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "windspeed", "notEmpty.weatherData.windspeed");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "windDeg", "notEmpty.weatherData.windDeg");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "temperature", "notEmpty.weatherData.temperature");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "temperatureMax", "notEmpty.weatherData.temperatureMax");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "temperatureMin", "notEmpty.weatherData.temperatureMin");
    }
}
