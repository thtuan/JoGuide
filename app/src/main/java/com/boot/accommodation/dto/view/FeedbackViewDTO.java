package com.boot.accommodation.dto.view;

import java.util.ArrayList;

/**
 * DTO cho man hinh ListTOur
 *
 * @author vuongbv
 * @since 3:25 PM 20/6/2016
 */
public class FeedbackViewDTO extends BaseDTO {
    private FeedbackDTO feedback;
    private ArrayList<FeedbackTourItemDTO> tour;

    public FeedbackDTO getFeedback() {
        return feedback;
    }

    public void setFeedback(FeedbackDTO feedback) {
        this.feedback = feedback;
    }

    public ArrayList<FeedbackTourItemDTO> getTour() {
        return tour;
    }

    public void setTour(ArrayList<FeedbackTourItemDTO> tour) {
        this.tour = tour;
    }
}
