package com.boot.accommodation.vp.tour;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.boot.accommodation.R;
import com.boot.accommodation.common.control.JoCoEditText;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.LocationFilterItemDTO;
import com.boot.accommodation.listener.OnEditTextControlListener;
import com.boot.accommodation.vp.home.AreaAdapter;
import com.boot.accommodation.vp.home.HomeBaseActivity;
import com.boot.accommodation.util.Utils;
import com.boot.accommodation.vp.home.CustomViewPager;
import com.boot.accommodation.vp.home.HomeAdapter;
import com.boot.accommodation.vp.location.LocationSearchFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Activity for search
 *
 * @author Vuong-bv
 * @since: 6/10/2016
 */
public class SearchActivity extends HomeBaseActivity {

    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.etSearch)
    JoCoEditText etSearch;
    @Bind(R.id.llHeader)
    RelativeLayout llHeader;
    @Bind(R.id.tlSearch)
    TabLayout tlSearch;
    @Bind(R.id.htabAppbar)
    AppBarLayout htabAppbar;
    @Bind(R.id.vpSearchPager)
    CustomViewPager vpSearchPager;
    @Bind(R.id.rlFragmentMap)
    CoordinatorLayout rlBackground;
    @Bind(R.id.ivFilter)
    ImageView ivFilter;
    private HomeAdapter adapter; // adapter pager
    private String keyWord;//param for search pass from Home activity
    private int typeSearch;//param for search pass from Home activity
    private static final int TYPE_LOCATION = 0; // loai location
    private static final int TYPE_TOUR = 1;// loai tour
    private AreaAdapter areaAdapter; // Area adapter to search
    private LocationSearchFragment placeSearchFragment; //Place search
    private TourSearchFragment tourSearchFragment; //tour search
    private String oldKeyWord = ""; //Old keyword

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableSlidingMenu(false);
        setContentView(R.layout.activity_search);
        initData();
        initViewPager();
        etSearch.setText(keyWord);
        etSearch.clearFocus();
        etSearch.setImageSearchVisible(true);
        etSearch.setImageClearVisibile(R.drawable.ic_clear_grey, true);
        Utils.hideKeyboardInput(this, etSearch);
        setSearchEvent();
    }

    /**
     * Init data
     */
    private void initData(){
//        Intent intent = getIntent();
//        if (intent != null) {
//            keyWord = intent.getStringExtra(Constants.IntentExtra.KEYWORD);
//            typeSearch = intent.getIntExtra(Constants.IntentExtra.TYPE_SEARCH, 0);
//            locationFilterItemDTO = (LocationFilterItemDTO)intent.getSerializableExtra(Constants.IntentExtra
//                    .LOCATION_FILTER);
//        }
        locationFilterItemDTO = new LocationFilterItemDTO();
    }

    /**
     * Init view pager
     */
    private void initViewPager() {
        setupViewPager();
        tlSearch.setupWithViewPager(vpSearchPager);
        vpSearchPager.setSwipeable(true);
        vpSearchPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                enableFilter(ivFilter, position);
            }

            @Override
            public void onPageSelected(int position) {
//                enableFilter(ivFilter, position);
                if (!etSearch.getText().toString().equals(oldKeyWord)) {
                    oldKeyWord = etSearch.getText().toString();
                    handleSearch();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupViewPager() {
        adapter = new HomeAdapter(getSupportFragmentManager());
        Bundle bundleLocation = putParamSearch();
        Bundle bundleTour = putParamSearch();
        if (typeSearch == TYPE_LOCATION) {
            bundleLocation.putString(Constants.IntentExtra.KEYWORD, keyWord);
        } else if (typeSearch == TYPE_TOUR) {
            bundleTour.putString(Constants.IntentExtra.KEYWORD, keyWord);
        }
        adapter.addFrag(placeSearchFragment = LocationSearchFragment.newInstance(bundleLocation), getString(R.string.text_locations).toUpperCase
                ());
        adapter.addFrag(tourSearchFragment = TourSearchFragment.newInstance(bundleTour), getString(R.string.text_tours).toUpperCase());
        vpSearchPager.setOffscreenPageLimit(adapter.getFragmentList().size());
        vpSearchPager.setAdapter(adapter);
        vpSearchPager.setCurrentItem(typeSearch);
    }

    /**
     * Set su kien search
     */
    private void setSearchEvent() {
        //get event : enter from keyboard for search
        etSearch.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //      Toast.makeText(SearchActivity.this, "fuck you", Toast.LENGTH_SHORT).show();
                    handleSearch();
                    Utils.hideKeyboardInput(SearchActivity.this, etSearch);
                    return true;
                }
                return false;
            }
        });

        etSearch.setListener(new OnEditTextControlListener() {
            @Override
            public void onTouchControl() {
                if(locationFilterSearch == null) {
                    getFilterAreasSearch();
                }
            }

            @Override
            public void onClear() {
            }
        });
        etSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Utils.hideKeyboardInput(SearchActivity.this, etSearch);
                handleSearch();
            }
        });
    }

    /**
     * Handle search
     */
    private void handleSearch() {
        keyWord = etSearch.getText().toString();
        if (!isHaveFamousLocation) {
            locationFilterItemDTO.setFamousLocation(homePresenterMgr.getFamousPlaceChoose
                    (etSearch.getText().toString()));
        }
//        int type = vpSearchPager.getCurrentItem();
        placeSearchFragment.getLocationSearch(keyWord, true);
        tourSearchFragment.getTourSearch(keyWord, true);
        Utils.hideKeyboardInput(SearchActivity.this, etSearch);
//        if (type == TYPE_TOUR) {
////            Fragment fragment = adapter.getFragmentList().get(TYPE_TOUR);
////            if (fragment != null) {
////                ((TourSearchFragment) fragment).getTourSearch(keyWord, true);
////                Utils.hideKeyboardInput(SearchActivity.this, etSearch);
////            }
//            tourSearchFragment.getTourSearch(keyWord, true);
//            Utils.hideKeyboardInput(SearchActivity.this, etSearch);
//        } else if (type == TYPE_LOCATION) {
////            Fragment fragment = adapter.getFragmentList().get(TYPE_LOCATION);
////            if (fragment != null) {
////                ((LocationSearchFragment) fragment).getLocationSearch(keyWord, true);
////                Utils.hideKeyboardInput(SearchActivity.this, etSearch);
////            }
//            placeSearchFragment.getLocationSearch(keyWord, true);
//            Utils.hideKeyboardInput(SearchActivity.this, etSearch);
//        }
    }

    @OnClick({R.id.ivBack, R.id.ivFilter})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                super.onBackPressed();
                break;
            case R.id.ivFilter:
                getDataFilter();
                break;
        }
    }

    /**
     * Put param search
     * @return
     */
    private Bundle putParamSearch() {
        Bundle data = new Bundle();
        data.putInt(Constants.IntentExtra.TYPE_SEARCH, typeSearch);
        data.putSerializable(Constants.IntentExtra.LOCATION_FILTER, locationFilterItemDTO);
        return data;
    }

    @Override
    public void receiveBroadcast(int action, Bundle bundle) {
        switch (action){
            case Constants.ActionEvent.ACTION_UPDATE_LOCATION_FILTER_SEARCH:
                renderLocationFilterSearch();
                break;
            case Constants.ActionEvent.ACTION_UPDATE_LOCATION:
                handleSearch();
                break;
            default:
                super.receiveBroadcast(action, bundle);
        }
    }

    /**
     * Render location search
     */
    private void renderLocationFilterSearch(){
        if(areaAdapter == null){
            areaAdapter = new AreaAdapter(this, android.R.layout.simple_spinner_item, locationFilterSearch);
            etSearch.setAdapter(areaAdapter);
        }

    }
}
