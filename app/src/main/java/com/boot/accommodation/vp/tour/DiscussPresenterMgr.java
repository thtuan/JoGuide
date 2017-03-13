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
public interface DiscussPresenterMgr {


    /**
     * method get rated
     *
     * @param itemId tourId
     */
    void getDiscuss(long itemId);

    /**
     * get more when we have long list data
     *
     * @param itemId tourId
     * @param adapter
     */
    void getMoreDiscuss(long itemId, BaseRecyclerViewAdapter adapter);

    /**
     * send request to server when user comment rand rating tour
     *
     * @param discussDTO
     */
    void requestCreateReviewTour( ReviewItemDTO discussDTO);

    /**
     * Like discuss
     * @param dto
     */
    void requestLikeDiscuss(ReviewItemDTO dto);

    /**
     * Upload photo
     * @param file
     */
    void uploadPhoto(File file);


    /**
     * Delete my comment
     * @param dto
     */
    void deleteReview(ReviewItemDTO dto);
}
