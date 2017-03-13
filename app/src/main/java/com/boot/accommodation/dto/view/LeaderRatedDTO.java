package com.boot.accommodation.dto.view;

/**
 * leaderRate DTO
 *
 * @author Vuong-bv
 * @since: 5/13/2016
 */
public class LeaderRatedDTO extends BaseDTO {

    private String id; //rated id
    private String nameReviewer;//tourist name
    private String imageUrl;//comment image
    private String avatar;//avatar tourist
    private String content;//noi dung comment
    private String reviewTime;//date time rated
    private double rating;// star rated
    private int likeCount;//how many people liked this comment
    private boolean islike = false; //check user login like or not this comment (rated)


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean islike() {
        return islike;
    }

    public void setIslike(boolean islike) {
        this.islike = islike;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return nameReviewer;
    }

    public void setName(String name) {
        this.nameReviewer = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateTime() {
        return reviewTime;
    }

    public void setDateTime(String dateTime) {
        this.reviewTime = dateTime;
    }

    public double getStar() {
        return rating;
    }

    public void setStar(double star) {
        this.rating = star;
    }

    public int getLiked() {
        return likeCount;
    }

    public void setLiked(int liked) {
        this.likeCount = liked;
    }
}
