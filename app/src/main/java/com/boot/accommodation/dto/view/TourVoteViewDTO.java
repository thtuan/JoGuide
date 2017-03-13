package com.boot.accommodation.dto.view;

import java.util.List;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 3:47 PM 20-06-2016
 */
public class TourVoteViewDTO extends BaseDTO {
    private List<TourVoteDTO> vote;

    public TourVoteViewDTO( List<TourVoteDTO> tourVoteDTOs) {
        this.vote = tourVoteDTOs;
    }

    public List<TourVoteDTO> getVote() {
        return vote;
    }

    public void setVote( List<TourVoteDTO> vote ) {
        this.vote = vote;
    }

}
