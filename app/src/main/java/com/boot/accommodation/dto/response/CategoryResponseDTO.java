package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.CategoryItemDTO;

import java.util.List;

/**
 * CategoryResponseDTO
 *
 * @author tuanlt
 * @since 4:16 PM 10/14/16
 */
public class CategoryResponseDTO extends BaseResponseDTO {
    private List<CategoryItemDTO> data; //List data category

    public List<CategoryItemDTO> getData() {
        return data;
    }

    public void setData(List<CategoryItemDTO> data) {
        this.data = data;
    }
}
