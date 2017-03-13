package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.view.ScheduleDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.TourModel;
import com.boot.accommodation.model.mgr.TourModelMgr;
import com.boot.accommodation.util.Utils;

/**
 * schedule presenter
 *
 * @author thtuan
 * @since 5:35 PM 18-05-2016
 */
public class SchedulePresenter implements SchedulePresenterMgr {

    private ScheduleMgr scheduleMgr;
    private TourModelMgr tourModelMgr;
    private String TAG = ""; // Tag

    public SchedulePresenter(ScheduleMgr mgr) {
        scheduleMgr = mgr;
        tourModelMgr = new TourModel();
        TAG = Utils.getTag(scheduleMgr.getClass());
    }

    /**
     * create schedule with tour plan id
     *
     * @param tourid      tour plan id
     * @param scheduleDTO thong tin schedule gui di
     */
    @Override
    public void createSchedule(String tourid, String tourPlanId ,final ScheduleDTO scheduleDTO) {
        //        scheduleMgr.returnResult(tour);
        tourModelMgr.createSchedule(tourid,tourPlanId ,scheduleDTO, new ModelCallBack<ScheduleDTO>(TAG) {
            @Override
            public void onSuccess(ScheduleDTO response) {
                scheduleMgr.showMessage();
                scheduleMgr.returnResult(scheduleDTO);
            }

            @Override
            public void onError( int errorCode, String error ) {
                scheduleMgr.showMessageErr(errorCode, error);
            }

        });
    }
}
