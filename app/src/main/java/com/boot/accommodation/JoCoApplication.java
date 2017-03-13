package com.boot.accommodation;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.v4.app.FragmentManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.gcm.GcmPubSub;
import com.boot.accommodation.analytics.AnalyticsTrackers;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.LanguagesDTO;
import com.boot.accommodation.dto.view.LoginTypeDTO;
import com.boot.accommodation.dto.view.ProfileDTO;
import com.boot.accommodation.dto.view.SettingViewDTO;
import com.boot.accommodation.dto.view.UserItemDTO;
import com.boot.accommodation.dto.view.UserPositionLogDTO;
import com.boot.accommodation.util.PreferenceUtils;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.TraceExceptionUtils;
import com.boot.accommodation.util.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Admin on 07/10/2015.
 */
public class JoCoApplication extends Application {

    private static JoCoApplication instance = null;
    private boolean isAppActive; // y bien kiem tra app co active hay ko
    private Context activityContext; // activity hien tai dang su dung
    private long timeSendLogPosition; // thoi gian goi log
    public static JoCoApplication getInstance() {
        return instance;
    }
    private Handler mHandler = new Handler();
    private int mRadiusPosition = 300; //dinh vi ban kinh 300m
    private Location mLocation;// luu lai thong tin location da dinh vi dc
    // phan luu xuong file
    private ProfileDTO profile = new ProfileDTO(); // luu thong tin user dang nhap
    private ArrayList<String> listStackTag = new ArrayList<String>(); // ds cac fragment
    public static boolean IS_VERSION_FOR_EMULATOR = false; // co phai la emulator hay ko de hardcode toa do
    //list position su dung dong bo len server trong thoi gian offline
    private ArrayList<UserPositionLogDTO> listPositionOffline = new ArrayList<UserPositionLogDTO>();
    private int valueDefault = -1;
    private int settingLocation = valueDefault; // type send location
    private int settingNotifyTour = valueDefault; // type notify tour
    private int settingNotifyReview = valueDefault; // type notify review
    private int settingShowLocation = valueDefault; // type show location
    @Override
    public void onCreate() {
        Log.d("JoCo", "Application start");
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        instance = this;
        PreferenceUtils.init(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
//        initCustomFont();
        LanguagesDTO dtoLang = loadLanguages();
        if (dtoLang != null) {
            updateLanguageConfig(dtoLang.getValue());
        }

//        // Google analytics
        AnalyticsTrackers.initialize(this);
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);

    }

    public Context getAppContext() {
        return this.getApplicationContext();
    }

    protected void attachBaseContext( Context base ) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    private void initCustomFont() {
        CalligraphyConfig config = new CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/Roboto-Light.ttf")
            .setFontAttrId(R.attr.fontPath)
            .build();

        CalligraphyConfig.initDefault(config);
    }


    public Handler getHandler() {
        return mHandler;
    }

    public void setHandler( Handler mHandler ) {
        this.mHandler = mHandler;
    }

    public int getRadiusPosition() {
        return mRadiusPosition;
    }

    public void setRadiusPosition( int mRadiusPosition ) {
        this.mRadiusPosition = mRadiusPosition;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation( Location mLocation ) {
        this.mLocation = mLocation;
    }

    public void setAppActive( boolean isActive ) {
        this.isAppActive = isActive;
    }

    public boolean isAppActive() {
        return isAppActive;
    }

    public void setActivityContext(Context context) {
        if (activityContext != context) {
            activityContext = context;
        }
    }

    public Context getActivityContext() {
        return activityContext;
    }

    public long getTimeSendLogPosition() {
        return timeSendLogPosition;
    }

    public void setTimeSendLogPosition(long timeSendLogPosition) {
        this.timeSendLogPosition = timeSendLogPosition;
    }

    /**
     * Get thong tin profile
     * @return
     */
    public ProfileDTO getProfile() {
        Object temp;
        if (profile == null || profile.getUserData() == null
            || StringUtil.isNullOrEmpty(profile.getUserData().getId())) {// nghia la bien
            // thai ban dau hoac da bi reset
            if ((temp = PreferenceUtils.readObject(PreferenceUtils.REFERENCE_PROFILE)) != null) {
                profile = (ProfileDTO) temp;// bi out memory
            }
            if (profile == null) {
                profile = new ProfileDTO();
                profile.setUserData(tryGetUserData());
                profile.save();
            } else if (profile != null
                && (profile.getUserData() == null || StringUtil.isNullOrEmpty(profile.getUserData().getId()))) {
                profile.setUserData(tryGetUserData());
                profile.save();
            }

        }
        return profile;
    }

    /**
     * Lay lai thong tin dang nhap
     * @return
     */
    private UserItemDTO tryGetUserData() {
        UserItemDTO userDTO = new UserItemDTO();
        userDTO.setId(PreferenceUtils.getString(Constants.Preference.PREFERENCE_USER_ID,""));
        userDTO.setIsLogin(PreferenceUtils.getBoolean(Constants.Preference.PREFERENCE_USER_LOGIN, false));
        userDTO.setFullName(PreferenceUtils.getString(Constants.Preference.PREFERENCE_USER_FULL_NAME,""));
        userDTO.setAvatar(PreferenceUtils.getString(Constants.Preference.PREFERENCE_USER_PICTURE,""));
        userDTO.setUserType(PreferenceUtils.getInt(Constants.Preference.PREFERENCE_USER_TYPE,0));
        userDTO.setPhone(PreferenceUtils.getString(Constants.Preference.PREFERENCE_USER_PHONE,""));
        userDTO.setStatus(PreferenceUtils.getString(Constants.Preference.PREFERENCE_USER_STATUS,""));
        userDTO.setToken(PreferenceUtils.getString(Constants.Preference.PREFERENCE_USER_TOKEN,""));
        userDTO.setLoginType(PreferenceUtils.getInt(Constants.Preference.PREFERENCE_LOGIN_TYPE, LoginTypeDTO.NORMAL.getValue()));
        return userDTO;
    }
    public void setProfile( ProfileDTO profile ) {
        this.profile = profile;
    }

    /**
     * Pop all tag trong list
     */
    public void popAllTag(){
        this.listStackTag.clear();
        PreferenceUtils.saveObject(this.listStackTag, Constants.CURRENT_TAG);
    }

    /**
     * remove tat ca fragment trong stack
     * @param fm
     */
    public void removeAllInBackStack(FragmentManager fm) {
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fm.executePendingTransactions();
        }
        popAllTag();
    }

    /**
     * Set current tag
     * @param tag
     */
    public void setCurrentTag(String tag) {
        this.listStackTag.add(tag);
        PreferenceUtils.saveObject(this.listStackTag, Constants.CURRENT_TAG);
    }

    public String getCurrentTag() {
        String tag = null;
        if(this.listStackTag != null && this.listStackTag.size() > 0) {
            tag = this.listStackTag.get(this.listStackTag.size() - 1);
        }
        return tag;
    }

    /**
     * Pop current tag
     */
    public void popCurrentTag(){
        if (this.listStackTag.size() > 0){
            this.listStackTag.remove(this.listStackTag.size() -1);
            PreferenceUtils.saveObject(this.listStackTag,Constants.CURRENT_TAG);
        }
    }

    /**
     * Add vi tri offline
     * @param pos
     */
    @SuppressWarnings("unchecked")
    public void addPosition(UserPositionLogDTO pos){
        Object temp;
        if (this.listPositionOffline == null || this.listPositionOffline.size() <= 0) {
            if ((temp = PreferenceUtils.readObject(
                PreferenceUtils.REFERENCE_POSITION)) != null) {
                this.listPositionOffline = (ArrayList<UserPositionLogDTO>) temp;
            }
        }
        this.listPositionOffline.add(pos);
        PreferenceUtils.saveObject(this.listPositionOffline,PreferenceUtils.REFERENCE_POSITION);
    }

    /**
     * Lay thong tin position offline
     * @return
     */
    @SuppressWarnings("unchecked")
    public ArrayList<UserPositionLogDTO> getListPositionOffline(){
        ArrayList<UserPositionLogDTO> listPos = new ArrayList<UserPositionLogDTO>();
        Object temp;
        if (this.listPositionOffline == null || this.listPositionOffline.size() <= 0) {
            if ((temp = PreferenceUtils.readObject(
                PreferenceUtils.REFERENCE_POSITION)) != null) {
                this.listPositionOffline = (ArrayList<UserPositionLogDTO>) temp;
            }
        }
        if (this.listPositionOffline.size() > 0){
            listPos = (ArrayList<UserPositionLogDTO>)this.listPositionOffline.clone();
        }
        return listPos;
    }

    /**
     * Clear position
     */
    public void clearListPosition(){
        if (this.listPositionOffline != null){
            this.listPositionOffline.clear();
            PreferenceUtils.saveObject(this.listPositionOffline,PreferenceUtils.REFERENCE_POSITION);
        }
    }

    /**
     * Load language
     * @return
     */
    public LanguagesDTO loadLanguages() {
        LanguagesDTO dto = null;
        LanguagesDTO dtoDefault = null;
        String languageDefault = "vi";
        String nameLanguageLoad = PreferenceUtils.getString(Constants.Preference.PREFERENCE_LANGUAGES,"");
        if (StringUtil.isNullOrEmpty(nameLanguageLoad)) {
//            nameLanguageLoad = Locale.getDefault().getLanguage();
            nameLanguageLoad = languageDefault;
        }
        ArrayList<LanguagesDTO> listLanguage = getListLanguage();
        for (LanguagesDTO languageDTO : listLanguage) {
            if (languageDTO.getValue().equals(nameLanguageLoad)) {
                dto = languageDTO;
                break;
            }
            //lay dto en
            if (languageDTO.getValue().equals(languageDefault)) {
                dtoDefault = languageDTO;
            }
        }

        if (dto == null && dtoDefault != null) {
            dto = dtoDefault;
        }
        return dto;
    }

    /**
     * Get language
     * @return
     */
    public ArrayList<LanguagesDTO> getListLanguage() {
        ArrayList<LanguagesDTO> listLang = new ArrayList<LanguagesDTO>();
        LanguagesDTO langVN = new LanguagesDTO(StringUtil.getString(R.string.text_language_name_vi), "vi", R.drawable
            .ic_nation_vietnam);
        LanguagesDTO langEN = new LanguagesDTO(StringUtil.getString(R.string.text_language_name_en), "en", R.drawable
            .ic_nation_en);
        listLang.add(langVN);
        listLang.add(langEN);
        return listLang;
    }

    /**
     * Save language
     * @param languageName
     */
    public void saveLanguages(String languageName){
        updateLanguageConfig(languageName);
        //update language
        PreferenceUtils.saveString(Constants.Preference.PREFERENCE_LANGUAGES,languageName);
    }

    /**
     * Update language
     * @param languageName
     */
    public void updateLanguageConfig( String languageName ) {
        Locale loc = new Locale(languageName);
        //Locale.setDefault(loc);
        Configuration config = new Configuration();
        config.locale = loc;
        JoCoApplication.getInstance().getAppContext().getResources().updateConfiguration(config, null);
        if (JoCoApplication.getInstance().getActivityContext() != null) {
            JoCoApplication.getInstance().getActivityContext().getResources().updateConfiguration(config, null);
            JoCoApplication.getInstance().getBaseContext().getResources().updateConfiguration(config, null);
        }
    }

    /**
     * subscrible or unsubscrible chanel notification
     * @param token
     * @param topic
     * @throws IOException
     */
    public void subscribleTopics(final String token,final List<Long> topic) {
        if(topic != null) {
         new AsyncTask<Void,Void,Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    if (!token.equals("")){
                        GcmPubSub pubSub = GcmPubSub.getInstance(JoCoApplication.getInstance().getActivityContext());
                        for (Long id : topic){
                            try {
                                pubSub.subscribe(token, "/topics/jc.tour.plan." + id, null);
                            } catch (IOException e) {
                                TraceExceptionUtils.getReportFromThrowable(e);
                            }
                        }
                    }
                    return null;
                }
            }.execute();
        }
    }

    /**
     * subscrible or unsubscrible chanel notification
     * @param token
     * @param topic
     * @throws IOException
     */
    public void unSubscribleTopics(final String token, final List<Long> topic){
        if(topic != null){
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void[] params) {
                    if (!token.equals("")){
                        GcmPubSub pubSub = GcmPubSub.getInstance(JoCoApplication.getInstance().getActivityContext());
                        for (Long id : topic){
                            try {
                                pubSub.unsubscribe(token,"/topics/jc.tour.plan." + id);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    return null;
                }
            }.execute();
        }
    }

    public int getSettingLocation() {
        if (settingLocation == valueDefault) {
            settingLocation = PreferenceUtils.getInt(Constants.Preference.PREFERENCE_SETTING_LOCATION, SettingViewDTO.SEND_LOCATION);
        }
        return settingLocation;
    }

    public void setSettingLocation(int settingLocation) {
        this.settingLocation = settingLocation;
        PreferenceUtils.saveInt(Constants.Preference.PREFERENCE_SETTING_LOCATION, settingLocation);
    }

    public int getSettingNotifyTour() {
        if (settingNotifyTour == valueDefault) {
            settingNotifyTour = PreferenceUtils.getInt(Constants.Preference.PREFERENCE_SETTING_NOTIFY_TOUR, SettingViewDTO.TURN_ON_NOTIFY_TOUR);
        }
        return settingNotifyTour;
    }

    public void setSettingNotifyTour(int settingNotifyTour) {
        this.settingNotifyTour = settingNotifyTour;
        PreferenceUtils.saveInt(Constants.Preference.PREFERENCE_SETTING_NOTIFY_TOUR, settingNotifyTour);
    }

    public int getSettingNotifyReview() {
        if (settingNotifyReview == valueDefault) {
            settingNotifyReview = PreferenceUtils.getInt(Constants.Preference.PREFERENCE_SETTING_NOTIFY_REVIEW, SettingViewDTO.TURN_ON_NOTIFY_REVIEW);
        }
        return settingNotifyReview;
    }

    public void setSettingNotifyReview(int settingNotifyReview) {
        this.settingNotifyReview = settingNotifyReview;
        PreferenceUtils.saveInt(Constants.Preference.PREFERENCE_SETTING_NOTIFY_REVIEW, settingNotifyReview);
    }

    /**
     * Get app version
     * @return
     */
    public String getAppVersion(){
        String result = "";
        try {
            result = JoCoApplication.getInstance().getPackageManager()
                .getPackageInfo(JoCoApplication.getInstance().getPackageName(), 0).versionName;
        } catch (Exception e) {
            Log.e(Utils.getTag(getActivityContext().getClass()), TraceExceptionUtils.getReportFromThrowable(e));
        }
        return result;
    }

    /**
     * Get device imei
     * @return
     */
    public String getDeviceIMEI() {
        String deviceImei = "";
        TelephonyManager telephonyManager = (TelephonyManager) JoCoApplication.getInstance().getAppContext().getSystemService(Context
            .TELEPHONY_SERVICE);
        deviceImei = telephonyManager.getDeviceId();
        if (StringUtil.isNullOrEmpty(deviceImei)) {
            deviceImei = "deviceImei";
        }
        return deviceImei;
    }

    public int getSettingShowLocation() {
        if (settingShowLocation == valueDefault) {
            settingShowLocation = PreferenceUtils.getInt(Constants.Preference.PREFERENCE_SETTING_SHOW_LOCATION, SettingViewDTO.SHOW_LOCATION);
        }
        return settingShowLocation;
    }

    public void setSettingShowLocation(int settingShowLocation) {
        PreferenceUtils.saveInt(Constants.Preference.PREFERENCE_SETTING_SHOW_LOCATION, settingShowLocation);
    }

    /**
     * Get google analytics tracker
     *
     * @return
     */
    public synchronized Tracker getGoogleAnalyticsTracker() {
        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
    }

    /***
     * Tracking screen view
     *
     * @param screenName screen name to be displayed on GA dashboard
     */
    public void trackScreenView( String screenName ) {
        Tracker t = getGoogleAnalyticsTracker();
        // Set screen name.
        t.setScreenName(screenName);
        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());
        GoogleAnalytics.getInstance(this).dispatchLocalHits();
    }

    /***
     * Tracking exception
     *
     * @param e exception to be tracked
     */
    public void trackException( Throwable e ) {
        if (e != null) {
            Tracker t = getGoogleAnalyticsTracker();
            t.send(new HitBuilders.ExceptionBuilder()
                .setDescription(
                    new StandardExceptionParser(this, null)
                        .getDescription(Thread.currentThread().getName(), e))
                .setFatal(false)
                .build()
            );
        }
    }

    /***
     * Tracking event
     *
     * @param category event category
     * @param action   action of the event
     * @param label    label
     */
    public void trackEvent( String category, String action, String label ) {
        /*Tracker t = getGoogleAnalyticsTracker();
        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder().setCategoryIds(category).setAction(action).setLabel(label).build());*/
    }

    /**
     * Get manufacturer
     * @return
     */
    public static String getManuFacturerName() {
        String manufacturer = Build.MANUFACTURER;
        return manufacturer!= null ? manufacturer.toUpperCase(): "";
    }

    /**
     * Get android version
     */
    public int getAndroidVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

}
