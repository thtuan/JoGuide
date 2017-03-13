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
 * Presenter cho man hinh discuss tour
 *
 * @author vuong
 * @since 15:47 PM 5/13/2016
 */
public class DiscussPresenter implements DiscussPresenterMgr {
    private String TAG = ""; // Tag
    ArrayList<ReviewItemDTO> lstTourDiscuss = new ArrayList<>(); // lst tourate
    DiscussViewMgr discussViewMgr;
    TourModelMgr tourModelMgr;
    private int page = 0;// so trang load
    private int pageSize = 0; //tong trang

    public DiscussPresenter(DiscussViewMgr tourRateViewMgr) {
        this.discussViewMgr = tourRateViewMgr;
        tourModelMgr = new TourModel();
        TAG = Utils.getTag(discussViewMgr.getClass());
    }


    @Override
    public void getDiscuss(long tourPlanId ) {
        page = 0;
        pageSize = 0;
        lstTourDiscuss.clear();
        handleGetTour(tourPlanId);
    }

    @Override
    public void getMoreDiscuss(long tourPlanId, BaseRecyclerViewAdapter adapter ) {
        if (Utils.checkLoadMore(adapter, pageSize, lstTourDiscuss.size())) {
            page++;
            handleGetTour(tourPlanId);
        }
    }

    /**
     * Handle get tour
     *
     * @param tourId
     */
    private void handleGetTour(long tourId ) {
        tourModelMgr.getTourDiscuss(tourId, page, new ModelCallBack<DiscussResponseDTO>(TAG) {

            @Override
            public void onSuccess( DiscussResponseDTO response ) {
                getDiscussSuccess(response);
            }

            @Override
            public void onError( int errorCode, String error ) {
                discussViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void requestCreateReviewTour( final ReviewItemDTO discussDTO) {
        tourModelMgr.requestDiscussTour(discussDTO, new ModelCallBack<ReviewCreateResponseDTO>(TAG) {

            @Override
            public void onSuccess(ReviewCreateResponseDTO response) {
//                discussDTO.setReviewId(response.getData());
                lstTourDiscuss.add(0,response.getData());
                discussViewMgr.renderLayout(lstTourDiscuss);
            }

            @Override
            public void onError( int errorCode, String error ) {
                discussViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void requestLikeDiscuss(final ReviewItemDTO dto) {
        tourModelMgr.requestLikeDiscussOfTour(dto.getReviewId(), new ModelCallBack<ReviewLikeResponseDTO>(TAG) {
            @Override
            public void onSuccess(ReviewLikeResponseDTO response) {
                if (dto.isLiked()){
                  dto.setLiked(false);
                } else {
                    dto.setLiked(true);
                }
                dto.setNumLike(response.getData());
                discussViewMgr.renderLayout(lstTourDiscuss);
            }

            @Override
            public void onError( int errorCode, String error ) {
                discussViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void uploadPhoto(File file ) {
        tourModelMgr.uploadPhoto(file, new ModelCallBack<PhotoUploadResponseDTO>(TAG) {
            @Override
            public void onSuccess(PhotoUploadResponseDTO response) {
                if (response.getData().size() > 0) {
                    discussViewMgr.updateFileNameUpload(response.getData().get(0).getURL());
                }
            }

            @Override
            public void onError(int errorCode, String error) {
                discussViewMgr.showMessageErr(errorCode, error);
            }

        }, new ProgressUpdateBody.UploadCallbacks() {
            @Override
            public void onProgressUpdate(int percentage) {
                discussViewMgr.updateProgressBar(percentage);
            }

            @Override
            public void onError() {
                discussViewMgr.updateError(Utils.getString(R.string.error_unknown_error));
            }

            @Override
            public void onFinish() {
                discussViewMgr.finishUpdatePhoto();
            }
        });
    }

    @Override
    public void deleteReview(final ReviewItemDTO dto) {
        tourModelMgr.deleteComment(dto.getReviewId(), new ModelCallBack<CommonResponseDTO>(TAG) {
            @Override
            public void onSuccess(CommonResponseDTO response) {
                if (((Boolean) response.getData()).booleanValue()){
                    lstTourDiscuss.remove(dto);
                    discussViewMgr.renderLayout(lstTourDiscuss);
                } else {
                    discussViewMgr.showMessage(StringUtil.getString(R.string.text_delete_fail));
                }
            }

            @Override
            public void onError( int errorCode, String error ) {
                discussViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    /**
     * Xu li lay tourist info thanh cong
     */
    private void getDiscussSuccess( DiscussResponseDTO response) {
        page = response.getData().getPaging().getPage();
        pageSize = response.getData().getPaging().getPageSize();
        lstTourDiscuss.addAll(response.getData().getTourDiscuss());
        discussViewMgr.renderLayout(lstTourDiscuss);
    }

}
