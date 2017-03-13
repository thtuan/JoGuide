package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.TourPlaceViewDTO;

/**
 * DTO respone tour place
 *
 * @author tuanlt
 * @since 6:09 PM 5/26/2016
 */
public class TourPlaceResponseDTO extends BaseResponseDTO {
    private TourPlaceViewDTO data; // data response

    public TourPlaceViewDTO getData() {
        return data;
    }

    public void setData( TourPlaceViewDTO data ) {
        this.data = data;
    }
}
