package com.boot.accommodation.model.mgr;

import com.boot.accommodation.dto.view.FeedbackItemDTO;
import com.boot.accommodation.dto.view.TabletActionLogDTO;
import com.boot.accommodation.listener.ModelCallBack;

/**
 * Model common mgr
 *
 * @author tuanlt
 * @since 10:54 SA 11/08/2016
 */
public interface CommonModelMgr {
    /**
     * Update action log
     *
     * @param tabletActionLogDTO
     * @param modelCallBack
     */
    void requestUpdateLog( TabletActionLogDTO tabletActionLogDTO, ModelCallBack modelCallBack );

    /**
     * Cancel request
     * @param tag
     */
    void cancelRequest(String tag);

    /**
     * Renew token
     * @param userId
     */
    void requestRenewToken(String userId, ModelCallBack modelCallBack);

    /**
     * Send feedback
     * @param feedbackItemDTO
     * @param modelCallBack
     */
    void requestSendFeedback(FeedbackItemDTO feedbackItemDTO, ModelCallBack modelCallBack);
}
