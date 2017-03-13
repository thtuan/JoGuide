package com.boot.accommodation.vp.profile;

import com.boot.accommodation.dto.view.TripItemDTO;

import java.util.List;

/**
 * AdminCollectionFragmentMgr
 *
 * @author thtuan
 * @since 2:41 PM 14-07-2016
 */
public interface AdminCollectionFragmentMgr {


    /**
     * render layout
     * @param mListTrips
     */
    void renderLayout(List<TripItemDTO> mListTrips);

    /**
     * show error
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);

}
