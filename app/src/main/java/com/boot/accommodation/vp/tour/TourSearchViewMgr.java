package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.view.SearchTourViewDTO;

/**
 * View Mgr cho man hinh tour search place
 *
 * @author vuong-bv
 * @since: 9:25 AM 5/31/2016
 */
public interface TourSearchViewMgr {
    /**
     * render layout
     * @param searchTourDto
     */
    void renderLayout(SearchTourViewDTO searchTourDto);

    /**
     * when we get error
     * @param error
     */
    void getResultSearchError(int errorCode, String error);

}
