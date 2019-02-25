package com.example.etablissementmanagement.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.etablissementmanagement.R;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.etablissementmanagement.Notification.App.CHANNEL_ID;

public class BuildNotification {

    public static final String NOTIFICATION_TITLE = "MyNotification";
    public static final String NOTIFICATION_DESCRIPTION = "New Etablissement Inserted";

    private NotificationManagerCompat notificationManager;
    private Context context;
    private Class aClass;

    /**
     *
     * @param notificationManager : to show the notification user .notify()
     * @param context : for use in intent
     * @param aClass  : for use in intent
     */
    public BuildNotification(NotificationManagerCompat notificationManager, Context context, Class aClass) {
        this.notificationManager = notificationManager;
        this.context = context;
        this.aClass = aClass;
    }

    /**
     * Function to create a notification and put it in the channel created in App.java
     */
    public void displayNotification() {
        Intent intent = new Intent(context, aClass);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(NOTIFICATION_DESCRIPTION)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(1, notification);
    }

}
