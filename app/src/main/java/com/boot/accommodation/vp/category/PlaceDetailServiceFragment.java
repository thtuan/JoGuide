package com.boot.accommodation.vp.category;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.CategoryItemDTO;
import com.boot.accommodation.dto.view.LocationCategoryTypeDTO;
import com.boot.accommodation.dto.view.LocationFilterItemDTO;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;
import com.boot.accommodation.vp.home.ListCategoryFilterAdapter;
import com.boot.accommodation.vp.location.LocationServiceActivity;
import com.boot.accommodation.vp.location.VehicleActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Service fragment
 *
 * @author tuanlt
 * @since 10:39 AM 9/22/2016
 */
public class PlaceDetailServiceFragment extends BaseFragment implements PlaceServiceViewMgr {

    @Bind(R.id.llEat)
    LinearLayout llEat;
    @Bind(R.id.llRest)
    LinearLayout llRest;
    @Bind(R.id.llVehicle)
    LinearLayout llVehicle;
    private PlaceItemDTO placeItemDTO; // place item info
    private AlertDialog alertDialog; //Alert dialog
    private ListCategoryFilterAdapter categoryAdapter; //Category adapter
    private PlaceServicePresenter placeServicePresenter; //Place list presenter

    public static PlaceDetailServiceFragment newInstance(Bundle bundle) {
        PlaceDetailServiceFragment z = new PlaceDetailServiceFragment();
        z.setArguments(bundle);
        return z;
    }

    /**
     * set view layout
     *
     * @return
     */
    @Override
    public int contentViewLayout() {
        return R.layout.fragment_service;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle b = getArguments();
        if (b != null) {
            placeItemDTO = b.getParcelable(Constants.IntentExtra.PLACE_ITEM);
        }
        enableRefresh(false);
        initData();
    }

    /**
     * Init data
     */
    private void initData(){
        placeServicePresenter = new PlaceServicePresenter(this);
    }

    @OnClick({R.id.llRest, R.id.llEat, R.id.llVehicle, R.id.llEntertainment})
    public void onClick(View view) {
        LocationFilterItemDTO locationFilterItemDTO = new LocationFilterItemDTO();
        CategoryItemDTO category = new CategoryItemDTO();
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.llVehicle:
                if (StringUtil.isNullOrEmpty(placeItemDTO.getVehicle())) {
                    mActivity.showMessage(R.string.text_no_data);
                } else {
                    bundle.putString(Constants.IntentExtra.INTENT_DATA, placeItemDTO.getVehicle());
                    mActivity.goNextScreen(VehicleActivity.class, bundle);
                }
                break;
            case R.id.llRest:
//                category.setId(LocationCategoryTypeDTO.HOTEL.getValue());
//                bundle.putString(Constants.IntentExtra.TITLE, StringUtil.getString(R.string.text_rest));
//                gotoService(locationFilterItemDTO, category, bundle);
                getCategoryChild();
                break;
            case R.id.llEat:
                category.setId(LocationCategoryTypeDTO.RESTAURANT.getValue());
                bundle.putString(Constants.IntentExtra.TITLE, StringUtil.getString(R.string.text_eat));
                gotoService(locationFilterItemDTO, category, bundle);
                break;
            case R.id.llEntertainment:
                category.setId(LocationCategoryTypeDTO.RELAX.getValue());
                bundle.putString(Constants.IntentExtra.TITLE, StringUtil.getString(R.string.text_entertainment));
                gotoService(locationFilterItemDTO, category, bundle);
                break;
            default:
                break;

        }
    }

    /**
     * go to services
     * @param locationFilterItemDTO
     * @param category
     * @param bundle
     */
    private void gotoService(LocationFilterItemDTO locationFilterItemDTO, CategoryItemDTO category, Bundle bundle) {
        category.setName(LocationCategoryTypeDTO.getNameByValue(category.getId()));
        locationFilterItemDTO.getCategories().add(category);
        locationFilterItemDTO.setCategoryIds("" + category.getId());
//        AreaDTO areaDTO = new AreaDTO();
//        areaDTO.setId(placeItemDTO.getFamousLocationId());
//        locationFilterItemDTO.setFamousLocation(areaDTO);
        bundle.putSerializable(Constants.IntentExtra.LOCATION_FILTER, locationFilterItemDTO);
        bundle.putDouble(Constants.IntentExtra.LAT, placeItemDTO.getLat());
        bundle.putDouble(Constants.IntentExtra.LNG, placeItemDTO.getLng());
        mActivity.goNextScreen(LocationServiceActivity.class, bundle);
    }

    /**
     * Show popup category
     */
    private void showPopupCategory(final List<CategoryItemDTO> categoryLocationItems) {
        if (alertDialog == null) {
            LayoutInflater li = LayoutInflater.from(mActivity);
            View promptsView = li.inflate(R.layout.popup_choose_location_category, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
            alertDialogBuilder.setView(promptsView);
            // Init view
            initView(promptsView, categoryLocationItems);
            // set dialog message
            alertDialogBuilder.setPositiveButton(StringUtil.getString(R.string.text_ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            }).setNegativeButton(StringUtil.getString(R.string.text_cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                    categoryAdapter.updateCategoryChose(false);
                }
            });
            // create alert dialog
            alertDialog = alertDialogBuilder.create();
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    categoryAdapter.updateCategoryChose(false);
                    Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            categoryAdapter.updateCategoryChose(true);
                            LocationFilterItemDTO locationFilterItemDTO = new LocationFilterItemDTO();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.IntentExtra.TITLE, StringUtil.getString(R.string.text_rest));
                            for (CategoryItemDTO item : categoryLocationItems) {
                                if(item.getSelected()){
                                    locationFilterItemDTO.getCategories().add(item);
                                }
                            }
                            if (locationFilterItemDTO.getCategories().size() > 0) {
                                locationFilterItemDTO.setCategoryIds(Utils.convertListToString(locationFilterItemDTO
                                        .getCategories(), true));
                                bundle.putSerializable(Constants.IntentExtra.LOCATION_FILTER, locationFilterItemDTO);
                                bundle.putDouble(Constants.IntentExtra.LAT, placeItemDTO.getLat());
                                bundle.putDouble(Constants.IntentExtra.LNG, placeItemDTO.getLng());
                                mActivity.goNextScreen(LocationServiceActivity.class, bundle);
                                alertDialog.dismiss();
                            } else {
                                mActivity.showMessage(R.string.text_have_choose_rest);
                            }
                        }
                    });
                }

            });

        }
        categoryAdapter.notifyDataSetChanged();
        alertDialog.show();
    }

    /**
     * Init view
     * @param promptsView
     */
    private void initView(View promptsView, List<CategoryItemDTO> categoryLocationItems) {
        RecyclerView recyclerView = (RecyclerView) promptsView.findViewById(R.id.rvListCategory);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
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
        showPopupCategory(categoryLocationItems);
    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        stopRefresh();
        closeProgress();
        mActivity.closeProgressDialog();
        switch (errorCode){
            default:
                handleError(errorCode,error);
        }
    }

    /**
     * Get category child
     */
    private void getCategoryChild() {
        placeServicePresenter.getCategoryChild();
    }

    @Override
    protected void receiveBroadcast(int action, Bundle extras) {
        switch (action) {
            case Constants.ActionEvent.ACTION_GET_LOCATION_INFO_SUCCESS:
                if (extras.containsKey(Constants.IntentExtra.PLACE_ITEM)) {
                    this.placeItemDTO = extras.getParcelable(Constants.IntentExtra.PLACE_ITEM);
                }
                break;
        }
    }
}
