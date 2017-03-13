package com.boot.accommodation.model.impl;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.boot.accommodation.base.BaseModel;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.response.CommonResponseDTO;
import com.boot.accommodation.dto.response.LoginResponseDTO;
import com.boot.accommodation.dto.view.ChangePasswordDTO;
import com.boot.accommodation.dto.view.LoginDTO;
import com.boot.accommodation.dto.view.LoginJocoDTO;
import com.boot.accommodation.dto.view.LoginTypeDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.mgr.LoginModelMgr;
import com.boot.accommodation.util.PreferenceUtils;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by Administrator on 4/28/2016.
 */
public class LoginModel extends BaseModel implements LoginModelMgr {

    private interface Interface {
        @POST("user/log-me-in")
        Call<LoginResponseDTO> requestLogin(
                @Body LoginJocoDTO loginDTO
        );

        @POST("user/log-me-in/facebook")
        Call<LoginResponseDTO> requestLoginFB(
            @Body LoginDTO loginDTO
        );

        @POST("user/log-me-in/google")
        Call<LoginResponseDTO> requestLoginGG(
            @Body LoginDTO loginDTO
        );

        @POST("user/signup")
        Call<LoginResponseDTO> requestSignUp(
                @Body LoginDTO userModel
        );

        @POST("user/forget-password")
        Call<CommonResponseDTO> requestEmail(
                @Body LoginDTO model
        );
        @POST("user/password/change")
        Call<CommonResponseDTO> requestChangePass(
                @Body ChangePasswordDTO changePasswordDTO,
                @Header(XAPITOKEN) String token
        );
        @GET
        public Call<ResponseBody> requestValidateGPlus( @Url String url );
    }

    @Override
    public void requestProfileFacebook(AccessToken accessToken, final FBCallBack callBack) {
        GraphRequest request = GraphRequest.newMeRequest(
            accessToken,
            new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
//                    loginViewMgr.requestProfileFBSuccess(object);
                    callBack.onCompleted(object,response);
                }
            });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,picture,email,first_name,last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void requestValidateGPlus( String token, ModelCallBack modelCallBack ) {
        Interface service = RETROFIT.create(Interface.class);
        call =  service.requestValidateGPlus("https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=" + token);
        requestAPI(modelCallBack);
    }

    @Override
    public void requestLogin( LoginDTO loginDTO, int typeLogin, ModelCallBack modelCallBack ) {
        Interface service = RETROFIT.create(Interface.class);
        if(loginDTO.getLoginType() == LoginTypeDTO.NORMAL.getValue()) {
            LoginJocoDTO loginJocoDTO = new LoginJocoDTO();
            loginJocoDTO.setEmail(loginDTO.getEmail());
            loginJocoDTO.setPhone(loginDTO.getPhone());
            loginJocoDTO.setPassword(loginDTO.getPassword());
            call = service.requestLogin(loginJocoDTO);
        }else if(loginDTO.getLoginType() == LoginTypeDTO.FACEBOOK.getValue()){
            call = service.requestLoginFB(loginDTO);
        }else{
            call = service.requestLoginGG(loginDTO);
        }
        requestAPI(modelCallBack);
    }

    @Override
    public void requestSignUp(LoginDTO userModel, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestSignUp(userModel);
        requestAPI(modelCallBack);
    }

    @Override
    public void requestEmailGetPass(LoginDTO model, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestEmail(model);
        requestAPI(modelCallBack);
    }

    @Override
    public void requestChangePassword(ChangePasswordDTO changePasswordDTO, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestChangePass(changePasswordDTO, PreferenceUtils.getString(Constants
                        .Preference.PREFERENCE_USER_TOKEN, ""));
        requestAPI(modelCallBack);
    }
}
