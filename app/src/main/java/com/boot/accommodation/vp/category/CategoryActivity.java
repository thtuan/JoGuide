package com.boot.accommodation.vp.category;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.util.PreferenceUtils;
import com.boot.accommodation.util.Utils;
import com.boot.accommodation.vp.home.HomeAdapter;
import com.boot.accommodation.vp.trip.TripFragment;

import butterknife.Bind;

/**
 * Created by Admin on 13/11/2015.
 */
public class CategoryActivity extends BaseActivity implements View.OnClickListener {

    public static final int PAGE_TRIP = 1;
    public static final int PAGE_COLLECTION = 2;
    public static final int PAGE_ACTIVITY = 0;

    @Bind(R.id.htab_appbar)
    AppBarLayout mAppBar;
    @Bind(R.id.htab_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.htab_viewpager)
    ViewPager mViewPager;
    @Bind(R.id.htab_tabs)
    TabLayout mTabLayout;
    @Bind(R.id.htab_collapse_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.iv_user_avatar_expand)
    CircularImageView mIvUserAvatarExpand;
    @Bind(R.id.tv_user_name_expand)
    TextView mTvUserNameExpand;
    @Bind(R.id.tv_user_status)
    TextView mTvUserStatus;

    TextView mTvUserNameCollapse;
    CircularImageView mIvUserAvatarCollapse;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableSlidingMenu(false);
        setContentView(R.layout.activity_category);
        initView();
        loadData();
        renderLayout();
    }

    private void initView() {
        initAppBar();
        initToolBar();
        initViewPager();
        initHeader();
    }

    private void initAppBar() {
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                // mToolbar.getHeight() + verticalOffset + balancedValue == mCollapsingToolbar.getHeight()
                float balancedValue = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
                float percent = (float) Math.abs(verticalOffset) / (mCollapsingToolbarLayout.getHeight() - mToolbar.getHeight() - balancedValue);
                percent = percent > 1 ? 0 : 1 - percent;

                mIvUserAvatarExpand.setAlpha(percent);
                mTvUserNameExpand.setAlpha(percent);
                mTvUserStatus.setAlpha(percent);

                if (percent == 0) {
                    mTvUserNameCollapse.setVisibility(View.VISIBLE);
                    mIvUserAvatarCollapse.setVisibility(View.VISIBLE);
                } else {
                    mTvUserNameCollapse.setVisibility(View.INVISIBLE);
                    mIvUserAvatarCollapse.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void initToolBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        RelativeLayout mRlBack = (RelativeLayout) mToolbar.findViewById(R.id.rl_back);
        mRlBack.setOnClickListener(this);
        mTvUserNameCollapse = (TextView) mToolbar.findViewById(R.id.tv_user_name_collapse);
        mTvUserNameCollapse.setText(PreferenceUtils.getString(Constants.Preference.PREFERENCE_USER_FULL_NAME, ""));
        mIvUserAvatarCollapse = (CircularImageView) mToolbar.findViewById(R.id.iv_user_avatar_collapse);
        String picture = PreferenceUtils.getString(Constants.Preference.PREFERENCE_USER_PICTURE, "");
        if (!TextUtils.isEmpty(picture)) {
            Glide.with(this).load(picture).asBitmap().centerCrop().into(mIvUserAvatarCollapse);
        } else {
            mIvUserAvatarCollapse.setImageDrawable(Utils.getDrawable(R.drawable.img_default_error));
        }
    }

    private void initViewPager() {
        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mCollapsingToolbarLayout.setTitleEnabled(false);
        if (getIntent() != null && getIntent().hasExtra(Constants.IntentExtra.PAGE_INDEX)) {
            mViewPager.setCurrentItem(getIntent().getIntExtra(Constants.IntentExtra.PAGE_INDEX, 0));
        }
    }

    private void initHeader() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.img_header_profile);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @SuppressWarnings("ResourceType")
            @Override
            public void onGenerated(Palette palette) {
                mCollapsingToolbarLayout.setContentScrimColor(Color.argb(0, 255, 255, 255));
            }
        });
    }

    private void loadData() {

    }

    private void renderLayout() {
        mTvUserNameExpand.setText(PreferenceUtils.getString(Constants.Preference.PREFERENCE_USER_FULL_NAME, ""));

        String picture = PreferenceUtils.getString(Constants.Preference.PREFERENCE_USER_PICTURE, "");
        if (!TextUtils.isEmpty(picture)) {
            Glide.with(this).load(picture).asBitmap().centerCrop().into(mIvUserAvatarExpand);
        } else {
            mIvUserAvatarExpand.setImageDrawable(Utils.getDrawable(R.drawable.img_default_error));
        }

        String status = PreferenceUtils.getString(Constants.Preference.PREFERENCE_USER_STATUS, "");
        if (!TextUtils.isEmpty(status)) {
            mTvUserStatus.setText(Utils.showUserStatus(status));
        } else {
            mTvUserStatus.setText(Utils.showUserStatus(R.string.text_status_default));
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        HomeAdapter adapter = new HomeAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        adapter.addFrag(ActivityFragment.newInstance(), getString(R.string.text_activities).toUpperCase());
        adapter.addFrag(TripFragment.newInstance(bundle), getString(R.string.text_trips).toUpperCase());
        adapter.addFrag(CollectionFragment.newInstance(), getString(R.string.text_collections).toUpperCase());
        viewPager.setOffscreenPageLimit(adapter.getFragmentList().size());
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }
}
