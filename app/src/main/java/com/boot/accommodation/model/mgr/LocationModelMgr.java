package com.boot.accommodation.model.mgr;

import com.boot.accommodation.common.control.ProgressUpdateBody;
import com.boot.accommodation.dto.view.GPSInfoDTO;
import com.boot.accommodation.dto.view.MediaDTO;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.dto.view.PlaceReportDTO;
import com.boot.accommodation.dto.view.ReviewItemDTO;
import com.boot.accommodation.listener.ModelCallBack;
import java.io.File;
import java.util.List;

/**
 * LocationModelMgr
 *
 * @author tuanlt
 * @since 10:25 AM 12/29/16
 */
public interface LocationModelMgr {

    /**
     * Get month
     * @param callBack
     */
    void getMonth(ModelCallBack callBack);

    /**
     * Get location detail
     * @param placeId
     * @param modelCallBack
     */
    void getPlaceDetail(long placeId,ModelCallBack modelCallBack);

    /**
     * Get review
     * @param placeId
     * @param page
     * @param modelCallBack
     */
    void getReview( long placeId, int page, ModelCallBack modelCallBack);

    /**
     * Request review place
     * @param myReview
     * @param modelCallBack
     */
    void requestReviewPlace(ReviewItemDTO myReview, ModelCallBack modelCallBack);

    /**
     * Request like review
     * @param reviewId
     * @param modelCallBack
     */
    void requestLikeReview(long reviewId, ModelCallBack modelCallBack);

    /**
     * Get place photo
     * @param placeId
     * @param typePhoto
     * @param page
     * @param modelCallBack
     */
    void getPlacePhoto(long placeId, int typePhoto, int page, ModelCallBack modelCallBack);

    /**
     * update favourite
     * @param placeId
     * @param modelCallBack
     */
    void requestFavouritePlace(long placeId, ModelCallBack modelCallBack);

    /**
     * check in
     * @param userId
     * @param placeId
     * @param locationInfo
     * @param modelCallBack
     */
    void requestCheckin(String userId, long placeId, GPSInfoDTO locationInfo, ModelCallBack modelCallBack);

    /**
     * get place check in
     * @param page
     * @param lat
     * @param lng
     * @param modelCallBack
     */
    void getAllPlaceCheckIn( int page, double lat, double lng,  ModelCallBack modelCallBack );

    void createLocationCheckIn(PlaceItemDTO placeItemDTO, ModelCallBack modelCallBack);

    /**
     * Upload photo
     * @param file
     * @param modelCallBack
     */
    void uploadPhoto(File file, ModelCallBack modelCallBack, ProgressUpdateBody.UploadCallbacks uploadCallbacks);

    /**
     * Delete my comment
     * @param itemId
     * @param modelCallBack
     */
    void deleteComment(long itemId, ModelCallBack modelCallBack);

    /**
     * Request upload location photo
     * @param placeId
     * @param mediaDTOs
     * @param modelCallBack
     */
    void requestUploadLocationPhoto(long placeId, List<MediaDTO> mediaDTOs, ModelCallBack modelCallBack);

    /**
     * Request report location
     * @param placeId
     * @param placeReportDTO
     * @param modelCallBack
     */
    void requestReportLocation(long placeId, PlaceReportDTO placeReportDTO, ModelCallBack modelCallBack);

    /**
     * Get filter area
     * @param modelCallBack
     */
    void getFilterAreas(ModelCallBack modelCallBack);

    /**
     * Get area special
     * @param modelCallBack
     */
    void getAreaSpecial(ModelCallBack modelCallBack);

}
