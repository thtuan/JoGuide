package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.PromotionDealDTO;

import java.util.List;

/**
 * PromotionDealResponseDTO
 *
 * @author tuanlt
 * @since 5:45 PM 12/13/16
 */
public class PromotionDealResponseDTO extends BaseResponseDTO {

    private List<PromotionDealDTO> data; //Data

    public List<PromotionDealDTO> getData() {
        return data;
    }

    public void setData(List<PromotionDealDTO> data) {
        this.data = data;
    }
}
