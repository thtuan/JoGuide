package com.boot.accommodation.vp.location;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.response.SearchLocationResponseDTO;
import com.boot.accommodation.dto.view.LocationFilterItemDTO;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.dto.view.SearchTypeDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.PlacePickerModel;
import com.boot.accommodation.model.impl.TourModel;
import com.boot.accommodation.model.mgr.PlacePickerModelMgr;
import com.boot.accommodation.model.mgr.TourModelMgr;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter  cho man hinh tour info expand
 *
 * @author vuong-bv
 * @since: 9:25 AM 5/31/2016
 */
public class LocationSearchPresenter implements LocationSearchPresenterMgr {

    private LocationSearchViewMgr placeSearchViewMgr; // place Search
    private TourModelMgr tourModelMgr; // model mgr
    private int pageFav = 0; // page favourite
    private int pageNearBy = 0; // page gan nhat
    private int pageSizeFav = 0; //  size fav
    private int pageSizeNearBy = 0; // size near by
    PlacePickerModelMgr placePickerModelMgr; // Place picker
    private String TAG = ""; // Tag
    private List<PlaceItemDTO> favourite = new ArrayList<>(); // Data search favourite
    private List<PlaceItemDTO> nearBy = new ArrayList<>(); // Data search nearby

    public LocationSearchPresenter(LocationSearchViewMgr placeSearchViewMgr) {
        this.placeSearchViewMgr = placeSearchViewMgr;
        tourModelMgr = new TourModel();
        placePickerModelMgr = new PlacePickerModel();
        TAG = Utils.getTag(placeSearchViewMgr.getClass());
    }

    @Override
    public void getSearchLocation(String keyWord, Double lat, Double lng, LocationFilterItemDTO locationFilterItemDTO) {
        pageFav = 0;
        pageNearBy = 0;
        pageSizeFav = 0;
        pageSizeNearBy = 0;
//        favourite.clear();
//        nearBy.clear();
        handleGetPlaceByCategory(keyWord, lat, lng, locationFilterItemDTO, SearchTypeDTO.ALL.getValue());
    }

    /**
     * Handle get place from joco
     * @param keyWord
     * @param lat
     * @param lng
     * @param locationFilterItemDTO
     */
    private void handleGetNearByPlaces(String keyWord, Double lat, Double lng, LocationFilterItemDTO
            locationFilterItemDTO, final boolean isLoadMore){
//        locationFilterItemDTO = Utils.initCategoryDefault(locationFilterItemDTO);
        // type = 0: tuc la lay du lieu search lan dau
        if (Constants.LAT_LNG_NULL != lat && Constants.LAT_LNG_NULL != lng) {
            tourModelMgr.getSearchLocation(SearchTypeDTO.NEARBY.getValue(), pageNearBy, keyWord, lat, lng, locationFilterItemDTO, new
                    ModelCallBack<SearchLocationResponseDTO>(TAG) {
                        @Override
                        public void onSuccess(SearchLocationResponseDTO response) {
                            pageNearBy = response.getData().getNearBy().getPaging().getPage();
                            pageSizeNearBy = response.getData().getNearBy().getPaging().getPageSize();
                            if (isLoadMore) {
                                nearBy.addAll(response.getData().getNearBy()
                                        .getSearchTypeList());
                            }else {
                                nearBy = response.getData().getNearBy().getSearchTypeList();
                            }
//                            getPlacesSuccess(nearBy, response.getData().getNearBy().getSearchTypeList());
//                            Utils.sortListWithDistance(nearBy);
                            placeSearchViewMgr.renderLayout(favourite, nearBy);
                        }

                        @Override
                        public void onError(int errorCode, String error) {
                            placeSearchViewMgr.showMessageErr(errorCode, error);
                        }

                    });
        }
    }

    /**
     * Get favourite places
     * @param keyWord
     * @param lat
     * @param lng
     * @param locationFilterItemDTO
     */
    private void handleGetFavouritePlaces(String keyWord, Double lat, Double lng, LocationFilterItemDTO
            locationFilterItemDTO, final boolean isLoadMore) {
//        locationFilterItemDTO = Utils.initCategoryDefault(locationFilterItemDTO);
        tourModelMgr.getSearchLocation(SearchTypeDTO.FAVOURITE.getValue(), pageFav, keyWord, lat, lng, locationFilterItemDTO, new
                ModelCallBack<SearchLocationResponseDTO>((TAG)) {
                    @Override
                    public void onSuccess(SearchLocationResponseDTO response) {
                        pageFav = response.getData().getFavourite().getPaging().getPage();
                        pageSizeFav = response.getData().getFavourite().getPaging().getPageSize();
                        if (isLoadMore) {
                            favourite.addAll(response.getData().getFavourite().getSearchTypeList());
                        }else {
                            favourite = response.getData().getFavourite().getSearchTypeList();
                        }
//                        getPlacesSuccess(favourite, response.getData().getFavourite().getSearchTypeList());
////                        Utils.sortListWithDistance(favourite);
                        placeSearchViewMgr.renderLayout(favourite, nearBy);
                    }

                    @Override
                    public void onError(int errorCode, String error) {
                        placeSearchViewMgr.showMessageErr(errorCode, error);
                    }

                });
    }
//
//    /**
//     * Get location success
//     * @param response
//     */
//    private void getLocationSuccess(SearchLocationResponseDTO response) {
//        SearchLocationViewDTO searchLocationViewDTO = response.getData();
//        if (searchLocationViewDTO != null) {
//            if (searchLocationViewDTO.getFavourite() != null && searchLocationViewDTO.getFavourite()
//                    .getSearchTypeList() != null) {
//                favourite.addAll(searchLocationViewDTO.getFavourite().getSearchTypeList());
//            }
//            if (searchLocationViewDTO.getNearBy() != null && searchLocationViewDTO.getNearBy()
//                    .getSearchTypeList() != null) {
//                nearBy.addAll(searchLocationViewDTO.getNearBy().getSearchTypeList());
//            }
//        }
//        pageFav = response.getData().getFavourite().getPaging().getPage();
//        pageSizeFav = response.getData().getFavourite().getPaging().getPageSize();
//        pageNearBy = response.getData().getNearBy().getPaging().getPage();
//        pageSizeNearBy = response.getData().getNearBy().getPaging().getPageSize();
//        Utils.sortListWithDistance(favourite);
//        Utils.sortListWithDistance(nearBy);
//        placeSearchViewMgr.renderLayoutMonth(favourite, nearBy);
//    }

    @Override
    public void getMoreFavouriteLocation(BaseRecyclerViewAdapter adapter, String keyWord, Double lat, Double lng,
                                         LocationFilterItemDTO locationFilterItemDTO) {
        if (Utils.checkLoadMore(adapter, pageSizeFav, favourite.size())) {
            pageFav++;
            handleGetFavouritePlaces(keyWord, lat, lng, locationFilterItemDTO, true);
        }
    }

    @Override
    public void getMoreNearByLocation(BaseRecyclerViewAdapter adapter, String keyWord, Double lat, Double lng,
                                      LocationFilterItemDTO locationFilterItemDTO) {
        if (Utils.checkLoadMore(adapter, pageSizeNearBy, nearBy.size())) {
            pageNearBy++;
            handleGetNearByPlaces(keyWord, lat, lng, locationFilterItemDTO, true);
        }
    }

    /**
     * Get place by category
     */
    private void handleGetPlaceByCategory(String keyWord, Double lat, Double lng, LocationFilterItemDTO
            locationFilterItemDTO, int typeSearch) {
//        long categoryId = 0;
//        //Defaul choose one
//        if (locationFilterItemDTO.getCategories() != null && locationFilterItemDTO.getCategories().size() > 0) {
//            categoryId = locationFilterItemDTO.getCategories().get(0).getId();
//        }
        handleGetFavouritePlaces(keyWord, lat, lng, locationFilterItemDTO, false);
        handleGetNearByPlaces(keyWord, lat, lng, locationFilterItemDTO, false);
    }

}
