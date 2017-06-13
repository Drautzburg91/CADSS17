package Mqtt.Service;

import Mqtt.Model.WeatherData;
import org.springframework.stereotype.Service;

/**
 * Created by Basti on 25.05.2017.
 */
@Service
public interface MessagingService {

    void publishLiveWeatherData();
    void publishFakeWeatherData(WeatherData weatherData);
    void setTransmittingLive(boolean transmittingLive);
    void setTransmittingGenerated(boolean transmittingGenerated);

}
