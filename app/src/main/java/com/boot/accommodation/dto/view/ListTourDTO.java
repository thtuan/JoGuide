package com.boot.accommodation.dto.view;

/**
 * Dto listtour
 *
 * @author Vuong-bv
 * @since: 20/6/2016
 */
public class ListTourDTO extends BaseDTO{
    private long tourId; // tour id
    private long tourPlanId; //tour plan id
    private String image;//image ò tour
    private String tourName;//name ò tour
    private String leaderName;//tour guide name
    private String date;//date start
    private int vote;// when we handle vote of user for tour we show icon about tour : good, bad or fail ..

    public ListTourDTO(long id,String image, String tourName, String leaderName, String date) {
        this.tourId = id;
        this.image = image;
        this.tourName = tourName;
        this.leaderName = leaderName;
        this.date = date;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public long getTourId() {
        return tourId;
    }

    public void setTourId( long tourId ) {
        this.tourId = tourId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTourPlanId() {
        return tourPlanId;
    }

    public void setTourPlanId( long tourPlanId ) {
        this.tourPlanId = tourPlanId;
    }
}
