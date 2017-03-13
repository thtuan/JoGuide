package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.QualityAssessmentViewDTO;

/**
 * Reponse Chat luong tour
 *
 * @author tuanlt
 * @since 10:33 AM 6/20/2016
 */
public class QualityAssessmentResponseDTO extends BaseResponseDTO {
    private QualityAssessmentViewDTO data; // du lieu chat luong tour

    public QualityAssessmentViewDTO getData() {
        return data;
    }

    public void setData( QualityAssessmentViewDTO data ) {
        this.data = data;
    }
}
