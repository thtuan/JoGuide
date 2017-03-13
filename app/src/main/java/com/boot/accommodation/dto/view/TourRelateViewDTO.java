package com.boot.accommodation.dto.view;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * DTO for expand tour info
 *
 * @author Vuong-bv
 * @since: 5/13/2016
 */
public class TourRelateViewDTO extends BaseDTO {

    @SerializedName("search")
    private List<TripItemDTO> tourRelate;// List tour relate
    private PagingDTO paging; // paging

    public PagingDTO getPaging() {
        return paging;
    }

    public void setPaging(PagingDTO paging) {
        this.paging = paging;
    }

    public TourRelateViewDTO() {
    }

    public List<TripItemDTO> getTourRelate() {
        return tourRelate;
    }

    public void setTourRelate(List<TripItemDTO> tourRelate) {
        this.tourRelate = tourRelate;
    }
}
