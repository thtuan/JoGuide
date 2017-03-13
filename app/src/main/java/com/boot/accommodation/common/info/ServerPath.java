package com.boot.accommodation.common.info;

/**
 * Thong tin ket noi
 *
 * @author tuanlt
 * @since: 11:56 PM 5/11/2016
 */
public class ServerPath {

    private static final String DEV_URL = "http://192.168.1.104:8111/";
    // Server release
    private static final String PROD_URL = "https://joco.com.vn/homestay/";
    private static final String IMAGE_URL = "https://jcst.joco.com.vn/w/media/display/resize/";
    private static final String STATIC_PATH = "https://jcst.joco.com.vn/upload";
    private static final String LOCATION_PATH = "https://joco.com.vn/location/";
    private static final String LOCATION_GOOGLE_PATH = "https://maps.googleapis.com/maps/api/place/photo?";
    private static final String TOUR_PATH = "https://joco.com.vn/tour/";

    // Server test
//    private static final String PROD_URL = "http://beta.joco.com.vn/api/";
//    private static final String IMAGE_URL = "http://betajcst.joco.com.vn/w/media/display/resize/";
//    private static final String STATIC_PATH = "https://betajcst.joco.com.vn/upload";
//    private static final String LOCATION_PATH = "http://beta.joco.com.vn/location/";
//    private static final String LOCATION_GOOGLE_PATH = "https://maps.googleapis.com/maps/api/place/photo?";
//    private static final String TOUR_PATH = "http://beta.joco.com.vn/ftour/";
//    http://betajcst.joco.com.vn/media/display/resize/600x337/web/img/header/header-3.png”

    // Server test 2
//    private static final String PROD_URL = "http://spidey.joco.com.vn/homestay/";
//    private static final String IMAGE_URL = "http://betajcst.joco.com.vn/w/media/display/resize/";
//    private static final String STATIC_PATH = "http://betajcst.joco.com.vn/upload";
//    private static final String LOCATION_PATH = "http://spidey.joco.com.vn/location/";
//    private static final String LOCATION_GOOGLE_PATH = "https://maps.googleapis.com/maps/api/place/photo?";
//    private static final String TOUR_PATH = "http://spidey.joco.com.vn/tour/";
    private static final MODE_RUN RUNNING_MODE = MODE_RUN.PROD;

    public static String getStaticPath() {
        return STATIC_PATH;
    }

    public static enum MODE_RUN {
        DEV, PROD
    }

    public static String getHostUrl() {
        return RUNNING_MODE == MODE_RUN.PROD ? PROD_URL : DEV_URL;
    }

    public static String getImageUrl(String path, int width, int height) {
        return IMAGE_URL + "x" + height + "/" + path;
    }

    public static String getImageUrl(String path, int height) {
        return IMAGE_URL + "x" + height + "/" + path;
    }

    public static String getGoogleImageUrl() {
        return LOCATION_GOOGLE_PATH;
    }

    public static String getTourPath() {
        return TOUR_PATH;
    }

    public static String getLocationPath() {
        return LOCATION_PATH;
    }
}
