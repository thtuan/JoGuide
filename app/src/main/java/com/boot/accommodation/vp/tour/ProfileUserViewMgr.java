package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.view.ProfileUserViewDTO;
import com.boot.accommodation.dto.view.VipMemberDTO;

/**
 * View Mgr cho man hinh tourist info
 *
 * @author vuong
 * @since: 15:49 PM 5/13/2016
 */
public interface ProfileUserViewMgr {


    /**
     * render layout innit, must be show first before adapter renderlayout
     * @param userItemDTO
     */
    void renderInnitLayout(ProfileUserViewDTO userItemDTO);


    /**
     * show message when they edit success
     * @param message
     */
    void showMessage(String message);

    /**
     * Edit user success
     */
    void editUserInfoSuccess();

    /**
     * show error
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);

    /**
     * request follow success
     * @param check
     */
    void requestFollowSuccess(boolean check);

    /**
     * Render vip member info
     */
    void renderVipMemberInfo(VipMemberDTO vipMemberDTO);

    /**
     * Render password success
     */
    void renderPasswordSuccess();
}
