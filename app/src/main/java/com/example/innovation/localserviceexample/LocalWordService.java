package com.example.innovation.localserviceexample;

/**
 * Created by 145747 on 9/5/2015.
 */
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class LocalWordService extends Service {
    private final IBinder mBinder = new MyBinder();
    private ArrayList<String> list = new ArrayList<String>();
    private String TAG = "LocalServiceExample";
    private boolean isRunning = false;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int i = 0;
            while(true){
                System.out.println("I am in the thread - Shashi with i : " + i);
                if(i%60 == 0) {
                    createNotification(i);
                }

                i++;
                if(i == 10000)
                    break;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public void onCreate() {
        Log.i(TAG, "Service onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(isRunning ) {
            System.out.println("Service is already running");
        }
        else {
            new Thread(runnable).start();
            isRunning = true;
            System.out.println("Started a new service");
        }
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {

        System.out.println("I am in onBind");
        return mBinder;
    }

    public class MyBinder extends Binder {
        LocalWordService getService() {
            return LocalWordService.this;
        }
    }

    //New 09-09
    @Override
    public void onStart(Intent intent, int startId) {
        System.out.println("I am in onStart");
        Toast.makeText(this, " MyService Started", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        System.out.println("I am in onDestroy");
        Toast.makeText(this, "MyService Stopped", Toast.LENGTH_LONG).show();
    }


    public List<String> getWordList() {
        return list;
    }

    public void createNotification(int num){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_template_icon_bg)
                .setContentTitle("Service Check")
                .setContentText("Service is Running with Value: " +num)
                .setCategory(Notification.CATEGORY_PROMO)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setPriority(Notification.PRIORITY_HIGH)
                .setVibrate(new long[] {1000, 1000, 1000, 1000, 1000})
                .setAutoCancel(true);

        Intent resultIntent = new Intent(this,MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        System.out.println("I am almost out before createNotification");
        notificationManager.notify(1, builder.build());
        System.out.println("I am almost out after createNotification");

    }
}
