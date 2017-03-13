package com.boot.accommodation.vp.tour;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.dto.response.FeedbackResponseDTO;
import com.boot.accommodation.dto.view.FeedbackItemDTO;
import com.boot.accommodation.dto.view.FeedbackTourItemDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.TourModel;
import com.boot.accommodation.model.mgr.TourModelMgr;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;

/**
 * Presenter  cho man hinh tour feedback
 *
 * @author vuong-bv
 * @since: 9:25 AM 5/31/2016
 */
public class FeedbackPresenter implements FeedbackPresenterMgr {

    private FeedbackViewMgr feedbackViewMgr; // view listTour
    private TourModelMgr tourModelMgr;
    private ArrayList<FeedbackItemDTO> lstFeedback = new ArrayList<>();
    private int page = 0;// so trang load
    private int pageSize = 0; // tong trang
    private String TAG = ""; // Tag

    public FeedbackPresenter(FeedbackViewMgr feedbackViewMgr) {
        this.feedbackViewMgr = feedbackViewMgr;
        tourModelMgr = new TourModel();
        TAG = Utils.getTag(feedbackViewMgr.getClass());
    }

    @Override
    public void getFeedback(Long idTour, Long tourPlanId, String date) {
        page = 0;
        pageSize = 0;
        lstFeedback.clear();
        tourModelMgr.getFeedback(idTour,tourPlanId, date, page, new ModelCallBack<FeedbackResponseDTO>(TAG) {
            @Override
            public void onSuccess(FeedbackResponseDTO response) {
                getFeedbackSuccess(response);
                if (response.getData().getFeedback().getFeeback().size()!=0){
                    //render list tour at spinner
                    if(response.getData().getTour() != null) {
                        FeedbackTourItemDTO item = new FeedbackTourItemDTO(0l, 0l, StringUtil.getString(R.string.text_select_tour));
                        response.getData().getTour().add(0, item);
                        feedbackViewMgr.renderListTour(response.getData().getTour());
                    }
                }
                else {
                    feedbackViewMgr.getFeedbacBlank();
                }

            }

            @Override
            public void onError( int errorCode, String error ) {
                feedbackViewMgr.showMessageErr(errorCode, error);
            }

        });
    }


    @Override
    public void getMoreFeedback(Long idTour, Long tourPlanId, String date, BaseRecyclerViewAdapter adapter) {
        if (Utils.checkLoadMore(adapter, pageSize, lstFeedback.size())) {
            page++;
            tourModelMgr.getFeedback(idTour,tourPlanId, date, page, new ModelCallBack<FeedbackResponseDTO>(TAG) {
                @Override
                public void onSuccess(FeedbackResponseDTO response) {
                    getFeedbackSuccess(response);
                }

                @Override
                public void onError( int errorCode, String error ) {
                    feedbackViewMgr.showMessageErr(errorCode, error);
                }

            });
        }
    }

    /**
     * when we get feedback success render data
     *
     * @param response
     */
    private void getFeedbackSuccess(FeedbackResponseDTO response) {
        lstFeedback.addAll(response.getData().getFeedback().getFeeback());
        page = response.getData().getFeedback().getPaging().getPage();
        pageSize = response.getData().getFeedback().getPaging().getPageSize();
        //render feedback data
        feedbackViewMgr.renderLayout(lstFeedback);
    }


}
