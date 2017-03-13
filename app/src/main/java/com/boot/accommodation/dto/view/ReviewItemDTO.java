package com.boot.accommodation.dto.view;

/**
 * DTO discuss
 *
 * @author Vuong-bv
 * @since: 5/13/2016
 */
public class ReviewItemDTO extends BaseDTO {

    private long reviewId; //rated id
    private Long userId; //user id
    private String userAvatar;//avatat of user comment
    private String userName;//tourist name
    private String image;//comment image
    private String content;//noi dung comment
    private String dateTime;//date time rated
    private int numLike;//how many people liked this comment
    private boolean liked = false; //check user login like or not this comment (rated)
    private int itemType; // type review ( in ItemTypeDTO)
    private double rating; // rating
    private long itemId; // id review (tourId, placeId, userId ...)
    private int userType = UserItemDTO.TYPE_USER; // Type of user

    public ReviewItemDTO() {

    }
    public ReviewItemDTO( long id, String idUser, String avatar, String touristName, String touristImage, String content
            , String dateTime, int liked) {
        this.reviewId = id;
        this.userId = Long.parseLong(idUser);
        this.userAvatar = avatar;
        this.userName = touristName;
        this.image = touristImage;
        this.content = content;
        this.dateTime = dateTime;
        this.numLike = liked;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId( Long userId ) {
        this.userId = userId;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar( String userAvatar ) {
        this.userAvatar = userAvatar;
    }

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId( long reviewId ) {
        this.reviewId = reviewId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName( String userName ) {
        this.userName = userName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getNumLike() {
        return numLike;
    }

    public void setNumLike( int numLike ) {
        this.numLike = numLike;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType( int itemType ) {
        this.itemType = itemType;
    }

    public double getRating() {
        return rating;
    }

    public void setRating( double rating ) {
        this.rating = rating;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked( boolean liked ) {
        this.liked = liked;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId( long itemId ) {
        this.itemId = itemId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
