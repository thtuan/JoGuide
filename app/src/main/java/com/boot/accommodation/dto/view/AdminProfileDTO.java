package com.boot.accommodation.dto.view;

/**
 * DTO contain data follow
 *
 * @author vuongbv
 * @since 2:36 PM 7/14/2016
 */
public class AdminProfileDTO extends BaseDTO {
    private long id;//id of admin
    private String name;//name of admin
    private String avatar; //avata of admin
    private boolean isFriend;//check is friend with user login
    private String fullName;//ful name of admin
    private String email;//email of amdin
    private String phone;//phone of admin
    private String website;//address website of admin
    private String about;//content about admin

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(boolean friend) {
        isFriend = friend;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
