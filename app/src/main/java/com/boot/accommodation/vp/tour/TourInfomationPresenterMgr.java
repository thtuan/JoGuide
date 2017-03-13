package com.boot.accommodation.vp.tour;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;

/**
 * TourInfomationPresenterMgr cho man hinh tour info expand
 *
 * @author vuong-bv
 * @since: 9:25 AM 5/31/2016
 */
public interface TourInfomationPresenterMgr {

    /**
     * Get tour info
     *
     * @param tourId
     * @param tourPlanId
     */
    void getTourInfo(long tourId, long tourPlanId);

    /**
     * get tour relate
     *
     * @param tourId
     */
    void getTourRelate(long tourId);

    /**
     * Get more Tour relate
     *
     * @param tourId
     * @param adapter
     */
    void getMoreTourRelate(long tourId, BaseRecyclerViewAdapter adapter);

}
