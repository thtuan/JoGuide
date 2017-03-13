package com.boot.accommodation.vp.home;

import android.view.View;

import com.boot.accommodation.dto.view.StatisticsViewDTO;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 3:37 PM 14-06-2016
 */
public interface StatisticsMgr {
    /**
     * render layout
     * @param statisticsViewDTO
     */
    void renderLayout(StatisticsViewDTO statisticsViewDTO);

    /**
     * method onevent control
     * @param action
     * @param item
     * @param View
     * @param position
     */
    void onEventControl(int action, Object item, View View, int position);

    /**
     * show error
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);
}
