package com.boot.accommodation.vp.category;

/**
 * Presenter cho man hinh lay chat luong tour
 *
 * @author tuanlt
 * @since 10:19 AM 6/20/2016
 */
public interface QualityAssessmentPresenterMgr {
    /**
     * Lay chat luong tour
     * @param tourId
     */
    void getQualityAssessment(long tourId, long tourPlanId, String date);
}
