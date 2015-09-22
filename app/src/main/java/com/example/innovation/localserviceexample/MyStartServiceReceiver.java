package com.example.innovation.localserviceexample;

/**
 * Created by 145747 on 9/5/2015.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyStartServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("I am in onReceive");
        Intent service = new Intent(context, LocalWordService.class);
        context.startService(service);
    }
}