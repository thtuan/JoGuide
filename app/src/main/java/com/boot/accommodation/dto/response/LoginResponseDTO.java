package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.UserItemDTO;

/**
 * Reponse DTO
 *
 * @author tuanlt
 * @since 10:29 AM 6/7/2016
 */
public class LoginResponseDTO extends BaseResponseDTO {
    private UserItemDTO data;

    public UserItemDTO getData() {
        return data;
    }

    public void setData( UserItemDTO data ) {
        this.data = data;
    }
}
