package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.TripViewDTO;

/**
 * Nhan respone ds tour
 *
 * @author tuanlt
 * @since: 9:24 AM 5/11/2016
 */
public class TripResponseDTO extends BaseResponseDTO {
    private TripViewDTO data;

    public TripViewDTO getData() {
        return data;
    }

    public void setData( TripViewDTO data ) {
        this.data = data;
    }
}
