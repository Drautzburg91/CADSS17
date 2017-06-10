package milf.wetter.cad.aktuelleswetter;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

/**
 * Created by georgmohr on 25.05.17.
 */

public class Chart {

    int [] x = new int[]{1,2,3,4,5,6};
   // int [] max1 = new int[]{20,22,23,20,25,24};
   // int [] min1 = new int[]{10,14,17,18,15,20};

   public void makeChart(LinearLayout tempChart, Context context, int [] max, int [] min){

        XYSeries maxSeries = new XYSeries("max");
        XYSeries minSeries = new XYSeries("min");

        for(int i=0;i<x.length;i++){
            maxSeries.add(x[i], max[i]);
            minSeries.add(x[i],min[i]);
        }
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(maxSeries);
        dataset.addSeries(minSeries);

        XYSeriesRenderer maxRenderer = new XYSeriesRenderer();
        maxRenderer.setColor(Color.RED);
        maxRenderer.setPointStyle(PointStyle.CIRCLE);
        maxRenderer.setFillPoints(true);
        maxRenderer.setLineWidth(3);
        maxRenderer.setDisplayChartValues(true);
        maxRenderer.setChartValuesTextSize(20);

        XYSeriesRenderer minRenderer = new XYSeriesRenderer();
        minRenderer.setColor(Color.BLUE);
        minRenderer.setPointStyle(PointStyle.CIRCLE);
        minRenderer.setFillPoints(true);
        minRenderer.setLineWidth(3);
        minRenderer.setDisplayChartValues(true);
        minRenderer.setChartValuesTextSize(20);

        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setXTitle("");
        multiRenderer.setYTitle("");
        multiRenderer.setChartTitle("");

        multiRenderer.setApplyBackgroundColor(false);
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
        multiRenderer.setMarginsColor(Color.parseColor("#0099cc"));
        multiRenderer.addSeriesRenderer(maxRenderer);
        multiRenderer.addSeriesRenderer(minRenderer);
        multiRenderer.setShowLegend(false);
        multiRenderer.setPanEnabled(false,false);
        GraphicalView graph = (GraphicalView) ChartFactory.getLineChartView(context,dataset, multiRenderer);
        tempChart.addView(graph);

    }

}
