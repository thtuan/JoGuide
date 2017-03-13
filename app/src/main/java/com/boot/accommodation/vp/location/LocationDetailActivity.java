package com.boot.accommodation.vp.location;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.LocationTypeDTO;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.listener.FragmentListener;
import com.boot.accommodation.util.ImageUtil;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;
import com.boot.accommodation.vp.home.CustomViewPager;
import com.boot.accommodation.vp.home.HomeAdapter;
import com.boot.accommodation.vp.tour.TourPlaceDirectionActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Admin on 14/10/2015.
 */
public class LocationDetailActivity extends BaseActivity implements LocationDetailViewMgr, FragmentListener {

    @Bind(R.id.ivPlaceDetailHeader)
    ImageView ivPlaceDetailHeader;
    @Bind(R.id.tvPlaceDetailHeaderMark)
    TextView tvPlaceDetailHeaderMark;
    @Bind(R.id.tvPlaceDetailHeaderNumVoted)
    TextView tvPlaceDetailHeaderNumVoted;
    @Bind(R.id.tvPlaceDetailHeaderName)
    TextView tvPlaceDetailHeaderName;
    @Bind(R.id.ivFavourite)
    ImageView ivFavourite;
    @Bind(R.id.prLoading)
    ProgressBar progressLoad;
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fabAddPicture)
    FloatingActionButton fabAddPicture;
    @Bind(R.id.cdView)
    CoordinatorLayout cdView;
    @Bind(R.id.htab_tabs)
    TabLayout htab_tabs;
    @Bind(R.id.detailPager)
    CustomViewPager detailPager;
    @Bind(R.id.ivGoogle)
    ImageView ivGooge;
    TextView tvLocation;
    @Bind(R.id.fabDirection)
    FloatingActionButton fabDirection;
    @Bind(R.id.fabReportLocation)
    FloatingActionButton fabReportLocation;
    @Bind(R.id.clFunction)
    CoordinatorLayout clFunction;
    @Bind(R.id.llReportLocation)
    LinearLayout llReportLocation;
    @Bind(R.id.llDirection)
    LinearLayout llDirection;
    private boolean isShowingRating = false;
    private HomeAdapter adapter; // adapter pager
    private boolean isReviewSuccess = false;
    private long locationId;
    private PlaceItemDTO placeItemDTO = new PlaceItemDTO();
    private LocationDetailPresenterMgr presenter;
    private PlaceItemDTO checkInDTO = new PlaceItemDTO();
    private CheckinAdapter checkinAdapter;
    private RecyclerView recyclerView;
    private EditText etStatus;
    private LayoutInflater inflater;
    private View popUpView;
    //    private boolean isHaveService = false; //Variable check service existed or no
//    private AlertDialog alertDialogReport; //Alert report location
//    public RelativeLayout rlMainMapView;
//    public BaseFragmentMapView mapHelper;
//    GoogleMap.InfoWindowAdapter adapterMap;
    private Animation animationFabOpen, animationFabClose; //Animation close or open
    private boolean isOpenFunction = false; //Variable check open or close function

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        enableSlidingMenu(false);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            placeItemDTO = b.getParcelable(Constants.IntentExtra.PLACE_ITEM);
            locationId = placeItemDTO.getId();
            presenter = new LocationDetailPresenter(this);
            if (locationId == 0) {
                showProgressDialog(true);
                presenter.getPlaceDetailGG(placeItemDTO);
            } else {
                presenter.initDataHeader(placeItemDTO);
            }
        }
        initView();
    }

    /**
     * Init view
     */
    private void initView() {
        animationFabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        animationFabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
    }

    @Override
    public void initViewPager(PlaceItemDTO item) {
        setupViewPager(detailPager);
        htab_tabs.setupWithViewPager(detailPager);
        htab_tabs.setTabMode(TabLayout.MODE_FIXED);
        detailPager.setSwipeable(true);
    }

    /**
     * Khoi tao du lieu view pager
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        adapter = new HomeAdapter(getSupportFragmentManager());
        Bundle data = new Bundle();
        data.putParcelable(Constants.IntentExtra.PLACE_ITEM, placeItemDTO);
        //Put data for review
        data.putLong(Constants.IntentExtra.ITEM_ID, placeItemDTO.getId());
        adapter.addFrag(LocationDetailInfoFragment.newInstance(data), getString(R.string.text_place_info).toUpperCase());
        adapter.addFrag(LocationDetailPictureFragment.newInstance(data), getString(R.string.text_place_image).toUpperCase());
        adapter.addFrag(LocationDetailReviewFragment.newInstance(data), getString(R.string.text_place_review).toUpperCase());
//        adapter.addFrag(PlaceDetailServiceFragment.newInstance(data), getString(R.string.text_service).toUpperCase());
        viewPager.setOffscreenPageLimit(adapter.getFragmentList().size());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                handleChangeTab(position);
            }

            @Override
            public void onPageSelected(int position) {
                handleChangeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * Handle change tab
     *
     * @param position
     */
    private void handleChangeTab(int position) {
        if (adapter.getFragmentList().get(position) instanceof LocationDetailInfoFragment) {
            clFunction.setVisibility(View.VISIBLE);
        } else {
            clFunction.setVisibility(View.INVISIBLE);
        }
        if (adapter.getFragmentList().get(position) instanceof LocationDetailPictureFragment) {
            fabAddPicture.setVisibility(View.VISIBLE);
        } else {
            fabAddPicture.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setFavourite(Boolean isFavourite) {
        placeItemDTO.setIsFavourite(isFavourite);
        if (placeItemDTO.getIsFavourite()) {
            ivFavourite.setImageResource(R.drawable.ic_favourite_fill_white);
        } else {
            ivFavourite
                    .setImageResource(R.drawable.ic_favourite_white);
        }
        //renderHeader(placeItemDTO);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentExtra.PLACE_ITEM, placeItemDTO);
        sendBroadcast(Constants.ActionEvent.ACTION_UPDATE_FAVOURITE, bundle);
    }

    @Override
    public void checkinSucess(List<PlaceItemDTO> placeItemDTOs) {
        this.closeProgress();
        inflater = LayoutInflater.from(this);
        popUpView = inflater.inflate(R.layout.popup_checkin, null);
        etStatus = (EditText) popUpView.findViewById(R.id.etStatus);
        recyclerView = (RecyclerView) popUpView.findViewById(R.id.rvCheckedIn);
        tvLocation = (TextView) popUpView.findViewById(R.id.tvLocation);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (checkinAdapter == null) {
            checkinAdapter = new CheckinAdapter(placeItemDTOs);
            checkinAdapter.setEnableLoadMore(true);
            checkinAdapter.setListener(this);
            recyclerView.setAdapter(checkinAdapter);
        } else {
            checkinAdapter.setData(placeItemDTOs);
        }

        checkinAdapter.notifyDataSetChanged();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(popUpView);
        dialogBuilder
                .setPositiveButton(Utils.getString(R.string.text_send), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkInDTO.setDescription(etStatus.getText().toString());
//                                checkInDTO.setAddress(tvLocation.getText().toString());
                        presenter.createCheckIn(checkInDTO);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(Utils.getString(R.string.text_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        dialogBuilder.show();

    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        closeProgress();
        closeProgressDialog();
        switch (errorCode) {
            default:
                handleError(errorCode, error);
        }
    }

    @Override
    public void renderPopup(List<PlaceItemDTO> lstPlaceDTO) {
        closeProgress();
        if (checkinAdapter == null) {
            checkinAdapter = new CheckinAdapter(lstPlaceDTO);
            checkinAdapter.setListener(this);
            checkinAdapter.setEnableLoadMore(true);
            recyclerView.setAdapter(checkinAdapter);
        } else {
            checkinAdapter.setData(lstPlaceDTO);
        }
        checkinAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * tạo header
     *
     * @param item
     */
    @Override
    public void renderHeader(PlaceItemDTO item) {
//        this.closeProgress();
        this.placeItemDTO = item;
        closeProgressDialog();
        if (LocationTypeDTO.GOOGLE.getValue() == item.getLocationType()) {
            ivGooge.setVisibility(View.VISIBLE);
            ImageUtil.loadImage(ivPlaceDetailHeader, ImageUtil.getGoogleImageUrl(item.getPhoto()), progressLoad);
        } else {
            ivGooge.setVisibility(View.GONE);
            ImageUtil.loadImage(ivPlaceDetailHeader, ImageUtil.getImageUrl(item.getPhoto()), progressLoad);
        }
        tvPlaceDetailHeaderMark.setText(String.valueOf(item.getRatePoint()));
        tvPlaceDetailHeaderNumVoted.setText(String.valueOf(item.getNumVoted()) + Constants.STR_SPACE + StringUtil.getString(R
                .string
                .text_default_num_voted));
        tvPlaceDetailHeaderName.setText(item.getName());
        if (item.getIsFavourite()) {
            ivFavourite.setImageResource(R.drawable.ic_favourite_fill_white);
        } else {
            ivFavourite
                    .setImageResource(R.drawable.ic_favourite_white);
        }
//        if (item.getCategoryId() != null && (item.getFamousLocationId() > 0)) {
//            if (adapter != null && !isHaveService) {
//                isHaveService = true;
//                Bundle data = new Bundle();
//                data.putParcelable(Constants.IntentExtra.PLACE_ITEM, placeItemDTO);
//                adapter.addFrag(PlaceDetailServiceFragment.newInstance(data), getString(R.string.text_service).toUpperCase());
//                adapter.notifyDataSetChanged();
//                htab_tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
//            }
//        }
    }

    @Override
    public void fragmentChanging(Fragment currentFragment) {
        if (!isReviewSuccess) {
            if (currentFragment instanceof LocationDetailInfoFragment) {
                clFunction.setVisibility(View.VISIBLE);
            } else {
                clFunction.setVisibility(View.GONE);
                if (isShowingRating) {
//                    ftShowRating.setIcon(R.drawable.ic_arrow_up);
//                    slideToDown();
                    isShowingRating = false;
                }
            }
        }
    }

    @OnClick({R.id.fabDirection, R.id.fabReportLocation, R.id.ivBack, R.id.ivFavourite, R.id.ivCheckin, R.id
            .fabAddPicture, R.id.fabFunction})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.fabFunction: {
                if (!isOpenFunction) {
                    llDirection.startAnimation(animationFabOpen);
                    llReportLocation.startAnimation(animationFabOpen);
                } else {
                    llDirection.startAnimation(animationFabClose);
                    llReportLocation.startAnimation(animationFabClose);
                }
                isOpenFunction = !isOpenFunction;
                break;
            }
            case R.id.fabDirection: {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.IntentExtra.PLACE_ITEM, placeItemDTO);
                goNextScreen(TourPlaceDirectionActivity.class, bundle);
                break;
            }
            case R.id.fabReportLocation:
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.IntentExtra.PLACE_ITEM, placeItemDTO);
                goNextScreen(LocationDetailReportActivity.class, bundle);
                break;
            case R.id.ivBack:
                super.onBackPressed();
                break;
            case R.id.ivFavourite:
                presenter.requestFavouritePlace(locationId);
                break;
            case R.id.ivCheckin:
                showProgress();
                presenter.requestCheckin(JoCoApplication.getInstance().getProfile().getMyGPSInfo());
                break;
            case R.id.fabAddPicture:
                sendBroadcast(Constants.ActionEvent.ACTION_UPLOAD_PICTURE, new Bundle());
                break;
        }
    }

    @Override
    public void onEventControl(int action, Object item, View View, int position) {
        switch (action) {
            case Constants.ActionEvent.ACTION_CHECKIN:
                this.showProgress();
                checkInDTO = new PlaceItemDTO();
                checkInDTO = (PlaceItemDTO) item;
                tvLocation.setText(checkInDTO.getName());
                tvLocation.setBackground(Utils.getDrawable(R.drawable.border_light_grey));
        }
    }

    @Override
    public void onLoadMore(int position) {
        presenter.getMorePlaces(checkinAdapter);
    }


}
