package com.boot.accommodation.model.mgr;

import com.boot.accommodation.common.control.ProgressUpdateBody;
import com.boot.accommodation.dto.view.CreateLocationDTO;
import com.boot.accommodation.dto.view.LocationFilterItemDTO;
import com.boot.accommodation.dto.view.TimeToGoFilterDTO;
import com.boot.accommodation.listener.ModelCallBack;

import java.io.File;

/**
 * Dinh nghia cac ham mac dinh cho lop home
 *
 * @author Administrator
 * @since: 11:07 AM 4/29/2016
 */
public interface HomeModelMgr {

    /**
     * Get all tour for home screen
     *
     * @param page
     * @param modelCallBack
     */
    void getAllTour(int page, ModelCallBack modelCallBack);

    /**
     * Get list location
     *
     * @param page
     * @param modelCallBack
     */
    void getAllPlace(int page, Double lat, Double lng, LocationFilterItemDTO locationFilterItemDTO, ModelCallBack
            modelCallBack);

    /**
     * Request favourite tour
     *
     * @param modelCallBack
     */
    void requestFavouriteTour(int itemType, long itemId, ModelCallBack modelCallBack);

    /**
     * Get statistic
     *
     * @param date
     * @param modelCallBack
     */
    void getStatistics(String date, ModelCallBack modelCallBack);

    /**
     * Request favourite place
     *
     * @param placeId
     * @param modelCallBack
     */
    void requestFavouritePlace(long placeId, ModelCallBack modelCallBack);

    /**
     * Get favourite place
     *
     * @param page
     * @param modelCallBack
     */
    void getFavouritePlace(int page, String userId, ModelCallBack modelCallBack);

    /**
     * get favorite tour
     *
     * @param userId        user id
     * @param search        search text
     * @param page          page 0 ->>
     * @param modelCallBack callback
     */
    void getFavouriteTour(String userId, String search, int page, ModelCallBack modelCallBack);

    /**
     * get my tour
     *
     * @param userId        user id
     * @param search        search text
     * @param page          page 0 ->>
     * @param modelCallBack callback
     */
    void getMyTour(String userId, String search, int page, ModelCallBack modelCallBack);

    /**
     * getCollection
     *
     * @param adminId
     * @param search
     * @param page
     * @param modelCallBack
     */
    void getCollection(String adminId, String search, int page, ModelCallBack modelCallBack);

    /**
     * upload photo
     *
     * @param file
     * @param modelCallBack
     * @param uploadCallbacks
     */
    void uploadPhoto(File file, ModelCallBack modelCallBack, ProgressUpdateBody.MultiUploadCallbacks uploadCallbacks,
                     int position);

    /**
     * create location on mobile
     *
     * @param createLocationDTO
     * @param modelCallBack
     */
    void requestCreateLocation(CreateLocationDTO createLocationDTO, ModelCallBack modelCallBack);

    /**
     * Get category
     *
     * @param modelCallBack
     */
    void getCategory(ModelCallBack modelCallBack);

    /**
     * Get info filter location
     *
     * @param modelCallBack
     */
    void getInfoFilterPlace(ModelCallBack modelCallBack);

    /**
     * Get famous place
     *
     * @param modelCallBack
     */
    void getFamousPlaceByProvince(long provinceId, ModelCallBack modelCallBack);

    /**
     * Get time to go places
     * @param modelCallBack
     * @param timeToGoFilterDTO
     */
    void getTimeToGoPlace(int page, TimeToGoFilterDTO timeToGoFilterDTO, ModelCallBack modelCallBack);

    /**
     * Get category child from parent
     * @param parentCategoryId
     */
    void getCategoryChild(long parentCategoryId, ModelCallBack modelCallBack);

    /**
     * Get filter area
     * @param modelCallBack
     */
    void getFilterAreas( ModelCallBack modelCallBack);

    /**
     * Get place created
     * @param userId
     * @param page
     * @param modelCallBack
     */
    void getPlaceCreated(String userId, int page, ModelCallBack modelCallBack);

    /**
     * Get appver
     */
    void getAppVersion(int typeMobile, ModelCallBack modelCallBack);

}
