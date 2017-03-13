package com.boot.accommodation.vp.login;

import com.boot.accommodation.base.BaseResponse;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.LoginModel;
import com.boot.accommodation.model.mgr.LoginModelMgr;

/**
 * Presenter for activity splash
 *
 * @author tuanlt
 * @since 12:15 AM 7/18/2016
 */
public class SplashPresenter implements SplashPresenterMgr {

    SplashViewMgr splashViewMgr; // view splash
    LoginModelMgr loginModelMgr; // presenter

    public SplashPresenter(SplashViewMgr splashViewMgr){
        loginModelMgr = new LoginModel();
        this.splashViewMgr = splashViewMgr;
    }

    @Override
    public void requestValidateGPlus( String token ) {
        loginModelMgr.requestValidateGPlus(token, new ModelCallBack() {
            @Override
            public void onSuccess( BaseResponse response ) {
                splashViewMgr.gotoHome();
            }

            @Override
            public void onError( int errorCode, String error ) {
                splashViewMgr.gotoLogin();
            }

        });
    }
}
