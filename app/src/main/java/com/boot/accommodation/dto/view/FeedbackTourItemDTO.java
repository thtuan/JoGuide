package com.boot.accommodation.dto.view;

/**
 * Dto listtour for feedback
 *
 * @author Vuong-bv
 * @since: 20/6/2016
 */
public class FeedbackTourItemDTO extends BaseDTO {
    private Long tourId;//id tour
    private Long tourPlanId; // tour plan
    private String tourName;//name of tour

    public FeedbackTourItemDTO(Long tourId, Long tourPlanId, String name ) {
        this.tourId = tourId;
        this.tourPlanId = tourPlanId;
        this.tourName = name;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public Long getTourPlanId() {
        return tourPlanId;
    }

    public void setTourPlanId( Long tourPlanId ) {
        this.tourPlanId = tourPlanId;
    }
}
