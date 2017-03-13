package com.boot.accommodation.vp.location;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.common.control.ProgressUpdateBody;
import com.boot.accommodation.dto.response.CommonResponseDTO;
import com.boot.accommodation.dto.response.DiscussResponseDTO;
import com.boot.accommodation.dto.response.PhotoUploadResponseDTO;
import com.boot.accommodation.dto.response.ReviewCreateResponseDTO;
import com.boot.accommodation.dto.response.ReviewLikeResponseDTO;
import com.boot.accommodation.dto.view.ReviewItemDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.LocationModel;
import com.boot.accommodation.model.impl.TourModel;
import com.boot.accommodation.model.mgr.LocationModelMgr;
import com.boot.accommodation.model.mgr.TourModelMgr;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

import java.io.File;
import java.util.ArrayList;

/**
 * Mo ta class
 *
 * @author Dungnx
 */
public class LocationDetailReviewPresenter implements LocationDetailReviewPresenterMgr {
    private LocationDetailReviewMgr placeDetailReviewMgr;
    private LocationModelMgr locationModelMgr;
    private TourModelMgr tourModelMgr;
    private ArrayList<ReviewItemDTO> lstReview = new ArrayList<>(); // lst review
    private int page = 0;
    private int pageSize = 0;
    private String TAG = ""; // Tag
    private int typeReview; //Type review
    private long itemId; //Item id review

    public LocationDetailReviewPresenter(LocationDetailReviewMgr placeDetailReviewMgr) {
        this.placeDetailReviewMgr = placeDetailReviewMgr;
        locationModelMgr = new LocationModel();
        tourModelMgr = new TourModel();
        TAG = Utils.getTag(placeDetailReviewMgr.getClass());
    }

    @Override
    public void getReview(int typeReview, long itemId) {
        page = 0;
        pageSize = 0;
        lstReview.clear();
        this.typeReview = typeReview;
        this.itemId = itemId;
        handleGetReview();
    }

    /**
     * Handle get review
     */
    private void handleGetReview() {
        if (LocationDetailReviewFragment.TYPE_REVIEW_LOCATION == typeReview) {
            handleGetReviewLocation(itemId);
        } else if (LocationDetailReviewFragment.TYPE_REVIEW_TOUR == typeReview) {
            handleGetReviewTour(itemId);
        } else {
            handleGetReviewUser(itemId);
        }
    }

    @Override
    public void getMoreReview(BaseRecyclerViewAdapter adapter) {
        if (Utils.checkLoadMore(adapter, pageSize, lstReview.size())) {
            page++;
            handleGetReview();
        }
    }

    /**
     * Handle get review
     *
     * @param placeId
     */
    private void handleGetReviewLocation(long placeId) {
        locationModelMgr.getReview(placeId, page, new ModelCallBack<DiscussResponseDTO>(TAG) {

            @Override
            public void onSuccess(DiscussResponseDTO response) {
                getReviewSuccess(response);
            }

            @Override
            public void onError(int errorCode, String error) {
                placeDetailReviewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void requestLikeReview(final ReviewItemDTO reviewItemDTO) {
        if (LocationDetailReviewFragment.TYPE_REVIEW_USER == typeReview) {
            handleLikeReviewUser(reviewItemDTO);
        } else if (LocationDetailReviewFragment.TYPE_REVIEW_TOUR == typeReview) {
            handleLikeReviewTour(reviewItemDTO);
        } else {
            handleLikeReviewLocation(reviewItemDTO);
        }
    }

    /**
     * Handle like location
     */
    private void handleLikeReviewLocation(final ReviewItemDTO reviewItemDTO) {
        locationModelMgr.requestLikeReview(reviewItemDTO.getReviewId(), new ModelCallBack<ReviewLikeResponseDTO>(TAG) {
            @Override
            public void onSuccess(ReviewLikeResponseDTO response) {
                if (reviewItemDTO.isLiked()) {
                    reviewItemDTO.setLiked(false);
                } else {
                    reviewItemDTO.setLiked(true);
                }
                reviewItemDTO.setNumLike(response.getData());
                placeDetailReviewMgr.renderLayout(lstReview);
            }

            @Override
            public void onError(int errorCode, String error) {
                placeDetailReviewMgr.showMessageErr(errorCode, error);
            }
        });
    }

    /**
     * Handle like review tour
     * @param dto
     */
    private void handleLikeReviewTour(final ReviewItemDTO dto) {
        tourModelMgr.requestLikeDiscussOfTour(dto.getReviewId(), new ModelCallBack<ReviewLikeResponseDTO>(TAG) {
            @Override
            public void onSuccess(ReviewLikeResponseDTO response) {
                if (dto.isLiked()){
                    dto.setLiked(false);
                } else {
                    dto.setLiked(true);
                }
                dto.setNumLike(response.getData());
                placeDetailReviewMgr.renderLayout(lstReview);
            }

            @Override
            public void onError( int errorCode, String error ) {
                placeDetailReviewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    private void handleLikeReviewUser(final ReviewItemDTO dto) {
        tourModelMgr.requestLikeReviewLeader(dto.getReviewId(), new ModelCallBack<ReviewLikeResponseDTO>(TAG) {

            @Override
            public void onSuccess( ReviewLikeResponseDTO response ) {
                if (dto.isLiked()){
                    dto.setLiked(false);
                } else {
                    dto.setLiked(true);
                }
                dto.setNumLike(response.getData().intValue());
                placeDetailReviewMgr.renderLayout(lstReview);
            }

            @Override
            public void onError( int errorCode, String error ) {
                placeDetailReviewMgr.showMessageErr(errorCode, error);
            }
        });
    }

    @Override
    public void requestCreateReview(final ReviewItemDTO myReview) {
        if (LocationDetailReviewFragment.TYPE_REVIEW_LOCATION == typeReview) {
            handleCreateReviewLocation(myReview);
        } else if (LocationDetailReviewFragment.TYPE_REVIEW_TOUR == typeReview) {
            handleCreateReviewTour(myReview);
        } else {
            handleCreateReviewUser(myReview);
        }
    }

    /**
     * Handle create review location
     * @param myReview
     */
    private void handleCreateReviewLocation(final ReviewItemDTO myReview) {
        locationModelMgr.requestReviewPlace(myReview, new ModelCallBack<ReviewCreateResponseDTO>(TAG) {
            @Override
            public void onSuccess(ReviewCreateResponseDTO response) {
                handleCreateReviewSuccess(response);
            }

            @Override
            public void onError(int errorCode, String error) {
                placeDetailReviewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    /**
     * Handle create review tour
     */
    private void handleCreateReviewTour(final ReviewItemDTO myReview) {
        tourModelMgr.requestDiscussTour(myReview, new ModelCallBack<ReviewCreateResponseDTO>(TAG) {

            @Override
            public void onSuccess(ReviewCreateResponseDTO response) {
                handleCreateReviewSuccess(response);
            }

            @Override
            public void onError( int errorCode, String error ) {
                placeDetailReviewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    /**
     * Handle create review success
     * @param response
     */
    private void handleCreateReviewSuccess(ReviewCreateResponseDTO response) {
        boolean isHaveContent = false;
        if (response.getData() != null) {
            if (!StringUtil.isNullOrEmpty(response.getData().getContent()) || !StringUtil.isNullOrEmpty(response
                    .getData().getImage())) {
                lstReview.add(0, response.getData());
                isHaveContent = true;
            }
        }
        placeDetailReviewMgr.reviewSuccess(lstReview, isHaveContent);
    }

    /**
     * Handle create review tour
     */
    private void handleCreateReviewUser(final ReviewItemDTO myReview) {
        tourModelMgr.requestReviewLeader(myReview, new ModelCallBack<ReviewCreateResponseDTO>(TAG) {
            @Override
            public void onSuccess(ReviewCreateResponseDTO response) {
                handleCreateReviewSuccess(response);
            }

            @Override
            public void onError( int errorCode, String error ) {
                placeDetailReviewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void uploadPhoto(File file) {
        locationModelMgr.uploadPhoto(file, new ModelCallBack<PhotoUploadResponseDTO>(TAG) {
            @Override
            public void onSuccess(PhotoUploadResponseDTO response) {
                if (response.getData().size() > 0) {
                    placeDetailReviewMgr.updateFileNameUpload(response.getData().get(0).getURL());
                }
            }

            @Override
            public void onError(int errorCode, String error) {
                placeDetailReviewMgr.showMessageErr(errorCode, error);
            }

        }, new ProgressUpdateBody.UploadCallbacks() {
            @Override
            public void onProgressUpdate(int percentage) {
                placeDetailReviewMgr.updateProgressBar(percentage);
            }

            @Override
            public void onError() {
                placeDetailReviewMgr.updateError(Utils.getString(R.string.error_unknown_error));
            }

            @Override
            public void onFinish() {
                placeDetailReviewMgr.finishUpdatePhoto();
            }
        });
    }

    @Override
    public void deleteReview(final ReviewItemDTO dto) {
        locationModelMgr.deleteComment(dto.getReviewId(), new ModelCallBack<CommonResponseDTO>(TAG) {
            @Override
            public void onSuccess(CommonResponseDTO response) {
                if (((Boolean) response.getData()).booleanValue()) {
                    lstReview.remove(dto);
                    placeDetailReviewMgr.deleteReviewSuccess(lstReview);
                } else {
                    placeDetailReviewMgr.showMessage(StringUtil.getString(R.string.text_delete_fail));
                }
            }

            @Override
            public void onError(int errorCode, String error) {
                placeDetailReviewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    /**
     * Handle get review success
     */
    private void getReviewSuccess(DiscussResponseDTO response) {
        page = response.getData().getPaging().getPage();
        pageSize = response.getData().getPaging().getPageSize();
        lstReview.addAll(response.getData().getTourDiscuss());
        placeDetailReviewMgr.renderLayout(lstReview);
    }

    /**
     * Handle get tour
     *
     * @param tourId
     */
    private void handleGetReviewTour(long tourId) {
        tourModelMgr.getTourDiscuss(tourId, page, new ModelCallBack<DiscussResponseDTO>(TAG) {

            @Override
            public void onSuccess( DiscussResponseDTO response ) {
                getReviewSuccess(response);
            }

            @Override
            public void onError( int errorCode, String error ) {
                placeDetailReviewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    /**
     * Handle review leader
     * @param userId
     */
    private void handleGetReviewUser(long userId ) {
        tourModelMgr.getReviewLeader(itemId, page, new ModelCallBack<DiscussResponseDTO>(TAG) {

            @Override
            public void onSuccess( DiscussResponseDTO response ) {
                getReviewSuccess(response);
            }

            @Override
            public void onError( int errorCode, String error ) {
                placeDetailReviewMgr.showMessageErr(errorCode, error);
            }

        });
    }


}
