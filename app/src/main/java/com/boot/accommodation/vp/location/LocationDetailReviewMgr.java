package com.boot.accommodation.vp.location;

import com.boot.accommodation.dto.view.ReviewItemDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Mo ta class
 *
 * @author Dungnx
 */
public interface LocationDetailReviewMgr {
    /**
     * Render layout review
     * @param lstReview
     */
    void renderLayout( ArrayList<ReviewItemDTO> lstReview);

    void likeReviewSuccess();

    /**
     * show error
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);

    /**
     * review success
     * @param lstReview
     */
    void reviewSuccess(ArrayList<ReviewItemDTO> lstReview, boolean isHaveContent);

    void finishProcessDialog();
    /**
     * Upload file name
     * @param fileName
     */
    void updateFileNameUpload(String fileName);

    /**
     * update bar
     * @param percent
     */
    void updateProgressBar(int percent);

    /**
     * finish update
     */
    void finishUpdatePhoto();

    /**
     * update error
     * @param message
     */
    void updateError(String message);
    /**
     * show message shen we request to server
     *
     * @param message
     */
    void showMessage(String message);

    /**
     * Delete review
     */
    void deleteReviewSuccess(List<ReviewItemDTO> lstReview);

}
