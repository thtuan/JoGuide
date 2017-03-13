package com.boot.accommodation.vp.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.ImageView;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.vp.location.LocationListFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Favourite Activity
 *
 * @author tuanlt
 * @since 11:50 AM 6/13/2016
 */
public class MyFavouriteActivity extends BaseActivity {

    @Bind(R.id.rlFragmentMap)
    CoordinatorLayout rlBackground;
    @Bind(R.id.ivBack)
    ImageView ivBack;
    int position; //Position image
    private long userId; //User id

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        userId = Long.valueOf(JoCoApplication.getInstance().getProfile().getUserData().getId());
        Intent intent = getIntent();
        if (intent != null) {
            position = intent.getIntExtra(Constants.IntentExtra.POSITION, 0);
            if(intent.hasExtra(Constants.IntentExtra.USER_ID)) {
                userId = intent.getLongExtra(Constants.IntentExtra.USER_ID, 0);
            }
        }
        enableSlidingMenu(false);
        setContentView(R.layout.activity_favourite);
        initView();
    }

    /**
     * Khoi tao view
     */
    private void initView() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.IntentExtra.FROM_MY_FAVOURITE, true);
        bundle.putString(Constants.IntentExtra.USER_ID, String.valueOf(userId));
        bundle.putString(Constants.IntentExtra.SEARCH_TEXT,"");
        switchFragment(LocationListFragment.newInstance(bundle),R.id.frDetail,false);
    }

    @OnClick(R.id.ivBack)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivBack:
                super.onBackPressed();
                break;
        }
    }
}
