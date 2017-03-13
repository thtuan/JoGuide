package com.boot.accommodation.vp.category;

import com.boot.accommodation.dto.response.QualityAssessmentResponseDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.QualityAssessmentModel;
import com.boot.accommodation.model.mgr.QualityAssessmentModelMgr;

/**
 * Presenter lay chat luong tour
 *
 * @author tuanlt
 * @since 10:20 AM 6/20/2016
 */
public class QualityAssessmentPresenter implements QualityAssessmentPresenterMgr {

    private QualityAssessmentModelMgr qualityAssessmentModelMgr; // Mgr chat luong tour
    private QualityAssessmentViewMgr qualityAssessmentViewMgr; // view

    public QualityAssessmentPresenter(QualityAssessmentViewMgr qualityAssessmentViewMgr) {
        qualityAssessmentModelMgr = new QualityAssessmentModel();
        this.qualityAssessmentViewMgr = qualityAssessmentViewMgr;
    }

    /**
     * Lay chat luong tour
     *
     * @param tourId
     */
    @Override
    public void getQualityAssessment(long tourId, long tourPlanId, String date) {
        qualityAssessmentModelMgr.getQualityAssessment(tourId, tourPlanId, date, new
                ModelCallBack<QualityAssessmentResponseDTO>
                        () {
                    @Override
                    public void onSuccess(QualityAssessmentResponseDTO response) {
                        qualityAssessmentViewMgr.renderLayout(response.getData());
                    }

                    @Override
                    public void onError( int errorCode, String error ) {
                        qualityAssessmentViewMgr.showMessageErr(errorCode, error);
                    }

                });
    }
}
