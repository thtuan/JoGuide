package com.boot.accommodation.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.boot.accommodation.R;
import com.boot.accommodation.util.TraceExceptionUtils;
import com.boot.accommodation.vp.category.NotificationActivity;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 5:51 PM 17-05-2016
 */
public class GCMListener extends GcmListenerService {
    public final static String TAG = "GCM";
    private int requestCode = 1;
    private String createDate;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        /*createDate = DateUtil.convertDateWithFormat(data.getString("remindTime"),DateUtil.FORMAT_DATE_TIME_ZONE,
                DateUtil.FORMAT_DATE);*/
        try {
            Bundle noti = data.getBundle("notification");
            //        Bundle payload = data.getBundle("data");
            String creator = data.getString("creator");
            //        String create = payload.getString("createAt");
            String title = noti.getString("title");
            String content = noti.getString("body");
            Log.d(TAG, "From: " + from);
            Log.d(TAG, "Message: " + title);
            sendNotification(title, content);
        }
        catch (NullPointerException e){
            Log.e("Error", TraceExceptionUtils.getReportFromThrowable(e));
        }

        /**
         * Can lay duoc danh sach id tour tham gia
         */
        /*for(String id : listId) {
            if (from.startsWith("/notice/" + id)) {
                sendNotification("notice",message, id);

                // message received from some topic.
            } else {
                // normal downstream message.
                sendNotification(message);
            }
            if (from.startsWith("/remind/" + id)) {
                sendNotification("remind",message, id);

                // message received from some topic.
            } else {
                // normal downstream message.
                sendNotification(message);
            }
            if (from.startsWith("/attention/" + id)) {
                sendNotification("attention",message, id);

                // message received from some topic.
            } else {
                // normal downstream message.
                sendNotification(message);
            }
        }*/

    }

    /**
     * send notification
     * @param title
     * @param body
     */
    private void sendNotification(String title, String body) {
        Intent resultIntent = new Intent(this, NotificationActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,requestCode,resultIntent,PendingIntent
                .FLAG_ONE_SHOT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSmallIcon(R.drawable.ic_notification);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.mipmap.ic_launcher));
        mBuilder.setContentInfo(createDate);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(body);
        mBuilder.setSound(defaultSoundUri);
        mBuilder.setAutoCancel(true);
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setGroupSummary(false);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// notificationID allows you to update the notification later on.
        mNotificationManager.notify(requestCode, mBuilder.build());
    }
}
