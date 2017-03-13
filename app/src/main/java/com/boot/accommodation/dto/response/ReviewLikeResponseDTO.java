package com.boot.accommodation.dto.response;

/**
 * Data reponse when like review
 *
 * @author tuanlt
 * @since 9:43 PM 7/5/2016
 */
public class ReviewLikeResponseDTO extends BaseResponseDTO {
    private Integer data; // data when like review

    public Integer getData() {
        return data;
    }

    public void setData( Integer data ) {
        this.data = data;
    }
}
