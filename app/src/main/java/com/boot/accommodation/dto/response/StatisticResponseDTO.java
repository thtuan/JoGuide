package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.StatisticsViewDTO;

/**
 * Response statistic
 *
 * @author tuanlt
 * @since 4:56 PM 7/12/2016
 */
public class StatisticResponseDTO extends BaseResponseDTO {

    private StatisticsViewDTO data; // data get from server

    public StatisticsViewDTO getData() {
        return data;
    }

    public void setData( StatisticsViewDTO data ) {
        this.data = data;
    }
}
