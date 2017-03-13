package com.boot.accommodation.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.TourNotificationDTO;
import com.boot.accommodation.vp.category.NotificationActivity;
import com.boot.accommodation.vp.home.HomeActivity;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 3:29 PM 13-06-2016
 */
public class RemindReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            TourNotificationDTO scheduleDTO = intent.getParcelableExtra(Constants.ALARM_BUNDLE);
            String title = scheduleDTO.getTitle();
            String message = scheduleDTO.getContent();
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            Notification.Builder mBuilder =
                    new Notification.Builder(context)
                            .setSmallIcon(R.drawable.ic_notification)
                            .setLargeIcon(BitmapFactory.decodeResource(JoCoApplication.getInstance().getResources()
                                    ,R.mipmap.ic_launcher))
                            .setContentTitle(title)
                            .setContentText(message)
                            .setVibrate(new long[]{30000})
                            .setSound(defaultSoundUri);
            mBuilder.setAutoCancel(true);
            Intent resultIntent = new Intent(context, NotificationActivity.class);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(HomeActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, mBuilder.build());
        }
    }
}
