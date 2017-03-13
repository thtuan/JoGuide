package com.boot.accommodation.vp.location;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.boot.accommodation.R;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.dto.view.TourItineraryItemDTO;
import com.boot.accommodation.listener.OnEventMapControlListener;
import com.boot.accommodation.map.CustomFragmentMapView;
import com.boot.accommodation.map.ImageMarkerMapView;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;
import com.boot.accommodation.vp.tour.SpinnerDay;
import com.boot.accommodation.vp.tour.SpinnerAdapter;

import java.util.ArrayList;

/**
 * Hien thi cac dia diem cua lich trinh tour tren ban do
 *
 * @author tuanlt
 * @since 11:12 AM 5/24/2016
 */
public class TourLocationPositionFragment extends CustomFragmentMapView implements OnEventMapControlListener,
     AdapterView.OnItemSelectedListener {

    public static final int ACTION_CLICK_PLACE = 1000; // action click dia diem
    public static final int ACTION_CLICK_PLACE_DETAIL = 1001; // action click chi tiet dia diem
    private ArrayList<TourItineraryItemDTO> tourItinerary = new ArrayList<>(); //ds dia diem
    ArrayList<LatLng> lstLatLngFitBounds = new ArrayList<>(); // mang fit bound
    private SpinnerDay spiner;// spiner tuyen trong tuan
    private SpinnerAdapter adapterLine;// spiner tuyen trong tuan
    private int indexSelected = -1; // vi tri chon
    private LinearLayout llSpiner; // spiner chon ngay
    // ds mau default
    private int[] lstColor = new int[]{
            Utils.getColor(R.color.primary_500),
            Utils.getColor(R.color.red),
            Utils.getColor(R.color.blue),
            Utils.getColor(R.color.purple),
            Utils.getColor(R.color.orange),
            Utils.getColor(R.color.lime),
            Utils.getColor(R.color.blue_grey),
    };

    public static TourLocationPositionFragment newInstance(Bundle bundle) {
        TourLocationPositionFragment f = new TourLocationPositionFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_custom_map;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!= null){
            tourItinerary = bundle.getParcelableArrayList(Constants.IntentExtra.INTENT_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        return v;
    }

    /**
     * Khoi tao control view
     */
    private void initView(){
        String[] days = new String[tourItinerary.get(tourItinerary.size()-1).getLocation().getDateSeq() + 1];
        days[0] = StringUtil.getString(R.string.text_all);
        for (int i = 0, size = tourItinerary.get(tourItinerary.size()-1).getLocation().getDateSeq(); i < size; i++) {
            days[i + 1] = StringUtil.getString(R.string.text_day) + Constants.STR_SPACE + String.valueOf(i + 1);
        }
        addSpinnerDay(days);
    }

    @Override
    public void onMapReady() {
        super.onMapReady();
        indexSelected = 0;
        initView();
        renderLayout();
    }

    @Override
    public void onMapLoaded() {
        super.onMapLoaded();
        fitBounds(lstLatLngFitBounds);
    }

    /**
     * render map
     */
    private void renderLayout() {
        drawPlacesInMap();
    }

    /**
     * Ve tren ban do
     */
    private void drawPlacesInMap() {
        clearAllMarkerOnMap();
        for (int i = 0, s = tourItinerary.size(); i < s; i++) {
            if(indexSelected == 0 || indexSelected == tourItinerary.get(i).getLocation().getDateSeq()){
                PlaceItemDTO item = tourItinerary.get(i).getLocation();
                ImageMarkerMapView imageMarkerMapView = new ImageMarkerMapView(mActivity, item);
                imageMarkerMapView.setSTT(tourItinerary.get(i).getLocation().getActivitySeq());
                Marker marker = addMarker(imageMarkerMapView, item.getLat(), item.getLng());
                setMarkerOnClickListener(marker, imageMarkerMapView, this, ACTION_CLICK_PLACE, item);
                if (item.getLat() != Constants.LAT_LNG_NULL && item.getLng() != Constants.LAT_LNG_NULL) {
                    lstLatLngFitBounds.add(new LatLng(item.getLat(), item.getLng()));
                }
                // van con diem o phia sau

                if(i < s -1 && tourItinerary.get(i).getLocation().getDateSeq() == tourItinerary.get(i + 1).getLocation()
                        .getDateSeq()) {
                    // noi duong di giua hai diem
                    int color = lstColor[tourItinerary.get(i).getLocation().getDateSeq() % lstColor.length];
                    PlaceItemDTO itemDest = tourItinerary.get(i + 1).getLocation();
                    if (itemDest.getLat() != Constants.LAT_LNG_NULL && itemDest.getLng() != Constants.LAT_LNG_NULL) {
                        addDirectionMap(item.getLat(), item.getLng(), itemDest.getLat(),
                                itemDest.getLng(), color);
                    }
                }
            }



        }
        setOnInfoWindowClickListener(this, ACTION_CLICK_PLACE_DETAIL);
        setInfoWindowAdapter(new LocationDetailInfoWindow(mapHelper,mActivity));
    }

    @Override
    public View onEventMap(int eventType, View control, Object data) {
        switch (eventType) {
            case ACTION_CLICK_PLACE:
                break;
            case ACTION_CLICK_PLACE_DETAIL:
                gotoPlaceDetail((PlaceItemDTO) data);
                break;
        }
        return null;
    }

    /**
     * Di toi man hinh chi tiet
     * @param placeItemDTO
     */
    public void gotoPlaceDetail(PlaceItemDTO placeItemDTO) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentExtra.PLACE_ITEM, placeItemDTO);
        (mActivity).goNextScreen(LocationDetailActivity.class, bundle);
    }

    /**
     * Add spinner ngay
     * @param days
     */
    private void addSpinnerDay(String[] days){
        spiner = new SpinnerDay(mActivity, 0);
        spiner.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));
        spiner.setMinimumWidth(110);
        spiner.setGravity(Gravity.CENTER_HORIZONTAL);
        adapterLine = new SpinnerAdapter(mActivity,
            R.layout.sp_item_day, days);
        spiner.setOnItemSelectedListener(this);
        spiner.setAdapter(adapterLine);
        spiner.setSelection(0);
        indexSelected = spiner.getSelectedItemPosition();
        if(llSpiner != null) {
            rlMainMapView.removeView(llSpiner);
        }
        llSpiner = new LinearLayout(rlMainMapView.getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
        llSpiner.setLayoutParams(params);
        llSpiner.setOrientation(LinearLayout.HORIZONTAL);
        llSpiner.addView(spiner);
        llSpiner.setGravity(Gravity.LEFT | Gravity.TOP);
        llSpiner.setPadding(Utils.dip2Pixel(8),Utils.dip2Pixel(8),0,0);
        rlMainMapView.addView(llSpiner);
    }

    @Override
    public void onItemSelected( AdapterView<?> parent, View view, int position, long id ) {
        if(indexSelected!= position){
            indexSelected = position;
            drawPlacesInMap();
        }
    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected placeItemDTO.
     */
    @Override
    public void onNothingSelected( AdapterView<?> parent ) {

    }
}
