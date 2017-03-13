package com.boot.accommodation.vp.home;

import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.response.FamousLocationResponseDTO;
import com.boot.accommodation.dto.response.LocationFilterResponseDTO;
import com.boot.accommodation.dto.view.AreaDTO;
import com.boot.accommodation.dto.view.CategoryItemDTO;
import com.boot.accommodation.dto.view.LocationFilterViewDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.HomeModel;
import com.boot.accommodation.model.mgr.HomeModelMgr;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * HomePresenter
 *
 * @author tuanlt
 * @since 6:03 PM 9/20/16
 */
public class HomePresenter implements HomePresenterMgr {

    private LocationFilterViewDTO locationFilterViewDTO = new LocationFilterViewDTO();
    private HomeViewMgr homeViewMgr; //Home view
    private HomeModelMgr homeModelMgr; //Home model
    private String TAG = "";

    public HomePresenter(HomeViewMgr homeViewMgr) {
        this.homeViewMgr = homeViewMgr;
        homeModelMgr = new HomeModel();
        TAG = Utils.getTag(homeViewMgr.getClass());
    }

    @Override
    public void getInfoFilterLocation() {
        homeModelMgr.getInfoFilterPlace(new ModelCallBack<LocationFilterResponseDTO>(TAG) {
            @Override
            public void onSuccess(LocationFilterResponseDTO response) {
                getLocationFilterSuccess(response);
                homeViewMgr.openPopUpCategory(locationFilterViewDTO);
//                homeViewMgr.renderFamousPlace(locationFilterViewDTO.getFamousLocation());
            }

            @Override
            public void onError(int errorCode, String error) {
                homeViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public List<CategoryItemDTO> getCategoryChoose() {
        List<CategoryItemDTO> categories = new ArrayList<>();
        for (int i = 0; i < locationFilterViewDTO.getCategory().size(); i++) {
            if (locationFilterViewDTO.getCategory().get(i).getSelected()) {
                categories.add(locationFilterViewDTO.getCategory().get(i));
            }
        }
        return categories;
    }

    @Override
    public boolean validateDataFilter(String famousPlace) {
        if (!StringUtil.isNullOrEmpty(famousPlace) && getDataExisted(locationFilterViewDTO.getFamousLocation(), famousPlace)
                == null) {
            homeViewMgr.showFamousPlaceError();
            return false;
        }
        return true;
    }

    @Override
    public AreaDTO getFamousPlaceChoose(String famousPlace) {
        AreaDTO dto = getDataExisted(locationFilterViewDTO.getFamousLocation(), famousPlace);
        return dto;
    }

    @Override
    public String getCategoryIdChoose(List<CategoryItemDTO> categories) {
        StringBuilder category = new StringBuilder();
        for (int i = 0; i < locationFilterViewDTO.getCategory().size(); i++) {
            if (locationFilterViewDTO.getCategory().get(i).getSelected()) {
                if (StringUtil.isNullOrEmpty(category.toString())) {
                    category.append(locationFilterViewDTO.getCategory().get(i).getId());
                } else {
                    category.append(Constants.STR_TOKEN + locationFilterViewDTO.getCategory().get(i).getId());
                }
            }
        }
        return category.toString();
    }

    /**
     * Get location filter success
     */
    private void getLocationFilterSuccess(LocationFilterResponseDTO response){
        locationFilterViewDTO.getCategory().clear();
        locationFilterViewDTO.getCategory().addAll(response.getData().getCategory());
//        locationFilterViewDTO.getProvince().clear();
//        locationFilterViewDTO.getProvince().addAll(response.getData().getProvince());
        locationFilterViewDTO.getFamousLocation().clear();
        locationFilterViewDTO.getFamousLocation().addAll(response.getData().getFamousLocation());
    }

    /**
     * Check data existed
     * @param areas
     * @param data
     */
    private AreaDTO getDataExisted(List<AreaDTO> areas, String data) {
        for (AreaDTO areaDTO : areas) {
            String temp1 = StringUtil.getEngStringFromUnicodeString(areaDTO.getFullLongName().toLowerCase()).trim();
            String temp2 = StringUtil.getEngStringFromUnicodeString(data.toLowerCase()).trim();
            if (!StringUtil.isNullOrEmpty(data) && temp1.equals(temp2)) {
                return areaDTO;
            }
        }
        return new AreaDTO();
    }

    @Override
    public void getFilterAreas() {
        homeModelMgr.getFilterAreas(new ModelCallBack<FamousLocationResponseDTO>() {
            @Override
            public void onSuccess(FamousLocationResponseDTO response) {
                homeViewMgr.renderFilterAreas(response.getData());
                locationFilterViewDTO.setFamousLocation(response.getData());
            }

            @Override
            public void onError(int errorCode, String error) {
                homeViewMgr.showMessageErr(errorCode, error);
            }

        });
    }



}
