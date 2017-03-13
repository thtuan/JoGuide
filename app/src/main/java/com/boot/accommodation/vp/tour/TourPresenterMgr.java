package com.boot.accommodation.vp.tour;

import java.util.List;

/**
 * Mgr presenter cho module tour
 *
 * @author tuanlt
 * @since: 1:40 PM 5/4/2016
 */
public interface TourPresenterMgr {
    /**
     * send list invi  te to server
     * @param list
     * @param tourPlanId
     */
    void requestInvite(long tourId, long tourPlanId, List<String> list);

    /**
     * get tour plan with tourId and tourPlanId
     * @param tourId
     * @param tourPlanId
     */
    void getTourPlan(long tourId, long tourPlanId);

    /**
     * Get i invited
     * @param tourId
     * @param tourPlanId
     */
    void getIInvited(long tourId, long tourPlanId);

    /**
     * Request ask to join tour
     * @param tourId
     * @param tourPlanId
     */
    void requestAskToJoinTour(long tourId, long tourPlanId);

    /**
     * Request accept join tour
     * @param tourId
     * @param tourPlanId
     */
    void requestAcceptJoinTour(long tourId, long tourPlanId, String inviteToken);

    /**
     * Check asked join tour or no
     * @param tourId
     * @param tourPlanId
     */
    void requestCheckAskedToJoinTour(long tourId, long tourPlanId);

    /**
     * Decline join tour
     * @param tourId
     * @param tourPlanId
     * @param inviteToken
     */
    void requestDeclineJoinTour(long tourId, long tourPlanId, String inviteToken);
}
