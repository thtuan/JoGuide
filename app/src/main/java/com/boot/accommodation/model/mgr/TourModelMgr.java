package com.boot.accommodation.model.mgr;

import com.boot.accommodation.common.control.ProgressUpdateBody;
import com.boot.accommodation.dto.response.TourPlanResponseDTO;
import com.boot.accommodation.dto.view.LocationFilterItemDTO;
import com.boot.accommodation.dto.view.ReviewItemDTO;
import com.boot.accommodation.dto.view.LeaderRatedDTO;
import com.boot.accommodation.dto.view.ScheduleDTO;
import com.boot.accommodation.dto.view.TourVoteDTO;
import com.boot.accommodation.listener.ModelCallBack;

import java.io.File;
import java.util.List;

/**
 * Mgr model cho tour
 *
 * @author tuanlt
 * @since: 11:51 PM 5/12/2016
 */
public interface TourModelMgr {

    /**
     * get plan notification
     * @param page
     * @param id
     * @param callBack
     */
    void getPlanNotification(int page, String id, ModelCallBack callBack);

    /**
     * get user notification
     * @param page
     * @param callBack
     */
    void getUserNotification(int page, ModelCallBack callBack);

    /**
     * get tour infomation for expand tour info
     * @param tourId
     * @param modelCallBack
     */
    void getTourInfomation(long tourId, long tourPlanId, ModelCallBack modelCallBack);

    /**
     * vuong-bv send request rating of tour and leader and like this rated
     * @param idTour
     * @param idLeader
     * @param leaderRatedDTO
     * @param modelCallBack
     */
    void requestRatingLeader(String idTour, String idLeader, LeaderRatedDTO leaderRatedDTO, ModelCallBack modelCallBack);

    /**
     * Request discuss tour
     * @param discussDTO
     * @param modelCallBack
     */
    void requestDiscussTour( ReviewItemDTO discussDTO, ModelCallBack modelCallBack);

    /**
     * Request like review
     * @param idRated
     * @param modelCallBack
     */
    void requestLikeReviewLeader( long idRated, ModelCallBack modelCallBack);

    /**
     * Request like discuss of tour
     * @param idRated
     * @param modelCallBack
     */
    void requestLikeDiscussOfTour(long idRated, ModelCallBack modelCallBack);

    /**
     * Get data search tour
     * @param page
     * @param keyWord
     * @param date
     * @param modelCallBack
     * @param type
     */
    void getSearchTour( int type, int page, String keyWord, String date, ModelCallBack modelCallBack );

    /**
     * Get data search location
     * @param type
     * @param page
     * @param keyWord
     * @param lat
     * @param lng
     * @param modelCallBack
     */
    void getSearchLocation(int type, int page, String keyWord, Double lat, Double lng, LocationFilterItemDTO
            locationFilterItemDTO, ModelCallBack modelCallBack );

    /**
     * vuong-bv get tourist leader rated
     * @param tourId
     * @param touristId
     * @param page
     * @param modelCallBack
     */
    void getLeaderRated (long tourId, long touristId, int page, ModelCallBack modelCallBack);

    /**
     * Get tour rated
     * @param tourPlanId
     * @param page
     * @param modelCallBack
     */
    void getTourDiscuss( long tourPlanId, int page, ModelCallBack modelCallBack );

    /**
     * Create schedule
     * @param tourid
     * @param tourPlanId
     * @param scheduleDTO
     * @param modelCallBack
     */
    void createSchedule(String tourid, String tourPlanId ,ScheduleDTO scheduleDTO, ModelCallBack modelCallBack);

    /**
     * vuong-bv get Tourisr Leader info
     * @param touristId
     * @param page
     * @param modelCallBack
     */
    void getLeaderInfo (String touristId, int page, ModelCallBack modelCallBack);

    /**
     * Lay lich trinh tour
     * @param tourId
     * @param modelCallBack
     */
    void getTourItinerary(long tourId, ModelCallBack modelCallBack);

    /**
     * Lay thong tin dia diem di cua mot tour
     * @param tourId
     * @param placeId
     */
    void getTourPlaceInfo(String tourId, String placeId, ModelCallBack modelCallBack);

    /**
     * gui yeu cau ban be
     * @param modelCallBack
     */
    void requestInvite(long tourId, long tourPlanId, List<String> list, ModelCallBack modelCallBack );

    /**
     * gui vote len server
     * @param lstTourVote
     * @param modelCallBack
     */
    void sendTourVote(long tourId, long tourPlanId, List<TourVoteDTO> lstTourVote, ModelCallBack modelCallBack);

    /**
     * get review leader
     * @param itemId
     * @param page
     * @param modelCallBack
     */
    void getReviewLeader(long itemId, Integer page, ModelCallBack modelCallBack);

    /**
     * Review leader
     *
     * @param discussDTO
     * @param modelCallBack
     */
    void requestReviewLeader(ReviewItemDTO discussDTO, ModelCallBack modelCallBack);

    /**
     * Get vote criteria
     * @param tourId
     * @param tourPlanId
     * @param modelCallBack
     */
    void getVoteCriteria(long tourId, long tourPlanId, ModelCallBack modelCallBack);

    /**
     * Upload photo
     * @param file
     * @param modelCallBack
     */
    void uploadPhoto(File file, ModelCallBack modelCallBack, ProgressUpdateBody.UploadCallbacks uploadCallbacks);

    /**
     * whether user seen notification or not
     * @param notificationId
     * @param modelCallBack
     */
    void requestSeen(List<Long> notificationId, ModelCallBack modelCallBack);


    /**
     * Delete my comment
     * @param itemId
     * @param modelCallBack
     */
    void deleteComment(long itemId, ModelCallBack modelCallBack);

    /**
     * Get tour relate
     * @param tourId
     * @param locationId
     * @param page
     * @param modelCallBack
     */
    void getTourRelate(long tourId, long locationId, int page, ModelCallBack modelCallBack);

    /**
     * Get tour follow tourPlanId
     * @param tourId
     * @param tourPlanId
     * @param modelCallBack
     */
    void getTourPlan(long tourId, long tourPlanId, ModelCallBack<TourPlanResponseDTO> modelCallBack);

    /**
     * Am i invited
     * @param tourId
     * @param tourPlanId
     * @param modelCallBack
     */
    void getIInvited(long tourId, long tourPlanId, ModelCallBack modelCallBack);

    /**
     * Request ask join tour
     * @param tourId
     * @param tourPlanId
     * @param modelCallBack
     */
    void requestAskJoinTour(long tourId, long tourPlanId, ModelCallBack modelCallBack);

    /**
     * Request accept join tour
     * @param tourId
     * @param tourPlanId
     * @param modelCallBack
     */
    void requestAcceptJoinTour(long tourId, long tourPlanId, String inviteToken, ModelCallBack modelCallBack);

    /**
     * Request check asked to join tour
     * @param tourId
     * @param tourPlanId
     * @param modelCallBack
     */
    void requestCheckAskedJoinTour(long tourId, long tourPlanId, ModelCallBack modelCallBack);

    /**
     * Request decline join tour
     * @param tourId
     * @param tourPlanId
     * @param modelCallBack
     */
    void requestDeclineJoinTour(long tourId, long tourPlanId, String inviteToken, ModelCallBack modelCallBack);

    /**
     * method get listtour
     * @param date
     * @param modelCallBack
     */
    void getListTour(String date, int page, ModelCallBack modelCallBack);

    /**
     * method get listtour
     * @param idTour
     * @param date
     * @param modelCallBack
     */
    void getFeedback( Long idTour, Long tourPlanId, String date, int page, ModelCallBack modelCallBack );

    /**
     * Get tour place picture
     * @param placeId
     * @param modelCallBack
     */
    void getTourPlacePicture(int page, long tourId, long placeId, ModelCallBack modelCallBack );

    /**
     * Get tour picture
     * @param page
     * @param tourId
     * @param modelCallBack
     */
    void getTourPicture(int page, long tourId, ModelCallBack modelCallBack );

}
