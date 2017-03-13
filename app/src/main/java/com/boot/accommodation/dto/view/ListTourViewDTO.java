package com.boot.accommodation.dto.view;

import java.util.List;

/**
 * DTO cho man hinh ListTOur
 *
 * @author vuongbv
 * @since 3:25 PM 20/6/2016
 */
public class ListTourViewDTO extends BaseDTO {
    private PagingDTO paging;
    private List<ListTourDTO> tour;

    public PagingDTO getPage() {
        return paging;
    }

    public void setPage(PagingDTO page) {
        this.paging = page;
    }

    public List<ListTourDTO> getListTourDTO() {
        return tour;
    }

    public void setListTourDTO(List<ListTourDTO> listTourDTO) {
        this.tour = listTourDTO;
    }
}
