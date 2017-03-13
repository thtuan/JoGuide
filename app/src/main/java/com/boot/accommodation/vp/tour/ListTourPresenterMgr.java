package com.boot.accommodation.vp.tour;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;

/**
 * list tour presenter
 *
 * @author vuong-bv
 * @since: 9:25 AM 5/31/2016
 */
public interface ListTourPresenterMgr {
    /**
     * get list tour
     * @param date
     */
    void getListTour( String date );

    /**
     * get more list tour
     * @param date
     * @param adapter
     */
    void getMoreTour( String date , BaseRecyclerViewAdapter adapter);
}
