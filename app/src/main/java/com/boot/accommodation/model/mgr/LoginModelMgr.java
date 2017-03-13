package com.boot.accommodation.model.mgr;

import com.facebook.AccessToken;
import com.facebook.GraphResponse;
import com.boot.accommodation.dto.view.ChangePasswordDTO;
import com.boot.accommodation.dto.view.LoginDTO;
import com.boot.accommodation.listener.ModelCallBack;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/28/2016.
 */
public interface LoginModelMgr {
    interface FBCallBack{
        void onCompleted(JSONObject object, GraphResponse response);
    }
    void requestProfileFacebook(AccessToken accessToken,FBCallBack callBack);

    /**
     * Validate token facebook
     * @param token
     * @param modelCallBack
     */
    void requestValidateGPlus(String token, ModelCallBack modelCallBack);
    /**
     * Request login
     * @param loginDTO
     * @param loginType
     * @param modelCallBack
     */
    void requestLogin( LoginDTO loginDTO, int loginType, ModelCallBack modelCallBack );

    /**
     * Request info to signup
     * @param loginDTO
     * @param modelCallBack
     */
    void requestSignUp( LoginDTO loginDTO, ModelCallBack modelCallBack );

    /**
     * request email to server
     * @param loginDTO
     * @param modelCallBack
     */
    void requestEmailGetPass(  LoginDTO loginDTO, ModelCallBack modelCallBack );

    /**
     * request change password
     * changePasswordDTO
     */
    void requestChangePassword(ChangePasswordDTO changePasswordDTO, ModelCallBack modelCallBack);
}
