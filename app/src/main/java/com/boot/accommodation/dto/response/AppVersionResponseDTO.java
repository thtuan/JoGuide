package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.AppVersionDTO;

/**
 * AppVersionResponseDTO
 *
 * @author tuanlt
 * @since 3:57 PM 12/15/16
 */
public class AppVersionResponseDTO extends BaseResponseDTO {
    private AppVersionDTO data;

    public AppVersionDTO getData() {
        return data;
    }

    public void setData(AppVersionDTO data) {
        this.data = data;
    }
}
