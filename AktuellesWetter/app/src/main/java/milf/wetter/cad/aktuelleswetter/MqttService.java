package milf.wetter.cad.aktuelleswetter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


/**
 * Created by georgmohr on 30.04.17.
 */

public class MqttService extends Service implements MqttCallback {

    final String serverUri = "";


    MqttAndroidClient myClient;
    MqttConnectOptions connOpt;

    static final String BROKER_URL = "tcp://ec2-35-163-22-190.us-west-2.compute.amazonaws.com:1883";
    static final String M2MIO_USERNAME = "caduser";
    static final String M2MIO_PASSWORD_MD5 = "caduser";

    // the following two flags control whether this example is a publisher, a subscriber or both
    static final Boolean subscriber = true;


    public void connectionLost(Throwable t) {
        System.out.println("Connection lost!");
        // code to reconnect to the broker would go here if desired
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

        Log.e("| Message: " , new String(message.getPayload()));

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        //System.out.println("Pub complete" + new String(token.getMessage().getPayload()));
    }


    public void onCreate() {
        super.onCreate();
        String clientID = "test";
        connOpt = new MqttConnectOptions();

        connOpt.setCleanSession(true);
        connOpt.setKeepAliveInterval(30);
        connOpt.setUserName(M2MIO_USERNAME);
        connOpt.setPassword(M2MIO_PASSWORD_MD5.toCharArray());

        // Connect to Broker
        try {
            myClient = new MqttAndroidClient(getApplicationContext(),clientID, BROKER_URL);
            myClient.setCallback(this);
            myClient.connect(connOpt);
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(-1);
        }


        // setup topic
        // topics on m2m.io are in the form <domain>/<stuff>/<thing>
        String myTopic = "today";


        // subscribe to topic if subscriber
        if (subscriber) {
            try {
                int subQoS = 0;
                myClient.subscribe(myTopic, subQoS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            // wait to ensure subscribed messages are delivered
            if (subscriber) {
                Thread.sleep(5000);
            }
            myClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}
