package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.FeedbackViewDTO;

/**
 * DTO chua thong tin ListTour
 *
 * @author tuanlt
 * @since 3:22 PM 5/19/2016
 */
public class FeedbackResponseDTO extends BaseResponseDTO {

    private FeedbackViewDTO data; // du lieu search

    public FeedbackViewDTO getData() {
        return data;
    }//get data

    public void setData( FeedbackViewDTO data ) {
        this.data = data;
    }//setdata
}
