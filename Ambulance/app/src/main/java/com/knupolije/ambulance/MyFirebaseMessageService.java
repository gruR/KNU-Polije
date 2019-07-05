package com.knupolije.ambulance;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessageService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Toast.makeText(this, "TEST", Toast.LENGTH_SHORT).show();
        super.onMessageReceived(remoteMessage);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        manager.notify(123, notification);
        if(remoteMessage.getNotification() != null){
            Log.d("Message","New Message");
        }
    }

    @Override
    public void onNewToken(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        Log.d("Firebase Token",s);
    }
}
