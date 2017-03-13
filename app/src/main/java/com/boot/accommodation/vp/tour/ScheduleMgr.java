package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.view.ScheduleDTO;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 5:35 PM 18-05-2016
 */
public interface ScheduleMgr {
    /**
     * show message
     */
    void showMessage();

    /**
     * show error
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);

    /**
     * set ket qua tra ve
     */
    void returnResult(ScheduleDTO scheduleDTO);
}
