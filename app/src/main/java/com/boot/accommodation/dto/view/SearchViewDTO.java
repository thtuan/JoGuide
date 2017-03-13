package com.boot.accommodation.dto.view;

import java.util.List;

/**
 * DTO cho man hinh search
 *
 * @author tuanlt
 * @since 3:25 PM 5/19/2016
 */
public class SearchViewDTO extends BaseDTO{
    private int page;
    private int totalPages;
    private List<SearchDTO> search;

    public int getPage() {
        return page;
    }

    public void setPage( int page ) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages( int totalPages ) {
        this.totalPages = totalPages;
    }

    public List<SearchDTO> getSearch() {
        return search;
    }

    public void setSearch( List<SearchDTO> search ) {
        this.search = search;
    }
}
