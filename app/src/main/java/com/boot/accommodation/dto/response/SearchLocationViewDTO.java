package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.SearchLocationDTO;

/**
 * Data search location
 *
 * @author tuanlt
 * @since 11:30 AM 7/1/2016
 */
public class SearchLocationViewDTO {
    private SearchLocationDTO favourite; // data favourite
    private SearchLocationDTO nearBy; // data near by

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
