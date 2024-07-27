package com.example.myfinalproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {
    int NOTIFICATION_ID=3;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"id");
        builder.setContentTitle("My Personal Trainer");
        builder.setContentText("Just reminding you about your workout is in 30 minutes hope you enjoy it");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        Notification notificationCompat = builder.build();
        NotificationManager managerCompat = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);
    }
}
