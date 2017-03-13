package com.boot.accommodation.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Build;
import android.support.annotation.AnyRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.model.LatLng;
import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.AreaDTO;
import com.boot.accommodation.dto.view.CategoryItemDTO;
import com.boot.accommodation.dto.view.LocationCategoryTypeDTO;
import com.boot.accommodation.dto.view.LocationFilterItemDTO;
import com.boot.accommodation.listener.OnEventControl;
import com.boot.accommodation.vp.position.PositionManager;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Admin on 06/10/2015.
 */
public class Utils {
    private static final int REQUEST_GOOGLE_PLAY_SERVICES = 1000 ;

    public static String getString(@StringRes int message) {
        return JoCoApplication.getInstance().getString(message);
    }

    public static int getColor(@ColorRes int color) {
        return ContextCompat.getColor(JoCoApplication.getInstance(), color);
    }

    public static Drawable getDrawable(@DrawableRes int drawable) {
        return ContextCompat.getDrawable(JoCoApplication.getInstance(), drawable);
    }

    public static void hideSoftInput(Context context) {
        InputMethodManager keyboard = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void showSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInputFromInputMethod(activity.getCurrentFocus().getWindowToken(), InputMethodManager.RESULT_SHOWN);
    }
    /**
     * Hide keyboard voi view cu the
     * @param context
     * @param input
     */
    public static void hideKeyboardInput(BaseActivity context, View input) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
//        View view = context.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (input == null) {
            input = new View(context);
        }
        input.clearFocus();
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * Validate mail
     * @param email
     * @return
     */
    public static boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String showUserStatus(int status) {
        return "\"" + getString(status) + "\"";
    }

    public static String showUserStatus(String status) {
        return "\"" + status + "\"";
    }

    public static String getQuantityResource(int text, int total) {
        return JoCoApplication.getInstance().getResources().getQuantityString(text, total, total);
    }

    public static final Uri getUriToDrawable(@AnyRes int drawableId) {
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + JoCoApplication.getInstance().getAppContext().getResources().getResourcePackageName(drawableId)
                + '/' + JoCoApplication.getInstance().getAppContext().getResources().getResourceTypeName(drawableId)
                + '/' + JoCoApplication.getInstance().getAppContext().getResources().getResourceEntryName(drawableId));
        return uri;
    }

    /**
     * Lay so trang tu vi tri cua position trong listview
     * @param position
     * @param pagePer
     * @return
     * @author tuanlt
     */
    public static final int getPageFromPosition(int position, int pagePer){
        if(pagePer > 0){
            return position / pagePer ;
        }
        return position/ Constants.PAGE_ITEM_PER;
    }

    /**
     * Cancle load more with adapter
     *
     * @param adapter
     * @param pageSize
     * @param sizeList
     * @return
     */
    public static boolean checkLoadMore(BaseRecyclerViewAdapter adapter, int pageSize, int sizeList) {
        // danh cho lan goi cuoi cung truoc khi loadmore
        boolean isCancel = false;
        if (pageSize == 0) {
            isCancel = true;
        }
//        else if (pageSize != 0 && sizeList % pageSize != 0) {
//            isCancel = true;
//        }
        else if (sizeList == adapter.getPreviousSizeList()) {
            isCancel = true;
        }
        if (adapter != null) {
            adapter.setCancleLoadMore(isCancel);
        }
        adapter.setPreviousSizeList(sizeList);
        return !isCancel;
    }

    /**
     * Get tag class
     * @param clz
     * @return
     */
    public static String getTag(Class<?> clz){
        String tag = "";
        if (clz != null) {
            tag = clz.getName();
        }
        return tag;
    }

    /**
     * Kiem tra google play services
     * @return
     */
    public static boolean checkGooglePlayServices( BaseActivity parent) {
        if(parent == null) {
            return false;
        }
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int code = api.isGooglePlayServicesAvailable(parent);
        if (code == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(code) &&
            api.showErrorDialogFragment(parent, code,
                REQUEST_GOOGLE_PLAY_SERVICES)) {
        } else {
            String str = GoogleApiAvailability.getInstance().getErrorString(code);
            Toast.makeText(parent, str, Toast.LENGTH_LONG).show();
        }
        return false;
    }

    /**
     * Chuyen dip to pixel
     * @param dips
     * @return
     */
    public static int dip2Pixel(int dips) {
        int ret = (int) (JoCoApplication.getInstance().getAppContext()
            .getResources().getDisplayMetrics().density
            * dips + 0.5f);
        return ret;
    }

    //method callphone tourist
    public static void callPhone(Context c, String dialNumber) {
        try {
            Intent callIntent = new Intent();
            callIntent.setAction(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + dialNumber));
            c.startActivity(callIntent);
        } catch (Exception e) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + dialNumber));
            c.startActivity(callIntent);
        }
    }

    //public text message tourist
    public static void textMessage(Context c, String dialNumber) {
        Uri uri = Uri.parse("smsto:" + dialNumber);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("", "");
        c.startActivity(it);
    }

    /**
     * Tra ve kich thuoc theo pixel
     * @param resourceId
     * @return
     */
    public static int getDimension(int resourceId){
        return (int) JoCoApplication.getInstance().getAppContext()
            .getResources().getDimension(resourceId);
    }

    /**
     * Lay % pin
     * @return
     */
    public static int getBatteryPercent() {
        Intent batteryIntent = JoCoApplication.getInstance().getAppContext().registerReceiver(null, new IntentFilter(Intent
            .ACTION_BATTERY_CHANGED));
        float level = (float)batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        float scale = (float)batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        // Error checking that probably isn't needed but I added just in case.
        if(level <0 || scale < 0) {
            return 0;
        }
        return (int)((level / scale) * 100.0d);
    }

    /**
     * Lay so luong tien trinh dang thuc thi
     * @return
     */
    public static int getNumAsyncTaskActive() {
        int result = 0;
        try {
            ThreadPoolExecutor executor = getThreadPoolExecutor();
            if (executor != null) {
                result = executor.getActiveCount();
            }
        } catch (Exception ex) {
            // neu loi thi loi os, khong can xu ly gi them neu ko lay duoc
            Log.w("", TraceExceptionUtils.getReportFromThrowable(ex));
        }
        return result;
    }

    /**
     * Lay tien trinh dang thuc thi
     * @return
     * @throws Exception
     */
    public static ThreadPoolExecutor getThreadPoolExecutor() throws Exception {
        return AsyncTask.THREAD_POOL_EXECUTOR instanceof ThreadPoolExecutor ? ((ThreadPoolExecutor) AsyncTask.THREAD_POOL_EXECUTOR)
            : null;
    }

    /**
     * Lay khoang cach giua hai diem bat ki
     * @param sourcePoint
     * @param destinationPoint
     * @return
     */
    public static double getDistanceBetween(LatLng sourcePoint,
                                            LatLng destinationPoint) {
        int EARTH_RADIUS = 6378137; // ban kinh trai dat theo don vi m
        if (sourcePoint == null || destinationPoint == null) {
            return 0;
        }
        double R = EARTH_RADIUS / 1000; // Radius of the earth in km
        double dLat = Math.toRadians(destinationPoint.latitude - sourcePoint.latitude);
        double dLon = Math.toRadians(destinationPoint.longitude - sourcePoint.longitude);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
            + Math.cos(Math.toRadians(sourcePoint.latitude))
            * Math.cos(Math.toRadians(destinationPoint.latitude))
            * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = (R * c) * 1000; // Distance in m
        return Math.round(distance);
    }

    /**
     * Xoay view 90 do
     * @param view
     */
    public static void rotateViewOpen(View view) {
        view.animate().setDuration(150L).rotation(90.0F);
    }

    /**
     * Xoay view ve lai 0 do
     * @param view
     */
    public static void rotateViewClose(View view) {
        view.animate().setDuration(150L).rotation(0.0F);
    }

    /**
     * Check Permision co duoc su dung hay ko doi voi marshallow tro len
     * @param context
     * @param actionPermission : action sau khi duoc su dung
     * @param permissionName: ten permission dc phep su dung
     * @return
     */
    public static boolean checkPermission( final Activity context, final int actionPermission, final String
        permissionName) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, permissionName) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permissionName)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Permission is necessary for app!!!");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick( DialogInterface dialog, int which ) {
                            ActivityCompat.requestPermissions(context, new String[]{permissionName},
                                actionPermission);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{permissionName}, actionPermission);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    /**
     * Convert list to string
     * @param categories
     * @return
     */
    public static String convertListToString(List<CategoryItemDTO> categories, boolean check) {
        StringBuilder category = new StringBuilder();
        for (int i = 0; i < categories.size(); i++) {
            if ((categories.get(i).getSelected() && check) || !check) {
                if (StringUtil.isNullOrEmpty(category.toString())) {
                    category.append(categories.get(i).getId());
                } else {
                    category.append(Constants.STR_TOKEN + categories.get(i).getId());
                }
            }
        }
        return category.toString();
    }

    /**
     * Init category default
     */
    private static LocationFilterItemDTO initCategory(LocationFilterItemDTO locationFilterItemDTO) {
        CategoryItemDTO itemPark = new CategoryItemDTO();
        itemPark.setId(LocationCategoryTypeDTO.VISIT.getValue());
        itemPark.setName(LocationCategoryTypeDTO.getNameByValue(itemPark.getId()));
        locationFilterItemDTO.getCategories().add(itemPark);
        locationFilterItemDTO.setCategoryIds("" + itemPark.getId());

        CategoryItemDTO itemAdvanture = new CategoryItemDTO();
        itemAdvanture.setId(LocationCategoryTypeDTO.ADVENTURE.getValue());
        itemAdvanture.setName(LocationCategoryTypeDTO.getNameByValue(itemAdvanture.getId()));
        locationFilterItemDTO.getCategories().add(itemAdvanture);
        locationFilterItemDTO.setCategoryIds("" + itemAdvanture.getId());

        CategoryItemDTO itemChurch = new CategoryItemDTO();
        itemChurch.setId(LocationCategoryTypeDTO.CHURCH.getValue());
        itemChurch.setName(LocationCategoryTypeDTO.getNameByValue(itemChurch.getId()));
        locationFilterItemDTO.getCategories().add(itemChurch);
        locationFilterItemDTO.setCategoryIds("" + itemChurch.getId());

        CategoryItemDTO itemMuseum = new CategoryItemDTO();
        itemMuseum.setId(LocationCategoryTypeDTO.MUSEUM.getValue());
        itemMuseum.setName(LocationCategoryTypeDTO.getNameByValue(itemMuseum.getId()));
        locationFilterItemDTO.getCategories().add(itemMuseum);
        locationFilterItemDTO.setCategoryIds("" + itemMuseum.getId());

        CategoryItemDTO itemPagoda = new CategoryItemDTO();
        itemPagoda.setId(LocationCategoryTypeDTO.PAGODA.getValue());
        itemPagoda.setName(LocationCategoryTypeDTO.getNameByValue(itemPagoda.getId()));
        locationFilterItemDTO.getCategories().add(itemPagoda);
        locationFilterItemDTO.setCategoryIds("" + itemPagoda.getId());
        return locationFilterItemDTO;
    }

    /**
     * Init data default
     */
    public static LocationFilterItemDTO initCategoryDefault(LocationFilterItemDTO locationFilterItemDTO) {
        if (locationFilterItemDTO.getCategories().size() == 0) {
            locationFilterItemDTO = initCategory(locationFilterItemDTO);
            locationFilterItemDTO.setCategoryIds(Utils.convertListToString
                    (locationFilterItemDTO.getCategories(), false));
        }
        return locationFilterItemDTO;
    }

    /**
     * Get distance from another location with my location
     * @param latItem
     * @param lngItem
     * @return
     */
    public static double getDistance(double latItem, double lngItem) {
        Double lat = JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLatitude();
        Double lng = JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLongtitude();
        LatLng from = null;
        if (!(lat == Constants.LAT_LNG_NULL || lng == Constants.LAT_LNG_NULL)) {
            from = new LatLng(lat, lng);
        }
        LatLng to = new LatLng(latItem, lngItem);
        double distance = Utils.getDistanceBetween(from, to);
        return distance;
    }

    /**
     * Open mot link
     *
     * @param url
     */
    public static void openLink(Activity activity, String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(browserIntent);
    }

    /**
     * Check resource exits
     * @return
     */
    public static boolean checkResourceExists(BaseActivity baseActivity, String resourceName) {
        int checkExistence = baseActivity.getResources().getIdentifier(resourceName, "string",
                baseActivity.getPackageName());
        if (checkExistence != 0) {  // the resouce exists...
            return true;
        }
        return false;
    }

    /**
     * Get ara exists
     * @param areas
     * @param data
     */
    public static AreaDTO getAreaExisted(List<AreaDTO> areas, String data) {
        for (AreaDTO areaDTO : areas) {
            String temp1 = StringUtil.getEngStringFromUnicodeString(areaDTO.getFullLongName().toLowerCase()).trim();
            String temp2 = StringUtil.getEngStringFromUnicodeString(data.toLowerCase()).trim();
            if (!StringUtil.isNullOrEmpty(data) && temp1.equals(temp2)) {
                return areaDTO;
            }
        }
        return new AreaDTO();
    }

    /**
     * Validate my position
     */
    public static boolean validateMyLocation(View frDetail) {
        if (!PositionManager.getInstance().isEnableGPS()) {
            PositionManager.getInstance().showAlertEnableGPS(frDetail, false, StringUtil.getString(R.string
                    .notify_setting_gps));
            return false;
        } else if (JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLatitude() == Constants.LAT_LNG_NULL
                || JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLongtitude() == Constants.LAT_LNG_NULL) {
            PositionManager.getInstance().showAlertEnableGPS(frDetail, true, StringUtil.getString(R.string
                    .text_not_found_position));
            return false;
        }
        return true;
    }

    /**
     * Show dialog
     * @param baseActivity
     * @param listener
     * @param layoutId
     * @param actionOk
     * @param titleOk
     * @param actionCancel
     * @param titleCancel
     */
    public static AlertDialog showDialog(BaseActivity baseActivity, final OnEventControl listener, final Object data,
                                         int layoutId, final int actionOk, String titleOk, final int actionCancel, String titleCancel, final int actionShow) {
        final AlertDialog alertDialog; //Alert dialog
        LayoutInflater li = LayoutInflater.from(baseActivity);
        final View promptsView = li.inflate(layoutId, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(baseActivity);
        alertDialogBuilder.setView(promptsView);
        // Init view
        // set dialog message
        alertDialogBuilder.setPositiveButton(titleOk, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        alertDialogBuilder.setNegativeButton(titleCancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                listener.onEventControl(actionCancel, data, promptsView, 0);
            }
        });
        // create alert dialog
        alertDialog = alertDialogBuilder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                listener.onEventControl(actionShow, data, promptsView, 0);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onEventControl(actionOk, data, promptsView, 0);
                    }
                });
            }
        });
        return alertDialog;
    }

}
