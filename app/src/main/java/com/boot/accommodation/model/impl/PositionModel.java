package com.boot.accommodation.model.impl;

import com.boot.accommodation.base.BaseModel;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.response.CommonResponseDTO;
import com.boot.accommodation.dto.view.UserPositionLogDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.mgr.PositionModelMgr;
import com.boot.accommodation.util.PreferenceUtils;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Model xu li vi tri
 *
 * @author tuanlt
 * @since 2:48 PM 6/7/2016
 */
public class PositionModel extends BaseModel implements PositionModelMgr {

    // API Vi tri
    private interface Interface {
        @POST("user/position")
        Call<CommonResponseDTO> updatePosition(
            @Body UserPositionLogDTO positionLogDTO,
            @Header(XAPITOKEN) String token
        );
    }

    /**
     * Day vi tri toi server
     *
     * @param userPositionLogDTO
     */
    @Override
    public void updatePosition( UserPositionLogDTO userPositionLogDTO, ModelCallBack modelCallBack ) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.updatePosition(userPositionLogDTO, PreferenceUtils.getString(Constants.Preference.PREFERENCE_USER_TOKEN, ""));
        // goi request
        requestAPI(modelCallBack);
    }
}
