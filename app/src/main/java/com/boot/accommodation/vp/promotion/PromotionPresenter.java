package com.boot.accommodation.vp.promotion;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.dto.response.CategoryResponseDTO;
import com.boot.accommodation.dto.response.PromotionDealResponseDTO;
import com.boot.accommodation.dto.view.PagingDTO;
import com.boot.accommodation.dto.view.PromotionDealDTO;
import com.boot.accommodation.dto.view.PromotionDealFilterDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.PromotionModel;
import com.boot.accommodation.model.mgr.PromotionModelMgr;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Promotion presenter
 *
 * @author tuanlt
 * @since 10:48 CH 26/07/2016
 */
public class PromotionPresenter implements PromotionPresenterMgr {

    private PromotionModelMgr promotionModelMgr; //Promotion model
    private String TAG = ""; //Tag
    private PromotionViewMgr promotionViewMgr; //Promotion view
    private PagingDTO pagingDTO = new PagingDTO();
    private List<PromotionDealDTO> promotionDealDTOs = new ArrayList<>();

    public PromotionPresenter(PromotionViewMgr promotionViewMgr) {
        this.promotionViewMgr = promotionViewMgr;
        this.promotionModelMgr = new PromotionModel();
        TAG = Utils.getTag(this.promotionViewMgr.getClass());
    }

    @Override
    public void getPromotionDealType() {
        promotionModelMgr.getPromotionDealType(new ModelCallBack<CategoryResponseDTO>(TAG) {
            @Override
            public void onSuccess(CategoryResponseDTO response) {
                promotionViewMgr.renderPromotionType(response.getData());
            }

            @Override
            public void onError(int errorCode, String error) {
                promotionViewMgr.handleError(errorCode, error);
            }

        });
    }

    @Override
    public void getPromotionDeal(PromotionDealFilterDTO promotionDealFilterDTO) {
        pagingDTO.setPage(0);
        promotionDealDTOs.clear();
        handleGetPromotionDeal(promotionDealFilterDTO);
    }

    /**
     * Handle get promotion deal
     */
    private void handleGetPromotionDeal(PromotionDealFilterDTO promotionDealFilterDTO) {
        promotionDealFilterDTO.setPaging(pagingDTO);
        promotionModelMgr.getPromotionDeal(promotionDealFilterDTO, new ModelCallBack<PromotionDealResponseDTO>(TAG) {
            @Override
            public void onSuccess(PromotionDealResponseDTO response) {
                promotionDealDTOs.addAll(response.getData());
                promotionViewMgr.renderLayout(promotionDealDTOs);
            }

            @Override
            public void onError(int errorCode, String error) {
                promotionViewMgr.handleError(errorCode, error);
            }

        });
    }

    @Override
    public void getMorePromotionDeal(BaseRecyclerViewAdapter adapter, PromotionDealFilterDTO promotionDealFilterDTO) {
        if (Utils.checkLoadMore(adapter, pagingDTO.getPageSize(), promotionDealDTOs.size())) {
            pagingDTO.setPage(pagingDTO.getPage() + 1);
            handleGetPromotionDeal(promotionDealFilterDTO);
        }
    }
}
