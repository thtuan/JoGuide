package com.boot.accommodation.constant;

/**
 * Created by Admin on 21/10/2015.
 */
public class Constants {
    public static final class Preference {
        public static final String PREFERENCE_USER_FACEBOOK_ID = "user_facebook_id";
        public static final String PREFERENCE_USER_GOOGLE_ID = "user_google_id";
        public static final String PREFERENCE_USER_ID = "user_id";
        public static final String PREFERENCE_USER_NAME = "user_name";
        public static final String PREFERENCE_USER_FULL_NAME = "user_full_name";
        public static final String PREFERENCE_USER_PICTURE = "user_picture";
        public static final String PREFERENCE_USER_STATUS = "user_status";
        public static final String PREFERENCE_USER_TOKEN = "user_token";
        public static final String PREFERENCE_USER_TYPE= "user_type";
        public static final String PREFERENCE_USER_PHONE= "user_phone";
        public static final String PREFERENCE_USER_EMAIL= "user_email";
        public static final String PREFERENCE_LOGIN_TYPE= "login_type";
        public static final String PREFERENCE_USER_FIRST_NAME = "first_name";
        public static final String PREFERENCE_USER_LAST_NAME = "last_name";
        public static final String PREFERENCE_LANGUAGES = "languages";
        public static final String PREFERENCE_NOTIFICATION_COUNT ="notification_count" ;
        public static final String PREFERENCE_SETTING_LOCATION ="setting_location" ;
        public static final String PREFERENCE_SETTING_NOTIFY_TOUR ="setting_notify_tour" ;
        public static final String PREFERENCE_SETTING_NOTIFY_REVIEW ="setting_notify_review" ;
        public static final String PREFERENCE_SETTING_SHOW_LOCATION="setting_show_location" ;
        public static final String PREFERENCE_USER_LOGIN = "user_login" ;
        public static final String PREFERENCE_GCM_TOKEN = "gcm_token" ;
        public static final String PREFERENCE_FROM = "preference_from";
        public static final String PREFERENCE_URI = "preference_uri";
        public static final String PREFERENCE_IS_FIRST_SHOW_APP = "is_first_show_app";
        public static final String PREFERENCE_IS_FIRST_SHOW_MAP = "is_first_show_map";
        public static final String PREFERENCE_IS_FIRST_SHOW_LIST = "is_first_show_list";
        public static final String PREFERENCE_SHOW_ALERT_APP_VERSION = "show_alert_app_version";
        public static final String PREFERENCE_VERSION_ALERT_SHOW = "version_alert_show";
        public static final String PREFERENCE_DEEP_LINK_TOUR_ID = "deepLinkTourId";
        public static final String PREFERENCE_DEEP_LINK_TOUR_PLAN_ID = "deepLinkTourPlanId";
    }

    public static final class IntentExtra {
        public static final String DATE_TIME = "date_time";
        public static final String LAUNCH_TOUR_ID = "launch_tour_id";
        public static final String TOURIST_DTO = "tourist_dto";
        public static final String PAGE_INDEX = "page_index";
        public static final String PLACE_ITEM = "place_item";
        public static final String USER_ITEM = "user_item";
        public static final String TRIP_ITEM = "trip_item";
        public static final String TRIP_PHOTO = "tour_photo";
        public static final String POSITION = "position";
        public static final String TOUR_ID = "tour_id";
        public static final String TOUR_NAME = "tour_name";
        public static final String TOURIST_ID = "tourist_id";
        public static final String TYPE_USER= "type_user";
        public static final String IS_MY_PROFILE= "is_my_profile";
        public static final String INTENT_DATA = "data";
        public static final String TYPE_SEARCH_LOCATION= "typeLocation";
        public static final String TOURIST_INFO = "tourist_info";
        public static final String TYPE_SEARCH_TOUR= "typeTour";
        public static final String TOUR_PLACE_ID= "tourPlaceId";
        public static final String TOUR_ITINERARY= "tourItinerary";
        public static final String FROM_MY_FAVOURITE= "fromMyFavourite";
        public static final String FROM_MY_TOUR_TRIP= "fromMyTourTrip";
        public static final String FROM_NOTIFICATION_MENU = "fromNotificationMenu";
        public static final String NOTIFICATION_ID = "notificationId";
        public static final String KEYWORD = "keyword";
        public static final String LAT = "lat";
        public static final String LNG = "lng";
        public static final String TYPE_SEARCH = "typeSearch";
        public static final String USER_NAME = "user_name";
        public static final String USER_NUMBER = "user_number";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String TOUR_PLAN_ID = "tourPlanId";
        public static final String USER_ID = "userId" ;
        public static final String SEARCH_TEXT = "searchText";
        public static final String FROM_COLLECTION = "fromCollection";
        public static final String NOTIFICATION = "notification";
        public static final String IS_JOIN = "isJoin";
        public static final String USER_DATA = "userData";
        public static final String UPLOAD_PHOTO_DTO = "uploadPhotoDTO" ;
        public static final String FROM_SCREEN = "fromScreen" ;
        public static final String TITLE = "title" ;
        public static final String UNIT_PRICE = "unit_price" ;
        public static final String LOCATION_FILTER = "locationFilter" ;
        public static final String FROM_TIME_TO_GO = "timeToGo" ;
        public static final String TYPE_FOLLOW = "type_follow";
        public static final String MONTH_IDS = "monthIds";
        public static final String LIST_AREA = "areas";
        public static final String FROM_PLACE_CREATED = "placeCreated" ;
        public static final String TYPE_FAVOURITE = "typeFavourite";
        public static final String PROMOTION_DEAL = "promotionDeal";
        public static final String PROMOTION_DEAL_ID = "promotionDealId";
        public static final String NEW_CREATE_LOCATION = "newCreateLocation";
        public static final String TYPE_REVIEW = "typeReview";
        public static final String ITEM_ID = "itemId";
        public static final String SHOW_PRICE = "showPrice";
    }

    public static final class ActionEvent {
        public static final int ACTION_UPDATE_POSITION_SERVICE = 1;
        public static final int ACTION_UPDATE_POSITION = 2;
        public static final int NOTIFY_REFRESH = 3;
        public static final int REQUEST_CODE_SCHEDULE = 4;
        public static final int RESULT_OK = 5;
        public static final int ACTION_VIEW_SEARCH = 6;
        public static final int ACTION_SIGNUP_SUCCESS = 7;
        public static final int ACTION_UPDATE_FAVOURITE = 8;
        public static final int ACTION_CHECKIN = 9;
        public static final int ACTION_FINISH_ALL_AND_START_ACTIVITY = 10;
        public static final int ACTION_UPDATE_INFO_LOCATION = 11;
        public static final int ACTION_TURN_ON_TOUR_NOTIFY = 12;
        public static final int ACTION_TURN_ON_REVIEW_NOTIFY = 13;
        public static final int ACTION_CHANGE_PROFILE = 14;
        public static final int ACTION_UPDATE_GCM_SERVICE = 15;
        public static final int ACTION_SIGN_OUT = 16;
        public static final int ACTION_DELETE_REVIEW = 17;
        public static final int REFESH_LIST_FOLLOW = 18;
        public static final int ACTION_VIEW_TOUR_INFO = 19;
        public static final int ACTION_UPDATE_LOCATION = 20;
        public static final int ACTION_FINISH_ALL_ACTIVITY = 21;
        public static final int ACTION_CHOOSE_CATEGORY = 22;
        public static final int ACTION_UPDATE_LOCATION_FILTER_SEARCH = 23;
        public static final int ACTION_CHOOSE_MONTH_BEST_TIME = 24;
        public static final int ACTION_UPLOAD_PICTURE = 25;
        public static final int ACTION_GO_TO_PROMOTION_DEAL_INFO = 26;
        public static final int ACTION_GET_LOCATION_INFO_SUCCESS = 27;
        public static final int ACTION_REGISTER_SUCCESS = 28;
    }

    /**
     * Log name de xuat
     */
    public static final class LogName {
        public static final String EXCEPTION = "exception";
        public static final String LOG_GPS = "loggps";
    }

    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String STR_BLANK = "";
    public static final String STR_COLON = ":";
    public static final String STR_SPACE = " ";
    public static final String STR_SUBTRACTION = " - ";
    public static final String STR_BRACKET_LEFT  = " (";
    public static final String STR_BRACKET_RIGHT  = ") ";
    public static final String STR_TOKEN  = ", ";
    public static final String STR_SPLASH  = "/";
    public static final String STR_BREAK_LINE = "\n";
    public static final int PAGE_ITEM_PER = 20; // mac dinh so luong item cho mot lan load la 20
    public static final String HASHCODE_BROADCAST = "com.boot.accommodation.hashCode";
    public static final String JOCO_ACTION = "com.boot.accommodation.BROADCAST";
    public static final String ACTION_BROADCAST = "com.boot.accommodation.action";
    public static final String BROACAST_PERMISSION = "com.boot.accommodation.permission.BROADCAST";
    public static final String CURRENT_TAG = "JocoCurrentTag";
    public static final double LAT_LNG_NULL = Double.MAX_VALUE;
    public static final String ALARM_TITLE = "alarmTitle";
    public static final String ALARM_MESSAGE = "alarmMessage";
    public static final String ALARM_BUNDLE = "alarmBundle";
    public static final String ALARM_DATE = "alarmDate";
    public static final String ALARM_PARCELABLE = "alarmParcelabe";
    public static final String ALARM_PARCELABLE1 = "alarmParcelabe";
    public static final String TEMP_IMG = "temp_image";
    public static final long SCOPE_DAY_MILI = 2592000000L;
    public static final String PICK_CONTACT = "pick_contact" ;
    public static final int PICK_EMAIL = 191 ;
    public static final String FACEBOOK_APP_ID = "facebook_app_id_new_last";
    public static final int MAX_FULL_IMAGE_HEIGHT_ZOOM = 1000;
    public static final int MAX_FULL_IMAGE_WIDTH_UPLOAD = 600;
    public static final int MAX_FULL_IMAGE_HEIGHT_UPLOAD = 600;
    public static final long MILI_SECOND_OF_DAY = 1000*60*60*24 ;
    public static final int MAX_THUMB_IMAGE_WIDTH_DOWNLOAD = 150;
    public static final int MAX_THUMB_IMAGE_HEIGHT_DOWNLOAD = 150;
    public static final String STR_BRACKET_SQUARE_RIGHT = "]";
    public static final String STR_BRACKET_SQUARE_LEFT  = "[";
    public static final int MAX_LINE_COMMENT = 5;
    public static final int MAX_IMAGE_MARKER_WIDTH = 60;
    public static final int MAX_IMAGE_MARKER_HEIGHT = 60;
    public static final float ALPHA_UPLOAD_PHOTO = 0.6f;
    public static final int MAX_FULL_IMAGE_HEIGHT_DOWNLOAD = 300;
    public static final int MAX_THUMB_IMAGE_WIDTH_AVATAR_OWNER_DOWNLOAD = 60;
    public static final int MAX_THUMB_IMAGE_HEIGHT_AVATAR_OWNER_DOWNLOAD = 60;
}