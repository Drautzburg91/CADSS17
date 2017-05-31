package milf.wetter.cad.aktuelleswetter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


/**
 * Created by georgmohr on 30.04.17.
 */

public class MqttService extends Service implements MqttCallback {

    final String serverUri = "";
    public String plz;
    Intent in = new Intent();
    private Intent intent;
    public static final String KEY_TEST = "test";
    MqttAndroidClient myClient;
    MqttConnectOptions connOpt;

    static final String BROKER_URL = "tcp://ec2-52-43-30-22.us-west-2.compute.amazonaws.com:1883";
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
        Log.e("onReceive: ",new String (message.getPayload()));
        in.putExtra("Message",message.getPayload());
        in.putExtra("Topic",topic);
        in.setAction("NOW");
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(in);

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        //System.out.println("Pub complete" + new String(token.getMessage().getPayload()));
    }


    public void onCreate() {
        super.onCreate();




    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        this.intent = intent;
        if(intent.getStringExtra("plz")!=null) {
            plz = intent.getStringExtra("plz");
            String clientID = "test";//MqttClient.generateClientId();
            connOpt = new MqttConnectOptions();

            connOpt.setCleanSession(true);
            connOpt.setKeepAliveInterval(30);
            connOpt.setUserName(M2MIO_USERNAME);
            connOpt.setPassword(M2MIO_PASSWORD_MD5.toCharArray());

            // Connect to Broker
            try {
                myClient = new MqttAndroidClient(getApplicationContext(), BROKER_URL, clientID);
                myClient.setCallback(this);
                myClient.connect(connOpt, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.i("MQTT-Service", "connection successful");
                        // subscribe to topic if subscriber
                        // setup topic
                        // topics on m2m.io are in the form <domain>/<stuff>/<thing>
                        String myTopic = "78467";
                        if (subscriber && plz != null) {
                            try {
                                int subQoS = 0;
                                myClient.subscribe(myTopic + "/today", subQoS);
                                myClient.subscribe(myTopic + "/weekly", subQoS);
                                myClient.subscribe(myTopic + "/alert", subQoS);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.e("MQTT-Service", "failed to connect", exception);
                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
                System.exit(-1);
            }


        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
