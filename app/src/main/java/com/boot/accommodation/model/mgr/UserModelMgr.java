package com.boot.accommodation.model.mgr;

import com.boot.accommodation.dto.view.FollowDTO;
import com.boot.accommodation.dto.view.VipMemberRegistrationDTO;
import com.boot.accommodation.listener.ModelCallBack;

/**
 * UserModelMgr
 *
 * @author tuanlt
 * @since 8:08 PM 12/25/16
 */
public interface UserModelMgr {

    /**
     * Get vip member config
     * @param modelCallBack
     */
    void getVipMemberConfig(ModelCallBack modelCallBack);

    /**
     * Get vip member info
     * @param modelCallBack
     */
    void getInfoVipMember(ModelCallBack modelCallBack);

    /**
     * Request register vip mmeber
     */
    void requestRegisterVipMember(VipMemberRegistrationDTO vipMemberRegistrationDTO, ModelCallBack modelCallBack);

    /**
     * get follower
     * @param userId
     * @param modelCallBack
     */
    void getFollower(long userId, ModelCallBack modelCallBack);

    /**
     * get following
     * @param userId
     * @param modelCallBack
     */
    void getFollowing(long userId, ModelCallBack modelCallBack);

    /**
     * Request follow user
     * @param followDTO
     * @param modelCallBack
     */
    void requestFollow(FollowDTO followDTO, ModelCallBack modelCallBack);
}
