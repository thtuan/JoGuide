package com.boot.accommodation.dto.response;

import com.boot.accommodation.base.BaseResponse;
import com.boot.accommodation.dto.view.AreaDTO;

import java.util.List;

/**
 * Location filter
 *
 * @author tuanlt
 * @since 4:47 PM  8/20/2016.
 */
public class FamousLocationResponseDTO extends BaseResponse {
    private List<AreaDTO> data;


    public List<AreaDTO> getData() {
        return data;
    }

    public void setData(List<AreaDTO> data) {
        this.data = data;
    }
}
