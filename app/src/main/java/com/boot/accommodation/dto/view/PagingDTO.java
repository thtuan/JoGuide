package com.boot.accommodation.dto.view;

/**
 * DTO cho phan paging
 *
 * @author tuanlt
 * @since 2:33 PM 6/8/2016
 */
public class PagingDTO extends BaseDTO {
    private int page;
    private int pageSize;
    private int total;

    public int getPage() {
        return page;
    }

    public void setPage( int page ) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize( int pageSize ) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal( int total ) {
        this.total = total;
    }
}
