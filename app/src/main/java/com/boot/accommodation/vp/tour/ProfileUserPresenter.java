package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.response.CommonResponseDTO;
import com.boot.accommodation.dto.response.ProfileUserResponseDTO;
import com.boot.accommodation.dto.response.VipMemberResponseDTO;
import com.boot.accommodation.dto.view.ChangePasswordDTO;
import com.boot.accommodation.dto.view.FollowDTO;
import com.boot.accommodation.dto.view.FollowItemDTO;
import com.boot.accommodation.dto.view.UserItemDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.LoginModel;
import com.boot.accommodation.model.impl.ProfileModel;
import com.boot.accommodation.model.impl.UserModel;
import com.boot.accommodation.model.mgr.LoginModelMgr;
import com.boot.accommodation.model.mgr.ProfileModelMgr;
import com.boot.accommodation.model.mgr.UserModelMgr;
import com.boot.accommodation.util.Utils;

/**
 * Presenter for Profile
 *
 * @author vuong
 * @since 15:47 PM 5/13/2016
 */
public class ProfileUserPresenter implements ProfileUserPresenterMgr {

    private ProfileUserViewMgr profileUserViewMgr; //Profile model
    private ProfileModelMgr tourModelMgr; //Tour model
    private LoginModelMgr loginModelMgr; //Login model
    private UserModelMgr userModelMgr; //User model
    private String TAG = ""; // Tag

    public ProfileUserPresenter( ProfileUserViewMgr profileUserViewMgr ) {
        this.profileUserViewMgr = profileUserViewMgr;
        tourModelMgr = new ProfileModel();
        userModelMgr = new UserModel();
        loginModelMgr = new LoginModel();
        TAG = Utils.getTag(profileUserViewMgr.getClass());
    }

    @Override
    public void getProfileUser(long touristId) {
        tourModelMgr.getProfileUser(touristId, new ModelCallBack<ProfileUserResponseDTO>(TAG) {

            @Override
            public void onSuccess(ProfileUserResponseDTO response) {
                profileUserViewMgr.renderInnitLayout(response.getData());
            }

            @Override
            public void onError( int errorCode, String error ) {
                profileUserViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void requestEditProfile( final UserItemDTO userItemDTO) {
        tourModelMgr.requestEditProfileInfo( userItemDTO, new ModelCallBack<CommonResponseDTO>(TAG) {
            @Override
            public void onSuccess(CommonResponseDTO response) {
                profileUserViewMgr.editUserInfoSuccess();
            }

            @Override
            public void onError( int errorCode, String error ) {
                profileUserViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void requestFollow(long userId, long followerUserId, final FollowItemDTO dto) {
        FollowDTO followDTO = new FollowDTO();
        followDTO.setUserId(userId);
        followDTO.setFollowUserId(followerUserId);
        tourModelMgr.requestFollow( followDTO, new ModelCallBack<CommonResponseDTO>(TAG) {
            @Override
            public void onSuccess(CommonResponseDTO response) {
                if (dto.getId()!= 0){
                    if (dto.getIsFriend()){
                        dto.setFriend(false);
                    } else {
                        dto.setFriend(true);
                    }
                    profileUserViewMgr.requestFollowSuccess(true);
                } else {
                    profileUserViewMgr.requestFollowSuccess(false);
                }
            }

            @Override
            public void onError( int errorCode, String error ) {
                profileUserViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void getInfoVipMember() {
        userModelMgr.getInfoVipMember(new ModelCallBack<VipMemberResponseDTO>(TAG) {
            @Override
            public void onSuccess(VipMemberResponseDTO response) {
                profileUserViewMgr.renderVipMemberInfo(response.getData());
            }

            @Override
            public void onError(int errorCode, String error) {
                profileUserViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void requestChangePassword(ChangePasswordDTO changePasswordDTO) {
        loginModelMgr.requestChangePassword(changePasswordDTO, new ModelCallBack<CommonResponseDTO>() {
            @Override
            public void onSuccess(CommonResponseDTO response) {
                profileUserViewMgr.renderPasswordSuccess();
            }

            @Override
            public void onError(int errorCode, String error) {
                profileUserViewMgr.showMessageErr(errorCode, error);
            }

        });
    }


}
