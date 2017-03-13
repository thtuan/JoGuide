package com.boot.accommodation.vp.tour;

import java.util.List;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 2:36 PM 17-05-2016
 */
public interface TourNotificationPresenterMgr {

    /**
     * handle how to get notification
     * @param userPlan
     * @param tourId
     */
    void handleGetNotification(int userPlan,String tourId);

    /**
     * hanlde how to load more notification
     * @param adapter
     * @param userPlan
     * @param id
     */
    void handleLoadMoreNotification(TourNotificationAdapter adapter, int userPlan, String id);

    /**
     * whether user seen or not
     * @param lstId
     */
    void requestSeen(List<Long> lstId);
}
