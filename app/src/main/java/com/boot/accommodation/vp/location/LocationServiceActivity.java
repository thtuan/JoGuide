package com.boot.accommodation.vp.location;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.common.control.JoCoEditText;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.AreaDTO;
import com.boot.accommodation.dto.view.CategoryItemDTO;
import com.boot.accommodation.dto.view.LocationFilterItemDTO;
import com.boot.accommodation.dto.view.SortTypeDTO;
import com.boot.accommodation.listener.OnEditTextControlListener;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;
import com.boot.accommodation.vp.category.PlaceServicePresenter;
import com.boot.accommodation.vp.category.PlaceServiceViewMgr;
import com.boot.accommodation.vp.home.AreaAdapter;
import com.boot.accommodation.vp.home.ListCategoryFilterAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Time to go places
 *
 * @author tuanlt
 * @since 10:39 AM 9/22/2016
 */
public class LocationServiceActivity extends BaseActivity implements PlaceServiceViewMgr {

    @Bind(R.id.llFilter)
    LinearLayout llFilter;
    @Bind(R.id.etSearch)
    JoCoEditText etSearch;
    @Bind(R.id.rdDistance)
    RadioButton rdDistance;
    @Bind(R.id.rdPrice)
    RadioButton rdPrice;
    @Bind(R.id.clMain)
    CoordinatorLayout clMain;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.ivFilter)
    ImageView ivFilter;
    @Bind(R.id.fabMap)
    FloatingActionButton fabMap;
    private AlertDialog alertDialog; //Alert dialog
    private ListCategoryFilterAdapter categoryAdapter; //Category adapter
    private final int ACTION_CHOOSE_CATEGORY_OK = 1000; //Action choose category ok
    private final int ACTION_CHOOSE_CATEGORY_CANCEL = 1001; //Action choose category cancel
    private final int ACTION_SHOW_CHOOSE_CATEGORY = 1002; //Action choose show category
    private PlaceServicePresenter placeServicePresenter; //Place list presenter
    private LocationListFragment placeListFragment; //Place list fragment
    private LocationFilterItemDTO locationFilterItemDTO; //Location filter
    private List<AreaDTO> areaDTOs;// List area filter
    private AreaAdapter areaAdapter; // Area adapter to search
    private boolean isChooseList = true; //Variable check choose list or map
    Bundle bundleAll = new Bundle();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_service);
        ButterKnife.bind(this);
        enableSlidingMenu(false);
        initView();
        initData();
    }

    /**
     * Init view
     */
    private void initView() {
        bundleAll = getIntent().getExtras();
        locationFilterItemDTO = (LocationFilterItemDTO) bundleAll.getSerializable(Constants.IntentExtra.LOCATION_FILTER);
        areaDTOs = bundleAll.getParcelableArrayList(Constants.IntentExtra.LIST_AREA);
        if (Utils.validateMyLocation(clMain)) {
            rdDistance.setChecked(true);
            locationFilterItemDTO.setSortType(SortTypeDTO.DISTANCE.getValue());
            bundleAll.putDouble(Constants.IntentExtra.LAT, JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLatitude());
            bundleAll.putDouble(Constants.IntentExtra.LNG, JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLongtitude());
        } else {
            rdPrice.setChecked(true);
            locationFilterItemDTO.setSortType(SortTypeDTO.PRICE.getValue());
            bundleAll.putDouble(Constants.IntentExtra.LAT, Constants.LAT_LNG_NULL);
            bundleAll.putDouble(Constants.IntentExtra.LNG, Constants.LAT_LNG_NULL);
        }
        switchFragment(placeListFragment = LocationListFragment.newInstance(bundleAll), R.id.frDetail, true);
        etSearch.setImageSearchVisible(true);
        etSearch.setImageClearVisibile(R.drawable.ic_clear_grey, true);
        etSearch.setText(locationFilterItemDTO.getFamousLocation() != null ? locationFilterItemDTO.getFamousLocation()
                .getFullLongName() : "");
        tvTitle.setText(StringUtil.getString(R.string.text_accommodation) + Constants.STR_SPACE + etSearch.getText());
        //get event : enter from keyboard for search
        etSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    handleSearch();
                    return true;
                }
                return false;
            }
        });
        etSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleSearch();
            }
        });
        etSearch.setListener(new OnEditTextControlListener() {
            @Override
            public void onTouchControl() {
                if (areaAdapter == null) {
                    areaAdapter = new AreaAdapter(LocationServiceActivity.this, android.R.layout.simple_spinner_item,
                            areaDTOs);
                    etSearch.setAdapter(areaAdapter);
                }
                etSearch.showDropDown();
            }

            @Override
            public void onClear() {

            }
        });
    }

    /**
     * Handle search
     */
    private void handleSearch() {
        String keyWord = etSearch.getText().toString();
        locationFilterItemDTO.setFamousLocation(Utils.getAreaExisted(this.areaDTOs, keyWord));
        if (rdDistance.isChecked()) {
            placeListFragment.getLocationForRest(JoCoApplication.getInstance()
                    .getProfile().getMyGPSInfo().getLatitude(), JoCoApplication.getInstance().getProfile().getMyGPSInfo()
                    .getLongtitude());
        } else {
            placeListFragment.getLocationForRest(Constants.LAT_LNG_NULL, Constants.LAT_LNG_NULL);
        }
        Utils.hideKeyboardInput(this, etSearch);
        tvTitle.setText(StringUtil.getString(R.string.text_accommodation) + Constants.STR_SPACE + keyWord);
    }


    /**
     * Init data
     */
    private void initData() {
        placeServicePresenter = new PlaceServicePresenter(this);
    }

    @OnClick({R.id.ivBack, R.id.ivFilter, R.id.rdDistance, R.id.rdPrice, R.id.fabMap})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                Utils.hideKeyboardInput(this, etSearch);
                break;
            case R.id.ivFilter:
                if (categoryAdapter == null) {
                    showProgressDialog(true);
                    placeServicePresenter.getCategoryChild();
                } else {
                    alertDialog.show();
                }
                break;
            case R.id.rdDistance:
                if (Utils.validateMyLocation(clMain)) {
                    locationFilterItemDTO.setSortType(SortTypeDTO.DISTANCE.getValue());
                    placeListFragment.getLocationForRest(JoCoApplication.getInstance().getProfile().getMyGPSInfo()
                            .getLatitude(), JoCoApplication.getInstance().getProfile().getMyGPSInfo()
                            .getLongtitude());
                } else {
                    rdDistance.setChecked(false);
                    placeListFragment.getLocationForRest(Constants.LAT_LNG_NULL, Constants.LAT_LNG_NULL);
                }
                break;
            case R.id.rdPrice:
                locationFilterItemDTO.setSortType(SortTypeDTO.PRICE.getValue());
                placeListFragment.getLocationForRest(Constants.LAT_LNG_NULL, Constants.LAT_LNG_NULL);
                break;
            case R.id.fabMap:
                if (isChooseList) {
                    fabMap.setImageResource(R.drawable.ic_list_white);
                    isChooseList = false;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.IntentExtra.INTENT_DATA, placeListFragment.getDataPlaces());
                    switchFragment(LocationMapFragment.newInstance(bundle), R.id.frDetail, true);
                } else {
                    fabMap.setImageResource(R.drawable.ic_map_white);
                    isChooseList = true;
                    switchFragment(placeListFragment = LocationListFragment.newInstance(bundleAll), R.id.frDetail, true);
                }
                hideSearch(isChooseList);
                break;
        }
    }

    /**
     * Hide search
     *
     * @param isHide
     */
    private void hideSearch(boolean isHide) {
        llFilter.setVisibility(isHide ? View.VISIBLE : View.GONE);
        ivFilter.setVisibility(isHide ? View.VISIBLE : View.GONE);
        etSearch.setVisibility(isHide ? View.VISIBLE : View.GONE);
        tvTitle.setVisibility(isHide ? View.GONE : View.VISIBLE);
    }


    /**
     * Show popup category
     */
    private void showPopupCategory(final List<CategoryItemDTO> categoryLocationItems) {
        if (alertDialog == null) {
            alertDialog = Utils.showDialog(this, this, categoryLocationItems, R.layout.popup_choose_location_category,
                    ACTION_CHOOSE_CATEGORY_OK, StringUtil.getString(R.string.text_ok), ACTION_CHOOSE_CATEGORY_CANCEL,
                    StringUtil.getString(R.string.text_cancel), ACTION_SHOW_CHOOSE_CATEGORY);
        }
        alertDialog.show();
    }

    /**
     * Init view
     */
    private void initView(View view, List<CategoryItemDTO> categoryLocationItems) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvListCategory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        //render list category
        if (categoryAdapter == null) {
            categoryAdapter = new ListCategoryFilterAdapter(categoryLocationItems);
            categoryAdapter.setListener(this);
            recyclerView.setAdapter(categoryAdapter);
        }
    }


    @Override
    public void renderLayout(List<CategoryItemDTO> categoryLocationItems) {
        closeProgressDialog();
        showPopupCategory(categoryLocationItems);
    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        stopRefresh();
        closeProgress();
        closeProgressDialog();
        switch (errorCode) {
            default:
                handleError(errorCode, error);
        }
    }

    @Override
    public void onEventControl(int action, Object item, View view, int position) {
        switch (action) {
            case ACTION_CHOOSE_CATEGORY_OK: {
                // Init view
                final List<CategoryItemDTO> categoryLocationItems = (List<CategoryItemDTO>) item;
                categoryAdapter.updateCategoryChose(true);
                List<CategoryItemDTO> categoryItemDTOs = new ArrayList<>();
                for (CategoryItemDTO dto : categoryLocationItems) {
                    if (dto.getSelected()) {
                        categoryItemDTOs.add(dto);
                    }
                }
                if (categoryItemDTOs.size() > 0) {
                    locationFilterItemDTO.getCategories().clear();
                    locationFilterItemDTO.getCategories().addAll(categoryItemDTOs);
                    locationFilterItemDTO.setCategoryIds(Utils.convertListToString(locationFilterItemDTO
                            .getCategories(), true));
                    handleSearch();
                    alertDialog.dismiss();
                } else {
                    showMessage(R.string.text_have_choose_rest);
                }
                break;
            }
            case ACTION_CHOOSE_CATEGORY_CANCEL:
                break;
            case ACTION_SHOW_CHOOSE_CATEGORY: {
                // Init view
                final List<CategoryItemDTO> categoryLocationItems = (List<CategoryItemDTO>) item;
                initView(view, categoryLocationItems);
                categoryAdapter.updateCategoryChose(false);
                break;
            }
        }
    }

}
