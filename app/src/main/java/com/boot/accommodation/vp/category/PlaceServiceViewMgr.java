package com.boot.accommodation.vp.category;

import com.boot.accommodation.dto.view.CategoryItemDTO;

import java.util.List;

/**
 * PlaceServiceViewMgr
 *
 * @author tuanlt
 * @since 4:19 PM 10/14/16
 */
public interface PlaceServiceViewMgr {

    /**
     * Render layout
     *
     * @param categoryLocationItems
     */
    void renderLayout(List<CategoryItemDTO> categoryLocationItems);

    /**
     * show error
     *
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);


}
