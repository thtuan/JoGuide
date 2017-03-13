package com.boot.accommodation.dto.view;

import java.util.ArrayList;

/**
 * DTO cho man hinh lich trinh tour
 *
 * @author tuanlt
 * @since 10:37 AM 6/3/2016
 */
public class TourItineraryViewDTO {
    private ArrayList<TourItineraryItemDTO> tourItinerary; // ds lich trinh tour

    public ArrayList<TourItineraryItemDTO> getTourItinerary() {
        return tourItinerary;
    }

    public void setTourItinerary( ArrayList<TourItineraryItemDTO> tourItinerary ) {
        this.tourItinerary = tourItinerary;
    }
}
