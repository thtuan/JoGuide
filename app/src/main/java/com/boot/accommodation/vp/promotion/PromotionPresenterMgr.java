package com.boot.accommodation.vp.promotion;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.dto.view.PromotionDealFilterDTO;

/**
 * Promotion presenter
 *
 * @author tuanlt
 * @since 10:47 CH 12/12/2016
 */
public interface PromotionPresenterMgr {

    /**
     * Get promotion type
     */
    void getPromotionDealType();

    /**
     * Promotion deal filter
     * @param promotionDealFilterDTO
     */
    void getPromotionDeal(PromotionDealFilterDTO promotionDealFilterDTO);

    /**
     * Get more promotion deal
     * @param promotionDealFilterDTO
     */
    void getMorePromotionDeal(BaseRecyclerViewAdapter adapter, PromotionDealFilterDTO promotionDealFilterDTO);
}
