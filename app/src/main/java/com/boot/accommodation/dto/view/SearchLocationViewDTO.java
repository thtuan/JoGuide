package com.boot.accommodation.dto.view;

/**
 * Data search location
 *
 * @author Vuong-bv
 * @since: 6/10/2016
 */
public class SearchLocationViewDTO extends BaseDTO {
    private SearchLocationDTO favourite; // Data search favourite
    private SearchLocationDTO nearBy; // Data search nearby

    public SearchLocationDTO getFavourite() {
        return favourite;
    }

    public void setFavourite( SearchLocationDTO favourite ) {
        this.favourite = favourite;
    }

    public SearchLocationDTO getNearBy() {
        return nearBy;
    }

    public void setNearBy( SearchLocationDTO nearBy ) {
        this.nearBy = nearBy;
    }
}
