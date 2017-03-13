package com.boot.accommodation.vp.location;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.util.StringUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Favourite user
 *
 * @author tuanlt
 * @since 5:10 PM 12/12/16
 */
public class VehicleActivity extends BaseActivity {


    @Bind(R.id.wvVehicle)
    WebView wvVehicle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        ButterKnife.bind(this);
        enableSlidingMenu(false);
        Bundle bundle = getIntent().getExtras();
        String content = "";
        if (bundle != null) {
            content = bundle.getString(Constants.IntentExtra.INTENT_DATA);
        }
        wvVehicle.loadDataWithBaseURL(null,
                StringUtil.getHtmlResizeImage(content), "text/html", "utf-8", null);
    }

    @OnClick({R.id.ivBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
        }
    }
}
