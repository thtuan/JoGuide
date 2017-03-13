package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.TourPlacePictureViewDTO;

/**
 * Tour place picture resonse
 *
 * @author tuanlt
 * @since 10:55 CH 14/08/2016
 */
public class TourPlacePictureResponseDTO extends BaseResponseDTO {
    private TourPlacePictureViewDTO data; // Data response from server

    public TourPlacePictureViewDTO getData() {
        return data;
    }

    public void setData( TourPlacePictureViewDTO data ) {
        this.data = data;
    }
}
