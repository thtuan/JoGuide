package com.boot.accommodation.vp.login;

import android.os.Bundle;
import android.os.Handler;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.vp.accommodation.AccommodationActivity;
import com.boot.accommodation.vp.home.HomeActivity;

public class SplashActivity extends BaseActivity implements SplashViewMgr {

    private static final long SPLASH_DISPLAY_LENGTH = 3000;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableSlidingMenu(false);
        setContentView(R.layout.activity_splash_screen);
//        setTranslucentStatus();
//        View bg_transparent = findViewById(R.id.bg_transparent);
//        Animation anim = AnimationUtils.loadAnimation(this, R.anim.transparent_increase);
//        anim.setDuration(SPLASH_DISPLAY_LENGTH / 2);
//        bg_transparent.startAnimation(anim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
//                UserItemDTO user = JoCoApplication.getInstance().getProfile().getUserData();
//                if (!StringUtil.isNullOrEmpty(user.getId())) {
//                    goNextScreen(AccommodationActivity.class, true);
//                } else {
//                    goNextScreen(AccommodationActivity.class, true);
//                }
                goNextScreen(AccommodationActivity.class, true);
            }
        }, SPLASH_DISPLAY_LENGTH);
        reStartLocating();
    }

    @Override
    public void gotoHome(){
        goNextScreen(HomeActivity.class, false);
    }

    @Override
    public void gotoLogin() {
        goNextScreen(LoginActivity.class, false);
    }

}
