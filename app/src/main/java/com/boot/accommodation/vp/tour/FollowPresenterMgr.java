package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.view.FollowItemDTO;

/**
 * FollowPresenterMgr
 *
 * @author thtuan
 * @since 12:51 PM 26-09-2016
 */
public interface FollowPresenterMgr {
    /**
     * get follower
     * @param userId
     */
    void getFollower(long userId);

    /**
     * get following
     * @param userId
     */
    void getFollowing(long userId);
    /**
     * Request follow
     * @param userId
     * @param followerUserId
     */
    void requestFollow(long userId, long followerUserId,FollowItemDTO dto );
}
