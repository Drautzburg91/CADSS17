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

    public void setWetterEins(String wetter){

        switch(wetter){
            case "01d":
                wetterMO.setImageResource(R.drawable.d01);
                break;
            case "02d":
                wetterMO.setImageResource(R.drawable.d02);
                break;
            case "03d":
                wetterMO.setImageResource(R.drawable.d03);
                break;
            case "04d":
                wetterMO.setImageResource(R.drawable.d04);
                break;
            case "09d":
                wetterMO.setImageResource(R.drawable.d09);
                break;
            case "10d":
                wetterMO.setImageResource(R.drawable.d10);
                break;
            case "11d":
                wetterMO.setImageResource(R.drawable.d11);
                break;
            case "13d":
                wetterMO.setImageResource(R.drawable.d13);
                break;
            case "50d":
                wetterMO.setImageResource(R.drawable.d50);
                break;
            case "01n":
                wetterMO.setImageResource(R.drawable.n01);
                break;
            case "02n":
                wetterMO.setImageResource(R.drawable.n02);
                break;
            case "03n":
                wetterMO.setImageResource(R.drawable.n03);
                break;
            case "04n":
                wetterMO.setImageResource(R.drawable.n04);
                break;
            case "09n":
                wetterMO.setImageResource(R.drawable.n09);
                break;
            case "10n":
                wetterMO.setImageResource(R.drawable.n10);
                break;
            case "11n":
                wetterMO.setImageResource(R.drawable.n11);
                break;
            case "13n":
                wetterMO.setImageResource(R.drawable.n13);
                break;
            case "50n":
                wetterMO.setImageResource(R.drawable.n50);
                break;



        }



    }
    public void setWetterZwei(String wetter){

        switch(wetter){
            case "01d":
                wetterDI.setImageResource(R.drawable.d01);
                break;
            case "02d":
                wetterDI.setImageResource(R.drawable.d02);
                break;
            case "03d":
                wetterDI.setImageResource(R.drawable.d03);
                break;
            case "04d":
                wetterDI.setImageResource(R.drawable.d04);
                break;
            case "09d":
                wetterDI.setImageResource(R.drawable.d09);
                break;
            case "10d":
                wetterDI.setImageResource(R.drawable.d10);
                break;
            case "11d":
                wetterDI.setImageResource(R.drawable.d11);
                break;
            case "13d":
                wetterDI.setImageResource(R.drawable.d13);
                break;
            case "50d":
                wetterDI.setImageResource(R.drawable.d50);
                break;
            case "01n":
                wetterDI.setImageResource(R.drawable.n01);
                break;
            case "02n":
                wetterDI.setImageResource(R.drawable.n02);
                break;
            case "03n":
                wetterDI.setImageResource(R.drawable.n03);
                break;
            case "04n":
                wetterDI.setImageResource(R.drawable.n04);
                break;
            case "09n":
                wetterDI.setImageResource(R.drawable.n09);
                break;
            case "10n":
                wetterDI.setImageResource(R.drawable.n10);
                break;
            case "11n":
                wetterDI.setImageResource(R.drawable.n11);
                break;
            case "13n":
                wetterDI.setImageResource(R.drawable.n13);
                break;
            case "50n":
                wetterDI.setImageResource(R.drawable.n50);
                break;



        }

    }
    public void setWetterDrei(String wetter){

        switch(wetter){
            case "01d":
                wetterMI.setImageResource(R.drawable.d01);
                break;
            case "02d":
                wetterMI.setImageResource(R.drawable.d02);
                break;
            case "03d":
                wetterMI.setImageResource(R.drawable.d03);
                break;
            case "04d":
                wetterMI.setImageResource(R.drawable.d04);
                break;
            case "09d":
                wetterMI.setImageResource(R.drawable.d09);
                break;
            case "10d":
                wetterMI.setImageResource(R.drawable.d10);
                break;
            case "11d":
                wetterMI.setImageResource(R.drawable.d11);
                break;
            case "13d":
                wetterMI.setImageResource(R.drawable.d13);
                break;
            case "50d":
                wetterMI.setImageResource(R.drawable.d50);
                break;
            case "01n":
                wetterMI.setImageResource(R.drawable.n01);
                break;
            case "02n":
                wetterMI.setImageResource(R.drawable.n02);
                break;
            case "03n":
                wetterMI.setImageResource(R.drawable.n03);
                break;
            case "04n":
                wetterMI.setImageResource(R.drawable.n04);
                break;
            case "09n":
                wetterMI.setImageResource(R.drawable.n09);
                break;
            case "10n":
                wetterMI.setImageResource(R.drawable.n10);
                break;
            case "11n":
                wetterMI.setImageResource(R.drawable.n11);
                break;
            case "13n":
                wetterMI.setImageResource(R.drawable.n13);
                break;
            case "50n":
                wetterMI.setImageResource(R.drawable.n50);
                break;



        }


    }
    public void setWetterVier(String wetter){


        switch(wetter){
            case "01d":
                wetterDO.setImageResource(R.drawable.d01);
                break;
            case "02d":
                wetterDO.setImageResource(R.drawable.d02);
                break;
            case "03d":
                wetterDO.setImageResource(R.drawable.d03);
                break;
            case "04d":
                wetterDO.setImageResource(R.drawable.d04);
                break;
            case "09d":
                wetterDO.setImageResource(R.drawable.d09);
                break;
            case "10d":
                wetterDO.setImageResource(R.drawable.d10);
                break;
            case "11d":
                wetterDO.setImageResource(R.drawable.d11);
                break;
            case "13d":
                wetterDO.setImageResource(R.drawable.d13);
                break;
            case "50d":
                wetterDO.setImageResource(R.drawable.d50);
                break;
            case "01n":
                wetterDO.setImageResource(R.drawable.n01);
                break;
            case "02n":
                wetterDO.setImageResource(R.drawable.n02);
                break;
            case "03n":
                wetterDO.setImageResource(R.drawable.n03);
                break;
            case "04n":
                wetterDO.setImageResource(R.drawable.n04);
                break;
            case "09n":
                wetterDO.setImageResource(R.drawable.n09);
                break;
            case "10n":
                wetterDO.setImageResource(R.drawable.n10);
                break;
            case "11n":
                wetterDO.setImageResource(R.drawable.n11);
                break;
            case "13n":
                wetterDO.setImageResource(R.drawable.n13);
                break;
            case "50n":
                wetterDO.setImageResource(R.drawable.n50);
                break;



        }


    }
    public void setWetterFuenf(String wetter){

        switch(wetter){
            case "01d":
                wetterFR.setImageResource(R.drawable.d01);
                break;
            case "02d":
                wetterFR.setImageResource(R.drawable.d02);
                break;
            case "03d":
                wetterFR.setImageResource(R.drawable.d03);
                break;
            case "04d":
                wetterFR.setImageResource(R.drawable.d04);
                break;
            case "09d":
                wetterFR.setImageResource(R.drawable.d09);
                break;
            case "10d":
                wetterFR.setImageResource(R.drawable.d10);
                break;
            case "11d":
                wetterFR.setImageResource(R.drawable.d11);
                break;
            case "13d":
                wetterFR.setImageResource(R.drawable.d13);
                break;
            case "50d":
                wetterFR.setImageResource(R.drawable.d50);
                break;
            case "01n":
                wetterFR.setImageResource(R.drawable.n01);
                break;
            case "02n":
                wetterFR.setImageResource(R.drawable.n02);
                break;
            case "03n":
                wetterFR.setImageResource(R.drawable.n03);
                break;
            case "04n":
                wetterFR.setImageResource(R.drawable.n04);
                break;
            case "09n":
                wetterFR.setImageResource(R.drawable.n09);
                break;
            case "10n":
                wetterFR.setImageResource(R.drawable.n10);
                break;
            case "11n":
                wetterFR.setImageResource(R.drawable.n11);
                break;
            case "13n":
                wetterFR.setImageResource(R.drawable.n13);
                break;
            case "50n":
                wetterFR.setImageResource(R.drawable.n50);
                break;



        }



    }
    public void setWetterSechs(String wetter){

        switch(wetter){
            case "01d":
                wetterSA.setImageResource(R.drawable.d01);
                break;
            case "02d":
                wetterSA.setImageResource(R.drawable.d02);
                break;
            case "03d":
                wetterSA.setImageResource(R.drawable.d03);
                break;
            case "04d":
                wetterSA.setImageResource(R.drawable.d04);
                break;
            case "09d":
                wetterSA.setImageResource(R.drawable.d09);
                break;
            case "10d":
                wetterSA.setImageResource(R.drawable.d10);
                break;
            case "11d":
                wetterSA.setImageResource(R.drawable.d11);
                break;
            case "13d":
                wetterSA.setImageResource(R.drawable.d13);
                break;
            case "50d":
                wetterSA.setImageResource(R.drawable.d50);
                break;
            case "01n":
                wetterSA.setImageResource(R.drawable.n01);
                break;
            case "02n":
                wetterSA.setImageResource(R.drawable.n02);
                break;
            case "03n":
                wetterSA.setImageResource(R.drawable.n03);
                break;
            case "04n":
                wetterSA.setImageResource(R.drawable.n04);
                break;
            case "09n":
                wetterSA.setImageResource(R.drawable.n09);
                break;
            case "10n":
                wetterSA.setImageResource(R.drawable.n10);
                break;
            case "11n":
                wetterSA.setImageResource(R.drawable.n11);
                break;
            case "13n":
                wetterSA.setImageResource(R.drawable.n13);
                break;
            case "50n":
                wetterSA.setImageResource(R.drawable.n50);
                break;



        }



    }
}
