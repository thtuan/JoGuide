package com.boot.accommodation.model.mgr;

import com.boot.accommodation.dto.view.UserPositionLogDTO;
import com.boot.accommodation.listener.ModelCallBack;

/**
 * Mgr xu li vi tri
 *
 * @author tuanlt
 * @since 2:48 PM 6/7/2016
 */
public interface PositionModelMgr {

    /**
     * Day vi tri toi server
     * @param userPositionLogDTO
     */
    void updatePosition( UserPositionLogDTO userPositionLogDTO, ModelCallBack modelCallBack);
}
