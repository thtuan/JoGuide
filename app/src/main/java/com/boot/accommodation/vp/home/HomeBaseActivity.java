package com.boot.accommodation.vp.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import com.innodroid.expandablerecycler.ExpandableRecyclerAdapter;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.common.control.JoCoEditText;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.AreaDTO;
import com.boot.accommodation.dto.view.CategoryItemDTO;
import com.boot.accommodation.dto.view.LocationCategoryTypeDTO;
import com.boot.accommodation.dto.view.LocationFilterItemDTO;
import com.boot.accommodation.dto.view.LocationFilterViewDTO;
import com.boot.accommodation.listener.OnEditTextControlListener;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

import java.util.List;

/**
 * Data similar for home activity and search activity
 *
 * @author tuanlt
 * @since 11:15 AM 9/22/16
 */
public class HomeBaseActivity extends BaseActivity implements HomeViewMgr {

    protected CategoryLocationFilterAdapter categoryAdapter; //Category adapter
    protected AreaAdapter famousPlaceAdapter; //Category adapter
    protected AlertDialog alertDialog; //Alert dialog
    protected HomePresenterMgr homePresenterMgr; //Home presenter
    JoCoEditText etToponym; //Famous place
    protected LocationFilterItemDTO locationFilterItemDTO; //Location filter
    RecyclerView rvListCategory;
    private LocationFilterViewDTO locationFilterViewDTO = new LocationFilterViewDTO(); //Location filter view
    protected List<AreaDTO> locationFilterSearch; //location filter of edittext search
    protected boolean isChooseFilter = false; //Variable check filter choose or no
    protected boolean isHaveFamousLocation = false; //Variable choose famous location or no

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initDataFilterDefault();
        homePresenterMgr = new HomePresenter(this);
    }

    @Override
    public void openPopUpCategory(final LocationFilterViewDTO locationFilterViewDTO) {
        closeProgressDialog();
        this.locationFilterViewDTO = locationFilterViewDTO;
//        this.locationFilterViewDTO.setFamousLocation(locationFilterSearch);
        getDataDefault();
        if (alertDialog == null) {
            LayoutInflater li = LayoutInflater.from(this);
            View promptsView = li.inflate(R.layout.popup_filter_location, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setView(promptsView);
            // Init view
            initView(promptsView);
            // set dialog message
            alertDialogBuilder.setPositiveButton(StringUtil.getString(R.string.text_ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            }).setNegativeButton(StringUtil.getString(R.string.text_cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                    categoryAdapter.updateChooseCategory(false);
                }
            });
            // create alert dialog
            alertDialog = alertDialogBuilder.create();
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            Window window = alertDialog.getWindow();
            lp.copyFrom(window.getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(lp);
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    categoryAdapter.updateChooseCategory(false);
                    //etToponym.setText(locationFilterItemDTO.getFamousLocation().getFullLongName());
                    Utils.hideKeyboardInput(HomeBaseActivity.this, etToponym);
                    Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Handel data
                            isChooseFilter = true;
                            handleDataFilterLocation();
                        }
                    });
                }

            });

        }
        categoryAdapter.notifyDataSetChanged();
        // show it
        alertDialog.show();
    }

    /**
     * Init view
     *
     * @param promptsView
     */
    private void initView(View promptsView) {
        rvListCategory = (RecyclerView) promptsView.findViewById(R.id.rvListCategory);
        rvListCategory.setLayoutManager(new LinearLayoutManager(this));
        rvListCategory.setHasFixedSize(true);
        //render list category
        if (categoryAdapter == null) {
            categoryAdapter = new CategoryLocationFilterAdapter(this, locationFilterViewDTO.getCategory());
            categoryAdapter.setListener(this);
            categoryAdapter.setMode(ExpandableRecyclerAdapter.MODE_NORMAL);
            categoryAdapter.expandAll();
            rvListCategory.setAdapter(categoryAdapter);
        }
        initViewFamousPlace(promptsView);
    }

    /**
     * Init view famous place
     *
     * @param promptsView
     */
    private void initViewFamousPlace(View promptsView) {
        etToponym = (JoCoEditText) promptsView.findViewById(R.id.etToponym);
        etToponym.setImageClearVisibile(R.drawable.ic_clear_grey, true);
        if(locationFilterItemDTO != null && locationFilterItemDTO.getFamousLocation() != null && isHaveFamousLocation) {
            etToponym.setText(locationFilterItemDTO.getFamousLocation().getFullLongName());
        } else {
            etToponym.setText("");
        }
        renderFamousPlace(locationFilterViewDTO.getFamousLocation());
        etToponym.setListener(new OnEditTextControlListener() {
            @Override
            public void onTouchControl() {
//                etToponym.showDropDown();
            }

            @Override
            public void onClear() {
            }
        });
        etToponym.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Utils.hideKeyboardInput(HomeBaseActivity.this, etToponym);
            }
        });
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
    public void renderFamousPlace(List<AreaDTO> famousPlaces) {
        if (famousPlaceAdapter == null) {
            famousPlaceAdapter = new AreaAdapter(this, android.R.layout.simple_spinner_item, famousPlaces);
        }
        etToponym.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Utils.hideSoftKeyboard(HomeBaseActivity.this);
            }
        });
        initDataFilter(etToponym, famousPlaceAdapter);
        famousPlaceAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProvinceError() {
        showMessage(R.string.text_province_error);
    }

    @Override
    public void showFamousPlaceError() {
        showMessage(R.string.text_famous_place_error);
    }

    @Override
    public void renderFilterAreas(List<AreaDTO> areaDTOs) {
        closeProgressDialog();
        if(locationFilterSearch == null){
            locationFilterSearch = areaDTOs;
        }
        locationFilterViewDTO.setFamousLocation(locationFilterSearch);
        sendBroadcast(Constants.ActionEvent.ACTION_UPDATE_LOCATION_FILTER_SEARCH, new Bundle());
    }

    /**
     * Init data
     */
    protected void initDataFilterDefault() {
        locationFilterViewDTO = new LocationFilterViewDTO();
        if (locationFilterItemDTO == null) {
            locationFilterItemDTO = new LocationFilterItemDTO();
            //Get data default
            CategoryItemDTO itemPark = new CategoryItemDTO();
            itemPark.setId(LocationCategoryTypeDTO.VISIT.getValue());
            itemPark.setName(LocationCategoryTypeDTO.getNameByValue(itemPark.getId()));
            locationFilterItemDTO.getCategories().add(itemPark);
//            locationFilterItemDTO.setCategoryIds("" + itemPark.getId());
            CategoryItemDTO itemAdvanture = new CategoryItemDTO();
            itemAdvanture.setId(LocationCategoryTypeDTO.ADVENTURE.getValue());
            itemAdvanture.setName(LocationCategoryTypeDTO.getNameByValue(itemAdvanture.getId()));
            locationFilterItemDTO.getCategories().add(itemAdvanture);
            locationFilterItemDTO.setCategoryIds(itemPark.getId() + Constants.STR_TOKEN + itemAdvanture.getId());
        }
    }

    /**
     * Init data edit text
     */
    private void initDataFilter(final JoCoEditText editText, final AreaAdapter adapter) {
        editText.setAdapter(adapter);
        editText.setImageClearVisibile(R.drawable.ic_clear_grey, true);
    }

    /**
     * Get data filter
     */
    protected void getDataFilter() {
        showProgressDialog(true);
        if (alertDialog == null) {
            homePresenterMgr.getInfoFilterLocation();
        } else {
            openPopUpCategory(locationFilterViewDTO);
        }
    }

    /**
     * Get data filter
     */
    private void getDataDefault() {
        for (CategoryItemDTO item : locationFilterViewDTO.getCategory()) {
            for (CategoryItemDTO categoryLocationItem : locationFilterItemDTO.getCategories()) {
                if (categoryLocationItem.getId() == item.getId()) {
                    item.setSelected(true);
                    break;
                }
            }
        }
    }

    /**
     * Enable filter
     */
    protected void enableFilter(ImageView ivFilter, int position) {
        if(position == 0) {
            ivFilter.setVisibility(View.VISIBLE);
        }else{
            ivFilter.setVisibility(View.GONE);
        }
    }

    /**
     * Handle data filter location
     */
    private void handleDataFilterLocation() {
        if (homePresenterMgr.validateDataFilter(etToponym.getText().toString())) {
            locationFilterItemDTO.setCategories(homePresenterMgr.getCategoryChoose());
            locationFilterItemDTO.setCategoryIds(homePresenterMgr.getCategoryIdChoose
                    (locationFilterItemDTO.getCategories()));
            AreaDTO areaDTO = homePresenterMgr.getFamousPlaceChoose(etToponym.getText().toString());
            locationFilterItemDTO.setFamousLocation(areaDTO);
            if (areaDTO.getId() > 0) {
                isHaveFamousLocation = true;
            } else {
                isHaveFamousLocation = false;
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.IntentExtra.LOCATION_FILTER, locationFilterItemDTO);
            sendBroadcast(Constants.ActionEvent.ACTION_UPDATE_LOCATION, bundle);
            categoryAdapter.updateChooseCategory(true);
            alertDialog.dismiss();
        }
    }

    /**
     * Get filter Area to search
     */
    protected void getFilterAreasSearch(){
        showProgressDialog(true);
        if (locationFilterSearch == null) {
            homePresenterMgr.getFilterAreas();
        } else {
            renderFilterAreas(locationFilterSearch);
        }
    }

}
