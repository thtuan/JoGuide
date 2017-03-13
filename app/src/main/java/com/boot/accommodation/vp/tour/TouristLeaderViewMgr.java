package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.view.TouristLeaderDTO;

/**
 * Mo ta class
 *
 * @author Vuong-bv
 * @since: 5/19/2016
 */
public interface TouristLeaderViewMgr {
    /**
     * method render layout to view
     *
     * @param leaderDTO
     */
    void renderLayout(TouristLeaderDTO leaderDTO);

    /**
     * show error
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);
}
