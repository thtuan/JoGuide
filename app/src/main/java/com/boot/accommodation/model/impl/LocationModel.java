package com.boot.accommodation.model.impl;

import com.boot.accommodation.base.BaseModel;
import com.boot.accommodation.common.control.ProgressUpdateBody;
import com.boot.accommodation.common.info.ServerPath;
import com.boot.accommodation.dto.response.CommonResponseDTO;
import com.boot.accommodation.dto.response.DiscussResponseDTO;
import com.boot.accommodation.dto.response.FamousLocationResponseDTO;
import com.boot.accommodation.dto.response.PhotoUploadResponseDTO;
import com.boot.accommodation.dto.response.PlaceDetailPhotoResponseDTO;
import com.boot.accommodation.dto.response.PlaceDetailResponseDTO;
import com.boot.accommodation.dto.response.PlaceResponseDTO;
import com.boot.accommodation.dto.response.ReviewCreateResponseDTO;
import com.boot.accommodation.dto.response.ReviewLikeResponseDTO;
import com.boot.accommodation.dto.response.TimeToGoResponseDTO;
import com.boot.accommodation.dto.view.CategoryTypeDTO;
import com.boot.accommodation.dto.view.GPSInfoDTO;
import com.boot.accommodation.dto.view.ItemTypeDTO;
import com.boot.accommodation.dto.view.MediaDTO;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.dto.view.PlaceReportDTO;
import com.boot.accommodation.dto.view.ReviewItemDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.mgr.LocationModelMgr;

import java.io.File;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * LocationModel
 *
 * @author tuanlt
 * @since 10:26 AM 12/29/16
 */
public class LocationModel extends BaseModel implements LocationModelMgr {

    // API search
    private interface Interface {
        @GET("common/category/{categoryType}")
        Call<TimeToGoResponseDTO> getMonth(
                @Path("categoryType") int categoryType
        );

        @GET("m/location/{id}")
        Call<PlaceDetailResponseDTO> getPlaceInfo(
                @Path("id") long id
        );

        @GET("m/location/{location_id}/photo")
        Call<PlaceDetailPhotoResponseDTO> getPlacePhoto(
                @Path("location_id") long id,
                @Query("typePhoto") int typePhoto,
                @Query("page") int page
        );

        /**
         * Get review
         *
         * @param page
         * @return
         */
        @GET("m/location/{locationId}/review")
        Call<DiscussResponseDTO> getReview(
                @Path("locationId") long locationId,
                @Query("page") int page
        );

        @GET("m/location/review/{reviewId}/like")
        Call<ReviewLikeResponseDTO> requestLikeReview(
                @Path("reviewId") long reviewId
        );

        @POST("m/location/{locationId}/review")
        Call<ReviewCreateResponseDTO> requestReviewPlace(
                @Path("locationId") long locationId,
                @Body ReviewItemDTO reviewItemDTO
        );

        @GET("user/favourite/{itemType}/{itemId}")
        Call<CommonResponseDTO> requestFavourite(
                @Path("itemType") long itemType,
                @Path("itemId") long tourId
        );

        @GET("user/checkin/{userId}/{placeId}/{lat}/{lng}")
        Call<CommonResponseDTO> requestCheckin(
                @Path("placeId") String userId,
                @Path("placeId") long placeId,
                @Path("placeId") double lat,
                @Path("placeId") double lng
        );

        /**
         * get place for checkin
         *
         * @param page  page ==
         * @param lat   latitude
         * @param lng   longitude
         * @return
         */
        @GET("m/location/getAllLocation")
        Call<PlaceResponseDTO> getAllPlaceCheckIn(
                @Query("page") int page,
                @Query("lag") double lat,
                @Query("lng") double lng,
                @Query("pageSize") int pageSize);

        /**
         * create check in location
         *
         * @param placeItemDTO
         * @return
         */
        @POST("user/checkin")
        Call<PlaceResponseDTO> createCheckInlocation(
                @Body PlaceItemDTO placeItemDTO);

        @Multipart
        @POST
        Call<PhotoUploadResponseDTO> uploadFile(
                @Url String url,
//            @Part("") MultipartBody.Part file,
                @Part("file\"; filename=\"pp.jpg") RequestBody image);

        //create rating for Tour
        @GET("review/{reviewId}/delete")
        Call<CommonResponseDTO> deleteReivew(
                @Path("reviewId") long reviewId
        );

        @POST("location/{locationId}/photo")
        Call<PhotoUploadResponseDTO> requestUploadPhoto(
                @Path("locationId") long locationId,
                @Body List<MediaDTO> mediaDTOs
        );

        @POST("location/{locationId}/report")
        Call<CommonResponseDTO> requestReportLocation(
                @Path("locationId") long locationId,
                @Body PlaceReportDTO placeReportDTO
        );

        @GET("m/location/filter/areas")
        Call<FamousLocationResponseDTO> getFilterAreas();
        @GET("area/special")
        Call<FamousLocationResponseDTO> getAreaSpecial();

    }

    @Override
    public void getMonth(ModelCallBack callBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getMonth(CategoryTypeDTO.LOCATION_BEST_IN_TIME.getValue());
        requestAPI(callBack);
    }

    /**
     * update favourite
     *
     * @param placeId
     * @param modelCallBack
     */
    @Override
    public void requestFavouritePlace(long placeId, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestFavourite(ItemTypeDTO.LOCATION.getValue(), placeId);
        requestAPI(modelCallBack);
    }

    @Override
    public void requestCheckin(String userId, long placeId, GPSInfoDTO locationInfo, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestCheckin(userId, placeId, locationInfo.getLatitude(), locationInfo.getLongtitude());
        requestAPI(modelCallBack);
    }

    @Override
    public void getAllPlaceCheckIn(int page, double lat, double lng, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getAllPlaceCheckIn(page, lat, lng, 5);
        requestAPI(modelCallBack);
    }

    /**
     * create location check in
     *
     * @param placeItemDTO  placeItemDTO
     * @param modelCallBack modelCallBack
     */
    @Override
    public void createLocationCheckIn(PlaceItemDTO placeItemDTO, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.createCheckInlocation(placeItemDTO);
        requestAPI(modelCallBack);
    }

    @Override
    public void uploadPhoto(File file, ModelCallBack modelCallBack, ProgressUpdateBody.UploadCallbacks uploadCallbacks) {
        Interface service =
                RETROFIT.create(Interface.class);
        ProgressUpdateBody requestFile = new ProgressUpdateBody(file, uploadCallbacks);
        call = service.uploadFile(ServerPath.getStaticPath(), requestFile);
        //call request
        requestAPI(modelCallBack);
    }

    @Override
    public void getPlaceDetail(long placeId, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getPlaceInfo(placeId);
        // goi request
        requestAPI(modelCallBack);
    }

    @Override
    public void getReview(long placeId, int page, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getReview(placeId, page);
        // goi request
        requestAPI(modelCallBack);
    }

    @Override
    public void requestReviewPlace(ReviewItemDTO myReview, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestReviewPlace(myReview.getItemId(), myReview);
        // goi request
        requestAPI(modelCallBack);
    }

    @Override
    public void requestLikeReview(long reviewId, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestLikeReview(reviewId);
        // goi request
        requestAPI(modelCallBack);
    }

    /**
     * Get place photo
     *
     * @param placeId
     * @param typePhoto
     * @param page
     * @param modelCallBack
     */
    public void getPlacePhoto(long placeId, int typePhoto, int page, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getPlacePhoto(placeId, typePhoto, page);
        // goi request
        requestAPI(modelCallBack);
    }

    @Override
    public void deleteComment(long itemId, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.deleteReivew(itemId);
        //call request
        requestAPI(modelCallBack);
    }

    @Override
    public void requestUploadLocationPhoto(long placeId, List<MediaDTO> mediaDTOs, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestUploadPhoto(placeId, mediaDTOs);
        // goi request
        requestAPI(modelCallBack);
    }

    @Override
    public void requestReportLocation(long placeId, PlaceReportDTO placeReportDTO, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestReportLocation(placeId, placeReportDTO);
        // goi request
        requestAPI(modelCallBack);
    }

    @Override
    public void getFilterAreas(ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getFilterAreas();
        // goi request
        requestAPI(modelCallBack);
    }

    @Override
    public void getAreaSpecial(ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getAreaSpecial();
        // goi request
        requestAPI(modelCallBack);
    }
}
