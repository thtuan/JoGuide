package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.TourInfoItemDTO;

/**
 * Lop repone cho man hinh thong tin tour
 *
 * @author tuanlt
 * @since 2:01 PM 5/16/2016
 */
public class TourInfoResponseDTO extends BaseResponseDTO {
    private TourInfoItemDTO data;

    public TourInfoItemDTO getData() {
        return data;
    }

    public void setData( TourInfoItemDTO data ) {
        this.data = data;
    }
}
