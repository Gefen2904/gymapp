package com.example.myfinalproject;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class Service extends android.app.Service {

    private static final int ALARM_REQUEST_CODE = 123;
    private final SelectedDays selectedDays=SelectedDays.getInstance(this);
    Calendar calendar;
    private static final long INTERVAL = 60 * 1000;
    private boolean firstNotification=true;
    private final String[] days={"","","","","","",""};
    private final String[] times={"","","","","","",""};


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        for(int i=0;i<selectedDays.getDays().size();i++){
            days[i]=selectedDays.getDays().get(i).getDay();
            times[i]=selectedDays.getDays().get(i).getTime();
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"id");
        builder.setContentTitle("My Personal Trainer");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        Notification notificationCompat = builder.build();
        startForeground(1,notificationCompat);
        startTimer();
        return super.onStartCommand(intent,flags,startId);
    }

    private void startTimer() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run() {
                calendar = Calendar.getInstance();
                if (firstNotification) {
                    for(int i=0;i<7;i++){

                        String currentTime=calendar.get(Calendar.HOUR_OF_DAY)+"/"+calendar.get(Calendar.MINUTE);
                        int currentDayNum= calendar.get(Calendar.DAY_OF_WEEK);
                        if (days[i].equals(getDayOfWeekString(currentDayNum))&&times[i].equals(currentTime))
                        {
                            scheduleAlarm();
                            firstNotification = false;
                        }
                    }
                }
            }
        }, 0, INTERVAL);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void scheduleAlarm() {
        Intent alarmIntent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }
    private String getDayOfWeekString(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
            default:
                return "Unknown";
        }
    }
}

