package com.boot.accommodation.vp.tourguide;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.common.control.EventCalendar;
import com.boot.accommodation.dto.view.TourGuideDTO;
import com.boot.accommodation.util.ImageUtil;
import com.github.siyamed.shapeimageview.CircularImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TourGuideDetailActivity extends BaseActivity implements View.OnClickListener, TourGuideDetailActivityMgr {
    @Bind(R.id.iv_menu)
    ImageView ivMenu;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.ivEdit)
    ImageView ivEdit;
    @Bind(R.id.ll_header)
    RelativeLayout llHeader;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.ciAvatar)
    CircularImageView ciAvatar;
    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.ecCalendar)
    EventCalendar ecCalendar;
    @Bind(R.id.tvTown)
    TextView tvTown;
    private TourGuideDTO tourGuideDTO;
    TourGuideDetailPresenterMgr tourGuideDetailPresenterMgr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_guide_detail);
        ButterKnife.bind(this);
        tourGuideDetailPresenterMgr = new TourGuideDetailPresenter(this);
        tourGuideDTO = (TourGuideDTO) getIntent().getExtras().get("data");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tourGuideDTO.getName());
        ImageUtil.loadImage(ciAvatar, tourGuideDTO.getImage());
        tvName.setText(tourGuideDTO.getName());
        tvTitle.setText(tourGuideDTO.getName());
        tvTown.setText(tourGuideDTO.getTown());
        ecCalendar.updateEvents(tourGuideDTO.getTourGuideEvents());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

}
