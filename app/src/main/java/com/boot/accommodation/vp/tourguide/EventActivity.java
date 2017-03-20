package com.boot.accommodation.vp.tourguide;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EventActivity extends BaseActivity implements EventActivityMgr {
    @Bind(R.id.iv_menu)
    ImageView ivMenu;
    @Bind(R.id.tlHome)
    TabLayout tlHome;
    @Bind(R.id.frFilter)
    FrameLayout frFilter;
    @Bind(R.id.ll_header)
    RelativeLayout llHeader;
    @Bind(R.id.vpTabView)
    ViewPager vpTabView;
    private EventPresenterMgr eventPresenterMgr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        ButterKnife.bind(this);
        eventPresenterMgr = new EventPresenter(this);
        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager());
        vpTabView.setAdapter(adapter);
        tlHome.setupWithViewPager(vpTabView);
        vpTabView.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlHome));
        tlHome.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vpTabView));
    }

}
