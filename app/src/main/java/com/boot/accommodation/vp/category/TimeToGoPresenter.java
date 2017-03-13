package com.boot.accommodation.vp.category;

import com.boot.accommodation.dto.response.FamousLocationResponseDTO;
import com.boot.accommodation.dto.response.TimeToGoResponseDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.HomeModel;
import com.boot.accommodation.model.impl.LocationModel;
import com.boot.accommodation.model.impl.TourModel;
import com.boot.accommodation.model.mgr.HomeModelMgr;
import com.boot.accommodation.model.mgr.LocationModelMgr;
import com.boot.accommodation.model.mgr.TourModelMgr;
import com.boot.accommodation.util.Utils;

/**
 * TimeToGoPresenterMgr
 *
 * @author tuanlt
 * @since 3:17 PM 10/21/16
 */
public class TimeToGoPresenter implements TimeToGoPresenterMgr {

    private String TAG = ""; // Tag
    private TimeToGoViewMgr timeToGoViewMgr; //Time to go view
    private LocationModelMgr locationModelMgr;
    private HomeModelMgr homeModelMgr; //Home model
    private TourModelMgr tourModelMgr; //Tour model

    public TimeToGoPresenter(TimeToGoViewMgr timeToGoViewMgr) {
        this.timeToGoViewMgr = timeToGoViewMgr;
        TAG = Utils.getTag(timeToGoViewMgr.getClass());
        locationModelMgr = new LocationModel();
        homeModelMgr = new HomeModel();
        tourModelMgr = new TourModel();
    }

    @Override
    public void getMonth() {
        locationModelMgr.getMonth(new ModelCallBack<TimeToGoResponseDTO>(TAG) {
            @Override
            public void onSuccess(TimeToGoResponseDTO response) {
                timeToGoViewMgr.renderLayoutMonth(response.getData());
            }

            @Override
            public void onError(int errorCode, String error) {
                timeToGoViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void getAreaFilter() {
        homeModelMgr.getFilterAreas(new ModelCallBack<FamousLocationResponseDTO>() {
            @Override
            public void onSuccess(FamousLocationResponseDTO response) {
                timeToGoViewMgr.renderLayoutLocationFilter(response.getData());
            }

            @Override
            public void onError(int errorCode, String error) {
                timeToGoViewMgr.showMessageErr(errorCode, error);
            }

        });
    }
}
