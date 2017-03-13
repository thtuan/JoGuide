package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.FollowUserDTO;

/**
 * FollowUserResponseDTO
 *
 * @author thtuan
 * @since 1:35 PM 26-09-2016
 */
public class FollowUserResponseDTO extends BaseResponseDTO {
    public FollowUserDTO getData() {
        return data;
    }

    public void setData(FollowUserDTO data) {
        this.data = data;
    }

    private FollowUserDTO data;
}
