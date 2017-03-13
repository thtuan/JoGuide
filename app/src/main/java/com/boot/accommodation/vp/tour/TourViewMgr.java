package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.view.TourInviteDTO;
import com.boot.accommodation.dto.view.TripItemDTO;

/**
 * Mgr View cho module tour
 *
 * @author tuanlt
 * @since: 1:39 PM 5/4/2016
 */
public interface TourViewMgr {
    /**
     * show message when user send feedback
     * @param check : if true : success. false : is fail
     */
    void showMessage(boolean check);

    /**
     * show error
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);

    /**
     * get tour plan success
     * @param data
     */
    void getTourPlanSuccess(TripItemDTO data);

    /**
     * Invite tour success
     */
    void inviteTourSuccess();

    /**
     * Get invited success
     */
    void getIInvitedSuccess(TourInviteDTO tourInviteDTO);

    /**
     * Request accept join tour success
     */
    void requestAcceptJoinTourSuccess();

    /**
     * Request join tour success
     * @param result
     */
    void requestAskJoinTourSuccess(Integer result);

    /**
     * Request check asked join tour
     * @param result
     */
    void requestCheckAskedJoinTourSuccess(Integer result);

    /**
     * Request accept join tour success
     */
    void requestDeclineJoinTourSuccess();
}
