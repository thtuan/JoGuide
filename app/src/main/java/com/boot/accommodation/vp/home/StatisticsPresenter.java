package com.boot.accommodation.vp.home;

import com.boot.accommodation.dto.response.StatisticResponseDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.HomeModel;
import com.boot.accommodation.util.Utils;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 3:38 PM 14-06-2016
 */
public class StatisticsPresenter implements StatisticsPresenterMgr {
    StatisticsMgr statisticsMgr;
    HomeModel statisticsModel;
    private String TAG = ""; // Tag

    public StatisticsPresenter(StatisticsMgr statisticsMgr) {
        this.statisticsMgr = statisticsMgr;
        statisticsModel = new HomeModel();
        TAG = Utils.getTag(statisticsMgr.getClass());
    }

    @Override
    public void getStatistics( String date) {
        statisticsModel.getStatistics(date, new ModelCallBack<StatisticResponseDTO>(TAG) {
            @Override
            public void onSuccess( StatisticResponseDTO response ) {
                statisticsMgr.renderLayout(response.getData());
            }

            @Override
            public void onError( int errorCode, String error ) {
                statisticsMgr.showMessageErr(errorCode, error);
            }

        });
    }
}
