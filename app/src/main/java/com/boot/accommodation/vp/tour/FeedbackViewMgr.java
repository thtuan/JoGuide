package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.view.FeedbackItemDTO;
import com.boot.accommodation.dto.view.FeedbackTourItemDTO;

import java.util.ArrayList;

/**
 * View Mgr cho man hinh tour search place
 *
 * @author vuong-bv
 * @since: 9:25 AM 5/31/2016
 */
public interface FeedbackViewMgr {
    /**
     * render layout
     * @param lstFeedback
     */
    void renderLayout( ArrayList<FeedbackItemDTO> lstFeedback);

    /**
     * render listtour at spinner
     * @param listTour
     */
    void renderListTour(ArrayList<FeedbackTourItemDTO> listTour);

    /**
     * show error
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);

    /**
     * when result null
     */
    void getFeedbacBlank();

}
