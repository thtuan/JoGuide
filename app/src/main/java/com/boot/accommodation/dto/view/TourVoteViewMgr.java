package com.boot.accommodation.dto.view;

import java.util.List;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 4:14 PM 20-06-2016
 */
public interface TourVoteViewMgr {
    /**
     * send message when send vote
     */
    void showMessageForPostSuccess();

    /**
     * show error
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);


    /**
     * Render layout
     * @param lstTourVote
     */
    void renderLayout(List<TourVoteDTO> lstTourVote);
}
