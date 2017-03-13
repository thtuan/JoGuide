package com.boot.accommodation.dto.view;

/**
 * DTO for expand tour info
 *
 * @author Vuong-bv
 * @since: 5/13/2016
 */
public class ProfileUserViewDTO extends BaseDTO {

    private UserItemDTO user;
    private int followers;
    private int followings;

    public UserItemDTO getUser() {
        return user;
    }

    public void setUser( UserItemDTO user ) {
        this.user = user;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers( int followers ) {
        this.followers = followers;
    }

    public int getFollowings() {
        return followings;
    }

    public void setFollowings(int followings) {
        this.followings = followings;
    }
}
