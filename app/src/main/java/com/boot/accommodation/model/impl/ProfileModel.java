package com.boot.accommodation.model.impl;

import com.boot.accommodation.base.BaseModel;
import com.boot.accommodation.common.control.ProgressUpdateBody;
import com.boot.accommodation.common.info.ServerPath;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.response.AdminProfileResponseDTO;
import com.boot.accommodation.dto.response.CommonResponseDTO;
import com.boot.accommodation.dto.response.PhotoUploadResponseDTO;
import com.boot.accommodation.dto.response.ProfileCollectionResponseDTO;
import com.boot.accommodation.dto.response.ProfileUserResponseDTO;
import com.boot.accommodation.dto.response.TripResponseDTO;
import com.boot.accommodation.dto.view.FollowDTO;
import com.boot.accommodation.dto.view.UserItemDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.mgr.ProfileModelMgr;
import com.boot.accommodation.util.PreferenceUtils;

import java.io.File;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Model of profile
 *
 * @author tuanlt
 * @since 3:42 PM 7/11/2016
 */
public class ProfileModel extends BaseModel implements ProfileModelMgr {
    private interface Interface {
        /**
         * Get profile collection
         *
         * @param userId
         * @param token
         * @return
         */
        @GET("user/{userId}/collection")
        Call<ProfileCollectionResponseDTO> getProfileCollection(
            @Path("userId") long userId,
            @Header(XAPITOKEN) String token
        );

        @GET("user/{adminId}/adminCollection")
        Call<TripResponseDTO> getAdminCollection(
            @Path("adminId") long adminId,
            @Query("page") int page,
            @Header(XAPITOKEN) String token
        );


        //Edit profile info of tourist
        @POST("user/profile/edit")
        Call<CommonResponseDTO> requestEditProfile(
            @Body UserItemDTO userItemDTO,
            @Header(XAPITOKEN) String token
        );

        //interface get profile user
        @GET("user/{userId}/profile")
        Call<ProfileUserResponseDTO> getProfileUser(
            @Path("userId") long userId,
            @Header(XAPITOKEN) String token
        );

        /**
         * Request follow user
         * @return
         */
        @POST("user/follow")
        Call<CommonResponseDTO> requestFollow(
            @Body FollowDTO followUserModel,
            @Header(XAPITOKEN) String token
        );

        @GET("user/admin/{adminId}/profile")
        Call<AdminProfileResponseDTO> getAdminProfile(
            @Path("adminId") long id,
            @Header(XAPITOKEN) String token
        );

        @Multipart
        @POST
        Call<PhotoUploadResponseDTO> uploadFile(
            @Url String url,
//            @Part("") MultipartBody.Part file,
            @Part("file\"; filename=\"pp.png") RequestBody image);

        @GET("user/{userId}/profile/uploadPhoto")
        Call<CommonResponseDTO> requestUploadPhoto(
            @Path("userId") long userId,
            @Query("photo") String photo,
            @Header(XAPITOKEN) String token
        );
    }

    @Override
    public void getProfileCollection( long userId, ModelCallBack modelCallBack ) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getProfileCollection(userId, PreferenceUtils.getString(Constants.Preference
            .PREFERENCE_USER_TOKEN, ""));
        requestAPI(modelCallBack);
    }

    @Override
    public void getAdminCollection( long adminId, int page, ModelCallBack modelCallBack ) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getAdminCollection(adminId, page, PreferenceUtils.getString(Constants.Preference
            .PREFERENCE_USER_TOKEN, ""));
        requestAPI(modelCallBack);
    }

    @Override
    public void requestEditProfileInfo( UserItemDTO userItemDTO, ModelCallBack modelCallBack ) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestEditProfile(userItemDTO, PreferenceUtils.getString(Constants.Preference
            .PREFERENCE_USER_TOKEN, ""));
        requestAPI(modelCallBack);
    }

    @Override
    public void getProfileUser(long userId, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getProfileUser(userId, PreferenceUtils.getString(Constants.Preference
            .PREFERENCE_USER_TOKEN, ""));
        // goi request
        requestAPI(modelCallBack);
    }

    @Override
    public void requestFollow( FollowDTO followUserModel, ModelCallBack modelCallBack ) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestFollow(followUserModel, PreferenceUtils.getString(Constants
            .Preference
            .PREFERENCE_USER_TOKEN, ""));
        requestAPI(modelCallBack);
    }

    @Override
    public void getAdminProfile(long id, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getAdminProfile(id, PreferenceUtils.getString(Constants.Preference.PREFERENCE_USER_TOKEN,
            ""));
        // goi request
        requestAPI(modelCallBack);
    }

    @Override
    public void uploadPhoto(File file, ModelCallBack modelCallBack, ProgressUpdateBody.UploadCallbacks uploadCallbacks) {
        Interface service = RETROFIT.create(Interface.class);
        ProgressUpdateBody requestFile = new ProgressUpdateBody(file, uploadCallbacks);
        call = service.uploadFile(ServerPath.getStaticPath(), requestFile);
        //call request
        requestAPI(modelCallBack);
    }

    @Override
    public void requestUploadPhoto( long userId, String photo, ModelCallBack modelCallBack ) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestUploadPhoto(userId,photo, PreferenceUtils.getString(Constants.Preference
            .PREFERENCE_USER_TOKEN,
            ""));
        // goi request
        requestAPI(modelCallBack);
    }

}
