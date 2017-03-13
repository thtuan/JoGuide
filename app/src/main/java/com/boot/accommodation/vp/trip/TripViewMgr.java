package com.boot.accommodation.vp.trip;

import com.boot.accommodation.dto.view.TripItemDTO;

import java.util.List;

/**
 * Mgr cho module trip
 *
 * @author tuanlt
 * @since: 9:54 AM 5/5/2016
 */
public interface TripViewMgr {

    /**
     * render layout
     * @param mListTrips
     */
    void renderLayout(List<TripItemDTO> mListTrips);

    /**
     * Xu li tour that bai
     * @param errorCode
     * @param error
     */
    void getToursError( int errorCode, String error );// lay tour that bai

    /**
     *
     * @param count
     */
    void setNotification(int count);
}
