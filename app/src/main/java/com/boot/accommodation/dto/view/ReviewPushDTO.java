package com.boot.accommodation.dto.view;

/**
 * Review used push to server
 *
 * @author thtuan
 * @since 10:24 PM 01-07-2016
 */
public class ReviewPushDTO extends BaseDTO {
    private int itemType;
    private int mediaType;
    private Long itemId;
    private Float rating;
    private String content;
    private Long reviewerId;
    private String urlImage;
    private Integer likeCount;

    public int getItemType() {
        return itemType;
    }

    public void setItemType( int itemType) {
        this.itemType = itemType;
    }

    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType( int mediaType) {
        this.mediaType = mediaType;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }
}
