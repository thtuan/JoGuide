package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.TripItemDTO;

/**
 * TourPlanResponseDTO
 *
 * @author thtuan
 * @since 11:00 AM 26-08-2016
 */
public class TourPlanResponseDTO extends BaseResponseDTO {
    private TripItemDTO data;

    public TripItemDTO getData() {
        return data;
    }

    public void setData(TripItemDTO data) {
        this.data = data;
    }
}
