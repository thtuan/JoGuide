package com.boot.accommodation.vp.tour;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;

/**
 * Presenter tour place picture
 *
 * @author tuanlt
 * @since 10:39 CH 14/08/2016
 */
public interface TourPlacePicturePresenterMgr {
    /**
     * get info tour place
     * @param placeId
     */
    void getTourPlacePicture(long tourId, long placeId);

    /**
     * get info tour place
     * @param adapter
     */
    void getMoreTourPlacePicture(BaseRecyclerViewAdapter adapter);
}
