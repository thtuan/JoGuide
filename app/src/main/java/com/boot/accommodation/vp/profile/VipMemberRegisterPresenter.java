package com.boot.accommodation.vp.profile;

import com.boot.accommodation.dto.response.CommonResponseDTO;
import com.boot.accommodation.dto.response.ProfileUserResponseDTO;
import com.boot.accommodation.dto.response.VipMemberConfigResponseDTO;
import com.boot.accommodation.dto.response.VipMemberResponseDTO;
import com.boot.accommodation.dto.view.VipMemberRegistrationDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.ProfileModel;
import com.boot.accommodation.model.impl.UserModel;
import com.boot.accommodation.model.mgr.ProfileModelMgr;
import com.boot.accommodation.model.mgr.UserModelMgr;
import com.boot.accommodation.util.Utils;

/**
 * VipMemberRegisterPresenter
 *
 * @author tuanlt
 * @since 10:52 AM 12/26/16
 */
public class VipMemberRegisterPresenter implements VipMemberRegisterPresenterMgr {

    private VipMemberRegisterViewMgr vipMemberRegisterViewMgr; //Vip member register
    private UserModelMgr userModelMgr; //User model
    private String TAG = "";
    private ProfileModelMgr profileModelMgr;

    public VipMemberRegisterPresenter(VipMemberRegisterViewMgr vipMemberRegisterViewMgr) {
        this.vipMemberRegisterViewMgr = vipMemberRegisterViewMgr;
        this.userModelMgr = new UserModel();
        TAG = Utils.getTag(vipMemberRegisterViewMgr.getClass());
        profileModelMgr = new ProfileModel();
    }

    @Override
    public void getVipMemberConfig() {
        userModelMgr.getVipMemberConfig(new ModelCallBack<VipMemberConfigResponseDTO>(TAG) {
            @Override
            public void onSuccess(VipMemberConfigResponseDTO response) {
                vipMemberRegisterViewMgr.renderVipMemberConfig(response.getData());
            }

            @Override
            public void onError(int errorCode, String error) {
                vipMemberRegisterViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void getInfoVipMember() {
        userModelMgr.getInfoVipMember(new ModelCallBack<VipMemberResponseDTO>(TAG) {
            @Override
            public void onSuccess(VipMemberResponseDTO response) {
                vipMemberRegisterViewMgr.renderLayout(response.getData());
            }

            @Override
            public void onError(int errorCode, String error) {
                vipMemberRegisterViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void requestRegisterVipMember(VipMemberRegistrationDTO vipMemberRegistrationDTO) {
        userModelMgr.requestRegisterVipMember(vipMemberRegistrationDTO, new ModelCallBack<CommonResponseDTO>(TAG) {
            @Override
            public void onSuccess(CommonResponseDTO response) {
                vipMemberRegisterViewMgr.requestRegisterVipMemberSuccess();
            }

            @Override
            public void onError(int errorCode, String error) {
                vipMemberRegisterViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void getUserInfo(long userId) {
        profileModelMgr.getProfileUser(userId, new ModelCallBack<ProfileUserResponseDTO>(TAG) {
            @Override
            public void onSuccess(ProfileUserResponseDTO response) {
                vipMemberRegisterViewMgr.getUserProfileSuccess(response.getData());
            }

            @Override
            public void onError(int errorCode, String error) {
                vipMemberRegisterViewMgr.showMessageErr(errorCode, error);
            }

        });
    }
}
