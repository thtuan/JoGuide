package com.boot.accommodation.model.mgr;

import com.boot.accommodation.dto.view.PromotionDealFilterDTO;
import com.boot.accommodation.listener.ModelCallBack;

/**
 * Promotion model
 *
 * @author tuanlt
 * @since 10:54 SA 11/08/2016
 */
public interface PromotionModelMgr {

    /**
     * Get promotion deal type
     * @param modelCallBack
     */
    void getPromotionDealType(ModelCallBack modelCallBack);

    /**
     * Get promotion deal
     * @param locationFilterItemDTO
     * @param modelCallBack
     */
    void getPromotionDeal(PromotionDealFilterDTO locationFilterItemDTO, ModelCallBack modelCallBack);
}
