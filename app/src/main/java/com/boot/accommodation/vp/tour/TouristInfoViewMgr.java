package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.view.TouristInfoDTO;

import java.util.ArrayList;

/**
 * View Mgr cho man hinh tourist info
 *
 * @author vuong
 * @since: 15:49 PM 5/13/2016
 */
public interface TouristInfoViewMgr {
    /**
     * renderlayout touristinfo
     *
     * @param touristInfoItemDTO
     */
    void renderLayout(ArrayList<TouristInfoDTO> touristInfoItemDTO);

    /**
     * show error
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);
}
