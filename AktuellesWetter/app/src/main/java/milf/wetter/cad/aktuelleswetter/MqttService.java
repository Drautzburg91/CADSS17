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

    public String plz;
    Intent in = new Intent();
    private Intent intent;
    MqttAndroidClient myClient;
    MqttConnectOptions connOpt;
    String lastPlz;
    boolean bool;

    static final String BROKER_URL = "tcp://ec2-34-210-210-13.us-west-2.compute.amazonaws.com:1883";
    static final String M2MIO_USERNAME = "cadAndroid";
    static final String M2MIO_PASSWORD_MD5 = "cadAndroid";

    // the following two flags control whether this example is a publisher, a subscriber or both
    static final Boolean subscriber = true;

    public void connectionLost(Throwable t) {
        System.out.println("Connection lost!");
        // code to reconnect to the broker would go here if desired
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Log.e("messageArrived: ",new String (message.getPayload()));
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
        bool = intent.getBooleanExtra("alert",true);
        if(bool == false) {
            Log.e("Boolean(alert,true)","true");
            lastPlz = intent.getStringExtra("lastPlz");
        }

        if(intent.getStringExtra("plz")!=null) {
            plz = intent.getStringExtra("plz");
            Log.i("onStartCommande",plz);
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
                       // String myTopic = "78467";

                        if(bool == false) {
                            Log.e("Boolean(alert,true)", lastPlz);
                            try {
                                myClient.unsubscribe(lastPlz + "/today/cep");
                                myClient.unsubscribe(lastPlz + "/weekly/cep");
                                myClient.unsubscribe(lastPlz + "/alert");
                            } catch (MqttException e) {
                                e.printStackTrace();
                            }
                        }
                        if (subscriber && plz != null) {
                            try {
                                int subQoS = 0;
                                myClient.subscribe(plz + "/today/cep", subQoS);
                                myClient.subscribe(plz + "/weekly/cep", subQoS);
                                myClient.subscribe(plz + "/alert", subQoS);


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
