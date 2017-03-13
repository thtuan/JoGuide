package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.LeaderRatedViewDTO;

/**
 * Nhan du lieu tu API tra ve
 *
 * @author vuong
 * @since: 15:04 PM 5/13/2016
 */
public class LeaderRatedResponseDTO extends BaseResponseDTO {
    /**
     * call view dto
     */
    private LeaderRatedViewDTO data;

    /**
     * get data
     *
     * @return
     */
    public LeaderRatedViewDTO getData() {
        return data;
    }

    /**
     * set data
     *
     * @param data
     */
    public void setData(LeaderRatedViewDTO data) {
        this.data = data;
    }
}
