package com.boot.accommodation.dto.response;

import java.util.List;

/**
 * Place picker response
 *
 * @author tuanlt
 * @since 6:04 CH 09/09/2016
 */
public class PlacePickerResponseDTO extends BaseResponseDTO {
    private List<PlacePickerItemDTO> results; // Result
    private String next_page_token; // Next page token

    public List<PlacePickerItemDTO> getResults() {
        return results;
    }

    public void setResults( List<PlacePickerItemDTO> results ) {
        this.results = results;
    }

    public String getNext_page_token() {
        return next_page_token;
    }

    public void setNext_page_token(String next_page_token) {
        this.next_page_token = next_page_token;
    }
}

