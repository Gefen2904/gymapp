    package com.example.myfinalproject;

    import android.app.NotificationChannel;
    import android.app.NotificationManager;
    import android.content.Intent;
    import android.os.Build;
    import android.os.Bundle;

    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;

    public class MainActivity extends AppCompatActivity {
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            //Intent serviceIntent = new Intent(this, Service.class);

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if(Build.VERSION.SDK_INT > 26) {
                NotificationChannel channel = new NotificationChannel("id","alert notifications",NotificationManager.IMPORTANCE_HIGH);
                manager.createNotificationChannel(channel);
            }

        }
    }