package com.boot.accommodation.dto.view;

/**
 * PromotionDealFilterDTO
 *
 * @author tuanlt
 * @since 5:20 PM 12/13/16
 */
public class PromotionDealFilterDTO extends BaseDTO {

    private Integer dealType; //Deal type
    private PagingDTO paging; //Paging

    public Integer getDealType() {
        return dealType;
    }

    public void setDealType(Integer dealType) {
        this.dealType = dealType;
    }

    public PagingDTO getPaging() {
        return paging;
    }

    public void setPaging(PagingDTO paging) {
        this.paging = paging;
    }
}
