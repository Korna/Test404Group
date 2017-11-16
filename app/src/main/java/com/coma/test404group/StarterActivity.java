package com.coma.test404group;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import com.coma.test404group.DI.DaggerStarterActivityComponent;
import com.coma.test404group.DI.SciBuilderModule;
import com.coma.test404group.DI.StarterSurfaceModule;
import com.coma.test404group.Model.Point;
import com.coma.test404group.Service.GeneratorService;
import com.scichart.charting.model.dataSeries.IXyDataSeries;
import com.scichart.charting.model.dataSeries.XyDataSeries;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.charting.visuals.renderableSeries.IRenderableSeries;
import com.scichart.core.framework.UpdateSuspender;
import com.scichart.drawing.utility.ColorUtil;
import com.scichart.extensions.builders.SciChartBuilder;

import java.util.Date;
import java.util.Random;

import javax.inject.Inject;


public class StarterActivity extends AppCompatActivity {
    //scichart objects injection
    @Inject
    volatile SciChartSurface surface;
    @Inject
    volatile SciChartBuilder sciChartBuilder;
    //service
    private Intent service;
    //previous dot
    private final Point<Double, Date> pd = new Point(0.0, new Date(0));
    //current dot
    private Point<Double, Date> point = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);
        //dependency injection
        DaggerStarterActivityComponent.builder()
                .sciBuilderModule(new SciBuilderModule(this))
                .starterSurfaceModule(new StarterSurfaceModule(this))
                .build()
                .inject(this);

        //service initialization
        service = new Intent(StarterActivity.this, GeneratorService.class);
        startService(service);

        //start custom receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("GeneratedPoint"));
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public synchronized void onReceive(Context context, Intent intent) {
            try {
                //bundle from pointservice
                Bundle b = intent.getBundleExtra("Point");
                point = (Point) b.getSerializable("Point");
                //using scichart method for nested operations
                UpdateSuspender.using(surface, insertPoint);
            }catch(ClassCastException cce){
                cce.getStackTrace();//catching invalid generic points
                point = null;
            }

        }
    };

    private final Runnable insertPoint = new Runnable() {
        @Override
        public void run() {
             //setting up data collection
             final IXyDataSeries<Date, Double> dataSeries = new XyDataSeries<>(Date.class, Double.class);
             dataSeries.setAcceptsUnsortedData(true);

             try {
                 //first point for line
                 dataSeries.append(pd.getDate(), pd.getRate());
                 //refresh point position from broadcast
                 pd.setDate(point.getDate());
                 pd.setRate(point.getRate());
                 //second point for line
                 dataSeries.append(pd.getDate(), pd.getRate());

                 //generate color
                 final Random random = new Random();
                 final int color = ColorUtil.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
                 //generate line
                 final IRenderableSeries line = generateLine(color, dataSeries);

                 //add line
                 surface.getRenderableSeries().add(line);
                 //draw horizontal line at last line point
                 surface.getAnnotations().get(0).setY1(pd.getRate());
             }catch(NullPointerException npe){
                 npe.getStackTrace();//catching null points
             }

        }
    };


    private IRenderableSeries generateLine(int color, final IXyDataSeries<Date, Double> dataSeries){
        final IRenderableSeries rs = sciChartBuilder.newMountainSeries()
                .withStrokeStyle(color)
                .withAreaFillLinearGradientColors(color, ColorUtil.argb(color, 0.5f))
                .withDataSeries(dataSeries).build();
        return rs;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(service);
    }
}
