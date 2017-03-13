package com.boot.accommodation.model.impl;

import com.boot.accommodation.base.BaseModel;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.response.CommonResponseDTO;
import com.boot.accommodation.dto.response.LoginResponseDTO;
import com.boot.accommodation.dto.view.FeedbackItemDTO;
import com.boot.accommodation.dto.view.TabletActionLogDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.mgr.CommonModelMgr;
import com.boot.accommodation.util.PreferenceUtils;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Common model
 *
 * @author tuanlt
 * @since 11:01 SA 11/08/2016
 */
public class CommonModel extends BaseModel implements CommonModelMgr {

    private interface Interface {

        @POST("user/mobile/log")
        Call<CommonResponseDTO> requestUpdateLog(
            @Body TabletActionLogDTO tabletActionLogDTO
        );

        @GET("user/{userId}/renew-token")
        Call<LoginResponseDTO> requestRenewToken(
                @Path("userId") String userId,
                @Header(XAPITOKEN) String token
        );

        @POST("m/tour/plan/{tourPlanId}/feedback")
        Call<CommonResponseDTO> requestSendFeedback(
                @Path("tourPlanId") long tourPlanId,
                @Body FeedbackItemDTO feedbackItemDTO
        );
    }

    @Override
    public void requestUpdateLog( TabletActionLogDTO tabletActionLogDTO, ModelCallBack modelCallBack ) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestUpdateLog(tabletActionLogDTO);
        // goi request
        requestAPI(modelCallBack);
    }

    @Override
    public void cancelRequest( String tag ) {
        super.cancelRequest(tag);
    }

    @Override
    public void requestRenewToken(String userId, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestRenewToken(userId, PreferenceUtils.getString(Constants.Preference
                .PREFERENCE_USER_TOKEN, ""));
        // goi request
        requestAPI(modelCallBack);
    }

    @Override
    public void requestSendFeedback(FeedbackItemDTO feedbackItemDTO, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.requestSendFeedback(feedbackItemDTO.getTourPlanId(), feedbackItemDTO);
        requestAPI(modelCallBack);
    }


}
