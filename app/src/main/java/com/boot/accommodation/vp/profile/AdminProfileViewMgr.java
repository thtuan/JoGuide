package com.boot.accommodation.vp.profile;

import com.boot.accommodation.dto.view.ProfileUserViewDTO;


/**
 * view mgr for admin
 *
 * @author vuongbv
 * @since 6:47 PM 7/14/2016
 */
public interface AdminProfileViewMgr {

    /**
     * render layout
     */
    void renderLayout(ProfileUserViewDTO profileUserViewDTO);

    /**
     * show error
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);


    /**
     * follow admin success
     */
    void requestFollowSuccess();
}
