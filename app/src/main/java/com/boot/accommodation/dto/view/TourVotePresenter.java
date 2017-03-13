package com.boot.accommodation.dto.view;

import com.boot.accommodation.base.BaseResponse;
import com.boot.accommodation.dto.response.TourVoteResponse;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.TourModel;
import com.boot.accommodation.model.mgr.TourModelMgr;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 4:15 PM 20-06-2016
 */
public class TourVotePresenter implements TourVotePresenterMgr {

    TourVoteViewMgr tourVoteViewMgr;
    TourModelMgr tourModelMgr;
    private List<TourVoteDTO> lstTourVote = new ArrayList<>(); // list tour vote
    private String TAG = ""; // Tag

    public TourVotePresenter(TourVoteViewMgr tourVoteMgr) {
        this.tourVoteViewMgr = tourVoteMgr;
        this.tourModelMgr = new TourModel();
        TAG = Utils.getTag(tourVoteViewMgr.getClass());
    }

    @Override
    public void sendVote(long tourId, long tourPlanId, final TourVoteDTO tourVote) {
        if (!StringUtil.isNullOrEmpty(tourVote.getVoteContent())) {
            if(lstTourVote.size() > 0){
                tourVote.setTourVoteId(lstTourVote.get(0).getTourVoteId());
            }
            lstTourVote.add(tourVote);
        }
        tourModelMgr.sendTourVote(tourId, tourPlanId, lstTourVote, new ModelCallBack(TAG) {
            @Override
            public void onSuccess(BaseResponse response) {
                tourVoteViewMgr.showMessageForPostSuccess();
            }

            @Override
            public void onError( int errorCode, String error ) {
                tourVoteViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void getVoteCriteria(long tourId, long tourPlanId) {
        lstTourVote.clear();
        tourModelMgr.getVoteCriteria(tourId, tourPlanId, new ModelCallBack<TourVoteResponse>(TAG) {
            @Override
            public void onSuccess(TourVoteResponse response) {
                lstTourVote.addAll(response.getData());
                tourVoteViewMgr.renderLayout(lstTourVote);
            }

            @Override
            public void onError( int errorCode, String error ) {
                tourVoteViewMgr.showMessageErr(errorCode, error);
            }

        });
    }


}