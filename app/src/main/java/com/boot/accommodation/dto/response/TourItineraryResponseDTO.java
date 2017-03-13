package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.TourItineraryViewDTO;

/**
 * Reponse cho man hinh lich trinh tour
 *
 * @author tuanlt
 * @since: 5/19/2016
 */
public class TourItineraryResponseDTO extends BaseResponseDTO {

    private TourItineraryViewDTO data;

    public TourItineraryViewDTO getData() {
        return data;
    }

    public void setData( TourItineraryViewDTO data ) {
        this.data = data;
    }
}
