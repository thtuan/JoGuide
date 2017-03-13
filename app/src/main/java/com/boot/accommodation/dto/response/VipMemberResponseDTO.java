package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.VipMemberDTO;

/**
 * VipMemberResponseDTO
 *
 * @author tuanlt
 * @since 8:32 PM 12/25/16
 */
public class VipMemberResponseDTO extends BaseResponseDTO {

    private VipMemberDTO data;

    public VipMemberDTO getData() {
        return data;
    }

    public void setData(VipMemberDTO data) {
        this.data = data;
    }
}
