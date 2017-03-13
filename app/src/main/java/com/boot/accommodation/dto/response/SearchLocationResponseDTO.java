package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.SearchLocationViewDTO;

/**
 * DTO chua thong tin search tour
 *
 * @author vuong-bv
 * @since 3:22 PM 6/10/2016
 */
public class SearchLocationResponseDTO extends BaseResponseDTO {
    private SearchLocationViewDTO data; // du lieu search

    public SearchLocationViewDTO getData() {
        return data;
    }

    public void setData( SearchLocationViewDTO data ) {
        this.data = data;
    }
}
