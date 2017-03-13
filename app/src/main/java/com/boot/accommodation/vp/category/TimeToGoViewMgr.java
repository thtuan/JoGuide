package com.boot.accommodation.vp.category;

import com.boot.accommodation.dto.view.AreaDTO;
import com.boot.accommodation.dto.view.CategoryItemDTO;

import java.util.List;

/**
 * TimeToGoViewMgr
 *
 * @author tuanlt
 * @since 2:56 PM 10/21/16
 */
public interface TimeToGoViewMgr {

    /**
     * Render layout
     *
     * @param month
     */
    void renderLayoutMonth(List<CategoryItemDTO> month);

    /**
     * show error
     *
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);

    /**
     * Render location filter
     * @param areaDTOs
     */
    void renderLayoutLocationFilter(List<AreaDTO> areaDTOs);

}
