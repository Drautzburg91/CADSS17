package milf.wetter.cad.aktuelleswetter;

import android.annotation.SuppressLint;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by georgmohr on 29.04.17.
 */

@SuppressWarnings("ALL")
public class Datum {

    private final TextView datum;
    private final TextView mo;
    private final TextView di;
    private final TextView mi;
    private final TextView don;
    private final TextView fr;
    private final TextView sa;

    @SuppressLint("SimpleDateFormat")
    private final DateFormat dateFormat = new SimpleDateFormat("EE ,dd MMMM yyyy HH:mm");
    private final DateTimeFormatter dateFormatDay = DateTimeFormat.forPattern("E");
    private final Date date = new Date();

    public Datum(TextView datum, TextView mo, TextView di, TextView mi, TextView don, TextView fr, TextView sa) {
        this.datum = datum;
        this.mo = mo;
        this.di = di;
        this.mi = mi;
        this.don = don;
        this.fr = fr;
        this.sa = sa;
    }

    public void setDatum() {

        datum.setText(dateFormat.format(date));

    }

    public void setEins() {
        DateTime dtOrg = new DateTime(date);
        DateTime dtPlusOne = dtOrg.plusDays(1);
        mo.setText(dateFormatDay.print(dtPlusOne));
    }

    public void setZwei() {
        DateTime dtOrg = new DateTime(date);
        DateTime dtPlusOne = dtOrg.plusDays(2);
        di.setText(dateFormatDay.print(dtPlusOne));
    }

    public void setDrei() {
        DateTime dtOrg = new DateTime(date);
        DateTime dtPlusOne = dtOrg.plusDays(3);
        mi.setText(dateFormatDay.print(dtPlusOne));
    }

    public void setVier() {
        DateTime dtOrg = new DateTime(date);
        DateTime dtPlusOne = dtOrg.plusDays(4);
        don.setText(dateFormatDay.print(dtPlusOne));
    }

    public void setFuenf() {
        DateTime dtOrg = new DateTime(date);
        DateTime dtPlusOne = dtOrg.plusDays(5);
        fr.setText(dateFormatDay.print(dtPlusOne));
    }

    public void setSechs() {
        DateTime dtOrg = new DateTime(date);
        DateTime dtPlusOne = dtOrg.plusDays(6);
        sa.setText(dateFormatDay.print(dtPlusOne));
    }

}
