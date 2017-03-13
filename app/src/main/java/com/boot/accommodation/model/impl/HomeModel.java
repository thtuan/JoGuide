package com.boot.accommodation.model.impl;

import com.boot.accommodation.base.BaseModel;
import com.boot.accommodation.common.control.ProgressUpdateBody;
import com.boot.accommodation.common.info.ServerPath;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.response.AppVersionResponseDTO;
import com.boot.accommodation.dto.response.CategoryLocationReponseDTO;
import com.boot.accommodation.dto.response.CategoryResponseDTO;
import com.boot.accommodation.dto.response.CommonResponseDTO;
import com.boot.accommodation.dto.response.FamousLocationResponseDTO;
import com.boot.accommodation.dto.response.LocationFilterResponseDTO;
import com.boot.accommodation.dto.response.PhotoUploadResponseDTO;
import com.boot.accommodation.dto.response.PlaceResponseDTO;
import com.boot.accommodation.dto.response.StatisticResponseDTO;
import com.boot.accommodation.dto.response.TripResponseDTO;
import com.boot.accommodation.dto.view.CreateLocationDTO;
import com.boot.accommodation.dto.view.ItemTypeDTO;
import com.boot.accommodation.dto.view.LocationFilterItemDTO;
import com.boot.accommodation.dto.view.TimeToGoFilterDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.mgr.HomeModelMgr;
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
 * Model cua man hinh Home
 *
 * @author tuanlt
 * @since: 10:50 AM 5/5/2016
 */
public class HomeModel extends BaseModel implements HomeModelMgr {
    // API Lay dia diem cu the
    private interface Interface {
        @GET("m/tour/getAllTour")
        Call<TripResponseDTO> getAllTour(
            @Query("page") int page,
            @Header(XAPITOKEN) String token
        );

        @POST("search")
        Call<PlaceResponseDTO> getAllPlace(
            @Query("page") int page,
            @Query("lat") Double lat,
            @Query("lng") Double lng,
            @Body LocationFilterItemDTO locationFilterItemDTO );

        @GET("user/favourite/{itemType}/{itemId}")
        Call<CommonResponseDTO> requestFavourite(
                @Path("itemType") int itemType,
                @Path("itemId") long tourId,
                @Header(XAPITOKEN) String token
        );

        @GET("m/tour/statistics")
        Call<StatisticResponseDTO> getStatistics(
            @Query("date") String date,
            @Header(XAPITOKEN) String token
        );


        @GET("m/location/getFavouriteLocation")
        Call<PlaceResponseDTO> getMyFavouritePlace(
            @Query("page") int page,
            @Query("userId") String userId,
            @Header(XAPITOKEN) String token
        );

        @GET("m/tour/getMyTour")
        Call<TripResponseDTO> getMyTour(
                @Query("search") String search,
                @Query("page") int page,
                @Header(XAPITOKEN) String token
        );

        @GET("m/tour/getFavouriteTour")
        Call<TripResponseDTO> getFavouriteTour(
                @Query("search") String search,
                @Query("page") int page,
                @Query("userId") String userId,
                @Header(XAPITOKEN) String token
        );
        @GET("user/{userId}/tour")
        Call<TripResponseDTO> getCollection(
                @Path("userId") String adminId,
                @Query("search") String search,
                @Query("page") int page,
                @Header(XAPITOKEN) String token
        );

        @Multipart
        @POST
        Call<PhotoUploadResponseDTO> uploadFile(
                @Url String url,
                @Part("file\"; filename=\"pp.jpg") RequestBody image);

        @GET("m/location/{userId}/create/uploadPhoto")
        Call<CommonResponseDTO> requestUploadPhoto(
                @Path("userId") long userId,
                @Query("photo") String photo,
                @Header(XAPITOKEN) String token
        );

        @POST("location")
        Call<CommonResponseDTO> requestCreateLocation(
                @Body CreateLocationDTO createLocationDTO,
                @Header(XAPITOKEN) String token
        );

        @GET("m/location/category")
        Call<CategoryLocationReponseDTO> getCategory(
                @Header(XAPITOKEN) String token
        );

        @GET("m/location/filter")
        Call<LocationFilterResponseDTO> getInfoFilterPlace(
                @Header(XAPITOKEN) String token
        );

        @GET("m/location/famousInProvince")
        Call<FamousLocationResponseDTO> getFamousPlaceByProvince(
                @Query("provinceId") long provinceId,
                @Header(XAPITOKEN) String token
        );

        @GET("m/location/where-to-go-this-time")
        Call<PlaceResponseDTO> getWhereTimeToGo(
                @Query("page") int page,
                @Query("monthIds") String monthIds,
                @Header(XAPITOKEN) String token
        );

        @POST("m/location/where-to-go-this-time")
        Call<PlaceResponseDTO> getWhereTimeToGo(
                @Query("page") int page,
                @Body TimeToGoFilterDTO timeToGoFilterDTO
        );

        @GET("m/location/category/{categoryId}/child")
        Call<CategoryResponseDTO> getCategoryChild(
                @Path("categoryId") long categoryId,
                @Header(XAPITOKEN) String token
        );

        @GET("m/location/filter/areas")
        Call<FamousLocationResponseDTO> getFilterAreas(
                @Header(XAPITOKEN) String token
        );

        @GET("user/{userId}/location")
        Call<PlaceResponseDTO> getPlaceCreated(
                @Path("userId") String userId,
                @Query("page") int page,
                @Header(XAPITOKEN) String token
        );

        @GET("common/mobile/app-version/{typeMobile}")
        Call<AppVersionResponseDTO> getAppVersion(
                @Path("typeMobile") int typeMobile,
                @Header(XAPITOKEN) String token
        );
    }

    @Override
    public void getAllTour(int page, ModelCallBack modelCallBack ) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getAllTour(page, PreferenceUtils.getString(Constants.Preference
            .PREFERENCE_USER_TOKEN,
            ""));
        // goi request
        requestAPI(modelCallBack);
    }

    @Override
    public void getAllPlace(int page, Double lat, Double lng, LocationFilterItemDTO locationFilterItemDTO, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getAllPlace(page, lat, lng, locationFilterItemDTO);
        requestAPI(modelCallBack);
    }

    @Override
    public void requestFavouriteTour(int itemType,long itemId, ModelCallBack modelCallBack ) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestFavourite(itemType, itemId, PreferenceUtils.getString(Constants
                .Preference
                .PREFERENCE_USER_TOKEN, ""));
        requestAPI(modelCallBack);
    }

    @Override
    public void getStatistics(String date, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call =  service.getStatistics(date,PreferenceUtils.getString(Constants.Preference
                .PREFERENCE_USER_TOKEN, ""));
        requestAPI(modelCallBack);
    }

    @Override
    public void requestFavouritePlace(long placeId, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestFavourite(ItemTypeDTO.LOCATION.getValue(), placeId, PreferenceUtils.getString(Constants.Preference.PREFERENCE_USER_TOKEN, ""));
        requestAPI(modelCallBack);
    }

    @Override
    public void getFavouritePlace(int page, String userId, ModelCallBack modelCallBack ) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getMyFavouritePlace(page, userId, PreferenceUtils.getString(Constants.Preference
            .PREFERENCE_USER_TOKEN, ""));
        requestAPI(modelCallBack);
    }


    @Override
    public void getFavouriteTour(String userId, String search, int page, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getFavouriteTour(search,page, userId, PreferenceUtils.getString(Constants
                        .Preference
                        .PREFERENCE_USER_TOKEN,
                ""));
        //call request
        requestAPI(modelCallBack);
    }
    /**
     * get my tour
     *
     * @param userId        user id
     * @param search        search text
     * @param page          page 0 ->>
     * @param modelCallBack callback
     */
    @Override
    public void getMyTour(String userId, String search, int page, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getMyTour(search,page, PreferenceUtils.getString(Constants
                        .Preference
                        .PREFERENCE_USER_TOKEN,
                ""));
        //call request
        requestAPI(modelCallBack);
    }

    /**
     * getCollection
     * @param adminId
     * @param search
     * @param page
     * @param modelCallBack
     */
    @Override
    public void getCollection(String adminId, String search, int page, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getCollection(adminId, search, page,  PreferenceUtils.getString(Constants
                        .Preference
                        .PREFERENCE_USER_TOKEN,
                ""));
        //call request
        requestAPI(modelCallBack);
    }

    /**
     * upload photo on mobile
     * @param file
     * @param modelCallBack
     * @param uploadCallbacks
     * @param position
     */
    @Override
    public void uploadPhoto(File file, ModelCallBack modelCallBack, ProgressUpdateBody.MultiUploadCallbacks
            uploadCallbacks, int position) {
        Interface service = RETROFIT.create(Interface.class);
        ProgressUpdateBody requestFile = new ProgressUpdateBody(file, uploadCallbacks, position);
        call = service.uploadFile(ServerPath.getStaticPath(), requestFile);
        //call request
        requestAPI(modelCallBack);
    }

    /**
     * create location on mobile
     * @param createLocationDTO
     * @param modelCallBack
     */
    @Override
    public void requestCreateLocation(CreateLocationDTO createLocationDTO, ModelCallBack modelCallBack ) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestCreateLocation(createLocationDTO , PreferenceUtils.getString(Constants.Preference
                        .PREFERENCE_USER_TOKEN,
                ""));
        // goi request
        requestAPI(modelCallBack);
    }

    @Override
    public void getCategory(ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getCategory(PreferenceUtils.getString(Constants.Preference
                        .PREFERENCE_USER_TOKEN, ""));
        // goi request
        requestAPI(modelCallBack);
    }

    @Override
    public void getInfoFilterPlace(ModelCallBack modelCallBack){
        Interface service = RETROFIT.create(Interface.class);
        call = service.getInfoFilterPlace(PreferenceUtils.getString(Constants.Preference
                .PREFERENCE_USER_TOKEN, ""));
        // goi request
        requestAPI(modelCallBack);
    }

    @Override
    public void getFamousPlaceByProvince(long provinceId, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getFamousPlaceByProvince(provinceId, PreferenceUtils.getString(Constants.Preference
                .PREFERENCE_USER_TOKEN, ""));
        // goi request
        requestAPI(modelCallBack);
    }

    @Override
    public void getTimeToGoPlace(int page, TimeToGoFilterDTO timeToGoFilterDTO, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getWhereTimeToGo(page, timeToGoFilterDTO);
        requestAPI(modelCallBack);
    }

    @Override
    public void getCategoryChild(long parentCategoryId, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getCategoryChild(parentCategoryId, PreferenceUtils.getString(Constants.Preference
                .PREFERENCE_USER_TOKEN, ""));
        requestAPI(modelCallBack);
    }

    @Override
    public void getFilterAreas(ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getFilterAreas(PreferenceUtils.getString(Constants.Preference
                .PREFERENCE_USER_TOKEN, ""));
        // goi request
        requestAPI(modelCallBack);
    }

    @Override
    public void getPlaceCreated(String userId, int page, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getPlaceCreated(userId, page, PreferenceUtils.getString(Constants.Preference
                .PREFERENCE_USER_TOKEN, ""));
        // goi request
        requestAPI(modelCallBack);
    }

    @Override
    public void getAppVersion(int typeMobile, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getAppVersion(typeMobile, PreferenceUtils.getString(Constants.Preference
                .PREFERENCE_USER_TOKEN, ""));
        // goi request
        requestAPI(modelCallBack);
    }
}
