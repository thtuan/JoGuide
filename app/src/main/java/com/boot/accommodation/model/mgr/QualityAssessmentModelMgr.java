package com.boot.accommodation.model.mgr;

import com.boot.accommodation.listener.ModelCallBack;

/**
 * Chat luong tour model mgr
 *
 * @author tuanlt
 * @since 10:28 AM 6/20/2016
 */
public interface QualityAssessmentModelMgr {
    /**
     * Lay chat luong tour
     * @param tourId
     * @param modelCallBack
     */
    void getQualityAssessment( long tourId, long tourPlanId, String date, ModelCallBack modelCallBack );
}

