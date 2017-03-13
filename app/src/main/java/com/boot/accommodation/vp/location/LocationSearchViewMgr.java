package com.boot.accommodation.vp.location;

import com.boot.accommodation.dto.view.PlaceItemDTO;

import java.util.List;

/**
 * View Mgr cho man hinh tour search place
 *
 * @author vuong-bv
 * @since: 9:25 AM 5/31/2016
 */
public interface LocationSearchViewMgr {
    /**
     * render layout
     */
    void renderLayout(List<PlaceItemDTO> favourite, List<PlaceItemDTO> nearBy);

    /**
     * show error
     *
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);

}
