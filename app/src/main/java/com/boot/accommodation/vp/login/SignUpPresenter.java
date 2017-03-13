package com.boot.accommodation.vp.login;

import com.facebook.CallbackManager;
import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.response.CommonResponseDTO;
import com.boot.accommodation.dto.response.LoginResponseDTO;
import com.boot.accommodation.dto.view.LoginDTO;
import com.boot.accommodation.dto.view.UserItemDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.LoginModel;
import com.boot.accommodation.model.mgr.LoginModelMgr;
import com.boot.accommodation.util.PreferenceUtils;
import com.boot.accommodation.util.Utils;

/**
 * Mo ta class
 *
 * @author Vuong-bv
 * @since: 6/24/2016
 */
public class SignUpPresenter implements SignUpPresenterMgr {
    CallbackManager mCallbackManagerFacebook;
    private SignUpViewMgr signUpViewMgr;
    private LoginModelMgr loginModelMgr;
    private String TAG = ""; // Tag

    public SignUpPresenter(SignUpViewMgr signUpViewMgr) {
        this.signUpViewMgr = signUpViewMgr;
        loginModelMgr = new LoginModel();
        TAG = Utils.getTag(signUpViewMgr.getClass());
    }

    @Override
    public void requestInfoSignUp(final LoginDTO loginDTO) {
        loginModelMgr.requestSignUp(loginDTO, new ModelCallBack<LoginResponseDTO>(TAG) {
            @Override
            public void onSuccess(LoginResponseDTO response) {
//                saveUserInfo(response.getData());
                signUpViewMgr.onSignUpSuccess(loginDTO, response.getData());
            }

            @Override
            public void onError( int errorCode, String error ) {
                signUpViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void requestGetPassword( LoginDTO loginDTO) {
        loginModelMgr.requestEmailGetPass(loginDTO, new ModelCallBack<CommonResponseDTO>(TAG) {
            @Override
            public void onSuccess(CommonResponseDTO response) {
                signUpViewMgr.requestEmailSuccess();
            }

            @Override
            public void onError( int errorCode, String error ) {
                signUpViewMgr.showMessageErr(errorCode,error);
            }

        });
    }

    /**
     * save user info to joco app
     * @param userDTO
     */
    private void saveUserInfo(UserItemDTO userDTO){
        JoCoApplication.getInstance().getProfile().setUserData(userDTO);
        PreferenceUtils.saveString(Constants.Preference.PREFERENCE_USER_ID, userDTO.getId());
        PreferenceUtils.saveString(Constants.Preference.PREFERENCE_USER_FULL_NAME, userDTO.getFullName());
        PreferenceUtils.saveString(Constants.Preference.PREFERENCE_USER_PICTURE, userDTO.getAvatar());
        PreferenceUtils.saveInt(Constants.Preference.PREFERENCE_USER_TYPE, userDTO.getUserType());
        PreferenceUtils.saveString(Constants.Preference.PREFERENCE_USER_PHONE, userDTO.getPhone());
        PreferenceUtils.saveString(Constants.Preference.PREFERENCE_USER_STATUS, userDTO.getStatus());
        PreferenceUtils.saveString(Constants.Preference.PREFERENCE_USER_TOKEN, userDTO.getToken());
        PreferenceUtils.saveInt(Constants.Preference.PREFERENCE_LOGIN_TYPE, userDTO.getLoginType());
        PreferenceUtils.saveString(Constants.Preference.PREFERENCE_USER_EMAIL, userDTO.getEmail());
        PreferenceUtils.saveString(Constants.Preference.PREFERENCE_USER_FIRST_NAME, userDTO.getFirstName());
        PreferenceUtils.saveString(Constants.Preference.PREFERENCE_USER_LAST_NAME, userDTO.getEmail());
    }
}
