package com.boot.accommodation.vp.home;

import com.boot.accommodation.dto.response.CommonResponseDTO;
import com.boot.accommodation.dto.view.ChangePasswordDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.LoginModel;
import com.boot.accommodation.model.mgr.LoginModelMgr;

/**
 * SettingPresenter
 *
 * @author thtuan
 * @since 4:25 PM 06-09-2016
 */
public class SettingPresenter implements SettingPresenterMgr {
    private SettingActivityMgr settingActivityMgr;
    private LoginModelMgr loginModelMgr;
    public SettingPresenter(SettingActivityMgr settingActivityMgr) {
        this.settingActivityMgr = settingActivityMgr;
        loginModelMgr = new LoginModel();
    }

    @Override
    public void setNewPassword(ChangePasswordDTO changePasswordDTO) {
        loginModelMgr.requestChangePassword(changePasswordDTO, new ModelCallBack<CommonResponseDTO>() {
            @Override
            public void onSuccess(CommonResponseDTO response) {
                settingActivityMgr.changePasswordSuccess((Boolean)response.getData());
            }

            @Override
            public void onError(int errorCode, String error) {

            }

        });
    }
}
