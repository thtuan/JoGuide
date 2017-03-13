package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.PlacePickerDetailItemDTO;

/**
 * Place picker detail response
 *
 * @author tuanlt
 * @since 11:07 SA 10/09/2016
 */
public class PlacePickerDetailResponseDTO extends BaseResponseDTO {
    private PlacePickerDetailItemDTO result;

    public PlacePickerDetailItemDTO getResult() {
        return result;
    }

    public void setResult( PlacePickerDetailItemDTO result ) {
        this.result = result;
    }
}
