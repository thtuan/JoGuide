package com.boot.accommodation.dto.view;

import com.google.gson.annotations.SerializedName;

/**
 * DTO view profile
 *
 * @author tuanlt
 * @since 4:12 PM 7/11/2016
 */
public class ProfileCollectionViewDTO extends BaseDTO {
    @SerializedName("favouriteLocations")
    private PlaceCollectionDTO favouritePlaces; // list data favourite
    private TourCollectionDTO favouriteTours; // list data check in
    private TourCollectionDTO toursCreated; // tours Created
    private PlaceCollectionDTO locationsCreated; // location Created

    public TourCollectionDTO getToursCreated() {
        return toursCreated;
    }

    public void setToursCreated(TourCollectionDTO toursCreated) {
        this.toursCreated = toursCreated;
    }

    public TourCollectionDTO getFavouriteTours() {
        return favouriteTours;
    }

    public void setFavouriteTours(TourCollectionDTO favouriteTours) {
        this.favouriteTours = favouriteTours;
    }

    public PlaceCollectionDTO getFavouritePlaces() {
        return favouritePlaces;
    }

    public void setFavouritePlaces(PlaceCollectionDTO favouritePlaces) {
        this.favouritePlaces = favouritePlaces;
    }

    public PlaceCollectionDTO getLocationsCreated() {
        return locationsCreated;
    }

    public void setLocationsCreated(PlaceCollectionDTO locationsCreated) {
        this.locationsCreated = locationsCreated;
    }
}
