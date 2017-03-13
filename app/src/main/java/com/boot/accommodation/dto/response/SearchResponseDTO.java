package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.SearchViewDTO;

/**
 * DTO chua thong tin search
 *
 * @author tuanlt
 * @since 3:22 PM 5/19/2016
 */
public class SearchResponseDTO extends BaseResponseDTO {
    private SearchViewDTO data; // du lieu search

    public SearchViewDTO getData() {
        return data;
    }

    public void setData( SearchViewDTO data ) {
        this.data = data;
    }
}
