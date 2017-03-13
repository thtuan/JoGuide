package com.boot.accommodation.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.constant.ErrorCode;
import com.boot.accommodation.dto.view.AppVersionDTO;
import com.boot.accommodation.dto.view.ConfigDTO;
import com.boot.accommodation.dto.view.LanguagesDTO;
import com.boot.accommodation.dto.view.LoginDTO;
import com.boot.accommodation.dto.view.UserItemDTO;
import com.boot.accommodation.listener.OnEventControl;
import com.boot.accommodation.util.ExifUtil;
import com.boot.accommodation.util.FileUtil;
import com.boot.accommodation.util.ImageUtil;
import com.boot.accommodation.util.PreferenceUtils;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.TraceExceptionUtils;
import com.boot.accommodation.util.Utils;
import com.boot.accommodation.vp.accommodation.AccommodationActivity;
import com.boot.accommodation.vp.category.TimeToGoActivity;
import com.boot.accommodation.vp.common.CommonPresenter;
import com.boot.accommodation.vp.home.HomeActivity;
import com.boot.accommodation.vp.home.LeftMenuFragment;
import com.boot.accommodation.vp.login.LoginActivity;
import com.boot.accommodation.vp.login.SignupActivity;
import com.boot.accommodation.vp.position.LocationService;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.boot.accommodation.R.drawable.shadow;

//import com.google.android.gms.location.LocationServices;

/**
 * Created by Admin on 06/10/2015.
 */
public class BaseActivity extends SlidingFragmentActivity implements GoogleApiClient.OnConnectionFailedListener,
        DialogInterface.OnCancelListener, OnEventControl, BaseViewMgr {

    public GoogleApiClient mGoogleApiClient;
    public GoogleSignInOptions gso;
    FrameLayout frMain; // frame main
    ProgressBar prLoading; // loading
    public LeftMenuFragment leftMenuFragment;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    // broadcast receiver, nhan broadcast
    private AlertDialog alertDialog; // Alert dialog
    public BaseFragment fragmentRequestTakePhoto;
    public File takenPhoto;
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_IMAGE_SELECTOR = 2;
    public ProgressDialog progressDialog;//Progress dialog
    private boolean doubleBackToExitPressedOnce = false;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (!isFinishing()) {
                    int action = intent.getExtras().getInt(Constants.ACTION_BROADCAST);
                    int hasCode = intent.getExtras().getInt(
                            Constants.HASHCODE_BROADCAST);
                    if (hasCode != BaseActivity.this.hashCode()) {
                        receiveBroadcast(action, intent.getExtras());
                    }
                }
            }catch (RuntimeException ex) {
                Log.e(Constants.LogName.EXCEPTION, TraceExceptionUtils.getReportFromThrowable(ex));
            }

        }
    };

    /**
     * Send broadcast
     * @param action
     * @param bundle
     */
    public void sendBroadcast( int action, Bundle bundle) {
        Intent intent = new Intent(Constants.JOCO_ACTION);
        bundle.putInt(Constants.ACTION_BROADCAST, action);
        bundle.putInt(Constants.HASHCODE_BROADCAST, intent.getClass()
            .hashCode());
        intent.putExtras(bundle);
        sendBroadcast(intent, Constants.BROACAST_PERMISSION);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the Behind View
        setBehindContentView(R.layout.menu_frame);
        // customize the SlidingMenu
        if ((this instanceof AccommodationActivity) || this instanceof TimeToGoActivity){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.menu_frame, leftMenuFragment = new LeftMenuFragment())
                    .commit();
            SlidingMenu sm = getSlidingMenu();
            sm.setShadowWidthRes(R.dimen.shadow_width);
            sm.setShadowDrawable(shadow);
            sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
            sm.setFadeDegree(0.35f);
            sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }
        inititalGoogleApi();
//        Thread.setDefaultUncaughtExceptionHandler(new TraceExceptionUtils(this));
        //dang ky su kien broadcast
        IntentFilter filter = new IntentFilter(Constants.JOCO_ACTION);
        registerReceiver(receiver, filter);
        //set activity context
        JoCoApplication.getInstance().setActivityContext(this);
    }

    /**
     * Init google api
     */
    private void inititalGoogleApi() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Plus.API)
//                .addApi(LocationServices.API)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void setContentView(int id) {
        super.setContentView(R.layout.activity_base);
        frMain = (FrameLayout) findViewById(R.id.frMain);
        prLoading = (ProgressBar) findViewById(R.id.prLoading);
        LayoutInflater inflater = (LayoutInflater) this
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(id, frMain);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * Khoi tao view
     */
    private void initView() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setEnabled(false);
            TypedValue tv = new TypedValue();
            if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics
                        ());
                swipeRefreshLayout.setProgressViewOffset(false, actionBarHeight, actionBarHeight * 2);
            }
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    //               sendBroadcast(Constants.ActionEvent.NOTIFY_REFRESH,new Bundle());
                    // just refresh one fragment
                    receiveBroadcast(Constants.ActionEvent.NOTIFY_REFRESH, new Bundle());
                }
            });
            swipeRefreshLayout.setColorSchemeResources(R.color.primary_500, R.color.primary_500, R.color.primary_500);
        }
    }

    /**
     * Nhan xu li broadcast
     *
     * @param action
     * @param bundle
     */
    protected void receiveBroadcast(int action, Bundle bundle) {
        switch (action){
            case Constants.ActionEvent.ACTION_UPDATE_POSITION_SERVICE:
                if (this == JoCoApplication.getInstance().getActivityContext()){
                    Log.d("Locating", "Global Received location update ................. ");
                    Location location = bundle.getParcelable(Constants.IntentExtra.INTENT_DATA);
                    updatePosition(location.getLongitude(), location.getLatitude(), location);
                } else{
                    Log.d("Locating","ACTION_UPDATE_POSITION_SERVICE not except");
                }
                break;
            case Constants.ActionEvent.ACTION_FINISH_ALL_AND_START_ACTIVITY:
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                break;
            case Constants.ActionEvent.ACTION_FINISH_ALL_ACTIVITY:
                finish();
                break;
        }
    }

    /**
     * Enable sliding menu
     * @param isEnable
     */
    protected void enableSlidingMenu(boolean isEnable) {
        if (getSlidingMenu() != null) {
            if (isEnable) {
                getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
                getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
            } else {
                getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LanguagesDTO dtoLang = JoCoApplication.getInstance().loadLanguages();
        if (dtoLang != null) {
            JoCoApplication.getInstance().updateLanguageConfig(dtoLang.getValue());
        }

        JoCoApplication.getInstance().setAppActive(true);
        //set activity context
        JoCoApplication.getInstance().setActivityContext(this);
        AppEventsLogger.activateApp(this);
        JoCoApplication.getInstance().trackScreenView(Utils.getTag(this.getClass()));
    }


    @Override
    protected void onPause() {
        super.onPause();
        // van hoat dong gps khi home, pause chuong trinh
        JoCoApplication.getInstance().setAppActive(false);
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        showMessage(connectionResult.getErrorMessage());
        closeProgressDialog();
    }

    public String logTag() {
        return this.getLocalClassName();
    }

    public void showProgressDialog(boolean isCancel) {
        showWait(getResources().getString(R.string.text_processing), isCancel);
    }

    public void showLoadingDialog(boolean isCancel) {
        showWait(getResources().getString(R.string.text_loading), isCancel);
    }

    public void showWait(String message, boolean isCancel) {
        if (progressDialog != null && progressDialog.isShowing()) {
            closeProgressDialog();
        }
        if (!isFinishing()) {
            if (progressDialog == null) {
//                progressDialog = ProgressDialog.show(this, "", message, true, isCancel);
                progressDialog = new ProgressDialog(this);
                progressDialog.show();
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Utils.getColor(android.R.color
                        .transparent)));
                progressDialog.setContentView(R.layout.progress_loading);
//                progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                progressDialog.setCancelable(isCancel);
                progressDialog.setCanceledOnTouchOutside(isCancel);
                progressDialog.setOnCancelListener(this);
                ProgressBar prLoading = (ProgressBar) progressDialog.findViewById(R.id.prLoading);
                prLoading.getIndeterminateDrawable().setColorFilter(Utils.getColor(R.color.primary_500), PorterDuff
                        .Mode
                        .SRC_IN);
            } else {
                progressDialog.show();
            }
        }
    }

    /**
     * Hien thi loading
     */
    public void showProgress(){
        if(prLoading != null  && !isFinishing()){
            prLoading.setIndeterminate(true);
        }
    }

    /**
     * Close progress
     */
    public void closeProgress(){
        if(prLoading != null && !isFinishing()){
            prLoading.setIndeterminate(false);
        }
    }
    public void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public void showAlert(String message) {
        showAlert(message, null);
    }

    public void showAlert(String message, String title) {
        showAlert(message, title, null, getString(R.string.text_ok), true);
    }

    public void showAlert(String message, String title, DialogInterface.OnClickListener onOk, String titleOk, boolean isCancel) {
        showAlert(message, title, onOk, titleOk, null, null, isCancel);
    }

    /**
     * Show alert
     * @param message
     * @param title
     * @param onOk
     * @param titleOk
     * @param onCancel
     * @param titleCancel
     * @param isCancel
     * @return
     */
    public AlertDialog showAlert(String message, String title, DialogInterface.OnClickListener onOk, String titleOk,
                          DialogInterface.OnClickListener onCancel, String titleCancel, boolean isCancel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setCancelable(isCancel);
        builder.setMessage(message);
        if (!StringUtil.isNullOrEmpty(titleOk)) {
            builder.setPositiveButton(titleOk, onOk);
        }
        if (!StringUtil.isNullOrEmpty(titleCancel)) {
            builder.setNegativeButton(titleCancel, onCancel);
        }
        if(alertDialog == null || !alertDialog.isShowing() ){
            alertDialog = builder.show();
        }
        return alertDialog;

    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showMessage(@StringRes int message) {
        Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show();
    }

    public <T extends Activity> void goNextScreen(Class<T> clazz) {
        goNextScreen(clazz, null, false);
    }

    public <T extends Activity> void goNextScreen(Class<T> clazz, Bundle bun) {
        goNextScreen(clazz, bun, false);
    }

    public <T extends Activity> void goNextScreen(Class<T> clazz, boolean isFinishAll) {
        goNextScreen(clazz, null, isFinishAll);
    }

    public <T extends Activity> void goNextScreen(Class<T> clazz, Bundle bun, boolean isFinishAll) {
        Intent intent = new Intent(this, clazz);
        if (bun != null) {
            intent.putExtras(bun);
        }
        startActivity(intent);
        if (isFinishAll) {
            finishAffinity();
        }
    }

    public <T extends Activity> void goNextScreen(Class<T> clazz, int requestCode) {
        goNextScreen(clazz, null, requestCode);
    }

    public <T extends Activity> void goNextScreen(Class<T> clazz, Bundle bun, int requestCode) {
        Intent intent = new Intent(this, clazz);
        if (bun != null) {
            intent.putExtras(bun);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        CommonPresenter.getInstance().cancelListRequest(Utils.getTag(this.getClass()));
        super.onDestroy();
    }

    /**
     * This method will be invoked when the dialog is canceled.
     *
     * @param dialog The dialog that was canceled will be passed into the
     *               method.
     */
    @Override
    public void onCancel( DialogInterface dialog ) {

    }

    /**
     * Xu li update position
     * @param lng
     * @param lat
     * @param loc
     */
    public void updatePosition( Double lng, Double lat, Location loc ) {

    }

    /**
     * Start dinh vi theo cam bien
     */
    public void startFusedLocationService() {
        if (isGooglePlayServiceAvailable()) {
            Intent service = new Intent(JoCoApplication.getInstance(), LocationService.class);
            startService(service);
        }
    }

    /**
     * Check google play services
     * @return
     */
    private boolean isGooglePlayServiceAvailable(){
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int code = api.isGooglePlayServicesAvailable(this);
        if(code == ConnectionResult.SUCCESS) {
            return true;
        }
        return false;
    }

    /**
     * Switch fragment
     * @param clazzFragment
     * @param isRemoveInBackStack: co remove tat ca fragment truoc trong stack hay ko
     * @param idFragment: id fragment replace
     */
    public void switchFragment(BaseFragment
        clazzFragment, int idFragment, boolean isRemoveInBackStack ){
        boolean result = false;
        String TAG = clazzFragment != null ? clazzFragment.getTAG() : null;
        Log.d("Fragment", "switchFragment: " + TAG);
        if (this != null && !this.isFinishing() && clazzFragment != null) {
            android.support.v4.app.FragmentManager fm =  getSupportFragmentManager();
            if (fm != null) {
                if (isRemoveInBackStack) {
                    JoCoApplication.getInstance().removeAllInBackStack(fm);
                }

                Fragment existsFrag = fm.findFragmentByTag(TAG);
                if (existsFrag != null) {
                    FragmentTransaction ftRemove = fm.beginTransaction();
                    ftRemove.remove(existsFrag);
                    ftRemove.commit();
                    Log.d("Fragment", "remove Fragment: " + TAG);
                }

                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(idFragment, clazzFragment, TAG);
                ft.addToBackStack(TAG);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();

                JoCoApplication.getInstance().setCurrentTag(TAG);
                Log.d("Fragment",
                    "Num fragment in stack : " + fm.getBackStackEntryCount());
                //set result
                result = true;
            } else{
                Log.d("Fragment", "switchFragment fail FragmentManager null: " + TAG);
            }
        } else{
            Log.d("Fragment", "switchFragment fail: " + TAG);
        }
        Log.d("Fragment", "switchFragment result: " + result);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() <= 1) {
            if (this instanceof HomeActivity) {
                if (doubleBackToExitPressedOnce) {
                    stopFusedLocationService();
                    finish();
                } else {
                    showMessage(StringUtil.getString(R.string.text_click_back_exit));
                }
                this.doubleBackToExitPressedOnce = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 5000);
            } else {
//            super.onBackPressed();
                // tam thoi finish
                finish();
            }
        } else {
            JoCoApplication.getInstance().popCurrentTag();
            super.onBackPressed();
        }
    }

    /**
     * Anh hien floatting button
     * @param isHide
     * @param position
     */
    public void hideFloatingButton(boolean isHide, int position){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {
                case BaseActivity.REQUEST_IMAGE_SELECTOR:
                    onSelectFromGalleryResult(data);
//                    handlePicture(requestCode, resultCode, null);
                    break;
                case BaseActivity.REQUEST_IMAGE_CAPTURE: {
                    // luu hinh anh
                    saveImageToTakenPhotoFolder();
                    handlePicture(takenPhoto.getAbsolutePath(), resultCode, data);
                    break;
                }
                default:
                    super.onActivityResult(requestCode,resultCode,data);
                    break;
            }
        } catch (Throwable ex) {
            Log.e(Constants.LogName.EXCEPTION, TraceExceptionUtils.getReportFromThrowable(ex));
        }
    }

    /**
     * Handle picture taken or choose from gallery
     * @param resultCode
     * @param data
     */
    public void handlePicture(String pathFile, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && takenPhoto != null && takenPhoto.exists()) {
//            String filePath = pathFile;
//            ImageValidatorTakingPhoto validator = new ImageValidatorTakingPhoto(this, pathFile, Constants.MAX_FULL_IMAGE_HEIGHT_UPLOAD);
//            validator.setDataIntent(data);
//            if (validator.execute()) {
                updatePhoto(takenPhoto.getAbsolutePath());
//            }
        } else {
            //Xoa file rac
            if (takenPhoto != null && takenPhoto.exists()) {
                takenPhoto.delete();
            }
        }
    }

    /**
     * Luu hinh anh vao thu muc photo
     */
    public void saveImageToTakenPhotoFolder() {
        String[] projection = {
                MediaStore.MediaColumns._ID,
                MediaStore.Images.ImageColumns.ORIENTATION,
                MediaStore.Images.Media.DATA
        };
        Cursor c = this.getContentResolver().query(ImageUtil.photoUri, projection, null, null, null);
        c.moveToFirst();
        String photoFileName = c.getString(c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
        if (c != null) {
            c.close();
        }
        Bitmap bitmap = BitmapFactory.decodeFile(photoFileName);
        bitmap = ExifUtil.rotateBitmap(ExifUtil.TAKE_PHOTO, photoFileName, bitmap);
        FileUtil.saveImageToDisk(bitmap, takenPhoto, "JPEG");
    }

    @Override
    public void onEventControl( int action, Object item, View view, int position ) {

    }

    @Override
    public void onLoadMore( int position ) {

    }

    /**
     * Handle error
     * @param errorCode
     * @param error
     */
    public void handleError(int errorCode, String error) {
        switch (errorCode) {
            case ErrorCode.ERR_COMMON:
                showMessage(R.string.text_error_in_processing);
                break;
            case ErrorCode.NO_CONNECTION:
                showMessage(R.string.text_error_connect_server);
                break;
            // session time out
            case ErrorCode.UN_AUTHORIZE:
            case ErrorCode.AUTHENTICATE_REQUIRED:
//                sendBroadcast(Constants.ActionEvent.ACTION_FINISH_ALL_ACTIVITY, new Bundle());
                showLogin();
                break;

        }
    }

    /**
     * Show login
     */
    public void showLogin() {
        goNextScreen(LoginActivity.class, false);
    }

    /**
     * Sign out fb
     */
    private void signOutFb() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            LoginManager.getInstance().logOut();
        }
    }

    /**
     * Sign out app
     */
    public void signOutApp() {
        if (!TextUtils.isEmpty(PreferenceUtils.getString(Constants.Preference.PREFERENCE_USER_ID, ""))) {
            sendBroadcast(Constants.ActionEvent.ACTION_SIGN_OUT, new Bundle());
            signOutFb();
            if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                            }
                        });
            }
            PreferenceUtils.clear();
            stopFusedLocationService();
            JoCoApplication.getInstance().setProfile(null);
        }
        showMessage(R.string.text_logout_success);
//        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
    }

    /**
     * re Start Locating
     * @since: 11:37:36 7 Jan 2015
     * @return: void
     * @throws:
     */
    public void reStartLocating(){
//        PositionManager.getInstance().reStart();
        if(isGooglePlayServiceAvailable()) {
            if (isServiceRunning(LocationService.class)) {
                stopFusedLocationService();
            }
            startFusedLocationService();
        }else{
            Utils.checkGooglePlayServices(this);
//            PositionManager.getInstance().start();
        }
    }

    /**
     * stop service dinh vi fused
     * @since: 14:34:41 6 Nov 2014
     * @return: void
     * @throws:
     */
    public void stopFusedLocationService() {
        Intent service = new Intent(JoCoApplication.getInstance()
            .getAppContext(), LocationService.class);
        stopService(service);
    }

    /**
     * Check service running
     * @param serviceClass
     * @return
     */
    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Xu li hinh anh sau khi chon file
     *
     * @param data
     */
    protected void onSelectFromGalleryResult(Intent data) {
        ImageUtil.photoUri = data.getData();
        Bitmap bm = null;
        String pathFile = "";
        if (data != null && data.getData() != null) {
            try {
                pathFile = ImageUtil.getRealPathFromURI(this,  ImageUtil.photoUri);
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                ExifUtil.rotateBitmap(0, data.getData().getPath(), bm);
                if (StringUtil.isNullOrEmpty(pathFile)) {
                    takenPhoto = ImageUtil.createFileFromBitMap(this, bm);
                } else {
                    takenPhoto = new File(pathFile);
                }
//                handlePicture(pathFile, Activity.RESULT_OK, data);
                updatePhoto(takenPhoto.getAbsolutePath());
            } catch (IOException e) {
                Log.e(Constants.LogName.EXCEPTION, TraceExceptionUtils.getReportFromThrowable(e));
            }
        } else if (data != null && data.getClipData() != null) {
            try {
                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                    ImageUtil.photoUri = data.getClipData().getItemAt(i).getUri();
                    pathFile = ImageUtil.getRealPathFromURI(this, ImageUtil.photoUri );
                    bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), ImageUtil.photoUri);
                    ExifUtil.rotateBitmap(0, ImageUtil.photoUri.getPath(), bm);
                    if (StringUtil.isNullOrEmpty(pathFile)) {
                        takenPhoto = ImageUtil.createFileFromBitMap(this, bm, i);
                        Log.e("OK1",takenPhoto.getAbsolutePath() );
                    } else {
                        takenPhoto = new File(pathFile);
                        Log.e("OK2",takenPhoto.getAbsolutePath() );
                    }
//                    handlePicture(pathFile, Activity.RESULT_OK, data);
                    Log.e("Tuan",takenPhoto.getAbsolutePath() );
                    updatePhoto(takenPhoto.getAbsolutePath());
                }
            } catch (IOException e) {
                Log.e(Constants.LogName.EXCEPTION, TraceExceptionUtils.getReportFromThrowable(e));
            }
        }
    }

    /**
     * File taken
     * @param fileTaken
     */
    protected void updatePhoto(String fileTaken){

    }

    @Override
    public void updateToken(String token) {
        PreferenceUtils.saveString(Constants.Preference.PREFERENCE_USER_TOKEN, token);
        sendBroadcast(Constants.ActionEvent.NOTIFY_REFRESH, new Bundle());
    }

    /**
     * An/show swipe refresh
     */
    protected void enableRefresh(boolean isEnable) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setEnabled(isEnable);
        }
    }

    /**
     * Dung refresh
     */
    protected void stopRefresh() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * Show refresh
     */
    protected void showRefresh(){
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    /**
     * Check app version
     */
    public void checkAppVersion() {
        String versionCurrent = JoCoApplication.getInstance().getAppVersion();
        final AppVersionDTO versionServer = JoCoApplication.getInstance().getProfile().getUserData().getAppVersion();
        if (versionServer != null && !StringUtil.isNullOrEmpty(versionServer.getVersion()) && !StringUtil
                .isNullOrEmpty(versionCurrent) && versionCurrent.compareTo(versionServer.getVersion()) < 0){
            if (AppVersionDTO.TYPE_FORCE == versionServer.getForce()) {
                showAlert(versionServer.getDescription(), StringUtil.getString(R.string.text_title_update_version), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick( DialogInterface dialog, int which ) {
                                closeProgressDialog();
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(versionServer.getLink()));
                                startActivity(browserIntent);
                                if (!Utils.checkResourceExists(BaseActivity.this, Constants.FACEBOOK_APP_ID)) {
                                    signOutFb();
                                }
                            }
                        },
                        StringUtil.getString(R.string.text_ok), false);
            }else{
                //Recommend download
                boolean isShowAlert = true;
                if (!PreferenceUtils.getBoolean(Constants.Preference.PREFERENCE_SHOW_ALERT_APP_VERSION, false)) {
                    saveAlertVersionShow(versionServer.getVersion());
                }else {
//                    if (this instanceof HomeActivity) {
                        String version = PreferenceUtils.getString(Constants.Preference
                                .PREFERENCE_VERSION_ALERT_SHOW, "");
                        if (version.compareTo(versionServer.getVersion()) < 0) {
                            isShowAlert = true;
                        }else {
                            isShowAlert = false;
                        }
//                    }
                }
                if (isShowAlert) {
                    saveAlertVersionShow(versionServer.getVersion());
                    showAlert(versionServer.getDescription(), StringUtil.getString(R.string.text_title_update_version), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    closeProgressDialog();
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse
                                            (versionServer.getLink()));
                                    startActivity(browserIntent);
                                    if (!Utils.checkResourceExists(BaseActivity.this, Constants.FACEBOOK_APP_ID)) {
                                        signOutFb();
                                    }
                                }
                            },
                            StringUtil.getString(R.string.text_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    handleContinuousLogin();
                                }
                            }, StringUtil.getString(R.string.text_cancel), true);
                }
            }
        } else {
            if (this instanceof LoginActivity || this instanceof SignupActivity) {
                handleContinuousLogin();
            }
        }
    }

    /**
     * Show alert version
     */
    private void saveAlertVersionShow(String version) {
        PreferenceUtils.saveBoolean(Constants.Preference.PREFERENCE_SHOW_ALERT_APP_VERSION, true);
        PreferenceUtils.saveString(Constants.Preference.PREFERENCE_VERSION_ALERT_SHOW, version);
    }

    /**
     * Handle continuous
     */
    protected void handleContinuousLogin() {

    }

    /**
     * Save user info
     * @param loginDTO
     * @param userDTO
     */
    public void saveUserInfo(LoginDTO loginDTO, UserItemDTO userDTO ) {
        userDTO.setLoginType(loginDTO.getLoginType());
        userDTO.setIsLogin(true);
        for (ConfigDTO configDTO : userDTO.getConfig()){
            PreferenceUtils.saveString(configDTO.getKey(), configDTO.getValue());
        }
        PreferenceUtils.saveBoolean(Constants.Preference.PREFERENCE_USER_LOGIN, userDTO.getIsLogin());
        PreferenceUtils.saveString(Constants.Preference.PREFERENCE_USER_ID, userDTO.getId());
        PreferenceUtils.saveString(Constants.Preference.PREFERENCE_USER_NAME, userDTO.getUserName());
        PreferenceUtils.saveString(Constants.Preference.PREFERENCE_USER_FULL_NAME, userDTO.getFullName());
        PreferenceUtils.saveInt(Constants.Preference.PREFERENCE_USER_TYPE, userDTO.getUserType());
        PreferenceUtils.saveString(Constants.Preference.PREFERENCE_USER_PHONE, userDTO.getPhone());
        PreferenceUtils.saveString(Constants.Preference.PREFERENCE_USER_STATUS, userDTO.getStatus());
        PreferenceUtils.saveString(Constants.Preference.PREFERENCE_USER_TOKEN, userDTO.getToken());
        PreferenceUtils.saveInt(Constants.Preference.PREFERENCE_LOGIN_TYPE, userDTO.getLoginType());
        PreferenceUtils.saveString(Constants.Preference.PREFERENCE_USER_EMAIL, userDTO.getEmail());
        PreferenceUtils.saveString(Constants.Preference.PREFERENCE_USER_FIRST_NAME, userDTO.getFirstName());
        PreferenceUtils.saveString(Constants.Preference.PREFERENCE_USER_LAST_NAME, userDTO.getLastName());
        PreferenceUtils.saveBoolean(Constants.Preference.PREFERENCE_USER_LOGIN, userDTO.getIsLogin());
        PreferenceUtils.saveString(Constants.Preference.PREFERENCE_USER_PICTURE, userDTO.getAvatar());
        JoCoApplication.getInstance().getProfile().setUserData(userDTO);
    }

}
