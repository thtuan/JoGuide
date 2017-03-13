package com.boot.accommodation.dto.view;

import java.util.ArrayList;

/**
 * DTO cho man hinh danh gia chat luong
 *
 * @author tuanlt
 * @since 5:13 PM 6/17/2016
 */
public class QualityAssessmentViewDTO extends BaseDTO {
    private int numFeedback; // so luong feedback
    private ArrayList<QualityAssessmentDTO> quality; // ds tour assessment
    private QuantityAssessmentValueDTO totalQualityAssessment; // total danh gia

    public int getNumFeedback() {
        return numFeedback;
    }

    public void setNumFeedback( int numFeedback ) {
        this.numFeedback = numFeedback;
    }

    public ArrayList<QualityAssessmentDTO> getQuality() {
        return quality;
    }

    public void setQuality( ArrayList<QualityAssessmentDTO> quality ) {
        this.quality = quality;
    }

    public QuantityAssessmentValueDTO getTotalQualityAssessment() {
        return totalQualityAssessment;
    }

    public void setTotalQualityAssessment( QuantityAssessmentValueDTO totalQualityAssessment ) {
        this.totalQualityAssessment = totalQualityAssessment;
    }
}
