package com.boot.accommodation.dto.view;

/**
 * DTO contain data follow
 *
 * @author tuanlt
 * @since 2:36 PM 7/12/2016
 */
public class FollowDTO {
    private long userId; // id user followed
    private long followUserId; // id

    public long getUserId() {
        return userId;
    }

    public long getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId( long followUserId ) {
        this.followUserId = followUserId;
    }

    public void setUserId( long userId ) {
        this.userId = userId;
    }
}
