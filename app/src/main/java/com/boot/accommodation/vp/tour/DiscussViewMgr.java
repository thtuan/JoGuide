package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.view.ReviewItemDTO;

import java.util.ArrayList;

/**
 * View Mgr cho man hinh tourist info
 *
 * @author vuong
 * @since: 15:49 PM 5/13/2016
 */
public interface DiscussViewMgr {
    /**
     * method renderLayoutMonth for tour rate
     *
     * @param DiscussDTO
     */
    void renderLayout( ArrayList<ReviewItemDTO> DiscussDTO);

    /**
     * show error
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);

    /**
     * show message
     *
     * @param message
     */
    void showMessage( String message );

    /**
     * Rating tour thanh cong
     */
    void ratingTourSuccess( ReviewItemDTO discussDTO);

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
     * Delete my comment
     * @param dto
     */
    void deleteComment(ReviewItemDTO dto);

}
