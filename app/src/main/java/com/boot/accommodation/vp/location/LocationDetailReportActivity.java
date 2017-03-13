package com.boot.accommodation.vp.location;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.common.control.JoCoEditText;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.dto.view.PlaceReportDTO;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Place detail report
 *
 * @author tuanlt
 * @since 10:39 AM 9/22/2016
 */
public class LocationDetailReportActivity extends BaseActivity implements LocationDetailReportViewMgr {

    @Bind(R.id.etContent)
    JoCoEditText etContent;
    @Bind(R.id.ivGoogle)
    ImageView ivGoogle;
    @Bind(R.id.nsvView)
    NestedScrollView nsvView;
    @Bind(R.id.llMain)
    LinearLayout llMain;
    private LocationDetailReportFragment placeDetailReportFragment; //Place detail fragment
    private PlaceItemDTO placeItemDTO; //Place item dto
    private LocationDetailReportPresenterMgr placeDetailReportPresenterMgr; //Place detail report
    private double lat = Constants.LAT_LNG_NULL; //Lat
    private double lng = Constants.LAT_LNG_NULL; //Lng

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail_report);
        enableSlidingMenu(false);
        enableRefresh(true);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    /**
     * Init view
     */
    private void initView() {
        ivGoogle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        nsvView.requestDisallowInterceptTouchEvent(true);
                        // Disable touch on transparent view
                        return false;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        nsvView.requestDisallowInterceptTouchEvent(false);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        nsvView.requestDisallowInterceptTouchEvent(true);
                        return false;
                    default:
                        return true;
                }
            }
        });

    }

    /**
     * Init data
     */
    private void initData() {
        Bundle bundle = getIntent().getExtras();
        placeItemDTO = bundle.getParcelable(Constants.IntentExtra.PLACE_ITEM);
        lat = placeItemDTO.getLat();
        lng = placeItemDTO.getLng();
        switchFragment(placeDetailReportFragment = LocationDetailReportFragment.newInstance(bundle), R.id.frDetail,
                false);
    }

    @OnClick({R.id.ivBack, R.id.ivDone, R.id.llMain})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                super.onBackPressed();
                Utils.hideSoftKeyboard(this);
                break;
            case R.id.ivDone:
                if (placeDetailReportPresenterMgr == null) {
                    placeDetailReportPresenterMgr = new LocationDetailReportPresenter(this);
                }
                if (!StringUtil.isNullOrEmpty(etContent.getText().toString())) {
                    Utils.hideKeyboardInput(this, etContent);
                    showProgressDialog(true);
                    PlaceReportDTO placeReportDTO = new PlaceReportDTO();
                    placeReportDTO.setFeedback(etContent.getText().toString());
                    if (lat != placeItemDTO.getLat() || lng != placeItemDTO.getLng()) {
                        placeReportDTO.setLat(placeItemDTO.getLat());
                        placeReportDTO.setLng(placeItemDTO.getLng());
                    }
                    placeDetailReportPresenterMgr.requestReportLocation(placeItemDTO.getId(), placeReportDTO);
                    Utils.hideSoftKeyboard(this);
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string
                            .text_please_input_reason_report_accommodation), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.llMain:
                Utils.hideSoftKeyboard(this);
                break;
        }
    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        closeProgressDialog();
        switch (errorCode) {
            default:
                handleError(errorCode, error);
        }
    }

    @Override
    public void showReportLocationSuccess() {
        showMessage(R.string.text_report_accommodation_success);
        closeProgressDialog();
        finish();
    }

    /**
     * Update location
     */
    public void updateLocation(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public void receiveBroadcast(int action, Bundle bundle) {
        switch (action) {
            case Constants.ActionEvent.NOTIFY_REFRESH:
                placeDetailReportFragment.refreshLocation(placeItemDTO);
                break;
        }
    }
}
