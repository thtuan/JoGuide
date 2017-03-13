package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.TourInviteDTO;

/**
 * TourInviteResponseDTO
 *
 * @author tuanlt
 * @since 2:06 PM 12/16/16
 */
public class TourInviteResponseDTO extends BaseResponseDTO {

    private TourInviteDTO data; //Data

    public TourInviteDTO getData() {
        return data;
    }

    public void setData(TourInviteDTO data) {
        this.data = data;
    }
}
