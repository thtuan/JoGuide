package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.PlaceItemDTO;

/**
 * Created by Admin on 16/03/2016.
 */
public class PlaceDetailResponseDTO extends BaseResponseDTO {

    private PlaceItemDTO data; // du lieu

    public PlaceItemDTO getData() {
        return data;
    }

    public void setData( PlaceItemDTO data ) {
        this.data = data;
    }
}
