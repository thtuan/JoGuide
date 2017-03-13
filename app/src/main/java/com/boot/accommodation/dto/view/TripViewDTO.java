package com.boot.accommodation.dto.view;

import java.util.List;

/**
 * DTO cho man hinh ds view
 *
 * @author tuanlt
 * @since: 9:31 AM 5/11/2016
 */
public class TripViewDTO extends BaseDTO {
    private PagingDTO paging;
    private List<TripItemDTO> tours;
    private int countNotification;

    public int getCountNotification() {
        return countNotification;
    }

    public void setCountNotification(int countNotification) {
        this.countNotification = countNotification;
    }

    public List<TripItemDTO> getTours() {
        return tours;
    }

    public void setTours(List<TripItemDTO> tours) {
        this.tours = tours;
    }

    public TripViewDTO() {
    }

    public PagingDTO getPaging() {
        return paging;
    }

    public void setPaging(PagingDTO paging) {
        this.paging = paging;
    }
}
