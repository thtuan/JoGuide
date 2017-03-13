package com.boot.accommodation.dto.view;

/**
 * Created by xdung on 5/21/2016.
 */
public class ReviewPlaceDetailDTO {
    private String id;
    private String avatar;
    private double ratePoint;
    private String dateRate;
    private String comment;
    private String image;
    private int numLike;

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar( String avatar ) {
        this.avatar = avatar;
    }

    public double getRatePoint() {
        return ratePoint;
    }

    public void setRatePoint( double ratePoint ) {
        this.ratePoint = ratePoint;
    }

    public String getDateRate() {
        return dateRate;
    }

    public void setDateRate( String dateRate ) {
        this.dateRate = dateRate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment( String comment ) {
        this.comment = comment;
    }

    public String getImage() {
        return image;
    }

    public void setImage( String image ) {
        this.image = image;
    }

    public int getNumLike() {
        return numLike;
    }

    public void setNumLike( int numLike ) {
        this.numLike = numLike;
    }
}
