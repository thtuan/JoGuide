package com.boot.accommodation.vp.tour;

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
import com.boot.accommodation.model.impl.TourModel;
import com.boot.accommodation.model.mgr.TourModelMgr;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

import java.io.File;
import java.util.ArrayList;

/**
 * Presenter cho man hinh thong tin tourist
 *
 * @author vuong
 * @since 15:47 PM 5/13/2016
 */
public class LeaderRatePresenter implements LeaderRatePresenterMgr {

    ArrayList<ReviewItemDTO> lstReview = new ArrayList<>(); // List review leader
    LeaderRateViewMgr leaderRateViewMgr;
    TourModelMgr tourModelMgr;
    private int page = 0;// so trang load
    private int pageSize = 0; //tong trang
    private String TAG = ""; // Tag

    public LeaderRatePresenter(LeaderRateViewMgr leaderRateViewMgr) {
        this.leaderRateViewMgr = leaderRateViewMgr;
        tourModelMgr = new TourModel();
        TAG = Utils.getTag(leaderRateViewMgr.getClass());
    }

    @Override
    public void getReview(long itemId ) {
        page = 0;
        pageSize = 0;
        lstReview.clear();
        handleReviewLeader(itemId);
    }

    /**
     * Handle review leader
     * @param itemId
     */
    private void handleReviewLeader(long itemId ) {
        tourModelMgr.getReviewLeader(itemId, page, new ModelCallBack<DiscussResponseDTO>(TAG) {

            @Override
            public void onSuccess( DiscussResponseDTO response ) {
                getRatedSuccess(response);
            }

            @Override
            public void onError( int errorCode, String error ) {
                leaderRateViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void getMoreReview( int itemType, long itemId, BaseRecyclerViewAdapter adapter ) {
        if (Utils.checkLoadMore(adapter, pageSize, lstReview.size())) {
            page++;
            handleReviewLeader(itemId);
        }
    }

    /**
     * tao review leader
     *
     * @param discussDTO
     */
    @Override
    public void requestReviewLeader( final ReviewItemDTO discussDTO) {
        tourModelMgr.requestReviewLeader(discussDTO, new ModelCallBack<ReviewCreateResponseDTO>(TAG) {
            @Override
            public void onSuccess(ReviewCreateResponseDTO response) {
                lstReview.add(0,response.getData());
                leaderRateViewMgr.renderLayout(lstReview);
            }

            @Override
            public void onError( int errorCode, String error ) {
                leaderRateViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void requestLikeReviewLeader( final ReviewItemDTO dto ) {
        tourModelMgr.requestLikeReviewLeader(dto.getReviewId(), new ModelCallBack<ReviewLikeResponseDTO>(TAG) {

            @Override
            public void onSuccess( ReviewLikeResponseDTO response ) {
                if (dto.isLiked()){
                    dto.setLiked(false);
                } else {
                    dto.setLiked(true);
                }
                dto.setNumLike(response.getData().intValue());
                leaderRateViewMgr.renderLayout(lstReview);
            }

            @Override
            public void onError( int errorCode, String error ) {
                leaderRateViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    /**
     * Xu li lay tourist info thanh cong
     */
    private void getRatedSuccess(DiscussResponseDTO response) {
        page = response.getData().getPaging().getPage();
        pageSize = response.getData().getPaging().getPageSize();
        lstReview.addAll(response.getData().getTourDiscuss());
        leaderRateViewMgr.renderLayout(lstReview);
    }

    @Override
    public void uploadPhoto(File file ) {
        tourModelMgr.uploadPhoto(file, new ModelCallBack<PhotoUploadResponseDTO>(TAG) {
            @Override
            public void onSuccess( PhotoUploadResponseDTO response ) {
                if(response.getData().size() > 0){
                    leaderRateViewMgr.updateFileNameUpload(response.getData().get(0).getURL());
                }
            }

            @Override
            public void onError( int errorCode, String error ) {
                leaderRateViewMgr.showMessageErr(errorCode, error);
            }

        }, new ProgressUpdateBody.UploadCallbacks() {
            @Override
            public void onProgressUpdate(int percentage) {
                leaderRateViewMgr.updateProgressBar(percentage);
            }

            @Override
            public void onError() {
                leaderRateViewMgr.updateError(Utils.getString(R.string.error_unknown_error));
            }

            @Override
            public void onFinish() {
                leaderRateViewMgr.finishUpdatePhoto();
            }
        });
    }


    @Override
    public void deleteReview(final ReviewItemDTO dto) {
        tourModelMgr.deleteComment(dto.getReviewId(), new ModelCallBack<CommonResponseDTO>(TAG) {
            @Override
            public void onSuccess(CommonResponseDTO response) {
                if (((Boolean) response.getData()).booleanValue()){
                    lstReview.remove(dto);
                    leaderRateViewMgr.renderLayout(lstReview);
                } else {
                    leaderRateViewMgr.showMessage(StringUtil.getString(R.string.text_delete_fail));
                }
            }

            @Override
            public void onError( int errorCode, String error ) {
                leaderRateViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

}
