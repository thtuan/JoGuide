package com.boot.accommodation.vp.location;

import com.boot.accommodation.base.BaseResponse;
import com.boot.accommodation.dto.view.PlaceReportDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.LocationModel;
import com.boot.accommodation.model.mgr.LocationModelMgr;
import com.boot.accommodation.util.Utils;

/**
 * Place detail report
 *
 * @author tuanlt
 * @since: 11:20 AM 5/5/2016
 */
public class LocationDetailReportPresenter implements LocationDetailReportPresenterMgr {

    private String TAG = ""; // Tag
    private LocationDetailReportViewMgr placeDetailReportViewMgr; //Place detail report
    private LocationModelMgr locationModelMgr; //Place detail model

    public LocationDetailReportPresenter(LocationDetailReportViewMgr placeDetailReportViewMgr) {
        this.placeDetailReportViewMgr = placeDetailReportViewMgr;
        TAG = Utils.getTag(placeDetailReportViewMgr.getClass());
        locationModelMgr = new LocationModel();
    }

    @Override
    public void requestReportLocation(long placeId, PlaceReportDTO placeReportDTO) {
        locationModelMgr.requestReportLocation(placeId, placeReportDTO, new ModelCallBack() {
            @Override
            public void onSuccess(BaseResponse response) {
                placeDetailReportViewMgr.showReportLocationSuccess();
            }

            @Override
            public void onError(int errorCode, String error) {
                placeDetailReportViewMgr.showMessageErr(errorCode, error);
            }

        });
    }
}
