package com.boot.accommodation.dto.view;

import java.util.List;

/**
 * Data search tour
 *
 * @author tuanlt
 * @since 11:05 AM 7/1/2016
 */
public class SearchTourDTO extends BaseDTO {
    private PagingDTO paging; // paging
    private List<TripItemDTO> search; // list data search

    public PagingDTO getPaging() {
        return paging;
    }

    public void setPaging( PagingDTO paging ) {
        this.paging = paging;
    }

    public List<TripItemDTO> getSearch() {
        return search;
    }

    public void setSearch( List<TripItemDTO> search ) {
        this.search = search;
    }
}
