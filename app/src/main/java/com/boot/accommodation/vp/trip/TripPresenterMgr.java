package com.boot.accommodation.vp.trip;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.dto.view.TripItemDTO;

/**
 * Mgr cua module Trip
 *
 * @author tuanlt
 * @since: 9:40 AM 5/5/2016
 */
public interface TripPresenterMgr {

    /**
     * get all tour
     *
     * @param typeTour
     * @param userId
     * @param search
     */
    void getTours(int typeTour, String userId, String search);

    /**
     * get more tour
     *
     * @param adapter
     */
    void getMoreTours(BaseRecyclerViewAdapter adapter);


    /**
     * Request favourite tour
     *
     * @param item
     * @param tourId
     */
    void requestFavouriteTour(TripItemDTO item, long tourId);

    /**
     * get my tour
     *
     * @param userId
     * @param search
     */
    void getMyTour( String userId, String search);

    /**
     * get favorite tour
     *
     * @param userId
     * @param search
     */
    void getFavouriteTour(String userId, String search);

    /**
     * getCollection
     *
     * @param adminId
     * @param search
     */
    void getCollection(String adminId, String search);

}

