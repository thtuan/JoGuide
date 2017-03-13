package com.boot.accommodation.vp.position;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.listener.LocatingListener;
import com.boot.accommodation.util.ActivityUtil;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.TraceExceptionUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Xu ly luong dinh vi
 *
 * @version: 1.0
 * @since: 1.0
 */
public class PositionManager extends Activity implements LocatingListener {
    //5 phut
    public final static long TIME_SEND_LOG_LOCATING = 300000;
    // 1 phut
    public final static int TIME_OUT_SHOW_LOCATING = 60000;
    final String LBS = "NETWORK";
    // thoi gian time out lay gps
    final int GPS_TIME_OUT = 30000;
    // 10s thoi gian time out lay lbs
    final int LBS_TIME_OUT = 10000;
    //thoi gian time period dang dung
    private static long currentTimePeriod = 0;
    public static final int ACCURACY = 50;

    final String LOG_TAG = "PositionManager";

    public static final int LOCATING_NONE = 0;// trang thai none
    static final int LOCATING_GPS_START = 1;// trang thai start gps
    static final int LOCATING_GPS_ON_PROGRESS = 2;// trang thai dang lay gps
    static final int LOCATING_GPS_FAILED = 3;// trang thai lay gps that bai
    static final int LOCATING_LBS_START = 4;// trang thai start lbs
    static final int LOCATING_LBS_ON_PROGRESS = 5;// trang thai dang lay lbs
    static final int LOCATING_LBS_FAILED = 6;// trang thai lay lbs that bai
    static final int LOCATING_VIETTEL = 7;// trang thai dinh vi viettel
    public int actionSuccess;// id action dinh vi 1 lan thanh cong
    public int actionFail;// id action dinh vi 1 lan that bai
    private static volatile PositionManager instance;
    private boolean isStart = false;
    public boolean isUsingGoogleLBS = true;// co su dung lbs cua Google khong
    Locating gpsLocating;// dinh vi gps
    Locating lbsLocating;// dinh vi lbs
    int locatingState = LOCATING_NONE;// trang thai dinh vi
    private Timer locTimer = new Timer();// timer dinh thoi lay toa do
    private PositionTimerTask locTask;// task lay toa do dung kem voi timer
    private boolean isFirstLBS;// bat luong LBS truoc
    private BaseFragment listener;
    PositionPresenterMgr positionPresenterMgr; // presenter day vi tri

    private static final Object lockObject = new Object();

    public static PositionManager getInstance() {
        if (instance == null) {
            synchronized (lockObject) {
                if (instance == null) {
                    instance = new PositionManager();
                }
            }
        }
        return instance;
    }
    @Override
    public void onTimeout(Locating locating ) {
        if (!isFirstLBS) {
            handleLocatingFailed();
        }
        isFirstLBS = false;//LBS google luon timeout truoc GPS
//        if (JoCoApplication.getInstance().isAppActive()
//            && Utils.isOnline(this)
//            && System.currentTimeMillis()
//            - JoCoApplication.getInstance().getTimeSendLogLocating() >= TIME_SEND_LOG_LOCATING) {
//            GlobalInfo.getInstance().setTimeSendLogLocating(System
//                .currentTimeMillis());
//            ServerLogger.sendLog("Locating", "Time : " + DateUtils.now()
//                    + "  -  Provider time-out: " + locating.getProviderName(),
//                false, TabletActionLogDTO.LOG_CLIENT);
//        }
    }

    class PositionTimerTask extends TimerTask {
        public boolean isCancled = false;

        @Override
        public void run() {
        }
    }

    private PositionManager() {
        instance = this;
    }

    /**
     * Thuc hien restart lai luong dinh vi
     * stop() -> start()
     *
     */
    public void reStart() {
        stop();
        start();
    }


    /**
     * khoi dong lay toa do
     *
     * @return: void
     * @throws:
     */
    public void start() {
        if(positionPresenterMgr != null) {
            positionPresenterMgr = new PostionPresenter();
        }
        start(Locating.TIME_TRIG_POSITION);
    }

    public void start( long timePeriod ) {
        setCurrentTimePeriod(timePeriod);
        Log.i("PositionManager", "Start Load Location");
        JoCoApplication.getInstance().getAppContext()
            .getSystemService(Context.LOCATION_SERVICE);
        isUsingGoogleLBS = isEnableGoogleLBS();
        isStart = true;
        isFirstLBS = true;
        // run 1 luong lbs song song voi luong dinh vi
        if (isUsingGoogleLBS) {
            // lay lbs google
            getLBSGoogle();
        } else {
            // lay luong lbs truoc, viettel truoc,// google sau
            onLocationChanged(null);

        }
        locTimer = new Timer();
        locTask = new PositionTimerTask() {
            @Override
            public void run() {
                if (!locTask.isCancled) {
                    (JoCoApplication.getInstance().getHandler())
                        .post(new Runnable() {
                            @Override
                            public void run() {
                                //Log.logToFile("Position", DateUtils.now() + " - new locating request");
                                if (JoCoApplication.getInstance().isAppActive()) {
                                    locatingState = LOCATING_NONE;
                                    requestLocationUpdates();
                                } else {
                                    stop();
                                }
                            }
                        });
                }
            }
        };
        locTimer.schedule(locTask, 0, timePeriod);
    }

    /**
     * Dung lay toa do
     *
     * @return: void
     * @throws:
     */
    public void stop() {
        isStart = false;
        if (JoCoApplication.getInstance().getLocation() != null
            && JoCoApplication.getInstance().getLocation().getTime() < System
            .currentTimeMillis() - Locating.MAX_TIME_RESET) {
            JoCoApplication.getInstance().setLocation(null);
            JoCoApplication.getInstance().getProfile().getMyGPSInfo()
                .setLongtitude(Constants.LAT_LNG_NULL);
            JoCoApplication.getInstance().getProfile().getMyGPSInfo()
                .setLattitude(Constants.LAT_LNG_NULL);
            JoCoApplication.getInstance().getProfile().save();
        }
        if (locTask != null) {
            locTask.cancel();
            locTask.isCancled = true;
        }
        if (locTimer != null) {
            locTimer.cancel();
        }
        if (gpsLocating != null) {
            gpsLocating.resetTimer();
        }
        if (lbsLocating != null) {
            lbsLocating.resetTimer();
        }
    }

    /**
     * quan ly trang thai va goi lay toa do
     *
     * @return: void
     * @throws:
     */
    private void requestLocationUpdates() {
        synchronized (this) {
            switch (locatingState) {
                case LOCATING_NONE:
                    Log.i(LOG_TAG, "requestLocationUpdates() - LOCATING_NONE");
                    locatingState = LOCATING_GPS_START;
                    requestLocationUpdates();
                    break;

                case LOCATING_GPS_START:
                    Log.i(LOG_TAG,
                        "requestLocationUpdates() - LOCATING_GPS_START");
                    if (gpsLocating == null) {
                        gpsLocating = new Locating(LocationManager.GPS_PROVIDER,
                            this);
                    }
                    if (!gpsLocating.requestLocating(GPS_TIME_OUT)) {
                        locatingState = LOCATING_GPS_FAILED;
                        requestLocationUpdates();
                    } else {
                        locatingState = LOCATING_GPS_ON_PROGRESS;
                    }
                    break;

                case LOCATING_GPS_ON_PROGRESS:
                    Log.i(LOG_TAG,
                        "requestLocationUpdates() - LOCATING_GPS_ON_PROGRESS");
                    break;

                case LOCATING_GPS_FAILED:
                    Log.i(LOG_TAG,
                        "requestLocationUpdates() - LOCATING_GPS_FAILED");
                    if (isUsingGoogleLBS) {
                        locatingState = LOCATING_LBS_START;
                    } else {
                        locatingState = LOCATING_VIETTEL;
                    }
//				if (JoCoApplication.getInstance().isAppActive()
//						&& (System.currentTimeMillis() - JoCoApplication
//								.getInstance().getTimeSendLogLocating()) >= TIME_SEND_LOG_LOCATING
//						&& GlobalUtil.checkNetworkAccess()) {
//					JoCoApplication.getInstance().setTimeSendLogLocating(System
//							.currentTimeMillis());
//					ServerLogger.sendLog("Locating",
//							"Time : " + DateUtils.now()
//									+ " .... LOCATING_GPS_FAILED", false,
//							TabletActionLogDTO.LOG_CLIENT);
//				}
                    requestLocationUpdates();
                    break;

                case LOCATING_LBS_START:
                    Log.i(LOG_TAG,
                        "requestLocationUpdates() - LOCATING_LBS_START");
                    if (lbsLocating == null) {
                        lbsLocating = new Locating(
                            LocationManager.NETWORK_PROVIDER, this);
                    }
                    if (!lbsLocating.requestLocating(LBS_TIME_OUT)) {// dinh vi lbs
                        locatingState = LOCATING_LBS_FAILED;
                        requestLocationUpdates();
                    } else {
                        locatingState = LOCATING_LBS_ON_PROGRESS;
                    }
                    break;

                case LOCATING_LBS_ON_PROGRESS:
                    Log.i(LOG_TAG,
                        "requestLocationUpdates() - LOCATING_LBS_ON_PROGRESS");
                    break;

                case LOCATING_LBS_FAILED:
                    Log.i(LOG_TAG,
                        "requestLocationUpdates() - LOCATING_LBS_FAILED");
                    // Log.logToFile(LOG_TAG,
                    // "requestLocationUpdates() - LOCATING_LBS_FAILED");
//				if (JoCoApplication.getInstance().isAppActive()
//						&& (System.currentTimeMillis() - JoCoApplication
//								.getInstance().getTimeSendLogLocating()) >= TIME_SEND_LOG_LOCATING
//						&& GlobalUtil.checkNetworkAccess()) {
//					JoCoApplication.getInstance().setTimeSendLogLocating(System
//							.currentTimeMillis());
//
//					ServerLogger.sendLog("Locating",
//							"Time : " + DateUtils.now()
//									+ " .... LOCATING_LBS_FAILED", false,
//							TabletActionLogDTO.LOG_CLIENT);
//				}
                    locatingState = LOCATING_NONE;
                    break;

                case LOCATING_VIETTEL:
                    Log.i(LOG_TAG, "requestLocationUpdates() - LOCATING_VIETTEL");
                    onLocationChanged(null);
                    break;
            }
        }
    }

    private void getLBSGoogle() {
        // isFirstLBS = false;
        (JoCoApplication.getInstance().getHandler()).post(new Runnable() {
            @Override
            public void run() {

                Locating lbs = new Locating(LocationManager.NETWORK_PROVIDER,
                    PositionManager.this);
                lbs.requestLocating(LBS_TIME_OUT);
            }
        });
    }

    /**
     * chuyen trang thai khi dinh vi that bai
     *
     * @author: AnhND
     * @return: void
     * @throws:
     */
    private void handleLocatingFailed() {
        synchronized (this) {
            switch (locatingState) {
                case LOCATING_GPS_ON_PROGRESS:
                    Log.i(LOG_TAG, "handleLocatingFailed() - GPS TIME OUT");
                    locatingState = LOCATING_GPS_FAILED;
                    requestLocationUpdates();
                    break;
                case LOCATING_LBS_ON_PROGRESS:
                    Log.i(LOG_TAG, "handleLocatingFailed() - LBS TIME OUT");
                    locatingState = LOCATING_LBS_FAILED;
                    requestLocationUpdates();
                    break;
                case LOCATING_VIETTEL:// lay lbs Viettel error thi lay lbs Google
                    Log.i(LOG_TAG, "handleLocatingFailed() - LBS TIME OUT");
                    locatingState = LOCATING_LBS_START;
                    (JoCoApplication.getInstance().getHandler()).post(new Runnable() {
                        @Override
                        public void run() {
                            requestLocationUpdates();
                        }
                    });
                    break;
            }
        }
    }

    @Override
    public void onLocationChanged( Location location ) {
        // TODO Auto-generated method stub
        if (location != null) {
            if (isFirstLBS && LBS.equals(location.getProvider().toString())) {
                isFirstLBS = false;
            }
            Log.d("PositionManager",
                location.getProvider().toString() + " (X,Y)= ("
                    + location.getLatitude() + " , "
                    + location.getLongitude() + " )");
            if(positionPresenterMgr == null){
                positionPresenterMgr = new PostionPresenter();
            }
            positionPresenterMgr.updatePosition(location.getLatitude(),location.getLongitude(),location);

            // broadcast thay doi vi tri
            //updatePosition(location.getLongitude(), location.getLatitude(),
            //		location);
            // neu co 1 truong hop gps success thi chay xong gps se dung tien
            // trinh kg lam gi
            if (locatingState == LOCATING_GPS_ON_PROGRESS) {
                locatingState = LOCATING_NONE;
            }
        } else {
            // requestActionUpdatePosition();
        }
    }

    @Override
    public void onProviderDisabled( String provider ) {

        handleLocatingFailed();
        if (provider.toUpperCase().contains("GPS")) {
            try {
                ((BaseActivity) JoCoApplication.getInstance()
                    .getActivityContext())
                    .showMessage(StringUtil.getString(R.string.text_gps_need_start_gps));
            } catch (Exception e) {
                Log.e("PositionManager", e.getMessage());
            }
        } else if (provider.toUpperCase().contains("NETWORK")) {
            try {
                ((BaseActivity) JoCoApplication.getInstance()
                    .getActivityContext())
                    .showMessage(StringUtil.getString(R.string.text_gps_need_start_gps_network));
            } catch (Exception e) {
                Log.e("PositionManager", e.getMessage());
            }
        }
    }

    @Override
    public void onProviderEnabled( String provider ) {

    }

    @Override
    public void onStatusChanged( String provider, int status, Bundle extras ) {

    }


    public boolean getIsStart() {
        return isStart;
    }

    public int getLocatingState() {
        return locatingState;
    }

    public boolean getIsFirstLBS() {
        return isFirstLBS;
    }

    /**
     * @return the enableGPS
     */
    public boolean isEnableGPS() {
        LocationManager manager = (LocationManager) JoCoApplication.getInstance().getApplicationContext().getSystemService(Context.LOCATION_SERVICE );
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * @return the enableGoogleLBS
     */
    public boolean isEnableGoogleLBS() {
        return new Locating(LocationManager.NETWORK_PROVIDER, this).isEnableGPS();
    }

    /**
     * Kiem tra enable GPS va show setting thiet lap GPS
     *
     * @author banghn
     */
    public void checkAndShowSettingGPS() {
        if (!isEnableGPS()) {
            showDialogSettingGPS();
        }
    }

    /**
     * show dialog confirm setting gps
     *
     * @author banghn
     */
    public void showDialogSettingGPS() {
//        AlertDialog alertDialog = null;
        try {
//            alertDialog = new AlertDialog.Builder(JoCoApplication.getInstance()
//                .getActivityContext()).create();
//            alertDialog.setMessage(StringUtil
//                .getString(R.string.notify_setting_gps));
//            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,
//                StringUtil.getString(R.string.text_close),
//                new DialogInterface.OnClickListener() {
//                    public void onClick( DialogInterface dialog, int which ) {
//                        dialog.dismiss();
//                        return;
//
//
//                    }
//                });
//            alertDialog.show();
            // hien thi man hinh setting
            ActivityUtil.switchActivity(JoCoApplication.getInstance().getActivityContext(),
                new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        } catch (Exception ex) {
            Log.w("", TraceExceptionUtils.getReportFromThrowable(ex));
        }
    }

    public static long getCurrentTimePeriod() {
        return currentTimePeriod;
    }

    public static void setCurrentTimePeriod( long currentTimePeriod ) {
        PositionManager.currentTimePeriod = currentTimePeriod;
    }

    /**
     * Show alert anable
     */
    public void showAlertEnableGPS(View view, boolean isEnable, String alert ) {
        if(view != null) {
            final Snackbar snackbar = Snackbar
                .make(view, alert, Snackbar.LENGTH_INDEFINITE);
            if (!isEnable) {
                snackbar.setAction
                    (StringUtil.getString(R.string.text_ok), new View.OnClickListener() {
                        @Override
                        public void onClick( View v ) {
                            PositionManager.getInstance().showDialogSettingGPS();

                        }
                    });
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    snackbar.dismiss();
                }
            }, 5000);
            snackbar.show();
        }
    }

}
