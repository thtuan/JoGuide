package com.boot.accommodation.vp.profile;

import com.boot.accommodation.dto.view.VipMemberRegistrationDTO;

/**
 * VipMemberRegisterPresenterMgr
 *
 * @author tuanlt
 * @since 10:41 AM 12/26/16
 */
public interface VipMemberRegisterPresenterMgr {

    /**
     * Get vip member config
     */
    void getVipMemberConfig();

    /**
     * Get info vip member
     */
    void getInfoVipMember();

    /**
     * Request register vip member
     * @param vipMemberRegistrationDTO
     */
    void requestRegisterVipMember(VipMemberRegistrationDTO vipMemberRegistrationDTO);

    /**
     * Get user info
     */
    void getUserInfo(long userId);
}
