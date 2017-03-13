package com.boot.accommodation.dto.view;

/**
 * Danh gia tour
 *
 * @author tuanlt
 * @since 5:11 PM 6/17/2016
 */
public class QualityAssessmentDTO extends BaseDTO {
    private String name; // ten tieu chi
    private QuantityAssessmentValueDTO vote = new QuantityAssessmentValueDTO(); // trang thai tieu chi

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public QuantityAssessmentValueDTO getVote() {
        return vote;
    }

    public void setVote(QuantityAssessmentValueDTO vote) {
        this.vote = vote;
    }
}
