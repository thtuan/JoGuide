package com.boot.accommodation.vp.profile;

import com.boot.accommodation.dto.view.ProfileUserViewDTO;
import com.boot.accommodation.dto.view.VipMemberConfigDTO;
import com.boot.accommodation.dto.view.VipMemberDTO;

/**
 * VipMemberRegisterViewMgr
 *
 * @author tuanlt
 * @since 10:58 AM 12/26/16
 */
public interface VipMemberRegisterViewMgr {

    /**
     * Get vip member config success
     */
    void renderVipMemberConfig(VipMemberConfigDTO vipMemberConfigDTO);

    /**
     * Register vip member success
     */
    void requestRegisterVipMemberSuccess();

    /**
     * Show message error
     * @param errorCode
     * @param message
     */
    void showMessageErr( int errorCode, String message );

    /**
     * Render layout
     * @param vipMemberDTO
     */
    void renderLayout(VipMemberDTO vipMemberDTO);

    /**
     * Get profile user success
     */
    void getUserProfileSuccess(ProfileUserViewDTO profileUserViewDTO);
}
