package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.TouristInfoViewDTO;

/**
 * Nhan du lieu tu API tra ve
 *
 * @author vuong
 * @since: 15:04 PM 5/13/2016
 */
public class TouristInfoResponseDTO extends BaseResponseDTO {
    private TouristInfoViewDTO data;

    public TouristInfoViewDTO getData() {
        return data;
    }
    public void setData(TouristInfoViewDTO data ) {
        this.data = data;
    }
}
