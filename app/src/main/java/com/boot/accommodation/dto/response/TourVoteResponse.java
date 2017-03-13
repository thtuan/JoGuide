package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.TourVoteDTO;

import java.util.List;

/**
 * Response get tour
 *
 * @author tuanlt
 * @since 10:44 AM 7/18/2016
 */
public class TourVoteResponse extends BaseResponseDTO {
    private List<TourVoteDTO> data;

    public List<TourVoteDTO> getData() {
        return data;
    }

    public void setData(List<TourVoteDTO> data) {
        this.data = data;
    }
}
