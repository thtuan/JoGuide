package com.boot.accommodation.vp.location;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.response.AppVersionResponseDTO;
import com.boot.accommodation.dto.response.CommonResponseDTO;
import com.boot.accommodation.dto.response.PlaceResponseDTO;
import com.boot.accommodation.dto.view.LocationFilterItemDTO;
import com.boot.accommodation.dto.view.MobileTypeDTO;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.dto.view.TimeToGoFilterDTO;
import com.boot.accommodation.dto.view.SortTypeDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.HomeModel;
import com.boot.accommodation.model.impl.PlacePickerModel;
import com.boot.accommodation.model.mgr.HomeModelMgr;
import com.boot.accommodation.model.mgr.PlacePickerModelMgr;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * presenter for get list location
 *
 * @author tuanlt
 * @since 10:39 AM 5/25/2016
 */
public class LocationListPresenter implements LocationListPresenterMgr {

    LocationListViewMgr placeListViewMgr; // view cua ds dia diem
    private ArrayList<PlaceItemDTO> lstPlaces = new ArrayList<>(); //ds dia diem
    private int page = 0; // page hien tai
    private int pageSize = 0; // page size
    HomeModelMgr placeModelMgr; // model mgr
    private String TAG = ""; // Tag
    PlacePickerModelMgr placePickerModelMgr; // Place picker
    private String userId; //User id
    private TimeToGoFilterDTO timeToGoFilterDTO; //Time to go filter

    public LocationListPresenter(LocationListViewMgr placeListViewMgr) {
        this.placeListViewMgr = placeListViewMgr;
        placeModelMgr = new HomeModel();
        placePickerModelMgr = new PlacePickerModel();
        TAG = Utils.getTag(placeListViewMgr.getClass());
    }

    @Override
    public void getPlaces(Double lat, Double lng, int typePlace, LocationFilterItemDTO locationFilterItemDTO, TimeToGoFilterDTO
                          timeToGoFilterDTO, String userId) {
        page = 0;
        this.lstPlaces.clear();
        this.userId = userId;
        this.timeToGoFilterDTO = timeToGoFilterDTO;
        if (LocationListFragment.TYPE_PLACE_FAVOURITE == typePlace) {
            getMyFavouritePlace();
        } else if (LocationListFragment.TYPE_PLACE_TIME_TO_GO == typePlace) {
            getTimeToGoPlaces();
        }else if(LocationListFragment.TYPE_PLACE_CREATED == typePlace){
            getPlaceCreated();
        } else {
            handleGetPlaceByCategory(lat, lng, locationFilterItemDTO);
        }
    }

    /**
     * Get more location
     *
     * @param lat
     * @param lng
     * @param adapter
     */
    @Override
    public void getMorePlaces(Double lat, Double lng, BaseRecyclerViewAdapter adapter, int typePlace,
                              LocationFilterItemDTO locationFilterItemDTO) {
        page++;
        if (LocationListFragment.TYPE_PLACE_FAVOURITE == typePlace || LocationListFragment.TYPE_PLACE_TIME_TO_GO ==
                typePlace || LocationListFragment.TYPE_PLACE_CREATED == typePlace ) {
            if (Utils.checkLoadMore(adapter, pageSize, lstPlaces.size())) {
                if (LocationListFragment.TYPE_PLACE_FAVOURITE == typePlace) {
                    getMyFavouritePlace();
                } else if (LocationListFragment.TYPE_PLACE_TIME_TO_GO == typePlace) {
                    getTimeToGoPlaces();
                } else {
                    getPlaceCreated();
                }
            }
        } else {
            //Load more get place from joco
            if (Utils.checkLoadMore(adapter, pageSize, lstPlaces.size())) {
                handleGetPlaces(lat, lng, locationFilterItemDTO);
            }
//            else {
//                handleGetPlacesGG(lat, lng, locationFilterItemDTO, adapter);
//            }
        }
    }

    /**
     * handle get more
     *
     * @param lat
     * @param lng
     */
    private void handleGetPlaces(Double lat, Double lng, LocationFilterItemDTO locationFilterItemDTO) {
//        locationFilterItemDTO = Utils.initCategoryDefault(locationFilterItemDTO);
        placeModelMgr.getAllPlace(page, lat == Constants.LAT_LNG_NULL ? null : lat, lng == Constants.LAT_LNG_NULL ?
                null : lng, locationFilterItemDTO, new ModelCallBack<PlaceResponseDTO>(TAG) {
            @Override
            public void onSuccess(PlaceResponseDTO response) {
                getPlacesSuccess(response);
            }

            @Override
            public void onError(int errorCode, String error) {
                placeListViewMgr.showMessageErr(errorCode, error);
            }

        });
    }


    /**
     * Xu li lay cac dia diem thanh cong
     *
     * @param response
     */
    private void getPlacesSuccess(PlaceResponseDTO response) {
//        for(int i = 0, size = response.getData().getLocations().size(); i < size; i++) {
//            PlaceItemDTO item = response.getData().getLocations().get(i);
//            if(!Utils.checkItemInList(item.getName(),lstPlaces)) {
//                lstPlaces.add(item);
//            }
//        }
        lstPlaces.addAll(response.getData().getLocations());
        page = response.getData().getPaging().getPage();
        pageSize = response.getData().getPaging().getPageSize();
//        Utils.sortListWithDistance(lstPlaces);
        placeListViewMgr.renderLayout(lstPlaces);
    }

    @Override
    public void gotoPlaceDetail(int position) {
        // Hard code crash
        if(lstPlaces.size() > position) {
            placeListViewMgr.gotoPlaceDetail(lstPlaces.get(position));
        }
    }

    @Override
    public void requestFavouritePlace(final PlaceItemDTO item) {
        placeModelMgr.requestFavouritePlace(item.getId(), new ModelCallBack<CommonResponseDTO>(TAG) {
                @Override
                public void onSuccess(CommonResponseDTO response) {
                    int numLike = item.getNumLike();
                    item.setIsFavourite(!item.getIsFavourite());
                    item.setNumLike(item.getIsFavourite() ? ++numLike : (numLike > 0 ? --numLike : 0));
                    placeListViewMgr.renderLayout(lstPlaces);
                }

                @Override
                public void onError( int errorCode, String error ) {
                    placeListViewMgr.showMessageErr(errorCode, error);
                }

                }
        );
    }


    @Override
    public void getAppVersion() {
        placeModelMgr.getAppVersion(MobileTypeDTO.ANDROID.getValue(), new ModelCallBack<AppVersionResponseDTO>(TAG) {
            @Override
            public void onSuccess(AppVersionResponseDTO response) {
                placeListViewMgr.checkAppVersion(response.getData());
            }

            @Override
            public void onError(int errorCode, String error) {
                placeListViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    /**
     * Get favourite place
     */
    private void getMyFavouritePlace() {
        placeModelMgr.getFavouritePlace(page, userId, new ModelCallBack<PlaceResponseDTO>(TAG) {
            @Override
            public void onSuccess(PlaceResponseDTO response) {
                getPlacesSuccess(response);
            }

            @Override
            public void onError( int errorCode, String error ) {
                placeListViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    /**
     * Get time to go places
     */
    private void getTimeToGoPlaces() {
        placeModelMgr.getTimeToGoPlace(page, timeToGoFilterDTO, new ModelCallBack<PlaceResponseDTO>(TAG) {
            @Override
            public void onSuccess(PlaceResponseDTO response) {
                if (timeToGoFilterDTO != null && SortTypeDTO.RANDOM.getValue() == timeToGoFilterDTO.getSortType
                        ()) {
                    Collections.shuffle(response.getData().getLocations());
                }
                getPlacesSuccess(response);
            }

            @Override
            public void onError(int errorCode, String error) {
                placeListViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    /**
     * Get place by category
     */
    private void handleGetPlaceByCategory(Double lat, Double lng, LocationFilterItemDTO locationFilterItemDTO) {
        handleGetPlaces(lat, lng, locationFilterItemDTO);
    }

    /**
     * Get time to go places
     */
    private void getPlaceCreated() {
        placeModelMgr.getPlaceCreated(userId, page, new ModelCallBack<PlaceResponseDTO>(TAG) {
            @Override
            public void onSuccess(PlaceResponseDTO response) {
                getPlacesSuccess(response);
            }

            @Override
            public void onError(int errorCode, String error) {
                placeListViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

}


