package com.boot.accommodation.vp.common;

import com.boot.accommodation.dto.view.TabletActionLogDTO;

/**
 * Presenter commont
 *
 * @author tuanlt
 * @since 10:47 CH 26/07/2016
 */
public interface CommonPresenterMgr {

    /**
     * Request update log
     * @param tabletActionLogDTO
     */
    void requestUpdateLog( TabletActionLogDTO tabletActionLogDTO);

    /**
     * Cancel list request
     */
    void cancelListRequest( String tag );

    /**
     * Renew token
     */
    void requestRenewToken(String userId);
}
