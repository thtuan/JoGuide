package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.view.TourInfoItemDTO;

/**
 * View Mgr cho man hinh tour info
 *
 * @author tuanlt
 * @since: 11:49 PM 5/12/2016
 */
public interface TourInfoViewMgr {
    /**
     * render layout
     * @param tourInfoItemDTO
     */
    void renderLayout(TourInfoItemDTO tourInfoItemDTO);

    /**
     * show error
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);

}
