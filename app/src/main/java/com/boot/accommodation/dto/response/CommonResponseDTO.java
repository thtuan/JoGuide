package com.boot.accommodation.dto.response;

/**
 * DTO return data is object( Boolean, Integer...)
 *
 * @author tuanlt
 * @since 1:55 PM 7/12/2016
 */
public class CommonResponseDTO extends BaseResponseDTO {
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData( Object data ) {
        this.data = data;
    }
}
