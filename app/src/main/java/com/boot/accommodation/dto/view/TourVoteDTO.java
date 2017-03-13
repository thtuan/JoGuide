package com.boot.accommodation.dto.view;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 3:32 PM 20-06-2016
 */
public class TourVoteDTO extends BaseDTO {
    private long tourVoteAttributeId; //id vote attribuate
    private long tourVoteId; //id vote
    private String title; // title
    private int voteValue; // vote value
    private String voteContent; // For others

    public TourVoteDTO() {
    }

    public TourVoteDTO( String title ) {
        this.title = title;
    }

    public int getVoteValue() {
        return voteValue;
    }

    public void setVoteValue( int voteValue ) {
        this.voteValue = voteValue;
    }

    public String getTitle() {
        return title;
    }


    public void setTitle( String title ) {
        this.title = title;
    }

    public String getVoteContent() {
        return voteContent;
    }

    public void setVoteContent( String voteContent ) {
        this.voteContent = voteContent;
    }

    public long getTourVoteAttributeId() {
        return tourVoteAttributeId;
    }

    public void setTourVoteAttributeId( long tourVoteAttributeId ) {
        this.tourVoteAttributeId = tourVoteAttributeId;
    }

    public long getTourVoteId() {
        return tourVoteId;
    }

    public void setTourVoteId( long tourVoteId ) {
        this.tourVoteId = tourVoteId;
    }
}
