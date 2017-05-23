package Mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Sebastian Thümmel on 22.05.2017.
 */
@Controller
public class MessagingController {

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
        WeatherData data = new WeatherData();
        model.addAttribute("weatherdata", data);
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String sendWeather(@ModelAttribute("weatherdata") @Validated WeatherData weatherData, BindingResult result, Model model){
        System.out.println(weatherData.toString());
        return "index";
    }

    @RequestMapping(value = "/messaging/liveData", method = RequestMethod.POST)
    public String startLiveApi(@ModelAttribute("weatherdata") WeatherData weatherData, Model model){
        mqttService.setTransmittingGenerated(false);
        mqttService.publishLiveWeatherData();
        System.out.println("Live API running");
        model.addAttribute("weatherdata", weatherData);
        return "index";
    }

    @RequestMapping(value = "/messaging/generatedData", method = RequestMethod.POST)
    public String startGeneratedData(@ModelAttribute("weatherdata") WeatherData weatherData, Model model){
        mqttService.setTransmittingLive(false);
        mqttService.publishFakeWeatherData(weatherData);
        System.out.println("Fake API running");
        model.addAttribute("weatherdata", weatherData);
        return "index";
    }


}
