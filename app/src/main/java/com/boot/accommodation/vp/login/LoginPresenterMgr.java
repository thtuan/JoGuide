package com.boot.accommodation.vp.login;

import android.app.Activity;
import android.content.Intent;

import com.facebook.AccessToken;
import com.google.android.gms.common.api.GoogleApiClient;
import com.boot.accommodation.dto.view.LoginDTO;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Mo ta class
 *
 * @author tuanlt
 * @since: 5:46 PM 5/5/2016
 */
public interface LoginPresenterMgr {
    void initFacebook(Activity activity);
    void requestProfileFacebook(AccessToken accessToken);
    void getFacebookProfile(String token,JSONObject object) throws JSONException;
    void callFBActivityResult(int requestCode, int resultCode, Intent data);
    void getProfileGooglePlus( GoogleApiClient mGoogleApiClient, Intent data);
    void requestLogin( LoginDTO loginDTO);
}