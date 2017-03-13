package com.boot.accommodation.vp.category;

import com.boot.accommodation.dto.view.CategoryItemDTO;
import com.boot.accommodation.dto.view.LocationCategoryTypeDTO;
import com.boot.accommodation.model.impl.HomeModel;
import com.boot.accommodation.model.mgr.HomeModelMgr;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * PlaceServicePresenter
 *
 * @author tuanlt
 * @since 3:40 PM 10/14/16
 */
public class PlaceServicePresenter implements PlaceServicePresenterMgr {
    private String TAG = ""; // Tag
    private HomeModelMgr placeServiceModelMgr; // model mgr
    private PlaceServiceViewMgr placeServiceViewMgr; // Place service
    private List<CategoryItemDTO> categoryLocationItems = new ArrayList<>();

    public PlaceServicePresenter(PlaceServiceViewMgr placeServiceViewMgr){
        TAG = Utils.getTag(placeServiceViewMgr.getClass());
        this.placeServiceViewMgr = placeServiceViewMgr;
        placeServiceModelMgr = new HomeModel();
    }


    @Override
    public void getCategoryChild() {
        CategoryItemDTO hotel = new CategoryItemDTO();
        hotel.setId(LocationCategoryTypeDTO.HOTEL.getValue());
        hotel.setName(Character.toString(LocationCategoryTypeDTO.HOTEL.name().charAt(0)).toUpperCase()
                + LocationCategoryTypeDTO.HOTEL.name().substring(1).toLowerCase());
        CategoryItemDTO hostel = new CategoryItemDTO();
        hostel.setId(LocationCategoryTypeDTO.HOSTEL.getValue());
        hostel.setName(Character.toString(LocationCategoryTypeDTO.HOSTEL.name().charAt(0)).toUpperCase()
                + LocationCategoryTypeDTO.HOSTEL.name().substring(1).toLowerCase());
        CategoryItemDTO homeStay = new CategoryItemDTO();
        homeStay.setId(LocationCategoryTypeDTO.HOMESTAY.getValue());
        homeStay.setName(Character.toString(LocationCategoryTypeDTO.HOMESTAY.name().charAt(0)).toUpperCase()
                + LocationCategoryTypeDTO.HOMESTAY.name().substring(1).toLowerCase());
        homeStay.setSelected(true);
        categoryLocationItems.add(hotel);
        categoryLocationItems.add(hostel);
        categoryLocationItems.add(homeStay);
        placeServiceViewMgr.renderLayout(categoryLocationItems);
//        placeServiceModelMgr.getCategoryChild(parentCategoryId, new ModelCallBack<CategoryResponseDTO>(TAG) {
//            @Override
//            public void onSuccess(CategoryResponseDTO response) {
//
//            }
//
//            @Override
//            public void onError(int errorCode, String error) {
//
//            }
//
//            @Override
//            public void onFinishAllProcess() {
//
//            }
//        });
    }
}
