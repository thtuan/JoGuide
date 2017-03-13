package com.boot.accommodation.dto.view;

/**
 * Data search tour
 *
 * @author Vuong-bv
 * @since: 6/10/2016
 */
public class SearchTourViewDTO extends BaseDTO {
    private SearchTourDTO favourite; // data favourite
    private SearchTourDTO nearBy; // data near by

    public SearchTourDTO getFavourite() {
        return favourite;
    }

    public void setFavourite( SearchTourDTO favourite ) {
        this.favourite = favourite;
    }

    public SearchTourDTO getNearBy() {
        return nearBy;
    }

    public void setNearBy( SearchTourDTO nearBy ) {
        this.nearBy = nearBy;
    }
}
