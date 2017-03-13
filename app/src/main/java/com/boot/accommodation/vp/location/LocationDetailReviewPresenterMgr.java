package com.boot.accommodation.vp.location;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.dto.view.ReviewItemDTO;

import java.io.File;

/**
 * Mo ta class
 *
 * @author Dungnx
 */
public interface LocationDetailReviewPresenterMgr {

    /**
     * Get review place
     * @param placeId
     * @param typeReview
     */
    void getReview(int typeReview, long placeId);

    /**
     * Get more review place
     * @param adapter
     */
    void getMoreReview(BaseRecyclerViewAdapter adapter );

    /**
     * Request review
     * @param reviewItemDTO
     */
    void requestLikeReview(ReviewItemDTO reviewItemDTO);

    /**
     * Request create review
     * @param myReview
     */
    void requestCreateReview(ReviewItemDTO myReview);

    /**
     * Upload photo
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
