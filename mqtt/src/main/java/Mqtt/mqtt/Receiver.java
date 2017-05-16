package Mqtt.mqtt;

import org.eclipse.paho.client.mqttv3.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

/**
 * Created by Sebastian Thümmel on 12.04.2017.
 */
public class Receiver {

    public static void main(String[] args) {

        Properties config = new Properties();
        String plz ="78467";

        try {
            //config.properties erstellen, code aus .example kopieren und bekannte url einsetzen
            config.load(new FileInputStream("config.properties"));
            MqttClient client = new MqttClient(config.getProperty("host"), MqttClient.generateClientId());
            client.setCallback(new MqttCallback() {
                public void connectionLost(Throwable throwable) {
                    System.out.println("connection lost");
                }

                public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                    System.out.println(new String(mqttMessage.getPayload()) + " - received via topic: " + topic);
                }

                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });
            client.connect();
            client.subscribe("today"+plz);
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}