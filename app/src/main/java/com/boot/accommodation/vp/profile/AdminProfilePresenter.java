package com.boot.accommodation.vp.profile;

import com.boot.accommodation.dto.response.AdminProfileResponseDTO;
import com.boot.accommodation.dto.response.CommonResponseDTO;
import com.boot.accommodation.dto.view.FollowDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.ProfileModel;
import com.boot.accommodation.model.mgr.ProfileModelMgr;
import com.boot.accommodation.util.Utils;

/**
 * Presenter for admin
 *
 * @author vuongbv
 * @since 6:47 PM 7/14/2016
 */
public class AdminProfilePresenter implements AdminProfilePresenterMgr {
    private String TAG = ""; // Tag
    private AdminProfileViewMgr adminProfileViewMgr; // view
    private ProfileModelMgr profileModelMgr;//tour model : call method follow admin

    public AdminProfilePresenter(AdminProfileViewMgr adminProfileViewMgr) {
        this.adminProfileViewMgr = adminProfileViewMgr;
        profileModelMgr = new ProfileModel();
        TAG = Utils.getTag(adminProfileViewMgr.getClass());
    }

    @Override
    public void getAdminProfile(long id) {
        profileModelMgr.getAdminProfile(id, new ModelCallBack<AdminProfileResponseDTO>(TAG) {
            @Override
            public void onSuccess(AdminProfileResponseDTO response) {
                adminProfileViewMgr.renderLayout(response.getData());
            }

            @Override
            public void onError( int errorCode, String error ) {
                adminProfileViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void followAdmin(long id, long idUserFollow) {
        FollowDTO followDTO = new FollowDTO();
        followDTO.setUserId(id);
        followDTO.setFollowUserId(idUserFollow);
        profileModelMgr.requestFollow(followDTO, new ModelCallBack<CommonResponseDTO>(TAG) {
            @Override
            public void onSuccess(CommonResponseDTO response) {
                adminProfileViewMgr.requestFollowSuccess();
            }

            @Override
            public void onError( int errorCode, String error ) {
                adminProfileViewMgr.showMessageErr(errorCode, error);
            }

        });
    }
}
