package com.boot.accommodation.map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.base.BaseFragmentMapView;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.listener.OnAddressListener;
import com.boot.accommodation.listener.OnEventMapControlListener;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.TraceExceptionUtils;
import com.boot.accommodation.util.Utils;
import com.boot.accommodation.vp.position.PositionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * GoogleFragmentMapView.java
 *
 * @author: dungdq3
 * @version: 1.0
 * @since: 8:58:20 AM Aug 5, 2014
 */
public class GoogleFragmentMapView
    extends BaseFragmentMapView
    implements
    OnMapLoadedCallback,
    OnMarkerDragListener,
    OnMarkerClickListener, OnMapClickListener, OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener{

    private static final float ZOOM_LEVEL_GOOGLE_NORMAL = 14 ;
    public class ListenerFromView {
        View view;
        OnEventMapControlListener listener;
        int action;
        private Object data;

        public Object getData() {
            return data;
        }
    }

    private InfoWindowAdapter adapter;
    public HashMap<String, ListenerFromView> hash;
    List<Marker> listPointRoute = null;
    Polyline polyRoute = null;

    public GoogleFragmentMapView(
        BaseActivity parent, InfoWindowAdapter adapter ) {
        super(parent);
        this.adapter = adapter;
    }

    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    //	final LatLng center = new LatLng(10.784829, 106.68307);
    final LatLng center = new LatLng(16.2872609, 107.1993245);

    public MapWrapperLayout bgMapView;
    // mapview cua google map
    //public MapView mapView;
    // google map
    public GoogleMap googleMap;
    // map tu id cua marker sang
    private Marker markerMyPosition;
    private Marker markerFlagPosition;
    private int widthMap = 0;
    private int heightMap = 0;
    private Marker lastOpenedMarker = null;
    //	TextMarkerMapView markerMapview;
    //view cua infowindow
    View infoWindowView = null;
    MapView mapView;
    //	OfflineMapManager offlineMapManager;
    public static final int ZOOM_LEVEL_GGMAP_DEFAULT = 5;
//    ArrayList<MarkerOptions> lstMarkerOption = new ArrayList<>();// list marker de add vao google map
//    ArrayList<LatLng> lstLatLng = new ArrayList<LatLng>(); // mang lat,lng de fix bound
    @Override
    public ViewGroup initMap( Bundle savedInstanceState ) {
        bgMapView = new MapWrapperLayout(parent);
        bgMapView.setBackgroundColor(Color.rgb(237, 234, 226));
        bgMapView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        //truoc khi su dung cac factory thi phai truyen vao App context de initialize
        MapsInitializer.initialize(JoCoApplication.getInstance().getAppContext());

        rlMainMapView = new RelativeLayout(parent);
        rlMainMapView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        rlMainMapView.addView(bgMapView);

        //add map
        mapView = new MapView(parent);
        mapView.onCreate(savedInstanceState);
        mapView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        bgMapView.addView(mapView);

//		markerMapview = new TextMarkerMapView(parent, R.drawable.icon_map, "", "", -1, -1);
        //add offline future
//		offlineMapManager = new OfflineMapManager(googleMap, parent, rlMainMapView);

        return rlMainMapView;
    }

    @Override
    public void renderMap() {
        mapView.getMapAsync(this);
    }

    @Override
    public void initZoomControl() {
        googleMap.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public void moveToCenterMyPosition() {
        if (googleMap == null) {
            return;
        }
        if (JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLatitude() != Constants.LAT_LNG_NULL
            && JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLongtitude() != Constants.LAT_LNG_NULL) {
            LatLng myLocation = new LatLng(JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLatitude(),
                JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLongtitude());
            CameraUpdate cu = CameraUpdateFactory.newLatLng(myLocation);
            googleMap.animateCamera(cu);
        }
    }

    public int getPaddingFitbounds() {
        return 180;
    }

    /**
     * Di chuyen ban don den 1 toa do
     *
     * @param lat
     * @param lng
     * @return: void
     * @throws:
     */
    public void moveMapTo( double lat, double lng ) {
        moveMapTo(new LatLng(lat, lng));
    }

    public void moveMapTo( LatLng latlng ) {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latlng).zoom(ZOOM_LEVEL_GOOGLE_NORMAL).build();

        if (googleMap != null) {
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    /**
     * Move map toi vi tri nao do voi muc zoom
     * @param latlng
     * @param zoomLevel
     */
    public void moveMapTo( LatLng latlng, int zoomLevel ) {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latlng).zoom(zoomLevel).build();
        if (googleMap != null) {
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    /**
     * covert view sang bitmap
     *
     * @param context
     * @return
     */
    public static Bitmap createDrawableFromView( Context context, Object object ) {
        // VTLog.e(true, "[Quang]", "start" + new Date());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        View view = (View) object;
        view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        // VTLog.e(true, "[Quang]", "end" + new Date());
        return bitmap;
    }

    public void clearAllCurrentMarker() {
        if (googleMap != null) {
            googleMap.clear();
        }
    }

    @Override
    public boolean onMarkerClick( Marker marker ) {
        // Check if there is an open info window
        if (lastOpenedMarker != null) {
            // Close the info window
            lastOpenedMarker.hideInfoWindow();

            // Is the marker the same marker that was already open
            if (lastOpenedMarker.equals(marker)) {
                // Nullify the lastOpened object
                lastOpenedMarker = null;
                // Return so that the info window isn't opened again
                return true;
            }
        }

        // Re-assign the last opened such that we can close it later
        lastOpenedMarker = marker;

        // Open the info window for the marker
        marker.showInfoWindow();

        // Event was handled by our code do not launch default behaviour.
        moveCameraAfterShowInfowindow(marker, 80);
        return true;
    }

    /**
     * move camera map xuong 1 doan sau khi show infowindow
     *
     * @param marker
     * @return: void
     * @throws:
     */
    private void moveCameraAfterShowInfowindow( Marker marker, final int pixel ) {
        if (googleMap != null) {
            Projection projection = googleMap.getProjection();
            LatLng markerLocation = marker.getPosition();
            Point screenPosition = projection.toScreenLocation(markerLocation);
            screenPosition.y -= pixel;
            LatLng latlng = projection.fromScreenLocation(screenPosition);
            CameraUpdate cu = CameraUpdateFactory.newLatLng(latlng);
            googleMap.animateCamera(cu);
        }
    }

    /**
     * Add View vao map
     * @param view
     * @param lat
     * @param lng
     */
    public Marker addMarkerToMap(final MarkerView view, double lat, double lng ) {
        LatLng latLng = new LatLng(lat, lng);
        MarkerOptions markerOptions = new MarkerOptions()
            .position(latLng).icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(parent, view))).anchor(
                (float)0.5,
                (float)1);
//        lstMarkerOption.add(markerOptions);
//        lstLatLng.add(latLng);
        final Marker marker = googleMap.addMarker(markerOptions);
        // truong hop ko co load hinh anh
        if (!StringUtil.isNullOrEmpty(view.getPhotoUrl())) {
            //load hinh xong moi set lai icon
            Glide.with(parent).load(view.getPhotoUrl())
                .asBitmap().thumbnail(.1f).fitCenter().placeholder(Utils.getDrawable(R.drawable.img_default_error))
                    .error(Utils.getDrawable(R.drawable.img_no_image)).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady( Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation ) {
                    try {
//                        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(bitmap);
                        view.setImageBitMap(bitmap);
                        marker.setIcon(BitmapDescriptorFactory.fromBitmap(GoogleFragmentMapView.createDrawableFromView
                            (parent, view)));
                    } catch (Exception e) {
                        Log.e("", TraceExceptionUtils.getReportFromThrowable(e));
                    }
                }
            });
        }
        return marker;
    }

    @Override
    public void initRefreshControl() {
        LinearLayout llMyPosition;
        llMyPosition = new LinearLayout(parent);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT);
        lp.setMargins(Utils.dip2Pixel(10), Utils.dip2Pixel(10), Utils.dip2Pixel(5), Utils.dip2Pixel(5));
        llMyPosition.setLayoutParams(lp);
        llMyPosition.setGravity(Gravity.LEFT);
        llMyPosition.setOrientation(LinearLayout.VERTICAL);

        ImageView ivMyPosition = new ImageView(parent);
        ivMyPosition.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT));
        ivMyPosition.setPadding(Utils.dip2Pixel(5), Utils.dip2Pixel(5), Utils.dip2Pixel(5),
            Utils.dip2Pixel(5));
//        ivMyPosition.setImageResource(R.drawable.ic_refresh_grey);
        ivMyPosition.setBackgroundResource(R.drawable.custom_button_with_border);
        ivMyPosition.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) {
                    listener.onRefresh();
                }
            }
        });
        llMyPosition.addView(ivMyPosition);
        rlMainMapView.addView(llMyPosition);
    }

    @Override
    public void onMarkerDrag( Marker arg0 ) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onMarkerDragEnd( Marker marker ) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onMarkerDragStart( Marker marker ) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onMapClick( LatLng point ) {
        if (listener != null) {
            listener.onSingleTap(point, null);
        }
//		onSingleTap(latlng, null);
    }

    @Override
    public void fitBounds( ArrayList<LatLng> arrayPosition ) {
        if (googleMap == null) {
            return;
        }
        ArrayList<LatLng> listLatLng = new ArrayList<LatLng>();
        for (LatLng latLng : arrayPosition) {
            LatLng latlg = new LatLng(latLng.latitude, latLng.longitude);
            listLatLng.add(latlg);
        }
        if (listLatLng != null && !listLatLng.isEmpty()) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (LatLng latLng : listLatLng) {
                builder.include(latLng);
            }
            LatLngBounds bounds = builder.build();
            //CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            if (widthMap <= 0 || heightMap <= 0) {
                widthMap = bgMapView.getWidth();
                heightMap = bgMapView.getHeight();
            }
            if (widthMap > 0 && heightMap > 0) {
                if(listLatLng.size() == 1){
                    moveMapTo(listLatLng.get(0));
                }else {
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,
                        widthMap, heightMap, getPaddingFitbounds());
                    if (googleMap != null) {
                        googleMap.animateCamera(cu);
                    }
                }
            }
        }
    }

    @Override
    public void initMyPositionControl() {
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationButtonClickListener(this);
    }

    @Override
    public void onMapLoaded() {
        mapView.invalidate();
        if(listener!= null) {
            listener.onMapLoaded();
        }
    }

    @Override
    public String requestGetCusLocation( double lat, double lng ) {
        List<Address> listAddress = null;
        String address = Constants.STR_BLANK;
        Geocoder coder = new Geocoder(parent, Locale.getDefault());
        try {
            listAddress = coder.getFromLocation(lat, lng, 1);
            address = listAddress.get(0).getAddressLine(0);
        } catch (IOException e) {
            Log.e("Google map", TraceExceptionUtils.getReportFromThrowable(e));
        }

        return address;
    }

    /**
     * Add marker
     * @param lat
     * @param lng
     * @param resource
     * @return
     */
    public Marker addMarker(double lat, double lng, int resource ) {
        if (googleMap != null) {
            return googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lng))
                    .icon(BitmapDescriptorFactory.fromResource(resource)).anchor(0.0f, 1f));
        } else {
            return null;
        }
    }

    @Override
    public void paddingControl(int left, int top, int right, int bottom) {
        googleMap.setPadding(left,top,right,bottom);
    }

    @Override
    public void showMarkerInMap( double lat, double lng, View view, MarkerPosition position ) {

    }

    @Override
    public void invalidate() {
        mapView.invalidate();
//		if (mapFragment.getView() != null) {
//			mapFragment.getView().invalidate();
//		}
    }

    @Override
    public void clearAllMarkerOnMap() {
        if (googleMap != null) {
            googleMap.clear();
//            lstMarkerOption.clear();
//            lstLatLng.clear();
        }
    }
    @Override
    public void setMarkerOnClickListener(Object marker, View markerView,
                                         OnEventMapControlListener listener, int action, Object userData ) {
        if (hash != null) {
            ListenerFromView a = hash.get(((Marker) marker).getId());
            if (a == null) {
                a = new ListenerFromView();
                a.action = action;
                a.data = userData;
                a.listener = listener;
                a.view = markerView;

                hash.put(((Marker) marker).getId(), a);
            }
        }
    }

    @Override
    public Object getMapView() {
        return googleMap;
    }

    @Override
    public void clearPopup() {
        if (lastOpenedMarker != null) {
            // Close the info window
            lastOpenedMarker.hideInfoWindow();

            // Is the marker the same marker that was already open
            if (lastOpenedMarker.equals(marker)) {
                // Nullify the lastOpened object
                lastOpenedMarker = null;
                // Return so that the info window isn't opened again
            }
        }
    }

    @Override
    public void getAdressFromLatLng( OnAddressListener listener, LatLng latlng ) {
        StringBuilder result = new StringBuilder();
        if (listener != null) {
            listener.showProgressLoading(true);
        }
        try {
            Geocoder geocoder = new Geocoder(JoCoApplication.getInstance().getActivityContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latlng.latitude, latlng.longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++){
                    if (i == address.getMaxAddressLineIndex()) {
                        result.append(address.getAddressLine(i));
                    } else {
                        result.append(address.getAddressLine(i)).append(", ");
                    }
                }
                if (listener != null) {
                    listener.setAddress(address, result.toString());
                }
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
        if (listener != null) {
            listener.showProgressLoading(false);
        }
    }

    @Override
    public void onResume() {
        mapView.onResume();
        Utils.checkGooglePlayServices(parent);
    }

    @Override
    public void onPause() {
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        if (mapView != null) {
            mapView.onDestroy();
        }
    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
    }

    @Override
    public void drawMarkerMyPosition( double lat, double lng ) {
        if (markerMyPosition != null) {
            markerMyPosition.remove();
        }
//        if (lat!= Constants.LAT_LNG_NULL && lng != Constants.LAT_LNG_NULL) {
//            markerMyPosition = addMarker(lat, lng, R.drawable.icon_my_location);
//        }
        mapView.invalidate();
    }

    @Override
    public void drawFlagMarkerPosition( double lat, double lng ) {
        if (markerFlagPosition != null) {
            markerFlagPosition.remove();
        }
        if (lat!= Constants.LAT_LNG_NULL && lng != Constants.LAT_LNG_NULL) {
            markerFlagPosition = addMarker(lat, lng, R.drawable.ic_flag_orange);
        }
        mapView.invalidate();
    }

    @Override
    public void setZoomBorder() {
        CameraPosition cameraPosition = new CameraPosition.Builder().zoom(4).build();

        if (googleMap != null) {
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    private void setAnchorMarker( Marker marker, MarkerAnchor anchorConfig ) {
        //anchorU 	u-coordinate of the anchor, as a ratio of the image width (in the range [0, 1])
        //anchorV 	v-coordinate of the anchor, as a ratio of the image height (in the range [0, 1])
        if (marker != null) {
            float anchorU = 0.5f;
            float anchorV = 0.5f;
            switch (anchorConfig) {
                case CENTER:
                    anchorU = 0.5f;
                    anchorV = 0.5f;
                    break;
                case BOTTOM_CENTER:
                    anchorU = 0.5f;
                    anchorV = 1f;
                    break;
                case BOTTOM_LEFT:
                    anchorU = 0f;
                    anchorV = 1f;
                    break;

                default:
                    anchorU = 0.5f;
                    anchorV = 0.5f;
                    break;
            }
            marker.setAnchor(anchorU, anchorV);
        }
    }
    @Override
    public void onMapReady( GoogleMap googleMap ) {
        this.googleMap = googleMap;
        googleMap.setOnMapLoadedCallback(this);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setOnMarkerDragListener(this);
        googleMap.setOnMapClickListener(this);

        hash = new HashMap<String, ListenerFromView>();
        initMyPositionControl();
        initZoomControl();
        clearAllMarkerOnMap();
//        initRefreshControl();
        moveMapTo(center, ZOOM_LEVEL_GGMAP_DEFAULT);
        bgMapView.init(googleMap, getPixelsFromDp(this, 39 + 20));

        googleMap.setOnMarkerClickListener(this);
        googleMap.setInfoWindowAdapter(this.adapter);

        googleMap.setInfoWindowAdapter(new InfoWindowAdapter() {

            private ListenerFromView listenerFromView;

            //			private ViewGroup view1;
            @Override
            public View getInfoWindow( Marker marker ) {
                if (hash != null) {
                    listenerFromView = hash.get(marker.getId());
                    if (listenerFromView != null) {
                        infoWindowView = listenerFromView.listener.onEventMap(listenerFromView.action, listenerFromView.view, listenerFromView.data);
                    }
                }
                if (infoWindowView == null) {
                    return null;
                }

                lastOpenedMarker = marker;

                bgMapView.setMarkerWithInfoWindow(marker, infoWindowView);

                return infoWindowView;
            }

            @Override
            public View getInfoContents( Marker marker ) {
                // TODO Auto-generated method stub
                return null;
            }
        });
        if(listener != null){
            listener.onMapReady();
        }
//        this.googleMap.setOnMyLocationButtonClickListener(this);
//        for(MarkerOptions markerOptions: lstMarkerOption){
//            Marker marker = googleMap.addMarker(markerOptions);
//        }
//        fitBounds(lstLatLng);
    }

    /**
     * Get pixel from dp
     * @param fragmentMapView
     * @param dp
     * @return
     */
    public static int getPixelsFromDp(BaseFragmentMapView fragmentMapView, double dp) {
        final double scale = fragmentMapView.parent.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * Draw marker my position
     */
    public void drawMarkerMyPosition() {
        // TODO Auto-generated method stub
        if (markerMyPosition != null) {
            markerMyPosition.remove();
        }

        if (JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLatitude() != Constants.LAT_LNG_NULL
            && JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLongtitude() != Constants.LAT_LNG_NULL) {
            markerMyPosition = addMarker(JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLatitude(),
                JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLongtitude(), R.drawable.icon_my_location);
        }

        mapView.invalidate();
    }

    @Override
    public boolean onMyLocationButtonClick() {
//        if (!PositionManager.getInstance().isEnableGPS() ) {
//            PositionManager.getInstance().showAlertEnableGPS(rlMainMapView, false, StringUtil.getString(R.string
//                .notify_setting_gps));
//        } else
        if (JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLatitude() == Constants.LAT_LNG_NULL
                || JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLongtitude() == Constants.LAT_LNG_NULL) {
            if (!PositionManager.getInstance().isEnableGPS()) {
                PositionManager.getInstance().showAlertEnableGPS(rlMainMapView, false, StringUtil.getString(R.string
                        .notify_setting_gps));
            } else {
                PositionManager.getInstance().showAlertEnableGPS(rlMainMapView, true, StringUtil.getString(R.string
                        .text_not_found_position));
            }
        } else if (listener != null) {
            listener.onClickMarkerMyPositionEvent();
        }
        return true;
    }

    @Override
    public void setInfoWindowAdapter(InfoWindowAdapter adapter){
        if(googleMap != null) {
            googleMap.setInfoWindowAdapter(adapter);
        }
    }

    @Override
    public void setOnInfoWindowClickListener(final OnEventMapControlListener listener, final int action) {
        if(googleMap != null) {
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                private ListenerFromView listenerFromView;
                @Override
                public void onInfoWindowClick(Marker marker) {
                    if (hash != null) {
                        listenerFromView = hash.get(marker.getId());
                        if (listenerFromView != null) {
                            infoWindowView = listenerFromView.listener.onEventMap(action, listenerFromView.view, listenerFromView.data);
                        }
                    }
                }
            });
        }
    }

    /**
     * Add duong di tren google map
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     */
    @Override
    public void addDirectionMap( double lat1, double lng1, double lat2, double lng2, final int color ) {
        GoogleDirection.withServerKey(StringUtil.getString(R.string.google_api_server_key))
            .from(new LatLng(lat1,lng1 ))
            .to(new LatLng(lat2,lng2))
//            .avoid(AvoidType.FERRIES)
//            .avoid(AvoidType.HIGHWAYS)
            .execute(new DirectionCallback() {
                @Override
                public void onDirectionSuccess( Direction direction, String rawBody) {
                    if(direction.isOK()) {
                        // Do something
                        Route route = direction.getRouteList().get(0);
                        Leg leg = route.getLegList().get(0);
                        List<Step> stepList = direction.getRouteList().get(0).getLegList().get(0).getStepList();
                        ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline
                            (JoCoApplication.getInstance().getActivityContext(),
                            stepList, 3, color, 0, Color.BLUE);
                        for (PolylineOptions polylineOption : polylineOptionList) {
                            googleMap.addPolyline(polylineOption);
                        }
                    } else {
                        // Do something

                    }
                }

                @Override
                public void onDirectionFailure(Throwable t) {
                    // Do something
                    int it  = 0;
                }
            });
    }

}
