package com.boot.accommodation.dto.view;

import java.util.List;

/**
 * DTO cho man hinh dia diem
 *
 * @author tuanlt
 * @since 3:19 PM 5/20/2016
 */
public class PlaceViewDTO {
    private PagingDTO paging; // trang
    private List<PlaceItemDTO> locations; // dia diem
    private List<AreaDTO> locationFilter; //location filter

    public List<PlaceItemDTO> getLocations() {
        return locations;
    }

    public void setLocations( List<PlaceItemDTO> locations ) {
        this.locations = locations;
    }

    public PagingDTO getPaging() {
        return paging;
    }

    public void setPaging( PagingDTO paging ) {
        this.paging = paging;
    }

    public List<AreaDTO> getLocationFilter() {
        return locationFilter;
    }

    public void setLocationFilter(List<AreaDTO> locationFilter) {
        this.locationFilter = locationFilter;
    }
}
