package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.VipMemberConfigDTO;

/**
 * VipMemberConfigResponseDTO
 *
 * @author tuanlt
 * @since 8:34 PM 12/25/16
 */
public class VipMemberConfigResponseDTO extends BaseResponseDTO {

    private VipMemberConfigDTO data; //Data

    public VipMemberConfigDTO getData() {
        return data;
    }

    public void setData(VipMemberConfigDTO data) {
        this.data = data;
    }
}
