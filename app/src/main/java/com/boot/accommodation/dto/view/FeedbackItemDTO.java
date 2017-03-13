package com.boot.accommodation.dto.view;

/**
 * Dto listtour
 *
 * @author Vuong-bv
 * @since: 20/6/2016
 */
public class FeedbackItemDTO extends BaseDTO {

    private long feedbackId; // id feedback
    private long userId; // id user
    private String userAvatar; //
    private String tourName; // tour name
    private String userName;  // name user
    private String content; // content
    private String date; // date
    private long tourId; // tour Id
    private long tourPlanId; // tour plan Id

    public FeedbackItemDTO() {};

    public FeedbackItemDTO(long feedbackId, String image, String tourName, String touristName, String date, String content) {
        this.feedbackId = feedbackId;
        this.userAvatar = image;
        this.tourName = tourName;
        this.userName = touristName;
        this.date = date;
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFeedbackId(long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTourId() {
        return tourId;
    }

    public void setTourId(long tourId) {
        this.tourId = tourId;
    }

    public long getTourPlanId() {
        return tourPlanId;
    }

    public void setTourPlanId(long tourPlanId) {
        this.tourPlanId = tourPlanId;
    }
}
