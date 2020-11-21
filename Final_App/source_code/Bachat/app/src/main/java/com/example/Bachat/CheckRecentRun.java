package com.example.Bachat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class CheckRecentRun extends Service {
    private NotificationManagerCompat notificationManager;

    private final static String TAG = "CheckRecentPlay";
    private static Long MILLISECS_PER_DAY = 86400000L;
    private static Long MILLISECS_PER_MIN = 60000L;

    //private static long delay = MILLISECS_PER_MIN * 1;   // 1 minutes (for testing)
    private static long delay = MILLISECS_PER_DAY * 3;   // 3 days
    private static long delay_day = MILLISECS_PER_DAY;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager =NotificationManagerCompat.from(this);

        Log.v(TAG, "Service started");
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS, MODE_PRIVATE);

        // Are notifications enabled?
        if (settings.getBoolean("enabled", true)) {
            // Is it time for a notification?
            if (settings.getLong("lastRun", Long.MAX_VALUE) < System.currentTimeMillis() - delay)
                sendNotification();
            if (settings.getLong("lastRun", Long.MAX_VALUE) < System.currentTimeMillis() - delay_day)
                sendNotificationDay();

        } else {
            Log.i(TAG, "Notifications are disabled");
        }

        // Set an alarm for the next time this service should run:
        setAlarm();

        Log.v(TAG, "Service stopped");
        stopSelf();
    }

    public void setAlarm() {

        Intent serviceIntent = new Intent(this, CheckRecentRun.class);
        PendingIntent pi = PendingIntent.getService(this, 131313, serviceIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pi);
        Log.v(TAG, "Alarm set");
    }

    public void sendNotification() {
        Intent LoginIntent = new Intent(this, LoginActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0,LoginIntent,0);

        Notification notification = new NotificationCompat.Builder(this,App.CHANNEL1_ID)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.appicon)
                .setContentTitle("We Miss You!")
                .setContentText("You are important to us... ")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build();

        notificationManager.notify(1,notification);


        /*@SuppressWarnings("deprecation")
        Notification noti = new Notification.Builder(this)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(this, 131314, mainIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT))
                .setContentTitle("We Miss You!")
                .setContentText("You are important to us... ")
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_account_circle)
                .setTicker("We Miss You!")
                .setWhen(System.currentTimeMillis())
                .getNotification();

        NotificationManager notificationManager
                = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(131315, noti);*/

        Log.v(TAG, "Notification sent");
    }


    public void sendNotificationDay() {
        Intent LoginIntent = new Intent(this, LoginActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0,LoginIntent,0);

        Notification notification = new NotificationCompat.Builder(this,App.CHANNEL2_ID)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.appicon)
                .setContentTitle("Hey there!")
                .setContentText("Do not forget to log your expenses today...")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build();

        notificationManager.notify(1,notification);


        /*@SuppressWarnings("deprecation")
        Notification noti = new Notification.Builder(this)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(this, 131314, mainIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT))
                .setContentTitle("We Miss You!")
                .setContentText("You are important to us... ")
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_account_circle)
                .setTicker("We Miss You!")
                .setWhen(System.currentTimeMillis())
                .getNotification();

        NotificationManager notificationManager
                = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(131315, noti);*/

        Log.v(TAG, "Notification sent");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
