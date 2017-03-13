package com.boot.accommodation.vp.location;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.dto.view.PlaceItemDTO;

/**
 * Mo ta class
 *
 * @author Dungnx
 */
public interface LocationDetailOverviewPresenterMgr {
    /**
     * get olace info
     *
     * @param dto
     */
    void getOverviewInfo(PlaceItemDTO dto);

    /**
     * get tour relate
     *
     * @param locationId
     */
    void getTourRelate(long locationId);

    /**
     * Get more Tour relate
     *
     * @param locationId
     * @param adapter
     */
    void getMoreTourRelate(long locationId, BaseRecyclerViewAdapter adapter);
}
