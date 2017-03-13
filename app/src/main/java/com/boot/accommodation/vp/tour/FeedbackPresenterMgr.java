package com.boot.accommodation.vp.tour;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;

/**
 * TourInfomationPresenterMgr cho man hinh tour info expand
 *
 * @author vuong-bv
 * @since: 9:25 AM 5/31/2016
 */
public interface FeedbackPresenterMgr {
    /**
     * get feedback
     * @param idTour
     * @param tourPlanId
     * @param date
     */
    void getFeedback(Long idTour, Long tourPlanId, String date);

    /**
     * method get more
     * @param idTour
     * @param date
     * @param adapter
     */
    void getMoreFeedback(Long idTour, Long tourPlanId, String date, BaseRecyclerViewAdapter adapter);
}
