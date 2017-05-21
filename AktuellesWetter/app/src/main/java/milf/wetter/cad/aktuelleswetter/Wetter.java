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

    public void setWetter(){


        wetterIcon.setImageResource(R.drawable.regen);
    }

    public void setWetterEins(){
        wetterMO.setImageResource(R.drawable.sonne_gewitter_mini);
    }
    public void setWetterZwei(){
        wetterDI.setImageResource(R.drawable.schnee_mini);
    }
    public void setWetterDrei(){
        wetterMI.setImageResource(R.drawable.sonne_regen_mini);
    }
    public void setWetterVier(){
        wetterDO.setImageResource(R.drawable.sonnig_mini);
    }
    public void setWetterFuenf(){
        wetterFR.setImageResource(R.drawable.schnee_wind_mini);
    }
    public void setWetterSechs(){
        wetterSA.setImageResource(R.drawable.sonnig_mit_schleier_mini);
    }
}
