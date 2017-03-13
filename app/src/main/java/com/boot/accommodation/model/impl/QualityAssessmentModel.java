package com.boot.accommodation.model.impl;

import com.boot.accommodation.base.BaseModel;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.response.QualityAssessmentResponseDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.mgr.QualityAssessmentModelMgr;
import com.boot.accommodation.util.PreferenceUtils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Model chat luong tour
 *
 * @author tuanlt
 * @since 10:31 AM 6/20/2016
 */
public class QualityAssessmentModel extends BaseModel implements QualityAssessmentModelMgr {
    // API Vi tri
    private interface Interface {
        @GET("m/tour/{tourId}/plan/{tourPlanId}/getQualityAssessment")
        Call<QualityAssessmentResponseDTO> getQualityAssessment(
            @Path("tourId") long tourId,
            @Path("tourPlanId") long tourPlanId,
            @Query("date") String date,
            @Header(XAPITOKEN) String token
        );
    }

    /**
     * Lay chat luong tour
     *
     * @param tourId
     */
    @Override
    public void getQualityAssessment( long tourId, long tourPlanId, String date, ModelCallBack modelCallBack ) {
        Interface service = RETROFIT.create(Interface.class);
        call = service.getQualityAssessment(tourId, tourPlanId, date, PreferenceUtils.getString(Constants.Preference
            .PREFERENCE_USER_TOKEN, ""));
        requestAPI(modelCallBack);
    }
}
