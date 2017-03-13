package com.boot.accommodation.vp.accommodation;

import com.boot.accommodation.dto.view.FeedbackItemDTO;

/**
 * AccommodationPresenterMgr
 *
 * @author tuanlt
 * @since 4:42 PM 1/13/17
 */
public interface AccommodationPresenterMgr {

    /**
     * Get area filter
     */
    void getAreaFilter();

    /**
     * Get area special
     */
    void getAreaSpecial();

    /**
     * Request send feedback
     * @param feedbackItemDTO
     */
    void requestSendFeedback( FeedbackItemDTO feedbackItemDTO);

}
