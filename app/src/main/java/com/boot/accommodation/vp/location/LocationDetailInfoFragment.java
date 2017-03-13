package com.boot.accommodation.vp.location;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.common.control.MyClickableSpan;
import com.boot.accommodation.common.control.NonUnderLineClickSpan;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.dto.view.TabletActionLogDTO;
import com.boot.accommodation.dto.view.TripItemDTO;
import com.boot.accommodation.map.GoogleFragmentMapView;
import com.boot.accommodation.map.ImageMarkerMapView;
import com.boot.accommodation.util.DateUtil;
import com.boot.accommodation.util.ExpandCollapseUtil;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;
import com.boot.accommodation.vp.tour.TourActivity;
import com.boot.accommodation.vp.tour.TourRelateAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by xdung on 3/8/2016.
 */
public class LocationDetailInfoFragment extends BaseFragment implements LocationDetailOverviewMgr, OnMapReadyCallback {

    public static final int ACTION_VIEW_PLACE_DETAIL = 1000;
    @Bind(R.id.tvAddress)
    TextView tvAddress;
    @Bind(R.id.tvPhone)
    TextView tvPhone;
    @Bind(R.id.tvWebsite)
    TextView tvWebsite;
    @Bind(R.id.rvLocationRelate)
    RecyclerView rvLocationRelate;
    @Bind(R.id.wvOverView)
    WebView wvOverView;
    @Bind(R.id.wvOutStanding)
    WebView wvOutStanding;
    @Bind(R.id.tlPhone)
    TableRow tlPhone;
    @Bind(R.id.tlWebsite)
    TableRow tlWebsite;
    @Bind(R.id.tlOpeningTime)
    TableRow tlOpeningTime;
    @Bind(R.id.tvOpeningTime)
    TextView tvOpeningTime;
    @Bind(R.id.tvBestTimeToGo)
    TextView tvBestTimeToGo;
    @Bind(R.id.tlBestTimeToGo)
    TableRow tlBestTimeToGo;
    LocationDetailOverviewPresenterMgr presenter; // presenter
    GoogleMap googleMap; // google map
    PlaceItemDTO placeItemDTO; // thong tin dia diem
    private static View view;// view render giao dien
    @Bind(R.id.llToursRelate)
    LinearLayout llToursRelate;
    @Bind(R.id.llOutStanding)
    LinearLayout llOutStanding;
    @Bind(R.id.llSpecialFood)
    LinearLayout llSpecialFood;
    @Bind(R.id.wvSpecialFood)
    WebView wvSpecialFood;
    @Bind(R.id.ivAcrossOutStading)
    ImageView ivAcrossOutStading;
    @Bind(R.id.ivAcrossPosition)
    ImageView ivAcrossPosition;
    @Bind(R.id.ivAcrossSpecialFood)
    ImageView ivAcrossSpecialFood;
    @Bind(R.id.ivAcrossIntro)
    ImageView ivAcrossIntro;
    @Bind(R.id.frMap)
    FrameLayout frMap;
    @Bind(R.id.tvPrice)
    TextView tvPrice;
    @Bind(R.id.tlPrice)
    TableRow tlPrice;
    private boolean isAddMarker = false; // kiem tra da add marker location chua
    TourRelateAdapter tourRelateAdapter;// adapter for relate tour
    private boolean isFirst = true;
    private ExpandCollapseUtil exOutStanding;//Expand collapse out standing
    private ExpandCollapseUtil exPosition;//Expand collapse position
    private ExpandCollapseUtil exSpecialFood;//Expand collapse special food
    private ExpandCollapseUtil exIntro;//Expand collapse location introduce
    private String timeStart = ""; //Time start call
    private String timeEnd = ""; //Time end call
    private boolean isCall = false; //Variable check call or no
    private String infoCall = ""; //Info call

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public static Fragment newInstance(Bundle data) {
        LocationDetailInfoFragment fragment = new LocationDetailInfoFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_place_detail_info;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if (b != null) {
            placeItemDTO = b.getParcelable(Constants.IntentExtra.PLACE_ITEM);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enableRefresh(true);
        showProgress();
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirst) {
            isFirst = false;
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.IntentExtra.PLACE_ITEM, placeItemDTO);
            mActivity.switchFragment(InnitLocationFragment.newInstance(bundle), R.id.frLocationMap, false);
        }
        if (isCall) {
            isCall = false;
            timeEnd = DateUtil.now();
            requestSendLog();
        }
    }

    /**
     * Khoi tao du lieu
     */
    public void initData() {
        presenter = new LocationDetailOverviewPresenter(this);
        presenter.getOverviewInfo(placeItemDTO);
    }

    @Override
    public void renderLayout(PlaceItemDTO placeItemDTO) {
        closeProgress();
        if (!StringUtil.isNullOrEmpty(placeItemDTO.getAddress())) {
            tvAddress.setText(placeItemDTO.getAddress());
        }
        if (!StringUtil.isNullOrEmpty(placeItemDTO.getWebsite())) {
            tlWebsite.setVisibility(View.VISIBLE);
            tvWebsite.setText(placeItemDTO.getWebsite());
        } else {
            tlWebsite.setVisibility(View.GONE);
        }
        if (placeItemDTO.getOutStanding() != null && !StringUtil.isNullOrEmpty(placeItemDTO.getOutStanding().trim())) {
            wvOutStanding.setVisibility(View.VISIBLE);
            llOutStanding.setVisibility(View.VISIBLE);
            setContentWebView(wvOutStanding, placeItemDTO.getOutStanding());
        } else {
            wvOutStanding.setVisibility(View.GONE);
            llOutStanding.setVisibility(View.GONE);
        }
        if (!StringUtil.isNullOrEmpty(placeItemDTO.getWorkingTime())) {
            tlOpeningTime.setVisibility(View.VISIBLE);
            tvOpeningTime.setText(placeItemDTO.getWorkingTime());
        } else {
            tlOpeningTime.setVisibility(View.GONE);
        }
//        if (!StringUtil.isNullOrEmpty(placeItemDTO.getBestTimeTogo())) {
//            tlBestTimeToGo.setVisibility(View.VISIBLE);
//            tvBestTimeToGo.setText(placeItemDTO.getBestTimeTogo());
//        } else {
//            tlBestTimeToGo.setVisibility(View.GONE);
//        }
        //Handle get tour relate
//        if (placeItemDTO.getCategoryId() != null && !placeItemDTO.getCategoryId().contains(LocationCategoryTypeDTO
//                .HOTEL.getValue())) {
//            presenter.getTourRelate(placeItemDTO.getId());
//            llToursRelate.setVisibility(View.VISIBLE);
//        } else {
//        }
        llToursRelate.setVisibility(View.GONE);
        if (StringUtil.isNullOrEmpty(placeItemDTO.getSpecialFood())) {
            llSpecialFood.setVisibility(View.GONE);
        } else {
            llSpecialFood.setVisibility(View.VISIBLE);
            wvSpecialFood.setVisibility(View.VISIBLE);
            setContentWebView(wvSpecialFood, placeItemDTO.getSpecialFood());
        }
        updateDescription(placeItemDTO.getDescription());
        // update header
        if (mActivity instanceof LocationDetailActivity) {
            ((LocationDetailActivity) mActivity).renderHeader(placeItemDTO);
        }
        stopRefresh();

        if (placeItemDTO.getPriceFrom() == 0 && placeItemDTO.getPriceTo() == 0) {
            tlPrice.setVisibility(View.GONE);
        } else {
            String priceUnit = !StringUtil.isNullOrEmpty(placeItemDTO.getPriceUnit()) ? StringUtil
                    .getStringResourceByName(placeItemDTO.getPriceUnit().trim()) : "";
            String priceFrom = StringUtil.parseMoneyByLocale(String.valueOf(placeItemDTO.getPriceFrom())) + Constants
                    .STR_SPACE + priceUnit ;
            String priceTo = StringUtil.parseMoneyByLocale(String.valueOf(placeItemDTO.getPriceTo())) + Constants
                    .STR_SPACE + priceUnit;
            tlPrice.setVisibility(View.VISIBLE);
            String result = "";
            if (placeItemDTO.getPriceFrom() > 0) {
                result = priceFrom;
            }
            if (placeItemDTO.getPriceTo() > 0) {
                result += Constants.STR_SUBTRACTION + priceTo;
            }
            tvPrice.setText(result);
        }

        if (!StringUtil.isNullOrEmpty(placeItemDTO.getPhoneNumber())) {
            String splits[] = placeItemDTO.getPhoneNumber().split("[-/\\\\]");
            SpannableStringBuilder spanTxt = new SpannableStringBuilder(placeItemDTO.getPhoneNumber());
            int start = 0, end;
            for (final String temp: splits) {
                end = start + temp.length();
                if (start < end) {
                    spanTxt.setSpan(new MyClickableSpan(temp) {
                        @Override
                        public void onClick(View widget) {
                            callPhoneTourist(temp);
                        }

                    }, start, end, 0);
                }
                start += temp.length();
            }
            tlPhone.setVisibility(View.VISIBLE);
            tvPhone.setMovementMethod(LinkMovementMethod.getInstance());
            tvPhone.setText(spanTxt, TextView.BufferType.SPANNABLE);
//            tvPhone.setText(placeItemDTO.getPhoneNumber());
        } else {
            tlPhone.setVisibility(View.GONE);
            tvPhone.setVisibility(View.GONE);
        }
    }

    @Override
    public void renderTourRelate(List<TripItemDTO> tripItemDTO) {
//        closeProgress();
        //set adapter for tour relate result
        if (tourRelateAdapter == null) {
            tourRelateAdapter = new TourRelateAdapter(tripItemDTO);
            tourRelateAdapter.setListener(this);
            tourRelateAdapter.setEnableLoadMore(true);
            tourRelateAdapter.setHorizotalList(true);
            rvLocationRelate.setAdapter(tourRelateAdapter);
        } else {
            tourRelateAdapter.setData(tripItemDTO);
        }
        tourRelateAdapter.notifyDataSetChanged();
        stopRefresh();
    }

    @Override
    public void onLoadMore(int position) {
        presenter.getMoreTourRelate(placeItemDTO.getId(), tourRelateAdapter);
    }

    /**
     * Add marker to map
     *
     * @param view
     * @param lat
     * @param lng
     * @return
     */
    public Marker addMarkerToMap(final ImageMarkerMapView view, double lat, double lng) {
        LatLng latLng = new LatLng(lat, lng);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng);
//                .icon(BitmapDescriptorFactory.fromBitmap(GoogleFragmentMapView.createDrawableFromView(mActivity, view)));
        final Marker marker = googleMap.addMarker(markerOptions);
        Glide.with(this).load(view.getPhotoUrl())
                .asBitmap().fitCenter().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
//                BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(bitmap);
                view.setImageBitMap(bitmap);
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(GoogleFragmentMapView.createDrawableFromView
                        (mActivity, view)));
            }
        });
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLng);
        googleMap.moveCamera(cameraUpdate);
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
        return marker;
    }

    /**
     * Update description location
     *
     * @param description
     */
    private void updateDescription(String description) {
        if (StringUtil.isNullOrEmpty(description)) {
            wvOverView.setVisibility(View.GONE);
        } else {
            wvOverView.setVisibility(View.VISIBLE);
//            wvOverView.load(Html.fromHtml(description));
//            wvOverView.loadData(description, "text/html; charset=utf-8", "UTF-8");
            setContentWebView(wvOverView, description);
        }
    }

    @Override
    public void finishProcessDialog() {
    }

    @OnClick({R.id.tvWebsite, R.id.frOutStanding, R.id.frPosition, R.id.frSpecialFood, R.id.frIntro})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvPhone:
                callPhoneTourist(tvPhone.getText().toString());
                break;
            case R.id.tvWebsite:
                Utils.openLink(mActivity, tvWebsite.getText().toString());
                break;
            case R.id.frOutStanding:
                if (exOutStanding == null) {
                    exOutStanding = new ExpandCollapseUtil();
                }
                exOutStanding.animationView(ivAcrossOutStading, wvOutStanding);
                break;
            case R.id.frPosition:
                if (exPosition == null) {
                    exPosition = new ExpandCollapseUtil();
                }
                exPosition.animationView(ivAcrossPosition, frMap);
                break;
            case R.id.frSpecialFood:
                if (exSpecialFood == null) {
                    exSpecialFood = new ExpandCollapseUtil();
                }
                exSpecialFood.animationView(ivAcrossSpecialFood, wvSpecialFood);
                break;
            case R.id.frIntro:
                if (exIntro == null) {
                    exIntro = new ExpandCollapseUtil();
                }
                exIntro.animationView(ivAcrossIntro, wvOverView);
                break;
        }
    }

    @Override
    public void onEventControl(int action, Object item, View View, int position) {
        switch (action) {
            case Constants.ActionEvent.ACTION_VIEW_TOUR_INFO:
                Bundle bd = new Bundle();
                bd.putParcelable(Constants.IntentExtra.TRIP_ITEM, (TripItemDTO) item);
                mActivity.goNextScreen(TourActivity.class, bd);
                break;
        }
    }

    /**
     * Goi dien
     *
     * @param dialNumber
     */
    private void callPhoneTourist(String dialNumber) {
        isCall = true;
        timeStart = DateUtil.now();
        try {
            Intent callIntent = new Intent();
            callIntent.setAction(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + dialNumber));
            startActivity(callIntent);
        } catch (Exception e) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + dialNumber));
            startActivity(callIntent);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        googleMap.clear();
        if (!isAddMarker) {
            ImageMarkerMapView imageMarkerMapView = new ImageMarkerMapView(mActivity, placeItemDTO);
            addMarkerToMap(imageMarkerMapView, placeItemDTO.getLat(), placeItemDTO.getLng());
            isAddMarker = true;
        }
    }

    @Override
    protected void receiveBroadcast(int action, Bundle extras) {
        switch (action) {
            case Constants.ActionEvent.NOTIFY_REFRESH:
            case Constants.ActionEvent.ACTION_UPDATE_INFO_LOCATION:
                presenter.getOverviewInfo(placeItemDTO);
                break;
        }
    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        stopRefresh();
        closeProgress();
        switch (errorCode) {
            default:
                handleError(errorCode, error);
        }
    }

    /**
     * Set content web view
     *
     * @param webview
     * @param content
     */
    private void setContentWebView(WebView webview, String content) {
        webview.loadDataWithBaseURL(null,
                StringUtil.getHtmlResizeImage(content), "text/html", "utf-8", null);
//        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    /**
     * Requeset send log
     */
    private void requestSendLog() {
        TabletActionLogDTO tabletActionLogDTO = new TabletActionLogDTO();
        tabletActionLogDTO.setContent("TIME_CALL_PHONE");
        tabletActionLogDTO.setAndroidVersion(JoCoApplication.getInstance().getAndroidVersion());
        tabletActionLogDTO.setAppVersion(JoCoApplication.getInstance().getAppVersion());
        tabletActionLogDTO.setDeviceImei(JoCoApplication.getInstance().getDeviceIMEI());
        tabletActionLogDTO.setCreateAt(DateUtil.formatNow(DateUtil.FORMAT_DATE_TIME_ZONE));
        infoCall = Constants.STR_BRACKET_LEFT + "id = " + placeItemDTO.getId() + Constants.STR_BRACKET_RIGHT;
        if (!StringUtil.isNullOrEmpty(placeItemDTO.getName())) {
            infoCall += placeItemDTO.getName();
        }
        if (!StringUtil.isNullOrEmpty(placeItemDTO.getAddress())) {
            infoCall += Constants.STR_SUBTRACTION + placeItemDTO.getAddress();
        }
        if (!StringUtil.isNullOrEmpty(JoCoApplication.getInstance().getProfile().getUserData().getId())) {
            tabletActionLogDTO.setUserId(Long.valueOf(JoCoApplication.getInstance().getProfile().getUserData().getId
                    ()));
        }
        tabletActionLogDTO.setDescription(infoCall + Constants.STR_BREAK_LINE + DateUtil.convertDateWithFormat(timeStart,
                DateUtil.FORMAT_DATE_TIME_ZONE, DateUtil.FORMAT_DATE_TIME_FULL) + Constants.STR_SUBTRACTION +
                DateUtil.convertDateWithFormat(timeEnd, DateUtil.FORMAT_DATE_TIME_ZONE, DateUtil.FORMAT_DATE_TIME_FULL));
        tabletActionLogDTO.setType(TabletActionLogDTO.LOG_CLIENT);
        requestUpdateLog(tabletActionLogDTO);
    }
}
