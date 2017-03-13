package com.boot.accommodation.vp.accommodation;

import com.boot.accommodation.dto.view.AreaDTO;

import java.util.List;

/**
 * AccommodationViewMgr
 *
 * @author tuanlt
 * @since 4:44 PM 1/13/17
 */
public interface AccommodationViewMgr {

    /**
     * Render area
     */
    void renderAreaFilter(List<AreaDTO> areaDTOs);

    /**
     * Render special
     * @param areaDTOs
     */
    void renderAreaSpecial(List<AreaDTO> areaDTOs);

    /**
     * show error
     *
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);

    /**
     * Request send feedback
     */
    void requestSendFeedbackSuccess();

}
