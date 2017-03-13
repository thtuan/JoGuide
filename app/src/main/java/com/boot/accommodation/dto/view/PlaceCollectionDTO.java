package com.boot.accommodation.dto.view;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Profile collection location data
 *
 * @author tuanlt
 * @date Jul 11, 2016
 * @since 1.0
 */
public class PlaceCollectionDTO {
    private int total; // Total
    @SerializedName("locations")
    private List<PlaceItemDTO> places;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


    public List<PlaceItemDTO> getPlaces() {
        return places;
    }

    public void setPlaces(List<PlaceItemDTO> places) {
        this.places = places;
    }
}
