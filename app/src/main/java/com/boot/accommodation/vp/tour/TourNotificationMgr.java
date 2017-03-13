package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.view.TourNotificationDTO;

import java.util.List;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 2:15 PM 17-05-2016
 */
public interface TourNotificationMgr {
    /**
     * render layout
     * @param tourNotificationDTOs
     */
    void renderLayout(List<TourNotificationDTO> tourNotificationDTOs);
    /**
     * show error
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);

    /**
     * request seen
     * @param tourNotificationDTOs
     */
    void requestSeen(List<TourNotificationDTO> tourNotificationDTOs);

}
