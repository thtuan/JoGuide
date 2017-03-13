package com.boot.accommodation.vp.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.boot.accommodation.R;
import com.boot.accommodation.common.control.JoCoEditText;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.LocationFilterItemDTO;
import com.boot.accommodation.gcm.GCMService;
import com.boot.accommodation.listener.OnEditTextControlListener;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;
import com.boot.accommodation.vp.location.LocationFragment;
import com.boot.accommodation.vp.tour.SearchActivity;
import com.boot.accommodation.vp.trip.TripFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Admin on 13/11/2015.
 */
public class HomeActivity extends HomeBaseActivity  {

    @Bind(R.id.iv_menu)
    ImageView ivMenu;
    @Bind(R.id.homePager)
    CustomViewPager homePager;
    @Bind(R.id.tl_home)
    TabLayout mTabLayout;
    @Bind(R.id.edSearch)
    JoCoEditText edSearch;
    @Bind(R.id.ivFilter)
    ImageView ivFilter;
    private String keyWord;//param for search
    private int typeSearch;//param for search
    HomeAdapter homeAdapter; // adapter
    private boolean isTourClick = true; // Variable check tour click
    private boolean isFirstLoadLocation = true; // Variable first load location
    private TextView tvLocation; // TextView location
    private AreaAdapter areaAdapter; // Area adapter to search

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableSlidingMenu(false);
        // customize the SlidingMenu
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initDataFilterDefault();
        initView();
//        reStartLocating();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Reset data
        edSearch.setText("");
    }

    @OnClick({R.id.iv_menu, R.id.edSearch, R.id.ivFilter})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_menu:
                getSlidingMenu().showMenu(true);
                break;
            case R.id.edSearch:
//                edSearch.setFocusableInTouchMode(true);
//                edSearch.requestFocus();
//                search();
                break;
            case R.id.ivFilter:
                getDataFilter();
                break;
        }
    }

    /**
     * Khoi tao view
     */
    private void initView() {
        if (Utils.checkGooglePlayServices(this)) {
            Intent gcmService = new Intent(this, GCMService.class);
            startService(gcmService);
        }
        homeAdapter = new HomeAdapter(getSupportFragmentManager());
        Bundle bundle = getDataInput();
        homeAdapter.addFrag(LocationFragment.newInstance(bundle), StringUtil.getString(R.string.text_locations).toUpperCase());
        homeAdapter.addFrag(TripFragment.newInstance(bundle), StringUtil.getString(R.string.text_tours).toUpperCase());
//        homePager.setOffscreenPageLimit(homeAdapter.getFragmentList().size());
//        homePager.setSwipeable(true);
        homePager.setAdapter(homeAdapter);
        homePager.setCurrentItem(0); // set focus vao tour dau tien
        mTabLayout.setupWithViewPager(homePager);
        handleEventTab();
        edSearch.setImageSearchVisible(true);
        setEventSearch();
        getSlidingMenu().setOnOpenListener(new SlidingMenu.OnOpenListener() {
            @Override
            public void onOpen() {
                Utils.rotateViewOpen(ivMenu);
            }
        });
        getSlidingMenu().setOnCloseListener(new SlidingMenu.OnCloseListener() {
            @Override
            public void onClose() {
                Utils.rotateViewClose(ivMenu);
            }
        });
        Utils.hideKeyboardInput(this, edSearch);
    }

    @NonNull
    private Bundle getDataInput() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.IntentExtra.LOCATION_FILTER, locationFilterItemDTO);
//        bundle.putParcelableArrayList(Constants.IntentExtra.INTENT_DATA, places);
        return bundle;
    }

    /**
     * set handle search
     */
    private void setEventSearch() {
        //get event : enter from keyboard for search
        edSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "search" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    handleSearch();
                    return true;
                }
                return false;
            }
        });
        edSearch.setListener(new OnEditTextControlListener() {
            @Override
            public void onTouchControl() {
                if(locationFilterSearch == null) {
                    getFilterAreasSearch();
                }
//                else{
//                    edSearch.showDropDown();
//                }
            }

            @Override
            public void onClear() {
            }
        });
        edSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Utils.hideKeyboardInput(HomeActivity.this, edSearch);
//                locationFilterItemDTO.setFamousLocation(locationFilterSearch.get(position));
                handleSearch();
            }
        });
    }

    /**
     * Handle search
     */
    private void handleSearch() {
        //get param pass to SearchActivity
        keyWord = edSearch.getText().toString();
        typeSearch = homePager.getCurrentItem();
        if (!keyWord.equals("")) {
            Bundle params = new Bundle();
            params.putString(Constants.IntentExtra.KEYWORD, keyWord);
            params.putInt(Constants.IntentExtra.TYPE_SEARCH, typeSearch);
            if (isChooseFilter) {
                params.putSerializable(Constants.IntentExtra.LOCATION_FILTER, locationFilterItemDTO);
            }else {
                params.putSerializable(Constants.IntentExtra.LOCATION_FILTER, new LocationFilterItemDTO());
            }
            goNextScreen(SearchActivity.class, params);
            Utils.hideKeyboardInput(HomeActivity.this, edSearch);
        }
    }

    /**
     * Handle tab
     */
    private void handleEventTab() {
        ViewGroup vg = (ViewGroup) mTabLayout.getChildAt(0);
        ViewGroup vgTab = (ViewGroup) vg.getChildAt(0);
        View tabViewChild = vgTab.getChildAt(1);
        if (tabViewChild instanceof TextView) {
            tvLocation = ((TextView) tabViewChild);
            tvLocation.setCompoundDrawablesWithIntrinsicBounds(R.drawable
                    .ic_list_green, 0, 0, 0);
            tvLocation.setCompoundDrawablePadding(Utils.getDimension(R.dimen.padding));
        }
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                homePager.setCurrentItem(tab.getPosition());
                handleTabSelected(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                handleTabSelected(tab);
            }
        });

        homePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                enableFilter(ivFilter, position);
            }

            @Override
            public void onPageSelected(int position) {
//                enableFilter(ivFilter, position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * Handle tab selected
     */
    void handleTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == 0) {
            if (!isTourClick) {
                if (tvLocation != null) {
                    if (isFirstLoadLocation) {
                        tvLocation.setCompoundDrawablesWithIntrinsicBounds(R.drawable
                                .ic_map_green, 0, 0, 0);
                    } else {
                        tvLocation.setCompoundDrawablesWithIntrinsicBounds(R.drawable
                                .ic_list_green, 0, 0, 0);
                    }
                    tvLocation.setCompoundDrawablePadding(Utils.getDimension(R.dimen.padding));
                    isFirstLoadLocation = !isFirstLoadLocation;
                    sendBroadcast(LocationFragment.ACTION_SWITCH_MAP_PLACE, new Bundle());
                }
            } else {
                isTourClick = false;
            }
        } else {
            isTourClick = true;
        }
    }

    @Override
    public void receiveBroadcast(int action, Bundle bundle) {
        switch (action){
            case Constants.ActionEvent.ACTION_UPDATE_LOCATION_FILTER_SEARCH:
                renderLocationFilterSearch();
                break;
            default:
                super.receiveBroadcast(action, bundle);
        }
    }

    /**
     * Render location filter search
     */
    private void renderLocationFilterSearch(){
        if(areaAdapter == null){
            areaAdapter = new AreaAdapter(this, android.R.layout.simple_spinner_item, locationFilterSearch);
            edSearch.setAdapter(areaAdapter);
        }
//        edSearch.showDropDown();
    }


}
