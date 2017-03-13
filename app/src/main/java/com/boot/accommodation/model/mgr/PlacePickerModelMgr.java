package com.boot.accommodation.model.mgr;

import com.boot.accommodation.listener.ModelCallBack;

/**
 * PlacePickerModelMgr
 *
 * @author tuanlt
 * @since 3:05 PM 9/23/16
 */
public interface PlacePickerModelMgr {

    /**
     * Get list place google by type
     * @param lat
     * @param lng
     * @param categoryId
     * @param modelCallBack
     */
    void getListPlaceGG(double lat, double lng, long categoryId, String name, ModelCallBack modelCallBack);

    /**
     * Get place detail
     * @param placeId
     * @param modelCallBack
     */
    void getPlaceDetail( String placeId, ModelCallBack modelCallBack );

    /**
     * Get list place google next
     * @param lat
     * @param lng
     * @param categoryId
     * @param pageToken
     * @param modelCallBack
     */
    void getListPlaceGGNext(double lat, double lng, long categoryId, String pageToken, ModelCallBack modelCallBack);

    /**
     * Get list place without position
     * @param textSearch
     * @param categoryId
     * @param modelCallBack
     */
    void getListPlaceGGWithoutPosition(String textSearch, long categoryId, ModelCallBack modelCallBack );

    /**
     * Get list place without position
     * @param categoryId
     * @param pageToken
     * @param modelCallBack
     */
    void getListPlaceGGNextWithoutPosition(long categoryId, String pageToken, ModelCallBack modelCallBack);

}
