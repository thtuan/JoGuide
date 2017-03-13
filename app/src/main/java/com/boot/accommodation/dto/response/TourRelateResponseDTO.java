package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.TourRelateViewDTO;

/**
 * Tour relate response
 *
 * @author vuong-bv
 * @since: 9:25 AM 5/31/2016
 */
public class TourRelateResponseDTO extends BaseResponseDTO {
    /**
     * set data
     */
    private TourRelateViewDTO data;

    /**
     * method get
     *
     * @return
     */
    public TourRelateViewDTO getData() {
        return data;
    }

    /**
     * method set
     *
     * @param data
     */
    public void setData(TourRelateViewDTO data) {
        this.data = data;
    }
}
