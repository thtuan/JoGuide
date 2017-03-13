package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.CategoryItemDTO;

import java.util.List;

/**
 * TimeToGoResponseDTO
 *
 * @author tuanlt
 * @since 3:24 PM 10/21/16
 */
public class TimeToGoResponseDTO extends BaseResponseDTO {
    private List<CategoryItemDTO> data; //Month

    public List<CategoryItemDTO> getData() {
        return data;
    }

    public void setData(List<CategoryItemDTO> data) {
        this.data = data;
    }
}
