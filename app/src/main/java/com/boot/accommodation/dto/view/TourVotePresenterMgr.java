package com.boot.accommodation.dto.view;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 4:15 PM 20-06-2016
 */
public interface TourVotePresenterMgr {
    /**
     * Send vote
     * @param tourVote
     */
    void sendVote(long tourId, long tourPlanId, TourVoteDTO tourVote);

    /**
     * Get vote criteria
     * @param tourId
     * @param tourPlanId
     */
    void getVoteCriteria(long tourId, long tourPlanId);
}
