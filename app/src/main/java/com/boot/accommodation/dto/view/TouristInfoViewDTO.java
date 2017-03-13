package com.boot.accommodation.dto.view;

import java.util.ArrayList;

/**
 * DTO cho man hinh ds view
 *
 * @author vuong
 * @since: 18:31 AM 5/13/2016
 */
public class TouristInfoViewDTO extends BaseDTO {
    private int page;
    private int totalPages;
    private ArrayList<TouristInfoDTO> tourists;

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

    public ArrayList<TouristInfoDTO> getTourists() {
        return tourists;
    }

    public void setTourists( ArrayList<TouristInfoDTO> tours ) {
        this.tourists = tours;
    }

    public TouristInfoViewDTO() {
    }

}
