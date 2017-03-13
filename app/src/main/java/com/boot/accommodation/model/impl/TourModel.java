package com.boot.accommodation.model.impl;

import com.boot.accommodation.base.BaseModel;
import com.boot.accommodation.common.control.ProgressUpdateBody;
import com.boot.accommodation.common.info.ServerPath;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.response.CommonResponseDTO;
import com.boot.accommodation.dto.response.DiscussResponseDTO;
import com.boot.accommodation.dto.response.FeedbackResponseDTO;
import com.boot.accommodation.dto.response.ListTourResponseDTO;
import com.boot.accommodation.dto.response.PhotoUploadResponseDTO;
import com.boot.accommodation.dto.response.ReviewCreateResponseDTO;
import com.boot.accommodation.dto.response.ReviewLikeResponseDTO;
import com.boot.accommodation.dto.response.SearchLocationResponseDTO;
import com.boot.accommodation.dto.response.SearchTourResponseDTO;
import com.boot.accommodation.dto.response.TourInfomationResponseDTO;
import com.boot.accommodation.dto.response.TourInviteResponseDTO;
import com.boot.accommodation.dto.response.TourItineraryResponseDTO;
import com.boot.accommodation.dto.response.TourNotificationResponseDTO;
import com.boot.accommodation.dto.response.TourPlacePictureResponseDTO;
import com.boot.accommodation.dto.response.TourPlanResponseDTO;
import com.boot.accommodation.dto.response.TourRelateResponseDTO;
import com.boot.accommodation.dto.response.TourVoteResponse;
import com.boot.accommodation.dto.response.TouristInfoResponseDTO;
import com.boot.accommodation.dto.response.TripResponseDTO;
import com.boot.accommodation.dto.view.LeaderRatedDTO;
import com.boot.accommodation.dto.view.LocationFilterItemDTO;
import com.boot.accommodation.dto.view.ReviewItemDTO;
import com.boot.accommodation.dto.view.ScheduleDTO;
import com.boot.accommodation.dto.view.TourVoteDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.mgr.TourModelMgr;
import com.boot.accommodation.util.PreferenceUtils;

import java.io.File;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Model cho tour
 *
 * @author tuanlt
 * @since: 11:51 PM 5/12/2016
 */
public class TourModel extends BaseModel implements TourModelMgr {
    private interface Interface {

        //interface get information tour for expand tour info
        @GET("m/tour/{tourId}/plan/{tourPlanId}/information")
        Call<TourInfomationResponseDTO> getTourInfomation(
                @Path("tourPlanId") long tourPlanId,
                @Path("tourId") long tourId,
                @Header(XAPITOKEN) String token
        );

        // interface for tourist info
        @GET("tourist/getTouristInfo/{tourId}/{page}")
        Call<TouristInfoResponseDTO> getTouristInfo(
                @Path("tourId") String tourId,
                @Path("page") int page,
                @Header(XAPITOKEN) String token
        );

        // interface for leader rated
        @GET("tourist/getLeaderRated/{page}")
        Call<LeaderRatedDTO> getLeaderRated(
                @Path("tourId") long tourId,
                @Path("touristId") long touristId,
                @Path("page") int page,
                @Header(XAPITOKEN) String token
        );

        // interface for tour rated
        @GET("m/tour/{tourId}/review")
        Call<DiscussResponseDTO> getTourDiscuss(
                @Path("tourId") long tourId,
                @Query("page") int page,
                @Header(XAPITOKEN) String token
        );

        //create rating for leader
        @POST("tourist/setRatting/{page}")
        Call<LeaderRatedDTO> ratingLeader(
                @Field("tourId") String tourId,
                @Field("touristId") String touristId,
                @Body LeaderRatedDTO leaderRatedDTO,
                @Header(XAPITOKEN) String token
        );

        //create rating for Tour
        @POST("m/tour/{tourId}/review")
        Call<ReviewCreateResponseDTO> requestDiscuss(
                @Path("tourId") long tourId,
                @Body ReviewItemDTO discussDTO,
                @Header(XAPITOKEN) String token
        );

        //create rating for Tour
        @GET("review/{reviewId}/delete")
        Call<CommonResponseDTO> deleteReivew(
                @Path("reviewId") long reviewId,
                @Header(XAPITOKEN) String token
        );

        //like this comment of leader
        @GET("user/review/{itemId}/like")
        Call<ReviewLikeResponseDTO> requestLikeReviewLeader(
                @Path("itemId") long reviewId,
                @Header(XAPITOKEN) String token
        );

        //Search Tour

        @GET("m/tour/search")
        Call<SearchTourResponseDTO> getSearchTour(
                @Query("type") int type,
                @Query("page") int page,
                @Query("search") String search,
                @Query("date") String date,
                @Header(XAPITOKEN) String token
        );

        @POST("m/location/search")
        Call<SearchLocationResponseDTO> getSearchLocation(
                @Query("type") int type,
                @Query("page") int page,
                @Query("search") String keyWord,
                @Query("lat") Double lat,
                @Query("lng") Double lng,
                @Body LocationFilterItemDTO locationFilterItemDTO,
                @Header(XAPITOKEN) String token
        );

        //like this comment of tour
        @GET("m/tour/review/{reviewId}/like")
        Call<ReviewLikeResponseDTO> requestLikeDiscussTour(
                @Path("reviewId") long reviewId,
                @Header(XAPITOKEN) String token
        );

        @GET("m/tour/plan/{tourPlanId}/notification")
        Call<TourNotificationResponseDTO> getPlanNotification(
                @Path("tourPlanId") long tourPlanId,
                @Query("page") int page,
                @Header(XAPITOKEN) String token
        );

        @GET("user/user/notification")
        Call<TourNotificationResponseDTO> getUserNotification(
                @Query("page") int page,
                @Query("pageSize") Integer pageSize,
                @Header(XAPITOKEN) String token
        );

        @POST("tour/{tourId}/plan/{tourPlanId}/notification")
        Call<ScheduleDTO> createSchedule(
                @Path("tourId") String tourId,
                @Path("tourPlanId") String tourPlanId,
                @Body ScheduleDTO schedule,
                @Header(XAPITOKEN) String token
        );

        @GET("tour/getTourPlaceInfo/{tourId}/{placeId}")
        Call<TripResponseDTO> getTourPlaceInfo(
                @Path("tourId") String tourId,
                @Path("page") String page,
                @Header(XAPITOKEN) String token
        );

        @GET("m/tour/{tourId}/itinerary")
        Call<TourItineraryResponseDTO> getTourItinerary(
                @Path("tourId") long tourId,
                @Header(XAPITOKEN) String token
        );

        @POST("m/tour/{tourId}/plan/{tourPlanId}/invite")
        Call<CommonResponseDTO> requestInvite(
                @Path("tourId") long tourId,
                @Path("tourPlanId") long tourPlanId,
                @Body List<String> list,
                @Header(XAPITOKEN) String token
        );

        /**
         * get review leader
         */
        @GET("review/user/{leaderId}")
        Call<DiscussResponseDTO> getReviewLeader(
                @Path("leaderId") long leaderId,
                @Query("page") int page,
                @Header(XAPITOKEN) String token
        );

        /**
         * tao review leader
         */
        @POST("review/user/{leaderId}")
        Call<ReviewCreateResponseDTO> requestReviewLeader(
                @Path("leaderId") long leaderId,
                @Body ReviewItemDTO discussDTO,
                @Header(XAPITOKEN) String token
        );

        @GET("m/tour/{tourId}/plan/{tourPlanId}/vote")
        Call<TourVoteResponse> getVoteCriteria(
                @Path("tourId") long tourId,
                @Path("tourPlanId") long tourPlanId,
                @Header(XAPITOKEN) String token
        );

        /**
         * tao post tour vote
         */
        @POST("m/tour/{tourId}/plan/{tourPlanId}/vote")
        Call<CommonResponseDTO> sendTourVote(
                @Path("tourId") long tourId,
                @Path("tourPlanId") long tourPlanId,
                @Body List<TourVoteDTO> tourVote,
                @Header(XAPITOKEN) String token
        );

        @Multipart
        @POST
        Call<PhotoUploadResponseDTO> uploadFile(
                @Url String url,
//            @Part("") MultipartBody.Part file,
                @Part("file\"; filename=\"pp.jpg") RequestBody image);

        @POST("user/notification/seen")
        Call<CommonResponseDTO> requestSeen(
                @Body List<Long> notificationIds,
                @Header(XAPITOKEN) String token
        );

        @GET("tour/relate")
        Call<TourRelateResponseDTO> getTourRelate(
                @Query("tourId") long tourId,
                @Query("locationId") long locationId,
                @Query("page") int page,
                @Header(XAPITOKEN) String token
        );
        @GET("m/tour/{tourId}/plan/{tourPlanId}")
        Call<TourPlanResponseDTO> getTourPlanId(
                @Path("tourId") long tourId,
                @Path("tourPlanId") long tourPlanId,
                @Header(XAPITOKEN) String token
        );

        @GET("tour/{tourId}/plan/{tourPlanId}/am-i-invited")
        Call<TourInviteResponseDTO> getIInvited(
                @Path("tourId") long tourId,
                @Path("tourPlanId") long tourPlanId,
                @Header(XAPITOKEN) String token
        );

        @POST("tour/{tourId}/plan/{tourPlanId}/ask-to-join")
        Call<CommonResponseDTO> requestAskJoinTour(
                @Path("tourId") long tourId,
                @Path("tourPlanId") long tourPlanId,
                @Header(XAPITOKEN) String token
        );

        @GET("tour/{tourId}/plan/{tourPlanId}/invite/join")
        Call<CommonResponseDTO> requestAcceptJoinTour(
                @Path("tourId") long tourId,
                @Path("tourPlanId") long tourPlanId,
                @Query("invite-token") String inviteToken,
                @Header(XAPITOKEN) String token
        );

        @GET("tour/{tourId}/plan/{tourPlanId}/am-i-asked-to-join")
        Call<CommonResponseDTO> requestCheckAskedToJoinTour(
                @Path("tourId") long tourId,
                @Path("tourPlanId") long tourPlanId,
                @Header(XAPITOKEN) String token
        );

        @GET("tour/{tourId}/plan/{tourPlanId}/invite/decline")
        Call<CommonResponseDTO> requestDeclineJoinTour(
                @Path("tourId") long tourId,
                @Path("tourPlanId") long tourPlanId,
                @Query("invite-token") String inviteToken,
                @Header(XAPITOKEN) String token
        );

        @GET("m/tour/getTourInDay")
        Call<ListTourResponseDTO> getListTour(
                @Query("date") String date,
                @Query("page") int page,
                @Header(XAPITOKEN) String token
        );

        @GET("m/tour/getFeedbackTour")
        Call<FeedbackResponseDTO> getFeedback(
                @Query("tourId") Long idTour,
                @Query("tourPlanId") Long tourPlanId,
                @Query("date") String date,
                @Query("page") int page,
                @Header(XAPITOKEN) String token
        );

        @GET("m/tour/{tourId}/itinerary/{locationId}/photo")
        Call<TourPlacePictureResponseDTO> getTourPlacePicture(
                @Path("tourId") long tourId,
                @Path("locationId") long placeId,
                @Query("page") int page,
                @Header(XAPITOKEN) String token
        );

        @GET("m/tour/{tourId}/photo")
        Call<TourPlacePictureResponseDTO> getTourPicture(
                @Path("tourId") long tourId,
                @Query("page") int page,
                @Header(XAPITOKEN) String token
        );
    }

    /**
     * @param page
     * @param id       id plan
     * @param callBack
     */
    @Override
    public void getPlanNotification(int page, String id, ModelCallBack callBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getPlanNotification(Long.valueOf(id), page, PreferenceUtils.getString(Constants.Preference
                        .PREFERENCE_USER_TOKEN,
                ""));
        requestAPI(callBack);
    }

    /**
     * @param page
     * @param callBack
     */
    @Override
    public void getUserNotification(int page, ModelCallBack callBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getUserNotification(page, 7, PreferenceUtils.getString(Constants.Preference
                        .PREFERENCE_USER_TOKEN,
                ""));
        requestAPI(callBack);
    }

    @Override
    public void getTourInfomation(long tourId, long tourPlanId, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getTourInfomation(tourId, tourPlanId, PreferenceUtils.getString(Constants.Preference
                .PREFERENCE_USER_TOKEN, ""));
        // goi request
        requestAPI(modelCallBack);
    }

    @Override
    public void requestRatingLeader(String idTour, String idLeader, LeaderRatedDTO leaderRatedDTO, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.ratingLeader(idTour, idLeader, leaderRatedDTO, PreferenceUtils.getString(Constants.Preference
                .PREFERENCE_USER_TOKEN, ""));
        requestAPI(modelCallBack);
    }

    @Override
    public void requestDiscussTour(ReviewItemDTO discussDTO, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestDiscuss(discussDTO.getItemId(), discussDTO, PreferenceUtils.getString(Constants.Preference
                .PREFERENCE_USER_TOKEN, ""));
        requestAPI(modelCallBack);

    }

    @Override
    public void requestLikeReviewLeader(long reviewId, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestLikeReviewLeader(reviewId, PreferenceUtils.getString(Constants
                .Preference
                .PREFERENCE_USER_TOKEN, ""));
        requestAPI(modelCallBack);
    }

    @Override
    public void requestLikeDiscussOfTour(long idRated, ModelCallBack
            modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestLikeDiscussTour(idRated, PreferenceUtils.getString(Constants
                .Preference
                .PREFERENCE_USER_TOKEN, ""));
        requestAPI(modelCallBack);
    }

    @Override
    public void getSearchTour(int type, int page, String keyWord, String date, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getSearchTour(type, page, keyWord, date, PreferenceUtils.getString(Constants.Preference
                .PREFERENCE_USER_TOKEN, ""));
        requestAPI(modelCallBack);
    }

    @Override
    public void getSearchLocation(int type, int page, String keyWord, Double lat, Double lng, LocationFilterItemDTO
            locationFilterItemDTO, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getSearchLocation(type, page, keyWord, lat, lng, locationFilterItemDTO, PreferenceUtils
                .getString(Constants.Preference
                        .PREFERENCE_USER_TOKEN, ""));
        requestAPI(modelCallBack);
    }

    @Override
    public void getLeaderRated(long tourId, long touristId, int page, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getLeaderRated(tourId, touristId, page, PreferenceUtils.getString(Constants.Preference
                .PREFERENCE_USER_TOKEN, ""));
        //call request
        requestAPI(modelCallBack);

    }

    @Override
    public void getTourDiscuss(long tourId, int page, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getTourDiscuss(tourId, page, PreferenceUtils.getString(Constants.Preference.PREFERENCE_USER_TOKEN, ""));
        //call request
        requestAPI(modelCallBack);
    }


    @Override
    public void getLeaderInfo(String touristId, int page, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getTouristInfo(touristId, page, PreferenceUtils.getString(Constants.Preference.PREFERENCE_USER_TOKEN, ""));
        //call request
        requestAPI(modelCallBack);
    }

    @Override
    public void createSchedule(String tourId, String tourPlanId, ScheduleDTO scheduleDTO, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.createSchedule(tourId, tourPlanId, scheduleDTO, PreferenceUtils.getString(Constants.Preference
                .PREFERENCE_USER_TOKEN, ""));
        //call request
        requestAPI(modelCallBack);
    }

    @Override
    public void getTourItinerary(long tourId, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getTourItinerary(tourId, PreferenceUtils.getString(Constants.Preference
                .PREFERENCE_USER_TOKEN, ""));
        requestAPI(modelCallBack);
    }

    /**
     * Lay thong tin dia diem di cua mot tour
     *
     * @param tourId
     * @param placeId
     */
    @Override
    public void getTourPlaceInfo(String tourId, String placeId, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getTourPlaceInfo(tourId, placeId, PreferenceUtils.getString(Constants.Preference.PREFERENCE_USER_TOKEN,
                ""));
        //call request
        requestAPI(modelCallBack);
    }

    @Override
    public void requestInvite(long tourId, long tourPlanId, List<String> list, ModelCallBack
            modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestInvite(tourId, tourPlanId, list, PreferenceUtils.getString(Constants.Preference
                        .PREFERENCE_USER_TOKEN,
                ""));
        //call request
        requestAPI(modelCallBack);
    }

    @Override
    public void sendTourVote(long tourId, long tourPlanId, List<TourVoteDTO> lstTourVote, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.sendTourVote(tourId, tourPlanId, lstTourVote, PreferenceUtils.getString(Constants.Preference.PREFERENCE_USER_TOKEN,
                ""));
        //call request
        requestAPI(modelCallBack);
    }

    /**
     * get review trong phan tourguide
     *
     * @param itemId        loai itemId
     * @param page          so trang
     * @param modelCallBack
     */
    @Override
    public void getReviewLeader(long itemId, Integer page, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getReviewLeader(itemId, page, PreferenceUtils.getString(Constants.Preference
                        .PREFERENCE_USER_TOKEN,
                ""));
        //call request
        requestAPI(modelCallBack);
    }

    @Override
    public void requestReviewLeader(ReviewItemDTO discussDTO, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestReviewLeader(discussDTO.getItemId(), discussDTO, PreferenceUtils.getString(Constants
                        .Preference
                        .PREFERENCE_USER_TOKEN,
                ""));
        //call request
        requestAPI(modelCallBack);
    }

    @Override
    public void getVoteCriteria(long tourId, long tourPlanId, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getVoteCriteria(tourId, tourPlanId, PreferenceUtils.getString(Constants
                .Preference
                .PREFERENCE_USER_TOKEN, ""));
        //call request
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
    public void requestSeen(List<Long> notificationIds, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestSeen(notificationIds, PreferenceUtils.getString(Constants
                .Preference
                .PREFERENCE_USER_TOKEN, ""));
        //call request
        requestAPI(modelCallBack);
    }

    @Override
    public void deleteComment(long itemId, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.deleteReivew(itemId, PreferenceUtils.getString(Constants
                .Preference
                .PREFERENCE_USER_TOKEN, ""));
        //call request
        requestAPI(modelCallBack);
    }

    @Override
    public void getTourPlan(long tourId, long tourPlanId, ModelCallBack<TourPlanResponseDTO> modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getTourPlanId(tourId, tourPlanId, PreferenceUtils.getString(Constants
                .Preference
                .PREFERENCE_USER_TOKEN, ""));
        //call request
        requestAPI(modelCallBack);
    }

    @Override
    public void getIInvited(long tourId, long tourPlanId, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getIInvited(tourId, tourPlanId, PreferenceUtils.getString(Constants
                .Preference.PREFERENCE_USER_TOKEN, ""));
        //call request
        requestAPI(modelCallBack);
    }

    @Override
    public void requestAskJoinTour(long tourId, long tourPlanId, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestAskJoinTour(tourId, tourPlanId, PreferenceUtils.getString(Constants
                .Preference.PREFERENCE_USER_TOKEN, ""));
        //call request
        requestAPI(modelCallBack);
    }

    @Override
    public void requestAcceptJoinTour(long tourId, long tourPlanId, String inviteToken, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestAcceptJoinTour(tourId, tourPlanId, inviteToken, PreferenceUtils.getString(Constants
                .Preference.PREFERENCE_USER_TOKEN, ""));
        //call request
        requestAPI(modelCallBack);
    }

    @Override
    public void requestCheckAskedJoinTour(long tourId, long tourPlanId, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestCheckAskedToJoinTour(tourId, tourPlanId, PreferenceUtils.getString(Constants
                .Preference.PREFERENCE_USER_TOKEN, ""));
        //call request
        requestAPI(modelCallBack);
    }

    @Override
    public void requestDeclineJoinTour(long tourId, long tourPlanId, String inviteToken, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestDeclineJoinTour(tourId, tourPlanId, inviteToken, PreferenceUtils.getString(Constants
                .Preference.PREFERENCE_USER_TOKEN, ""));
        //call request
        requestAPI(modelCallBack);
    }

    @Override
    public void getListTour( String date, int page,  ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getListTour(date,page, PreferenceUtils.getString(Constants.Preference.PREFERENCE_USER_TOKEN,
                ""));
        // goi request
        requestAPI(modelCallBack);
    }

    @Override
    public void getTourRelate(long tourId, long locationId, int page, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getTourRelate(tourId, locationId, page, PreferenceUtils.getString(Constants
                .Preference.PREFERENCE_USER_TOKEN, ""));
        //call request
        requestAPI(modelCallBack);
    }

    @Override
    public void getFeedback(Long idTour, Long tourPlanId, String date, int page, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getFeedback(idTour, tourPlanId, date, page, PreferenceUtils.getString(Constants.Preference
                        .PREFERENCE_USER_TOKEN, ""));
        // goi request
        requestAPI(modelCallBack);
    }
    
    @Override
    public void getTourPlacePicture(int page, long tourId, long placeId, ModelCallBack modelCallBack ) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getTourPlacePicture(tourId, placeId, page, PreferenceUtils.getString(Constants.Preference
                .PREFERENCE_USER_TOKEN, ""));
        requestAPI(modelCallBack);
    }

    @Override
    public void getTourPicture( int page, long tourId, ModelCallBack modelCallBack ) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getTourPicture(tourId, page, PreferenceUtils.getString(Constants.Preference
                .PREFERENCE_USER_TOKEN, ""));
        requestAPI(modelCallBack);
    }

}
