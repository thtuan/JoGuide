package com.boot.accommodation.vp.common;

import com.boot.accommodation.base.BaseViewMgr;
import com.boot.accommodation.dto.response.CommonResponseDTO;
import com.boot.accommodation.dto.response.LoginResponseDTO;
import com.boot.accommodation.dto.view.TabletActionLogDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.CommonModel;
import com.boot.accommodation.model.mgr.CommonModelMgr;

/**
 * Common presenter
 *
 * @author tuanlt
 * @since 10:48 CH 26/07/2016
 */
public class CommonPresenter implements CommonPresenterMgr {

    CommonModelMgr commonModelMgr; // Common model
    private static CommonPresenter instance = null; // Instance
    private BaseViewMgr baseViewMgr; //Base view mgr

    public static CommonPresenter getInstance() {
        if (instance == null) {
            instance = new CommonPresenter();
        }
        return instance;
    }

    public CommonPresenter() {
        commonModelMgr = new CommonModel();
    }


    @Override
    public void requestUpdateLog( TabletActionLogDTO tabletActionLogDTO ) {
        commonModelMgr.requestUpdateLog(tabletActionLogDTO, new ModelCallBack<CommonResponseDTO>() {
            @Override
            public void onSuccess( CommonResponseDTO response ) {

            }

            @Override
            public void onError( int errorCode, String error ) {

            }

        });
    }

    @Override
    public void cancelListRequest( String tag ) {
        commonModelMgr.cancelRequest(tag);
    }

    @Override
    public void requestRenewToken(String userId) {
        commonModelMgr.requestRenewToken(userId, new ModelCallBack<LoginResponseDTO>() {
            @Override
            public void onSuccess(LoginResponseDTO response) {
                baseViewMgr.updateToken(response.getData().getToken());
            }

            @Override
            public void onError(int errorCode, String error) {

            }

        });
    }

    public void setBaseViewMgr(BaseViewMgr baseViewMgr) {
        this.baseViewMgr = baseViewMgr;
    }
}
