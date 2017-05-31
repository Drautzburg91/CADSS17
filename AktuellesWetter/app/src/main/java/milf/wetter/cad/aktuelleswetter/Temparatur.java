package milf.wetter.cad.aktuelleswetter;

import android.widget.TextView;

/**
 * Created by georgmohr on 22.05.17.
 */

public class Temparatur {

     TextView momTemp;

     TextView maxTempMo;
     TextView maxTempDi;
     TextView maxTempMi;
     TextView maxTempDo;
     TextView maxTempFr;
     TextView maxTempSa;

     TextView minTempMo;
     TextView minTempDi;
     TextView minTempMi;
     TextView minTempDo;
     TextView minTempFr;
     TextView minTempSa;


    public Temparatur(TextView maxTempMo, TextView maxTempDi, TextView maxTempMi, TextView maxTempDo, TextView maxTempFr, TextView maxTempSa,
                      TextView minTempMo, TextView minTempDi, TextView minTempMi, TextView minTempDo, TextView minTempFr, TextView minTempSa,
                      TextView momTemp){

        this.momTemp=momTemp;
        this.maxTempMo=maxTempMo;this.maxTempDi=maxTempDi;
        this.maxTempMi=maxTempMi;this.maxTempDo=maxTempDo;
        this.maxTempFr=maxTempFr;this.maxTempSa=maxTempSa;
        this.minTempMo=minTempMo;this.minTempDi=minTempDi;
        this.minTempMi=minTempMi;this.minTempDo=minTempDo;
        this.minTempFr=minTempFr;this.minTempSa=minTempSa;
    }

    public void setTemp(String temp){
        momTemp.setText(temp+"°C");
    }
    public void setTempMo(String min, String max){
        maxTempMo.setText(max+"°C");
        minTempMo.setText(min+"°C");
    }
    public void setTempDi(String min, String max){
        maxTempDi.setText(max+"°C");
        minTempDi.setText(min+"°C");
    }
    public void setTempMi(String min, String max){
        maxTempMi.setText(max+"°C");
        minTempMi.setText(min+"°C");
    }
    public void setTempDo(String min, String max){
        maxTempDo.setText(max+"°C");
        minTempDo.setText(min+"°C");
    }
    public void setTempFr(String min, String max){
        maxTempFr.setText(max+"°C");
        minTempFr.setText(min+"°C");
    }
    public void setTempSa(String min, String max){
        maxTempSa.setText(max+"°C");
        minTempSa.setText(min+"°C");
    }
}
