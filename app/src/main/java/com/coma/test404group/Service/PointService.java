package com.coma.test404group.Service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.coma.test404group.Model.Point;

/**
 * Created by Koma on 15.11.2017.
 */

public abstract class PointService extends IntentService {

    private final static String THREAD_NAME = "PointService";
    private final static int GENERATE_TIME = 1000;

    final Handler handler = new Handler();
    final Runnable runnable = new Runnable() {
        public void run() {
            Point point = getPoint();
            //checking if point still not received
            if(isPointLoaded())
                sendPoint(point);
            //making loop
            handler.postDelayed(this, GENERATE_TIME);
        }
    };

    public PointService(){
        super(THREAD_NAME);
    }


    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler.post(runnable);
    }

    @Override
    public boolean stopService(Intent name) {
        handler.removeCallbacks(runnable);
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        stopSelf();
    }

    protected void sendPoint(Point point){
        Intent intent = new Intent("GeneratedPoint");
        //putting generated point in the bundle
        Bundle b = new Bundle();
        b.putSerializable("Point", point);
        intent.putExtra("Point", b);
        //sending broadcast
        LocalBroadcastManager.getInstance(this.getBaseContext()).sendBroadcast(intent);
    }

    public abstract Point getPoint();
    public abstract boolean isPointLoaded();
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {}
}
