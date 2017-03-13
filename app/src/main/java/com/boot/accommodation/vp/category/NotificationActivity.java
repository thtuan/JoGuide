package com.boot.accommodation.vp.category;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.common.control.JoCoEditText;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.vp.tour.TourNotificationFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends BaseActivity implements View.OnKeyListener{

    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.edSearch)
    JoCoEditText edSearch;
    @Bind(R.id.iv_search)
    ImageView ivSearch;
    @Bind(R.id.rl_search)
    RelativeLayout rlSearch;
    @Bind(R.id.ll_header)
    RelativeLayout llHeader;
    @Bind(R.id.frNotificationActivity)
    FrameLayout frNotificationActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        enableSlidingMenu(false);
        ButterKnife.bind(this);
        edSearch.setImageSearchVisible(true);
        edSearch.setOnKeyListener(this);
        init();
    }

    private void init() {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.IntentExtra.FROM_NOTIFICATION_MENU, 1);
        switchFragment(TourNotificationFragment.newInstance(bundle), R.id.frNotificationActivity, false);
    }

    @OnClick({R.id.iv_back, R.id.edSearch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.edSearch:
                break;
        }
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)) {
            handleSearch();
            return true;
        }
        return false;
    }

    private void handleSearch() {
        String searchText = StringUtil.getEngStringFromUnicodeString(edSearch.getText().toString());
        if (!StringUtil.isNullOrEmpty(searchText)) {
        } else {
            init();
        }
    }
}
