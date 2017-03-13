package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.view.ScheduleDTO;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 5:34 PM 18-05-2016
 */
public interface SchedulePresenterMgr {
    void createSchedule(String tourid, String tourPlanId ,ScheduleDTO scheduleDTO);
}
