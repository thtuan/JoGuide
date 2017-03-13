package com.boot.accommodation.dto.view;

import java.util.ArrayList;

/**
 * DTO cho man hinh ds view
 *
 * @author vuong
 * @since: 18:31 AM 5/13/2016
 */
public class DiscussViewDTO extends BaseDTO {
    private PagingDTO paging; // paging
    private ArrayList<ReviewItemDTO> tourDiscuss; // list review

    public PagingDTO getPaging() {
        return paging;
    }

    public void setPaging(PagingDTO paging) {
        this.paging = paging;
    }

    public ArrayList<ReviewItemDTO> getTourDiscuss() {
        return tourDiscuss;
    }

    public void setTourDiscuss(ArrayList<ReviewItemDTO> tourDiscuss) {
        this.tourDiscuss = tourDiscuss;
    }
}
