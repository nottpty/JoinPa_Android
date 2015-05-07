package com.example.taweesoft.joinpa;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.util.Log;

import com.example.taweesoft.joinpa.JoiningEventView.OneEvent;
import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.MainActivity;
import com.example.taweesoft.joinpa.R;
import com.google.android.gcm.GCMBaseIntentService;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Notification System.
 * Created on 2/5/2558.
 */
public class GCMIntentService extends GCMBaseIntentService {
    // Use your PROJECT ID from Google API into SENDER_ID
    public static final String SENDER_ID = "472972918645";

    public GCMIntentService() {
        super(SENDER_ID);
    }

    @Override
    protected void onRegistered(Context context, String registrationId) {
        Resources.deviceID = registrationId;
    }

    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Resources.deviceID = registrationId;
    }

    @Override
    protected void onMessage(Context context, Intent data) {
        String message;
        // Message from PHP server
        message = data.getStringExtra("message");

        int iconID = R.drawable.noti_icon;
        if(message.contains("@@")){
            String[] msgArr = message.split("@@");
            iconID = Resources.icons.get(Integer.parseInt(msgArr[0]));
            message = msgArr[1];
            Log.d("OOO : " , message);
            Resources.isNewData = true;
        }

        Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), iconID);

        // Open a new activity called GCMMessageView
        Intent intent = new Intent(this, FirstActivity.class);
        // Pass data to the new activity
        intent.putExtra("message", message);
        // Starts the activity on notification click
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        // Create the notification with a notification builder
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); // Play notification sound
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), sound);
        r.play();


        Notification notification = new Notification.Builder(this)
                .setSmallIcon(iconID)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Join Pa!!")
                .setContentText(message).setContentIntent(pIntent)
                .getNotification();
        // Remove the notification on click
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(R.string.app_name, notification);

        {
            // Wake Android Device when notification received
            PowerManager pm = (PowerManager) context
                    .getSystemService(Context.POWER_SERVICE);
            final PowerManager.WakeLock mWakelock = pm.newWakeLock(
                    PowerManager.FULL_WAKE_LOCK
                            | PowerManager.ACQUIRE_CAUSES_WAKEUP, "GCM_PUSH");
            mWakelock.acquire();

            // Timer before putting Android Device to sleep mode.
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                public void run() {
                    mWakelock.release();
                }
            };
            timer.schedule(task, 5000);
        }

    }

    @Override
    protected void onError(Context arg0, String errorId) {

        Log.e(TAG, "onError: errorId=" + errorId);
    }

}
