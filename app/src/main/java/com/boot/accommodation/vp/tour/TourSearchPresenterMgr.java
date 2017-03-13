package com.boot.accommodation.vp.tour;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;

/**
 * TourInfomationPresenterMgr cho man hinh tour info expand
 *
 * @author vuong-bv
 * @since: 9:25 AM 5/31/2016
 */
public interface TourSearchPresenterMgr {
    /**
     * get result search location
     *
     * @param keyWord
     * @param date    date search
     */
    void getSearchTour( String keyWord, String date );

    /**
     * Get more data favourite search tour
     *
     * @param keyWord
     * @param date
     */
    void getMoreFavouriteTour( BaseRecyclerViewAdapter adapter, String keyWord, String date );

    /**
     * Get more data near by
     *
     * @param keyWord
     * @param date
     */
    void getMoreNearByTour( BaseRecyclerViewAdapter adapter, String keyWord, String date );
}
