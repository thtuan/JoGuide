package com.boot.accommodation.vp.login;

import android.app.Activity;
import android.content.Intent;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.response.LoginResponseDTO;
import com.boot.accommodation.dto.view.LoginDTO;
import com.boot.accommodation.dto.view.LoginTypeDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.LoginModel;
import com.boot.accommodation.model.mgr.LoginModelMgr;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * Mo ta class
 *
 * @author tuanlt
 * @since: 5:52 PM 5/5/2016
 */
public class LoginPresenter implements LoginPresenterMgr {

    CallbackManager mCallbackManagerFacebook;
    private LoginViewMgr loginViewMgr;
    private LoginModelMgr loginModelMgr;
    private String TAG = ""; // Tag

    public LoginPresenter(LoginViewMgr loginViewMgr){
        this.loginViewMgr = loginViewMgr;
        loginModelMgr = new LoginModel();
        mCallbackManagerFacebook = CallbackManager.Factory.create();
        TAG = Utils.getTag(loginViewMgr.getClass());
    }

    @Override
    public void initFacebook(Activity activity) {
        LoginManager login = LoginManager.getInstance();
        login.registerCallback(mCallbackManagerFacebook,
            new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    loginViewMgr.connectFBSuccess(loginResult);
                }

                @Override
                public void onCancel() {
                    loginViewMgr.connectFBCancel();
                }

                @Override
                public void onError(FacebookException e) {
                    loginViewMgr.connectFBCancel();
                }
            });
    }

    @Override
    public void requestProfileFacebook(final AccessToken accessToken) {
        loginModelMgr.requestProfileFacebook(accessToken, new LoginModelMgr.FBCallBack() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                loginViewMgr.requestProfileFBSuccess(accessToken,object);
            }
        });
    }

    @Override
    public void getFacebookProfile( String token,JSONObject object) throws JSONException {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserId(object.getString("id"));
        if(!object.isNull("first_name")) {
            loginDTO.setFirstName(object.getString("first_name"));
        }
        if(!object.isNull("last_name")) {
            loginDTO.setLastName(object.getString("last_name"));
        }
        loginDTO.setAvatarUrl("https://graph.facebook.com/" + object.getString("id") + "/picture?type=normal");
        if(!object.isNull("email")) {
            loginDTO.setEmail(object.getString("email"));
        }
        loginDTO.setToken(token);
        loginDTO.setLoginType(LoginTypeDTO.FACEBOOK.getValue());
        loginViewMgr.requestLogin(loginDTO);
    }

    @Override
    public void callFBActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManagerFacebook.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void getProfileGooglePlus( GoogleApiClient mGoogleApiClient, Intent data) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if (result.isSuccess()) {
            GoogleSignInAccount acc = result.getSignInAccount();
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setUserId(acc.getId());
            if (!StringUtil.isNullOrEmpty(acc.getDisplayName())) {
                loginDTO.setFirstName(acc.getDisplayName().split(Constants.STR_SPACE)[0].trim());
                loginDTO.setLastName(acc.getDisplayName().substring(loginDTO.getFirstName().length(), acc.getDisplayName()
                    .length()).trim());
            }
            loginDTO.setAvatarUrl(acc.getPhotoUrl() != null ? acc.getPhotoUrl().toString() : "");
            loginDTO.setEmail(acc.getEmail());
//            loginDTO.setUserName(acc.getEmail());
            loginDTO.setToken(acc.getIdToken());
            loginDTO.setLoginType(LoginTypeDTO.GOOGLE.getValue());
            loginViewMgr.requestLogin(loginDTO);
        } else {
        }
    }

    @Override
    public void requestLogin( final LoginDTO loginDTO ) {
        loginDTO.setLocate(Locale.getDefault().getLanguage());
        loginModelMgr.requestLogin(loginDTO, loginDTO.getLoginType(), new ModelCallBack<LoginResponseDTO>(TAG) {
            @Override
            public void onSuccess( LoginResponseDTO response ) {
                loginViewMgr.handleLoginSuccess(loginDTO, response.getData());
            }

            @Override
            public void onError( int errorCode, String error ) {
                loginViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

}
