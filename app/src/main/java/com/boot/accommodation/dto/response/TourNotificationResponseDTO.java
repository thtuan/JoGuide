package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.TourNotificationViewDTO;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 11:19 AM 17-05-2016
 */
public class TourNotificationResponseDTO extends BaseResponseDTO {
    private TourNotificationViewDTO data;

    public TourNotificationViewDTO getData() {
        return data;
    }

    public void setData(TourNotificationViewDTO data) {
        this.data = data;
    }
}
