package com.boot.accommodation.dto.view;

/**
 * Dto tourist for expand list tourinfo
 *
 * @author Vuong-bv
 * @since: 5/30/2016
 */
public class FollowItemDTO extends  BaseDTO  {
    private long id; //touristID
    private String name;//tourist name
    private String image;//tourist image
    private String phone;//phone number
    private LocationDTO location;//loction of tourist
    private boolean isFriend;

    public FollowItemDTO() {
    }

    public FollowItemDTO( long id, String name, String image, String phone, LocationDTO location, boolean isFriend) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.phone = phone;
        this.location = location;
        this.isFriend = isFriend;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    public boolean getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(boolean friend) {
        isFriend = friend;
    }
}
