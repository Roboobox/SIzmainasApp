package com.avg.roboo.stunduizmainas;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
//import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
//import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by roboo on 02.12.2017.
 */

public class FCMReciever extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        //Log.d("TestFire", "From: " + remoteMessage.getFrom());

        if(remoteMessage.getData().size() > 0){
            Log.d("TestFire", "Message data payload: " + remoteMessage.getData());
            Log.d("TestFire", "Message data payload: " + remoteMessage.getData().get("message"));
        }

        if(remoteMessage.getNotification() != null){
            Log.d("TestFire", "Notification body: " + remoteMessage.getNotification().getBody());
        }

        SharedPreferences sharedPref = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String klaseSaved = sharedPref.getString("userChoiceKlase", "");

        String notifyKlase = remoteMessage.getData().get("klase");
        String message = remoteMessage.getData().get("message");

        if(klaseSaved.equals(notifyKlase)) {
            sendNotification(message, klaseSaved);
        }
        else{
            Log.d("SubscribtionCheck", "Unsubscribed from " + notifyKlase);
            FirebaseMessaging.getInstance().unsubscribeFromTopic(notifyKlase);
        }

    }

    public void sendNotification(String messageBody, String toKlase) {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.LOLLIPOP);
        Intent intent = new Intent(this, SplashScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Stundu Izmaiņas", messageBody);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        Log.d("Notifications",  android.os.Build.VERSION.SDK_INT + " - curr ver");
        Log.d("Notifications",  android.os.Build.VERSION_CODES.LOLLIPOP + " - lolipop ver");
        Log.d("Notifications", useWhiteIcon + " - uses white icon");
        notificationBuilder.setSmallIcon(R.drawable.ic_stat_icon_trnsp);
        notificationBuilder.setColor(Color.RED);
        if(!useWhiteIcon) {
            notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_icon_notifications));
        }
       // }
        //else{
            //notificationBuilder.setSmallIcon(R.mipmap.ic_launcher_icon);
        //}
        //SharedPreferences sharedPref = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        //String klaseSaved = sharedPref.getString("userChoiceKlase", "");
        notificationBuilder.setContentTitle("Jaunas izmaiņas " + toKlase + " klasei!");
        notificationBuilder.setContentText(messageBody);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(defaultSoundUri);
        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
