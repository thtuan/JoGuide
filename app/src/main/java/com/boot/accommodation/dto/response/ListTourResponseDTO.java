package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.ListTourViewDTO;

/**
 * DTO chua thong tin ListTour
 *
 * @author tuanlt
 * @since 3:22 PM 5/19/2016
 */
public class ListTourResponseDTO extends BaseResponseDTO {

    private ListTourViewDTO data; // du lieu search

    public ListTourViewDTO getData() {
        return data;
    }//get data

    public void setData( ListTourViewDTO data ) {
        this.data = data;
    }//setdata
}
