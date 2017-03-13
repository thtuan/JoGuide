package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.response.CommonResponseDTO;
import com.boot.accommodation.dto.response.FollowUserResponseDTO;
import com.boot.accommodation.dto.view.FollowDTO;
import com.boot.accommodation.dto.view.FollowItemDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.UserModel;
import com.boot.accommodation.model.mgr.UserModelMgr;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * FollowPresenter
 *
 * @author thtuan
 * @since 12:51 PM 26-09-2016
 */
public class FollowPresenter implements FollowPresenterMgr {
    FollowViewMgr followViewMgr;
    UserModelMgr userModelMgr;
    private String TAG = ""; // Tag
    List<FollowItemDTO> lstFollow = new ArrayList<>(); // list follower

    public FollowPresenter(FollowViewMgr followViewMgr) {
        this.followViewMgr = followViewMgr;
        this.userModelMgr = new UserModel();
        TAG = Utils.getTag(followViewMgr.getClass());
    }

    @Override
    public void getFollower(long userId) {
        userModelMgr.getFollower(userId, new ModelCallBack<FollowUserResponseDTO>() {
            @Override
            public void onSuccess(FollowUserResponseDTO response) {
                getProfileSuccess(response);
            }

            @Override
            public void onError(int errorCode, String error) {

            }

        });
    }

    @Override
    public void getFollowing(long userId) {
        userModelMgr.getFollowing(userId, new ModelCallBack<FollowUserResponseDTO>() {
            @Override
            public void onSuccess(FollowUserResponseDTO response) {
                getProfileSuccess(response);
            }

            @Override
            public void onError(int errorCode, String error) {

            }

        });
    }

    @Override
    public void requestFollow(long userId, long followerUserId, final FollowItemDTO dto) {
        FollowDTO followDTO = new FollowDTO();
        followDTO.setUserId(userId);
        followDTO.setFollowUserId(followerUserId);
        userModelMgr.requestFollow( followDTO, new ModelCallBack<CommonResponseDTO>(TAG) {
            @Override
            public void onSuccess(CommonResponseDTO response) {
                if (dto.getId()!= 0){
                    if (dto.getIsFriend()){
                        dto.setFriend(false);
                    } else {
                        dto.setFriend(true);
                    }
                    followViewMgr.renderLayout(lstFollow);
                    followViewMgr.requestFollowSuccess(true);
                } else {
                    followViewMgr.requestFollowSuccess(false);
                }
            }

            @Override
            public void onError( int errorCode, String error ) {
                followViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    /**
     *  Xu li lay tourist info thanh cong
     */
    private void getProfileSuccess(FollowUserResponseDTO response) {
        lstFollow.clear();
        lstFollow.addAll(response.getData().getFollower());
        followViewMgr.renderLayout(lstFollow);
    }

}
