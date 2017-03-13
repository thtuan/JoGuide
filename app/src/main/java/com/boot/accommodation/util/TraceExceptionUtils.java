package com.boot.accommodation.util;

import android.app.Activity;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.vp.common.CommonPresenterMgr;

/**
 * Mo ta class
 *
 * @author tuanlt
 * @since: 10:23 AM 5/6/2016
 */
public class TraceExceptionUtils  implements Thread.UncaughtExceptionHandler, Runnable {

    private Thread.UncaughtExceptionHandler unExceptionHandler;
    //private Activity app = null;
    CommonPresenterMgr commonPresenterMgr; // common presenter
    public TraceExceptionUtils(Activity app) {
        this.unExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //this.app = app;
    }

    @Override
    public void run() {

    }

    @Override
    public void uncaughtException(Thread paramThread, Throwable e) {
//        StackTraceElement[] arr = e.getStackTrace();
//        String report = e.toString() + "\n\n";
//        report += "--------- Stack trace ---------\n\n";
//        for (int i = 0; i < arr.length; i++) {
//            report += "    " + arr[i].toString() + "\n";
//        }
//        report += "-------------------------------\n\n";
//
//        // If the exception was thrown in a background thread inside
//        // AsyncTask, then the actual exception can be found with getCause
//        report += "--------- Cause ---------\n\n";
//        Throwable cause = e.getCause();
//        if (cause != null) {
//            report += cause.toString() + "\n\n";
//            arr = cause.getStackTrace();
//            for (int i = 0; i < arr.length; i++) {
//                report += "    " + arr[i].toString() + "\n";
//            }
//        }
//        report += "-------------------------------\n\n";
//        if(commonPresenterMgr == null){
//            commonPresenterMgr = new CommonPresenter();
//        }
//        TabletActionLogDTO tabletActionLogDTO = new TabletActionLogDTO();
//        tabletActionLogDTO.setContent(Utils.getTag(JoCoApplication.getInstance().getActivityContext().getClass()));
//        tabletActionLogDTO.setDescription(report);
//        tabletActionLogDTO.setType(TabletActionLogDTO.LOG_FORCE_CLOSE);
//        tabletActionLogDTO.setUserId(Long.parseLong(JoCoApplication.getInstance().getProfile().getUserData().getId()));
//        tabletActionLogDTO.setAppVersion(JoCoApplication.getInstance().getAppVersion());
//        tabletActionLogDTO.setDeviceImei(JoCoApplication.getInstance().getDeviceIMEI());
//        tabletActionLogDTO.setAndroidVersion(android.os.Build.VERSION.SDK_INT);
//        tabletActionLogDTO.setCreateAt(DateUtil.formatNow(DateUtil.FORMAT_DATE_TIME_ZONE));
//        commonPresenterMgr.requestUpdateLog(tabletActionLogDTO);
//        if (!GlobalInfo.getInstance().isSendLogException){
//            //clear cac thong tin trong ctrinh
////			GlobalUtil.getInstance().clearAllData();
//            // send log len server
//            ServerLogger.sendLog(GlobalInfo.getInstance().getCurrentTag(), report, TabletActionLogDTO.LOG_FORCE_CLOSE);
//            GlobalInfo.getInstance().isSendLogException = true;
//        }
//        unExceptionHandler.uncaughtException(paramThread, e);
        JoCoApplication.getInstance().trackException(e);
    }

    /**
     * Get report msg throwable
     *
     * @param e
     * @return
     */
    public static String getReportFromThrowable(Throwable e) {
        StackTraceElement[] arr = e.getStackTrace();
        String report = e.toString() + "\n\n";
        report += "--------- Stack trace ---------\n\n";
        for (int i = 0; i < arr.length; i++) {
            report += "    " + arr[i].toString() + "\n";
        }
        // If the exception was thrown in a background thread inside
        // AsyncTask, then the actual exception can be found with getCause
        report += "--------- Cause ---------\n\n";
        Throwable cause = e.getCause();
        if (cause != null) {
            report += cause.toString() + "\n\n";
            arr = cause.getStackTrace();
            for (int i = 0; i < arr.length; i++) {
                report += "    " + arr[i].toString() + "\n";
            }
        }
        return report;
    }
}