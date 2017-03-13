package com.boot.accommodation.model.impl;

import com.boot.accommodation.base.BaseModel;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.response.CommonResponseDTO;
import com.boot.accommodation.dto.response.FollowUserResponseDTO;
import com.boot.accommodation.dto.response.VipMemberConfigResponseDTO;
import com.boot.accommodation.dto.response.VipMemberResponseDTO;
import com.boot.accommodation.dto.view.FollowDTO;
import com.boot.accommodation.dto.view.VipMemberRegistrationDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.mgr.UserModelMgr;
import com.boot.accommodation.util.PreferenceUtils;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * UserModel
 *
 * @author tuanlt
 * @since 8:28 PM 12/25/16
 */
public class UserModel extends BaseModel implements UserModelMgr {

    private interface Interface {

        @GET("user/vip-member/config")
        Call<VipMemberConfigResponseDTO> getVipMemberConfig(
                @Header(XAPITOKEN) String token
        );

        @GET("user/vip-member/info")
        Call<VipMemberResponseDTO> getInfoVipMember(
                @Header(XAPITOKEN) String token
        );

        @POST("user/vip-member/register")
        Call<CommonResponseDTO> requestRegisterVipMember(
                @Body VipMemberRegistrationDTO vipMemberRegistrationDTO,
                @Header(XAPITOKEN) String token
        );

        /**
         * Get profile collection
         *
         * @param userId
         * @param token
         * @return
         */
        @GET("user/{userId}/follower")
        Call<FollowUserResponseDTO> getFollower(
                @Path("userId") long userId,
                @Header(XAPITOKEN) String token
        );

        @GET("user/{adminId}/following")
        Call<FollowUserResponseDTO> getFollowing(
                @Path("adminId") long userId,
                @Header(XAPITOKEN) String token
        );
        /**
         * Request follow user
         * @return
         */
        @POST("user/follow")
        Call<CommonResponseDTO> requestFollow(
                @Body FollowDTO followUserModel,
                @Header(XAPITOKEN) String token
        );
    }

    @Override
    public void getVipMemberConfig(ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getVipMemberConfig(PreferenceUtils.getString(Constants.Preference.PREFERENCE_USER_TOKEN,""));
        // goi request
        requestAPI(modelCallBack);
    }

    @Override
    public void getInfoVipMember(ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getInfoVipMember(PreferenceUtils.getString(Constants.Preference.PREFERENCE_USER_TOKEN,""));
        // goi request
        requestAPI(modelCallBack);
    }

    @Override
    public void requestRegisterVipMember(VipMemberRegistrationDTO vipMemberRegistrationDTO, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestRegisterVipMember(vipMemberRegistrationDTO, PreferenceUtils.getString(Constants
                .Preference.PREFERENCE_USER_TOKEN,""));
        // goi request
        requestAPI(modelCallBack);
    }

    @Override
    public void getFollower(long userId, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getFollower(userId, PreferenceUtils.getString(Constants.Preference
                .PREFERENCE_USER_TOKEN, ""));
        // goi request
        requestAPI(modelCallBack);
    }

    @Override
    public void getFollowing(long userId, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getFollowing(userId, PreferenceUtils.getString(Constants.Preference
                .PREFERENCE_USER_TOKEN, ""));
        // goi request
        requestAPI(modelCallBack);
    }

    @Override
    public void requestFollow(FollowDTO followUserModel, ModelCallBack modelCallBack ) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestFollow(followUserModel, PreferenceUtils.getString(Constants
                .Preference
                .PREFERENCE_USER_TOKEN, ""));
        requestAPI(modelCallBack);
    }
}
