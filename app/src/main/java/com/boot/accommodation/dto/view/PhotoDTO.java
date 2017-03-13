package com.boot.accommodation.dto.view;

/**
 * Created by xdung on 5/20/2016.
 */
public class PhotoDTO {
    public String createUser;
    public String comment;
    public String url;
    public String createUserAvatar;
    public String createDate;
    private int locationType;
    private int progressPercentage = 100; //percent load photo

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreateUserAvatar() {
        return createUserAvatar;
    }

    public void setCreateUserAvatar(String createUserAvatar) {
        this.createUserAvatar = createUserAvatar;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getLocationType() {
        return locationType;
    }

    public void setLocationType(int locationType) {
        this.locationType = locationType;
    }

    public int getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(int progressPercentage) {
        this.progressPercentage = progressPercentage;
    }
}
