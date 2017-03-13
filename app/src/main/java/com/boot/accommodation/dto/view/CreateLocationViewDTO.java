package com.boot.accommodation.dto.view;

import java.util.List;

/**
 * DTO cho man hinh CreateLocation
 *
 * @author vuongbv
 * @since 3:25 PM 20/6/2016
 */
public class CreateLocationViewDTO extends BaseDTO {

    private List<CategoryItemDTO> category;//list category
    private List<CategoryItemDTO> month; //Month

    public List<CategoryItemDTO> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryItemDTO> category) {
        this.category = category;
    }

    public List<CategoryItemDTO> getMonth() {
        return month;
    }

    public void setMonth(List<CategoryItemDTO> month) {
        this.month = month;
    }
}
