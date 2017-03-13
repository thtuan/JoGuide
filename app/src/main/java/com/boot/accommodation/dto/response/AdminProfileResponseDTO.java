package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.ProfileUserViewDTO;

/**
 * response for profile of user
 *
 * @author vuong
 * @since 2:01 PM 6/6/2016
 */
public class AdminProfileResponseDTO extends BaseResponseDTO {
    private ProfileUserViewDTO data;

    public ProfileUserViewDTO getData() {
        return data;
    }

    public void setData( ProfileUserViewDTO data ) {
        this.data = data;
    }
}
