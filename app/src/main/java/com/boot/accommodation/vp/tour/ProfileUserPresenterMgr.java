package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.view.ChangePasswordDTO;
import com.boot.accommodation.dto.view.FollowItemDTO;
import com.boot.accommodation.dto.view.UserItemDTO;

/**
 * profile for user presenter
 *
 * @author vuong
 * @since: 15:47 PM 5/13/2016
 */
public interface ProfileUserPresenterMgr {
    /**
     * get tourist
     *
     */
    void getProfileUser(long touristId );

    /**
     * send request when user edit their profile
     *
     * @param userItemDTO
     */
    void requestEditProfile( UserItemDTO userItemDTO);

    /**
     * Request follow
     * @param userId
     * @param followerUserId
     */
    void requestFollow(long userId, long followerUserId,FollowItemDTO dto );

    /**
     * Get info vip member
     */
    void getInfoVipMember();

    /**
     * Reques change pass
     * @param changePasswordDTO
     */
    void requestChangePassword(ChangePasswordDTO changePasswordDTO);

}
