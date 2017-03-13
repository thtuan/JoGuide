package com.boot.accommodation.dto.response;

import com.boot.accommodation.base.BaseResponse;
import com.boot.accommodation.dto.view.LocationFilterViewDTO;

/**
 * Location filter
 *
 * @author tuanlt
 * @since 4:47 PM  8/20/2016.
 */
public class LocationFilterResponseDTO extends BaseResponse {
    private LocationFilterViewDTO data;//data category

    public LocationFilterViewDTO getData() {
        return data;
    }

    public void setData(LocationFilterViewDTO data) {
        this.data = data;
    }
}
