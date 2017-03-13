package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.ReviewItemDTO;

/**
 * Response when create review
 *
 * @author tuanlt
 * @since 2:45 PM 7/6/2016
 */
public class ReviewCreateResponseDTO extends BaseResponseDTO {
    private ReviewItemDTO data; // data get from server

    public ReviewItemDTO getData() {
        return data;
    }

    public void setData( ReviewItemDTO data ) {
        this.data = data;
    }
}
