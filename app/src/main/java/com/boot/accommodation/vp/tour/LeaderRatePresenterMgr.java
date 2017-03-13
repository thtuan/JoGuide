package com.boot.accommodation.vp.tour;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.dto.view.ReviewItemDTO;

import java.io.File;

/**
 * TouristInfoPresenterMgr
 *
 * @author vuong
 * @since: 15:47 PM 5/13/2016
 */
public interface LeaderRatePresenterMgr {

    /**
     * Get review of leader
     *
     * @param itemId
     */
    public void getReview(long itemId);

    /**
     * Get more review leader
     *
     * @param itemType
     * @param itemId
     * @param adapter
     */
    void getMoreReview(int itemType, long itemId, BaseRecyclerViewAdapter adapter);

    /**
     * send request when user commetn and rating
     *
     * @param discussDTO
     */
    void requestReviewLeader(ReviewItemDTO discussDTO);

    /**
     * send request when user like any rated
     */
    void requestLikeReviewLeader(ReviewItemDTO dto);

    /**
     * Upload photo
     *
     * @param file
     */
    void uploadPhoto(File file);

    /**
     * Delete my comment
     *
     * @param dto
     */
    void deleteReview(ReviewItemDTO dto);

}
