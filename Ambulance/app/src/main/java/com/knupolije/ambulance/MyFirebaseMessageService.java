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

import static android.content.ContentValues.TAG;

public class MyFirebaseMessageService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.i("Message Received","On Message Received" +remoteMessage.getData().get("title"));
        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
        Intent intent = new Intent(this,MainActivity.class);
        notificationUtils.showSmallNotification(remoteMessage.getData().get("title"),remoteMessage.getData().get("body"),intent);

    }

//    @Override
//    public void onNewToken(String s) {
//        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
//        Log.i("FIREBASE TOKEN","Token is"+ s);
//    }
}
