package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.DiscussViewDTO;

/**
 * Nhan du lieu tu API tra ve
 *
 * @author vuong
 * @since: 15:04 PM 5/13/2016
 */
public class DiscussResponseDTO extends BaseResponseDTO {
    /**
     * call view dto
     */
    private DiscussViewDTO data;

    /**
     * method get
     *
     * @return
     */
    public DiscussViewDTO getData() {
        return data;
    }

    /**
     * method set
     *
     * @param data
     */
    public void setData( DiscussViewDTO data ) {
        this.data = data;
    }
}
