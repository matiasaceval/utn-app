package ar.edu.utn.mdp.utnapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import me.pushy.sdk.Pushy;

public class PushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Attempt to extract the "title" property from the data payload, or fallback to app shortcut label
        String notificationTitle = intent.getStringExtra("title") != null ? intent.getStringExtra("title") : context.getPackageManager().getApplicationLabel(context.getApplicationInfo()).toString();

        // Attempt to extract the "message" property from the data payload: {"message":"Hello World!"}
        String notificationText = intent.getStringExtra("message") != null ? intent.getStringExtra("message") : "Test notification";

        // Prepare a notification with vibration, sound and lights
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setAutoCancel(true)
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setColor(ContextCompat.getColor(context, R.color.white))
                .setSmallIcon(R.drawable.ic_utn_notification)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setLights(Color.RED, 1000, 1000)
                .setVibrate(new long[]{0, 400, 250, 400})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE));

        // Automatically configure a Notification Channel for devices running Android O+
        Pushy.setNotificationChannel(builder, context);

        // Get an instance of the NotificationManager service
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Build the notification and display it
        notificationManager.notify(1, builder.build());
    }
}
