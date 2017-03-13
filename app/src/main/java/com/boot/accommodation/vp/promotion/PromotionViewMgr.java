package com.boot.accommodation.vp.promotion;

import com.boot.accommodation.dto.view.CategoryItemDTO;
import com.boot.accommodation.dto.view.PromotionDealDTO;

import java.util.List;

/**
 * PromotionViewMgr
 *
 * @author tuanlt
 * @since 4:53 PM 12/13/16
 */
public interface PromotionViewMgr {

    /**
     * Render promotion type
     * @param categoryItemDTOs
     */
    void renderPromotionType(List<CategoryItemDTO> categoryItemDTOs);

    /**
     * Render layout
     */
    void renderLayout(List<PromotionDealDTO> promotionDealDTOs);

    /**
     * Handle error
     * @param errorCode
     * @param error
     */
    void handleError(int errorCode, String error);

}
