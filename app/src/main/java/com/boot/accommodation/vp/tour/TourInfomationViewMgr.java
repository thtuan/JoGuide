package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.view.TourInformationDTO;
import com.boot.accommodation.dto.view.TripItemDTO;

import java.util.List;

/**
 * View Mgr cho man hinh tour info expand
 *
 * @author vuong-bv
 * @since: 9:25 AM 5/31/2016
 */
public interface TourInfomationViewMgr {
    /**
     * render layout
     * @param tourtInfomationDTO
     */
    void renderLayout(TourInformationDTO tourtInfomationDTO);

    /**
     * Render tour relate
     * @param tripItemDTO
     */
    void renderTourRelate(List<TripItemDTO> tripItemDTO);

    /**
     * show error
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);

}
