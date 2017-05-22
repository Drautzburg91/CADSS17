package milf.wetter.cad.aktuelleswetter;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by georgmohr on 30.04.17.
 */

public class Wetter {

    ImageView wetterIcon;
    ImageView wetterMO;
    ImageView wetterDI;
    ImageView wetterMI;
    ImageView wetterDO;
    ImageView wetterFR;
    ImageView wetterSA;
    Context context;
    MqttService m ;

    public Wetter(Context context,ImageView wetterIcon,ImageView wetterMO,ImageView wetterDI,ImageView wetterMI,ImageView wetterDO,ImageView wetterFR,ImageView wetterSA){
        this.wetterIcon=wetterIcon;
        this.wetterMO=wetterMO;
        this.wetterDI=wetterDI;
        this.wetterMI=wetterMI;
        this.wetterDO=wetterDO;
        this.wetterFR=wetterFR;
        this.wetterSA=wetterSA;
        this.context=context;

    }

    public void setWetter(String wetter){


        switch(wetter){
            case "01d":
                wetterIcon.setImageResource(R.drawable.d01);
                break;
            case "02d":
                wetterIcon.setImageResource(R.drawable.d02);
                break;
            case "03d":
                wetterIcon.setImageResource(R.drawable.d03);
                break;
            case "04d":
                wetterIcon.setImageResource(R.drawable.d04);
                break;
            case "09d":
                wetterIcon.setImageResource(R.drawable.d09);
                break;
            case "10d":
                wetterIcon.setImageResource(R.drawable.d10);
                break;
            case "11d":
                wetterIcon.setImageResource(R.drawable.d11);
                break;
            case "13d":
                wetterIcon.setImageResource(R.drawable.d13);
                break;
            case "50d":
                wetterIcon.setImageResource(R.drawable.d50);
                break;
            case "01n":
                wetterIcon.setImageResource(R.drawable.n01);
                break;
            case "02n":
                wetterIcon.setImageResource(R.drawable.n02);
                break;
            case "03n":
                wetterIcon.setImageResource(R.drawable.n03);
                break;
            case "04n":
                wetterIcon.setImageResource(R.drawable.n04);
                break;
            case "09n":
                wetterIcon.setImageResource(R.drawable.n09);
                break;
            case "10n":
                wetterIcon.setImageResource(R.drawable.n10);
                break;
            case "11n":
                wetterIcon.setImageResource(R.drawable.n11);
                break;
            case "13n":
                wetterIcon.setImageResource(R.drawable.n13);
                break;
            case "50n":
                wetterIcon.setImageResource(R.drawable.n50);
                break;



        }


    }

    public void setWetterEins(){
        wetterMO.setImageResource(R.drawable.d01);
    }
    public void setWetterZwei(){
        wetterDI.setImageResource(R.drawable.d02);
    }
    public void setWetterDrei(){
        wetterMI.setImageResource(R.drawable.d09);
    }
    public void setWetterVier(){
        wetterDO.setImageResource(R.drawable.d11);
    }
    public void setWetterFuenf(){
        wetterFR.setImageResource(R.drawable.d10);
    }
    public void setWetterSechs(){
        wetterSA.setImageResource(R.drawable.d50);
    }
}
