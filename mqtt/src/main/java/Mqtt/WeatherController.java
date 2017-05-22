package Mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Sebastian Thümmel on 22.05.2017.
 */
@Controller
public class WeatherController {

    @Autowired
    MqttService mqttService;

    @Autowired
    WeatherFormValidator weatherFormValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder){
        binder.setValidator(weatherFormValidator);
    }

    @RequestMapping("/")
    public String index(Model model) {
        //mqttService.publishWeatherData();
        WeatherData data = new WeatherData();
        model.addAttribute("weatherdata", data);
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String sendWeather(@ModelAttribute("weatherdata") @Validated WeatherData weatherData, BindingResult result, Model model){
        System.out.println(weatherData.toString());
        return "index";
    }

    @RequestMapping(value = "/API-Data", method = RequestMethod.GET)
    public String startLiveApi(WeatherData weatherData, Model model){
        mqttService.publishWeatherData();
        System.out.println("Live API running");
        model.addAttribute("weatherdata", new WeatherData());
        return "index";
    }

}
