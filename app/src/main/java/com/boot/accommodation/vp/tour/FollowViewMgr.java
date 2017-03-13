package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.view.FollowItemDTO;

import java.util.List;

/**
 * FollowViewMgr
 *
 * @author thtuan
 * @since 12:47 PM 26-09-2016
 */
public interface FollowViewMgr {
    void renderLayout(List<FollowItemDTO> listFollower);

    void requestFollowSuccess(boolean b);

    void showMessageErr(int errorCode, String error);
}
