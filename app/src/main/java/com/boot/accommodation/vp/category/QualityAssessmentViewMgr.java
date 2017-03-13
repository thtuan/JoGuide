package com.boot.accommodation.vp.category;

import com.boot.accommodation.dto.view.QualityAssessmentViewDTO;

/**
 * View Mgr chat luong tour
 *
 * @author tuanlt
 * @since 10:55 AM 6/20/2016
 */
public interface QualityAssessmentViewMgr {
    /**
     * render layout
     * @param qualityAssessmentViewDTO
     */
    void renderLayout(QualityAssessmentViewDTO qualityAssessmentViewDTO);

    /**
     * show error
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);
}
