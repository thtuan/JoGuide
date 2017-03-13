package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.ProfileCollectionViewDTO;

/**
 * DTO response profile
 *
 * @author tuanlt
 * @since 4:08 PM 7/11/2016
 */
public class ProfileCollectionResponseDTO extends BaseResponseDTO {
    private ProfileCollectionViewDTO data; // data view of profile collection

    public ProfileCollectionViewDTO getData() {
        return data;
    }

    public void setData( ProfileCollectionViewDTO data ) {
        this.data = data;
    }
}
