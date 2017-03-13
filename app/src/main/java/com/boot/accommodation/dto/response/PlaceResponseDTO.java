package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.PlaceViewDTO;

/**
 * Lop reponse dia diem
 *
 * @author tuanlt
 * @since: 6:53 PM 5/11/2016
 */
public class PlaceResponseDTO extends BaseResponseDTO {
    private PlaceViewDTO data;

    public PlaceViewDTO getData() {
        return data;
    }

    public void setData( PlaceViewDTO data ) {
        this.data = data;
    }
}
