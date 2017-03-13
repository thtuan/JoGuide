package com.boot.accommodation.dto.view;

/**
 * PlaceReportDTO
 *
 * @author tuanlt
 * @since 5:39 PM 11/9/16
 */
public class PlaceReportDTO extends BaseDTO {
    private String feedback; //Feedback
    private Double lat; //Lat
    private Double lng; //Lng

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
