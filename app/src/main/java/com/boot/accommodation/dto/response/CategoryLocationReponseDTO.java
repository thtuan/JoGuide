package com.boot.accommodation.dto.response;

import com.boot.accommodation.base.BaseResponse;
import com.boot.accommodation.dto.view.CreateLocationViewDTO;

/**
 * ResponseDTO category
 *
 * @author vuong-bv
 * @since 4:47 PM  8/20/2016.
 */
public class CategoryLocationReponseDTO extends BaseResponse {
    private CreateLocationViewDTO data;//data category

    public CreateLocationViewDTO getData() {
        return data;
    }

    public void setData(CreateLocationViewDTO data) {
        this.data = data;
    }
}
