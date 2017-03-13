package com.boot.accommodation.vp.login;

import com.facebook.AccessToken;
import com.facebook.login.LoginResult;
import com.boot.accommodation.dto.view.LoginDTO;
import com.boot.accommodation.dto.view.UserItemDTO;

import org.json.JSONObject;

/**
 * View for Login
 *
 * @author tuanlt
 * @since: 5:43 PM 5/5/2016
 */
public interface LoginViewMgr {
    void connectFBSuccess( LoginResult loginResult );

    void connectFBCancel();

    void connectFBError();

    void requestProfileFBSuccess( AccessToken accessToken, JSONObject object );

    void showMessageErr( int errorCode, String message );

    /**
     * Request login
     */
    void requestLogin( LoginDTO loginDTO );

    /**
     * Save user info
     *
     * @param loginDTO
     * @param userDTO
     */
    void handleLoginSuccess( LoginDTO loginDTO, UserItemDTO userDTO );
}
