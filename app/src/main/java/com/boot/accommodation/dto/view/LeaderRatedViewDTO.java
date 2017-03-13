package com.boot.accommodation.dto.view;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO cho man hinh ds view
 *
 * @author vuong
 * @since: 18:31 AM 5/13/2016
 */
public class LeaderRatedViewDTO extends BaseDTO {
    private PagingDTO paging;
    private List<ReviewItemDTO> review;

    public PagingDTO getPaging() {
        return paging;
    }

    public void setPaging(PagingDTO paging) {
        this.paging = paging;
    }

    public List<ReviewItemDTO> getReview() {
        return review;
    }

    public void setReview( ArrayList<ReviewItemDTO> review ) {
        this.review = review;
    }
}
