package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.view.ListTourDTO;

import java.util.List;

/**
 * View Mgr cho man hinh tour search place
 *
 * @author vuong-bv
 * @since: 9:25 AM 5/31/2016
 */
public interface ListTourViewMgr {
    /**
     * render layout
     * @param listTourDTO
     */
    void renderLayout(List<ListTourDTO> listTourDTO);

    /**
     * show error
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);

}
