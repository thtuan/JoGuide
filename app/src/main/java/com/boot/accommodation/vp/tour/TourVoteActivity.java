package com.boot.accommodation.vp.tour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.common.control.JoCoEditText;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.TourVoteDTO;
import com.boot.accommodation.dto.view.TourVoteViewMgr;
import com.boot.accommodation.dto.view.TourVotePresenter;
import com.boot.accommodation.dto.view.TourVotePresenterMgr;
import com.boot.accommodation.dto.view.TripItemDTO;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TourVoteActivity extends BaseActivity implements TourVoteViewMgr {

    private TourVotePresenterMgr tourVotePresenterMgr;
    private TourVoteAdapter adapter;
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btnSend)
    Button ivSend;
    @Bind(R.id.rvVote)
    RecyclerView rvVote;
    @Bind(R.id.etComment)
    JoCoEditText etComment;
    private TripItemDTO tripItem;// trip dto

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_vote);
        ButterKnife.bind(this);
        enableSlidingMenu(false);
        Intent intent = getIntent();
        if (intent != null) {
            tripItem = intent.getParcelableExtra(Constants.IntentExtra.TRIP_ITEM);
        }
        init();
        showProgress();
    }
    void init(){
        tourVotePresenterMgr = new TourVotePresenter(this);
        rvVote.setLayoutManager(new LinearLayoutManager(this));
        rvVote.setHasFixedSize(true);
        etComment.setImageClearVisibile(false);
        Utils.hideKeyboardInput(this,etComment);
        tourVotePresenterMgr.getVoteCriteria(tripItem.getTourId(),tripItem.getTourPlanId());
    }

    @OnClick({R.id.ivBack, R.id.btnSend,R.id.etComment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.btnSend:
                TourVoteDTO tourVoteDTO = new TourVoteDTO();
                tourVoteDTO.setVoteContent(etComment.getText().toString());
                tourVotePresenterMgr.sendVote(tripItem.getTourId(),tripItem.getTourPlanId(),tourVoteDTO);
                showProgress();
                break;
        }
    }



    @Override
    public void showMessageForPostSuccess() {
        closeProgress();
        showMessage(StringUtil.getString(R.string.text_vote_tour_success));
        super.onBackPressed();
    }


    @Override
    public void showMessageErr(int errorCode, String error) {
        closeProgress();
        switch (errorCode){
            default:
                handleError(errorCode,error);
        }
    }

    @Override
    public void renderLayout( List<TourVoteDTO> lstTourVote ) {
        closeProgress();
        if (adapter == null) {
            adapter = new TourVoteAdapter(lstTourVote);
            rvVote.setAdapter(adapter);
        } else {
            adapter.setData(lstTourVote);
        }
        adapter.notifyDataSetChanged();
    }
}
