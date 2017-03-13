package com.boot.accommodation.vp.location;

import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.dto.view.TripItemDTO;

import java.util.List;

/**
 * View Mgr of place detail
 *
 * @author Dungnx
 */
public interface LocationDetailOverviewMgr {
    /**
     * Render layout
     *
     * @param placeItemDTO
     */
    void renderLayout(PlaceItemDTO placeItemDTO);

    /**
     * show error
     *
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);

    void finishProcessDialog();

    /**
     * Render tour relate
     *
     * @param tripItemDTO
     */
    void renderTourRelate(List<TripItemDTO> tripItemDTO);
}
