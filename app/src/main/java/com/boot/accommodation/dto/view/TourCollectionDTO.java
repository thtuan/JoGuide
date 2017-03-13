package com.boot.accommodation.dto.view;

import java.util.List;

/**
 * Profile collection tour data
 *
 * @author tuanlt
 * @date Jul 11, 2016
 * @since 1.0
 */
public class TourCollectionDTO {
    private int total;
    private List<TripItemDTO> tours; // list data tour

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<TripItemDTO> getTours() {
        return tours;
    }

    public void setTours(List<TripItemDTO> tours) {
        this.tours = tours;
    }
}
