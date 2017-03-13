package com.boot.accommodation.dto.view;

import java.util.List;

/**
 * Data searchTypeList Location
 *
 * @author tuanlt
 * @since 11:27 AM 7/1/2016
 */
public class SearchLocationDTO extends BaseDTO {
    private PagingDTO paging; // paging
    private List<PlaceItemDTO> searchTypeList; // data search

    public PagingDTO getPaging() {
        return paging;
    }

    public void setPaging( PagingDTO paging ) {
        this.paging = paging;
    }

    public List<PlaceItemDTO> getSearchTypeList() {
        return searchTypeList;
    }

    public void setSearchTypeList(List<PlaceItemDTO> searchTypeList) {
        this.searchTypeList = searchTypeList;
    }
}
