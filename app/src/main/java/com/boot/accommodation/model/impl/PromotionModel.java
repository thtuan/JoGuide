package com.boot.accommodation.model.impl;

import com.boot.accommodation.base.BaseModel;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.response.CategoryResponseDTO;
import com.boot.accommodation.dto.response.PromotionDealResponseDTO;
import com.boot.accommodation.dto.view.PromotionDealFilterDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.mgr.PromotionModelMgr;
import com.boot.accommodation.util.PreferenceUtils;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Promotion model
 *
 * @author tuanlt
 * @since 11:01 SA 11/08/2016
 */
public class PromotionModel extends BaseModel implements PromotionModelMgr {

    private interface Interface {

        @GET("other-feature/promotion-deal/type")
        Call<CategoryResponseDTO> getPromotionDealType(
                @Header(XAPITOKEN) String token
        );

        @POST("other-feature/promotion-deal/latest")
        Call<PromotionDealResponseDTO> getPromotionDeal(
                @Body PromotionDealFilterDTO locationFilterItemDTO,
                @Header(XAPITOKEN) String token
        );
    }

    @Override
    public void getPromotionDealType(ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getPromotionDealType(PreferenceUtils.getString(Constants.Preference
                .PREFERENCE_USER_TOKEN, ""));
        requestAPI(modelCallBack);
    }

    @Override
    public void getPromotionDeal( PromotionDealFilterDTO locationFilterItemDTO, ModelCallBack modelCallBack) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getPromotionDeal(locationFilterItemDTO, PreferenceUtils.getString(Constants.Preference
                .PREFERENCE_USER_TOKEN, ""));
        requestAPI(modelCallBack);
    }
}
