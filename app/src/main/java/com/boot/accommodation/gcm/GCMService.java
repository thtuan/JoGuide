package com.boot.accommodation.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.boot.accommodation.R;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.util.PreferenceUtils;
import com.boot.accommodation.util.TraceExceptionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 5:37 PM 17-05-2016
 */
public class GCMService extends IntentService {
    private static final String TAG = "RegIntentService";
    public static String token;
    private List<Long> tourPlanIds = new ArrayList<>();
    public GCMService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_sender), GoogleCloudMessaging.INSTANCE_ID_SCOPE,
                    null);
            this.token = token;
            PreferenceUtils.saveString(Constants.Preference.PREFERENCE_GCM_TOKEN, token);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Error token: ", TraceExceptionUtils.getReportFromThrowable(e));
        }
    }

    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
    }



    /*public static void subscribleTopics(List<Long> planIds){
        try {
            new GCMService(planIds).subscribleTopics(token,planIds );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


}
