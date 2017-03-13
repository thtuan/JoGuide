package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.SearchTourViewDTO;

/**
 * DTO chua thong tin search tour
 *
 * @author vuong-bv
 * @since 3:22 PM 6/10/2016
 */
public class SearchTourResponseDTO extends BaseResponseDTO {
    private SearchTourViewDTO data; // du lieu search

    public SearchTourViewDTO getData() {
        return data;
    }

    public void setData( SearchTourViewDTO data ) {
        this.data = data;
    }
}
